package com.agrosync.application.usecase.lotes.impl;

import com.agrosync.application.primaryports.dto.animales.request.EditarAnimalDTO;
import com.agrosync.application.primaryports.dto.animales.request.RegistrarNuevoAnimalDTO;
import com.agrosync.application.primaryports.dto.lotes.request.EditarLoteDTO;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.repository.*;
import com.agrosync.application.usecase.animales.rulesvalidator.EditarAnimalRulesValidator;
import com.agrosync.application.usecase.animales.rulesvalidator.RegistrarNuevoAnimalRulesValidator;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.cuentas.CompensarCuentas;
import com.agrosync.application.usecase.lotes.EditarLote;
import com.agrosync.application.usecase.lotes.rulesvalidator.EditarLoteRulesValidator;
import com.agrosync.crosscutting.helpers.GenerarNumeroHelper;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.exceptions.AnimalNoEditableException;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.lotes.exceptions.IdentificadorLoteNoExisteException;
import com.agrosync.domain.lotes.exceptions.ListaAnimalesVaciaException;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class EditarLoteImpl implements EditarLote {

    private final LoteRepository loteRepository;
    private final AnimalRepository animalRepository;
    private final ActualizarCartera actualizarCartera;
    private final EditarLoteRulesValidator editarLoteRulesValidator;
    private final EditarAnimalRulesValidator editarAnimalRulesValidator;
    private final RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator;
    private final AbonoRepository abonoRepository;
    private final CobroRepository cobroRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final CompensarCuentas compensarCuentas;
    private final CuentaPagarRepository cuentaPagarRepository;

    public EditarLoteImpl(LoteRepository loteRepository,
                          AnimalRepository animalRepository,
                          ActualizarCartera actualizarCartera,
                          EditarLoteRulesValidator editarLoteRulesValidator,
                          EditarAnimalRulesValidator editarAnimalRulesValidator,
                          RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator,
                          AbonoRepository abonoRepository,
                          CobroRepository cobroRepository,
                          CuentaCobrarRepository cuentaCobrarRepository,
                          CompensarCuentas compensarCuentas, CuentaPagarRepository cuentaPagarRepository) {
        this.loteRepository = loteRepository;
        this.animalRepository = animalRepository;
        this.actualizarCartera = actualizarCartera;
        this.editarLoteRulesValidator = editarLoteRulesValidator;
        this.editarAnimalRulesValidator = editarAnimalRulesValidator;
        this.registrarNuevoAnimalRulesValidator = registrarNuevoAnimalRulesValidator;
        this.abonoRepository = abonoRepository;
        this.cobroRepository = cobroRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.compensarCuentas = compensarCuentas;
        this.cuentaPagarRepository = cuentaPagarRepository;
    }

    @Override
    public void ejecutar(EditarLoteDTO data) {
        // 1. Obtener lote existente
        LoteEntity lote = loteRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorLoteNoExisteException::create);

        // 2. Validar reglas del lote
        LoteDomain loteDomain = new LoteDomain();
        loteDomain.setId(data.getId());
        loteDomain.setSuscripcionId(data.getSuscripcionId());
        loteDomain.setContramarca(data.getContramarca());
        editarLoteRulesValidator.validar(loteDomain);

        UUID suscripcionId = data.getSuscripcionId();
        SuscripcionEntity suscripcion = lote.getSuscripcion();

        // Track de cambios en precios de compra para recalcular
        BigDecimal diferenciaPrecioCompra = BigDecimal.ZERO;

        // 3. Procesar animales a editar
        if (!ObjectHelper.isNull(data.getAnimalesAEditar())) {
            for (EditarAnimalDTO animalDTO : data.getAnimalesAEditar()) {
                BigDecimal diferencia = procesarEdicionAnimal(animalDTO, suscripcionId);
                diferenciaPrecioCompra = diferenciaPrecioCompra.add(diferencia);
            }
        }

        // 4. Procesar animales a eliminar (verificar que no estén vendidos)
        if (!ObjectHelper.isNull(data.getAnimalesAEliminar())) {
            for (UUID animalId : data.getAnimalesAEliminar()) {
                diferenciaPrecioCompra = diferenciaPrecioCompra
                        .add(procesarEliminacionAnimal(animalId, suscripcionId));
            }
        }

        // 5. Procesar animales a agregar
        if (!ObjectHelper.isNull(data.getAnimalesAAgregar())) {
            for (RegistrarNuevoAnimalDTO nuevoAnimalDTO : data.getAnimalesAAgregar()) {
                diferenciaPrecioCompra = diferenciaPrecioCompra
                        .add(procesarNuevoAnimal(nuevoAnimalDTO, lote, suscripcion));
            }
        }

        // 6. Validar que el lote no quede vacío
        long animalesActivos = lote.getAnimales().stream()
                .filter(a -> a.getEstado() != EstadoAnimalEnum.ANULADO)
                .count();
        if (animalesActivos == 0) {
            throw ListaAnimalesVaciaException.create();
        }

        // 7. Actualizar contramarca si cambió
        if (!TextHelper.isEmpty(data.getContramarca()) &&
                !data.getContramarca().equals(lote.getContramarca())) {
            lote.setContramarca(data.getContramarca());
        }

        // 8. Recalcular peso total del lote
        BigDecimal pesoTotal = calcularPesoTotal(lote);
        lote.setPesoTotal(pesoTotal);

        // 9. Actualizar compra y cuenta por pagar si hay diferencia en precio de compra
        if (diferenciaPrecioCompra.compareTo(BigDecimal.ZERO) != 0) {
            actualizarCompraYCuentaPagar(lote, diferenciaPrecioCompra);
        }

        // 10. Guardar lote
        loteRepository.save(lote);
    }

    private BigDecimal procesarEdicionAnimal(EditarAnimalDTO animalDTO, UUID suscripcionId) {
        BigDecimal diferenciaPrecioCompra;

        AnimalEntity animal = animalRepository.findByIdAndSuscripcion_Id(animalDTO.getId(), suscripcionId)
                .orElseThrow(IdentificadorAnimalNoExisteException::create);

        // Validar reglas de edición del animal
        AnimalDomain animalDomain = new AnimalDomain();
        animalDomain.setId(animalDTO.getId());
        animalDomain.setSuscripcionId(suscripcionId);
        animalDomain.setPeso(animalDTO.getPeso());
        animalDomain.setPrecioKiloCompra(animalDTO.getPrecioKiloCompra());
        editarAnimalRulesValidator.validar(animalDomain);

        boolean esVendido = animal.getEstado() == EstadoAnimalEnum.VENDIDO;

        if (esVendido && (animalDTO.getSexo() != null || animalDTO.getPeso() != null)) {
            throw AnimalNoEditableException.createPorVendido();
        }

        // Calcular costo anterior
        BigDecimal pesoAnterior = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
        BigDecimal precioCompraAnterior = ObjectHelper.getDefault(animal.getPrecioKiloCompra(), BigDecimal.ZERO);
        BigDecimal costoAnterior = pesoAnterior.multiply(precioCompraAnterior);

        // Actualizar datos
        if (animalDTO.getSexo() != null) {
            animal.setSexo(animalDTO.getSexo());
        }

        if (animalDTO.getPeso() != null) {
            animal.setPeso(animalDTO.getPeso());
        }

        if (animalDTO.getPrecioKiloCompra() != null) {
            animal.setPrecioKiloCompra(animalDTO.getPrecioKiloCompra());
        }

        // Calcular costo nuevo
        BigDecimal pesoNuevo = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
        BigDecimal precioCompraNuevo = ObjectHelper.getDefault(animal.getPrecioKiloCompra(), BigDecimal.ZERO);
        BigDecimal costoNuevo = pesoNuevo.multiply(precioCompraNuevo);

        diferenciaPrecioCompra = costoNuevo.subtract(costoAnterior);

        animalRepository.save(animal);
        return diferenciaPrecioCompra;
    }

    private BigDecimal procesarEliminacionAnimal(UUID animalId, UUID suscripcionId) {
        AnimalEntity animal = animalRepository.findByIdAndSuscripcion_Id(animalId, suscripcionId)
                .orElseThrow(IdentificadorAnimalNoExisteException::create);

        // No permitir eliminar animales vendidos
        if (animal.getEstado() == EstadoAnimalEnum.VENDIDO) {
            throw AnimalNoEditableException.createPorVendido();
        }

        // Calcular diferencia en precio de compra (restar el costo del animal)
        BigDecimal peso = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
        BigDecimal precioCompra = ObjectHelper.getDefault(animal.getPrecioKiloCompra(), BigDecimal.ZERO);
        BigDecimal costoAnimal = peso.multiply(precioCompra).negate();

        // Marcar animal como anulado en lugar de eliminarlo físicamente
        animal.setEstado(EstadoAnimalEnum.ANULADO);
        animalRepository.save(animal);

        return costoAnimal;
    }

    private BigDecimal procesarNuevoAnimal(RegistrarNuevoAnimalDTO nuevoAnimalDTO, LoteEntity lote,
            SuscripcionEntity suscripcion) {
        // Validar reglas del nuevo animal
        AnimalDomain animalDomain = new AnimalDomain();
        animalDomain.setSuscripcionId(suscripcion.getId());
        animalDomain.setPeso(nuevoAnimalDTO.getPeso());
        animalDomain.setPrecioKiloCompra(nuevoAnimalDTO.getPrecioKiloCompra());
        registrarNuevoAnimalRulesValidator.validar(animalDomain);

        // Crear nuevo animal
        AnimalEntity nuevoAnimal = new AnimalEntity();
        nuevoAnimal.setSlot(nuevoAnimalDTO.getSlot());
        nuevoAnimal.setPeso(nuevoAnimalDTO.getPeso());
        nuevoAnimal.setSexo(nuevoAnimalDTO.getSexo());
        nuevoAnimal.setPrecioKiloCompra(nuevoAnimalDTO.getPrecioKiloCompra());
        nuevoAnimal.setPrecioKiloVenta(BigDecimal.ZERO);
        nuevoAnimal.setEstado(EstadoAnimalEnum.DISPONIBLE);
        nuevoAnimal.setLote(lote);
        nuevoAnimal.setSuscripcion(suscripcion);
        nuevoAnimal.setVenta(null);

        // Generar número de animal
        nuevoAnimal.setNumeroAnimal(GenerarNumeroHelper.generarNumeroAnimal(
                lote.getContramarca(), nuevoAnimal.getSlot(), lote.getFecha()));

        lote.getAnimales().add(nuevoAnimal);

        // Calcular costo del nuevo animal
        BigDecimal peso = ObjectHelper.getDefault(nuevoAnimalDTO.getPeso(), BigDecimal.ZERO);
        BigDecimal precioCompra = ObjectHelper.getDefault(nuevoAnimalDTO.getPrecioKiloCompra(), BigDecimal.ZERO);

        return peso.multiply(precioCompra);
    }

    private BigDecimal calcularPesoTotal(LoteEntity lote) {
        return lote.getAnimales().stream()
                .filter(a -> a.getEstado() != EstadoAnimalEnum.ANULADO)
                .map(a -> ObjectHelper.getDefault(a.getPeso(), BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void actualizarCompraYCuentaPagar(LoteEntity lote, BigDecimal diferenciaPrecio) {
        if (lote.getCompra() == null)
            return;

        var compra = lote.getCompra();
        var cuentaPagar = compra.getCuentaPagar();

        // Actualizar precio total de compra
        BigDecimal precioActual = ObjectHelper.getDefault(compra.getPrecioTotalCompra(), BigDecimal.ZERO);
        compra.setPrecioTotalCompra(precioActual.add(diferenciaPrecio));

        // Actualizar cuenta por pagar
        if (cuentaPagar != null) {
            BigDecimal montoTotalActual = ObjectHelper.getDefault(cuentaPagar.getMontoTotal(), BigDecimal.ZERO);
            BigDecimal saldoPendienteActual = ObjectHelper.getDefault(cuentaPagar.getSaldoPendiente(), BigDecimal.ZERO);

            cuentaPagar.setMontoTotal(montoTotalActual.add(diferenciaPrecio));
            cuentaPagar.setSaldoPendiente(saldoPendienteActual.add(diferenciaPrecio));

            // Actualizar estado de la cuenta según el saldo
            actualizarEstadoCuenta(cuentaPagar);

            // Actualizar cartera del proveedor
            if (compra.getProveedor() != null && lote.getSuscripcion() != null) {
                UUID proveedorId = compra.getProveedor().getId();
                UUID suscripcionId = lote.getSuscripcion().getId();

                if (diferenciaPrecio.compareTo(BigDecimal.ZERO) > 0) {
                    actualizarCartera.incrementarCuentasPagar(proveedorId, suscripcionId, diferenciaPrecio);
                } else {
                    actualizarCartera.reducirCuentasPagarPorAbono(proveedorId, suscripcionId, diferenciaPrecio.abs());
                }
            }

            // Anular pagos/cruces y regenerar siempre que haya cambio
            anularYRegenerarPagosYCruces(cuentaPagar, lote.getSuscripcion().getId());
        }
    }

    private void actualizarEstadoCuenta(CuentaPagarEntity cuenta) {
        BigDecimal saldo = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO);
        BigDecimal montoTotal = ObjectHelper.getDefault(cuenta.getMontoTotal(), BigDecimal.ZERO);

        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            cuenta.setEstado(EstadoCuentaEnum.PAGADA);
        } else if (saldo.compareTo(montoTotal) < 0) {
            cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        } else {
            cuenta.setEstado(EstadoCuentaEnum.PENDIENTE);
        }
    }

    private void anularYRegenerarPagosYCruces(CuentaPagarEntity cuentaPagar, UUID suscripcionId) {
        // Anular abonos automáticos por cruce de cuentas
        if (!ObjectHelper.isNull(cuentaPagar.getAbonos())) {
            cuentaPagar.getAbonos().stream()
                    .filter(abono -> abono.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                     abono.getEstado() != EstadoAbonoEnum.ANULADO)
                    .forEach(abono -> {
                        abono.setEstado(EstadoAbonoEnum.ANULADO);
                        abono.setMotivoAnulacion("Anulación automática por edición de lote");
                        abono.setFechaAnulacion(java.time.LocalDateTime.now());
                    });
        }

        // Anular cuentas por cobrar creadas por cruce
        List<CuentaCobrarEntity> cuentasCreadasPorCruce = cuentaCobrarRepository
            .findByCliente_IdAndSuscripcion_Id(cuentaPagar.getCompra().getProveedor().getId(), suscripcionId)
            .stream()
            .filter(cuenta -> ObjectHelper.isNull(cuenta.getVenta()) &&
                              !ObjectHelper.isNull(cuenta.getCobros()) &&
                              cuenta.getCobros().stream()
                                  .anyMatch(cobro -> cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                                    cobro.getEstado() != EstadoCobroEnum.ANULADO))
            .toList();

        for (CuentaCobrarEntity cuenta : cuentasCreadasPorCruce) {
            cuenta.setEstado(EstadoCuentaEnum.ANULADA);
            cuenta.setSaldoPendiente(BigDecimal.ZERO);
            cuenta.getCobros().stream()
                .filter(cobro -> cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                cobro.getEstado() != EstadoCobroEnum.ANULADO)
                .forEach(cobro -> {
                    cobro.setEstado(EstadoCobroEnum.ANULADO);
                    cobro.setMotivoAnulacion("Anulación automática por edición de lote");
                    cobro.setFechaAnulacion(java.time.LocalDateTime.now());
                });
            cuentaCobrarRepository.save(cuenta);
        }

        // Regenerar pagos/cruces basados en el nuevo saldo llamando a la lógica de compensación
        BigDecimal nuevoSaldoPendiente = ObjectHelper.getDefault(cuentaPagar.getSaldoPendiente(), BigDecimal.ZERO);
        if (nuevoSaldoPendiente.compareTo(BigDecimal.ZERO) > 0) {
            CompensarCuentas.ResultadoCompensacion resultado = compensarCuentas.compensarCuentasCobrarConCompra(
                    cuentaPagar.getCompra().getProveedor(),
                    cuentaPagar.getCompra().getSuscripcion(),
                    nuevoSaldoPendiente,
                    cuentaPagar.getCompra().getFechaCompra(),
                    cuentaPagar.getCompra().getNumeroCompra()
            );

            // Actualizar el saldo pendiente de la cuenta por pagar con el saldo restante
            cuentaPagar.setSaldoPendiente(resultado.saldoRestante());
            cuentaPagarRepository.save(cuentaPagar);
        }
    }
}

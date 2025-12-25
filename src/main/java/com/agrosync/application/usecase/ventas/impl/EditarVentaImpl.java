package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.EditarPrecioVentaAnimalDTO;
import com.agrosync.application.primaryports.dto.ventas.request.EditarVentaDTO;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.cuentas.CompensarCuentas;
import com.agrosync.application.usecase.ventas.EditarVenta;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.domain.enums.ventas.EstadoVentaEnum;
import com.agrosync.domain.ventas.exceptions.IdentificadorVentaNoExisteException;
import com.agrosync.domain.ventas.exceptions.VentaNoEditableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EditarVentaImpl implements EditarVenta {

    private final VentaRepository ventaRepository;
    private final AnimalRepository animalRepository;
    private final ActualizarCartera actualizarCartera;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final CompensarCuentas compensarCuentas;
    private final CuentaCobrarRepository cuentaCobrarRepository;

    public EditarVentaImpl(VentaRepository ventaRepository,
                           AnimalRepository animalRepository,
                           ActualizarCartera actualizarCartera,
                           CuentaPagarRepository cuentaPagarRepository,
                           CompensarCuentas compensarCuentas, CuentaCobrarRepository cuentaCobrarRepository) {
        this.ventaRepository = ventaRepository;
        this.animalRepository = animalRepository;
        this.actualizarCartera = actualizarCartera;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.compensarCuentas = compensarCuentas;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
    }

    @Override
    public void ejecutar(EditarVentaDTO data) {
        // 1. Obtener venta
        VentaEntity venta = ventaRepository.findByIdAndSuscripcion_Id(data.getVentaId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorVentaNoExisteException::create);

        // 2. Validar estado de la venta
        if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
            throw VentaNoEditableException.create();
        }

        BigDecimal diferenciaPrecioTotalVenta = BigDecimal.ZERO;

        // 3. Procesar cambios de precio en animales
        if (!ObjectHelper.isNull(data.getAnimales())) {
            for (EditarPrecioVentaAnimalDTO animalDTO : data.getAnimales()) {
                BigDecimal diferencia = procesarCambioPrecioAnimal(animalDTO, venta, data.getSuscripcionId());
                diferenciaPrecioTotalVenta = diferenciaPrecioTotalVenta.add(diferencia);
            }
        }

        // 4. Actualizar venta y cuenta por cobrar si hubo cambios
        if (diferenciaPrecioTotalVenta.compareTo(BigDecimal.ZERO) != 0) {
            actualizarVentaYCuentaCobrar(venta, diferenciaPrecioTotalVenta, data.getSuscripcionId());
        }

        ventaRepository.save(venta);
    }

    private BigDecimal procesarCambioPrecioAnimal(EditarPrecioVentaAnimalDTO animalDTO, VentaEntity venta,
            UUID suscripcionId) {
        AnimalEntity animal = animalRepository.findByIdAndSuscripcion_Id(animalDTO.getAnimalId(), suscripcionId)
                .orElseThrow(IdentificadorAnimalNoExisteException::create);

        // Verificar que el animal pertenezca a la venta
        if (animal.getVenta() == null || !animal.getVenta().getId().equals(venta.getId())) {
            // Se podría lanzar una excepción específica, pero por ahora ignoramos o
            // lanzamos genérica
            throw IdentificadorAnimalNoExisteException.create(); // O crear una excepcion
                                                                 // AnimalNoPerteneceAVentaException
        }

        BigDecimal precioAnterior = ObjectHelper.getDefault(animal.getPrecioKiloVenta(), BigDecimal.ZERO);
        BigDecimal nuevoPrecio = ObjectHelper.getDefault(animalDTO.getNuevoPrecioKiloVenta(), BigDecimal.ZERO);

        // Si no hay cambio, retornar 0
        if (precioAnterior.compareTo(nuevoPrecio) == 0) {
            return BigDecimal.ZERO;
        }

        // Actualizar precio en animal
        animal.setPrecioKiloVenta(nuevoPrecio);
        animalRepository.save(animal);

        // Calcular diferencia en el total de la venta para este animal
        BigDecimal peso = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
        BigDecimal costoAnterior = peso.multiply(precioAnterior);
        BigDecimal costoNuevo = peso.multiply(nuevoPrecio);

        return costoNuevo.subtract(costoAnterior);
    }

    private void actualizarVentaYCuentaCobrar(VentaEntity venta, BigDecimal diferenciaPrecio, UUID suscripcionId) {
        // Actualizar precio total de venta
        BigDecimal precioActual = ObjectHelper.getDefault(venta.getPrecioTotalVenta(), BigDecimal.ZERO);
        BigDecimal nuevoPrecioTotal = precioActual.add(diferenciaPrecio);
        venta.setPrecioTotalVenta(nuevoPrecioTotal.max(BigDecimal.ZERO));

        // Actualizar cuenta por cobrar
        CuentaCobrarEntity cuentaCobrar = venta.getCuentaCobrar();
        if (cuentaCobrar != null) {
            BigDecimal montoTotalActual = ObjectHelper.getDefault(cuentaCobrar.getMontoTotal(), BigDecimal.ZERO);
            BigDecimal saldoPendienteActual = ObjectHelper.getDefault(cuentaCobrar.getSaldoPendiente(),
                    BigDecimal.ZERO);

            BigDecimal nuevoMontoTotal = montoTotalActual.add(diferenciaPrecio).max(BigDecimal.ZERO);
            BigDecimal nuevoSaldo = saldoPendienteActual.add(diferenciaPrecio);
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                nuevoSaldo = BigDecimal.ZERO;
            }

            cuentaCobrar.setMontoTotal(nuevoMontoTotal);
            cuentaCobrar.setSaldoPendiente(nuevoSaldo);

            // Actualizar estado de la cuenta
            actualizarEstadoCuentaCobrar(cuentaCobrar);

            // Actualizar cartera del cliente
            if (venta.getCliente() != null) {
                UUID clienteId = venta.getCliente().getId();

                if (diferenciaPrecio.compareTo(BigDecimal.ZERO) > 0) {
                    actualizarCartera.incrementarCuentasCobrar(clienteId, suscripcionId, diferenciaPrecio);
                } else {
                    actualizarCartera.reducirCuentasCobrarPorCobro(clienteId, suscripcionId,
                            diferenciaPrecio.abs());
                }
            }

            // Anular pagos/cruces y regenerar siempre que haya cambio
            anularYRegenerarPagosYCruces(cuentaCobrar, suscripcionId);
        }
    }

    private void actualizarEstadoCuentaCobrar(CuentaCobrarEntity cuenta) {
        BigDecimal saldo = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO);
        BigDecimal montoTotal = ObjectHelper.getDefault(cuenta.getMontoTotal(), BigDecimal.ZERO);

        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            cuenta.setEstado(EstadoCuentaEnum.COBRADA);
        } else if (saldo.compareTo(montoTotal) < 0) {
            cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_COBRADA);
        } else {
            cuenta.setEstado(EstadoCuentaEnum.PENDIENTE);
        }
    }

    private void anularYRegenerarPagosYCruces(CuentaCobrarEntity cuentaCobrar, UUID suscripcionId) {
        // Anular cobros automáticos por cruce de cuentas
        if (!ObjectHelper.isNull(cuentaCobrar.getCobros())) {
            cuentaCobrar.getCobros().stream()
                    .filter(cobro -> cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                     cobro.getEstado() != EstadoCobroEnum.ANULADO)
                    .forEach(cobro -> {
                        // Restaurar saldo y cartera antes de anular
                        BigDecimal monto = ObjectHelper.getDefault(cobro.getMonto(), BigDecimal.ZERO);
                        BigDecimal nuevoSaldo = ObjectHelper.getDefault(cuentaCobrar.getSaldoPendiente(),
                                BigDecimal.ZERO).add(monto);
                        cuentaCobrar.setSaldoPendiente(nuevoSaldo);
                        actualizarEstadoCuentaCobrar(cuentaCobrar);
                        if (!ObjectHelper.isNull(cuentaCobrar.getCliente())) {
                            actualizarCartera.revertirCobro(
                                    cuentaCobrar.getCliente().getId(),
                                    suscripcionId,
                                    monto
                            );
                        }

                        cobro.setEstado(EstadoCobroEnum.ANULADO);
                        cobro.setMotivoAnulacion("Anulación automática por edición de venta");
                        cobro.setFechaAnulacion(java.time.LocalDateTime.now());
                    });
        }

        // Anular cuentas por pagar creadas por cruce
        List<CuentaPagarEntity> cuentasCreadasPorCruce = cuentaPagarRepository
                .findByProveedor_IdAndSuscripcion_Id(cuentaCobrar.getVenta().getCliente().getId(), suscripcionId)
                .stream()
                .filter(cuenta -> !ObjectHelper.isNull(cuenta.getAbonos()) &&
                        cuenta.getAbonos().stream()
                                .anyMatch(abono -> abono.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                                   abono.getEstado() != EstadoAbonoEnum.ANULADO))
                .toList();

        for (CuentaPagarEntity cuenta : cuentasCreadasPorCruce) {
            cuenta.getAbonos().stream()
                    .filter(abono -> abono.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                     abono.getEstado() != EstadoAbonoEnum.ANULADO)
                    .forEach(abono -> {
                        BigDecimal monto = ObjectHelper.getDefault(abono.getMonto(), BigDecimal.ZERO);
                        BigDecimal nuevoSaldo = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO)
                                .add(monto);
                        cuenta.setSaldoPendiente(nuevoSaldo);
                        actualizarEstadoCuentaPagar(cuenta);

                        abono.setEstado(EstadoAbonoEnum.ANULADO);
                        abono.setMotivoAnulacion("Anulación automática por edición de venta");
                        abono.setFechaAnulacion(java.time.LocalDateTime.now());

                        if (!ObjectHelper.isNull(cuenta.getProveedor())) {
                            actualizarCartera.revertirAbono(
                                    cuenta.getProveedor().getId(),
                                    suscripcionId,
                                    monto
                            );
                        }
                    });
            cuentaPagarRepository.save(cuenta);
        }

        // Regenerar pagos/cruces basados en el nuevo saldo llamando a la lógica de compensación
        BigDecimal nuevoSaldoPendiente = ObjectHelper.getDefault(cuentaCobrar.getSaldoPendiente(), BigDecimal.ZERO);
        if (nuevoSaldoPendiente.compareTo(BigDecimal.ZERO) > 0) {
            CompensarCuentas.ResultadoCompensacion resultado = compensarCuentas.compensarCuentasPagarConVenta(
                    cuentaCobrar.getVenta().getCliente(),
                    cuentaCobrar.getVenta().getSuscripcion(),
                    nuevoSaldoPendiente,
                    cuentaCobrar.getVenta().getFechaVenta(),
                    cuentaCobrar.getVenta().getNumeroVenta()
            );

            // Actualizar el saldo pendiente de la cuenta por cobrar con el saldo restante
            BigDecimal saldoRestante = resultado.saldoRestante();
            if (saldoRestante.compareTo(BigDecimal.ZERO) < 0) {
                saldoRestante = BigDecimal.ZERO;
            }
            cuentaCobrar.setSaldoPendiente(saldoRestante);
            actualizarEstadoCuentaCobrar(cuentaCobrar);
            cuentaCobrarRepository.save(cuentaCobrar);
        }
    }

    private void actualizarEstadoCuentaPagar(CuentaPagarEntity cuenta) {
        BigDecimal saldo = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO);
        BigDecimal montoTotal = ObjectHelper.getDefault(cuenta.getMontoTotal(), BigDecimal.ZERO);

        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            cuenta.setEstado(EstadoCuentaEnum.PAGADA);
            cuenta.setSaldoPendiente(BigDecimal.ZERO);
        } else if (saldo.compareTo(montoTotal) < 0) {
            cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        } else {
            cuenta.setEstado(EstadoCuentaEnum.PENDIENTE);
        }
    }
}

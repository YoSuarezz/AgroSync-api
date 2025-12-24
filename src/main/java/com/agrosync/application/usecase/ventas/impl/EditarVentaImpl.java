package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.EditarPrecioVentaAnimalDTO;
import com.agrosync.application.primaryports.dto.ventas.request.EditarVentaDTO;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.ventas.EditarVenta;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.ventas.EstadoVentaEnum;
import com.agrosync.domain.ventas.exceptions.IdentificadorVentaNoExisteException;
import com.agrosync.domain.ventas.exceptions.VentaNoEditableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class EditarVentaImpl implements EditarVenta {

    private final VentaRepository ventaRepository;
    private final AnimalRepository animalRepository;
    private final ActualizarCartera actualizarCartera;

    public EditarVentaImpl(VentaRepository ventaRepository,
            AnimalRepository animalRepository,
            ActualizarCartera actualizarCartera) {
        this.ventaRepository = ventaRepository;
        this.animalRepository = animalRepository;
        this.actualizarCartera = actualizarCartera;
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
        venta.setPrecioTotalVenta(precioActual.add(diferenciaPrecio));

        // Actualizar cuenta por cobrar
        CuentaCobrarEntity cuentaCobrar = venta.getCuentaCobrar();
        if (cuentaCobrar != null) {
            BigDecimal montoTotalActual = ObjectHelper.getDefault(cuentaCobrar.getMontoTotal(), BigDecimal.ZERO);
            BigDecimal saldoPendienteActual = ObjectHelper.getDefault(cuentaCobrar.getSaldoPendiente(),
                    BigDecimal.ZERO);

            cuentaCobrar.setMontoTotal(montoTotalActual.add(diferenciaPrecio));
            cuentaCobrar.setSaldoPendiente(saldoPendienteActual.add(diferenciaPrecio));

            // Actualizar estado de la cuenta
            actualizarEstadoCuentaCobrar(cuentaCobrar);

            // Actualizar cartera del cliente
            if (venta.getCliente() != null) {
                UUID clienteId = venta.getCliente().getId();

                if (diferenciaPrecio.compareTo(BigDecimal.ZERO) > 0) {
                    actualizarCartera.incrementarCuentasCobrar(clienteId, suscripcionId, diferenciaPrecio);
                } else {
                    actualizarCartera.reducirCuentasCobrarPorCobro(clienteId, suscripcionId, diferenciaPrecio.abs());
                }
            }
        }
    }

    private void actualizarEstadoCuentaCobrar(CuentaCobrarEntity cuenta) {
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
}

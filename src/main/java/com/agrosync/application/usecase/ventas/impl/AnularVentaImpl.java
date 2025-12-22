package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.repository.*;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.ventas.AnularVenta;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.ventas.EstadoVentaEnum;
import com.agrosync.domain.ventas.VentaDomain;
import com.agrosync.domain.ventas.exceptions.IdentificadorVentaNoExisteException;
import com.agrosync.domain.ventas.exceptions.MotivoAnulacionVentaRequeridoException;
import com.agrosync.domain.ventas.exceptions.VentaConCobrosException;
import com.agrosync.domain.ventas.exceptions.VentaYaAnuladaException;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AnularVentaImpl implements AnularVenta {

    private final VentaRepository ventaRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final CobroRepository cobroRepository;
    private final AbonoRepository abonoRepository;
    private final AnimalRepository animalRepository;
    private final ActualizarCartera actualizarCartera;

    public AnularVentaImpl(VentaRepository ventaRepository,
                          CuentaCobrarRepository cuentaCobrarRepository,
                          CuentaPagarRepository cuentaPagarRepository,
                          CobroRepository cobroRepository,
                          AbonoRepository abonoRepository,
                          AnimalRepository animalRepository,
                          ActualizarCartera actualizarCartera) {
        this.ventaRepository = ventaRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.cobroRepository = cobroRepository;
        this.abonoRepository = abonoRepository;
        this.animalRepository = animalRepository;
        this.actualizarCartera = actualizarCartera;
    }

    @Override
    public void ejecutar(VentaDomain data) {
        // 1. Buscar y validar la venta
        VentaEntity venta = buscarYValidarVenta(data);

        // 2. Obtener saldo pendiente antes de anular
        BigDecimal saldoPendiente = obtenerSaldoPendiente(venta);

        // 3. Anular la venta
        anularVenta(venta, data.getMotivoAnulacion());

        // 4. Anular la cuenta por cobrar y sus cobros automáticos
        anularCuentaCobrar(venta.getCuentaCobrar());

        // 5. Anular cuentas por pagar creadas por cruce de cuentas
        anularCuentasCreadasPorCruce(venta);

        // 6. Anular cobros automáticos relacionados
        anularCobrosRelacionados(venta);

        // 7. Anular abonos automáticos relacionados
        anularAbonosRelacionados(venta);

        // 8. Devolver animales a estado disponible
        devolverAnimales(venta);

        // 9. Revertir cartera del cliente
        revertirCarteraCliente(venta, saldoPendiente);
    }

    private VentaEntity buscarYValidarVenta(VentaDomain data) {
        // Validar que se proporcione motivo
        if (TextHelper.isEmpty(data.getMotivoAnulacion())) {
            throw MotivoAnulacionVentaRequeridoException.create();
        }

        // Buscar la venta
        VentaEntity venta = ventaRepository
                .findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorVentaNoExisteException::create);

        // Validar que no esté ya anulada
        if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
            throw VentaYaAnuladaException.create();
        }

        // Validar que no tenga cobros manuales
        validarCobrosManuales(venta);

        return venta;
    }

    private void validarCobrosManuales(VentaEntity venta) {
        CuentaCobrarEntity cuentaCobrar = venta.getCuentaCobrar();
        if (!ObjectHelper.isNull(cuentaCobrar) &&
            !ObjectHelper.isNull(cuentaCobrar.getCobros()) &&
            !cuentaCobrar.getCobros().isEmpty()) {
            boolean tieneCobrosManuales = cuentaCobrar.getCobros().stream()
                    .anyMatch(cobro -> cobro.getMetodoPago() != MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                       cobro.getEstado() != EstadoCobroEnum.ANULADO);
            if (tieneCobrosManuales) {
                throw VentaConCobrosException.create();
            }
        }
    }

    private BigDecimal obtenerSaldoPendiente(VentaEntity venta) {
        CuentaCobrarEntity cuentaCobrar = venta.getCuentaCobrar();
        return !ObjectHelper.isNull(cuentaCobrar)
                ? ObjectHelper.getDefault(cuentaCobrar.getSaldoPendiente(), BigDecimal.ZERO)
                : BigDecimal.ZERO;
    }

    private void anularVenta(VentaEntity venta, String motivoAnulacion) {
        venta.setEstado(EstadoVentaEnum.ANULADA);
        venta.setMotivoAnulacion(motivoAnulacion);
        venta.setFechaAnulacion(LocalDateTime.now());
        ventaRepository.save(venta);
    }

    private void anularCuentaCobrar(CuentaCobrarEntity cuentaCobrar) {
        if (ObjectHelper.isNull(cuentaCobrar)) {
            return;
        }

        cuentaCobrar.setEstado(EstadoCuentaEnum.ANULADA);
        cuentaCobrar.setSaldoPendiente(BigDecimal.ZERO);

        // Anular cobros automáticos por cruce de cuentas
        if (!ObjectHelper.isNull(cuentaCobrar.getCobros())) {
            cuentaCobrar.getCobros().stream()
                    .filter(cobro -> cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                     cobro.getEstado() != EstadoCobroEnum.ANULADO)
                    .forEach(cobro -> {
                        cobro.setEstado(EstadoCobroEnum.ANULADO);
                        cobro.setMotivoAnulacion("Anulación automática por anulación de venta origen");
                        cobro.setFechaAnulacion(LocalDateTime.now());
                    });
        }

        cuentaCobrarRepository.save(cuentaCobrar);
    }

    private void anularCuentasCreadasPorCruce(VentaEntity venta) {
        if (ObjectHelper.isNull(venta.getCliente())) {
            return;
        }

        List<CuentaPagarEntity> cuentasCreadasPorCruce = cuentaPagarRepository
            .findByProveedor_IdAndSuscripcion_Id(venta.getCliente().getId(), venta.getSuscripcion().getId())
            .stream()
            .filter(cuenta -> ObjectHelper.isNull(cuenta.getCompra()) &&
                              !ObjectHelper.isNull(cuenta.getAbonos()) &&
                              cuenta.getAbonos().stream()
                                  .anyMatch(abono -> abono.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                                    abono.getConcepto().contains(venta.getNumeroVenta()) &&
                                                    abono.getEstado() != EstadoAbonoEnum.ANULADO))
            .toList();

        for (CuentaPagarEntity cuenta : cuentasCreadasPorCruce) {
            cuenta.setEstado(EstadoCuentaEnum.ANULADA);
            cuenta.setSaldoPendiente(BigDecimal.ZERO);
            cuenta.getAbonos().stream()
                .filter(abono -> abono.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                abono.getEstado() != EstadoAbonoEnum.ANULADO)
                .forEach(abono -> {
                    abono.setEstado(EstadoAbonoEnum.ANULADO);
                    abono.setMotivoAnulacion("Anulación automática por anulación de venta origen");
                    abono.setFechaAnulacion(LocalDateTime.now());
                });
            cuentaPagarRepository.save(cuenta);

            // Revertir cartera
            actualizarCartera.reducirCuentasPagarPorAbono(
                cuenta.getProveedor().getId(),
                cuenta.getSuscripcion().getId(),
                cuenta.getMontoTotal()
            );
        }
    }

    private void anularCobrosRelacionados(VentaEntity venta) {
        List<CobroEntity> cobrosRelacionados = cobroRepository.findBySuscripcion_Id(venta.getSuscripcion().getId())
            .stream()
            .filter(cobro -> cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                            cobro.getConcepto().contains(venta.getNumeroVenta()) &&
                            cobro.getEstado() != EstadoCobroEnum.ANULADO)
            .toList();

        for (CobroEntity cobro : cobrosRelacionados) {
            anularCobroYRestaurarCuenta(cobro, venta.getSuscripcion().getId());
        }
    }

    private void anularAbonosRelacionados(VentaEntity venta) {
        List<AbonoEntity> abonosRelacionados = abonoRepository.findBySuscripcion_Id(venta.getSuscripcion().getId())
            .stream()
            .filter(abono -> abono.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                            abono.getConcepto().contains(venta.getNumeroVenta()) &&
                            abono.getEstado() != EstadoAbonoEnum.ANULADO)
            .toList();

        for (AbonoEntity abono : abonosRelacionados) {
            anularAbonoYRestaurarCuenta(abono, venta.getSuscripcion().getId());
        }
    }

    private void anularCobroYRestaurarCuenta(CobroEntity cobro, UUID suscripcionId) {
        cobro.setEstado(EstadoCobroEnum.ANULADO);
        cobro.setMotivoAnulacion("Anulación automática por anulación de venta origen");
        cobro.setFechaAnulacion(LocalDateTime.now());
        cobroRepository.save(cobro);

        CuentaCobrarEntity cuenta = cobro.getCuentaCobrar();
        if (!ObjectHelper.isNull(cuenta)) {
            restaurarSaldoCuentaCobrar(cuenta, cobro.getMonto());
            cuentaCobrarRepository.save(cuenta);

            if (!ObjectHelper.isNull(cuenta.getCliente())) {
                actualizarCartera.revertirCobro(
                    cuenta.getCliente().getId(),
                    suscripcionId,
                    cobro.getMonto()
                );
            }
        }
    }

    private void anularAbonoYRestaurarCuenta(AbonoEntity abono, UUID suscripcionId) {
        abono.setEstado(EstadoAbonoEnum.ANULADO);
        abono.setMotivoAnulacion("Anulación automática por anulación de venta origen");
        abono.setFechaAnulacion(LocalDateTime.now());
        abonoRepository.save(abono);

        CuentaPagarEntity cuenta = abono.getCuentaPagar();
        if (!ObjectHelper.isNull(cuenta)) {
            restaurarSaldoCuentaPagar(cuenta, abono.getMonto());
            cuentaPagarRepository.save(cuenta);

            if (!ObjectHelper.isNull(cuenta.getProveedor())) {
                actualizarCartera.revertirAbono(
                    cuenta.getProveedor().getId(),
                    suscripcionId,
                    abono.getMonto()
                );
            }
        }
    }

    private void restaurarSaldoCuentaCobrar(CuentaCobrarEntity cuenta, BigDecimal monto) {
        BigDecimal saldoActual = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO);
        BigDecimal nuevoSaldo = saldoActual.add(monto);
        cuenta.setSaldoPendiente(nuevoSaldo);

        BigDecimal montoTotal = ObjectHelper.getDefault(cuenta.getMontoTotal(), BigDecimal.ZERO);
        if (nuevoSaldo.compareTo(montoTotal) >= 0) {
            cuenta.setEstado(EstadoCuentaEnum.PENDIENTE);
        } else {
            cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_COBRADA);
        }
    }

    private void restaurarSaldoCuentaPagar(CuentaPagarEntity cuenta, BigDecimal monto) {
        BigDecimal saldoActual = ObjectHelper.getDefault(cuenta.getSaldoPendiente(), BigDecimal.ZERO);
        BigDecimal nuevoSaldo = saldoActual.add(monto);
        cuenta.setSaldoPendiente(nuevoSaldo);

        BigDecimal montoTotal = ObjectHelper.getDefault(cuenta.getMontoTotal(), BigDecimal.ZERO);
        if (nuevoSaldo.compareTo(montoTotal) >= 0) {
            cuenta.setEstado(EstadoCuentaEnum.PENDIENTE);
        } else {
            cuenta.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        }
    }

    private void devolverAnimales(VentaEntity venta) {
        List<AnimalEntity> animales = venta.getAnimales();
        if (!ObjectHelper.isNull(animales) && !animales.isEmpty()) {
            animales.forEach(animal -> {
                animal.setEstado(EstadoAnimalEnum.DISPONIBLE);
                animal.setVenta(null);
                animal.setPrecioKiloVenta(null);
            });
            animalRepository.saveAll(animales);
        }
    }

    private void revertirCarteraCliente(VentaEntity venta, BigDecimal saldoPendiente) {
        if (saldoPendiente.compareTo(BigDecimal.ZERO) > 0 && !ObjectHelper.isNull(venta.getCliente())) {
            actualizarCartera.reducirCuentasCobrarPorCobro(
                    venta.getCliente().getId(),
                    venta.getSuscripcion().getId(),
                    saldoPendiente
            );
        }
    }
}

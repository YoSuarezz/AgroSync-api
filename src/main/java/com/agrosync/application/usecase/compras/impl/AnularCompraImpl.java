package com.agrosync.application.usecase.compras.impl;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.compras.AnularCompra;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.compras.exceptions.CompraConAbonosException;
import com.agrosync.domain.compras.exceptions.CompraConAnimalesVendidosException;
import com.agrosync.domain.compras.exceptions.CompraYaAnuladaException;
import com.agrosync.domain.compras.exceptions.IdentificadorCompraNoExisteException;
import com.agrosync.domain.compras.exceptions.MotivoAnulacionRequeridoException;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.compras.EstadoCompraEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AnularCompraImpl implements AnularCompra {

    private final CompraRepository compraRepository;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final AnimalRepository animalRepository;
    private final AbonoRepository abonoRepository;
    private final CobroRepository cobroRepository;
    private final ActualizarCartera actualizarCartera;

    public AnularCompraImpl(CompraRepository compraRepository,
                            CuentaPagarRepository cuentaPagarRepository,
                            CuentaCobrarRepository cuentaCobrarRepository,
                            AnimalRepository animalRepository,
                            AbonoRepository abonoRepository,
                            CobroRepository cobroRepository,
                            ActualizarCartera actualizarCartera) {
        this.compraRepository = compraRepository;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.animalRepository = animalRepository;
        this.abonoRepository = abonoRepository;
        this.cobroRepository = cobroRepository;
        this.actualizarCartera = actualizarCartera;
    }

    @Override
    public void ejecutar(CompraDomain data) {
        // 1. Buscar y validar la compra
        CompraEntity compra = buscarYValidarCompra(data);

        // 2. Obtener saldo pendiente antes de anular
        BigDecimal saldoPendiente = obtenerSaldoPendiente(compra);

        // 3. Anular la compra
        anularCompra(compra, data.getMotivoAnulacion());

        // 4. Anular la cuenta por pagar y sus abonos automáticos
        anularCuentaPagar(compra.getCuentaPagar());

        // 5. Anular cuentas por cobrar creadas por cruce de cuentas
        anularCuentasCreadasPorCruce(compra);

        // 6. Anular cobros automáticos relacionados
        anularCobrosRelacionados(compra);

        // 7. Marcar animales como anulados
        anularAnimales(compra);

        // 8. Revertir cartera del proveedor
        revertirCarteraProveedor(compra, saldoPendiente);
    }

    private CompraEntity buscarYValidarCompra(CompraDomain data) {
        // Validar que se proporcione motivo
        if (TextHelper.isEmpty(data.getMotivoAnulacion())) {
            throw MotivoAnulacionRequeridoException.create();
        }

        // Buscar la compra
        CompraEntity compra = compraRepository
                .findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorCompraNoExisteException::create);

        // Validar que no esté ya anulada
        if (compra.getEstado() == EstadoCompraEnum.ANULADA) {
            throw CompraYaAnuladaException.create();
        }

        // Validar que no tenga abonos manuales
        validarAbonosManuales(compra);

        // Validar que ningún animal haya sido vendido
        validarAnimalesVendidos(compra);

        return compra;
    }

    private void validarAbonosManuales(CompraEntity compra) {
        CuentaPagarEntity cuentaPagar = compra.getCuentaPagar();
        if (!ObjectHelper.isNull(cuentaPagar) &&
            !ObjectHelper.isNull(cuentaPagar.getAbonos()) &&
            !cuentaPagar.getAbonos().isEmpty()) {
            boolean tieneAbonosManuales = cuentaPagar.getAbonos().stream()
                    .anyMatch(abono -> abono.getMetodoPago() != MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                       abono.getEstado() != EstadoAbonoEnum.ANULADO);
            if (tieneAbonosManuales) {
                throw CompraConAbonosException.create();
            }
        }
    }

    private void validarAnimalesVendidos(CompraEntity compra) {
        LoteEntity lote = compra.getLote();
        if (!ObjectHelper.isNull(lote) && !ObjectHelper.isNull(lote.getAnimales())) {
            boolean tieneAnimalesVendidos = lote.getAnimales().stream()
                    .anyMatch(animal -> animal.getEstado() == EstadoAnimalEnum.VENDIDO);
            if (tieneAnimalesVendidos) {
                throw CompraConAnimalesVendidosException.create();
            }
        }
    }

    private BigDecimal obtenerSaldoPendiente(CompraEntity compra) {
        CuentaPagarEntity cuentaPagar = compra.getCuentaPagar();
        return !ObjectHelper.isNull(cuentaPagar)
                ? ObjectHelper.getDefault(cuentaPagar.getSaldoPendiente(), BigDecimal.ZERO)
                : BigDecimal.ZERO;
    }

    private void anularCompra(CompraEntity compra, String motivoAnulacion) {
        compra.setEstado(EstadoCompraEnum.ANULADA);
        compra.setMotivoAnulacion(motivoAnulacion);
        compra.setFechaAnulacion(LocalDateTime.now());
        compraRepository.save(compra);
    }

    private void anularCuentaPagar(CuentaPagarEntity cuentaPagar) {
        if (ObjectHelper.isNull(cuentaPagar)) {
            return;
        }

        cuentaPagar.setEstado(EstadoCuentaEnum.ANULADA);
        cuentaPagar.setSaldoPendiente(BigDecimal.ZERO);

        // Anular abonos automáticos por cruce de cuentas
        if (!ObjectHelper.isNull(cuentaPagar.getAbonos())) {
            cuentaPagar.getAbonos().stream()
                    .filter(abono -> abono.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                     abono.getEstado() != EstadoAbonoEnum.ANULADO)
                    .forEach(abono -> {
                        // Revertir efecto en cartera antes de anular
                        if (!ObjectHelper.isNull(cuentaPagar.getProveedor())) {
                            actualizarCartera.revertirAbono(
                                    cuentaPagar.getProveedor().getId(),
                                    cuentaPagar.getSuscripcion().getId(),
                                    ObjectHelper.getDefault(abono.getMonto(), BigDecimal.ZERO)
                            );
                        }

                        abono.setEstado(EstadoAbonoEnum.ANULADO);
                        abono.setMotivoAnulacion("Anulación automática por anulación de compra origen");
                        abono.setFechaAnulacion(LocalDateTime.now());
                    });
        }

        cuentaPagarRepository.save(cuentaPagar);
    }

    private void anularCuentasCreadasPorCruce(CompraEntity compra) {
        if (ObjectHelper.isNull(compra.getProveedor())) {
            return;
        }

        List<CuentaCobrarEntity> cuentasCreadasPorCruce = cuentaCobrarRepository
            .findByCliente_IdAndSuscripcion_Id(compra.getProveedor().getId(), compra.getSuscripcion().getId())
            .stream()
            .filter(cuenta -> ObjectHelper.isNull(cuenta.getVenta()) &&
                              !ObjectHelper.isNull(cuenta.getCobros()) &&
                              cuenta.getCobros().stream()
                                  .anyMatch(cobro -> cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                                                    cobro.getConcepto().contains(compra.getNumeroCompra()) &&
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
                    cobro.setMotivoAnulacion("Anulación automática por anulación de compra origen");
                    cobro.setFechaAnulacion(LocalDateTime.now());
                });
            cuentaCobrarRepository.save(cuenta);

            // Revertir cartera: reducir cuentas por cobrar
            actualizarCartera.reducirCuentasCobrarPorCobro(
                cuenta.getCliente().getId(),
                cuenta.getSuscripcion().getId(),
                cuenta.getMontoTotal()
            );
        }
    }

    private void anularCobrosRelacionados(CompraEntity compra) {
        List<CobroEntity> cobrosRelacionados = cobroRepository.findBySuscripcion_Id(compra.getSuscripcion().getId())
            .stream()
            .filter(cobro -> cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS &&
                            cobro.getConcepto().contains(compra.getNumeroCompra()) &&
                            cobro.getEstado() != EstadoCobroEnum.ANULADO)
            .toList();

        for (CobroEntity cobro : cobrosRelacionados) {
            anularCobroYRestaurarCuenta(cobro, compra.getSuscripcion().getId());
        }
    }

    private void anularCobroYRestaurarCuenta(CobroEntity cobro, UUID suscripcionId) {
        cobro.setEstado(EstadoCobroEnum.ANULADO);
        cobro.setMotivoAnulacion("Anulación automática por anulación de compra origen");
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

    private void anularAnimales(CompraEntity compra) {
        LoteEntity lote = compra.getLote();
        if (!ObjectHelper.isNull(lote) && !ObjectHelper.isNull(lote.getAnimales())) {
            List<AnimalEntity> animales = lote.getAnimales();
            animales.forEach(animal -> animal.setEstado(EstadoAnimalEnum.ANULADO));
            animalRepository.saveAll(animales);
        }
    }

    private void revertirCarteraProveedor(CompraEntity compra, BigDecimal saldoPendiente) {
        if (saldoPendiente.compareTo(BigDecimal.ZERO) > 0 && !ObjectHelper.isNull(compra.getProveedor())) {
            actualizarCartera.reducirCuentasPagarPorAbono(
                    compra.getProveedor().getId(),
                    compra.getSuscripcion().getId(),
                    saldoPendiente
            );
        }
    }
}

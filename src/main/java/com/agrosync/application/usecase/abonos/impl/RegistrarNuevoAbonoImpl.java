package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.usecase.abonos.RegistrarNuevoAbono;
import com.agrosync.application.usecase.abonos.rulesvalidator.RegistrarNuevoAbonoRulesValidator;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.cuentaspagar.exceptions.IdentificadorCuentaPagarNoExisteException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RegistrarNuevoAbonoImpl implements RegistrarNuevoAbono {

    private final CuentaPagarRepository cuentaPagarRepository;
    private final AbonoRepository abonoRepository;
    private final CarteraRepository carteraRepository;
    private final RegistrarNuevoAbonoRulesValidator rulesValidator;
    private final CuentaPagarEntityMapper cuentaPagarMapper;

    public RegistrarNuevoAbonoImpl(
            CuentaPagarRepository cuentaPagarRepository,
            AbonoRepository abonoRepository,
            CarteraRepository carteraRepository,
            RegistrarNuevoAbonoRulesValidator rulesValidator,
            CuentaPagarEntityMapper cuentaPagarMapper
    ) {
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.abonoRepository = abonoRepository;
        this.carteraRepository = carteraRepository;
        this.rulesValidator = rulesValidator;
        this.cuentaPagarMapper = cuentaPagarMapper;
    }

    @Override
    public void ejecutar(AbonoDomain abonoDomain) {

        // 1. Buscar cuenta por pagar
        var cuentaEntity = cuentaPagarRepository
                .findByIdAndSuscripcion_Id(
                        abonoDomain.getCuentaPagar().getId(),
                        abonoDomain.getSuscripcionId()
                )
                .orElseThrow(IdentificadorCuentaPagarNoExisteException::create);

        // 2. Convertir entity a dominio para validaciones
        CuentaPagarDomain cuentaPagar = cuentaPagarMapper.toDomain(cuentaEntity);
        abonoDomain.setCuentaPagar(cuentaPagar);

        // 3. Validar reglas de negocio
        rulesValidator.validar(abonoDomain);

        // 4. Crear y guardar el abono
        AbonoEntity abonoEntity = new AbonoEntity();
        abonoEntity.setCuentaPagar(cuentaEntity);
        abonoEntity.setMonto(abonoDomain.getMonto());
        abonoEntity.setFechaPago(abonoDomain.getFechaPago() != null ? abonoDomain.getFechaPago() : LocalDateTime.now());
        abonoEntity.setMetodoPago(abonoDomain.getMetodoPago());
        abonoEntity.setConcepto(abonoDomain.getConcepto());
        abonoEntity.setSuscripcion(SuscripcionEntity.create(abonoDomain.getSuscripcionId()));

        abonoRepository.save(abonoEntity);

        // 5. Actualizar saldo pendiente
        BigDecimal nuevoSaldo = cuentaEntity.getSaldoPendiente()
                .subtract(abonoDomain.getMonto());

        cuentaEntity.setSaldoPendiente(nuevoSaldo);

        // 6. Actualizar estado de la cuenta por pagar
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
            cuentaEntity.setEstado(EstadoCuentaEnum.PAGADA);
        } else {
            cuentaEntity.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        }

        cuentaPagarRepository.save(cuentaEntity);

        // 7. Actualizar cartera del proveedor
        actualizarCarteraProveedor(cuentaEntity, abonoDomain.getMonto());
    }

    private void actualizarCarteraProveedor(CuentaPagarEntity cuentaPagar, BigDecimal montoAbono) {
        CarteraEntity cartera = cuentaPagar.getProveedor().getCartera();

        if (cartera != null) {
            // Reducir cuentas por cobrar del proveedor (le pagamos, ya no le debemos tanto)
            BigDecimal nuevoTotalCuentasCobrar =
                    cartera.getTotalCuentasCobrar().subtract(montoAbono);
            cartera.setTotalCuentasCobrar(nuevoTotalCuentasCobrar);

            // Reducir saldo del proveedor (le debemos menos)
            BigDecimal saldoActual = cartera.getSaldoActual().subtract(montoAbono);
            cartera.setSaldoActual(saldoActual);

            carteraRepository.save(cartera);
        }
    }
}

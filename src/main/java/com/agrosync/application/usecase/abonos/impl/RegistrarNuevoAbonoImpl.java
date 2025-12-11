package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.RegistrarAbonoDTO;
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
    private final RegistrarNuevoAbonoRulesValidator registrarNuevoAbonoRulesValidator;
    private final CuentaPagarEntityMapper cuentaPagarEntityMapper;

    public RegistrarNuevoAbonoImpl(
            CuentaPagarRepository cuentaPagarRepository,
            AbonoRepository abonoRepository,
            CarteraRepository carteraRepository,
            RegistrarNuevoAbonoRulesValidator registrarNuevoAbonoRulesValidator,
            CuentaPagarEntityMapper cuentaPagarEntityMapper) {
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.abonoRepository = abonoRepository;
        this.carteraRepository = carteraRepository;
        this.registrarNuevoAbonoRulesValidator = registrarNuevoAbonoRulesValidator;
        this.cuentaPagarEntityMapper = cuentaPagarEntityMapper;
    }

    @Override
    public void ejecutar(RegistrarAbonoDTO data) {
        // 1. Buscar la cuenta por pagar
        CuentaPagarEntity cuentaPagarEntity = cuentaPagarRepository
                .findByIdAndSuscripcion_Id(data.getIdCuentaPagar(), data.getSuscripcionId())
                .orElseThrow(IdentificadorCuentaPagarNoExisteException::create);

        // 2. Convertir a domain para validaciones
        CuentaPagarDomain cuentaPagarDomain = cuentaPagarEntityMapper.toDomain(cuentaPagarEntity);

        AbonoDomain abonoDomain = new AbonoDomain();
        abonoDomain.setMonto(data.getMonto());
        abonoDomain.setMetodoPago(data.getMetodoPago());
        abonoDomain.setCuentaPagar(cuentaPagarDomain);

        // 3. Validar reglas de negocio
        registrarNuevoAbonoRulesValidator.validar(abonoDomain);

        // 4. Crear y guardar el abono
        AbonoEntity abonoEntity = new AbonoEntity();
        abonoEntity.setCuentaPagar(cuentaPagarEntity);
        abonoEntity.setMonto(data.getMonto());
        abonoEntity.setFechaPago(LocalDateTime.now());
        abonoEntity.setMetodoPago(data.getMetodoPago());
        abonoEntity.setSuscripcion(SuscripcionEntity.create(data.getSuscripcionId()));

        abonoRepository.save(abonoEntity);

        // 5. Actualizar saldo pendiente de la cuenta
        BigDecimal nuevoSaldo = cuentaPagarEntity.getSaldoPendiente().subtract(data.getMonto());
        cuentaPagarEntity.setSaldoPendiente(nuevoSaldo);

        // 6. Actualizar estado de la cuenta
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
            cuentaPagarEntity.setEstado(EstadoCuentaEnum.PAGADA);
        } else {
            cuentaPagarEntity.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        }

        cuentaPagarRepository.save(cuentaPagarEntity);

        // 7. Actualizar cartera del proveedor
        actualizarCarteraProveedor(cuentaPagarEntity, data.getMonto());
    }

    private void actualizarCarteraProveedor(CuentaPagarEntity cuentaPagar, BigDecimal montoAbono) {
        CarteraEntity cartera = cuentaPagar.getProveedor().getCartera();

        if (cartera != null) {
            // Reducir el total de cuentas por pagar
            BigDecimal nuevoTotalCuentasPagar = cartera.getTotalCuentasPagar().subtract(montoAbono);
            cartera.setTotalCuentasPagar(nuevoTotalCuentasPagar);

            // Recalcular saldo actual (totalCuentasCobrar - totalCuentasPagar)
            BigDecimal saldoActual = cartera.getTotalCuentasCobrar().subtract(nuevoTotalCuentasPagar);
            cartera.setSaldoActual(saldoActual);

            carteraRepository.save(cartera);
        }
    }
}

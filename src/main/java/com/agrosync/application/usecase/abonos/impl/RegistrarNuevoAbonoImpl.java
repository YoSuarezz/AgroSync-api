package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.mapper.abonos.AbonoEntityMapper;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.usecase.abonos.RegistrarNuevoAbono;
import com.agrosync.application.usecase.abonos.rulesvalidator.RegistrarNuevoAbonoRulesValidator;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.cuentaspagar.exceptions.IdentificadorCuentaPagarNoExisteException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrarNuevoAbonoImpl implements RegistrarNuevoAbono {

    private final CuentaPagarRepository cuentaPagarRepository;
    private final AbonoRepository abonoRepository;
    private final CarteraRepository carteraRepository;
    private final RegistrarNuevoAbonoRulesValidator rulesValidator;
    private final CuentaPagarEntityMapper cuentaPagarMapper;
    private final AbonoEntityMapper abonoEntityMapper;

    public RegistrarNuevoAbonoImpl(
            CuentaPagarRepository cuentaPagarRepository,
            AbonoRepository abonoRepository,
            CarteraRepository carteraRepository,
            RegistrarNuevoAbonoRulesValidator rulesValidator,
            CuentaPagarEntityMapper cuentaPagarMapper,
            AbonoEntityMapper abonoEntityMapper
    ) {
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.abonoRepository = abonoRepository;
        this.carteraRepository = carteraRepository;
        this.rulesValidator = rulesValidator;
        this.cuentaPagarMapper = cuentaPagarMapper;
        this.abonoEntityMapper = abonoEntityMapper;
    }

    @Override
    public void ejecutar(AbonoDomain abonoDomain) {

        // 1. Buscar cuenta por pagar
        CuentaPagarEntity cuentaEntity = cuentaPagarRepository
                .findByIdAndSuscripcion_Id(
                        abonoDomain.getCuentaPagar().getId(),
                        abonoDomain.getSuscripcionId()
                )
                .orElseThrow(IdentificadorCuentaPagarNoExisteException::create);

        // 2. Convertir entity a dominio para validaciones
        CuentaPagarDomain cuentaPagar = cuentaPagarMapper.toDomain(cuentaEntity);
        abonoDomain.setCuentaPagar(cuentaPagar);

        // 3. Establecer fecha por defecto si es null
        if (ObjectHelper.isNull(abonoDomain.getFechaPago())) {
            abonoDomain.setFechaPago(LocalDateTime.now());
        }

        // 4. Validar reglas de negocio
        rulesValidator.validar(abonoDomain);

        // 5. Crear y guardar el abono usando el mapper
        AbonoEntity abonoEntity = abonoEntityMapper.toEntity(abonoDomain);
        abonoEntity.setCuentaPagar(cuentaEntity);

        abonoRepository.save(abonoEntity);

        // 6. Actualizar saldo pendiente
        BigDecimal nuevoSaldo = cuentaEntity.getSaldoPendiente()
                .subtract(abonoDomain.getMonto());

        cuentaEntity.setSaldoPendiente(nuevoSaldo);

        // 7. Actualizar estado de la cuenta por pagar
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
            cuentaEntity.setEstado(EstadoCuentaEnum.PAGADA);
        } else {
            cuentaEntity.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
        }

        cuentaPagarRepository.save(cuentaEntity);

        // 8. Actualizar cartera del proveedor
        actualizarCarteraProveedor(cuentaEntity, abonoDomain.getMonto());
    }

    private void actualizarCarteraProveedor(CuentaPagarEntity cuentaPagar, BigDecimal montoAbono) {
        CarteraEntity cartera = cuentaPagar.getProveedor().getCartera();

        if (cartera != null) {
            // Reducir cuentas por pagar del proveedor (le pagamos, ya no le debemos tanto)
            BigDecimal totalCuentasPagar = ObjectHelper.getDefault(cartera.getTotalCuentasPagar(), BigDecimal.ZERO);
            BigDecimal nuevoTotalCuentasPagar = totalCuentasPagar.subtract(montoAbono);
            cartera.setTotalCuentasPagar(nuevoTotalCuentasPagar.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : nuevoTotalCuentasPagar);

            // Reducir saldo del proveedor (le debemos menos, su saldo a favor baja hacia 0)
            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.subtract(montoAbono));

            carteraRepository.save(cartera);
        }
    }
}

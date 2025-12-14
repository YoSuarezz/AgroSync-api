package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.usecase.cobros.RegistrarNuevoCobro;
import com.agrosync.application.usecase.cobros.rulesvalidator.RegistrarNuevoCobroRulesValidator;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.cuentascobrar.exceptions.IdentificadorCuentaCobrarNoExisteException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RegistrarNuevoCobroImpl implements RegistrarNuevoCobro {

    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final CobroRepository cobroRepository;
    private final CarteraRepository carteraRepository;
    private final RegistrarNuevoCobroRulesValidator rulesValidator;
    private final CuentaCobrarEntityMapper cuentaCobrarMapper;

    public RegistrarNuevoCobroImpl(
            CuentaCobrarRepository cuentaCobrarRepository,
            CobroRepository cobroRepository,
            CarteraRepository carteraRepository,
            RegistrarNuevoCobroRulesValidator rulesValidator,
            CuentaCobrarEntityMapper cuentaCobrarMapper
    ) {
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.cobroRepository = cobroRepository;
        this.carteraRepository = carteraRepository;
        this.rulesValidator = rulesValidator;
        this.cuentaCobrarMapper = cuentaCobrarMapper;
    }

    @Override
    public void ejecutar(CobroDomain cobroDomain) {

        // 1. Buscar cuenta por cobrar
        var cuentaEntity = cuentaCobrarRepository
                .findByIdAndSuscripcion_Id(
                        cobroDomain.getCuentaCobrar().getId(),
                        cobroDomain.getSuscripcionId()
                )
                .orElseThrow(IdentificadorCuentaCobrarNoExisteException::create);

        // 2. Convertir entity a dominio para validaciones
        CuentaCobrarDomain cuentaCobrar = cuentaCobrarMapper.toDomain(cuentaEntity);
        cobroDomain.setCuentaCobrar(cuentaCobrar);

        // 3. Validar reglas de negocio
        rulesValidator.validar(cobroDomain);

        // 4. Crear y guardar el cobro
        CobroEntity cobroEntity = new CobroEntity();
        cobroEntity.setCuentaCobrar(cuentaEntity);
        cobroEntity.setMonto(cobroDomain.getMonto());
        cobroEntity.setFechaCobro(cobroDomain.getFechaCobro() != null ? cobroDomain.getFechaCobro() : LocalDateTime.now());
        cobroEntity.setMetodoPago(cobroDomain.getMetodoPago());
        cobroEntity.setConcepto(cobroDomain.getConcepto());
        cobroEntity.setSuscripcion(SuscripcionEntity.create(cobroDomain.getSuscripcionId()));

        cobroRepository.save(cobroEntity);

        // 5. Actualizar saldo pendiente
        BigDecimal nuevoSaldo = cuentaEntity.getSaldoPendiente()
                .subtract(cobroDomain.getMonto());

        cuentaEntity.setSaldoPendiente(nuevoSaldo);

        // 6. Actualizar estado de la cuenta por cobrar
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
            cuentaEntity.setEstado(EstadoCuentaEnum.COBRADA);
        } else {
            cuentaEntity.setEstado(EstadoCuentaEnum.PARCIALMENTE_COBRADA);
        }

        cuentaCobrarRepository.save(cuentaEntity);

        // 7. Actualizar cartera del cliente
        actualizarCarteraCliente(cuentaEntity, cobroDomain.getMonto());
    }

    private void actualizarCarteraCliente(CuentaCobrarEntity cuentaCobrar, BigDecimal montoCobro) {
        CarteraEntity cartera = cuentaCobrar.getCliente().getCartera();

        if (cartera != null) {
            // Reducir cuentas por pagar del cliente (nos pagó, ya no nos debe tanto)
            BigDecimal nuevoTotalCuentasPagar =
                    cartera.getTotalCuentasPagar().subtract(montoCobro);
            cartera.setTotalCuentasPagar(nuevoTotalCuentasPagar);

            // Aumentar saldo del cliente (nos debe menos, su saldo sube hacia 0)
            BigDecimal saldoActual = cartera.getSaldoActual().add(montoCobro);
            cartera.setSaldoActual(saldoActual);

            carteraRepository.save(cartera);
        }
    }
}

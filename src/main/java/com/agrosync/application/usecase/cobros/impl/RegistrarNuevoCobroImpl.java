package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.RegistrarCobroDTO;
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
    private final RegistrarNuevoCobroRulesValidator registrarNuevoCobroRulesValidator;
    private final CuentaCobrarEntityMapper cuentaCobrarEntityMapper;

    public RegistrarNuevoCobroImpl(
            CuentaCobrarRepository cuentaCobrarRepository,
            CobroRepository cobroRepository,
            CarteraRepository carteraRepository,
            RegistrarNuevoCobroRulesValidator registrarNuevoCobroRulesValidator,
            CuentaCobrarEntityMapper cuentaCobrarEntityMapper) {
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.cobroRepository = cobroRepository;
        this.carteraRepository = carteraRepository;
        this.registrarNuevoCobroRulesValidator = registrarNuevoCobroRulesValidator;
        this.cuentaCobrarEntityMapper = cuentaCobrarEntityMapper;
    }

    @Override
    public void ejecutar(RegistrarCobroDTO data) {
        // 1. Buscar la cuenta por cobrar
        CuentaCobrarEntity cuentaCobrarEntity = cuentaCobrarRepository
                .findByIdAndSuscripcion_Id(data.getIdCuentaCobrar(), data.getSuscripcionId())
                .orElseThrow(IdentificadorCuentaCobrarNoExisteException::create);

        // 2. Convertir a domain para validaciones
        CuentaCobrarDomain cuentaCobrarDomain = cuentaCobrarEntityMapper.toDomain(cuentaCobrarEntity);

        CobroDomain cobroDomain = new CobroDomain();
        cobroDomain.setMonto(data.getMonto());
        cobroDomain.setMetodoPago(data.getMetodoPago());
        cobroDomain.setConcepto(data.getConcepto());
        cobroDomain.setCuentaCobrar(cuentaCobrarDomain);

        // 3. Validar reglas de negocio
        registrarNuevoCobroRulesValidator.validar(cobroDomain);

        // 4. Crear y guardar el cobro
        CobroEntity cobroEntity = new CobroEntity();
        cobroEntity.setCuentaCobrar(cuentaCobrarEntity);
        cobroEntity.setMonto(data.getMonto());
        cobroEntity.setFechaCobro(LocalDateTime.now());
        cobroEntity.setMetodoPago(data.getMetodoPago());
        cobroEntity.setConcepto(data.getConcepto());
        cobroEntity.setSuscripcion(SuscripcionEntity.create(data.getSuscripcionId()));

        cobroRepository.save(cobroEntity);

        // 5. Actualizar saldo pendiente de la cuenta
        BigDecimal nuevoSaldo = cuentaCobrarEntity.getSaldoPendiente().subtract(data.getMonto());
        cuentaCobrarEntity.setSaldoPendiente(nuevoSaldo);

        // 6. Actualizar estado de la cuenta
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
            cuentaCobrarEntity.setEstado(EstadoCuentaEnum.COBRADA);
        } else {
            cuentaCobrarEntity.setEstado(EstadoCuentaEnum.PARCIALMENTE_COBRADA);
        }

        cuentaCobrarRepository.save(cuentaCobrarEntity);

        // 7. Actualizar cartera del cliente
        actualizarCarteraCliente(cuentaCobrarEntity, data.getMonto());
    }

    private void actualizarCarteraCliente(CuentaCobrarEntity cuentaCobrar, BigDecimal montoCobro) {
        CarteraEntity cartera = cuentaCobrar.getCliente().getCartera();

        if (cartera != null) {
            // Reducir el total de cuentas por cobrar
            BigDecimal nuevoTotalCuentasCobrar = cartera.getTotalCuentasCobrar().subtract(montoCobro);
            cartera.setTotalCuentasCobrar(nuevoTotalCuentasCobrar);

            // Recalcular saldo actual (totalCuentasCobrar - totalCuentasPagar)
            BigDecimal saldoActual = nuevoTotalCuentasCobrar.subtract(cartera.getTotalCuentasPagar());
            cartera.setSaldoActual(saldoActual);

            carteraRepository.save(cartera);
        }
    }
}

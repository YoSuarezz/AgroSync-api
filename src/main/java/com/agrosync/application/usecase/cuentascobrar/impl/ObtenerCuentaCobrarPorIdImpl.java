package com.agrosync.application.usecase.cuentascobrar.impl;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.usecase.cuentascobrar.ObtenerCuentaCobrarPorId;
import com.agrosync.application.usecase.cuentascobrar.rulesvalidator.ObtenerCuentaCobrarPorIdRulesValidator;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.cuentascobrar.exceptions.IdentificadorCuentaCobrarNoExisteException;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCuentaCobrarPorIdImpl implements ObtenerCuentaCobrarPorId {

    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final ObtenerCuentaCobrarPorIdRulesValidator obtenerCuentaCobrarPorIdRulesValidator;
    private final CuentaCobrarEntityMapper cuentaCobrarEntityMapper;

    public ObtenerCuentaCobrarPorIdImpl(CuentaCobrarRepository cuentaCobrarRepository,
                                        ObtenerCuentaCobrarPorIdRulesValidator obtenerCuentaCobrarPorIdRulesValidator,
                                        CuentaCobrarEntityMapper cuentaCobrarEntityMapper) {
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.obtenerCuentaCobrarPorIdRulesValidator = obtenerCuentaCobrarPorIdRulesValidator;
        this.cuentaCobrarEntityMapper = cuentaCobrarEntityMapper;
    }

    @Override
    public CuentaCobrarDomain ejecutar(CuentaCobrarIdSuscripcionDTO data) {
        obtenerCuentaCobrarPorIdRulesValidator.validar(data);
        CuentaCobrarEntity resultado = cuentaCobrarRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorCuentaCobrarNoExisteException::create);
        return cuentaCobrarEntityMapper.toDomain(resultado);
    }
}

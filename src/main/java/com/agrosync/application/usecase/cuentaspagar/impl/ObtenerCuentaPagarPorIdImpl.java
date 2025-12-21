package com.agrosync.application.usecase.cuentaspagar.impl;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.usecase.cuentaspagar.ObtenerCuentaPagarPorId;
import com.agrosync.application.usecase.cuentaspagar.rulesvalidator.ObtenerCuentaPagarPorIdRulesValidator;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.cuentaspagar.exceptions.IdentificadorCuentaPagarNoExisteException;
import org.springframework.stereotype.Service;

@Service
public class ObtenerCuentaPagarPorIdImpl implements ObtenerCuentaPagarPorId {

    private final CuentaPagarRepository cuentaPagarRepository;
    private final ObtenerCuentaPagarPorIdRulesValidator obtenerCuentaPagarPorIdRulesValidator;
    private final CuentaPagarEntityMapper cuentaPagarEntityMapper;

    public ObtenerCuentaPagarPorIdImpl(CuentaPagarRepository cuentaPagarRepository,
                                       ObtenerCuentaPagarPorIdRulesValidator obtenerCuentaPagarPorIdRulesValidator,
                                       CuentaPagarEntityMapper cuentaPagarEntityMapper) {
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.obtenerCuentaPagarPorIdRulesValidator = obtenerCuentaPagarPorIdRulesValidator;
        this.cuentaPagarEntityMapper = cuentaPagarEntityMapper;
    }

    @Override
    public CuentaPagarDomain ejecutar(CuentaPagarIdSuscripcionDTO data) {
        obtenerCuentaPagarPorIdRulesValidator.validar(data);
        CuentaPagarEntity resultado = cuentaPagarRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorCuentaPagarNoExisteException::create);
        return cuentaPagarEntityMapper.toDomain(resultado);
    }
}

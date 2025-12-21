package com.agrosync.application.usecase.compras.impl;

import com.agrosync.application.primaryports.dto.compras.request.CompraIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.usecase.compras.ObtenerCompraPorId;
import com.agrosync.application.usecase.compras.rulesvalidator.ObtenerCompraPorIdRulesValidator;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.compras.exceptions.IdentificadorCompraNoExisteException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerCompraPorIdImpl implements ObtenerCompraPorId {

    private final CompraRepository compraRepository;
    private final ObtenerCompraPorIdRulesValidator obtenerCompraPorIdRulesValidator;
    private final CompraEntityMapper compraEntityMapper;

    public ObtenerCompraPorIdImpl(CompraRepository compraRepository, ObtenerCompraPorIdRulesValidator obtenerCompraPorIdRulesValidator, CompraEntityMapper compraEntityMapper) {
        this.compraRepository = compraRepository;
        this.obtenerCompraPorIdRulesValidator = obtenerCompraPorIdRulesValidator;
        this.compraEntityMapper = compraEntityMapper;
    }

    @Override
    public CompraDomain ejecutar(CompraIdSuscripcionDTO data) {
        obtenerCompraPorIdRulesValidator.validar(data);
        Optional<CompraEntity> resultado = compraRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        CompraEntity compraEntity = resultado.orElseThrow(IdentificadorCompraNoExisteException::create);
        return compraEntityMapper.toDomain(compraEntity);
    }
}

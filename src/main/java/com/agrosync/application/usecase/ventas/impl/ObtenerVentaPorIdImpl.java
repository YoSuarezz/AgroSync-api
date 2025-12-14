package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.VentaIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.mapper.ventas.VentaEntityMapper;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.ventas.ObtenerVentaPorId;
import com.agrosync.application.usecase.ventas.rulesvalidator.ObtenerVentaPorIdRulesValidator;
import com.agrosync.domain.ventas.VentaDomain;
import com.agrosync.domain.ventas.exceptions.IdentificadorVentaNoExisteException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObtenerVentaPorIdImpl implements ObtenerVentaPorId {

    private final VentaRepository ventaRepository;
    private final ObtenerVentaPorIdRulesValidator obtenerVentaPorIdRulesValidator;
    private final VentaEntityMapper ventaEntityMapper;

    public ObtenerVentaPorIdImpl(VentaRepository ventaRepository, ObtenerVentaPorIdRulesValidator obtenerVentaPorIdRulesValidator, VentaEntityMapper ventaEntityMapper) {
        this.ventaRepository = ventaRepository;
        this.obtenerVentaPorIdRulesValidator = obtenerVentaPorIdRulesValidator;
        this.ventaEntityMapper = ventaEntityMapper;
    }

    @Override
    public VentaDomain ejecutar(VentaIdSuscripcionDTO data) {
        obtenerVentaPorIdRulesValidator.validar(data);
        Optional<VentaEntity> resultado = ventaRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId());
        VentaEntity ventaEntity = resultado.orElseThrow(IdentificadorVentaNoExisteException::create);
        return ventaEntityMapper.toDomain(ventaEntity);
    }
}

package com.agrosync.application.usecase.suscripcion.impl;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.suscripcion.SuscripcionEntityMapper;
import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.application.usecase.suscripcion.RegistrarSuscripcion;
import com.agrosync.application.usecase.suscripcion.rulesvalidator.RegistrarSuscripcionRulesValidator;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class RegistrarSuscripcionImpl implements RegistrarSuscripcion {

    private final SuscripcionRepository suscripcionRepository;
    private final RegistrarSuscripcionRulesValidator registrarSuscripcionRulesValidator;
    private final SuscripcionEntityMapper suscripcionEntityMapper;

    public RegistrarSuscripcionImpl(SuscripcionRepository suscripcionRepository,
                                    RegistrarSuscripcionRulesValidator registrarSuscripcionRulesValidator,
                                    SuscripcionEntityMapper suscripcionEntityMapper) {
        this.suscripcionRepository = suscripcionRepository;
        this.registrarSuscripcionRulesValidator = registrarSuscripcionRulesValidator;
        this.suscripcionEntityMapper = suscripcionEntityMapper;
    }

    @Override
    public void ejecutar(SuscripcionDomain data) {
        registrarSuscripcionRulesValidator.validar(data);

        LocalDateTime fechaInicio = ObjectHelper.getDefault(data.getFechaInicio(), LocalDateTime.now());
        data.setFechaInicio(fechaInicio);
        data.setFechaUltimoPago(ObjectHelper.getDefault(data.getFechaUltimoPago(), fechaInicio));

        LocalDateTime fechaProximoCobro = calcularFechaProximoCobro(
                data.getPlanSuscripcion(),
                ObjectHelper.getDefault(data.getFechaUltimoPago(), fechaInicio)
        );
        data.setFechaProximoCobro(fechaProximoCobro);

        SuscripcionEntity entity = suscripcionEntityMapper.toEntity(data);
        suscripcionRepository.save(entity);
    }

    private LocalDateTime calcularFechaProximoCobro(PlanSuscripcionEnum plan, LocalDateTime fechaUltimoPago) {
        PlanSuscripcionEnum planFinal = ObjectHelper.getDefault(plan, PlanSuscripcionEnum.MENSUAL);
        LocalDateTime fechaBase = ObjectHelper.getDefault(fechaUltimoPago, LocalDateTime.now());

        if (planFinal == PlanSuscripcionEnum.ANUAL) {
            return fechaBase.plus(1, ChronoUnit.YEARS);
        }
        return fechaBase.plus(30, ChronoUnit.DAYS);
    }
}

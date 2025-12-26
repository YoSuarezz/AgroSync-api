package com.agrosync.application.usecase.suscripcion.impl;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.application.usecase.suscripcion.ActualizarPlanSuscripcion;
import com.agrosync.application.usecase.suscripcion.rulesvalidator.ActualizarPlanSuscripcionRulesValidator;
import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.domain.suscripcion.exceptions.SuscripcionNoExisteException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class ActualizarPlanSuscripcionImpl implements ActualizarPlanSuscripcion {

    private final SuscripcionRepository suscripcionRepository;
    private final ActualizarPlanSuscripcionRulesValidator actualizarPlanSuscripcionRulesValidator;

    public ActualizarPlanSuscripcionImpl(SuscripcionRepository suscripcionRepository,
                                         ActualizarPlanSuscripcionRulesValidator actualizarPlanSuscripcionRulesValidator) {
        this.suscripcionRepository = suscripcionRepository;
        this.actualizarPlanSuscripcionRulesValidator = actualizarPlanSuscripcionRulesValidator;
    }

    @Override
    public void ejecutar(SuscripcionDomain data) {
        actualizarPlanSuscripcionRulesValidator.validar(data);

        SuscripcionEntity suscripcion = suscripcionRepository.findById(data.getId())
                .orElseThrow(SuscripcionNoExisteException::create);

        PlanSuscripcionEnum nuevoPlan = data.getPlanSuscripcion();
        suscripcion.setPlanSuscripcion(nuevoPlan);

        // recalcular próximo cobro si hay fecha último pago
        if (suscripcion.getFechaUltimoPago() != null) {
            LocalDateTime fechaUltimoPago = suscripcion.getFechaUltimoPago();
            suscripcion.setFechaProximoCobro(calcularFechaProximoCobro(nuevoPlan, fechaUltimoPago));
        }

        suscripcionRepository.save(suscripcion);
    }

    private LocalDateTime calcularFechaProximoCobro(PlanSuscripcionEnum plan, LocalDateTime fechaUltimoPago) {
        PlanSuscripcionEnum planFinal = plan != null ? plan : PlanSuscripcionEnum.MENSUAL;
        if (planFinal == PlanSuscripcionEnum.ANUAL) {
            return fechaUltimoPago.plus(1, ChronoUnit.YEARS);
        }
        return fechaUltimoPago.plus(30, ChronoUnit.DAYS);
    }
}

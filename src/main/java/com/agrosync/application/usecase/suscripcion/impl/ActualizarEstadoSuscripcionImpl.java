package com.agrosync.application.usecase.suscripcion.impl;

import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.application.usecase.suscripcion.ActualizarEstadoSuscripcion;
import com.agrosync.application.usecase.suscripcion.rulesvalidator.ActualizarEstadoSuscripcionRulesValidator;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import com.agrosync.domain.suscripcion.exceptions.SuscripcionNoExisteException;
import com.agrosync.domain.suscripcion.exceptions.FechaUltimoPagoRequeridaException;
import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;
import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class ActualizarEstadoSuscripcionImpl implements ActualizarEstadoSuscripcion {

    private final SuscripcionRepository suscripcionRepository;
    private final ActualizarEstadoSuscripcionRulesValidator actualizarEstadoSuscripcionRulesValidator;

    public ActualizarEstadoSuscripcionImpl(SuscripcionRepository suscripcionRepository,
                                           ActualizarEstadoSuscripcionRulesValidator actualizarEstadoSuscripcionRulesValidator) {
        this.suscripcionRepository = suscripcionRepository;
        this.actualizarEstadoSuscripcionRulesValidator = actualizarEstadoSuscripcionRulesValidator;
    }

    @Override
    public void ejecutar(SuscripcionDomain data) {
        actualizarEstadoSuscripcionRulesValidator.validar(data);

        SuscripcionEntity suscripcion = suscripcionRepository.findById(data.getId())
                .orElseThrow(SuscripcionNoExisteException::create);

        EstadoSuscripcionEnum nuevoEstado = data.getEstadoSuscripcion();
        suscripcion.setEstadoSuscripcion(nuevoEstado);

        if (nuevoEstado == EstadoSuscripcionEnum.ACTIVA) {
            LocalDateTime fechaUltimoPago = data.getFechaUltimoPago();
            if (fechaUltimoPago == null) {
                throw FechaUltimoPagoRequeridaException.create();
            }
            suscripcion.setFechaUltimoPago(fechaUltimoPago);
            suscripcion.setFechaProximoCobro(calcularFechaProximoCobro(
                    suscripcion.getPlanSuscripcion(), fechaUltimoPago));
        }

        suscripcionRepository.save(suscripcion);
    }

    private LocalDateTime calcularFechaProximoCobro(PlanSuscripcionEnum plan, LocalDateTime fechaUltimoPago) {
        PlanSuscripcionEnum planFinal = plan != null ? plan : PlanSuscripcionEnum.MENSUAL;
        LocalDateTime fechaBase = fechaUltimoPago != null ? fechaUltimoPago : LocalDateTime.now();

        if (planFinal == PlanSuscripcionEnum.ANUAL) {
            return fechaBase.plus(1, ChronoUnit.YEARS);
        }
        return fechaBase.plus(30, ChronoUnit.DAYS);
    }
}

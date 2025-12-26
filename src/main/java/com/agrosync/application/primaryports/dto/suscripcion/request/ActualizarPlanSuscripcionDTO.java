package com.agrosync.application.primaryports.dto.suscripcion.request;

import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;

import java.util.UUID;

public class ActualizarPlanSuscripcionDTO {

    private UUID id;
    private PlanSuscripcionEnum planSuscripcion;

    public ActualizarPlanSuscripcionDTO() {
    }

    public ActualizarPlanSuscripcionDTO(UUID id, PlanSuscripcionEnum planSuscripcion) {
        setId(id);
        setPlanSuscripcion(planSuscripcion);
    }

    public static ActualizarPlanSuscripcionDTO create(UUID id, PlanSuscripcionEnum planSuscripcion) {
        return new ActualizarPlanSuscripcionDTO(id, planSuscripcion);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlanSuscripcionEnum getPlanSuscripcion() {
        return planSuscripcion;
    }

    public void setPlanSuscripcion(PlanSuscripcionEnum planSuscripcion) {
        this.planSuscripcion = planSuscripcion;
    }
}

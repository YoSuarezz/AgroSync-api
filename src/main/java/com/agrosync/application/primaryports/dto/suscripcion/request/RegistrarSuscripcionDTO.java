package com.agrosync.application.primaryports.dto.suscripcion.request;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;
import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrarSuscripcionDTO {

    private UUID id;
    private String nombreEmpresa;
    private String direccionEmpresa;
    private Long telefonoEmpresa;
    private String nit;
    private String logoUrl;
    private String email;
    private EstadoSuscripcionEnum estadoSuscripcion;
    private PlanSuscripcionEnum planSuscripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaUltimoPago;
    private LocalDateTime fechaProximoCobro;

    public RegistrarSuscripcionDTO() {
        setNombreEmpresa(TextHelper.EMPTY);
        setDireccionEmpresa(TextHelper.EMPTY);
        setTelefonoEmpresa(0L);
        setNit(TextHelper.EMPTY);
        setLogoUrl(TextHelper.EMPTY);
        setEmail(TextHelper.EMPTY);
        setEstadoSuscripcion(EstadoSuscripcionEnum.ACTIVA);
        setPlanSuscripcion(PlanSuscripcionEnum.MENSUAL);
        setFechaInicio(LocalDateTime.now());
        setFechaUltimoPago(LocalDateTime.now());
    }

    public RegistrarSuscripcionDTO(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa,
                                   String nit, String logoUrl, String email, EstadoSuscripcionEnum estadoSuscripcion,
                                   PlanSuscripcionEnum planSuscripcion, LocalDateTime fechaInicio, LocalDateTime fechaUltimoPago,
                                   LocalDateTime fechaProximoCobro) {
        setId(id);
        setNombreEmpresa(nombreEmpresa);
        setDireccionEmpresa(direccionEmpresa);
        setTelefonoEmpresa(telefonoEmpresa);
        setNit(nit);
        setLogoUrl(logoUrl);
        setEmail(email);
        setEstadoSuscripcion(estadoSuscripcion);
        setPlanSuscripcion(planSuscripcion);
        setFechaInicio(fechaInicio);
        setFechaUltimoPago(fechaUltimoPago);
        setFechaProximoCobro(fechaProximoCobro);
    }

    public static RegistrarSuscripcionDTO create(UUID id, String nombreEmpresa, String direccionEmpresa,
                                                 Long telefonoEmpresa, String nit, String logoUrl, String email,
                                                 EstadoSuscripcionEnum estadoSuscripcion, PlanSuscripcionEnum planSuscripcion,
                                                 LocalDateTime fechaInicio, LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
        return new RegistrarSuscripcionDTO(id, nombreEmpresa, direccionEmpresa, telefonoEmpresa, nit, logoUrl, email,
                estadoSuscripcion, planSuscripcion, fechaInicio, fechaUltimoPago, fechaProximoCobro);
    }

    public static RegistrarSuscripcionDTO create() {
        return new RegistrarSuscripcionDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public RegistrarSuscripcionDTO setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = TextHelper.applyTrim(nombreEmpresa);
        return this;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public RegistrarSuscripcionDTO setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = TextHelper.applyTrim(direccionEmpresa);
        return this;
    }

    public Long getTelefonoEmpresa() {
        return ObjectHelper.getDefault(telefonoEmpresa, 0L);
    }

    public void setTelefonoEmpresa(Long telefonoEmpresa) {
        this.telefonoEmpresa = ObjectHelper.getDefault(telefonoEmpresa, 0L);
    }

    public String getNit() {
        return nit;
    }

    public RegistrarSuscripcionDTO setNit(String nit) {
        this.nit = TextHelper.applyTrim(nit);
        return this;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public RegistrarSuscripcionDTO setLogoUrl(String logoUrl) {
        this.logoUrl = TextHelper.applyTrim(logoUrl);
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegistrarSuscripcionDTO setEmail(String email) {
        this.email = TextHelper.applyTrim(email);
        return this;
    }

    public EstadoSuscripcionEnum getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public void setEstadoSuscripcion(EstadoSuscripcionEnum estadoSuscripcion) {
        this.estadoSuscripcion = ObjectHelper.getDefault(estadoSuscripcion, EstadoSuscripcionEnum.ACTIVA);
    }

    public PlanSuscripcionEnum getPlanSuscripcion() {
        return planSuscripcion;
    }

    public void setPlanSuscripcion(PlanSuscripcionEnum planSuscripcion) {
        this.planSuscripcion = ObjectHelper.getDefault(planSuscripcion, PlanSuscripcionEnum.MENSUAL);
    }

    public LocalDateTime getFechaInicio() {
        return ObjectHelper.getDefault(fechaInicio, LocalDateTime.now());
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = ObjectHelper.getDefault(fechaInicio, LocalDateTime.now());
    }

    public LocalDateTime getFechaUltimoPago() {
        return ObjectHelper.getDefault(fechaUltimoPago, LocalDateTime.now());
    }

    public void setFechaUltimoPago(LocalDateTime fechaUltimoPago) {
        this.fechaUltimoPago = ObjectHelper.getDefault(fechaUltimoPago, LocalDateTime.now());
        this.fechaProximoCobro = this.fechaUltimoPago.plusDays(30);
    }

    public LocalDateTime getFechaProximoCobro() {
        return ObjectHelper.getDefault(fechaProximoCobro, LocalDateTime.now());
    }

    public void setFechaProximoCobro(LocalDateTime fechaProximoCobro) {
        if (fechaProximoCobro == null && this.fechaUltimoPago != null) {
            this.fechaProximoCobro = this.fechaUltimoPago.plusDays(30);
        } else {
            this.fechaProximoCobro = ObjectHelper.getDefault(fechaProximoCobro, LocalDateTime.now());
        }
    }
}

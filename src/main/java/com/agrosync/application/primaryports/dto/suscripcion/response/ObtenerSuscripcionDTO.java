package com.agrosync.application.primaryports.dto.suscripcion.response;

import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;
import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.time.LocalDateTime;
import java.util.UUID;

public class ObtenerSuscripcionDTO {

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

    public ObtenerSuscripcionDTO(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa, String nit,
                                 String logoUrl, String email, EstadoSuscripcionEnum estadoSuscripcion,
                                 PlanSuscripcionEnum planSuscripcion,
                                 LocalDateTime fechaInicio, LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
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

    public ObtenerSuscripcionDTO() {
        setId(UUIDHelper.getDefault());
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
        setFechaProximoCobro(LocalDateTime.now());
    }

    public static ObtenerSuscripcionDTO create(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa,
                                               String nit, String logoUrl, String email, EstadoSuscripcionEnum estadoSuscripcion,
                                               PlanSuscripcionEnum planSuscripcion,
                                               LocalDateTime fechaInicio, LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
        return new ObtenerSuscripcionDTO(id, nombreEmpresa, direccionEmpresa, telefonoEmpresa, nit, logoUrl, email,
                estadoSuscripcion, planSuscripcion, fechaInicio, fechaUltimoPago, fechaProximoCobro);
    }

    public static ObtenerSuscripcionDTO create() {
        return new ObtenerSuscripcionDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public Long getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(Long telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EstadoSuscripcionEnum getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public void setEstadoSuscripcion(EstadoSuscripcionEnum estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
    }

    public PlanSuscripcionEnum getPlanSuscripcion() {
        return planSuscripcion;
    }

    public void setPlanSuscripcion(PlanSuscripcionEnum planSuscripcion) {
        this.planSuscripcion = planSuscripcion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(LocalDateTime fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public LocalDateTime getFechaProximoCobro() {
        return fechaProximoCobro;
    }

    public void setFechaProximoCobro(LocalDateTime fechaProximoCobro) {
        this.fechaProximoCobro = fechaProximoCobro;
    }
}

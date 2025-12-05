package com.agrosync.domain.suscripcion;

import com.agrosync.application.primaryports.enums.suscripcion.EstadoSuscripcionEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.auth.AuthUserDomain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SuscripcionDomain extends BaseDomain {

    private String nombreEmpresa;
    private String direccionEmpresa;
    private Long telefonoEmpresa;
    private String nit;
    private String logoUrl;
    private String email;
    private List<AuthUserDomain> usuarios;
    private EstadoSuscripcionEnum estadoSuscripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaUltimoPago;
    private LocalDateTime fechaProximoCobro;

    public SuscripcionDomain() {
        super();
    }

    public SuscripcionDomain(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa, String nit,
                             String logoUrl, String email, List<AuthUserDomain> usuarios,
                             EstadoSuscripcionEnum estadoSuscripcion, LocalDateTime fechaInicio,
                             LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
        super(id);
        setNombreEmpresa(nombreEmpresa);
        setDireccionEmpresa(direccionEmpresa);
        setTelefonoEmpresa(telefonoEmpresa);
        setNit(nit);
        setLogoUrl(logoUrl);
        setEmail(email);
        setUsuarios(usuarios);
        setEstadoSuscripcion(estadoSuscripcion);
        setFechaInicio(fechaInicio);
        setFechaUltimoPago(fechaUltimoPago);
        setFechaProximoCobro(fechaProximoCobro);
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = TextHelper.applyTrim(nombreEmpresa);
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = TextHelper.applyTrim(direccionEmpresa);
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
        this.nit = TextHelper.applyTrim(nit);
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = TextHelper.applyTrim(logoUrl);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = TextHelper.applyTrim(email);
    }

    public List<AuthUserDomain> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<AuthUserDomain> usuarios) {
        this.usuarios = usuarios;
    }

    public EstadoSuscripcionEnum getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public void setEstadoSuscripcion(EstadoSuscripcionEnum estadoSuscripcion) {
        this.estadoSuscripcion = ObjectHelper.getDefault(estadoSuscripcion, EstadoSuscripcionEnum.ACTIVA);
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

package com.agrosync.application.primaryports.dto.suscripcion.response;

import com.agrosync.application.primaryports.dto.auth.AuthUserDTO;
import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObtenerSuscripcionPorIdDTO {

    private UUID id;
    private String nombreEmpresa;
    private String direccionEmpresa;
    private Long telefonoEmpresa;
    private String nit;
    private String logoUrl;
    private String email;
    private List<AuthUserDTO> usuarios;
    private EstadoSuscripcionEnum estadoSuscripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaUltimoPago;
    private LocalDateTime fechaProximoCobro;

    public ObtenerSuscripcionPorIdDTO(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa,
                                      String nit, String logoUrl, String email, List<AuthUserDTO> usuarios,
                                      EstadoSuscripcionEnum estadoSuscripcion, LocalDateTime fechaInicio,
                                      LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
        setId(id);
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

    public ObtenerSuscripcionPorIdDTO() {
        setId(UUIDHelper.getDefault());
        setNombreEmpresa(TextHelper.EMPTY);
        setDireccionEmpresa(TextHelper.EMPTY);
        setTelefonoEmpresa(0L);
        setNit(TextHelper.EMPTY);
        setLogoUrl(TextHelper.EMPTY);
        setEmail(TextHelper.EMPTY);
        setUsuarios(new ArrayList<>());
        setEstadoSuscripcion(EstadoSuscripcionEnum.ACTIVA);
        setFechaInicio(LocalDateTime.now());
        setFechaUltimoPago(LocalDateTime.now());
        setFechaProximoCobro(LocalDateTime.now());
    }

    public static ObtenerSuscripcionPorIdDTO create(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa,
                                                    String nit, String logoUrl, String email, List<AuthUserDTO> usuarios,
                                                    EstadoSuscripcionEnum estadoSuscripcion, LocalDateTime fechaInicio,
                                                    LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
        return new ObtenerSuscripcionPorIdDTO(id, nombreEmpresa, direccionEmpresa, telefonoEmpresa, nit, logoUrl, email,
                usuarios, estadoSuscripcion, fechaInicio, fechaUltimoPago, fechaProximoCobro);
    }

    public static ObtenerSuscripcionPorIdDTO create() {
        return new ObtenerSuscripcionPorIdDTO();
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

    public List<AuthUserDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<AuthUserDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public EstadoSuscripcionEnum getEstadoSuscripcion() {
        return estadoSuscripcion;
    }

    public void setEstadoSuscripcion(EstadoSuscripcionEnum estadoSuscripcion) {
        this.estadoSuscripcion = estadoSuscripcion;
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

package com.agrosync.application.secondaryports.entity.suscripcion;

import com.agrosync.domain.enums.suscripcion.EstadoSuscripcionEnum;
import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.auth.AuthUserEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.enums.suscripcion.PlanSuscripcionEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "suscripcion", schema = "agryxo")
public class SuscripcionEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre_empresa", nullable = false)
    private String nombreEmpresa;

    @Column(name = "direccion_empresa")
    private String direccionEmpresa;

    @Column(name = "telefono_empresa")
    private Long telefonoEmpresa;

    @Column(name = "nit", unique = true, nullable = false)
    private String nit;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "suscripcion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AuthUserEntity> usuarios;

    @Enumerated(EnumType.STRING)
    private EstadoSuscripcionEnum estadoSuscripcion;

    @Enumerated(EnumType.STRING)
    private PlanSuscripcionEnum planSuscripcion;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_ultimo_pago")
    private LocalDateTime fechaUltimoPago;

    @Column(name = "fecha_proximo_cobro")
    private LocalDateTime fechaProximoCobro;

    public SuscripcionEntity() {
        setId(UUIDHelper.getDefault());
        setNombreEmpresa(TextHelper.EMPTY);
        setDireccionEmpresa(TextHelper.EMPTY);
        setTelefonoEmpresa(0L);
        setNit(TextHelper.EMPTY);
        setLogoUrl(TextHelper.EMPTY);
        setEmail(TextHelper.EMPTY);
        setUsuarios(new ArrayList<>());
        setEstadoSuscripcion(EstadoSuscripcionEnum.ACTIVA);
        setPlanSuscripcion(PlanSuscripcionEnum.MENSUAL);
        setFechaInicio(LocalDateTime.now());
        setFechaUltimoPago(LocalDateTime.now());
        setFechaProximoCobro(LocalDateTime.now());
    }

    public SuscripcionEntity(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa, String nit,
                             String logoUrl, String email, List<AuthUserEntity> usuarios,
                             EstadoSuscripcionEnum estadoSuscripcion, PlanSuscripcionEnum planSuscripcion,
                             LocalDateTime fechaInicio, LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
        setId(id);
        setNombreEmpresa(nombreEmpresa);
        setDireccionEmpresa(direccionEmpresa);
        setTelefonoEmpresa(telefonoEmpresa);
        setNit(nit);
        setLogoUrl(logoUrl);
        setEmail(email);
        setUsuarios(usuarios);
        setEstadoSuscripcion(estadoSuscripcion);
        setPlanSuscripcion(planSuscripcion);
        setFechaInicio(fechaInicio);
        setFechaUltimoPago(fechaUltimoPago);
        setFechaProximoCobro(fechaProximoCobro);
    }

    public static SuscripcionEntity create(UUID id, String nombreEmpresa, String direccionEmpresa, Long telefonoEmpresa,
                                           String nit, String logoUrl, String email, List<AuthUserEntity> usuarios,
                                           EstadoSuscripcionEnum estadoSuscripcion, PlanSuscripcionEnum planSuscripcion,
                                           LocalDateTime fechaInicio, LocalDateTime fechaUltimoPago, LocalDateTime fechaProximoCobro) {
        return new SuscripcionEntity(id, nombreEmpresa, direccionEmpresa, telefonoEmpresa, nit, logoUrl, email, usuarios,
                estadoSuscripcion, planSuscripcion, fechaInicio, fechaUltimoPago, fechaProximoCobro);
    }

    public static SuscripcionEntity create(UUID id) {
        return new SuscripcionEntity(id, TextHelper.EMPTY, TextHelper.EMPTY, 0L, TextHelper.EMPTY, TextHelper.EMPTY,
                TextHelper.EMPTY, new ArrayList<>(), EstadoSuscripcionEnum.ACTIVA, PlanSuscripcionEnum.MENSUAL,
                LocalDateTime.now(), LocalDateTime.now(),
                LocalDateTime.now());
    }

    public static SuscripcionEntity create() {
        return new SuscripcionEntity(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, 0L, TextHelper.EMPTY,
                TextHelper.EMPTY, TextHelper.EMPTY, new ArrayList<>(), EstadoSuscripcionEnum.ACTIVA, PlanSuscripcionEnum.MENSUAL,
                LocalDateTime.now(),
                LocalDateTime.now(), LocalDateTime.now());
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

    public List<AuthUserEntity> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<AuthUserEntity> usuarios) {
        this.usuarios = usuarios;
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

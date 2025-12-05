package com.agrosync.application.secondaryports.entity.ventas;

import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "venta")
public class VentaEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero_venta")
    private String numeroVenta; // VE - contramarcaLote - 4 digitos aleatorios

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private UsuarioEntity cliente;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(name = "precio_total_venta")
    private BigDecimal precioTotalVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalEntity> animales;

    @OneToOne(mappedBy = "venta", cascade = CascadeType.ALL)
    private CuentaCobrarEntity cuentaCobrar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    public VentaEntity() {
        setId(UUIDHelper.getDefault());
        setNumeroVenta(TextHelper.EMPTY);
        setCliente(UsuarioEntity.create());
        setFechaVenta(LocalDate.now());
        setPrecioTotalVenta(BigDecimal.ZERO);
        setAnimales(new ArrayList<>());
        setCuentaCobrar(null);
        setSuscripcion(SuscripcionEntity.create());
    }

    public VentaEntity(UUID id, String numeroVenta, UsuarioEntity cliente, LocalDate fechaVenta, BigDecimal precioTotalVenta, List<AnimalEntity> animales, CuentaCobrarEntity cuentaCobrar, SuscripcionEntity suscripcion) {
        setId(id);
        setNumeroVenta(numeroVenta);
        setCliente(cliente);
        setFechaVenta(fechaVenta);
        setPrecioTotalVenta(precioTotalVenta);
        setAnimales(animales);
        setCuentaCobrar(cuentaCobrar);
        setSuscripcion(suscripcion);
    }

    public static VentaEntity create(UUID id, String numeroVenta, UsuarioEntity cliente, LocalDate fechaVenta, BigDecimal precioTotalVenta, List<AnimalEntity> animales, CuentaCobrarEntity cuentaCobrar, SuscripcionEntity suscripcion) {
        return new VentaEntity(id, numeroVenta, cliente, fechaVenta, precioTotalVenta, animales, cuentaCobrar, suscripcion);
    }

    public static VentaEntity create(UUID id) {
        return new VentaEntity(id, TextHelper.EMPTY, UsuarioEntity.create(), LocalDate.now(), BigDecimal.ZERO, new ArrayList<>(), null, SuscripcionEntity.create());
    }

    public static VentaEntity create() {
        return new VentaEntity(UUIDHelper.getDefault(), TextHelper.EMPTY, UsuarioEntity.create(), LocalDate.now(), BigDecimal.ZERO, new ArrayList<>(), null, SuscripcionEntity.create());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = TextHelper.applyTrim(numeroVenta);
    }

    public UsuarioEntity getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioEntity cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getPrecioTotalVenta() {
        return precioTotalVenta;
    }

    public void setPrecioTotalVenta(BigDecimal precioTotalVenta) {
        this.precioTotalVenta = precioTotalVenta;
    }

    public List<AnimalEntity> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalEntity> animales) {
        this.animales = animales;
    }

    public CuentaCobrarEntity getCuentaCobrar() {
        return cuentaCobrar;
    }

    public void setCuentaCobrar(CuentaCobrarEntity cuentaCobrar) {
        this.cuentaCobrar = cuentaCobrar;
    }

    public SuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(SuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }
}

package com.agrosync.application.secondaryports.entity.lotes;

import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lote")
public class LoteEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "id_compra")
    private CompraEntity compra;

    @Column(name = "numero_lote")
    private String numeroLote; // Lote - Semana - año

    @Column(name = "contramarca")
    private String contramarca;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "peso_total", precision = 10, scale = 2)
    private BigDecimal pesoTotal;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalEntity> animales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    public LoteEntity() {
        setId(UUIDHelper.getDefault());
        setCompra(null);
        setNumeroLote(TextHelper.EMPTY);
        setContramarca(TextHelper.EMPTY);
        setFecha(LocalDate.now());
        setPesoTotal(BigDecimal.ZERO);
        setAnimales(new ArrayList<>());
        setSuscripcion(SuscripcionEntity.create());
    }

    public LoteEntity(UUID id, CompraEntity compra, String numeroLote, String contramarca, LocalDate fecha, BigDecimal pesoTotal, List<AnimalEntity> animales, SuscripcionEntity suscripcion) {
        setId(id);
        setCompra(compra);
        setNumeroLote(numeroLote);
        setContramarca(contramarca);
        setFecha(fecha);
        setPesoTotal(pesoTotal);
        setAnimales(animales);
        setSuscripcion(suscripcion);
    }

    public static LoteEntity create(UUID id, CompraEntity compra, String numeroLote, String contramarca, LocalDate fecha, BigDecimal pesoTotal, List<AnimalEntity> animales, SuscripcionEntity suscripcion) {
        return new LoteEntity(id, compra, numeroLote, contramarca, fecha, pesoTotal, animales, suscripcion);
    }

    public static LoteEntity create(UUID id) {
        return new LoteEntity(id, null, TextHelper.EMPTY, TextHelper.EMPTY, LocalDate.now(), BigDecimal.ZERO, new ArrayList<>(), SuscripcionEntity.create());
    }

    public static LoteEntity create() {
        return new LoteEntity(UUIDHelper.getDefault(), null, TextHelper.EMPTY, TextHelper.EMPTY, LocalDate.now(), BigDecimal.ZERO, new ArrayList<>(), SuscripcionEntity.create());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public CompraEntity getCompra() {
        return compra;
    }

    public void setCompra(CompraEntity compra) {
        this.compra = compra;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = TextHelper.applyTrim(numeroLote);
    }

    public String getContramarca() {
        return contramarca;
    }

    public void setContramarca(String contramarca) {
        this.contramarca = TextHelper.applyTrim(contramarca);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(BigDecimal pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

    public List<AnimalEntity> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalEntity> animales) {
        this.animales = animales;
    }

    public SuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(SuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }
}

package com.agrosync.application.secondaryports.entity.animales;

import com.agrosync.application.primaryports.enums.EstadoAnimalEnum;
import com.agrosync.application.primaryports.enums.SexoEnum;
import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "animal")
public class AnimalEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "slot")
    private String slot;

    @Column(name = "numero_animal")
    private String numeroAnimal; // contramarca del lote - slot- semana - año

    @Column(name = "peso")
    private BigDecimal peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private SexoEnum sexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote")
    private LoteEntity lote;

    @Column(name = "precio_kilo_compra")
    private BigDecimal precioKiloCompra;

    @Column(name = "precio_kilo_venta")
    private BigDecimal precioKiloVenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoAnimalEnum estado;

    public AnimalEntity() {
        setId(UUIDHelper.getDefault());
        setSlot(TextHelper.EMPTY);
        setNumeroAnimal(TextHelper.EMPTY);
        setPeso(BigDecimal.ZERO);
        setSexo(SexoEnum.MACHO);
        setLote(LoteEntity.create());
        setPrecioKiloCompra(BigDecimal.ZERO);
        setPrecioKiloVenta(BigDecimal.ZERO);
        setEstado(EstadoAnimalEnum.DISPONIBLE);
    }

    public AnimalEntity(UUID id, String slot, String numeroAnimal, BigDecimal peso, SexoEnum sexo, LoteEntity lote, BigDecimal precioKiloCompra, BigDecimal precioKiloVenta, EstadoAnimalEnum estado) {
        setId(id);
        setSlot(slot);
        setNumeroAnimal(numeroAnimal);
        setPeso(peso);
        setSexo(sexo);
        setLote(lote);
        setPrecioKiloCompra(precioKiloCompra);
        setPrecioKiloVenta(precioKiloVenta);
        setEstado(estado);
    }

    public static AnimalEntity create(UUID id, String slot, String numeroAnimal, BigDecimal peso, SexoEnum sexo, LoteEntity lote, BigDecimal precioKiloCompra, BigDecimal precioKiloVenta, EstadoAnimalEnum estado) {
        return new AnimalEntity(id, slot, numeroAnimal, peso, sexo, lote, precioKiloCompra, precioKiloVenta, estado);
    }

    public static AnimalEntity create(UUID id) {
        return new AnimalEntity(id, TextHelper.EMPTY, TextHelper.EMPTY, BigDecimal.ZERO, SexoEnum.MACHO, LoteEntity.create(), BigDecimal.ZERO, BigDecimal.ZERO, EstadoAnimalEnum.DISPONIBLE);
    }

    public static AnimalEntity create() {
        return new AnimalEntity(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, BigDecimal.ZERO, SexoEnum.MACHO, LoteEntity.create(), BigDecimal.ZERO, BigDecimal.ZERO, EstadoAnimalEnum.DISPONIBLE);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = TextHelper.EMPTY;
    }

    public String getNumeroAnimal() {
        return numeroAnimal;
    }

    public void setNumeroAnimal(String numeroAnimal) {
        this.numeroAnimal = numeroAnimal;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public LoteEntity getLote() {
        return lote;
    }

    public void setLote(LoteEntity lote) {
        this.lote = ObjectHelper.getDefault(lote , LoteEntity.create());
    }

    public BigDecimal getPrecioKiloCompra() {
        return precioKiloCompra;
    }

    public void setPrecioKiloCompra(BigDecimal precioKiloCompra) {
        this.precioKiloCompra = precioKiloCompra;
    }

    public BigDecimal getPrecioKiloVenta() {
        return precioKiloVenta;
    }

    public void setPrecioKiloVenta(BigDecimal precioKiloVenta) {
        this.precioKiloVenta = precioKiloVenta;
    }

    public EstadoAnimalEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnimalEnum estado) {
        this.estado = estado;
    }
}

package com.agrosync.application.secondaryports.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lote")
public class LoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @Column(name = "contramarca")
    private Integer contramarca;

    @Column(name = "precio_total")
    private BigDecimal precioTotal;

    @Column(name = "fecha")
    private LocalDate fecha;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AnimalEntity> animales;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GastoEntity> gastos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<GastoEntity> getGastos() {
        return gastos;
    }

    public void setGastos(List<GastoEntity> gastos) {
        this.gastos = gastos;
    }

    public List<AnimalEntity> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalEntity> animales) {
        this.animales = animales;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Integer getContramarca() {
        return contramarca;
    }

    public void setContramarca(Integer contramarca) {
        this.contramarca = contramarca;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "LoteEntity{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", contramarca=" + contramarca +
                ", precio_total=" + precioTotal +
                ", fecha=" + fecha +
                '}';
    }
}

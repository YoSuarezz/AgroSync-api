package com.sedikev.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal")
public class AnimalEntity {
    @Column(name="id_animal")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JoinColumn(name = "id_lote")
    @ManyToOne
    private LoteEntity lote;

    @Column(name = "peso")
    private BigDecimal peso;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "slot")
    private int num_lote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoteEntity getLote() {
        return lote;
    }

    public void setLote(LoteEntity lote) {
        this.lote = lote;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getNum_lote() {
        return num_lote;
    }

    public void setNum_lote(int num_lote) {
        this.num_lote = num_lote;
    }
}

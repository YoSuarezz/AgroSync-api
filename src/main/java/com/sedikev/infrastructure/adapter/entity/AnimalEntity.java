package com.sedikev.infrastructure.adapter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal")
public class AnimalEntity {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @JoinColumn(name = "id_lote")
    @ManyToOne
    private LoteEntity loteEntity;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "peso")
    private Float peso;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "num_lote")
    private int num_lote;
}

package com.sedikev.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne
//    @JoinColumn(name = "id_lote", nullable = false)
//    private Lote lote;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Float peso;

    @Column(nullable = false, length = 10)
    private String sexo;

    @Column(name = "num_lote", nullable = false)
    private Integer numLote;
}

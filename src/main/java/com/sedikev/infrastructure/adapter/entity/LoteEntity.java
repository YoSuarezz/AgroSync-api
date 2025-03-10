package com.sedikev.infrastructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_proveedor")
    @ManyToOne
    private UsuarioEntity usuarioEntity;

    @Column(name = "contramarca")
    private int contramarca;

    @Column(name = "precio_kilo")
    private BigDecimal precio_kilo;

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @OneToMany(mappedBy = "loteEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AnimalEntity> lista_animalEntity;

    @OneToMany(mappedBy = "loteEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GastoEntity> lista_gastoEntity;
}

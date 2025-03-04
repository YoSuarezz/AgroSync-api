package com.sedikev.domain.entity;

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
public class Lote {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_proveedor")
    @ManyToOne
    private Usuario proveedor;

    @Column(name = "contramarca")
    private int contramarca;

    @Column(name = "precio_kilo")
    private BigDecimal precio_kilo;

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Animal> lista_animal;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Gasto> lista_gasto;
}

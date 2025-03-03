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
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "id_animal")
    @OneToOne
    private Animal animal;

    @JoinColumn(name = "id_comprador")
    @ManyToOne
    private Usuario comprador;

    @Column(name = "estado")
    private String estado;

    @Column(name = "precio_kilo")
    private BigDecimal precio_kilo;

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pago> lista_pago;
}

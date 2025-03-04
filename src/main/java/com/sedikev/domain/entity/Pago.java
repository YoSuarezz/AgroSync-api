package com.sedikev.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "id_venta")
    @ManyToOne
    private Venta venta;

    @JoinColumn(name = "id_comprador")
    @ManyToOne
    private Usuario comprador;

    @Column(name = "tipo_pago")
    private String tipo_pago;

    @Column(name = "cantidad")
    private BigDecimal cantidad;

    @Column(name = "descripcion")
    @Lob
    private String descripcion;

    @Column(name = "fecha")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
}

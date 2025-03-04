package com.sedikev.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "tipo_usuario")
    private String tipo_usuario;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoteEntity> lista_loteEntity;

    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VentaEntity> lista_ventaEntity;

    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PagoEntity> lista_pagoEntities;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GastoEntity> lista_gastoEntity;
}

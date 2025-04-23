package com.sedikev.infrastructure.adapter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "tipo_usuario")
    private String tipo_usuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoteEntity> lista_loteEntity;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VentaEntity> lista_ventaEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public List<LoteEntity> getLista_loteEntity() {
        return lista_loteEntity;
    }

    public void setLista_loteEntity(List<LoteEntity> lista_loteEntity) {
        this.lista_loteEntity = lista_loteEntity;
    }

    public List<VentaEntity> getLista_ventaEntity() {
        return lista_ventaEntity;
    }

    public void setLista_ventaEntity(List<VentaEntity> lista_ventaEntity) {
        this.lista_ventaEntity = lista_ventaEntity;
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipo_usuario='" + tipo_usuario + '\'' +
                '}';
    }
}

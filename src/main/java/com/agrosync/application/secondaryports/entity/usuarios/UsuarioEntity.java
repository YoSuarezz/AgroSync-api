package com.agrosync.application.secondaryports.entity.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.agrosync.application.secondaryports.entity.LoteEntity;
import com.agrosync.application.secondaryports.entity.VentaEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TIpoUsuarioEntity tipo_usuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LoteEntity> lista_loteEntity;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VentaEntity> lista_ventaEntity;

    public UsuarioEntity() {
        setId(id);
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipo_usuario(TIpoUsuarioEntity.create());
        setLista_loteEntity(new ArrayList<>());
        setLista_ventaEntity(new ArrayList<>());
    }

    public UsuarioEntity(Long id, String nombre, String telefono, TIpoUsuarioEntity tipo_usuario, List<LoteEntity> lista_loteEntity, List<VentaEntity> lista_ventaEntity) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipo_usuario(tipo_usuario);
        setLista_loteEntity(lista_loteEntity);
        setLista_ventaEntity(lista_ventaEntity);
    }

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
        this.nombre = TextHelper.applyTrim(nombre);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = TextHelper.applyTrim(telefono);
    }

    public TIpoUsuarioEntity getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TIpoUsuarioEntity tipo_usuario) {
        this.tipo_usuario = ObjectHelper.getDefault(tipo_usuario, TIpoUsuarioEntity.create());
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
}

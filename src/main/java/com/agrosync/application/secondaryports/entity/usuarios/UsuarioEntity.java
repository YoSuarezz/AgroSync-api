package com.agrosync.application.secondaryports.entity.usuarios;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import jakarta.persistence.*;

import java.util.ArrayList;

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

    public UsuarioEntity() {
        setId(id);
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipo_usuario(TIpoUsuarioEntity.create());
    }

    public UsuarioEntity(Long id, String nombre, String telefono, TIpoUsuarioEntity tipo_usuario) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipo_usuario(tipo_usuario);
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

}

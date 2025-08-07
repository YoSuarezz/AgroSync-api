package com.sedikev.application.secondaryports.entity.usuarios;

import com.sedikev.crosscutting.helpers.TextHelper;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo_usuario")
public class TIpoUsuarioEntity {

    @Id
    @Column(name = "id_tipo_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    public TIpoUsuarioEntity() {
        setId(id);
        setNombre(TextHelper.EMPTY);
    }

    public TIpoUsuarioEntity(Long id, String nombre) {
        setId(id);
        setNombre(nombre);
    }

    public static TIpoUsuarioEntity create(Long id, String nombre) {
        return new TIpoUsuarioEntity(id, nombre);
    }

    public static TIpoUsuarioEntity create(Long id) {
        return new TIpoUsuarioEntity(id, TextHelper.EMPTY);
    }

    public static TIpoUsuarioEntity create() {
        return new TIpoUsuarioEntity();
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
}

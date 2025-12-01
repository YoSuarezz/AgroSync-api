package com.agrosync.application.secondaryports.entity.usuarios;

import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tipo_usuario")
public class TipoUsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    public TipoUsuarioEntity() {
        setId(UUIDHelper.getDefault());
        setNombre(TextHelper.EMPTY);
    }

    public TipoUsuarioEntity(UUID id, String nombre) {
        setId(id);
        setNombre(nombre);
    }

    public static TipoUsuarioEntity create(UUID id, String nombre) {
        return new TipoUsuarioEntity(id, nombre);
    }

    public static TipoUsuarioEntity create(UUID id) {
        return new TipoUsuarioEntity(id, TextHelper.EMPTY);
    }

    public static TipoUsuarioEntity create() {
        return new TipoUsuarioEntity(UUIDHelper.getDefault(), TextHelper.EMPTY);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
    }
}

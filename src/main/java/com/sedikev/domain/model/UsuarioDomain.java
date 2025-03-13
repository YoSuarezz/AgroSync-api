package com.sedikev.domain.model;

import lombok.*;

@Data
public class UsuarioDomain {

    private Long id;
    private String nombre;
    private String telefono;
    private String tipo_usuario;
}

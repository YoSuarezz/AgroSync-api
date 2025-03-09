package com.sedikev.application.domain;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDomain {

    private Long id;
    private String nombre;
    private String telefono;
    private String tipo_usuario;
}

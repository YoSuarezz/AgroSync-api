package com.sedikev.application.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private String id;
    private Integer idLote;
    private String nombre;
    private Float peso;
    private String sexo;
    private Integer numLote;
}

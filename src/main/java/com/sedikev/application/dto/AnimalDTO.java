package com.sedikev.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
@Data
@Getter
@Setter
public class AnimalDTO {

    private String id;
    private LoteDTO lote;
    private String nombre;
    private BigDecimal peso;
    private String sexo;
    private Integer num_lote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoteDTO getLote() {
        return lote;
    }

    public void setLote(LoteDTO lote) {
        this.lote = lote;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getNum_lote() {
        return num_lote;
    }

    public void setNum_lote(Integer num_lote) {
        this.num_lote = num_lote;
    }
}

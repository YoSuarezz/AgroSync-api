package com.sedikev.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
@Data
@Getter
@Setter
public class AnimalDTO {

    private int id;
    private LoteDTO lote;
    private BigDecimal peso;
    private String sexo;
    private Integer num_lote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoteDTO getLote() {
        return lote;
    }

    public void setLote(LoteDTO lote) {
        this.lote = lote;
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

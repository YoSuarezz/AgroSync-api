package com.agrosync.application.primaryports.dto.animales.request;

import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.enums.animales.EstadoAnimalEnum;
import com.agrosync.application.primaryports.enums.animales.SexoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.UUID;

public class AnimalPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerAnimalDTO animal;
    private SexoEnum sexo;
    private EstadoAnimalEnum estado;
    private UUID suscripcionId;

    public AnimalPageDTO() {
    }

    public AnimalPageDTO(int page, int size, String sortBy, String sortDirection, ObtenerAnimalDTO animal, SexoEnum sexo, EstadoAnimalEnum estado, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setAnimal(animal);
        setSexo(sexo);
        setEstado(estado);
        setSuscripcionId(suscripcionId);
    }

    public static AnimalPageDTO create(int page, int size, String sortBy, String sortDirection, ObtenerAnimalDTO animal, SexoEnum sexo, EstadoAnimalEnum estado, UUID suscripcionId) {
        return new AnimalPageDTO(page, size, sortBy, sortDirection, animal, sexo, estado, suscripcionId);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public ObtenerAnimalDTO getAnimal() {
        return animal;
    }

    public void setAnimal(ObtenerAnimalDTO animal) {
        this.animal = ObjectHelper.getDefault(animal, ObtenerAnimalDTO.create());
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public EstadoAnimalEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnimalEnum estado) {
        this.estado = estado;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}


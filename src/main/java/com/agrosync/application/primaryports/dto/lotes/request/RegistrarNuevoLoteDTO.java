package com.agrosync.application.primaryports.dto.lotes.request;

import com.agrosync.application.primaryports.dto.animales.request.RegistrarNuevoAnimalDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegistrarNuevoLoteDTO {

    private String contramarca;
    private List<RegistrarNuevoAnimalDTO> animales;
    private UUID suscripcionId;

    public RegistrarNuevoLoteDTO() {
        setContramarca(TextHelper.EMPTY);
        setAnimales(new ArrayList<>());
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public RegistrarNuevoLoteDTO(String contramarca, List<RegistrarNuevoAnimalDTO> animales, UUID suscripcionId) {
        setContramarca(contramarca);
        setAnimales(animales);
        setSuscripcionId(suscripcionId);
    }

    public static RegistrarNuevoLoteDTO create(String contramarca, List<RegistrarNuevoAnimalDTO> animales, UUID suscripcionId) {
        return new RegistrarNuevoLoteDTO(contramarca, animales, suscripcionId);
    }

    public static RegistrarNuevoLoteDTO create() {
        return new RegistrarNuevoLoteDTO();
    }

    public String getContramarca() {
        return contramarca;
    }

    public void setContramarca(String contramarca) {
        this.contramarca = TextHelper.applyTrim(contramarca);
    }

    public List<RegistrarNuevoAnimalDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<RegistrarNuevoAnimalDTO> animales) {
        this.animales = ObjectHelper.getDefault(animales, new ArrayList<>());
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}

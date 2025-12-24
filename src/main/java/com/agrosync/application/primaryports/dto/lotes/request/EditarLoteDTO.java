package com.agrosync.application.primaryports.dto.lotes.request;

import com.agrosync.application.primaryports.dto.animales.request.EditarAnimalDTO;
import com.agrosync.application.primaryports.dto.animales.request.RegistrarNuevoAnimalDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditarLoteDTO {

    private UUID id;
    private String contramarca;
    private List<EditarAnimalDTO> animalesAEditar;
    private List<RegistrarNuevoAnimalDTO> animalesAAgregar;
    private List<UUID> animalesAEliminar;
    private UUID suscripcionId;

    public EditarLoteDTO() {
        setId(UUIDHelper.getDefault());
        setContramarca(TextHelper.EMPTY);
        setAnimalesAEditar(new ArrayList<>());
        setAnimalesAAgregar(new ArrayList<>());
        setAnimalesAEliminar(new ArrayList<>());
        setSuscripcionId(UUIDHelper.getDefault());
    }

    public EditarLoteDTO(UUID id, String contramarca, List<EditarAnimalDTO> animalesAEditar,
            List<RegistrarNuevoAnimalDTO> animalesAAgregar, List<UUID> animalesAEliminar,
            UUID suscripcionId) {
        setId(id);
        setContramarca(contramarca);
        setAnimalesAEditar(animalesAEditar);
        setAnimalesAAgregar(animalesAAgregar);
        setAnimalesAEliminar(animalesAEliminar);
        setSuscripcionId(suscripcionId);
    }

    public static EditarLoteDTO create(UUID id, String contramarca, List<EditarAnimalDTO> animalesAEditar,
            List<RegistrarNuevoAnimalDTO> animalesAAgregar, List<UUID> animalesAEliminar,
            UUID suscripcionId) {
        return new EditarLoteDTO(id, contramarca, animalesAEditar, animalesAAgregar, animalesAEliminar, suscripcionId);
    }

    public static EditarLoteDTO create() {
        return new EditarLoteDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getContramarca() {
        return contramarca;
    }

    public void setContramarca(String contramarca) {
        this.contramarca = TextHelper.applyTrim(contramarca);
    }

    public List<EditarAnimalDTO> getAnimalesAEditar() {
        return animalesAEditar;
    }

    public void setAnimalesAEditar(List<EditarAnimalDTO> animalesAEditar) {
        this.animalesAEditar = ObjectHelper.getDefault(animalesAEditar, new ArrayList<>());
    }

    public List<RegistrarNuevoAnimalDTO> getAnimalesAAgregar() {
        return animalesAAgregar;
    }

    public void setAnimalesAAgregar(List<RegistrarNuevoAnimalDTO> animalesAAgregar) {
        this.animalesAAgregar = ObjectHelper.getDefault(animalesAAgregar, new ArrayList<>());
    }

    public List<UUID> getAnimalesAEliminar() {
        return animalesAEliminar;
    }

    public void setAnimalesAEliminar(List<UUID> animalesAEliminar) {
        this.animalesAEliminar = ObjectHelper.getDefault(animalesAEliminar, new ArrayList<>());
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = UUIDHelper.getDefault(suscripcionId, UUIDHelper.getDefault());
    }
}

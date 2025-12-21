package com.agrosync.domain;

import java.util.UUID;


//Clase de dominio para representar un identificador junto con su suscripción.
//Usada para validaciones que requieren ambos valores.

public class IdConSuscripcion {

    private final UUID id;
    private final UUID suscripcionId;

    public IdConSuscripcion(UUID id, UUID suscripcionId) {
        this.id = id;
        this.suscripcionId = suscripcionId;
    }

    public static IdConSuscripcion of(UUID id, UUID suscripcionId) {
        return new IdConSuscripcion(id, suscripcionId);
    }

    public UUID getId() {
        return id;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }
}

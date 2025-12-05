package com.agrosync.domain.suscripcion.rules;

import java.util.UUID;

public interface SuscripcionExisteRule {

    void validate(UUID data);
}

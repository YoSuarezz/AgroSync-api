package com.agrosync.domain;

import java.util.UUID;

public abstract class BaseDomain {

    private UUID id;

    protected BaseDomain() {
    }

    protected BaseDomain(UUID id) {
        setId(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

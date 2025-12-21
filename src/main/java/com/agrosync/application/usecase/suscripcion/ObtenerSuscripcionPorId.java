package com.agrosync.application.usecase.suscripcion;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.suscripcion.SuscripcionDomain;

import java.util.UUID;

public interface ObtenerSuscripcionPorId extends UseCaseWithReturn<UUID, SuscripcionDomain> {
}

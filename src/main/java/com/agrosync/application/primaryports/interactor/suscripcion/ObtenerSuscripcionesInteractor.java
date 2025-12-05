package com.agrosync.application.primaryports.interactor.suscripcion;

import com.agrosync.application.primaryports.dto.suscripcion.request.SuscripcionPageDTO;
import com.agrosync.application.primaryports.dto.suscripcion.response.ObtenerSuscripcionDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerSuscripcionesInteractor extends InteractorWithReturn<SuscripcionPageDTO, PageResponse<ObtenerSuscripcionDTO>> {
}

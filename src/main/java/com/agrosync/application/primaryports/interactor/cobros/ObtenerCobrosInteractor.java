package com.agrosync.application.primaryports.interactor.cobros;

import com.agrosync.application.primaryports.dto.cobros.request.CobroPageDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerCobrosInteractor extends InteractorWithReturn<CobroPageDTO, PageResponse<ObtenerCobroDTO>> {
}


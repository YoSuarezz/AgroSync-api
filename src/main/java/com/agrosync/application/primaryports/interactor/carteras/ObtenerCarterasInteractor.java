package com.agrosync.application.primaryports.interactor.carteras;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerCarterasInteractor extends InteractorWithReturn<CarteraPageDTO, PageResponse<ObtenerCarteraDTO>> {
}

package com.agrosync.application.primaryports.interactor.lotes;

import com.agrosync.application.primaryports.dto.lotes.request.LotePageDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerLotesInteractor extends InteractorWithReturn<LotePageDTO, PageResponse<ObtenerLoteDTO>> {
}

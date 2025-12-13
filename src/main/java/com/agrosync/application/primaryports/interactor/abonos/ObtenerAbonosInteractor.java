package com.agrosync.application.primaryports.interactor.abonos;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoPageDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerAbonosInteractor extends InteractorWithReturn<AbonoPageDTO, PageResponse<ObtenerAbonoDTO>> {
}

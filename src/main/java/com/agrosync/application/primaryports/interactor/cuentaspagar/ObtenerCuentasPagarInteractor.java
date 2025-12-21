package com.agrosync.application.primaryports.interactor.cuentaspagar;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarPageDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerCuentasPagarInteractor extends InteractorWithReturn<CuentaPagarPageDTO, PageResponse<ObtenerCuentaPagarDTO>> {
}

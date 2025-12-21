package com.agrosync.application.primaryports.interactor.cuentascobrar;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarPageDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerCuentasCobrarInteractor extends InteractorWithReturn<CuentaCobrarPageDTO, PageResponse<ObtenerCuentaCobrarDTO>> {
}

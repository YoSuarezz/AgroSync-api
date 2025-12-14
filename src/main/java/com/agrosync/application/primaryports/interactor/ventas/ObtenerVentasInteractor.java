package com.agrosync.application.primaryports.interactor.ventas;

import com.agrosync.application.primaryports.dto.ventas.request.VentaPageDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentasDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerVentasInteractor extends InteractorWithReturn<VentaPageDTO, PageResponse<ObtenerVentasDTO>> {
}

package com.agrosync.application.primaryports.interactor.compras;

import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerComprasInteractor extends InteractorWithReturn<CompraPageDTO, PageResponse<ObtenerCompraDTO>> {
}

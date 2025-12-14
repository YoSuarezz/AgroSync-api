package com.agrosync.application.primaryports.interactor.compras;

import com.agrosync.application.primaryports.dto.compras.request.CompraIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDetalleDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

public interface ObtenerCompraPorIdInteractor extends InteractorWithReturn<CompraIdSuscripcionDTO, ObtenerCompraDetalleDTO> {
}

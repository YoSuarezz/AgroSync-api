package com.agrosync.application.primaryports.interactor.ventas;

import com.agrosync.application.primaryports.dto.ventas.request.VentaIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentaDetalleDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

public interface ObtenerVentaPorIdInteractor extends InteractorWithReturn<VentaIdSuscripcionDTO, ObtenerVentaDetalleDTO> {
}

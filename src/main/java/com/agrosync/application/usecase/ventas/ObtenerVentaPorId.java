package com.agrosync.application.usecase.ventas;

import com.agrosync.application.primaryports.dto.ventas.request.VentaIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.ventas.VentaDomain;

public interface ObtenerVentaPorId extends UseCaseWithReturn<VentaIdSuscripcionDTO, VentaDomain> {
}

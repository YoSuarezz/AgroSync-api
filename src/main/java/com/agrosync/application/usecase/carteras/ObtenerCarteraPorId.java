package com.agrosync.application.usecase.carteras;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.carteras.CarteraDomain;

public interface ObtenerCarteraPorId extends UseCaseWithReturn<CarteraIdSuscripcionDTO, CarteraDomain> {
}

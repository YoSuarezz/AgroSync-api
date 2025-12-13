package com.agrosync.application.usecase.abonos;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.abonos.AbonoDomain;

public interface ObtenerAbonoPorId extends UseCaseWithReturn<AbonoIdSuscripcionDTO, AbonoDomain> {
}

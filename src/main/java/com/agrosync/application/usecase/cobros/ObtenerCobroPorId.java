package com.agrosync.application.usecase.cobros;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.cobros.CobroDomain;

public interface ObtenerCobroPorId extends UseCaseWithReturn<CobroIdSuscripcionDTO, CobroDomain> {
}

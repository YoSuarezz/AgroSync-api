package com.agrosync.application.usecase.lotes;

import com.agrosync.application.primaryports.dto.lotes.request.LoteIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.lotes.LoteDomain;

public interface ObtenerLotePorId extends UseCaseWithReturn<LoteIdSuscripcionDTO, LoteDomain> {
}

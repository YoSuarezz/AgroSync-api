package com.agrosync.application.usecase.cobros;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;

import java.util.UUID;

public interface ObtenerCobroPorId extends UseCaseWithReturn<UUID[], ObtenerCobroDTO> {
}

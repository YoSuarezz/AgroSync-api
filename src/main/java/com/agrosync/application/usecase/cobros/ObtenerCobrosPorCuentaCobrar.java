package com.agrosync.application.usecase.cobros;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;

import java.util.List;
import java.util.UUID;

public interface ObtenerCobrosPorCuentaCobrar extends UseCaseWithReturn<UUID[], List<ObtenerCobroDTO>> {
}

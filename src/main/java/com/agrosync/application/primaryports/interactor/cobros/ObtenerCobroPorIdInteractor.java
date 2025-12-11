package com.agrosync.application.primaryports.interactor.cobros;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.UUID;

public interface ObtenerCobroPorIdInteractor extends InteractorWithReturn<UUID[], ObtenerCobroDTO> {
}

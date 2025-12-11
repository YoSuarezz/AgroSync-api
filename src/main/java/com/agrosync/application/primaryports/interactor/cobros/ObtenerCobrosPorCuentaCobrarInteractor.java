package com.agrosync.application.primaryports.interactor.cobros;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.List;
import java.util.UUID;

public interface ObtenerCobrosPorCuentaCobrarInteractor extends InteractorWithReturn<UUID[], List<ObtenerCobroDTO>> {
}

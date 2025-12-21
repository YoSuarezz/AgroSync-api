package com.agrosync.application.primaryports.interactor.cobros;

import com.agrosync.application.primaryports.dto.cobros.request.CobroIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.List;

public interface ObtenerCobrosPorCuentaCobrarInteractor extends InteractorWithReturn<CobroIdSuscripcionDTO, List<ObtenerCobroDTO>> {
}

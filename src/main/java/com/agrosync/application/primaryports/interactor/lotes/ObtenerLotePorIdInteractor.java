package com.agrosync.application.primaryports.interactor.lotes;

import com.agrosync.application.primaryports.dto.lotes.request.LoteIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;


public interface ObtenerLotePorIdInteractor extends InteractorWithReturn<LoteIdSuscripcionDTO, ObtenerLoteDTO> {
}

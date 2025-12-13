package com.agrosync.application.primaryports.interactor.abonos;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.List;

public interface ObtenerAbonosPorCuentaPagarInteractor extends InteractorWithReturn<AbonoIdSuscripcionDTO, List<ObtenerAbonoDTO>> {
}

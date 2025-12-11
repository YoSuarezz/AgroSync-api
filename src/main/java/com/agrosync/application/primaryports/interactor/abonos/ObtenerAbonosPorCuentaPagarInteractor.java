package com.agrosync.application.primaryports.interactor.abonos;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.List;
import java.util.UUID;

public interface ObtenerAbonosPorCuentaPagarInteractor extends InteractorWithReturn<UUID[], List<ObtenerAbonoDTO>> {
}

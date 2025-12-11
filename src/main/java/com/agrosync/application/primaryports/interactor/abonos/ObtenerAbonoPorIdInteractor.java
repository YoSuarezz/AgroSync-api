package com.agrosync.application.primaryports.interactor.abonos;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

import java.util.UUID;

public interface ObtenerAbonoPorIdInteractor extends InteractorWithReturn<UUID[], ObtenerAbonoDTO> {
}

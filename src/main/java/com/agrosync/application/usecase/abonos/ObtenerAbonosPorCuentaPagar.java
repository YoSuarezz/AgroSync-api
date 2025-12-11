package com.agrosync.application.usecase.abonos;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;

import java.util.List;
import java.util.UUID;

public interface ObtenerAbonosPorCuentaPagar extends UseCaseWithReturn<UUID[], List<ObtenerAbonoDTO>> {
}

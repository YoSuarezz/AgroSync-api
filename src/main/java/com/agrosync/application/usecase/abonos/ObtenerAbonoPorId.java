package com.agrosync.application.usecase.abonos;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;

import java.util.UUID;

public interface ObtenerAbonoPorId extends UseCaseWithReturn<UUID[], ObtenerAbonoDTO> {
}

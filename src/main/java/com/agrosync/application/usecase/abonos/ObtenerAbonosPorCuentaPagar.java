package com.agrosync.application.usecase.abonos;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.abonos.AbonoDomain;

import java.util.List;

public interface ObtenerAbonosPorCuentaPagar extends UseCaseWithReturn<AbonoIdSuscripcionDTO, List<AbonoDomain>> {
}

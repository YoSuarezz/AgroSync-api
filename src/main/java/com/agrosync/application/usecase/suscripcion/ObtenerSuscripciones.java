package com.agrosync.application.usecase.suscripcion;

import com.agrosync.application.primaryports.dto.suscripcion.request.SuscripcionPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import org.springframework.data.domain.Page;

public interface ObtenerSuscripciones extends UseCaseWithReturn<SuscripcionPageDTO, Page<SuscripcionDomain>> {
}

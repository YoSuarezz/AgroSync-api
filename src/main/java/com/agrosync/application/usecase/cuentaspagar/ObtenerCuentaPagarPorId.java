package com.agrosync.application.usecase.cuentaspagar;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;

public interface ObtenerCuentaPagarPorId extends UseCaseWithReturn<CuentaPagarIdSuscripcionDTO, CuentaPagarDomain> {
}

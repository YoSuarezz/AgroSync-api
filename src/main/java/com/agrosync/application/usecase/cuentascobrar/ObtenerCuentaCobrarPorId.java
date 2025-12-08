package com.agrosync.application.usecase.cuentascobrar;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;

public interface ObtenerCuentaCobrarPorId extends UseCaseWithReturn<CuentaCobrarIdSuscripcionDTO, CuentaCobrarDomain> {
}

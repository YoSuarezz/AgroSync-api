package com.agrosync.application.usecase.cuentaspagar;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import org.springframework.data.domain.Page;

public interface ObtenerCuentasPagar extends UseCaseWithReturn<CuentaPagarPageDTO, Page<CuentaPagarDomain>> {
}

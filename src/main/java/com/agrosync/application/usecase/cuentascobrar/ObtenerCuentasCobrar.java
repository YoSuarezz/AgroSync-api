package com.agrosync.application.usecase.cuentascobrar;


import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import org.springframework.data.domain.Page;

public interface ObtenerCuentasCobrar extends UseCaseWithReturn<CuentaCobrarPageDTO, Page<CuentaCobrarDomain>> {
}

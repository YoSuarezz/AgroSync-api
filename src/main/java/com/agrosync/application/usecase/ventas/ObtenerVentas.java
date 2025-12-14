package com.agrosync.application.usecase.ventas;

import com.agrosync.application.primaryports.dto.ventas.request.VentaPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.ventas.VentaDomain;
import org.springframework.data.domain.Page;

public interface ObtenerVentas extends UseCaseWithReturn<VentaPageDTO, Page<VentaDomain>> {
}

package com.agrosync.application.usecase.compras;

import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.compras.CompraDomain;
import org.springframework.data.domain.Page;

public interface ObtenerCompras extends UseCaseWithReturn<CompraPageDTO, Page<CompraDomain>> {
}

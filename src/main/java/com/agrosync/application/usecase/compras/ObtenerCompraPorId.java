package com.agrosync.application.usecase.compras;

import com.agrosync.application.primaryports.dto.compras.request.CompraIdSuscripcionDTO;
import com.agrosync.domain.compras.CompraDomain;

public interface ObtenerCompraPorId extends com.agrosync.application.usecase.UseCaseWithReturn<CompraIdSuscripcionDTO, CompraDomain> {
}

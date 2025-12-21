package com.agrosync.application.usecase.cobros;

import com.agrosync.application.primaryports.dto.cobros.request.CobroPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.cobros.CobroDomain;
import org.springframework.data.domain.Page;

public interface ObtenerCobros extends UseCaseWithReturn<CobroPageDTO, Page<CobroDomain>> {
}


package com.agrosync.application.usecase.lotes;

import com.agrosync.application.primaryports.dto.lotes.request.LotePageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.lotes.LoteDomain;
import org.springframework.data.domain.Page;

public interface ObtenerLotes extends UseCaseWithReturn<LotePageDTO, Page<LoteDomain>> {
}

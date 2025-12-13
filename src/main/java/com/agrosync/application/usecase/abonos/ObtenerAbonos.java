package com.agrosync.application.usecase.abonos;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.abonos.AbonoDomain;
import org.springframework.data.domain.Page;

public interface ObtenerAbonos extends UseCaseWithReturn<AbonoPageDTO, Page<AbonoDomain>> {
}

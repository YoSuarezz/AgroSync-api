package com.agrosync.application.usecase.carteras;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.carteras.CarteraDomain;
import org.springframework.data.domain.Page;

public interface ObtenerCarteras extends UseCaseWithReturn<CarteraPageDTO, Page<CarteraDomain>> {
}

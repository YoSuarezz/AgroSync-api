package com.agrosync.application.usecase.animales;

import com.agrosync.application.primaryports.dto.animales.request.AnimalPageDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.animales.AnimalDomain;
import org.springframework.data.domain.Page;

public interface ObtenerAnimales extends UseCaseWithReturn<AnimalPageDTO, Page<AnimalDomain>> {
}

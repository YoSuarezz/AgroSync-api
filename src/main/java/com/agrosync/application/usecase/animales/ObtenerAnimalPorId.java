package com.agrosync.application.usecase.animales;

import com.agrosync.application.primaryports.dto.animales.request.AnimalIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.animales.AnimalDomain;

public interface ObtenerAnimalPorId extends UseCaseWithReturn<AnimalIdSuscripcionDTO, AnimalDomain> {
}

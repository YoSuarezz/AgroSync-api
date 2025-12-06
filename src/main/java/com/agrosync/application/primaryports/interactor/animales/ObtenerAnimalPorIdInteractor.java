package com.agrosync.application.primaryports.interactor.animales;

import com.agrosync.application.primaryports.dto.animales.request.AnimalIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;

public interface ObtenerAnimalPorIdInteractor extends InteractorWithReturn<AnimalIdSuscripcionDTO, ObtenerAnimalDTO> {
}

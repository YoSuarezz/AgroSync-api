package com.agrosync.application.primaryports.interactor.animales;

import com.agrosync.application.primaryports.dto.animales.request.AnimalPageDTO;
import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;

public interface ObtenerAnimalesInteractor extends InteractorWithReturn<AnimalPageDTO, PageResponse<ObtenerAnimalDTO>> {
}

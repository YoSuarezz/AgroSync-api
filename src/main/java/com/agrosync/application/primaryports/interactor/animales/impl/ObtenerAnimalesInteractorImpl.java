package com.agrosync.application.primaryports.interactor.animales.impl;

import com.agrosync.application.primaryports.dto.animales.request.AnimalPageDTO;
import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.interactor.animales.ObtenerAnimalesInteractor;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.application.usecase.animales.ObtenerAnimales;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.infrastructure.primaryadapters.adapter.response.PageResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ObtenerAnimalesInteractorImpl implements ObtenerAnimalesInteractor {

    private final ObtenerAnimales obtenerAnimales;

    public ObtenerAnimalesInteractorImpl(ObtenerAnimales obtenerAnimales) {
        this.obtenerAnimales = obtenerAnimales;
    }

    @Override
    public PageResponse<ObtenerAnimalDTO> ejecutar(AnimalPageDTO data) {
        Page<AnimalDomain> resultados = obtenerAnimales.ejecutar(data);
        Page<ObtenerAnimalDTO> dtoPage = AnimalDTOMapper.INSTANCE.toObtenerDTOCollection(resultados);
        return PageResponse.from(dtoPage);
    }
}

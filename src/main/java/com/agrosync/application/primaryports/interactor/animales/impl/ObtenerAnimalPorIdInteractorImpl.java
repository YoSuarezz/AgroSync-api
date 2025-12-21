package com.agrosync.application.primaryports.interactor.animales.impl;

import com.agrosync.application.primaryports.dto.animales.request.AnimalIdSuscripcionDTO;
import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.primaryports.interactor.animales.ObtenerAnimalPorIdInteractor;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.application.usecase.animales.ObtenerAnimalPorId;
import com.agrosync.domain.animales.AnimalDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerAnimalPorIdInteractorImpl implements ObtenerAnimalPorIdInteractor {

    private final ObtenerAnimalPorId obtenerAnimalPorId;

    public ObtenerAnimalPorIdInteractorImpl(ObtenerAnimalPorId obtenerAnimalPorId) {
        this.obtenerAnimalPorId = obtenerAnimalPorId;
    }

    @Override
    public ObtenerAnimalDTO ejecutar(AnimalIdSuscripcionDTO data) {
        AnimalDomain resultado = obtenerAnimalPorId.ejecutar(data);
        return AnimalDTOMapper.INSTANCE.toObtenerDTO(resultado);
    }
}

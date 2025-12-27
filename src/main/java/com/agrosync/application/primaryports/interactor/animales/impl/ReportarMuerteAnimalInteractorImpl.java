package com.agrosync.application.primaryports.interactor.animales.impl;

import com.agrosync.application.primaryports.dto.animales.request.ReportarMuerteAnimalDTO;
import com.agrosync.application.primaryports.interactor.animales.ReportarMuerteAnimalInteractor;
import com.agrosync.application.primaryports.mapper.animales.AnimalDTOMapper;
import com.agrosync.application.usecase.animales.ReportarMuerteAnimal;
import com.agrosync.domain.animales.AnimalDomain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportarMuerteAnimalInteractorImpl implements ReportarMuerteAnimalInteractor {

    private final ReportarMuerteAnimal reportarMuerteAnimal;

    public ReportarMuerteAnimalInteractorImpl(ReportarMuerteAnimal reportarMuerteAnimal) {
        this.reportarMuerteAnimal = reportarMuerteAnimal;
    }

    @Override
    public void ejecutar(ReportarMuerteAnimalDTO data) {
        AnimalDomain animalDomain = AnimalDTOMapper.INSTANCE.toDomain(data);
        reportarMuerteAnimal.ejecutar(animalDomain);
    }
}

package com.agrosync.application.usecase.animales.impl;

import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.usecase.animales.ReportarMuerteAnimal;
import com.agrosync.application.usecase.animales.rulesvalidator.ReportarMuerteAnimalRulesValidator;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.exceptions.IdentificadorAnimalNoExisteException;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportarMuerteAnimalImpl implements ReportarMuerteAnimal {

    private final AnimalRepository animalRepository;
    private final ReportarMuerteAnimalRulesValidator reportarMuerteAnimalRulesValidator;

    public ReportarMuerteAnimalImpl(AnimalRepository animalRepository,
                                    ReportarMuerteAnimalRulesValidator reportarMuerteAnimalRulesValidator) {
        this.animalRepository = animalRepository;
        this.reportarMuerteAnimalRulesValidator = reportarMuerteAnimalRulesValidator;
    }

    @Override
    public void ejecutar(AnimalDomain data) {
        reportarMuerteAnimalRulesValidator.validar(data);

        AnimalEntity animal = animalRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorAnimalNoExisteException::create);

        animal.setEstado(EstadoAnimalEnum.MUERTO);
        animalRepository.save(animal);
    }
}

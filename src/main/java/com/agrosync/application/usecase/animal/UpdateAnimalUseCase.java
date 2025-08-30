package com.agrosync.application.usecase.animal;

import com.agrosync.application.primaryports.mapper.AnimalMapper;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.AnimalDomain;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.entity.AnimalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdateAnimalUseCase implements UseCaseWithReturn<AnimalDomain, AnimalDomain> {

    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Override
    public AnimalDomain ejecutar(AnimalDomain animalDomain) {

        if (animalDomain.getPeso() == null || animalDomain.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessAgroSyncException("El peso debe ser un número positivo");
        }

        if (!animalRepository.existsById(animalDomain.getId())) {
            throw new BusinessAgroSyncException("El animal no existe");
        }

        if(animalDomain.getPrecioKiloCompra().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessAgroSyncException("El precio por kilo no puede ser negativo");
        }

        if (!Objects.equals(animalDomain.getSexo(), "macho") && !Objects.equals(animalDomain.getSexo(), "hembra")) {
            throw new BusinessAgroSyncException("El sexo debe ser macho o hembra");
        }

        if (animalDomain.getIdLote() == null) {
            throw new BusinessAgroSyncException("El animal debe estar asociado a un lote");
        }

        if (animalDomain.getSlot() == null || animalDomain.getSlot() <= 0 || animalDomain.getSlot() > 25) {
            throw new BusinessAgroSyncException("El slot debe estar entre 1 y 25");
        }

        Optional<AnimalEntity> ocupado = animalRepository.findByLoteIdAndSlot(animalDomain.getIdLote(), animalDomain.getSlot());
        if (ocupado.isPresent() && !ocupado.get().getId().equals(animalDomain.getId())) {
            throw new BusinessAgroSyncException(
                    String.format("El slot %d ya está ocupado en el lote %d", animalDomain.getSlot(), animalDomain.getIdLote())
            );
        }

        AnimalEntity animalEntity = animalMapper.toEntity(animalDomain);
        AnimalEntity animalUpdated = animalRepository.save(animalEntity);
        return animalMapper.toDomain(animalUpdated);
    }
}
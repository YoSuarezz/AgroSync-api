package com.agrosync.application.usecase.animal;

import com.agrosync.application.primaryports.mapper.AnimalMapper;
import com.agrosync.application.primaryports.mapper.LoteMapper;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.model.AnimalDomain;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.secondaryports.entity.AnimalEntity;
import com.agrosync.application.secondaryports.entity.LoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateAnimalUseCase implements UseCaseWithReturn<AnimalDomain, AnimalDomain> {

    private final AnimalRepository animalRepository;
    private final LoteRepository loteRepository;
    private final AnimalMapper animalMapper;
    private final LoteMapper loteMapper;

    @Override
    public AnimalDomain ejecutar(AnimalDomain animalDomain) {

        if (animalDomain.getPeso() == null || animalDomain.getPeso().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessAgroSyncException("El peso debe ser un número positivo");
        }

        if (animalDomain.getPrecioKiloCompra().compareTo(BigDecimal.ZERO) <= 0) {
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

        if (animalRepository.existsByLoteIdAndSlot(animalDomain.getIdLote(), animalDomain.getSlot())) {
            throw new BusinessAgroSyncException(
                    String.format("Ya existe un animal en el lote %d con slot %d", animalDomain.getIdLote(), animalDomain.getSlot())
            );
        }

        // Convertimos el dominio a entidad y la guardamos
        AnimalEntity animalEntity = new AnimalEntity();

        Optional<LoteEntity> optionalLote = loteRepository.findById(animalDomain.getIdLote());
        if (optionalLote.isPresent()) {
            LoteEntity lote = optionalLote.get();
            animalEntity.setLote(lote);
            // Ya puedes usar lote aquí
        } else {
            // El lote no existe
            throw new RuntimeException("No se encontró el lote con id " + animalDomain.getIdLote());
        }



        animalEntity.setId(animalDomain.getId());
        animalEntity.setVenta(null);
        animalEntity.setPeso(animalDomain.getPeso());
        animalEntity.setSexo(animalDomain.getSexo());
        animalEntity.setSlot(animalDomain.getSlot());
        animalEntity.setPrecioKiloCompra(animalDomain.getPrecioKiloCompra());
        animalEntity.setPrecioKiloVenta(animalDomain.getPrecioKiloVenta());

        AnimalEntity animalSaved = animalRepository.save(animalEntity);
        return animalMapper.toDomain(animalSaved);
    }
}
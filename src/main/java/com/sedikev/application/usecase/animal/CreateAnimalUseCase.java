package com.sedikev.application.usecase.animal;

import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
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
            throw new BusinessSedikevException("El peso debe ser un número positivo");
        }

        if (animalDomain.getPrecioKiloCompra().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessSedikevException("El precio por kilo no puede ser negativo");
        }

        if (!Objects.equals(animalDomain.getSexo(), "macho") && !Objects.equals(animalDomain.getSexo(), "hembra")) {
            throw new BusinessSedikevException("El sexo debe ser macho o hembra");
        }

        if (animalDomain.getIdLote() == null) {
            throw new BusinessSedikevException("El animal debe estar asociado a un lote");
        }

        if (animalDomain.getSlot() == null || animalDomain.getSlot() <= 0 || animalDomain.getSlot() > 25) {
            throw new BusinessSedikevException("El slot debe estar entre 1 y 25");
        }

        if (animalRepository.existsByLoteIdAndSlot(animalDomain.getIdLote(), animalDomain.getSlot())) {
            throw new BusinessSedikevException(
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
package com.sedikev.application.usecase.lote;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.application.usecase.animal.CreateAnimalUseCase;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateLoteUseCase implements UseCaseWithReturn<LoteDomain, LoteDomain> {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;
    private final CreateAnimalUseCase createAnimalUseCase;

    @Override
    public LoteDomain ejecutar(LoteDomain loteDomain) {

        // Validación de lote
        if (loteDomain.getPrecio_kilo().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessSedikevException("El precio por kilo no puede ser negativo");
        }

        if (loteRepository.existsById(loteDomain.getId())) {
            throw new BusinessSedikevException("El lote ya existe");
        }

        if (loteDomain.getUsuario() == null || loteDomain.getUsuario().getId() == null) {
            throw new BusinessSedikevException("El lote debe estar asociado a un usuario");
        }

        if (loteDomain.getContramarca() <= 0) {
            throw new BusinessSedikevException("La contramarca debe ser mayor que cero");
        }

        // Validación y preparación de animales
        if (loteDomain.getAnimales() == null || loteDomain.getAnimales().isEmpty()) {
            throw new BusinessSedikevException("El lote debe tener al menos un animal");
        }

        int slot = 1;
        for (AnimalDomain animal : loteDomain.getAnimales()) {
            animal.setNum_lote(slot++);
            createAnimalUseCase.validarAnimal(animal); // solo validar, no guardar
        }

        // Guardar en cascada
        LoteEntity loteEntity = loteMapper.toEntity(loteDomain);
        LoteEntity loteSaved = loteRepository.save(loteEntity);

        return loteMapper.toDomain(loteSaved);
    }
}

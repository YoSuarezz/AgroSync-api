package com.sedikev.application.usecase.lote;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.application.secondaryports.repository.LoteRepository;
import com.sedikev.application.primaryports.mapper.LoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetLoteByIdUseCase implements UseCaseWithReturn<Long, LoteDomain> {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    @Override
    public LoteDomain ejecutar(Long id) {
        // Buscar el lote por ID, mapear la entidad al dominio y lanzar excepción si no existe
        return loteRepository.findById(id)
                .map(loteMapper::toDomain)
                .orElseThrow(() -> new BusinessSedikevException("Lote no encontrado con ID: " + id));
    }
}

package com.sedikev.application.usecase.pago;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.PagoDomain;
import com.sedikev.application.secondaryports.repository.PagoRepository;
import com.sedikev.application.primaryports.mapper.PagoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPagoByIdUseCase implements UseCaseWithReturn<Long, PagoDomain> {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    @Override
    public PagoDomain ejecutar(Long id) {
        // Buscar el pago por ID, mapear la entidad al dominio y lanzar excepción si no existe
        return pagoRepository.findById(id)
                .map(pagoMapper::toDomain)
                .orElseThrow(() -> new BusinessSedikevException("Pago no encontrado con ID: " + id));
    }
}

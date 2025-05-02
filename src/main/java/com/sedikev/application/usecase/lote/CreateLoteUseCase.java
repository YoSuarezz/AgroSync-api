package com.sedikev.application.usecase.lote;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.temporal.WeekFields;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateLoteUseCase implements UseCaseWithReturn<LoteDomain, LoteDomain> {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    @Override
    public LoteDomain ejecutar(LoteDomain loteDomain) {

        if (loteDomain.getUsuario() == null || loteDomain.getUsuario().getId() == null) {
            throw new BusinessSedikevException("El lote debe estar asociado a un usuario");
        }

        if (loteDomain.getPrecioTotal() == null || loteDomain.getPrecioTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessSedikevException("El precio total debe ser mayor que cero");
        }

        if (loteDomain.getContramarca() <= 0 || loteDomain.getContramarca() == null ) {
            throw new BusinessSedikevException("La contramarca no puede ser 0 o nula");
        }

        Optional<LoteEntity> lote = loteRepository.findByContramarcaAndSemana(loteDomain.getContramarca(), loteDomain.getFecha().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear()));
        if(lote.isPresent()) {
            throw new BusinessSedikevException("Ya existe un lote con esa contramarca en esta semana");
        }

        // Guardar
        LoteEntity loteEntity = loteMapper.toEntity(loteDomain);
        LoteEntity loteSaved = loteRepository.save(loteEntity);

        return loteMapper.toDomain(loteSaved);
    }
}
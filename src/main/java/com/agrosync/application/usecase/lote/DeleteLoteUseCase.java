package com.agrosync.application.usecase.lote;

import com.agrosync.application.primaryports.mapper.LoteMapper;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.domain.model.LoteDomain;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.usecase.UseCaseWithoutReturn;
import com.agrosync.domain.service.CarteraService;
import com.agrosync.application.secondaryports.entity.LoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteLoteUseCase implements UseCaseWithoutReturn<Long> {

    private final LoteRepository loteRepository;
    private final CarteraService carteraService;  // ← inyectamos
    private final LoteMapper loteMapper;          // ← para convertir a domain

    @Override
    public void ejecutar(Long loteId) {
        // 1) Verificar que exista
        LoteEntity entidad = loteRepository.findById(loteId)
                .orElseThrow(() -> new BusinessAgroSyncException("El lote no existe"));

        // 2) Ajustar cartera: devolver el precioTotal
        LoteDomain lote = loteMapper.toDomain(entidad);
        Long proveedorId = lote.getUsuario().getId();
        CarteraDomain cartera = carteraService.findByUserId(proveedorId);

        // Sumamos el precioTotal que íbamos a “devolver”
        cartera.setSaldo(cartera.getSaldo().add(lote.getPrecioTotal()));
        carteraService.update(cartera);

        // 3) Finalmente, borrar el lote
        loteRepository.deleteById(loteId);
    }
}
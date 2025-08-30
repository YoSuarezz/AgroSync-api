package com.agrosync.application.usecase.pago;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.PagoDomain;
import com.agrosync.application.secondaryports.repository.PagoRepository;
import com.agrosync.application.primaryports.mapper.PagoMapper;
import com.agrosync.application.secondaryports.entity.PagoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UpdatePagoUseCase implements UseCaseWithReturn<PagoDomain, PagoDomain> {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    @Override
    public PagoDomain ejecutar(PagoDomain pagoDomain) {
        // Validación de negocio: Verificar que el pago exista
        if (!pagoRepository.existsById(pagoDomain.getId())) {
            throw new BusinessAgroSyncException("El pago no existe");
        }
        // Validación de negocio: Verificar que la cantidad no sea negativa
        if (pagoDomain.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessAgroSyncException("La cantidad no puede ser negativa");
        }
        if (pagoDomain.getVenta() == null || pagoDomain.getVenta().getId() == null) {
            throw new BusinessAgroSyncException("El pago debe estar asociado a un venta");
        }
        if (!(Objects.equals(pagoDomain.getTipo_pago(), "venta") || Objects.equals(pagoDomain.getTipo_pago(), "abono")
                || Objects.equals(pagoDomain.getTipo_pago(), "credito"))) {
            throw new BusinessAgroSyncException("El tipo de pago debe ser venta, abono o credito");
        }
        if (pagoDomain.getCantidad() == null || pagoDomain.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessAgroSyncException("La cantidad debe ser un número positivo");
        }

        // Mapear y actualizar el pago
        PagoEntity pagoEntity = pagoMapper.toEntity(pagoDomain);
        PagoEntity pagoUpdated = pagoRepository.save(pagoEntity);
        return pagoMapper.toDomain(pagoUpdated);
    }
}

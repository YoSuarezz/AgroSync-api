package com.sedikev.application.usecase.pago;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.PagoDomain;
import com.sedikev.application.secondaryports.repository.PagoRepository;
import com.sedikev.application.primaryports.mapper.PagoMapper;
import com.sedikev.application.secondaryports.entity.PagoEntity;
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
            throw new BusinessSedikevException("El pago no existe");
        }
        // Validación de negocio: Verificar que la cantidad no sea negativa
        if (pagoDomain.getCantidad().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessSedikevException("La cantidad no puede ser negativa");
        }
        if (pagoDomain.getVenta() == null || pagoDomain.getVenta().getId() == null) {
            throw new BusinessSedikevException("El pago debe estar asociado a un venta");
        }
        if (!(Objects.equals(pagoDomain.getTipo_pago(), "venta") || Objects.equals(pagoDomain.getTipo_pago(), "abono")
                || Objects.equals(pagoDomain.getTipo_pago(), "credito"))) {
            throw new BusinessSedikevException("El tipo de pago debe ser venta, abono o credito");
        }
        if (pagoDomain.getCantidad() == null || pagoDomain.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessSedikevException("La cantidad debe ser un número positivo");
        }

        // Mapear y actualizar el pago
        PagoEntity pagoEntity = pagoMapper.toEntity(pagoDomain);
        PagoEntity pagoUpdated = pagoRepository.save(pagoEntity);
        return pagoMapper.toDomain(pagoUpdated);
    }
}

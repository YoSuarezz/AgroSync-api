package com.sedikev.application.usecase.venta;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.application.secondaryports.repository.VentaRepository;
import com.sedikev.application.primaryports.mapper.VentaMapper;
import com.sedikev.application.secondaryports.entity.VentaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateVentaUseCase implements UseCaseWithReturn<VentaDomain, VentaDomain> {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;

    @Override
    public VentaDomain ejecutar(VentaDomain ventaDomain) {
        // Validación de negocio: Verificar que la venta exista
        if (!ventaRepository.existsById(ventaDomain.getId())) {
            throw new BusinessSedikevException("La venta no existe");
        }

        // Validación de negocio: Verificar que la venta tenga un usuario relacionado
        if (ventaDomain.getUsuario() == null || ventaDomain.getUsuario().getId() == null) {
            throw new BusinessSedikevException("La venta debe tener un usuario relacionado");
        }

        // Validación de negocio: Verificar que el estado sea "pagado" o "no pagado"
        if (!ventaDomain.getEstado().equalsIgnoreCase("pagado") &&
                !ventaDomain.getEstado().equalsIgnoreCase("no pagado")) {
            throw new BusinessSedikevException("El estado debe ser 'pagado' o 'no pagado'");
        }

        // Validación de negocio: Verificar que el precio de la venta sea mayor que 0
        if (ventaDomain.getPrecioVenta() == null || ventaDomain.getPrecioVenta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessSedikevException("El precio de venta debe ser mayor que 0");
        }

        // Mapear y actualizar la venta
        VentaEntity ventaEntity = ventaMapper.toEntity(ventaDomain);
        VentaEntity ventaUpdated = ventaRepository.save(ventaEntity);
        return ventaMapper.toDomain(ventaUpdated);
    }
}

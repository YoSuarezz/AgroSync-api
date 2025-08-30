package com.agrosync.application.usecase.venta;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.domain.model.VentaDomain;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.primaryports.mapper.VentaMapper;
import com.agrosync.domain.service.CarteraService;
import com.agrosync.application.secondaryports.entity.VentaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CreateVentaUseCase implements UseCaseWithReturn<VentaDomain, VentaDomain> {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final CarteraService carteraService;

    @Override
    public VentaDomain ejecutar(VentaDomain ventaDomain) {

        // Validación de negocio: Verificar que la venta tenga un usuario relacionado
        if (ventaDomain.getUsuario() == null || ventaDomain.getUsuario().getId() == null) {
            throw new BusinessAgroSyncException("La venta debe tener un usuario relacionado");
        }

        // Validación de negocio: Verificar que el estado sea "pagado" o "no pagado"
        if (!ventaDomain.getEstado().equalsIgnoreCase("pagado") &&
                !ventaDomain.getEstado().equalsIgnoreCase("no pagado")) {
            throw new BusinessAgroSyncException("El estado debe ser 'pagado' o 'no pagado'");
        }

        // Validación de negocio: Verificar que el precio de la venta sea mayor que 0
        if (ventaDomain.getPrecioVenta() == null || ventaDomain.getPrecioVenta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessAgroSyncException("El precio de venta debe ser mayor que 0");
        }

        CarteraDomain cartera = carteraService.findByUserId(ventaDomain.getUsuario().getId());
        BigDecimal saldo = cartera.getSaldo().subtract(ventaDomain.getPrecioVenta());
        cartera.setSaldo(saldo);
        carteraService.update(cartera);

        // Mapear y guardar la venta
        VentaEntity ventaEntity = ventaMapper.toEntity(ventaDomain);
        VentaEntity ventaSaved = ventaRepository.save(ventaEntity);
        return ventaMapper.toDomain(ventaSaved);
    }
}
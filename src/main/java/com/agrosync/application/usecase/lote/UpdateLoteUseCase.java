package com.agrosync.application.usecase.lote;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.domain.model.LoteDomain;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.primaryports.mapper.LoteMapper;
import com.agrosync.domain.service.CarteraService;
import com.agrosync.application.secondaryports.entity.LoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateLoteUseCase implements UseCaseWithReturn<LoteDomain, LoteDomain> {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;
    private final CarteraService carteraService;

    @Override
    public LoteDomain ejecutar(LoteDomain loteDomain) {

        // 1) Cargamos el estado previo del lote (para saber diferencia)
        LoteEntity antes = loteRepository.findById(loteDomain.getId())
                .orElseThrow(() -> new BusinessAgroSyncException("Lote no existe"));
        BigDecimal precioViejo = antes.getPrecioTotal();

        // Validación de negocio: Verificar que el lote exista
        if (!loteRepository.existsById(loteDomain.getId())) {
            throw new BusinessAgroSyncException("El lote no existe");
        }
        // Validación de negocio: Verificar que el precio total no sea negativo
        if (loteDomain.getPrecioTotal() == null || loteDomain.getPrecioTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessAgroSyncException("El precio total debe ser mayor que cero");
        }
        if (loteDomain.getUsuario() == null || loteDomain.getUsuario().getId() == null) {
            throw new BusinessAgroSyncException("El lote debe estar asociado a un proveedor");
        }
        if (loteDomain.getContramarca() <= 0 || loteDomain.getContramarca() == null ) {
            throw new BusinessAgroSyncException("La contramarca no puede ser 0 o nula");
        }

        if (loteRepository.existsByContramarcaAndIdNot(loteDomain.getContramarca(), loteDomain.getId()))
            throw new BusinessAgroSyncException("Ya existe otro lote con esa contramarca");

        // Mapear y actualizar el lote
        LoteEntity loteEntity = loteMapper.toEntity(loteDomain);
        LoteEntity loteUpdated = loteRepository.save(loteEntity);

        Long proveedorId = loteUpdated.getUsuario().getId();
        CarteraDomain cartera = carteraService.findByUserId(proveedorId);
        // Calculamos la diferencia: nuevo − viejo
        BigDecimal diff = loteUpdated.getPrecioTotal().subtract(precioViejo);
        // Si aumenta el gasto, restamos más; si disminuye, "devolvemos" saldo
        cartera.setSaldo(cartera.getSaldo().subtract(diff));
        carteraService.update(cartera);
        return loteMapper.toDomain(loteUpdated);
    }
}

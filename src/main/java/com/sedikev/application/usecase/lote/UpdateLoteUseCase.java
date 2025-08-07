package com.sedikev.application.usecase.lote;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.domain.service.CarteraService;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
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
                .orElseThrow(() -> new BusinessSedikevException("Lote no existe"));
        BigDecimal precioViejo = antes.getPrecioTotal();

        // Validación de negocio: Verificar que el lote exista
        if (!loteRepository.existsById(loteDomain.getId())) {
            throw new BusinessSedikevException("El lote no existe");
        }
        // Validación de negocio: Verificar que el precio total no sea negativo
        if (loteDomain.getPrecioTotal() == null || loteDomain.getPrecioTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessSedikevException("El precio total debe ser mayor que cero");
        }
        if (loteDomain.getUsuario() == null || loteDomain.getUsuario().getId() == null) {
            throw new BusinessSedikevException("El lote debe estar asociado a un proveedor");
        }
        if (loteDomain.getContramarca() <= 0 || loteDomain.getContramarca() == null ) {
            throw new BusinessSedikevException("La contramarca no puede ser 0 o nula");
        }

        if (loteRepository.existsByContramarcaAndIdNot(loteDomain.getContramarca(), loteDomain.getId()))
            throw new BusinessSedikevException("Ya existe otro lote con esa contramarca");

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

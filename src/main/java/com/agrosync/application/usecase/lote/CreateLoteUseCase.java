package com.agrosync.application.usecase.lote;

import com.agrosync.application.primaryports.mapper.UsuarioMapper;
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
import java.time.temporal.WeekFields;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateLoteUseCase implements UseCaseWithReturn<LoteDomain, LoteDomain> {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;
    private final CarteraService carteraService;
    private final UsuarioMapper usuarioMapper;

    @Override
    public LoteDomain ejecutar(LoteDomain loteDomain) {

        if (loteDomain.getUsuario() == null || loteDomain.getUsuario().getId() == null) {
            throw new BusinessAgroSyncException("El lote debe estar asociado a un usuario");
        }

        if (loteDomain.getPrecioTotal() == null || loteDomain.getPrecioTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessAgroSyncException("El precio total debe ser mayor que cero");
        }

        if (loteDomain.getContramarca() <= 0 || loteDomain.getContramarca() == null ) {
            throw new BusinessAgroSyncException("La contramarca no puede ser 0 o nula");
        }

        Optional<LoteEntity> lote = loteRepository.findByContramarcaAndSemana(loteDomain.getContramarca(), loteDomain.getFecha().get(WeekFields.SUNDAY_START.weekOfWeekBasedYear()));
        if(lote.isPresent()) {
            throw new BusinessAgroSyncException("Ya existe un lote con esa contramarca en esta semana");
        }

        // Guardar
        LoteEntity loteEntity = loteMapper.toEntity(loteDomain);
        LoteEntity loteSaved = loteRepository.save(loteEntity);

        // 2) Ajustar cartera del proveedor
        Long proveedorId = loteSaved.getUsuario().getId();
        CarteraDomain cartera;
        try {
            cartera = carteraService.findByUserId(proveedorId);
        } catch (BusinessAgroSyncException ex) {
            // si no existía, la creamos con saldo 0
            cartera = new CarteraDomain();
            // Asumiendo que existe un usuarioMapper
            cartera.setUsuario(usuarioMapper.toDomain(loteSaved.getUsuario()));            cartera.setSaldo(BigDecimal.ZERO);
            cartera = carteraService.save(cartera);
        }
        // Restamos porque es un gasto para nosotros
        cartera.setSaldo(cartera.getSaldo().subtract(loteSaved.getPrecioTotal()));
        carteraService.update(cartera);

        return loteMapper.toDomain(loteSaved);
    }
}
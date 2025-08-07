package com.agrosync.application.primaryports.service;

import com.agrosync.application.primaryports.mapper.LoteMapper;
import com.agrosync.application.usecase.lote.*;
import com.agrosync.domain.model.LoteDomain;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.domain.service.LoteService;
import com.agrosync.application.secondaryports.entity.LoteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoteFacadeImpl implements LoteService {

    private final CreateLoteUseCase createLoteUseCase;
    private final UpdateLoteUseCase updateLoteUseCase;
    private final DeleteLoteUseCase deleteLoteUseCase;
    private final GetLoteByIdUseCase getLoteByIdUseCase;
    private final GetAllLotesUseCase getAllLotesUseCase;
    private final GetLotesByProveedorIdUseCase getLotesByProveedorIdUseCase;


    @Autowired private LoteRepository loteRepository;
    @Autowired private LoteMapper loteMapper;

    @Override
    public LoteDomain save(LoteDomain loteDomain) {
        return createLoteUseCase.ejecutar(loteDomain);
    }

    @Override
    @Transactional
    public LoteDomain update(LoteDomain loteDomain) {
        System.out.println("Lote domain: " + loteDomain);
        // Primero verifica que el lote existe
        LoteEntity entity = loteRepository.findById(loteDomain.getId())
                .orElseThrow(() -> new RuntimeException("Lote no encontrado para actualizar"));

        // Actualiza solo los campos modificables
        entity.setContramarca(loteDomain.getContramarca());
        entity.setFecha(loteDomain.getFecha());

        System.out.println("Entity :" + entity);
        // No actualices relaciones directamente aquí
        return loteMapper.toDomain(loteRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LoteDomain findById(Long id) {
        return getLoteByIdUseCase.ejecutar(id);
    }

    @Override
    public void deleteById(Long id) {
        deleteLoteUseCase.ejecutar(id);
    }

    @Override
    public List<LoteDomain> findAll() {
        return getAllLotesUseCase.ejecutar(null);
    }
  
    @Override
    public Optional<LoteEntity> findByContramarcaAndSemana(Integer contramarca, Integer semana) {
        return loteRepository.findByContramarcaAndSemana(contramarca, semana);
    }
    
    @Override
    public List<LoteDomain> findByProveedorId(Long proveedorId) {
        return getLotesByProveedorIdUseCase.ejecutar(proveedorId);
    }
}

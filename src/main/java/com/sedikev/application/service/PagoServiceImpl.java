package com.sedikev.application.service;

import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.model.PagoDomain;
import com.sedikev.application.mapper.PagoMapper;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
import com.sedikev.infrastructure.adapter.entity.PagoEntity;
import com.sedikev.domain.repository.PagoRepository;
import com.sedikev.domain.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final PagoMapper pagoMapper;

    @Transactional
    public PagoDomain save(PagoDomain pagoDomain) {
        PagoEntity pagoEntity = pagoMapper.toEntity(pagoDomain);
        PagoEntity pagoSaved = pagoRepository.save(pagoEntity);
        return pagoMapper.toDomain(pagoSaved);
    }

    @Transactional
    public LoteDomain update(LoteDomain loteDomain) {
        LoteEntity loteEntity = loteMapper.toEntity(loteDomain);
        LoteEntity loteSaved = loteRepository.save(loteEntity);
        return loteMapper.toDomain(loteSaved);
    }


    @Transactional(readOnly = true)
    public PagoDomain findById(Long id) {
        return pagoMapper.toDomain(pagoRepository.findById(id).orElse(null));
    }

    @Transactional
    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PagoDomain> findAll() {
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toDomain)
                .collect(Collectors.toList());
    }
}

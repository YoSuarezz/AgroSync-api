package com.sedikev.application.service;

import com.sedikev.domain.model.LoteDomain;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.domain.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoteServiceImpl implements LoteService {

    private final LoteRepository loteRepository;
    private final LoteMapper loteMapper;

    @Transactional
    public LoteDomain save(LoteDomain loteDomain) {
        LoteEntity loteEntity = loteMapper.toEntity(loteDomain);
        LoteEntity loteSaved = loteRepository.save(loteEntity);
        return loteMapper.toDomain(loteSaved);
    }

    @Transactional
    public LoteDomain update(LoteDomain loteDomain) {
        LoteEntity loteEntity = loteMapper.toEntity(loteDomain);
        LoteEntity loteSaved = loteRepository.save(loteEntity);
        return loteMapper.toDomain(loteSaved);
    }


    @Transactional(readOnly = true)
    public LoteDomain findById(Long id) {
        return loteMapper.toDomain(loteRepository.findById(id).orElse(null));
    }

    @Transactional
    public void deleteById(Long id) {
        loteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LoteDomain> findAll() {
        return loteRepository.findAll().stream()
                .map(loteMapper::toDomain)
                .collect(Collectors.toList());
    }
}

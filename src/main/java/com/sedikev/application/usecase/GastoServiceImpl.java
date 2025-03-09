package com.sedikev.application.usecase;

import com.sedikev.domain.model.GastoDomain;
import com.sedikev.application.mapper.GastoMapper;
import com.sedikev.infrastructure.adapter.entity.GastoEntity;
import com.sedikev.domain.repository.GastoRepository;
import com.sedikev.domain.service.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GastoServiceImpl implements GastoService {

    @Autowired
    private final GastoRepository gastoRepository;

    @Autowired
    private final GastoMapper gastoMapper;

    @Transactional
    public GastoDomain save(GastoDomain gastoDomain) {
        GastoEntity gastoEntity = gastoMapper.toEntity(gastoDomain);
        GastoEntity gastoSaved = gastoRepository.save(gastoEntity);
        return gastoMapper.toDomain(gastoSaved);
    }

    @Transactional(readOnly = true)
    public GastoDomain findById(Long id) {
        return gastoMapper.toDomain(gastoRepository.findById(id).orElse(null));
    }

    @Transactional
    public void deleteById(Long id) {
        gastoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<GastoDomain> findAll() {
        return gastoRepository.findAll().stream()
                .map(gastoMapper::toDomain)
                .collect(Collectors.toList());
    }
}

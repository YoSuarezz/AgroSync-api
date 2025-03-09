package com.sedikev.application.usecase;

import com.sedikev.application.domain.VentaDomain;
import com.sedikev.application.mapper.VentaMapper;
import com.sedikev.domain.entity.VentaEntity;
import com.sedikev.domain.repository.VentaRepository;
import com.sedikev.domain.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    @Autowired
    private final VentaRepository ventaRepository;

    @Autowired
    private final VentaMapper ventaMapper;

    @Transactional
    public VentaDomain save(VentaDomain ventaDomain) {
        VentaEntity ventaEntity = ventaMapper.toEntity(ventaDomain);
        VentaEntity ventaSaved = ventaRepository.save(ventaEntity);
        return ventaMapper.toDomain(ventaSaved);
    }

    @Transactional(readOnly = true)
    public VentaDomain findById(Long id) {
        return ventaMapper.toDomain(ventaRepository.findById(id).orElse(null));
    }

    @Transactional
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<VentaDomain> findAll() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDomain)
                .collect(Collectors.toList());
    }
}

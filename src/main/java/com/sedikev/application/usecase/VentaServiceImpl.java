package com.sedikev.application.usecase;

import com.sedikev.domain.entity.VentaEntity;
import com.sedikev.domain.repository.VentaRepository;
import com.sedikev.domain.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Transactional
    public VentaEntity save(VentaEntity ventaEntity) {
        return ventaRepository.save(ventaEntity);
    }

    @Transactional(readOnly = true)
    public VentaEntity findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<VentaEntity> findAll() {
        return (List<VentaEntity>) ventaRepository.findAll();
    }
}

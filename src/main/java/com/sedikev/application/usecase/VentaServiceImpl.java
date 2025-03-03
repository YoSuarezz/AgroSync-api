package com.sedikev.application.usecase;

import com.sedikev.domain.entity.Venta;
import com.sedikev.domain.repository.VentaRepository;
import com.sedikev.domain.service.VentaService;
import com.sedikev.infrastructure.persistence.VentaRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Transactional
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Transactional(readOnly = true)
    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Venta> findAll() {
        return (List<Venta>) ventaRepository.findAll();
    }
}

package com.sedikev.application.usecase;

import com.sedikev.domain.entity.Pago;
import com.sedikev.domain.repository.PagoRepository;
import com.sedikev.domain.service.PagoService;
import com.sedikev.infrastructure.persistence.PagoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Transactional(readOnly = true)
    public Pago findById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Pago> findAll() {
        return (List<Pago>) pagoRepository.findAll();
    }
}

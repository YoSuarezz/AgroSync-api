package com.sedikev.application.usecase;

import com.sedikev.domain.entity.PagoEntity;
import com.sedikev.domain.repository.PagoRepository;
import com.sedikev.domain.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public PagoEntity save(PagoEntity pagoEntity) {
        return pagoRepository.save(pagoEntity);
    }

    @Transactional(readOnly = true)
    public PagoEntity findById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PagoEntity> findAll() {
        return (List<PagoEntity>) pagoRepository.findAll();
    }
}

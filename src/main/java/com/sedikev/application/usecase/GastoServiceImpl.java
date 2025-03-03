package com.sedikev.application.usecase;

import com.sedikev.domain.entity.Gasto;
import com.sedikev.domain.repository.GastoRepository;
import com.sedikev.domain.service.GastoService;
import com.sedikev.infrastructure.persistence.GastoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GastoServiceImpl implements GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Transactional
    public Gasto save(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    @Transactional(readOnly = true)
    public Gasto findById(Long id) {
        return gastoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        gastoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Gasto> findAll() {
        return (List<Gasto>) gastoRepository.findAll();
    }
}

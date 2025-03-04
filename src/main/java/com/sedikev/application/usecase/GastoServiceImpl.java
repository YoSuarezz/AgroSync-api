package com.sedikev.application.usecase;

import com.sedikev.domain.entity.GastoEntity;
import com.sedikev.domain.repository.GastoRepository;
import com.sedikev.domain.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GastoServiceImpl implements GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Transactional
    public GastoEntity save(GastoEntity gastoEntity) {
        return gastoRepository.save(gastoEntity);
    }

    @Transactional(readOnly = true)
    public GastoEntity findById(Long id) {
        return gastoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        gastoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<GastoEntity> findAll() {
        return (List<GastoEntity>) gastoRepository.findAll();
    }
}

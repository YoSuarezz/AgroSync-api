package com.sedikev.application.usecase;

import com.sedikev.domain.entity.LoteEntity;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.domain.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoteServiceImpl implements LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Transactional
    public LoteEntity save(LoteEntity loteEntity) {
        return loteRepository.save(loteEntity);
    }

    @Transactional(readOnly = true)
    public LoteEntity findById(Long id) {
        return loteRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        loteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LoteEntity> findAll() {
        return (List<LoteEntity>) loteRepository.findAll();
    }
}

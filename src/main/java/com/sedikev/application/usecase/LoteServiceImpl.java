package com.sedikev.application.usecase;

import com.sedikev.domain.entity.Lote;
import com.sedikev.domain.repository.LoteRepository;
import com.sedikev.domain.service.LoteService;
import com.sedikev.infrastructure.persistence.LoteRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoteServiceImpl implements LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Transactional
    public Lote save(Lote lote) {
        return loteRepository.save(lote);
    }

    @Transactional(readOnly = true)
    public Lote findById(Long id) {
        return loteRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        loteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Lote> findAll() {
        return (List<Lote>) loteRepository.findAll();
    }
}

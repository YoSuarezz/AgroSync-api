package com.sedikev.application.usecase;

import com.sedikev.domain.entity.Cartera;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.domain.service.CarteraService;
import com.sedikev.infrastructure.persistence.CarteraRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarteraServiceImpl implements CarteraService {

    @Autowired
    private CarteraRepository carteraRepository;

    @Transactional
    public Cartera save(Cartera cartera) {
        return carteraRepository.save(cartera);
    }

    @Transactional(readOnly = true)
    public Cartera findById(Long id) {
        return carteraRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        carteraRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Cartera> findAll() {
        return (List<Cartera>) carteraRepository.findAll();
    }
}

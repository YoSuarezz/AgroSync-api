package com.sedikev.application.usecase;

import com.sedikev.domain.entity.CarteraEntity;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.domain.service.CarteraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarteraServiceImpl implements CarteraService {

    @Autowired
    private CarteraRepository carteraRepository;

    @Transactional
    public CarteraEntity save(CarteraEntity carteraEntity) {
        return carteraRepository.save(carteraEntity);
    }

    @Transactional(readOnly = true)
    public CarteraEntity findById(Long id) {
        return carteraRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        carteraRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CarteraEntity> findAll() {
        return (List<CarteraEntity>) carteraRepository.findAll();
    }
}

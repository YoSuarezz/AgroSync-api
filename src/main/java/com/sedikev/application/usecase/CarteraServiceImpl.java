package com.sedikev.application.usecase;

import com.sedikev.application.domain.CarteraDomain;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.domain.entity.CarteraEntity;
import com.sedikev.domain.repository.CarteraRepository;
import com.sedikev.domain.service.CarteraService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarteraServiceImpl implements CarteraService {

    @Autowired
    private final CarteraRepository carteraRepository;

    @Autowired
    private final CarteraMapper carteraMapper;

    @Transactional
    @Override
    public CarteraDomain save(CarteraDomain carteraDomain) {
        CarteraEntity carteraEntity = carteraMapper.toEntity(carteraDomain);
        CarteraEntity carteraSaved = carteraRepository.save(carteraEntity);
        return carteraMapper.toDomain(carteraSaved);
    }

    @Transactional(readOnly = true)
    @Override
    public CarteraDomain findById(Long id) {
        CarteraEntity carteraEntity = carteraRepository.findById(id).orElse(null);
        return carteraMapper.toDomain(carteraEntity);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        carteraRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarteraDomain> findAll() {
        return carteraRepository.findAll().stream()
                .map(carteraMapper::toDomain)
                .collect(Collectors.toList());
    }
}

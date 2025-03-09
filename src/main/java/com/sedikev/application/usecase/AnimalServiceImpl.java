package com.sedikev.application.usecase;

import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.domain.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private final AnimalRepository animalRepository;

    @Autowired
    private final AnimalMapper animalMapper;

    @Transactional
    @Override
    public AnimalDomain save(AnimalDomain animalDomain) {
        AnimalEntity animalEntity = animalMapper.toEntity(animalDomain);
        AnimalEntity savedEntity = animalRepository.save(animalEntity);
        return animalMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public AnimalDomain findById(String id) {
        AnimalEntity animalEntity = animalRepository.findById(id).orElse(null);
        return animalMapper.toDomain(animalEntity);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        animalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnimalDomain> findAll() {
        return animalRepository.findAll().stream()
                .map(animalMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnimalDomain> findByLote(Long idLote) {
        return animalRepository.findAll().stream()
                .filter(animalEntity -> idLote.equals(animalEntity.getLoteEntity().getId()))
                .map(animalMapper::toDomain)
                .collect(Collectors.toList());
    }
}

package com.sedikev.application.usecase;

import com.sedikev.application.domain.AnimalDomain;
import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.domain.entity.AnimalEntity;
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
        // Convertir el dominio a una entidad
        AnimalEntity animalEntity = animalMapper.toEntity(animalDomain);
        // Guardar la entidad en la base de datos
        AnimalEntity savedEntity = animalRepository.save(animalEntity);
        // Convertir la entidad guardada de vuelta a un dominio
        return animalMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public AnimalDomain findById(String id) {
        // Buscar la entidad por ID
        AnimalEntity animalEntity = animalRepository.findById(id).orElse(null);
        // Convertir la entidad a un dominio
        return animalMapper.toDomain(animalEntity);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        // Eliminar la entidad por ID
        animalRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnimalDomain> findAll() {
        // Obtener todas las entidades y convertirlas a dominios
        return animalRepository.findAll().stream()
                .map(animalMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AnimalDomain> findByLote(Long idLote) {
        // Filtrar las entidades por ID de lote y convertirlas a dominios
        return animalRepository.findAll().stream()
                .filter(animalEntity -> idLote.equals(animalEntity.getLoteEntity().getId()))
                .map(animalMapper::toDomain)
                .collect(Collectors.toList());
    }
}

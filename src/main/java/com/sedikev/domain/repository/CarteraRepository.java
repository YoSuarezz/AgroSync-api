package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarteraRepository extends JpaRepository<CarteraEntity, Long> {

    CarteraEntity save(CarteraEntity carteraEntity);

    Optional<CarteraEntity> findById(Long id);

    void deleteById(Long id);

    List<CarteraEntity> findAll();
}

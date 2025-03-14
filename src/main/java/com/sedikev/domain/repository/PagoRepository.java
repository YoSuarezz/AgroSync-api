package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {

    PagoEntity save(PagoEntity pagoEntity);

    Optional<PagoEntity> findById(Long id);

    void deleteById(Long id);

    List<PagoEntity> findAll();
}

package com.sedikev.domain.repository;

import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    AnimalEntity save(AnimalEntity animalEntity);

    Optional<AnimalEntity> findById(Long id);

    void deleteById(Long id);

    void deleteByLoteId(Long loteId);

    List<AnimalEntity> findByLoteId(Long loteId);

    List<AnimalEntity> findAll();

    boolean existsByLoteIdAndSlot(Long loteId, Integer slot);

    // Recupera el animal que ocupa ese lote y slot, si lo hay
    Optional<AnimalEntity> findByLoteIdAndSlot(Long loteId, Integer slot);

}

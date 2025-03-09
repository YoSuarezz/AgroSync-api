package com.sedikev.infrastructure.adapter.persistence;

import com.sedikev.infrastructure.adapter.entity.AnimalEntity;
import com.sedikev.domain.repository.AnimalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepositoryImpl extends JpaRepository<AnimalEntity, String>, AnimalRepository {
}

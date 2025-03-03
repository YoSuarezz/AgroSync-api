package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.Animal;
import com.sedikev.domain.repository.AnimalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepositoryImpl extends JpaRepository<Animal, String>, AnimalRepository {
}

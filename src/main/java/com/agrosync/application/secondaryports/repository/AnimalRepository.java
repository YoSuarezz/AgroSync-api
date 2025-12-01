package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AnimalRepository extends JpaRepository<AnimalEntity, UUID> {
}

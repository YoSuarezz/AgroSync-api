package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.LoteEntity;
import com.sedikev.domain.repository.LoteRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepositoryImpl extends JpaRepository<LoteEntity, Long>, LoteRepository {
}

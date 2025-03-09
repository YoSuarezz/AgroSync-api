package com.sedikev.infrastructure.adapter.persistence;

import com.sedikev.infrastructure.adapter.entity.LoteEntity;
import com.sedikev.domain.repository.LoteRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepositoryImpl extends JpaRepository<LoteEntity, Long>, LoteRepository {
}

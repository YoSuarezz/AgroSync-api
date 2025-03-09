package com.sedikev.infrastructure.adapter.persistence;

import com.sedikev.infrastructure.adapter.entity.PagoEntity;
import com.sedikev.domain.repository.PagoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepositoryImpl extends JpaRepository<PagoEntity, Long>, PagoRepository {
}

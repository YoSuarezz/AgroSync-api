package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.PagoEntity;
import com.sedikev.domain.repository.PagoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepositoryImpl extends JpaRepository<PagoEntity, Long>, PagoRepository {
}

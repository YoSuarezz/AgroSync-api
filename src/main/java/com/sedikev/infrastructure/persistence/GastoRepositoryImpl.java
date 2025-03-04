package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.GastoEntity;
import com.sedikev.domain.repository.GastoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepositoryImpl extends JpaRepository<GastoEntity, Long>, GastoRepository {
}

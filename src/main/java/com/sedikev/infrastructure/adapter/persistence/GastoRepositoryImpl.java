package com.sedikev.infrastructure.adapter.persistence;

import com.sedikev.infrastructure.adapter.entity.GastoEntity;
import com.sedikev.domain.repository.GastoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepositoryImpl extends JpaRepository<GastoEntity, Long>, GastoRepository {
}

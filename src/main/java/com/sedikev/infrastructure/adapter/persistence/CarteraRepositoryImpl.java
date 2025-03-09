package com.sedikev.infrastructure.adapter.persistence;

import com.sedikev.infrastructure.adapter.entity.CarteraEntity;
import com.sedikev.domain.repository.CarteraRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteraRepositoryImpl extends JpaRepository<CarteraEntity, Long>, CarteraRepository {
}

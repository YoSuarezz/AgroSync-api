package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.CarteraEntity;
import com.sedikev.domain.repository.CarteraRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteraRepositoryImpl extends JpaRepository<CarteraEntity, Long>, CarteraRepository {
}

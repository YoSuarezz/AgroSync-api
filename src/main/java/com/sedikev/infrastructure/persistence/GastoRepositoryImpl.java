package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.Gasto;
import com.sedikev.domain.repository.GastoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GastoRepositoryImpl extends JpaRepository<Gasto, Long>, GastoRepository {
}

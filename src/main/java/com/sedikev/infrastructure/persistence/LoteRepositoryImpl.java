package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.Lote;
import com.sedikev.domain.repository.LoteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LoteRepositoryImpl extends JpaRepository<Lote, Long>, LoteRepository {
}

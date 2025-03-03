package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.Pago;
import com.sedikev.domain.repository.PagoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PagoRepositoryImpl extends JpaRepository<Pago, Long>, PagoRepository {
}

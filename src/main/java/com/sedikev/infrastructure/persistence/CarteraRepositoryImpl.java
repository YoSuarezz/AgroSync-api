package com.sedikev.infrastructure.persistence;

import com.sedikev.domain.entity.Cartera;
import com.sedikev.domain.repository.AnimalRepository;
import com.sedikev.domain.repository.CarteraRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CarteraRepositoryImpl extends JpaRepository<Cartera, Long>, CarteraRepository {
}

package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoteRepository extends JpaRepository<LoteEntity, UUID> {
}

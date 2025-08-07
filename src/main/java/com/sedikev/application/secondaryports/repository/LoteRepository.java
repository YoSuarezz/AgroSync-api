package com.sedikev.application.secondaryports.repository;

import com.sedikev.application.secondaryports.entity.LoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoteRepository extends JpaRepository<LoteEntity, Long> {

    LoteEntity save(LoteEntity loteEntity);

    Optional<LoteEntity> findById(Long id);

    void deleteById(Long id);

    List<LoteEntity> findAll();

    boolean existsByContramarca(int contramarca);

    boolean existsByContramarcaAndIdNot(int contramarca, Long id);

    @Query(value = "SELECT * FROM lote WHERE contramarca = :contramarca AND WEEK(fecha, 3) = :semana", nativeQuery = true)
    Optional<LoteEntity> findByContramarcaAndSemana(@Param("contramarca") Integer contramarca,
                                                    @Param("semana") Integer semana);
  
    List<LoteEntity> findByUsuarioId(Long usuarioId);
}
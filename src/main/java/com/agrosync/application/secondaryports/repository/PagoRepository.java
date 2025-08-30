package com.agrosync.application.secondaryports.repository;

import com.agrosync.application.secondaryports.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {

    PagoEntity save(PagoEntity pagoEntity);

    Optional<PagoEntity> findById(Long id);

    void deleteById(Long id);

    List<PagoEntity> findAll();

    // Méto-do para obtener los pagos por usuarioId
    List<PagoEntity> findByVentaUsuarioId(Long usuarioId);  // Aquí cambiamos el nombre de la consulta para hacer la relación correcta
}

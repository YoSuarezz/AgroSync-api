package com.agrosync.infrastructure.secondaryadapters.config;

import com.agrosync.application.secondaryports.entity.usuarios.TIpoUsuarioEntity;
import com.agrosync.application.secondaryports.repository.TipoUsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public DataInitializer(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @PostConstruct
    public void init() {
        if (tipoUsuarioRepository.count() == 0) {
            tipoUsuarioRepository.saveAll(List.of(
                    TIpoUsuarioEntity.create().setId(1L).setNombre("Cliente"),
                    TIpoUsuarioEntity.create().setId(2L).setNombre("Proveedor")
            ));
        }
    }
}

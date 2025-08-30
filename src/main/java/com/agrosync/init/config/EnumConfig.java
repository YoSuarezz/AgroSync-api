package com.agrosync.init.config;

import com.agrosync.application.secondaryports.repository.TipoUsuarioRepository;
import com.agrosync.crosscutting.enums.TipoUsuarioEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public EnumConfig(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @PostConstruct
    public void init() {
        TipoUsuarioEnum.setRepository(tipoUsuarioRepository);
    }

}

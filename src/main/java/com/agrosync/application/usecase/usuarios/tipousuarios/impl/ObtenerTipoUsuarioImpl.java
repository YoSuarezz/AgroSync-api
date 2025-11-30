package com.agrosync.application.usecase.usuarios.tipousuarios.impl;

import com.agrosync.application.secondaryports.entity.usuarios.TipoUsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.TipoUsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.TipoUsuarioRepository;
import com.agrosync.application.usecase.usuarios.tipousuarios.ObtenerTipoUsuario;
import com.agrosync.domain.usuarios.TipoUsuarioDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerTipoUsuarioImpl implements ObtenerTipoUsuario {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public ObtenerTipoUsuarioImpl(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public List<TipoUsuarioDomain> ejecutar() {
        List<TipoUsuarioEntity> resultados = tipoUsuarioRepository.findAll();
        return TipoUsuarioEntityMapper.INSTANCE.toDomainCollection(resultados);
    }
}

package com.agrosync.application.primaryports.service;

import com.agrosync.application.usecase.usuarios.*;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioFacadeImpl implements UsuarioService {

    private final CreateUsuarioUseCase createUsuarioUseCase;
    private final UpdateUsuarioUseCase updateUsuarioUseCase;
    private final DeleteUsuarioUseCase deleteUsuarioUseCase;
    private final GetUsuarioByIdUseCase getUsuarioByIdUseCase;
    private final GetAllUsuariosUseCase getAllUsuariosUseCase;

    @Override
    public UsuarioDomain save(UsuarioDomain usuarioDomain) {
        return createUsuarioUseCase.ejecutar(usuarioDomain);
    }

    @Override
    public UsuarioDomain update(UsuarioDomain usuarioDomain) {
        return updateUsuarioUseCase.ejecutar(usuarioDomain);
    }

    @Override
    public UsuarioDomain findById(Long id) {
        return getUsuarioByIdUseCase.ejecutar(id);
    }

    @Override
    public void deleteById(Long id) {
        deleteUsuarioUseCase.ejecutar(id);
    }

    @Override
    public List<UsuarioDomain> findAll() {
        return getAllUsuariosUseCase.ejecutar(null);
    }
}

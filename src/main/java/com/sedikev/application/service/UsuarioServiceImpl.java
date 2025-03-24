package com.sedikev.application.service;

import com.sedikev.application.usecase.usuario.*;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

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

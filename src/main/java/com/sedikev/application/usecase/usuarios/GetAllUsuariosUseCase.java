package com.sedikev.application.usecase.usuarios;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.domain.usuarios.UsuarioDomain;
import com.sedikev.application.secondaryports.repository.UsuarioRepository;
import com.sedikev.application.primaryports.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetAllUsuariosUseCase implements UseCaseWithReturn<Void, List<UsuarioDomain>> {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDomain> ejecutar(Void unused) {
        // Obtener todos los usuarios
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDomain)
                .collect(Collectors.toList());
    }
}

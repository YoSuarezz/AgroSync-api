package com.agrosync.application.usecase.usuarios;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.primaryports.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUsuarioByIdUseCase implements UseCaseWithReturn<Long, UsuarioDomain> {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDomain ejecutar(Long id) {
        // Buscar el usuario por ID, mapear la entidad al dominio y lanzar excepción si no existe
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDomain)
                .orElseThrow(() -> new BusinessAgroSyncException("Usuario no encontrado con ID: " + id));
    }
}

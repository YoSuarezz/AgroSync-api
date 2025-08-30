package com.agrosync.application.usecase.usuarios;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.primaryports.mapper.UsuarioMapper;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUsuarioUseCase implements UseCaseWithReturn<UsuarioDomain, UsuarioDomain> {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDomain ejecutar(UsuarioDomain usuarioDomain) {
        // Validación de negocio: Verificar que el usuario exista
        if (!usuarioRepository.existsById(usuarioDomain.getId())) {
            throw new BusinessAgroSyncException("El usuario no existe");
        }

        // Validación de negocio: El nombre debe tener menos de 50 caracteres
        if (usuarioDomain.getNombre().length() > 50) {
            throw new BusinessAgroSyncException("El nombre no puede tener más de 50 caracteres");
        }

        // Validación de negocio: El teléfono debe tener exactamente 10 caracteres
        if (usuarioDomain.getTelefono().length() != 10) {
            throw new BusinessAgroSyncException("El teléfono debe tener exactamente 10 caracteres");
        }

        // Mapear y actualizar el usuario
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioDomain);
        UsuarioEntity usuarioUpdated = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioUpdated);
    }
}

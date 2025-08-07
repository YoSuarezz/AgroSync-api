package com.sedikev.application.usecase.usuario;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.application.secondaryports.repository.UsuarioRepository;
import com.sedikev.application.primaryports.mapper.UsuarioMapper;
import com.sedikev.application.secondaryports.entity.UsuarioEntity;
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
            throw new BusinessSedikevException("El usuario no existe");
        }

        // Validación de negocio: El nombre debe tener menos de 50 caracteres
        if (usuarioDomain.getNombre().length() > 50) {
            throw new BusinessSedikevException("El nombre no puede tener más de 50 caracteres");
        }

        // Validación de negocio: El teléfono debe tener exactamente 10 caracteres
        if (usuarioDomain.getTelefono().length() != 10) {
            throw new BusinessSedikevException("El teléfono debe tener exactamente 10 caracteres");
        }

        // Validación de negocio: El tipo de usuario debe ser "proveedor" o "cliente"
        if (!usuarioDomain.getTipo_usuario().equalsIgnoreCase("proveedor") &&
                !usuarioDomain.getTipo_usuario().equalsIgnoreCase("cliente")) {
            throw new BusinessSedikevException("El tipo de usuario debe ser 'proveedor' o 'cliente'");
        }

        // Mapear y actualizar el usuario
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioDomain);
        UsuarioEntity usuarioUpdated = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioUpdated);
    }
}

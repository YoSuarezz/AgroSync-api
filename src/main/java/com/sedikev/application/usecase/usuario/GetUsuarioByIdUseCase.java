package com.sedikev.application.usecase.usuario;

import com.sedikev.application.usecase.UseCaseWithReturn;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.domain.repository.UsuarioRepository;
import com.sedikev.application.mapper.UsuarioMapper;
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
                .orElseThrow(() -> new BusinessSedikevException("Usuario no encontrado con ID: " + id));
    }
}

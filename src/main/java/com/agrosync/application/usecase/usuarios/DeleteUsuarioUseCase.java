package com.agrosync.application.usecase.usuarios;

import com.agrosync.crosscutting.exception.custom.BusinessAgroSyncException;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.UseCaseWithoutReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUsuarioUseCase implements UseCaseWithoutReturn<Long> {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void ejecutar(Long id) {
        // Validar que el usuario exista
        if (!usuarioRepository.existsById(id)) {
            throw new BusinessAgroSyncException("El usuario no existe");
        }

        // Eliminar el usuario
        usuarioRepository.deleteById(id);
    }
}
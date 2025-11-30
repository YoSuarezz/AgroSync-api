package com.agrosync.application.usecase.usuarios.usuario;

import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.usuarios.UsuarioDomain;

import java.util.UUID;

public interface ObtenerUsuarioPorId extends UseCaseWithReturn<UUID, UsuarioDomain> {
}

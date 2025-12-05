package com.agrosync.application.usecase.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioIdSuscripcionDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.usuarios.UsuarioDomain;

public interface ObtenerUsuarioPorId extends UseCaseWithReturn<UsuarioIdSuscripcionDTO, UsuarioDomain> {
}

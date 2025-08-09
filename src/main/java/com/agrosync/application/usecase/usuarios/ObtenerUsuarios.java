package com.agrosync.application.usecase.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioRequest;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.usuarios.UsuarioDomain;
import org.springframework.data.domain.Page;

public interface ObtenerUsuarios extends UseCaseWithReturn<UsuarioRequest, Page<UsuarioDomain>> {
}

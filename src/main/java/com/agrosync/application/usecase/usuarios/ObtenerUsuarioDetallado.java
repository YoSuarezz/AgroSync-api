package com.agrosync.application.usecase.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioDetalladoFiltroDTO;
import com.agrosync.application.usecase.UseCaseWithReturn;
import com.agrosync.domain.usuarios.UsuarioDetalladoDomain;
import com.agrosync.domain.usuarios.UsuarioDomain;

public interface ObtenerUsuarioDetallado extends UseCaseWithReturn<UsuarioDetalladoFiltroDTO, UsuarioDetalladoDomain> {
}

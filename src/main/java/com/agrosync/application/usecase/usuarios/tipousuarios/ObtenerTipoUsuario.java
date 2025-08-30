package com.agrosync.application.usecase.usuarios.tipousuarios;

import com.agrosync.application.usecase.UseCaseWithoutInput;
import com.agrosync.domain.usuarios.TipoUsuarioDomain;

import java.util.List;

public interface ObtenerTipoUsuario extends UseCaseWithoutInput<List<TipoUsuarioDomain>> {
}

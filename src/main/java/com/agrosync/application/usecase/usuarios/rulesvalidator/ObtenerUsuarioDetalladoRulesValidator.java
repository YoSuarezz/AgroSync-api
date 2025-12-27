package com.agrosync.application.usecase.usuarios.rulesvalidator;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioDetalladoFiltroDTO;
import com.agrosync.application.usecase.RulesValidator;
import com.agrosync.domain.usuarios.UsuarioDomain;

public interface ObtenerUsuarioDetalladoRulesValidator {

    void validar(UsuarioDetalladoFiltroDTO filtro);
}

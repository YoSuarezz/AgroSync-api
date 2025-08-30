package com.agrosync.application.primaryports.interactor.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioDTO;
import com.agrosync.application.primaryports.dto.usuarios.UsuarioRequest;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import org.springframework.data.domain.Page;

public interface ObtenerUsuariosInteractor extends InteractorWithReturn<UsuarioRequest, Page<UsuarioDTO>> {
}

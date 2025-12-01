package com.agrosync.application.primaryports.interactor.usuarios;

import com.agrosync.application.primaryports.dto.usuarios.request.RegiserNewUserDTO;
import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioRequest;
import com.agrosync.application.primaryports.interactor.InteractorWithReturn;
import org.springframework.data.domain.Page;

public interface ObtenerUsuariosInteractor extends InteractorWithReturn<UsuarioRequest, Page<RegiserNewUserDTO>> {
}

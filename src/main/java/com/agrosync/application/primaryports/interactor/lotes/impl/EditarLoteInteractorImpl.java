package com.agrosync.application.primaryports.interactor.lotes.impl;

import com.agrosync.application.primaryports.dto.lotes.request.EditarLoteDTO;
import com.agrosync.application.primaryports.interactor.lotes.EditarLoteInteractor;
import com.agrosync.application.usecase.lotes.EditarLote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EditarLoteInteractorImpl implements EditarLoteInteractor {

    private final EditarLote editarLote;

    public EditarLoteInteractorImpl(EditarLote editarLote) {
        this.editarLote = editarLote;
    }

    @Override
    public void ejecutar(EditarLoteDTO data) {
        editarLote.ejecutar(data);
    }
}

package com.agrosync.domain.lotes.rules.impl;

import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.domain.enums.compras.EstadoCompraEnum;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.lotes.exceptions.IdentificadorLoteNoExisteException;
import com.agrosync.domain.lotes.exceptions.LoteNoEditableException;
import com.agrosync.domain.lotes.rules.LoteEditableRule;
import org.springframework.stereotype.Service;

@Service
public class LoteEditableRuleImpl implements LoteEditableRule {

    private final LoteRepository loteRepository;

    public LoteEditableRuleImpl(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @Override
    public void validate(LoteDomain data) {
        var loteEntity = loteRepository.findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorLoteNoExisteException::create);

        if (loteEntity.getCompra() != null && loteEntity.getCompra().getEstado() == EstadoCompraEnum.ANULADA) {
            throw LoteNoEditableException.create();
        }
    }
}

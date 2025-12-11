package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.usecase.cobros.ObtenerCobroPorId;
import com.agrosync.domain.cobros.exceptions.CobroNoExisteException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ObtenerCobroPorIdImpl implements ObtenerCobroPorId {

    private final CobroRepository cobroRepository;

    public ObtenerCobroPorIdImpl(CobroRepository cobroRepository) {
        this.cobroRepository = cobroRepository;
    }

    @Override
    public ObtenerCobroDTO ejecutar(UUID[] data) {
        UUID cobroId = data[0];
        UUID suscripcionId = data[1];

        CobroEntity cobro = cobroRepository.findByIdAndSuscripcion_Id(cobroId, suscripcionId)
                .orElseThrow(CobroNoExisteException::create);

        return ObtenerCobroDTO.create(
                cobro.getId(),
                cobro.getCuentaCobrar().getId(),
                cobro.getCuentaCobrar().getNumeroCuenta(),
                cobro.getMonto(),
                cobro.getFechaCobro(),
                cobro.getMetodoPago(),
                cobro.getConcepto());
    }
}

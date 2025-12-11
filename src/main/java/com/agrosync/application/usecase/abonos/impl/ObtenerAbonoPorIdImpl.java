package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.usecase.abonos.ObtenerAbonoPorId;
import com.agrosync.domain.abonos.exceptions.AbonoNoExisteException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ObtenerAbonoPorIdImpl implements ObtenerAbonoPorId {

    private final AbonoRepository abonoRepository;

    public ObtenerAbonoPorIdImpl(AbonoRepository abonoRepository) {
        this.abonoRepository = abonoRepository;
    }

    @Override
    public ObtenerAbonoDTO ejecutar(UUID[] data) {
        UUID abonoId = data[0];
        UUID suscripcionId = data[1];

        AbonoEntity abono = abonoRepository.findByIdAndSuscripcion_Id(abonoId, suscripcionId)
                .orElseThrow(AbonoNoExisteException::create);

        return ObtenerAbonoDTO.create(
                abono.getId(),
                abono.getCuentaPagar().getId(),
                abono.getCuentaPagar().getNumeroCuenta(),
                abono.getMonto(),
                abono.getFechaPago(),
                abono.getMetodoPago());
    }
}

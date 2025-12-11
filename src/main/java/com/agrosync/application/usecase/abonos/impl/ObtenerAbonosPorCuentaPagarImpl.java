package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.usecase.abonos.ObtenerAbonosPorCuentaPagar;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ObtenerAbonosPorCuentaPagarImpl implements ObtenerAbonosPorCuentaPagar {

    private final AbonoRepository abonoRepository;

    public ObtenerAbonosPorCuentaPagarImpl(AbonoRepository abonoRepository) {
        this.abonoRepository = abonoRepository;
    }

    @Override
    public List<ObtenerAbonoDTO> ejecutar(UUID[] data) {
        UUID cuentaPagarId = data[0];
        UUID suscripcionId = data[1];

        List<AbonoEntity> abonos = abonoRepository.findByCuentaPagar_IdAndSuscripcion_Id(cuentaPagarId, suscripcionId);

        return abonos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ObtenerAbonoDTO mapToDTO(AbonoEntity entity) {
        return ObtenerAbonoDTO.create(
                entity.getId(),
                entity.getCuentaPagar().getId(),
                entity.getCuentaPagar().getNumeroCuenta(),
                entity.getMonto(),
                entity.getFechaPago(),
                entity.getMetodoPago());
    }
}

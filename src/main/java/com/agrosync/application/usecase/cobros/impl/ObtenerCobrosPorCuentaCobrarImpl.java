package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.usecase.cobros.ObtenerCobrosPorCuentaCobrar;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ObtenerCobrosPorCuentaCobrarImpl implements ObtenerCobrosPorCuentaCobrar {

    private final CobroRepository cobroRepository;

    public ObtenerCobrosPorCuentaCobrarImpl(CobroRepository cobroRepository) {
        this.cobroRepository = cobroRepository;
    }

    @Override
    public List<ObtenerCobroDTO> ejecutar(UUID[] data) {
        UUID cuentaCobrarId = data[0];
        UUID suscripcionId = data[1];

        List<CobroEntity> cobros = cobroRepository.findByCuentaCobrar_IdAndSuscripcion_Id(cuentaCobrarId,
                suscripcionId);

        return cobros.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ObtenerCobroDTO mapToDTO(CobroEntity entity) {
        return ObtenerCobroDTO.create(
                entity.getId(),
                entity.getCuentaCobrar().getId(),
                entity.getCuentaCobrar().getNumeroCuenta(),
                entity.getMonto(),
                entity.getFechaCobro(),
                entity.getMetodoPago(),
                entity.getConcepto());
    }
}

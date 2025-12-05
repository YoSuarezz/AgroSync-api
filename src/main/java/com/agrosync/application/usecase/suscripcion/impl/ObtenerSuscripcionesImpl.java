package com.agrosync.application.usecase.suscripcion.impl;

import com.agrosync.application.primaryports.dto.suscripcion.request.SuscripcionPageDTO;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.suscripcion.SuscripcionEntityMapper;
import com.agrosync.application.secondaryports.repository.SuscripcionRepository;
import com.agrosync.application.usecase.suscripcion.ObtenerSuscripciones;
import com.agrosync.domain.suscripcion.SuscripcionDomain;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerSuscripcionesImpl implements ObtenerSuscripciones {

    private final SuscripcionRepository suscripcionRepository;

    public ObtenerSuscripcionesImpl(SuscripcionRepository suscripcionRepository) {
        this.suscripcionRepository = suscripcionRepository;
    }

    @Override
    public Page<SuscripcionDomain> ejecutar(SuscripcionPageDTO domain) {
        Sort sort = Sort.by(Sort.Direction.fromString(domain.getSortDirection()), domain.getSortBy());
        Pageable pageable = PageRequest.of(domain.getPage(), domain.getSize(), sort);

        Specification<SuscripcionEntity> specs = buildSpecification(domain);

        Page<SuscripcionEntity> entities = suscripcionRepository.findAll(specs, pageable);

        return new PageImpl<>(SuscripcionEntityMapper.INSTANCE.toDomainCollection(entities.getContent()), pageable,
                entities.getTotalElements());
    }

    private Specification<SuscripcionEntity> buildSpecification(SuscripcionPageDTO domain) {
        List<Specification<SuscripcionEntity>> specs = new ArrayList<>();

        if (domain.getNombreEmpresa() != null && !domain.getNombreEmpresa().trim().isEmpty()) {
            String[] palabras = domain.getNombreEmpresa().trim().split("\\s+");
            for (String palabra : palabras) {
                specs.add((root, query, cb) -> cb.like(cb.lower(root.get("nombreEmpresa")),
                        "%" + palabra.toLowerCase() + "%"));
            }
        }

        if (domain.getNit() != null && !domain.getNit().trim().isEmpty()) {
            specs.add((root, query, cb) -> cb.like(cb.lower(root.get("nit")), "%" + domain.getNit().toLowerCase() + "%"));
        }

        return Specification.allOf(specs);
    }
}

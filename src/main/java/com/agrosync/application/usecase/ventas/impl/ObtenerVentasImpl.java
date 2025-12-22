package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.application.primaryports.dto.ventas.request.VentaPageDTO;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.mapper.ventas.VentaEntityMapper;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.ventas.ObtenerVentas;
import com.agrosync.application.usecase.ventas.rulesvalidator.ObtenerVentasRulesValidator;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.ventas.VentaDomain;
import com.agrosync.domain.enums.ventas.EstadoVentaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ObtenerVentasImpl implements ObtenerVentas {

    private final VentaRepository ventaRepository;
    private final ObtenerVentasRulesValidator obtenerVentasRulesValidator;
    private final VentaEntityMapper ventaEntityMapper;

    public ObtenerVentasImpl(VentaRepository ventaRepository, ObtenerVentasRulesValidator obtenerVentasRulesValidator, VentaEntityMapper ventaEntityMapper) {
        this.ventaRepository = ventaRepository;
        this.obtenerVentasRulesValidator = obtenerVentasRulesValidator;
        this.ventaEntityMapper = ventaEntityMapper;
    }

    @Override
    public Page<VentaDomain> ejecutar(VentaPageDTO data) {
        obtenerVentasRulesValidator.validar(data);
        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "fechaVenta";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<VentaEntity> spec = buildSpecification(data);

        Page<VentaEntity> entities = ventaRepository.findAll(spec, pageable);
        return entities.map(ventaEntityMapper::toDomain);
    }

    private Specification<VentaEntity> buildSpecification(VentaPageDTO data) {
        List<Specification<VentaEntity>> specs = new ArrayList<>();

        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        if (StringUtils.hasText(data.getNumeroVenta())) {
            String numero = data.getNumeroVenta().trim().toLowerCase();
            specs.add((root, query, cb) -> cb.like(cb.lower(root.get("numeroVenta")), "%" + numero + "%"));
        }

        if (data.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaVenta"), data.getFechaInicio()));
        }

        if (data.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaVenta"), data.getFechaFin()));
        }

        UUID clienteId = data.getClienteId();
        if (clienteId != null && !UUIDHelper.isDefault(clienteId)) {
            specs.add((root, query, cb) -> cb.equal(root.get("cliente").get("id"), clienteId));
        }

        // Filtrar ventas anuladas
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoVentaEnum.ANULADA));

        return Specification.allOf(specs);
    }
}

package com.agrosync.application.usecase.compras.impl;

import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.usecase.compras.ObtenerCompras;
import com.agrosync.application.usecase.compras.rulesvalidator.ObtenerComprasRulesValidator;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.enums.compras.EstadoCompraEnum;
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
public class ObtenerComprasImpl implements ObtenerCompras {

    private final CompraRepository compraRepository;
    private final ObtenerComprasRulesValidator obtenerComprasRulesValidator;
    private final CompraEntityMapper compraEntityMapper;

    public ObtenerComprasImpl(CompraRepository compraRepository, ObtenerComprasRulesValidator obtenerComprasRulesValidator, CompraEntityMapper compraEntityMapper) {
        this.compraRepository = compraRepository;
        this.obtenerComprasRulesValidator = obtenerComprasRulesValidator;
        this.compraEntityMapper = compraEntityMapper;
    }

    @Override
    public Page<CompraDomain> ejecutar(CompraPageDTO data) {
        obtenerComprasRulesValidator.validar(data);
        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "fechaCompra";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<CompraEntity> spec = buildSpecification(data);

        Page<CompraEntity> entities = compraRepository.findAll(spec, pageable);

        return entities.map(compraEntityMapper::toDomain);
    }

    private Specification<CompraEntity> buildSpecification(CompraPageDTO data) {
        List<Specification<CompraEntity>> specs = new ArrayList<>();

        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        if (StringUtils.hasText(data.getNumeroCompra())) {
            String numeroCompra = data.getNumeroCompra().trim().toLowerCase();
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("numeroCompra")), "%" + numeroCompra + "%"));
        }

        UUID proveedorId = data.getProveedorId();
        if (proveedorId != null && !UUIDHelper.isDefault(proveedorId)) {
            specs.add((root, query, cb) -> cb.equal(root.get("proveedor").get("id"), proveedorId));
        }

        if (data.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaCompra"), data.getFechaInicio()));
        }

        if (data.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaCompra"), data.getFechaFin()));
        }

        // Filtrar compras anuladas
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoCompraEnum.ANULADA));

        return Specification.allOf(specs);
    }
}

package com.agrosync.application.usecase.cuentaspagar.impl;

import com.agrosync.application.primaryports.dto.cuentaspagar.request.CuentaPagarPageDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.mapper.cuentaspagar.CuentaPagarEntityMapper;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.usecase.cuentaspagar.ObtenerCuentasPagar;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerCuentasPagarImpl implements ObtenerCuentasPagar {

    private final CuentaPagarRepository cuentaPagarRepository;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final CuentaPagarEntityMapper cuentaPagarEntityMapper;

    public ObtenerCuentasPagarImpl(CuentaPagarRepository cuentaPagarRepository,
                                   SuscripcionExisteRule suscripcionExisteRule,
                                   CuentaPagarEntityMapper cuentaPagarEntityMapper) {
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.cuentaPagarEntityMapper = cuentaPagarEntityMapper;
    }

    @Override
    public Page<CuentaPagarDomain> ejecutar(CuentaPagarPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());

        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "fechaEmision";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<CuentaPagarEntity> spec = buildSpecification(data);

        Page<CuentaPagarEntity> entities = cuentaPagarRepository.findAll(spec, pageable);

        return new PageImpl<>(
                cuentaPagarEntityMapper.toDomainCollection(entities.getContent()),
                pageable,
                entities.getTotalElements()
        );
    }

    private Specification<CuentaPagarEntity> buildSpecification(CuentaPagarPageDTO data) {
        List<Specification<CuentaPagarEntity>> specs = new ArrayList<>();

        ObtenerCuentaPagarDTO filtro = data.getCuentaPagar();

        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        if (data.getEstado() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("estado"), data.getEstado()));
        }

        if (filtro != null) {

            if (StringUtils.hasText(filtro.getNumeroCuenta())) {
                String numeroCuenta = filtro.getNumeroCuenta().toLowerCase().trim();
                specs.add((root, query, cb) ->
                        cb.like(cb.lower(root.get("numeroCuenta")), "%" + numeroCuenta + "%"));
            }

            if (filtro.getProveedor() != null && filtro.getProveedor().getId() != null) {
                specs.add((root, query, cb) -> cb.equal(root.get("proveedor").get("id"), filtro.getProveedor().getId()));
            }

            if (filtro.getFechaVencimiento() != null) {
                specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaVencimiento"), filtro.getFechaVencimiento()));
            }
        }

        return Specification.allOf(specs);
    }
}
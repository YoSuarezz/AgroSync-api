package com.agrosync.application.usecase.cuentascobrar.impl;

import com.agrosync.application.primaryports.dto.cuentascobrar.request.CuentaCobrarPageDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;

import com.agrosync.application.secondaryports.mapper.cuentascobrar.CuentaCobrarEntityMapper;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.usecase.cuentascobrar.ObtenerCuentasCobrar;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerCuentasCobrarImpl implements ObtenerCuentasCobrar {

    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final CuentaCobrarEntityMapper cuentaCobrarEntityMapper;

    public ObtenerCuentasCobrarImpl(CuentaCobrarRepository cuentaCobrarRepository,
                                    SuscripcionExisteRule suscripcionExisteRule,
                                    CuentaCobrarEntityMapper cuentaCobrarEntityMapper) {
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.cuentaCobrarEntityMapper = cuentaCobrarEntityMapper;
    }

    @Override
    public Page<CuentaCobrarDomain> ejecutar(CuentaCobrarPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());

        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "fechaEmision";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<CuentaCobrarEntity> spec = buildSpecification(data);

        Page<CuentaCobrarEntity> entities = cuentaCobrarRepository.findAll(spec, pageable);

        return new PageImpl<>(
                cuentaCobrarEntityMapper.toDomainCollection(entities.getContent()),
                pageable,
                entities.getTotalElements()
        );
    }

    private Specification<CuentaCobrarEntity> buildSpecification(CuentaCobrarPageDTO data) {
        List<Specification<CuentaCobrarEntity>> specs = new ArrayList<>();

        ObtenerCuentaCobrarDTO filtro = data.getCuentaCobrar();

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

            if (filtro.getCliente() != null && filtro.getCliente().getId() != null) {
                specs.add((root, query, cb) -> cb.equal(root.get("cliente").get("id"), filtro.getCliente().getId()));
            }

            if (filtro.getFechaVencimiento() != null) {
                specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaVencimiento"), filtro.getFechaVencimiento()));
            }
        }

        return Specification.allOf(specs);
    }
}
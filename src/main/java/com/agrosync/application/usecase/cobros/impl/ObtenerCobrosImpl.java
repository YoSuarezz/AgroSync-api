package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.primaryports.dto.cobros.request.CobroPageDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.mapper.cobros.CobroEntityMapper;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.usecase.cobros.ObtenerCobros;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerCobrosImpl implements ObtenerCobros {

    private final CobroRepository cobroRepository;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final CobroEntityMapper cobroEntityMapper;

    public ObtenerCobrosImpl(CobroRepository cobroRepository,
            SuscripcionExisteRule suscripcionExisteRule,
            CobroEntityMapper cobroEntityMapper) {
        this.cobroRepository = cobroRepository;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.cobroEntityMapper = cobroEntityMapper;
    }

    @Override
    public Page<CobroDomain> ejecutar(CobroPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());

        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "fechaCobro";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<CobroEntity> spec = buildSpecification(data);

        Page<CobroEntity> entities = cobroRepository.findAll(spec, pageable);

        return new PageImpl<>(
                cobroEntityMapper.toDomainCollection(entities.getContent()),
                pageable,
                entities.getTotalElements());
    }

    private Specification<CobroEntity> buildSpecification(CobroPageDTO data) {
        List<Specification<CobroEntity>> specs = new ArrayList<>();

        ObtenerCobroDTO filtro = data.getCobro();

        if (data.getSuscripcionId() != null && !UUIDHelper.isDefault(data.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        if (data.getCuentaCobrarId() != null && !UUIDHelper.isDefault(data.getCuentaCobrarId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("cuentaCobrar").get("id"), data.getCuentaCobrarId()));
        }

        if (data.getMetodoPago() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("metodoPago"), data.getMetodoPago()));
        }

        if (filtro != null) {
            if (StringUtils.hasText(filtro.getNumeroCuentaCobrar())) {
                String numeroCuenta = filtro.getNumeroCuentaCobrar().toLowerCase().trim();
                specs.add((root, query, cb) -> cb.like(cb.lower(root.get("cuentaCobrar").get("numeroCuenta")), "%" + numeroCuenta + "%"));
            }

            if (StringUtils.hasText(filtro.getConcepto())) {
                String concepto = filtro.getConcepto().toLowerCase().trim();
                specs.add((root, query, cb) -> cb.like(cb.lower(root.get("concepto")), "%" + concepto + "%"));
            }
        }

        // Filtrar cobros anulados
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoCobroEnum.ANULADO));

        return specs.stream().reduce(Specification::and).orElse(null);
    }
}

package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoPageDTO;
import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.mapper.abonos.AbonoEntityMapper;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.usecase.abonos.ObtenerAbonos;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObtenerAbonosImpl implements ObtenerAbonos {

    private final AbonoRepository abonoRepository;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final AbonoEntityMapper abonoEntityMapper;

    public ObtenerAbonosImpl(AbonoRepository abonoRepository,
            SuscripcionExisteRule suscripcionExisteRule,
            AbonoEntityMapper abonoEntityMapper) {
        this.abonoRepository = abonoRepository;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.abonoEntityMapper = abonoEntityMapper;
    }

    @Override
    public Page<AbonoDomain> ejecutar(AbonoPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());

        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "fechaPago";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<AbonoEntity> spec = buildSpecification(data);

        Page<AbonoEntity> entities = abonoRepository.findAll(spec, pageable);

        return new PageImpl<>(
                abonoEntityMapper.toDomainCollection(entities.getContent()),
                pageable,
                entities.getTotalElements());
    }

    private Specification<AbonoEntity> buildSpecification(AbonoPageDTO data) {
        List<Specification<AbonoEntity>> specs = new ArrayList<>();

        ObtenerAbonoDTO filtro = data.getAbono();

        if (data.getSuscripcionId() != null && !UUIDHelper.isDefault(data.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        if (data.getCuentaPagarId() != null && !UUIDHelper.isDefault(data.getCuentaPagarId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("cuentaPagar").get("id"), data.getCuentaPagarId()));
        }

        if (data.getMetodoPago() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("metodoPago"), data.getMetodoPago()));
        }

        // Excluir abonos anulados
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoAbonoEnum.ANULADO));

        if (filtro != null) {
            if (StringUtils.hasText(filtro.getNumeroCuentaPagar())) {
                String numeroCuenta = filtro.getNumeroCuentaPagar().toLowerCase().trim();
                specs.add((root, query, cb) -> cb.like(cb.lower(root.get("cuentaPagar").get("numeroCuenta")), "%" + numeroCuenta + "%"));
            }

            if (StringUtils.hasText(filtro.getConcepto())) {
                String concepto = filtro.getConcepto().toLowerCase().trim();
                specs.add((root, query, cb) -> cb.like(cb.lower(root.get("concepto")), "%" + concepto + "%"));
            }
        }

        return specs.stream().reduce(Specification::and).orElse(null);
    }
}

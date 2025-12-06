package com.agrosync.application.usecase.lotes.impl;

import com.agrosync.application.primaryports.dto.lotes.request.LotePageDTO;
import com.agrosync.application.primaryports.dto.lotes.response.ObtenerLoteDTO;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.mapper.lotes.LoteEntityMapper;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.usecase.lotes.ObtenerLotes;
import com.agrosync.domain.lotes.LoteDomain;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Service
public class ObtenerLotesImpl implements ObtenerLotes {

    private final LoteRepository loteRepository;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final LoteEntityMapper loteEntityMapper;

    public ObtenerLotesImpl(LoteRepository loteRepository,
                            SuscripcionExisteRule suscripcionExisteRule,
                            LoteEntityMapper loteEntityMapper) {
        this.loteRepository = loteRepository;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.loteEntityMapper = loteEntityMapper;
    }

    @Override
    public Page<LoteDomain> ejecutar(LotePageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());

        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "numeroLote";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "ASC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<LoteEntity> spec = buildSpecification(data);

        Page<LoteEntity> entities = loteRepository.findAll(spec, pageable);

        return new PageImpl<>(
                loteEntityMapper.toDomainCollection(entities.getContent()),
                pageable,
                entities.getTotalElements()
        );
    }

    private Specification<LoteEntity> buildSpecification(LotePageDTO data) {
        List<Specification<LoteEntity>> specs = new ArrayList<>();

        ObtenerLoteDTO loteFiltro = data.getLote();

        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        if (loteFiltro != null) {

            if (StringUtils.hasText(loteFiltro.getNumeroLote())) {
                String numeroLote = loteFiltro.getNumeroLote().toLowerCase().trim();
                specs.add((root, query, cb) ->
                        cb.like(cb.lower(root.get("numeroLote")), "%" + numeroLote + "%"));
            }

            if (!loteFiltro.getFecha().isEqual(com.agrosync.crosscutting.helpers.DateHelper.DEFAULT_DATE)) {
                specs.add((root, query, cb) -> cb.equal(root.get("fecha"), loteFiltro.getFecha()));
            }

            if (loteFiltro.getPesoTotal() != null && loteFiltro.getPesoTotal().compareTo(BigDecimal.ZERO) > 0) {
                specs.add((root, query, cb) -> cb.ge(root.get("pesoTotal"), loteFiltro.getPesoTotal()));
            }
        }

        return Specification.allOf(specs);
    }
}
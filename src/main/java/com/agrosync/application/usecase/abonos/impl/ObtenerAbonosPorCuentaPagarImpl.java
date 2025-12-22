package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.primaryports.dto.abonos.request.AbonoIdSuscripcionDTO;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.mapper.abonos.AbonoEntityMapper;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.usecase.abonos.ObtenerAbonosPorCuentaPagar;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.cuentaspagar.rules.IdentificadorCuentaPagarExisteRule;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerAbonosPorCuentaPagarImpl implements ObtenerAbonosPorCuentaPagar {

    private final AbonoRepository abonoRepository;
    private final AbonoEntityMapper abonoEntityMapper;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final IdentificadorCuentaPagarExisteRule identificadorCuentaPagarExisteRule;

    public ObtenerAbonosPorCuentaPagarImpl(AbonoRepository abonoRepository,
                                           AbonoEntityMapper abonoEntityMapper,
                                           SuscripcionExisteRule suscripcionExisteRule,
                                           IdentificadorCuentaPagarExisteRule identificadorCuentaPagarExisteRule) {
        this.abonoRepository = abonoRepository;
        this.abonoEntityMapper = abonoEntityMapper;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.identificadorCuentaPagarExisteRule = identificadorCuentaPagarExisteRule;
    }

    @Override
    public List<AbonoDomain> ejecutar(AbonoIdSuscripcionDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        identificadorCuentaPagarExisteRule.validate(data.getId());

        List<AbonoEntity> abonos = abonoRepository.findAll(
                (root, query, cb) -> cb.and(
                        cb.equal(root.get("cuentaPagar").get("id"), data.getId()),
                        cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()),
                        cb.notEqual(root.get("estado"), EstadoAbonoEnum.ANULADO)
                )
        );

        return abonoEntityMapper.toDomainCollection(abonos);
    }
}

package com.agrosync.application.usecase.usuarios.rulesvalidator.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioDetalladoFiltroDTO;
import com.agrosync.application.usecase.usuarios.rulesvalidator.ObtenerUsuarioDetalladoRulesValidator;
import com.agrosync.crosscutting.exception.custom.AgroSyncException;
import com.agrosync.crosscutting.exception.enums.Layer;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ObtenerUsuarioDetalladoRulesValidatorImpl implements ObtenerUsuarioDetalladoRulesValidator {

    private final SuscripcionExisteRule suscripcionExisteRule;
    private final UsuarioIdExisteRule usuarioIdExisteRule;

    public ObtenerUsuarioDetalladoRulesValidatorImpl(SuscripcionExisteRule suscripcionExisteRule, UsuarioIdExisteRule usuarioIdExisteRule) {
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.usuarioIdExisteRule = usuarioIdExisteRule;
    }

    @Override
    public void validar(UsuarioDetalladoFiltroDTO filtro) {
        suscripcionExisteRule.validate(filtro.getSuscripcionId());
        usuarioIdExisteRule.validate(filtro.getUsuarioId());

        if (filtro == null) {
            throw new AgroSyncException("El filtro del usuario es requerido", Layer.DOMAIN);
        }

        LocalDate inicio = filtro.getFechaInicio();
        LocalDate fin = filtro.getFechaFin();

        if (inicio != null && fin != null && inicio.isAfter(fin)) {
            throw new AgroSyncException("La fecha inicial no puede ser mayor que la fecha final", Layer.DOMAIN);
        }

    }

}

package com.agrosync.application.usecase.animales.rulesvalidator.impl;


import com.agrosync.application.usecase.animales.rulesvalidator.RegistrarNuevoAnimalRulesValidator;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.rules.NumeroAnimalUnicoRule;
import com.agrosync.domain.animales.rules.PesoValidoRule;
import com.agrosync.domain.animales.rules.PrecioCompraValidoRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevoAnimalRulesValidatorImpl implements RegistrarNuevoAnimalRulesValidator {

    private final PrecioCompraValidoRule precioCompraValidoRule;
    private final PesoValidoRule pesoValidoRule;
    private final NumeroAnimalUnicoRule numeroAnimalUnicoRule;
    private final SuscripcionExisteRule suscripcionExisteRule;

    public RegistrarNuevoAnimalRulesValidatorImpl(PrecioCompraValidoRule precioCompraValidoRule, PesoValidoRule pesoValidoRule, NumeroAnimalUnicoRule numeroAnimalUnicoRule, SuscripcionExisteRule suscripcionExisteRule) {
        this.precioCompraValidoRule = precioCompraValidoRule;
        this.pesoValidoRule = pesoValidoRule;
        this.numeroAnimalUnicoRule = numeroAnimalUnicoRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
    }

    @Override
    public void validar(AnimalDomain data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        numeroAnimalUnicoRule.validate(data);
        pesoValidoRule.validate(data.getPeso());
        precioCompraValidoRule.validate(data.getPrecioKiloCompra());
    }
}

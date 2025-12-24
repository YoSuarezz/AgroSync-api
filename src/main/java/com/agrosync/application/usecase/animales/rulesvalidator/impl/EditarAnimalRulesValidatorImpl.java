package com.agrosync.application.usecase.animales.rulesvalidator.impl;

import com.agrosync.application.usecase.animales.rulesvalidator.EditarAnimalRulesValidator;
import com.agrosync.domain.IdConSuscripcion;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.rules.AnimalEditableRule;
import com.agrosync.domain.animales.rules.IdentificadorAnimalExisteRule;
import com.agrosync.domain.animales.rules.PesoValidoRule;
import com.agrosync.domain.animales.rules.PrecioCompraValidoRule;
import org.springframework.stereotype.Service;

@Service
public class EditarAnimalRulesValidatorImpl implements EditarAnimalRulesValidator {

    private final IdentificadorAnimalExisteRule identificadorAnimalExisteRule;
    private final AnimalEditableRule animalEditableRule;
    private final PesoValidoRule pesoValidoRule;
    private final PrecioCompraValidoRule precioCompraValidoRule;

    public EditarAnimalRulesValidatorImpl(IdentificadorAnimalExisteRule identificadorAnimalExisteRule,
            AnimalEditableRule animalEditableRule,
            PesoValidoRule pesoValidoRule,
            PrecioCompraValidoRule precioCompraValidoRule) {
        this.identificadorAnimalExisteRule = identificadorAnimalExisteRule;
        this.animalEditableRule = animalEditableRule;
        this.pesoValidoRule = pesoValidoRule;
        this.precioCompraValidoRule = precioCompraValidoRule;
    }

    @Override
    public void validar(AnimalDomain data) {
        identificadorAnimalExisteRule.validate(IdConSuscripcion.of(data.getId(), data.getSuscripcionId()));
        animalEditableRule.validate(data);

        if (data.getPeso() != null) {
            pesoValidoRule.validate(data.getPeso());
        }
        if (data.getPrecioKiloCompra() != null) {
            precioCompraValidoRule.validate(data.getPrecioKiloCompra());
        }
    }
}

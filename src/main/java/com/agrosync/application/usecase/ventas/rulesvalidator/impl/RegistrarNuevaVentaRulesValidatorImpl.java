package com.agrosync.application.usecase.ventas.rulesvalidator.impl;

import com.agrosync.application.usecase.ventas.rulesvalidator.RegistrarNuevaVentaRulesValidator;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.animales.rules.IdentificadorAnimalValidoRule;
import com.agrosync.domain.animales.rules.PrecioVentaValidoRule;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import com.agrosync.domain.usuarios.rules.UsuarioClienteValidoRule;
import com.agrosync.domain.usuarios.rules.UsuarioIdExisteRule;
import com.agrosync.domain.ventas.VentaDomain;
import com.agrosync.domain.ventas.exceptions.VentaSinAnimalesException;
import com.agrosync.domain.ventas.rules.AnimalesVentaUnicosRule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegistrarNuevaVentaRulesValidatorImpl implements RegistrarNuevaVentaRulesValidator {

    private final UsuarioIdExisteRule usuarioIdExisteRule;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final PrecioVentaValidoRule precioVentaValidoRule;
    private final IdentificadorAnimalValidoRule identificadorAnimalValidoRule;
    private final AnimalesVentaUnicosRule animalesVentaUnicosRule;
    private final UsuarioClienteValidoRule usuarioClienteValidoRule;

    public RegistrarNuevaVentaRulesValidatorImpl(UsuarioIdExisteRule usuarioIdExisteRule,
                                                 SuscripcionExisteRule suscripcionExisteRule,
                                                 PrecioVentaValidoRule precioVentaValidoRule,
                                                 IdentificadorAnimalValidoRule identificadorAnimalValidoRule,
                                                 AnimalesVentaUnicosRule animalesVentaUnicosRule,
                                                 UsuarioClienteValidoRule usuarioClienteValidoRule) {
        this.usuarioIdExisteRule = usuarioIdExisteRule;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.precioVentaValidoRule = precioVentaValidoRule;
        this.identificadorAnimalValidoRule = identificadorAnimalValidoRule;
        this.animalesVentaUnicosRule = animalesVentaUnicosRule;
        this.usuarioClienteValidoRule = usuarioClienteValidoRule;
    }

    @Override
    public void validar(VentaDomain data) {
        if (ObjectHelper.isNull(data)) {
            throw VentaSinAnimalesException.create();
        }

        UUID clienteId = ObjectHelper.isNull(data.getCliente())
                ? UUIDHelper.getDefault()
                : data.getCliente().getId();
        usuarioIdExisteRule.validate(clienteId);
        usuarioClienteValidoRule.validate(clienteId);
        suscripcionExisteRule.validate(data.getSuscripcionId());

        List<AnimalDomain> animales = data.getAnimales();
        if (ObjectHelper.isNull(animales) || animales.isEmpty()) {
            throw VentaSinAnimalesException.create();
        }

        animalesVentaUnicosRule.validate(animales);

        animales.forEach(animal -> {
            identificadorAnimalValidoRule.validate(animal.getId());
            precioVentaValidoRule.validate(animal.getPrecioKiloVenta());
            if (UUIDHelper.isDefault(animal.getSuscripcionId())) {
                animal.setSuscripcionId(data.getSuscripcionId());
            }
        });
    }
}

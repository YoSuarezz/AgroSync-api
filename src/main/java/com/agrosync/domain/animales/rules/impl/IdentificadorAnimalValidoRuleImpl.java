package com.agrosync.domain.animales.rules.impl;

import com.agrosync.domain.animales.exceptions.IdentificadorAnimalInvalidoException;
import com.agrosync.domain.animales.rules.IdentificadorAnimalValidoRule;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class IdentificadorAnimalValidoRuleImpl implements IdentificadorAnimalValidoRule {

    @Override
    public void validate(UUID id) {
        if (id == null) {
            throw IdentificadorAnimalInvalidoException.create();
        }
        // Nota: Si el tipo de entrada ya es UUID, solo se verifica nullidad.
    }
}
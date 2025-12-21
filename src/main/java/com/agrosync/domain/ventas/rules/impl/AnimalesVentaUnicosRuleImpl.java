package com.agrosync.domain.ventas.rules.impl;

import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.ventas.exceptions.AnimalesDuplicadosEnVentaException;
import com.agrosync.domain.ventas.rules.AnimalesVentaUnicosRule;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AnimalesVentaUnicosRuleImpl implements AnimalesVentaUnicosRule {

    @Override
    public void validate(List<AnimalDomain> animales) {
        if (ObjectHelper.isNull(animales)) {
            return;
        }

        Set<UUID> identificadores = new HashSet<>();
        for (AnimalDomain animal : animales) {
            if (ObjectHelper.isNull(animal)) {
                continue;
            }
            UUID animalId = animal.getId();
            if (ObjectHelper.isNull(animalId)) {
                continue;
            }
            if (!identificadores.add(animalId)) {
                throw AnimalesDuplicadosEnVentaException.create();
            }
        }
    }
}

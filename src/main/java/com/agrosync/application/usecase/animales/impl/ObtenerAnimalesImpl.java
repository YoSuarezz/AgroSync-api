package com.agrosync.application.usecase.animales.impl;

import com.agrosync.application.primaryports.dto.animales.request.AnimalPageDTO;
import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.mapper.animales.AnimalEntityMapper;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.usecase.animales.ObtenerAnimales;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class ObtenerAnimalesImpl implements ObtenerAnimales {

    private final AnimalRepository animalRepository;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final AnimalEntityMapper animalEntityMapper;

    public ObtenerAnimalesImpl(AnimalRepository animalRepository,
                               SuscripcionExisteRule suscripcionExisteRule,
                               AnimalEntityMapper animalEntityMapper) {
        this.animalRepository = animalRepository;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.animalEntityMapper = animalEntityMapper;
    }

    @Override
    public Page<AnimalDomain> ejecutar (AnimalPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "numeroAnimal";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "ASC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<AnimalEntity> spec = buildSpecification(data);

        Page<AnimalEntity> entities = animalRepository.findAll(spec, pageable);

        return new PageImpl<>(
                animalEntityMapper.toDomainCollection(entities.getContent()),
                pageable,
                entities.getTotalElements()
        );
    }

    private Specification<AnimalEntity> buildSpecification(AnimalPageDTO data) {
        List<Specification<AnimalEntity>> specs = new ArrayList<>();

        ObtenerAnimalDTO animalFiltro = data.getAnimal();

        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        if (animalFiltro != null) {

            if (StringUtils.hasText(animalFiltro.getNumeroAnimal())) {
                String numeroAnimal = animalFiltro.getNumeroAnimal().toLowerCase().trim();
                specs.add((root, query, cb) ->
                        cb.like(cb.lower(root.get("numeroAnimal")), "%" + numeroAnimal + "%"));
            }

            if (animalFiltro.getEstado() != null) {
                specs.add((root, query, cb) -> cb.equal(root.get("estado"), animalFiltro.getEstado()));
            }

            if (animalFiltro.getSexo() != null) {
                specs.add((root, query, cb) -> cb.equal(root.get("sexo"), animalFiltro.getSexo()));
            }

            if (animalFiltro.getPeso() != null && animalFiltro.getPeso().compareTo(java.math.BigDecimal.ZERO) > 0) {
                specs.add((root, query, cb) -> cb.ge(root.get("peso"), animalFiltro.getPeso()));
            }
        }

        return Specification.allOf(specs);
    }
}

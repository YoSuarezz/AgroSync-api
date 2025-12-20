package com.agrosync.application.usecase.carteras.impl;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.mapper.carteras.CarteraEntityMapper;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.usecase.carteras.ObtenerCarteras;
import com.agrosync.application.usecase.carteras.rulesvalidator.ObtenerCarterasRulesValidator;
import com.agrosync.domain.carteras.CarteraDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ObtenerCarterasImpl implements ObtenerCarteras {

    private final CarteraRepository carteraRepository;
    private final ObtenerCarterasRulesValidator obtenerCarterasRulesValidator;
    private final CarteraEntityMapper carteraEntityMapper;

    public ObtenerCarterasImpl(CarteraRepository carteraRepository, ObtenerCarterasRulesValidator obtenerCarterasRulesValidator, CarteraEntityMapper carteraEntityMapper) {
        this.carteraRepository = carteraRepository;
        this.obtenerCarterasRulesValidator = obtenerCarterasRulesValidator;
        this.carteraEntityMapper = carteraEntityMapper;
    }

    @Override
    public Page<CarteraDomain> ejecutar(CarteraPageDTO data) {
        obtenerCarterasRulesValidator.validar(data);
        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "usuario.nombre";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "ASC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<CarteraEntity> spec = buildSpecification(data);

        Page<CarteraEntity> entities = carteraRepository.findAll(spec, pageable);

        return entities.map(carteraEntityMapper::toDomain);
    }

    private Specification<CarteraEntity> buildSpecification(CarteraPageDTO data) {
        List<Specification<CarteraEntity>> specs = new ArrayList<>();

        // Filtro por suscripción (obligatorio)
        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        // Filtro por tipo de usuario
        if (data.getTipoUsuario() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("usuario").get("tipoUsuario"), data.getTipoUsuario()));
        }

        // Filtro por nombre de usuario (búsqueda parcial)
        if (data.getUsuario() != null && StringUtils.hasText(data.getUsuario().getNombre())) {
            List<String> palabrasNombre = Arrays.stream(data.getUsuario().getNombre().trim().split("\\s+"))
                    .filter(StringUtils::hasText)
                    .map(String::toLowerCase)
                    .toList();

            for (String palabra : palabrasNombre) {
                specs.add((root, query, cb) ->
                        cb.like(
                                cb.lower(root.get("usuario").get("nombre")),
                                "%" + palabra + "%"
                        )
                );
            }
        }

        return Specification.allOf(specs);
    }
}

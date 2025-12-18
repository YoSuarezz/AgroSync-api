package com.agrosync.application.usecase.carteras.impl;

import com.agrosync.application.primaryports.dto.carteras.request.CarteraPageDTO;
import com.agrosync.application.primaryports.dto.compras.request.CompraPageDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.mapper.carteras.CarteraEntityMapper;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.usecase.carteras.ObtenerCarteras;
import com.agrosync.application.usecase.carteras.rulesvalidator.ObtenerCarterasRulesValidator;
import com.agrosync.application.usecase.compras.rulesvalidator.ObtenerComprasRulesValidator;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.domain.compras.CompraDomain;
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
import java.util.UUID;

@Service
public class ObtenerCarterasImpl implements ObtenerCarteras {

    private final CarteraRepository carteraRepository;
    private final ObtenerCarterasRulesValidator obtenerCarterasRulesValidator;
    private final CarteraEntityMapper carteraEntityMapper;

    public ObtenerCarterasImpl(CarteraRepository carteraRepository, ObtenerCarterasRulesValidator obtenerCarterasRulesValidator, CarteraEntityMapper carterasEntityMapper) {
        this.carteraRepository = carteraRepository;
        this.obtenerCarterasRulesValidator = obtenerCarterasRulesValidator;
        this.carteraEntityMapper = carterasEntityMapper;
    }

    @Override
    public Page<CarteraDomain> ejecutar(CarteraPageDTO data) {
        obtenerCarterasRulesValidator.validar(data);
        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "fechaCompra";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "DESC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<CarteraEntity> spec = buildSpecification(data);

        Page<CarteraEntity> entities = carteraRepository.findAll(spec, pageable);

        return entities.map(carteraEntityMapper::toDomain);
    }

    private Specification<CarteraEntity> buildSpecification(CarteraPageDTO data) {
        List<Specification<CarteraEntity>> specs = new ArrayList<>();

        ObtenerUsuarioDTO usuarioFiltro = data.getUsuario();

        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        System.out.println(usuarioFiltro.getNombre());
        if (usuarioFiltro != null && StringUtils.hasText(usuarioFiltro.getNombre())) {
            List<String> palabrasNombre = Arrays.stream(usuarioFiltro.getNombre().trim().split("\\s+"))
                    .filter(StringUtils::hasText)
                    .map(String::toLowerCase)
                    .toList();

            for (String palabra : palabrasNombre) {
                specs.add((root, query, cb) ->
                        cb.like(
                                cb.lower(root.get("usuario").get("nombre")),
                                "%" + palabra.toLowerCase() + "%"
                        )
                );
            }
        }

        return Specification.allOf(specs);
    }
}

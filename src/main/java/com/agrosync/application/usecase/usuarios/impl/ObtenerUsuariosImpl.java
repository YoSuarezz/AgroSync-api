package com.agrosync.application.usecase.usuarios.impl;

import com.agrosync.application.primaryports.dto.usuarios.request.UsuarioPageDTO;
import com.agrosync.application.primaryports.dto.usuarios.response.ObtenerUsuarioDTO;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.ObtenerUsuarios;
import com.agrosync.domain.usuarios.UsuarioDomain;
import com.agrosync.domain.suscripcion.rules.SuscripcionExisteRule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerUsuariosImpl implements ObtenerUsuarios {

    private final UsuarioRepository usuarioRepository;
    private final SuscripcionExisteRule suscripcionExisteRule;
    private final UsuarioEntityMapper usuarioEntityMapper;

    public ObtenerUsuariosImpl(UsuarioRepository usuarioRepository, SuscripcionExisteRule suscripcionExisteRule, UsuarioEntityMapper usuarioEntityMapper) {
        this.usuarioRepository = usuarioRepository;
        this.suscripcionExisteRule = suscripcionExisteRule;
        this.usuarioEntityMapper = usuarioEntityMapper;
    }

    @Override
    public Page<UsuarioDomain> ejecutar(UsuarioPageDTO data) {
        suscripcionExisteRule.validate(data.getSuscripcionId());
        String sortBy = StringUtils.hasText(data.getSortBy()) ? data.getSortBy() : "nombre";
        String sortDirection = StringUtils.hasText(data.getSortDirection()) ? data.getSortDirection() : "ASC";

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(data.getPage(), data.getSize(), sort);

        Specification<UsuarioEntity> spec = buildSpecification(data);

        Page<UsuarioEntity> entities = usuarioRepository.findAll(spec, pageable);

        return new PageImpl<>(
                usuarioEntityMapper.toDomainCollection(entities.getContent()),
                pageable,
                entities.getTotalElements()
        );
    }

    private Specification<UsuarioEntity> buildSpecification(UsuarioPageDTO data) {
        List<Specification<UsuarioEntity>> specs = new ArrayList<>();

        ObtenerUsuarioDTO usuarioFiltro = data.getUsuario();
        var tipoFiltro = data.getTipoUsuario();

        if (usuarioFiltro != null && StringUtils.hasText(usuarioFiltro.getNombre())) {
            List<String> palabrasNombre = Arrays.stream(usuarioFiltro.getNombre().trim().split("\\s+"))
                    .filter(StringUtils::hasText)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            for (String palabra : palabrasNombre) {
                specs.add((root, query, cb) -> cb.like(cb.lower(root.get("nombre")), "%" + palabra + "%"));
            }
        }

        if (usuarioFiltro != null && StringUtils.hasText(usuarioFiltro.getTelefono())) {
            specs.add((root, query, cb) -> cb.like(cb.lower(root.get("telefono")), "%" + usuarioFiltro.getTelefono().toLowerCase() + "%"));
        }

        if (tipoFiltro != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("tipoUsuario"), tipoFiltro));
        }

        if (data.getSuscripcionId() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), data.getSuscripcionId()));
        }

        return Specification.allOf(specs);
    }
}

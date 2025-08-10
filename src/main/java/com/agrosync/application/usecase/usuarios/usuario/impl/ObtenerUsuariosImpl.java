package com.agrosync.application.usecase.usuarios.usuario.impl;

import com.agrosync.application.primaryports.dto.usuarios.UsuarioRequest;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.mapper.usuarios.UsuarioEntityMapper;
import com.agrosync.application.secondaryports.repository.UsuarioRepository;
import com.agrosync.application.usecase.usuarios.usuario.ObtenerUsuarios;
import com.agrosync.domain.usuarios.UsuarioDomain;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ObtenerUsuariosImpl implements ObtenerUsuarios {

    private final UsuarioRepository usuarioRepository;

    public ObtenerUsuariosImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Page<UsuarioDomain> ejecutar(UsuarioRequest data) {
        Pageable pageable = data.getPageable();
        String nombre = data.getUsuario().getNombre();

        return usuarioRepository.findAll((Root<UsuarioEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null && !nombre.isBlank()) {
                var palabrasNombre = Arrays.stream(nombre.trim().split("\\s+"))
                        .map(String::toLowerCase)
                        .toList();
                Path<String> nombrePath = root.get("nombre");
                for (String palabra : palabrasNombre) {
                    predicates.add(cb.like(cb.lower(nombrePath), "%" + palabra + "%"));
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(UsuarioEntityMapper.INSTANCE::toDomain);
    }
}

package com.sedikev.application.service;

import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.infrastructure.adapter.entity.UsuarioEntity;
import com.sedikev.domain.repository.UsuarioRepository;
import com.sedikev.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Transactional
    public UsuarioDomain save(UsuarioDomain usuarioDomain) {
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioDomain);
        UsuarioEntity usuarioSaved = usuarioRepository.save(usuarioEntity);
        return usuarioMapper.toDomain(usuarioSaved);
    }

    @Transactional(readOnly = true)
    public UsuarioDomain findById(Long id) {
        return usuarioMapper.toDomain(usuarioRepository.findById(id).orElse(null));
    }

    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDomain> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDomain)
                .collect(Collectors.toList());
    }
}

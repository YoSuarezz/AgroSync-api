package com.sedikev.application.usecase;

import com.sedikev.domain.entity.UsuarioEntity;
import com.sedikev.domain.repository.UsuarioRepository;
import com.sedikev.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioEntity save(UsuarioEntity usuarioEntity) {
        return usuarioRepository.save(usuarioEntity);
    }

    @Transactional(readOnly = true)
    public UsuarioEntity findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UsuarioEntity> findAll() {
        return (List<UsuarioEntity>) usuarioRepository.findAll();
    }
}

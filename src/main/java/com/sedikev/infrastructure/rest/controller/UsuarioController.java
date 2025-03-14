package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @PostMapping(path = "usuario")
    public UsuarioDTO create(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDomain usuarioDomain = usuarioMapper.toDomain(usuarioDTO);
        UsuarioDomain usuarioSaved = usuarioService.save(usuarioDomain);
        return usuarioMapper.toDTO(usuarioSaved);
    }

    @PutMapping(path = "usuario")
    public UsuarioDTO update(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDomain usuarioDomain = usuarioMapper.toDomain(usuarioDTO);
        UsuarioDomain usuarioSaved = usuarioService.save(usuarioDomain);
        return usuarioMapper.toDTO(usuarioSaved);
    }

    @DeleteMapping(path = "usuario/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
    }

    @GetMapping(path = "usuario/{id}")
    public UsuarioDTO findById(@PathVariable Long id) {
        return usuarioMapper.toDTO(usuarioService.findById(id));
    }

    @GetMapping(path = "usuarios")
    public List<UsuarioDTO> findAll(){
        return usuarioService.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

}
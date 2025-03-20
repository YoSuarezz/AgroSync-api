package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping(path = "usuario")
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDomain usuarioDomain = usuarioMapper.toDomain(usuarioDTO);
        UsuarioDomain usuarioSaved = usuarioService.save(usuarioDomain);
        UsuarioDTO responseDTO = usuarioMapper.toDTO(usuarioSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(path = "usuario")
    public ResponseEntity<UsuarioDTO> update(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDomain usuarioDomain = usuarioMapper.toDomain(usuarioDTO);
        UsuarioDomain usuarioSaved = usuarioService.save(usuarioDomain);
        UsuarioDTO responseDTO = usuarioMapper.toDTO(usuarioSaved);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "usuario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "usuario/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) {
        UsuarioDomain usuarioDomain = usuarioService.findById(id);
        if (usuarioDomain == null) {
            return ResponseEntity.notFound().build();
        }
        UsuarioDTO responseDTO = usuarioMapper.toDTO(usuarioDomain);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(path = "usuarios")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDomain> usuarioDomains = usuarioService.findAll();
        List<UsuarioDTO> responseDTOs = usuarioDomains.stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
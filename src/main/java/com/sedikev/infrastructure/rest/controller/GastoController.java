package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.GastoDTO;
import com.sedikev.application.mapper.GastoMapper;
import com.sedikev.domain.model.GastoDomain;
import com.sedikev.domain.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private GastoMapper gastoMapper;

    @PostMapping(path = "gasto")
    public GastoDTO create(@RequestBody GastoDTO gastoDTO) {
        GastoDomain gastoDomain = gastoMapper.toDomain(gastoDTO);
        GastoDomain gastoSaved = gastoService.save(gastoDomain);
        return gastoMapper.toDTO(gastoSaved);
    }

    @PutMapping(path = "gasto")
    public GastoDTO update(@RequestBody GastoDTO gastoDTO) {
        GastoDomain gastoDomain = gastoMapper.toDomain(gastoDTO);
        GastoDomain gastoSaved = gastoService.save(gastoDomain);
        return gastoMapper.toDTO(gastoSaved);
    }

    @DeleteMapping(path = "gasto/{id}")
    public void delete(@PathVariable Long id) {
        gastoService.deleteById(id);
    }

    @GetMapping(path = "gasto/{id}")
    public GastoDTO findById(@PathVariable Long id) {
        return gastoMapper.toDTO(gastoService.findById(id));
    }

    @GetMapping(path = "gastos")
    public List<GastoDTO> findAll(){
        return gastoService.findAll().stream()
                .map(gastoMapper::toDTO)
                .collect(Collectors.toList());
    }

}

package com.agrosync.infrastructure.primaryadapters.controller.antiguos;

import com.agrosync.application.primaryports.dto.GastoDTO;
import com.agrosync.application.primaryports.mapper.GastoMapper;
import com.agrosync.domain.model.GastoDomain;
import com.agrosync.domain.service.GastoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class GastoController {

    private final GastoService gastoService;
    private final GastoMapper gastoMapper;

    @PostMapping(path = "gasto")
    public ResponseEntity<GastoDTO> create(@RequestBody GastoDTO gastoDTO) {
        GastoDomain gastoDomain = gastoMapper.toDomain(gastoDTO);
        GastoDomain gastoSaved = gastoService.save(gastoDomain);
        GastoDTO responseDTO = gastoMapper.toDTO(gastoSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(path = "gasto")
    public ResponseEntity<GastoDTO> update(@RequestBody GastoDTO gastoDTO) {
        GastoDomain gastoDomain = gastoMapper.toDomain(gastoDTO);
        GastoDomain gastoSaved = gastoService.save(gastoDomain);
        GastoDTO responseDTO = gastoMapper.toDTO(gastoSaved);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "gasto/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gastoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "gasto/{id}")
    public ResponseEntity<GastoDTO> findById(@PathVariable Long id) {
        GastoDomain gastoDomain = gastoService.findById(id);
        if (gastoDomain == null) {
            return ResponseEntity.notFound().build();
        }
        GastoDTO responseDTO = gastoMapper.toDTO(gastoDomain);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(path = "gastos")
    public ResponseEntity<List<GastoDTO>> findAll() {
        List<GastoDomain> gastoDomains = gastoService.findAll();
        List<GastoDTO> responseDTOs = gastoDomains.stream()
                .map(gastoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}

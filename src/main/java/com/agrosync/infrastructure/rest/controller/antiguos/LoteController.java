package com.agrosync.infrastructure.rest.controller.antiguos;

import com.agrosync.application.primaryports.dto.LoteDTO;
import com.agrosync.application.primaryports.mapper.LoteMapper;
import com.agrosync.domain.model.LoteDomain;
import com.agrosync.domain.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class LoteController {

    private final LoteService loteService;
    private final LoteMapper loteMapper;

    @PostMapping(path = "lote")
    public ResponseEntity<LoteDTO> create(@RequestBody LoteDTO loteDTO) {
        LoteDomain loteDomain = loteMapper.toDomain(loteDTO);
        LoteDomain loteSaved = loteService.save(loteDomain);
        LoteDTO responseDTO = loteMapper.toDTO(loteSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(path = "lote")
    public ResponseEntity<LoteDTO> update(@RequestBody LoteDTO loteDTO) {
        LoteDomain loteDomain = loteMapper.toDomain(loteDTO);
        LoteDomain loteSaved = loteService.save(loteDomain);
        LoteDTO responseDTO = loteMapper.toDTO(loteSaved);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "lote/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "lote/{id}")
    public ResponseEntity<LoteDTO> findById(@PathVariable Long id) {
        LoteDomain loteDomain = loteService.findById(id);
        if (loteDomain == null) {
            return ResponseEntity.notFound().build();
        }
        LoteDTO responseDTO = loteMapper.toDTO(loteDomain);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(path = "lotes")
    public ResponseEntity<List<LoteDTO>> findAll() {
        List<LoteDomain> loteDomains = loteService.findAll();
        List<LoteDTO> responseDTOs = loteDomains.stream()
                .map(loteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
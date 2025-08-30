package com.agrosync.infrastructure.primaryadapters.controller.antiguos;

import com.agrosync.application.primaryports.dto.CarteraDTO;
import com.agrosync.application.primaryports.mapper.CarteraMapper;
import com.agrosync.domain.model.CarteraDomain;
import com.agrosync.domain.service.CarteraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class CarteraController {

    private final CarteraService carteraService;
    private final CarteraMapper carteraMapper;

    @PostMapping(path = "cartera")
    public ResponseEntity<CarteraDTO> create(@RequestBody CarteraDTO carteraDTO) {
        CarteraDomain carteraDomain = carteraMapper.toDomain(carteraDTO);
        CarteraDomain carteraSaved = carteraService.save(carteraDomain);
        CarteraDTO responseDTO = carteraMapper.toDTO(carteraSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(path = "cartera")
    public ResponseEntity<CarteraDTO> update(@RequestBody CarteraDTO carteraDTO) {
        CarteraDomain carteraDomain = carteraMapper.toDomain(carteraDTO);
        CarteraDomain carteraSaved = carteraService.save(carteraDomain);
        CarteraDTO responseDTO = carteraMapper.toDTO(carteraSaved);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "cartera/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carteraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "cartera/{id}")
    public ResponseEntity<CarteraDTO> findById(@PathVariable Long id) {
        CarteraDomain carteraDomain = carteraService.findById(id);
        if (carteraDomain == null) {
            return ResponseEntity.notFound().build();
        }
        CarteraDTO responseDTO = carteraMapper.toDTO(carteraDomain);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(path = "carteras")
    public ResponseEntity<List<CarteraDTO>> findAll() {
        List<CarteraDomain> carteraDomains = carteraService.findAll();
        List<CarteraDTO> responseDTOs = carteraDomains.stream()
                .map(carteraMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}
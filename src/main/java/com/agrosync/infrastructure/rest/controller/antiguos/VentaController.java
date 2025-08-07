package com.agrosync.infrastructure.rest.controller.antiguos;

import com.agrosync.application.primaryports.dto.VentaDTO;
import com.agrosync.application.primaryports.mapper.VentaMapper;
import com.agrosync.domain.model.VentaDomain;
import com.agrosync.domain.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final VentaMapper ventaMapper;

    @PostMapping(path = "venta")
    public ResponseEntity<VentaDTO> create(@RequestBody VentaDTO ventaDTO) {
        VentaDomain ventaDomain = ventaMapper.toDomain(ventaDTO);
        VentaDomain ventaSaved = ventaService.save(ventaDomain);
        VentaDTO responseDTO = ventaMapper.toDTO(ventaSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(path = "venta")
    public ResponseEntity<VentaDTO> update(@RequestBody VentaDTO ventaDTO) {
        VentaDomain ventaDomain = ventaMapper.toDomain(ventaDTO);
        VentaDomain ventaSaved = ventaService.save(ventaDomain);
        VentaDTO responseDTO = ventaMapper.toDTO(ventaSaved);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping(path = "venta/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "venta/{id}")
    public ResponseEntity<VentaDTO> findById(@PathVariable Long id) {
        VentaDomain ventaDomain = ventaService.findById(id);
        if (ventaDomain == null) {
            return ResponseEntity.notFound().build();
        }
        VentaDTO responseDTO = ventaMapper.toDTO(ventaDomain);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(path = "ventas")
    public ResponseEntity<List<VentaDTO>> findAll() {
        List<VentaDomain> ventaDomains = ventaService.findAll();
        List<VentaDTO> responseDTOs = ventaDomains.stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }
}

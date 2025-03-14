package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.application.dto.VentaDTO;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.mapper.VentaMapper;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.domain.service.CarteraService;
import com.sedikev.domain.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaMapper ventaMapper;

    @PostMapping(path = "venta")
    public VentaDTO create(@RequestBody VentaDTO ventaDTO) {
        VentaDomain ventaDomain = ventaMapper.toDomain(ventaDTO);
        VentaDomain ventaSaved = ventaService.save(ventaDomain);
        return ventaMapper.toDTO(ventaSaved);
    }

    @PutMapping(path = "venta")
    public VentaDTO update(@RequestBody VentaDTO ventaDTO) {
        VentaDomain ventaDomain = ventaMapper.toDomain(ventaDTO);
        VentaDomain ventaSaved = ventaService.save(ventaDomain);
        return ventaMapper.toDTO(ventaSaved);
    }

    @DeleteMapping(path = "venta/{id}")
    public void delete(@PathVariable Long id) {
        ventaService.deleteById(id);
    }

    @GetMapping(path = "venta/{id}")
    public VentaDTO findById(@PathVariable Long id) {
        return ventaMapper.toDTO(ventaService.findById(id));
    }

    @GetMapping(path = "ventas")
    public List<VentaDTO> findAll(){
        return ventaService.findAll().stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

}

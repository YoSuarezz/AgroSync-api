package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.domain.model.CarteraDomain;
import com.sedikev.domain.service.CarteraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class CarteraController {

    @Autowired
    private CarteraService carteraService;

    @Autowired
    private CarteraMapper carteraMapper;

    @PostMapping(path = "cartera")
    public CarteraDTO create(@RequestBody CarteraDTO carteraDTO) {
        CarteraDomain carteraDomain = carteraMapper.toDomain(carteraDTO);
        CarteraDomain carteraSaved = carteraService.save(carteraDomain);
        return carteraMapper.toDTO(carteraSaved);
    }

    @PutMapping(path = "cartera")
    public CarteraDTO update(@RequestBody CarteraDTO carteraDTO) {
        CarteraDomain carteraDomain = carteraMapper.toDomain(carteraDTO);
        CarteraDomain carteraSaved = carteraService.save(carteraDomain);
        return carteraMapper.toDTO(carteraSaved);
    }

    @DeleteMapping(path = "cartera/{id}")
    public void delete(@PathVariable Long id) {
        carteraService.deleteById(id);
    }

    @GetMapping(path = "cartera/{id}")
    public CarteraDTO findById(@PathVariable Long id) {
        return carteraMapper.toDTO(carteraService.findById(id));
    }

    @GetMapping(path = "carteras")
    public List<CarteraDTO> findAll(){
        return carteraService.findAll().stream()
                .map(carteraMapper::toDTO)
                .collect(Collectors.toList());
    }

}

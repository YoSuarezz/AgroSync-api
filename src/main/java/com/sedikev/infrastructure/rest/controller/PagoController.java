package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.PagoDTO;
import com.sedikev.application.mapper.PagoMapper;
import com.sedikev.domain.model.PagoDomain;
import com.sedikev.domain.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired
    private PagoMapper pagoMapper;

    @PostMapping(path = "pago")
    public PagoDTO create(@RequestBody PagoDTO pagoDTO) {
        PagoDomain pagoDomain = pagoMapper.toDomain(pagoDTO);
        PagoDomain pagoSaved = pagoService.save(pagoDomain);
        return pagoMapper.toDTO(pagoSaved);
    }

    @PutMapping(path = "pago")
    public PagoDTO update(@RequestBody PagoDTO pagoDTO) {
        PagoDomain pagoDomain = pagoMapper.toDomain(pagoDTO);
        PagoDomain pagoSaved = pagoService.save(pagoDomain);
        return pagoMapper.toDTO(pagoSaved);
    }

    @DeleteMapping(path = "pago/{id}")
    public void delete(@PathVariable Long id) {
        pagoService.deleteById(id);
    }

    @GetMapping(path = "pago/{id}")
    public PagoDTO findById(@PathVariable Long id) {
        return pagoMapper.toDTO(pagoService.findById(id));
    }

    @GetMapping(path = "pagos")
    public List<PagoDTO> findAll(){
        return pagoService.findAll().stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

}

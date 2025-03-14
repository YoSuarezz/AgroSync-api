package com.sedikev.infrastructure.rest.controller;

import com.sedikev.application.dto.LoteDTO;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.service.LoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
public class LoteController {

    @Autowired
    private LoteService loteService;

    @Autowired
    private LoteMapper loteMapper;

    @PostMapping(path = "lote")
    public LoteDTO create(@RequestBody LoteDTO loteDTO) {
        LoteDomain loteDomain = loteMapper.toDomain(loteDTO);
        LoteDomain loteSaved = loteService.save(loteDomain);
        return loteMapper.toDTO(loteSaved);
    }

    @PutMapping(path = "lote")
    public LoteDTO update(@RequestBody LoteDTO loteDTO) {
        LoteDomain loteDomain = loteMapper.toDomain(loteDTO);
        LoteDomain loteSaved = loteService.save(loteDomain);
        return loteMapper.toDTO(loteSaved);
    }

    @DeleteMapping(path = "lote/{id}")
    public void delete(@PathVariable Long id) {
        loteService.deleteById(id);
    }

    @GetMapping(path = "lote/{id}")
    public LoteDTO findById(@PathVariable Long id) {
        return loteMapper.toDTO(loteService.findById(id));
    }

    @GetMapping(path = "lotes")
    public List<LoteDTO> findAll(){
        return loteService.findAll().stream()
                .map(loteMapper::toDTO)
                .collect(Collectors.toList());
    }

}

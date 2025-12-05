package com.agrosync.application.usecase.compras.impl;

import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.usecase.compras.RegistrarNuevaCompra;
import com.agrosync.domain.compras.CompraDomain;
import org.springframework.stereotype.Service;

@Service
public class RegistrarNuevaCompraImpl implements RegistrarNuevaCompra {

    private final CompraRepository compraRepository;
    private final CompraEntityMapper compraEntityMapper;

    public RegistrarNuevaCompraImpl(CompraRepository compraRepository, CompraEntityMapper compraEntityMapper) {
        this.compraRepository = compraRepository;
        this.compraEntityMapper = compraEntityMapper;
    }

    @Override
    public void ejecutar(CompraDomain data) {
        CompraEntity compra = compraEntityMapper.toEntity(data);
        compra.getLote().setCompra(compra);
        compra.getLote().setSuscripcion(compra.getSuscripcion());
        compraRepository.save(compra);
    }
}

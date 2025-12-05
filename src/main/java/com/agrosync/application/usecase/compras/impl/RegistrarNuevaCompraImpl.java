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

    public RegistrarNuevaCompraImpl(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Override
    public void ejecutar(CompraDomain data) {
        CompraEntity compra = CompraEntityMapper.INSTANCE.toEntity(data);
        compraRepository.save(compra);
    }
}

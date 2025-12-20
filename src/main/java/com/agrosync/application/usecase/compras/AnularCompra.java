package com.agrosync.application.usecase.compras;

import com.agrosync.application.usecase.UseCaseWithoutReturn;
import com.agrosync.domain.compras.CompraDomain;

/**
 * Caso de uso para anular una compra existente.
 * Revierte todos los efectos: animales, cuenta por pagar y cartera del proveedor.
 */
public interface AnularCompra extends UseCaseWithoutReturn<CompraDomain> {
}


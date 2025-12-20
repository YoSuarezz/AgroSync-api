package com.agrosync.application.usecase.ventas;

import com.agrosync.application.usecase.UseCaseWithoutReturn;
import com.agrosync.domain.ventas.VentaDomain;

/**
 * Caso de uso para anular una venta existente.
 * Revierte todos los efectos: animales vuelven a DISPONIBLE, cuenta por cobrar y cartera del cliente.
 */
public interface AnularVenta extends UseCaseWithoutReturn<VentaDomain> {
}


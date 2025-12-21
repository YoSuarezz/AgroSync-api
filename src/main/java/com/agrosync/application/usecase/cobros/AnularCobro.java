package com.agrosync.application.usecase.cobros;

import com.agrosync.application.usecase.UseCaseWithoutReturn;
import com.agrosync.domain.cobros.CobroDomain;

/**
 * Caso de uso para anular un cobro existente.
 * Revierte el efecto del cobro: restaura saldo en cuenta por cobrar y actualiza cartera.
 */
public interface AnularCobro extends UseCaseWithoutReturn<CobroDomain> {
}


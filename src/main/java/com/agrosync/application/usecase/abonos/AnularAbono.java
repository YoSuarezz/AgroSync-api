package com.agrosync.application.usecase.abonos;

import com.agrosync.application.usecase.UseCaseWithoutReturn;
import com.agrosync.domain.abonos.AbonoDomain;

/**
 * Caso de uso para anular un abono existente.
 * Revierte el efecto del abono: restaura saldo en cuenta por pagar y actualiza cartera.
 */
public interface AnularAbono extends UseCaseWithoutReturn<AbonoDomain> {
}


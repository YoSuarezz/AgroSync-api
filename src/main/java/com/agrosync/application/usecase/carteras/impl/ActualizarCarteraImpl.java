package com.agrosync.application.usecase.carteras.impl;

import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ActualizarCarteraImpl implements ActualizarCartera {

    private final CarteraRepository carteraRepository;

    public ActualizarCarteraImpl(CarteraRepository carteraRepository) {
        this.carteraRepository = carteraRepository;
    }

    @Override
    public void incrementarCuentasCobrar(UUID usuarioId, UUID suscripcionId, BigDecimal monto) {
        if (ObjectHelper.isNull(usuarioId) || ObjectHelper.isNull(suscripcionId) || ObjectHelper.isNull(monto)) {
            return;
        }

        Optional<CarteraEntity> carteraOpt = carteraRepository.findByUsuario_IdAndSuscripcion_Id(usuarioId, suscripcionId);
        if (carteraOpt.isPresent()) {
            CarteraEntity cartera = carteraOpt.get();
            BigDecimal totalActual = ObjectHelper.getDefault(cartera.getTotalCuentasCobrar(), BigDecimal.ZERO);
            cartera.setTotalCuentasCobrar(totalActual.add(monto));

            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.add(monto));

            carteraRepository.save(cartera);
        }
    }

    @Override
    public void incrementarCuentasPagar(UUID usuarioId, UUID suscripcionId, BigDecimal monto) {
        if (ObjectHelper.isNull(usuarioId) || ObjectHelper.isNull(suscripcionId) || ObjectHelper.isNull(monto)) {
            return;
        }

        Optional<CarteraEntity> carteraOpt = carteraRepository.findByUsuario_IdAndSuscripcion_Id(usuarioId, suscripcionId);
        if (carteraOpt.isPresent()) {
            CarteraEntity cartera = carteraOpt.get();
            BigDecimal totalActual = ObjectHelper.getDefault(cartera.getTotalCuentasPagar(), BigDecimal.ZERO);
            cartera.setTotalCuentasPagar(totalActual.add(monto));

            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.subtract(monto));

            carteraRepository.save(cartera);
        }
    }

    @Override
    public void reducirCuentasCobrarPorCobro(UUID usuarioId, UUID suscripcionId, BigDecimal montoCobro) {
        if (ObjectHelper.isNull(usuarioId) || ObjectHelper.isNull(suscripcionId) || ObjectHelper.isNull(montoCobro)) {
            return;
        }

        Optional<CarteraEntity> carteraOpt = carteraRepository.findByUsuario_IdAndSuscripcion_Id(usuarioId, suscripcionId);
        if (carteraOpt.isPresent()) {
            CarteraEntity cartera = carteraOpt.get();

            BigDecimal totalCuentasPagar = ObjectHelper.getDefault(cartera.getTotalCuentasPagar(), BigDecimal.ZERO);
            BigDecimal nuevoTotal = totalCuentasPagar.subtract(montoCobro);
            cartera.setTotalCuentasPagar(nuevoTotal.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : nuevoTotal);

            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.add(montoCobro));

            carteraRepository.save(cartera);
        }
    }

    @Override
    public void reducirCuentasPagarPorAbono(UUID usuarioId, UUID suscripcionId, BigDecimal montoAbono) {
        if (ObjectHelper.isNull(usuarioId) || ObjectHelper.isNull(suscripcionId) || ObjectHelper.isNull(montoAbono)) {
            return;
        }

        Optional<CarteraEntity> carteraOpt = carteraRepository.findByUsuario_IdAndSuscripcion_Id(usuarioId, suscripcionId);
        if (carteraOpt.isPresent()) {
            CarteraEntity cartera = carteraOpt.get();

            BigDecimal totalCuentasCobrar = ObjectHelper.getDefault(cartera.getTotalCuentasCobrar(), BigDecimal.ZERO);
            BigDecimal nuevoTotal = totalCuentasCobrar.subtract(montoAbono);
            cartera.setTotalCuentasCobrar(nuevoTotal.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : nuevoTotal);

            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.subtract(montoAbono));

            carteraRepository.save(cartera);
        }
    }
}


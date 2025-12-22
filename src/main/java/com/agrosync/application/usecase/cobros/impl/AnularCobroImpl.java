package com.agrosync.application.usecase.cobros.impl;

import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.cobros.AnularCobro;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.cobros.exceptions.CobroAutomaticoNoAnulableException;
import com.agrosync.domain.cobros.exceptions.CobroYaAnuladoException;
import com.agrosync.domain.cobros.exceptions.IdentificadorCobroNoExisteException;
import com.agrosync.domain.cobros.exceptions.MotivoAnulacionCobroRequeridoException;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class AnularCobroImpl implements AnularCobro {

    private final CobroRepository cobroRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final ActualizarCartera actualizarCartera;

    public AnularCobroImpl(CobroRepository cobroRepository,
                          CuentaCobrarRepository cuentaCobrarRepository,
                          ActualizarCartera actualizarCartera) {
        this.cobroRepository = cobroRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.actualizarCartera = actualizarCartera;
    }

    @Override
    public void ejecutar(CobroDomain data) {
        // 1. Validar que se proporcione motivo
        if (TextHelper.isEmpty(data.getMotivoAnulacion())) {
            throw MotivoAnulacionCobroRequeridoException.create();
        }

        // 2. Buscar el cobro
        CobroEntity cobro = cobroRepository
                .findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorCobroNoExisteException::create);

        // 3. Validar que no esté ya anulado
        if (cobro.getEstado() == EstadoCobroEnum.ANULADO) {
            throw CobroYaAnuladoException.create();
        }

        // 4. Validar que no sea un cobro automático por cruce de cuentas
        if (cobro.getMetodoPago() == MetodoPagoEnum.CRUCE_DE_CUENTAS) {
            throw CobroAutomaticoNoAnulableException.create();
        }

        // 5. Obtener la cuenta por cobrar asociada
        CuentaCobrarEntity cuentaCobrar = cobro.getCuentaCobrar();
        BigDecimal montoCobro = ObjectHelper.getDefault(cobro.getMonto(), BigDecimal.ZERO);

        // 6. Anular el cobro
        cobro.setEstado(EstadoCobroEnum.ANULADO);
        cobro.setMotivoAnulacion(data.getMotivoAnulacion());
        cobro.setFechaAnulacion(LocalDateTime.now());
        cobroRepository.save(cobro);

        // 7. Restaurar el saldo pendiente en la cuenta por cobrar
        if (!ObjectHelper.isNull(cuentaCobrar)) {
            BigDecimal saldoActual = ObjectHelper.getDefault(cuentaCobrar.getSaldoPendiente(), BigDecimal.ZERO);
            BigDecimal nuevoSaldo = saldoActual.add(montoCobro);
            cuentaCobrar.setSaldoPendiente(nuevoSaldo);

            // Actualizar estado de la cuenta
            BigDecimal montoTotal = ObjectHelper.getDefault(cuentaCobrar.getMontoTotal(), BigDecimal.ZERO);
            if (nuevoSaldo.compareTo(montoTotal) >= 0) {
                cuentaCobrar.setEstado(EstadoCuentaEnum.PENDIENTE);
            } else {
                cuentaCobrar.setEstado(EstadoCuentaEnum.PARCIALMENTE_COBRADA);
            }

            cuentaCobrarRepository.save(cuentaCobrar);

            // 8. Revertir la cartera del cliente
            if (!ObjectHelper.isNull(cuentaCobrar.getCliente())) {
                actualizarCartera.revertirCobro(
                        cuentaCobrar.getCliente().getId(),
                        data.getSuscripcionId(),
                        montoCobro
                );
            }
        }
    }
}

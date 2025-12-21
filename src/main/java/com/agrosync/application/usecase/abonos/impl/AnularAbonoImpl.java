package com.agrosync.application.usecase.abonos.impl;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.usecase.abonos.AnularAbono;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.abonos.exceptions.AbonoYaAnuladoException;
import com.agrosync.domain.abonos.exceptions.IdentificadorAbonoNoExisteException;
import com.agrosync.domain.abonos.exceptions.MotivoAnulacionAbonoRequeridoException;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class AnularAbonoImpl implements AnularAbono {

    private final AbonoRepository abonoRepository;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final ActualizarCartera actualizarCartera;

    public AnularAbonoImpl(AbonoRepository abonoRepository,
                          CuentaPagarRepository cuentaPagarRepository,
                          ActualizarCartera actualizarCartera) {
        this.abonoRepository = abonoRepository;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.actualizarCartera = actualizarCartera;
    }

    @Override
    public void ejecutar(AbonoDomain data) {
        // 1. Validar que se proporcione motivo
        if (TextHelper.isEmpty(data.getMotivoAnulacion())) {
            throw MotivoAnulacionAbonoRequeridoException.create();
        }

        // 2. Buscar el abono
        AbonoEntity abono = abonoRepository
                .findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorAbonoNoExisteException::create);

        // 3. Validar que no esté ya anulado
        if (abono.getEstado() == EstadoAbonoEnum.ANULADO) {
            throw AbonoYaAnuladoException.create();
        }

        // 4. Obtener la cuenta por pagar asociada
        CuentaPagarEntity cuentaPagar = abono.getCuentaPagar();
        BigDecimal montoAbono = ObjectHelper.getDefault(abono.getMonto(), BigDecimal.ZERO);

        // 5. Anular el abono
        abono.setEstado(EstadoAbonoEnum.ANULADO);
        abono.setMotivoAnulacion(data.getMotivoAnulacion());
        abono.setFechaAnulacion(LocalDateTime.now());
        abonoRepository.save(abono);

        // 6. Restaurar el saldo pendiente en la cuenta por pagar
        if (!ObjectHelper.isNull(cuentaPagar)) {
            BigDecimal saldoActual = ObjectHelper.getDefault(cuentaPagar.getSaldoPendiente(), BigDecimal.ZERO);
            BigDecimal nuevoSaldo = saldoActual.add(montoAbono);
            cuentaPagar.setSaldoPendiente(nuevoSaldo);

            // Actualizar estado de la cuenta
            BigDecimal montoTotal = ObjectHelper.getDefault(cuentaPagar.getMontoTotal(), BigDecimal.ZERO);
            if (nuevoSaldo.compareTo(montoTotal) >= 0) {
                cuentaPagar.setEstado(EstadoCuentaEnum.PENDIENTE);
            } else {
                cuentaPagar.setEstado(EstadoCuentaEnum.PARCIALMENTE_PAGADA);
            }

            cuentaPagarRepository.save(cuentaPagar);

            // 7. Revertir la cartera del proveedor
            if (!ObjectHelper.isNull(cuentaPagar.getProveedor())) {
                actualizarCartera.revertirAbono(
                        cuentaPagar.getProveedor().getId(),
                        data.getSuscripcionId(),
                        montoAbono
                );
            }
        }
    }
}


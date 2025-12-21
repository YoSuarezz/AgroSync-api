package com.agrosync.application.usecase.ventas.impl;

import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.ventas.AnularVenta;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.ventas.EstadoVentaEnum;
import com.agrosync.domain.ventas.VentaDomain;
import com.agrosync.domain.ventas.exceptions.IdentificadorVentaNoExisteException;
import com.agrosync.domain.ventas.exceptions.MotivoAnulacionVentaRequeridoException;
import com.agrosync.domain.ventas.exceptions.VentaConCobrosException;
import com.agrosync.domain.ventas.exceptions.VentaYaAnuladaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AnularVentaImpl implements AnularVenta {

    private final VentaRepository ventaRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final AnimalRepository animalRepository;
    private final ActualizarCartera actualizarCartera;

    public AnularVentaImpl(VentaRepository ventaRepository,
                          CuentaCobrarRepository cuentaCobrarRepository,
                          AnimalRepository animalRepository,
                          ActualizarCartera actualizarCartera) {
        this.ventaRepository = ventaRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.animalRepository = animalRepository;
        this.actualizarCartera = actualizarCartera;
    }

    @Override
    public void ejecutar(VentaDomain data) {
        // 1. Validar que se proporcione motivo
        if (TextHelper.isEmpty(data.getMotivoAnulacion())) {
            throw MotivoAnulacionVentaRequeridoException.create();
        }

        // 2. Buscar la venta
        VentaEntity venta = ventaRepository
                .findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorVentaNoExisteException::create);

        // 3. Validar que no esté ya anulada
        if (venta.getEstado() == EstadoVentaEnum.ANULADA) {
            throw VentaYaAnuladaException.create();
        }

        // 4. Validar que no tenga cobros
        CuentaCobrarEntity cuentaCobrar = venta.getCuentaCobrar();
        if (!ObjectHelper.isNull(cuentaCobrar) &&
            !ObjectHelper.isNull(cuentaCobrar.getCobros()) &&
            !cuentaCobrar.getCobros().isEmpty()) {
            throw VentaConCobrosException.create();
        }

        // 5. Obtener saldo pendiente antes de anular (para revertir cartera)
        BigDecimal saldoPendiente = BigDecimal.ZERO;
        if (!ObjectHelper.isNull(cuentaCobrar)) {
            saldoPendiente = ObjectHelper.getDefault(cuentaCobrar.getSaldoPendiente(), BigDecimal.ZERO);
        }

        // 6. Anular la venta
        venta.setEstado(EstadoVentaEnum.ANULADA);
        venta.setMotivoAnulacion(data.getMotivoAnulacion());
        venta.setFechaAnulacion(LocalDateTime.now());

        // 7. Anular la cuenta por cobrar
        if (!ObjectHelper.isNull(cuentaCobrar)) {
            cuentaCobrar.setEstado(EstadoCuentaEnum.ANULADA);
            cuentaCobrar.setSaldoPendiente(BigDecimal.ZERO);
            cuentaCobrarRepository.save(cuentaCobrar);
        }

        // 8. Devolver animales a estado DISPONIBLE
        List<AnimalEntity> animales = venta.getAnimales();
        if (!ObjectHelper.isNull(animales) && !animales.isEmpty()) {
            animales.forEach(animal -> {
                animal.setEstado(EstadoAnimalEnum.DISPONIBLE);
                animal.setVenta(null);
                animal.setPrecioKiloVenta(null);
            });
            animalRepository.saveAll(animales);
        }

        // 9. Guardar venta anulada
        ventaRepository.save(venta);

        // 10. Revertir cartera del cliente (reducir cuentas por cobrar)
        if (saldoPendiente.compareTo(BigDecimal.ZERO) > 0 && !ObjectHelper.isNull(venta.getCliente())) {
            actualizarCartera.reducirCuentasCobrarPorCobro(
                    venta.getCliente().getId(),
                    venta.getSuscripcion().getId(),
                    saldoPendiente
            );
        }
    }
}


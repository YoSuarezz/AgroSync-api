package com.agrosync.application.usecase.compras.impl;

import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.usecase.carteras.ActualizarCartera;
import com.agrosync.application.usecase.compras.AnularCompra;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.compras.exceptions.CompraConAbonosException;
import com.agrosync.domain.compras.exceptions.CompraConAnimalesVendidosException;
import com.agrosync.domain.compras.exceptions.CompraYaAnuladaException;
import com.agrosync.domain.compras.exceptions.IdentificadorCompraNoExisteException;
import com.agrosync.domain.compras.exceptions.MotivoAnulacionRequeridoException;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.compras.EstadoCompraEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AnularCompraImpl implements AnularCompra {

    private final CompraRepository compraRepository;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final AnimalRepository animalRepository;
    private final ActualizarCartera actualizarCartera;

    public AnularCompraImpl(CompraRepository compraRepository,
                            CuentaPagarRepository cuentaPagarRepository,
                            AnimalRepository animalRepository,
                            ActualizarCartera actualizarCartera) {
        this.compraRepository = compraRepository;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.animalRepository = animalRepository;
        this.actualizarCartera = actualizarCartera;
    }

    @Override
    public void ejecutar(CompraDomain data) {
        // 1. Validar que se proporcione motivo
        if (TextHelper.isEmpty(data.getMotivoAnulacion())) {
            throw MotivoAnulacionRequeridoException.create();
        }

        // 2. Buscar la compra
        CompraEntity compra = compraRepository
                .findByIdAndSuscripcion_Id(data.getId(), data.getSuscripcionId())
                .orElseThrow(IdentificadorCompraNoExisteException::create);

        // 3. Validar que no esté ya anulada
        if (compra.getEstado() == EstadoCompraEnum.ANULADA) {
            throw CompraYaAnuladaException.create();
        }

        // 4. Validar que no tenga abonos
        CuentaPagarEntity cuentaPagar = compra.getCuentaPagar();
        if (!ObjectHelper.isNull(cuentaPagar) &&
            !ObjectHelper.isNull(cuentaPagar.getAbonos()) &&
            !cuentaPagar.getAbonos().isEmpty()) {
            throw CompraConAbonosException.create();
        }

        // 5. Validar que ningún animal haya sido vendido
        LoteEntity lote = compra.getLote();
        if (!ObjectHelper.isNull(lote) && !ObjectHelper.isNull(lote.getAnimales())) {
            boolean tieneAnimalesVendidos = lote.getAnimales().stream()
                    .anyMatch(animal -> animal.getEstado() == EstadoAnimalEnum.VENDIDO);
            if (tieneAnimalesVendidos) {
                throw CompraConAnimalesVendidosException.create();
            }
        }

        // 6. Obtener saldo pendiente antes de anular (para revertir cartera)
        BigDecimal saldoPendiente = BigDecimal.ZERO;
        if (!ObjectHelper.isNull(cuentaPagar)) {
            saldoPendiente = ObjectHelper.getDefault(cuentaPagar.getSaldoPendiente(), BigDecimal.ZERO);
        }

        // 7. Anular la compra
        compra.setEstado(EstadoCompraEnum.ANULADA);
        compra.setMotivoAnulacion(data.getMotivoAnulacion());
        compra.setFechaAnulacion(LocalDateTime.now());

        // 8. Anular la cuenta por pagar
        if (!ObjectHelper.isNull(cuentaPagar)) {
            cuentaPagar.setEstado(EstadoCuentaEnum.ANULADA);
            cuentaPagar.setSaldoPendiente(BigDecimal.ZERO);
            cuentaPagarRepository.save(cuentaPagar);
        }

        // 9. Marcar animales como anulados/eliminados (cambiar estado)
        if (!ObjectHelper.isNull(lote) && !ObjectHelper.isNull(lote.getAnimales())) {
            List<AnimalEntity> animales = lote.getAnimales();
            animales.forEach(animal -> animal.setEstado(EstadoAnimalEnum.ANULADO));
            animalRepository.saveAll(animales);
        }

        // 10. Guardar compra anulada
        compraRepository.save(compra);

        // 11. Revertir cartera del proveedor (reducir cuentas por pagar)
        if (saldoPendiente.compareTo(BigDecimal.ZERO) > 0 && !ObjectHelper.isNull(compra.getProveedor())) {
            actualizarCartera.reducirCuentasPagarPorAbono(
                    compra.getProveedor().getId(),
                    compra.getSuscripcion().getId(),
                    saldoPendiente
            );
        }
    }
}


package com.agrosync.application.usecase.compras.impl;

import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.mapper.compras.CompraEntityMapper;
import com.agrosync.application.secondaryports.repository.CarteraRepository;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.usecase.animales.rulesvalidator.RegistrarNuevoAnimalRulesValidator;
import com.agrosync.application.usecase.compras.RegistrarNuevaCompra;
import com.agrosync.application.primaryports.enums.animales.EstadoAnimalEnum;
import com.agrosync.application.primaryports.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.application.usecase.compras.rulesvalidator.RegistrarNuevaCompraRulesValidator;
import com.agrosync.application.usecase.lotes.rulesvalidator.RegistrarNuevoLoteRulesValidator;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.domain.compras.CompraDomain;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RegistrarNuevaCompraImpl implements RegistrarNuevaCompra {

    private final CompraRepository compraRepository;
    private final CarteraRepository carteraRepository;
    private final RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator;
    private final RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator;
    private final RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator;
    private final CompraEntityMapper compraEntityMapper;

    public RegistrarNuevaCompraImpl(CompraRepository compraRepository, CarteraRepository carteraRepository, RegistrarNuevaCompraRulesValidator registrarNuevaCompraRulesValidator, RegistrarNuevoLoteRulesValidator registrarNuevoLoteRulesValidator, RegistrarNuevoAnimalRulesValidator registrarNuevoAnimalRulesValidator, CompraEntityMapper compraEntityMapper) {
        this.compraRepository = compraRepository;
        this.carteraRepository = carteraRepository;
        this.registrarNuevaCompraRulesValidator = registrarNuevaCompraRulesValidator;
        this.registrarNuevoLoteRulesValidator = registrarNuevoLoteRulesValidator;
        this.registrarNuevoAnimalRulesValidator = registrarNuevoAnimalRulesValidator;
        this.compraEntityMapper = compraEntityMapper;
    }

    @Override
    public void ejecutar(CompraDomain data) {
        registrarNuevaCompraRulesValidator.validar(data);
        data.getLote().setFecha(data.getFechaCompra());
        // TODO: Validar animales y lotes correctamente
        //registrarNuevoLoteRulesValidator.validar(data.getLote());
        CompraEntity compra = compraEntityMapper.toEntity(data);

        SuscripcionEntity suscripcion = SuscripcionEntity.create(data.getSuscripcionId());
        compra.setSuscripcion(suscripcion);

        LocalDate fechaCompra = ObjectHelper.getDefault(compra.getFechaCompra(), LocalDate.now());
        compra.setFechaCompra(fechaCompra);

        LoteEntity lote = compra.getLote();
        lote.setCompra(compra);
        lote.setSuscripcion(suscripcion);
        lote.setFecha(fechaCompra);
        if (TextHelper.isEmpty(lote.getNumeroLote())) {
            lote.setNumeroLote(generarNumeroLote(lote.getContramarca(), lote.getFecha()));
        }

        BigDecimal pesoTotal = BigDecimal.ZERO;
        BigDecimal precioTotalCompra = BigDecimal.ZERO;

        if (!ObjectHelper.isNull(lote.getAnimales())) {
            for (AnimalEntity animal : lote.getAnimales()) {
                if (ObjectHelper.isNull(animal)) {
                    continue;
                }

                animal.setLote(lote);
                animal.setSuscripcion(suscripcion);
                animal.setVenta(null);
                animal.setEstado(EstadoAnimalEnum.DISPONIBLE);

                if (TextHelper.isEmpty(animal.getNumeroAnimal())) {
                    animal.setNumeroAnimal(generarNumeroAnimal(lote.getContramarca(), animal.getSlot(), lote.getFecha()));
                }

                BigDecimal peso = ObjectHelper.getDefault(animal.getPeso(), BigDecimal.ZERO);
                BigDecimal precioKiloCompra = ObjectHelper.getDefault(animal.getPrecioKiloCompra(), BigDecimal.ZERO);

                pesoTotal = pesoTotal.add(peso);
                precioTotalCompra = precioTotalCompra.add(peso.multiply(precioKiloCompra));
            }
        }

        lote.setPesoTotal(pesoTotal);
        compra.setPrecioTotalCompra(precioTotalCompra);

        if (TextHelper.isEmpty(compra.getNumeroCompra())) {
            compra.setNumeroCompra(generarNumeroCompra(lote.getContramarca()));
        }

        CuentaPagarEntity cuentaPagar = compra.getCuentaPagar();
        if (ObjectHelper.isNull(cuentaPagar)) {
            cuentaPagar = new CuentaPagarEntity();
            compra.setCuentaPagar(cuentaPagar);
        }

        cuentaPagar.setCompra(compra);
        cuentaPagar.setProveedor(compra.getProveedor());
        cuentaPagar.setSuscripcion(suscripcion);
        cuentaPagar.setMontoTotal(precioTotalCompra);
        cuentaPagar.setSaldoPendiente(precioTotalCompra);
        cuentaPagar.setEstado(EstadoCuentaEnum.PENDIENTE);
        cuentaPagar.setFechaEmision(fechaCompra);
        cuentaPagar.setFechaVencimiento(fechaCompra);

        if (TextHelper.isEmpty(cuentaPagar.getNumeroCuenta())) {
            cuentaPagar.setNumeroCuenta(generarNumeroCuenta(compra.getNumeroCompra()));
        }

        compraRepository.save(compra);

        // Actualizar la cartera del proveedor
        actualizarCarteraProveedor(compra.getProveedor().getId(), suscripcion.getId(), precioTotalCompra);
    }

    private void actualizarCarteraProveedor(UUID proveedorId, UUID suscripcionId, BigDecimal montoCompra) {
        Optional<CarteraEntity> carteraOpt = carteraRepository.findByUsuario_IdAndSuscripcion_Id(proveedorId, suscripcionId);
        if (carteraOpt.isPresent()) {
            CarteraEntity cartera = carteraOpt.get();
            // Aumentar cuentas por cobrar del proveedor (nosotros le debemos a él)
            BigDecimal totalActual = ObjectHelper.getDefault(cartera.getTotalCuentasCobrar(), BigDecimal.ZERO);
            cartera.setTotalCuentasCobrar(totalActual.add(montoCompra));

            // Saldo positivo para el proveedor (le debemos dinero)
            BigDecimal saldoActual = ObjectHelper.getDefault(cartera.getSaldoActual(), BigDecimal.ZERO);
            cartera.setSaldoActual(saldoActual.add(montoCompra));

            carteraRepository.save(cartera);
        }
    }

    private String generarNumeroCompra(String contramarca) {
        return String.format("CO-%s-%s", TextHelper.getDefault(contramarca, TextHelper.EMPTY), generarDigitosAleatorios(4));
    }

    private String generarNumeroCuenta(String numeroCompra) {
        return String.format("CXP-%s", TextHelper.getDefault(numeroCompra, TextHelper.EMPTY));
    }

    private String generarNumeroLote(String contramarca, LocalDate fecha) {
        LocalDate fechaLote = ObjectHelper.getDefault(fecha, LocalDate.now());
        int semana = fechaLote.get(WeekFields.ISO.weekOfWeekBasedYear());
        return String.format("%s-%d-%d", TextHelper.getDefault(contramarca, " "), semana, fechaLote.getYear());
    }

    private String generarNumeroAnimal(String contramarca, String slot, LocalDate fecha) {
        LocalDate fechaAnimal = ObjectHelper.getDefault(fecha, LocalDate.now());
        int semana = fechaAnimal.get(WeekFields.ISO.weekOfWeekBasedYear());
        return String.format("%s-%s-%02d-%d", TextHelper.getDefault(contramarca, TextHelper.EMPTY), TextHelper.getDefault(slot, TextHelper.EMPTY), semana, fechaAnimal.getYear());
    }

    private String generarDigitosAleatorios(int cantidad) {
        int limite = (int) Math.pow(10, cantidad);
        int numero = ThreadLocalRandom.current().nextInt(limite);
        return String.format("%0" + cantidad + "d", numero);
    }
}

package com.agrosync.application.usecase.reportes.impl;

import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.animales.AnimalEntity;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.application.secondaryports.repository.AbonoRepository;
import com.agrosync.application.secondaryports.repository.AnimalRepository;
import com.agrosync.application.secondaryports.repository.CobroRepository;
import com.agrosync.application.secondaryports.repository.CompraRepository;
import com.agrosync.application.secondaryports.repository.CuentaCobrarRepository;
import com.agrosync.application.secondaryports.repository.CuentaPagarRepository;
import com.agrosync.application.secondaryports.repository.LoteRepository;
import com.agrosync.application.secondaryports.repository.VentaRepository;
import com.agrosync.application.usecase.reportes.ObtenerDashboardReport;
import com.agrosync.application.usecase.reportes.rulesvalidator.ReporteDashboardRulesValidator;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.enums.abonos.EstadoAbonoEnum;
import com.agrosync.domain.enums.animales.EstadoAnimalEnum;
import com.agrosync.domain.enums.cobros.EstadoCobroEnum;
import com.agrosync.domain.enums.cuentas.EstadoCuentaEnum;
import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.domain.enums.ventas.EstadoVentaEnum;
import com.agrosync.domain.reportes.DashboardReportDomain;
import com.agrosync.domain.reportes.ReporteDashboardFiltroDomain;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ObtenerDashboardReportImpl implements ObtenerDashboardReport {

    private final VentaRepository ventaRepository;
    private final CompraRepository compraRepository;
    private final CobroRepository cobroRepository;
    private final AbonoRepository abonoRepository;
    private final CuentaCobrarRepository cuentaCobrarRepository;
    private final CuentaPagarRepository cuentaPagarRepository;
    private final AnimalRepository animalRepository;
    private final LoteRepository loteRepository;
    private final ReporteDashboardRulesValidator rulesValidator;

    public ObtenerDashboardReportImpl(VentaRepository ventaRepository,
                                      CompraRepository compraRepository,
                                      CobroRepository cobroRepository,
                                      AbonoRepository abonoRepository,
                                      CuentaCobrarRepository cuentaCobrarRepository,
                                      CuentaPagarRepository cuentaPagarRepository,
                                      AnimalRepository animalRepository,
                                      LoteRepository loteRepository,
                                      ReporteDashboardRulesValidator rulesValidator) {
        this.ventaRepository = ventaRepository;
        this.compraRepository = compraRepository;
        this.cobroRepository = cobroRepository;
        this.abonoRepository = abonoRepository;
        this.cuentaCobrarRepository = cuentaCobrarRepository;
        this.cuentaPagarRepository = cuentaPagarRepository;
        this.animalRepository = animalRepository;
        this.loteRepository = loteRepository;
        this.rulesValidator = rulesValidator;
    }

    @Override
    public DashboardReportDomain ejecutar(ReporteDashboardFiltroDomain filtro) {
        rulesValidator.validar(filtro);

        List<VentaEntity> ventas = ventaRepository.findAll(buildVentaSpecification(filtro));
        List<CompraEntity> compras = compraRepository.findAll(buildCompraSpecification(filtro));
        List<CobroEntity> cobros = cobroRepository.findAll(buildCobroSpecification(filtro));
        List<AbonoEntity> abonos = abonoRepository.findAll(buildAbonoSpecification(filtro));
        List<CuentaCobrarEntity> cuentasCobrar = cuentaCobrarRepository.findAll(buildCuentaCobrarSpecification(filtro));
        List<CuentaPagarEntity> cuentasPagar = cuentaPagarRepository.findAll(buildCuentaPagarSpecification(filtro));
        List<AnimalEntity> animales = animalRepository.findAll(buildAnimalSpecification(filtro));
        List<com.agrosync.application.secondaryports.entity.lotes.LoteEntity> lotes = loteRepository.findAll();

        DashboardReportDomain.CashFlow flujoCaja = construirFlujoCaja(ventas, compras, cobros, abonos, cuentasCobrar, cuentasPagar);
        DashboardReportDomain.Cartera carteraCobrar = construirCarteraCobrar(cuentasCobrar, cobros);
        DashboardReportDomain.Cartera carteraPagar = construirCarteraPagar(cuentasPagar, abonos);
        DashboardReportDomain.Inventario inventario = construirInventario(animales);
        DashboardReportDomain.Ventas ventasReporte = construirVentas(ventas, animales);
        DashboardReportDomain.Compras comprasReporte = construirCompras(compras, animales);
        DashboardReportDomain.Margen margen = construirMargen(flujoCaja, ventas, compras, animales);
        DashboardReportDomain.TopRelaciones topRelaciones = construirTopRelaciones(ventas, compras);
        List<DashboardReportDomain.LoteRentabilidad> rentabilidadLotes = construirRentabilidadLotes(lotes, animales, ventas);

        return DashboardReportDomain.create(flujoCaja, carteraCobrar, carteraPagar, inventario, ventasReporte, comprasReporte, margen, topRelaciones, rentabilidadLotes, filtro.getFechaInicio(), filtro.getFechaFin());
    }

    private Specification<VentaEntity> buildVentaSpecification(ReporteDashboardFiltroDomain filtro) {
        List<Specification<VentaEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaVenta"), filtro.getFechaInicio()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaVenta"), filtro.getFechaFin()));
        }
        if (!UUIDHelper.isDefault(filtro.getClienteId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("cliente").get("id"), filtro.getClienteId()));
        }
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoVentaEnum.ANULADA));

        return Specification.allOf(specs);
    }

    private Specification<CompraEntity> buildCompraSpecification(ReporteDashboardFiltroDomain filtro) {
        List<Specification<CompraEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaCompra"), filtro.getFechaInicio()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaCompra"), filtro.getFechaFin()));
        }
        if (!UUIDHelper.isDefault(filtro.getProveedorId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("proveedor").get("id"), filtro.getProveedorId()));
        }
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), com.agrosync.domain.enums.compras.EstadoCompraEnum.ANULADA));
        return Specification.allOf(specs);
    }

    private Specification<CobroEntity> buildCobroSpecification(ReporteDashboardFiltroDomain filtro) {
        List<Specification<CobroEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaCobro"), filtro.getFechaInicio().atStartOfDay()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaCobro"), filtro.getFechaFin().atTime(23, 59, 59)));
        }
        if (filtro.getMetodoPago() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("metodoPago"), filtro.getMetodoPago()));
        }
        if (!UUIDHelper.isDefault(filtro.getClienteId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("cuentaCobrar").get("cliente").get("id"), filtro.getClienteId()));
        }
        specs.add((root, query, cb) -> cb.equal(root.get("estado"), EstadoCobroEnum.ACTIVO));
        return Specification.allOf(specs);
    }

    private Specification<AbonoEntity> buildAbonoSpecification(ReporteDashboardFiltroDomain filtro) {
        List<Specification<AbonoEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaPago"), filtro.getFechaInicio().atStartOfDay()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaPago"), filtro.getFechaFin().atTime(23, 59, 59)));
        }
        if (filtro.getMetodoPago() != null) {
            specs.add((root, query, cb) -> cb.equal(root.get("metodoPago"), filtro.getMetodoPago()));
        }
        if (!UUIDHelper.isDefault(filtro.getProveedorId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("cuentaPagar").get("proveedor").get("id"), filtro.getProveedorId()));
        }
        specs.add((root, query, cb) -> cb.equal(root.get("estado"), EstadoAbonoEnum.ACTIVO));
        return Specification.allOf(specs);
    }

    private Specification<CuentaCobrarEntity> buildCuentaCobrarSpecification(ReporteDashboardFiltroDomain filtro) {
        List<Specification<CuentaCobrarEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaInicio()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaFin()));
        }
        if (!UUIDHelper.isDefault(filtro.getClienteId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("cliente").get("id"), filtro.getClienteId()));
        }
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoCuentaEnum.ANULADA));
        return Specification.allOf(specs);
    }

    private Specification<CuentaPagarEntity> buildCuentaPagarSpecification(ReporteDashboardFiltroDomain filtro) {
        List<Specification<CuentaPagarEntity>> specs = new ArrayList<>();

        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        if (filtro.getFechaInicio() != null) {
            specs.add((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaInicio()));
        }
        if (filtro.getFechaFin() != null) {
            specs.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaEmision"), filtro.getFechaFin()));
        }
        if (!UUIDHelper.isDefault(filtro.getProveedorId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("proveedor").get("id"), filtro.getProveedorId()));
        }
        specs.add((root, query, cb) -> cb.notEqual(root.get("estado"), EstadoCuentaEnum.ANULADA));
        return Specification.allOf(specs);
    }

    private Specification<AnimalEntity> buildAnimalSpecification(ReporteDashboardFiltroDomain filtro) {
        List<Specification<AnimalEntity>> specs = new ArrayList<>();
        if (!UUIDHelper.isDefault(filtro.getSuscripcionId())) {
            specs.add((root, query, cb) -> cb.equal(root.get("suscripcion").get("id"), filtro.getSuscripcionId()));
        }
        return Specification.allOf(specs);
    }

    private DashboardReportDomain.CashFlow construirFlujoCaja(List<VentaEntity> ventas, List<CompraEntity> compras, List<CobroEntity> cobros, List<AbonoEntity> abonos, List<CuentaCobrarEntity> cuentasCobrar, List<CuentaPagarEntity> cuentasPagar) {
        DashboardReportDomain.CashFlow cashFlow = new DashboardReportDomain.CashFlow();

        BigDecimal totalVentas = sumarBigDecimal(ventas.stream()
                .filter(venta -> venta.getEstado() != EstadoVentaEnum.ANULADA)
                .map(VentaEntity::getPrecioTotalVenta)
                .toList());

        BigDecimal totalCompras = sumarBigDecimal(compras.stream()
                .filter(compra -> compra.getEstado() != com.agrosync.domain.enums.compras.EstadoCompraEnum.ANULADA)
                .map(CompraEntity::getPrecioTotalCompra)
                .toList());

        BigDecimal totalCobros = sumarBigDecimal(cobros.stream()
                .filter(cobro -> cobro.getEstado() == EstadoCobroEnum.ACTIVO)
                .map(CobroEntity::getMonto)
                .toList());

        BigDecimal totalAbonos = sumarBigDecimal(abonos.stream()
                .filter(abono -> abono.getEstado() == EstadoAbonoEnum.ACTIVO)
                .map(AbonoEntity::getMonto)
                .toList());

        BigDecimal saldoCartera = sumarBigDecimal(cuentasCobrar.stream()
                .filter(this::cuentaPendiente)
                .map(CuentaCobrarEntity::getSaldoPendiente)
                .toList());

        BigDecimal saldoCuentasPagar = sumarBigDecimal(cuentasPagar.stream()
                .filter(this::cuentaPendiente)
                .map(CuentaPagarEntity::getSaldoPendiente)
                .toList());

        BigDecimal ingresos = totalCobros;
        BigDecimal egresos = totalAbonos;

        cashFlow.setTotalVentas(totalVentas);
        cashFlow.setTotalCompras(totalCompras);
        cashFlow.setTotalCobros(totalCobros);
        cashFlow.setTotalAbonos(totalAbonos);
        cashFlow.setIngresosTotales(ingresos);
        cashFlow.setEgresosTotales(egresos);
        cashFlow.setNeto(ingresos.subtract(egresos));
        cashFlow.setSaldoCartera(saldoCartera);
        cashFlow.setSaldoCuentasPorPagar(saldoCuentasPagar);
        cashFlow.setProyeccionCaja(cashFlow.getNeto().add(saldoCartera).subtract(saldoCuentasPagar));
        cashFlow.setMetodosPago(construirDistribucionMetodos(cobros, abonos));
        cashFlow.setTendenciaMensual(construirTendenciaMensual(cobros, abonos));

        return cashFlow;
    }

    private List<DashboardReportDomain.MetodoPagoDetalle> construirDistribucionMetodos(List<CobroEntity> cobros, List<AbonoEntity> abonos) {
        Map<MetodoPagoEnum, BigDecimal> ingresosPorMetodo = cobros.stream()
                .filter(cobro -> cobro.getEstado() == EstadoCobroEnum.ACTIVO)
                .collect(Collectors.groupingBy(CobroEntity::getMetodoPago,
                        Collectors.mapping(CobroEntity::getMonto, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        Map<MetodoPagoEnum, BigDecimal> egresosPorMetodo = abonos.stream()
                .filter(abono -> abono.getEstado() == EstadoAbonoEnum.ACTIVO)
                .collect(Collectors.groupingBy(AbonoEntity::getMetodoPago,
                        Collectors.mapping(AbonoEntity::getMonto, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));

        Map<MetodoPagoEnum, DashboardReportDomain.MetodoPagoDetalle> resultado = new HashMap<>();

        ingresosPorMetodo.forEach((metodo, valor) -> resultado.put(metodo, DashboardReportDomain.MetodoPagoDetalle.create(metodo.name(), valor, BigDecimal.ZERO)));
        egresosPorMetodo.forEach((metodo, valor) -> {
            DashboardReportDomain.MetodoPagoDetalle detalle = resultado.getOrDefault(metodo, DashboardReportDomain.MetodoPagoDetalle.create(metodo.name(), BigDecimal.ZERO, BigDecimal.ZERO));
            detalle.setEgresos(detalle.getEgresos().add(valor));
            resultado.put(metodo, detalle);
        });

        return new ArrayList<>(resultado.values());
    }

    private List<DashboardReportDomain.Tendencia> construirTendenciaMensual(List<CobroEntity> cobros, List<AbonoEntity> abonos) {
        List<DashboardReportDomain.Tendencia> tendencia = new ArrayList<>();
        YearMonth current = YearMonth.now();

        for (int i = 5; i >= 0; i--) {
            YearMonth month = current.minusMonths(i);
            BigDecimal ingresos = sumarBigDecimal(cobros.stream()
                    .filter(cobro -> cobro.getFechaCobro() != null && YearMonth.from(cobro.getFechaCobro()).equals(month))
                    .map(CobroEntity::getMonto)
                    .toList());

            BigDecimal egresos = sumarBigDecimal(abonos.stream()
                    .filter(abono -> abono.getFechaPago() != null && YearMonth.from(abono.getFechaPago()).equals(month))
                    .map(AbonoEntity::getMonto)
                    .toList());

            tendencia.add(DashboardReportDomain.Tendencia.create(month.toString(), ingresos, egresos, ingresos.subtract(egresos)));
        }
        return tendencia;
    }

    private List<DashboardReportDomain.Tendencia> construirTendenciaVentas(List<VentaEntity> ventas) {
        Map<YearMonth, DashboardReportDomain.Tendencia> mapa = new TreeMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 5; i >= 0; i--) {
            YearMonth m = current.minusMonths(i);
            mapa.put(m, DashboardReportDomain.Tendencia.create(m.toString(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        }
        ventas.forEach(v -> {
            YearMonth mes = YearMonth.from(v.getFechaVenta());
            if (mapa.containsKey(mes)) {
                DashboardReportDomain.Tendencia t = mapa.get(mes);
                t.setIngresos(t.getIngresos().add(defaultBigDecimal(v.getPrecioTotalVenta())));
                t.setNeto(t.getIngresos().subtract(t.getEgresos()));
            }
        });
        return new ArrayList<>(mapa.values());
    }

    private List<DashboardReportDomain.Tendencia> construirTendenciaCompras(List<CompraEntity> compras) {
        Map<YearMonth, DashboardReportDomain.Tendencia> mapa = new TreeMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 5; i >= 0; i--) {
            YearMonth m = current.minusMonths(i);
            mapa.put(m, DashboardReportDomain.Tendencia.create(m.toString(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        }
        compras.forEach(c -> {
            YearMonth mes = YearMonth.from(c.getFechaCompra());
            if (mapa.containsKey(mes)) {
                DashboardReportDomain.Tendencia t = mapa.get(mes);
                t.setEgresos(t.getEgresos().add(defaultBigDecimal(c.getPrecioTotalCompra())));
                t.setNeto(t.getIngresos().subtract(t.getEgresos()));
            }
        });
        return new ArrayList<>(mapa.values());
    }

    private List<DashboardReportDomain.Tendencia> construirTendenciaMargen(List<VentaEntity> ventas, List<CompraEntity> compras) {
        Map<YearMonth, DashboardReportDomain.Tendencia> mapa = new TreeMap<>();
        YearMonth current = YearMonth.now();
        for (int i = 5; i >= 0; i--) {
            YearMonth m = current.minusMonths(i);
            mapa.put(m, DashboardReportDomain.Tendencia.create(m.toString(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));
        }
        ventas.forEach(v -> {
            YearMonth mes = YearMonth.from(v.getFechaVenta());
            if (mapa.containsKey(mes)) {
                DashboardReportDomain.Tendencia t = mapa.get(mes);
                t.setIngresos(t.getIngresos().add(defaultBigDecimal(v.getPrecioTotalVenta())));
            }
        });
        compras.forEach(c -> {
            YearMonth mes = YearMonth.from(c.getFechaCompra());
            if (mapa.containsKey(mes)) {
                DashboardReportDomain.Tendencia t = mapa.get(mes);
                t.setEgresos(t.getEgresos().add(defaultBigDecimal(c.getPrecioTotalCompra())));
            }
        });
        mapa.values().forEach(t -> t.setNeto(t.getIngresos().subtract(t.getEgresos())));
        return new ArrayList<>(mapa.values());
    }

    private DashboardReportDomain.Cartera construirCarteraCobrar(List<CuentaCobrarEntity> cuentas, List<CobroEntity> cobros) {
        DashboardReportDomain.Cartera cartera = new DashboardReportDomain.Cartera();
        List<CuentaCobrarEntity> activas = cuentas.stream().filter(this::cuentaPendiente).toList();
        LocalDate hoy = LocalDate.now();

        BigDecimal totalPendiente = sumarBigDecimal(activas.stream().map(CuentaCobrarEntity::getSaldoPendiente).toList());
        BigDecimal totalVencido = sumarBigDecimal(activas.stream()
                .filter(cuenta -> cuenta.getFechaVencimiento() != null && cuenta.getFechaVencimiento().isBefore(hoy))
                .map(CuentaCobrarEntity::getSaldoPendiente)
                .toList());

        cartera.setTotalPendiente(totalPendiente);
        cartera.setTotalVencido(totalVencido);
        cartera.setAntiguedad(construirBuckets(activas));
        cartera.setPrincipalesEntidades(construirEntidadesSaldoCobrar(activas));
        cartera.setCuentas(construirCuentasItemsCobrar(activas, hoy));
        cartera.setCuentasVencidas((int) activas.stream()
                .filter(cuenta -> cuenta.getFechaVencimiento() != null && cuenta.getFechaVencimiento().isBefore(hoy))
                .count());

        BigDecimal totalFacturado = sumarBigDecimal(activas.stream().map(CuentaCobrarEntity::getMontoTotal).toList());
        BigDecimal totalCobrado = sumarBigDecimal(cobros.stream().map(CobroEntity::getMonto).toList());
        cartera.setTasaRecuperacion(totalFacturado.compareTo(BigDecimal.ZERO) > 0
                ? totalCobrado.divide(totalFacturado, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0.0);

        return cartera;
    }

    private DashboardReportDomain.Cartera construirCarteraPagar(List<CuentaPagarEntity> cuentas, List<AbonoEntity> abonos) {
        DashboardReportDomain.Cartera cartera = new DashboardReportDomain.Cartera();
        List<CuentaPagarEntity> activas = cuentas.stream().filter(this::cuentaPendiente).toList();
        LocalDate hoy = LocalDate.now();

        BigDecimal totalPendiente = sumarBigDecimal(activas.stream().map(CuentaPagarEntity::getSaldoPendiente).toList());
        BigDecimal totalVencido = sumarBigDecimal(activas.stream()
                .filter(cuenta -> cuenta.getFechaVencimiento() != null && cuenta.getFechaVencimiento().isBefore(hoy))
                .map(CuentaPagarEntity::getSaldoPendiente)
                .toList());

        cartera.setTotalPendiente(totalPendiente);
        cartera.setTotalVencido(totalVencido);
        cartera.setAntiguedad(construirBuckets(activas));
        cartera.setPrincipalesEntidades(construirEntidadesSaldoPagar(activas));
        cartera.setCuentas(construirCuentasItemsPagar(activas, hoy));
        cartera.setCuentasVencidas((int) activas.stream()
                .filter(cuenta -> cuenta.getFechaVencimiento() != null && cuenta.getFechaVencimiento().isBefore(hoy))
                .count());

        BigDecimal totalObligaciones = sumarBigDecimal(activas.stream().map(CuentaPagarEntity::getMontoTotal).toList());
        BigDecimal totalPagado = sumarBigDecimal(abonos.stream().map(AbonoEntity::getMonto).toList());
        cartera.setTasaRecuperacion(totalObligaciones.compareTo(BigDecimal.ZERO) > 0
                ? totalPagado.divide(totalObligaciones, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0.0);

        return cartera;
    }

    private List<DashboardReportDomain.AgingBucket> construirBuckets(List<? extends Object> cuentas) {
        List<DashboardReportDomain.AgingBucket> buckets = new ArrayList<>();
        buckets.add(DashboardReportDomain.AgingBucket.create("0-30 días", BigDecimal.ZERO, 0));
        buckets.add(DashboardReportDomain.AgingBucket.create("31-60 días", BigDecimal.ZERO, 0));
        buckets.add(DashboardReportDomain.AgingBucket.create("61-90 días", BigDecimal.ZERO, 0));
        buckets.add(DashboardReportDomain.AgingBucket.create("+90 días", BigDecimal.ZERO, 0));

        LocalDate hoy = LocalDate.now();

        cuentas.forEach(cuenta -> {
            LocalDate fechaVencimiento;
            BigDecimal saldo;

            if (cuenta instanceof CuentaCobrarEntity cobrar) {
                fechaVencimiento = cobrar.getFechaVencimiento();
                saldo = cobrar.getSaldoPendiente();
            } else if (cuenta instanceof CuentaPagarEntity pagar) {
                fechaVencimiento = pagar.getFechaVencimiento();
                saldo = pagar.getSaldoPendiente();
            } else {
                return;
            }

            if (fechaVencimiento == null || saldo == null) {
                return;
            }

            long dias = ChronoUnit.DAYS.between(fechaVencimiento, hoy);
            if (dias < 0) {
                dias = 0;
            }

            DashboardReportDomain.AgingBucket bucket;
            if (dias <= 30) {
                bucket = buckets.get(0);
            } else if (dias <= 60) {
                bucket = buckets.get(1);
            } else if (dias <= 90) {
                bucket = buckets.get(2);
            } else {
                bucket = buckets.get(3);
            }

            bucket.setMonto(bucket.getMonto().add(defaultBigDecimal(saldo)));
            bucket.setCuentas(bucket.getCuentas() + 1);
        });

        return buckets;
    }

    private List<DashboardReportDomain.EntidadSaldo> construirEntidadesSaldoCobrar(List<CuentaCobrarEntity> cuentas) {
        Map<UUID, DashboardReportDomain.EntidadSaldo> mapa = new HashMap<>();
        cuentas.forEach(cuenta -> {
            UUID id = cuenta.getCliente() != null ? cuenta.getCliente().getId() : null;
            String nombre = cuenta.getCliente() != null ? cuenta.getCliente().getNombre() : "Cliente";
            DashboardReportDomain.EntidadSaldo entidad = mapa.getOrDefault(id, DashboardReportDomain.EntidadSaldo.create(id, nombre, BigDecimal.ZERO));
            entidad.setSaldoPendiente(entidad.getSaldoPendiente().add(defaultBigDecimal(cuenta.getSaldoPendiente())));
            mapa.put(id, entidad);
        });
        return mapa.values().stream()
                .sorted(Comparator.comparing(DashboardReportDomain.EntidadSaldo::getSaldoPendiente).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<DashboardReportDomain.EntidadSaldo> construirEntidadesSaldoPagar(List<CuentaPagarEntity> cuentas) {
        Map<UUID, DashboardReportDomain.EntidadSaldo> mapa = new HashMap<>();
        cuentas.forEach(cuenta -> {
            UUID id = cuenta.getProveedor() != null ? cuenta.getProveedor().getId() : null;
            String nombre = cuenta.getProveedor() != null ? cuenta.getProveedor().getNombre() : "Proveedor";
            DashboardReportDomain.EntidadSaldo entidad = mapa.getOrDefault(id, DashboardReportDomain.EntidadSaldo.create(id, nombre, BigDecimal.ZERO));
            entidad.setSaldoPendiente(entidad.getSaldoPendiente().add(defaultBigDecimal(cuenta.getSaldoPendiente())));
            mapa.put(id, entidad);
        });
        return mapa.values().stream()
                .sorted(Comparator.comparing(DashboardReportDomain.EntidadSaldo::getSaldoPendiente).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<DashboardReportDomain.CuentaItem> construirCuentasItemsCobrar(List<CuentaCobrarEntity> cuentas, LocalDate hoy) {
        return cuentas.stream()
                .sorted(Comparator.comparing(CuentaCobrarEntity::getFechaVencimiento, Comparator.nullsLast(Comparator.naturalOrder())))
                .limit(20)
                .map(cuenta -> DashboardReportDomain.CuentaItem.create(
                        cuenta.getId(),
                        cuenta.getNumeroCuenta(),
                        cuenta.getCliente() != null ? cuenta.getCliente().getNombre() : TextHelper.EMPTY,
                        cuenta.getMontoTotal(),
                        cuenta.getSaldoPendiente(),
                        cuenta.getFechaVencimiento(),
                        cuenta.getFechaEmision(),
                        cuenta.getEstado() != null ? cuenta.getEstado().name() : TextHelper.EMPTY,
                        diasAtraso(cuenta.getFechaVencimiento(), hoy)
                ))
                .collect(Collectors.toList());
    }

    private List<DashboardReportDomain.CuentaItem> construirCuentasItemsPagar(List<CuentaPagarEntity> cuentas, LocalDate hoy) {
        return cuentas.stream()
                .sorted(Comparator.comparing(CuentaPagarEntity::getFechaVencimiento, Comparator.nullsLast(Comparator.naturalOrder())))
                .limit(20)
                .map(cuenta -> DashboardReportDomain.CuentaItem.create(
                        cuenta.getId(),
                        cuenta.getNumeroCuenta(),
                        cuenta.getProveedor() != null ? cuenta.getProveedor().getNombre() : TextHelper.EMPTY,
                        cuenta.getMontoTotal(),
                        cuenta.getSaldoPendiente(),
                        cuenta.getFechaVencimiento(),
                        cuenta.getFechaEmision(),
                        cuenta.getEstado() != null ? cuenta.getEstado().name() : TextHelper.EMPTY,
                        diasAtraso(cuenta.getFechaVencimiento(), hoy)
                ))
                .collect(Collectors.toList());
    }

    private DashboardReportDomain.Inventario construirInventario(List<AnimalEntity> animales) {
        DashboardReportDomain.Inventario inventario = new DashboardReportDomain.Inventario();

        List<AnimalEntity> disponibles = animales.stream()
                .filter(animal -> animal.getEstado() == EstadoAnimalEnum.DISPONIBLE)
                .toList();

        inventario.setTotalDisponibles(disponibles.size());
        inventario.setTotalVendidos((int) animales.stream().filter(animal -> animal.getEstado() == EstadoAnimalEnum.VENDIDO).count());

        BigDecimal pesoTotal = sumarBigDecimal(disponibles.stream().map(AnimalEntity::getPeso).toList());
        BigDecimal valorInventario = sumarBigDecimal(disponibles.stream()
                .map(animal -> defaultBigDecimal(animal.getPeso()).multiply(defaultBigDecimal(animal.getPrecioKiloCompra())))
                .toList());
        BigDecimal valorPotencial = sumarBigDecimal(disponibles.stream()
                .map(animal -> defaultBigDecimal(animal.getPeso()).multiply(defaultBigDecimal(animal.getPrecioKiloVenta())))
                .toList());

        inventario.setPesoTotal(pesoTotal);
        inventario.setValorInventario(valorInventario);
        inventario.setValorPotencialVenta(valorPotencial);
        inventario.setMargenPotencial(valorPotencial.subtract(valorInventario));
        inventario.setPorSexo(construirDistribucionSexo(disponibles));
        inventario.setPorLote(construirDistribucionLote(disponibles));
        inventario.setAnimalesAntiguos(construirAnimalesAntiguos(disponibles));

        return inventario;
    }

    private DashboardReportDomain.Ventas construirVentas(List<VentaEntity> ventas, List<AnimalEntity> animales) {
        DashboardReportDomain.Ventas reporte = new DashboardReportDomain.Ventas();
        List<VentaEntity> activas = ventas.stream().filter(v -> v.getEstado() != EstadoVentaEnum.ANULADA).toList();

        BigDecimal total = sumarBigDecimal(activas.stream().map(VentaEntity::getPrecioTotalVenta).toList());
        reporte.setTotalVentas(total);

        reporte.setTicketPromedio(activas.isEmpty() ? BigDecimal.ZERO :
                total.divide(BigDecimal.valueOf(activas.size()), 2, RoundingMode.HALF_UP));

        List<AnimalEntity> vendidos = animales.stream().filter(a -> a.getEstado() == EstadoAnimalEnum.VENDIDO).toList();
        reporte.setAnimalesVendidos((long) vendidos.size());
        BigDecimal totalPeso = sumarBigDecimal(vendidos.stream().map(AnimalEntity::getPeso).toList());
        BigDecimal totalVentaKilo = sumarBigDecimal(vendidos.stream()
                .map(a -> defaultBigDecimal(a.getPeso()).multiply(defaultBigDecimal(a.getPrecioKiloVenta()))).toList());
        reporte.setPrecioPromedioKilo(totalPeso.compareTo(BigDecimal.ZERO) > 0
                ? totalVentaKilo.divide(totalPeso, 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO);

        Map<UUID, DashboardReportDomain.EntidadSaldo> porCliente = new HashMap<>();
        activas.forEach(v -> {
            UUID id = v.getCliente() != null ? v.getCliente().getId() : null;
            String nombre = v.getCliente() != null ? v.getCliente().getNombre() : "Cliente";
            DashboardReportDomain.EntidadSaldo entidad = porCliente.getOrDefault(id, DashboardReportDomain.EntidadSaldo.create(id, nombre, BigDecimal.ZERO));
            entidad.setSaldoPendiente(entidad.getSaldoPendiente().add(defaultBigDecimal(v.getPrecioTotalVenta())));
            porCliente.put(id, entidad);
        });
        reporte.setTopClientes(porCliente.values().stream()
                .sorted(Comparator.comparing(DashboardReportDomain.EntidadSaldo::getSaldoPendiente).reversed())
                .limit(10)
                .collect(Collectors.toList()));

        Map<UUID, Long> frecuenciaClientes = activas.stream()
                .collect(Collectors.groupingBy(v -> v.getCliente() != null ? v.getCliente().getId() : UUIDHelper.getDefault(), Collectors.counting()));
        long recurrentes = frecuenciaClientes.values().stream().filter(c -> c != null && c > 1).count();
        long nuevos = frecuenciaClientes.values().stream().filter(c -> c != null && c == 1).count();
        reporte.setClientesRecurrentes(recurrentes);
        reporte.setClientesNuevos(nuevos);

        reporte.setTendencia(construirTendenciaVentas(activas));
        return reporte;
    }

    private DashboardReportDomain.Compras construirCompras(List<CompraEntity> compras, List<AnimalEntity> animales) {
        DashboardReportDomain.Compras reporte = new DashboardReportDomain.Compras();
        List<CompraEntity> activas = compras.stream().filter(c -> c.getEstado() != com.agrosync.domain.enums.compras.EstadoCompraEnum.ANULADA).toList();

        BigDecimal total = sumarBigDecimal(activas.stream().map(CompraEntity::getPrecioTotalCompra).toList());
        reporte.setTotalCompras(total);
        reporte.setCompraPromedio(activas.isEmpty() ? BigDecimal.ZERO :
                total.divide(BigDecimal.valueOf(activas.size()), 2, RoundingMode.HALF_UP));

        List<AnimalEntity> comprados = animales.stream().filter(a -> a.getEstado() != null).toList();
        reporte.setAnimalesComprados((long) comprados.size());

        BigDecimal totalPeso = sumarBigDecimal(comprados.stream().map(AnimalEntity::getPeso).toList());
        BigDecimal totalCompraKilo = sumarBigDecimal(comprados.stream()
                .map(a -> defaultBigDecimal(a.getPeso()).multiply(defaultBigDecimal(a.getPrecioKiloCompra()))).toList());
        reporte.setPrecioPromedioKiloCompra(totalPeso.compareTo(BigDecimal.ZERO) > 0
                ? totalCompraKilo.divide(totalPeso, 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO);

        Map<UUID, DashboardReportDomain.EntidadSaldo> porProveedor = new HashMap<>();
        activas.forEach(c -> {
            UUID id = c.getProveedor() != null ? c.getProveedor().getId() : null;
            String nombre = c.getProveedor() != null ? c.getProveedor().getNombre() : "Proveedor";
            DashboardReportDomain.EntidadSaldo entidad = porProveedor.getOrDefault(id, DashboardReportDomain.EntidadSaldo.create(id, nombre, BigDecimal.ZERO));
            entidad.setSaldoPendiente(entidad.getSaldoPendiente().add(defaultBigDecimal(c.getPrecioTotalCompra())));
            porProveedor.put(id, entidad);
        });
        reporte.setTopProveedores(porProveedor.values().stream()
                .sorted(Comparator.comparing(DashboardReportDomain.EntidadSaldo::getSaldoPendiente).reversed())
                .limit(10)
                .collect(Collectors.toList()));

        // frecuencia mensual simple
        BigDecimal meses = BigDecimal.valueOf(Math.max(1, activas.stream()
                .map(c -> YearMonth.from(c.getFechaCompra()))
                .distinct()
                .count()));
        reporte.setFrecuenciaMensual(BigDecimal.valueOf(activas.size()).divide(meses, 2, RoundingMode.HALF_UP));
        reporte.setTendencia(construirTendenciaCompras(activas));
        return reporte;
    }

    private DashboardReportDomain.Margen construirMargen(DashboardReportDomain.CashFlow cashFlow, List<VentaEntity> ventas, List<CompraEntity> compras, List<AnimalEntity> animales) {
        DashboardReportDomain.Margen margen = new DashboardReportDomain.Margen();
        BigDecimal totalVentas = sumarBigDecimal(ventas.stream().map(VentaEntity::getPrecioTotalVenta).toList());
        BigDecimal totalCompras = sumarBigDecimal(compras.stream().map(CompraEntity::getPrecioTotalCompra).toList());

        margen.setMargenBruto(totalVentas.subtract(totalCompras));
        // margen neto aproximado usando flujo neto (cobros - abonos)
        margen.setMargenNeto(cashFlow.getNeto());

        BigDecimal pesoVendidos = sumarBigDecimal(animales.stream()
                .filter(a -> a.getEstado() == EstadoAnimalEnum.VENDIDO)
                .map(AnimalEntity::getPeso).toList());
        margen.setMargenPorKilo(pesoVendidos.compareTo(BigDecimal.ZERO) > 0
                ? margen.getMargenBruto().divide(pesoVendidos, 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO);

        margen.setPuntoEquilibrio(totalVentas.compareTo(BigDecimal.ZERO) > 0 ? totalCompras : BigDecimal.ZERO);
        margen.setTendencia(construirTendenciaMargen(ventas, compras));
        return margen;
    }

    private DashboardReportDomain.TopRelaciones construirTopRelaciones(List<VentaEntity> ventas, List<CompraEntity> compras) {
        DashboardReportDomain.TopRelaciones top = new DashboardReportDomain.TopRelaciones();

        Map<UUID, DashboardReportDomain.EntidadSaldo> clientesVolumen = new HashMap<>();
        Map<UUID, Long> clientesFrecuencia = new HashMap<>();
        ventas.forEach(v -> {
            UUID id = v.getCliente() != null ? v.getCliente().getId() : null;
            String nombre = v.getCliente() != null ? v.getCliente().getNombre() : "Cliente";
            DashboardReportDomain.EntidadSaldo entidad = clientesVolumen.getOrDefault(id, DashboardReportDomain.EntidadSaldo.create(id, nombre, BigDecimal.ZERO));
            entidad.setSaldoPendiente(entidad.getSaldoPendiente().add(defaultBigDecimal(v.getPrecioTotalVenta())));
            clientesVolumen.put(id, entidad);
            clientesFrecuencia.put(id, clientesFrecuencia.getOrDefault(id, 0L) + 1);
        });

        top.setTopClientesVolumen(clientesVolumen.values().stream()
                .sorted(Comparator.comparing(DashboardReportDomain.EntidadSaldo::getSaldoPendiente).reversed())
                .limit(10)
                .collect(Collectors.toList()));
        top.setTopClientesFrecuencia(clientesFrecuencia.entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> DashboardReportDomain.EntidadSaldo.create(entry.getKey(), clientesVolumen.get(entry.getKey()) != null ? clientesVolumen.get(entry.getKey()).getNombre() : "Cliente", BigDecimal.valueOf(entry.getValue())))
                .collect(Collectors.toList()));

        Map<UUID, DashboardReportDomain.EntidadSaldo> proveedoresVolumen = new HashMap<>();
        Map<UUID, Long> proveedoresFrecuencia = new HashMap<>();
        compras.forEach(c -> {
            UUID id = c.getProveedor() != null ? c.getProveedor().getId() : null;
            String nombre = c.getProveedor() != null ? c.getProveedor().getNombre() : "Proveedor";
            DashboardReportDomain.EntidadSaldo entidad = proveedoresVolumen.getOrDefault(id, DashboardReportDomain.EntidadSaldo.create(id, nombre, BigDecimal.ZERO));
            entidad.setSaldoPendiente(entidad.getSaldoPendiente().add(defaultBigDecimal(c.getPrecioTotalCompra())));
            proveedoresVolumen.put(id, entidad);
            proveedoresFrecuencia.put(id, proveedoresFrecuencia.getOrDefault(id, 0L) + 1);
        });

        top.setTopProveedoresVolumen(proveedoresVolumen.values().stream()
                .sorted(Comparator.comparing(DashboardReportDomain.EntidadSaldo::getSaldoPendiente).reversed())
                .limit(10)
                .collect(Collectors.toList()));
        top.setTopProveedoresFrecuencia(proveedoresFrecuencia.entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> DashboardReportDomain.EntidadSaldo.create(entry.getKey(), proveedoresVolumen.get(entry.getKey()) != null ? proveedoresVolumen.get(entry.getKey()).getNombre() : "Proveedor", BigDecimal.valueOf(entry.getValue())))
                .collect(Collectors.toList()));

        return top;
    }

    private List<DashboardReportDomain.LoteRentabilidad> construirRentabilidadLotes(List<com.agrosync.application.secondaryports.entity.lotes.LoteEntity> lotes, List<AnimalEntity> animales, List<VentaEntity> ventas) {
        if (lotes == null) {
            return new ArrayList<>();
        }
        Map<UUID, VentaEntity> ventasPorId = ventas.stream().collect(Collectors.toMap(VentaEntity::getId, v -> v, (a, b) -> a));
        Map<UUID, List<AnimalEntity>> animalesPorLote = animales.stream()
                .filter(a -> a.getLote() != null && a.getLote().getId() != null)
                .collect(Collectors.groupingBy(a -> a.getLote().getId()));

        return lotes.stream().map(lote -> {
            List<AnimalEntity> animalesLote = animalesPorLote.getOrDefault(lote.getId(), new ArrayList<>());
            BigDecimal costoTotal = lote.getCompra() != null ? defaultBigDecimal(lote.getCompra().getPrecioTotalCompra()) : BigDecimal.ZERO;
            BigDecimal ingresosTotales = animalesLote.stream()
                    .filter(a -> a.getVenta() != null && a.getVenta().getId() != null)
                    .map(a -> {
                        VentaEntity venta = ventasPorId.get(a.getVenta().getId());
                        return venta != null ? defaultBigDecimal(a.getPeso()).multiply(defaultBigDecimal(a.getPrecioKiloVenta())) : BigDecimal.ZERO;
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal ganancia = ingresosTotales.subtract(costoTotal);
            BigDecimal margen = costoTotal.compareTo(BigDecimal.ZERO) > 0
                    ? ganancia.divide(costoTotal, 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;
            BigDecimal roi = costoTotal.compareTo(BigDecimal.ZERO) > 0
                    ? ingresosTotales.divide(costoTotal, 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            BigDecimal pesoTotal = sumarBigDecimal(animalesLote.stream().map(AnimalEntity::getPeso).toList());
            BigDecimal compraPromKg = pesoTotal.compareTo(BigDecimal.ZERO) > 0
                    ? defaultBigDecimal(costoTotal).divide(pesoTotal, 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;
            BigDecimal ventaPromKg = pesoTotal.compareTo(BigDecimal.ZERO) > 0
                    ? ingresosTotales.divide(pesoTotal, 2, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            Long rotacion = 0L;
            if (lote.getFecha() != null) {
                LocalDate fechaVenta = animalesLote.stream()
                        .map(AnimalEntity::getVenta)
                        .filter(Objects::nonNull)
                        .map(VentaEntity::getFechaVenta)
                        .filter(Objects::nonNull)
                        .max(Comparator.naturalOrder())
                        .orElse(lote.getFecha());
                rotacion = ChronoUnit.DAYS.between(lote.getFecha(), fechaVenta);
            }

            DashboardReportDomain.LoteRentabilidad dto = new DashboardReportDomain.LoteRentabilidad();
            dto.setContramarca(lote.getContramarca());
            dto.setNumeroLote(lote.getNumeroLote());
            dto.setFecha(lote.getFecha());
            dto.setCostoTotal(costoTotal);
            dto.setIngresosTotales(ingresosTotales);
            dto.setGananciaNeta(ganancia);
            dto.setMargen(margen);
            dto.setRoi(roi);
            dto.setPrecioPromedioCompraKg(compraPromKg);
            dto.setPrecioPromedioVentaKg(ventaPromKg);
            dto.setTiempoRotacionDias(rotacion);
            return dto;
        }).sorted(Comparator.comparing(DashboardReportDomain.LoteRentabilidad::getGananciaNeta).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<DashboardReportDomain.Distribucion> construirDistribucionSexo(List<AnimalEntity> animales) {
        Map<String, List<AnimalEntity>> agrupados = animales.stream()
                .collect(Collectors.groupingBy(animal -> animal.getSexo() != null ? animal.getSexo().name() : "SIN_SEXO"));

        return agrupados.entrySet().stream()
                .map(entry -> DashboardReportDomain.Distribucion.create(
                        entry.getKey(),
                        (long) entry.getValue().size(),
                        sumarBigDecimal(entry.getValue().stream().map(AnimalEntity::getPeso).toList())
                ))
                .collect(Collectors.toList());
    }

    private List<DashboardReportDomain.Distribucion> construirDistribucionLote(List<AnimalEntity> animales) {
        Map<String, List<AnimalEntity>> agrupados = animales.stream()
                .collect(Collectors.groupingBy(animal -> {
                    if (animal.getLote() != null) {
                        if (ObjectHelper.isNull(animal.getLote().getContramarca())) {
                            return TextHelper.EMPTY;
                        }
                        return animal.getLote().getContramarca();
                    }
                    return "Sin lote";
                }));

        return agrupados.entrySet().stream()
                .map(entry -> DashboardReportDomain.Distribucion.create(
                        entry.getKey(),
                        (long) entry.getValue().size(),
                        sumarBigDecimal(entry.getValue().stream().map(AnimalEntity::getPeso).toList())
                ))
                .sorted(Comparator.comparing(DashboardReportDomain.Distribucion::getCantidad).reversed())
                .collect(Collectors.toList());
    }

    private List<DashboardReportDomain.AnimalAntiguo> construirAnimalesAntiguos(List<AnimalEntity> animales) {
        return animales.stream()
                .filter(animal -> animal.getCreatedDate() != null)
                .sorted(Comparator.comparing(AnimalEntity::getCreatedDate))
                .limit(5)
                .map(animal -> {
                    LocalDateTime fechaIngreso = LocalDateTime.ofInstant(animal.getCreatedDate().toInstant(), ZoneId.systemDefault());
                    long dias = ChronoUnit.DAYS.between(fechaIngreso.toLocalDate(), LocalDate.now());
                    String lote = animal.getLote() != null ? animal.getLote().getContramarca() : "Sin lote";
                    return DashboardReportDomain.AnimalAntiguo.create(animal.getId(), animal.getNumeroAnimal(), lote, dias, animal.getPeso(), fechaIngreso);
                })
                .collect(Collectors.toList());
    }

    private boolean cuentaPendiente(CuentaCobrarEntity cuenta) {
        if (cuenta == null || cuenta.getSaldoPendiente() == null) {
            return false;
        }
        if (cuenta.getEstado() == EstadoCuentaEnum.ANULADA || cuenta.getEstado() == EstadoCuentaEnum.COBRADA || cuenta.getEstado() == EstadoCuentaEnum.PAGADA) {
            return false;
        }
        return cuenta.getSaldoPendiente().compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean cuentaPendiente(CuentaPagarEntity cuenta) {
        if (cuenta == null || cuenta.getSaldoPendiente() == null) {
            return false;
        }
        if (cuenta.getEstado() == EstadoCuentaEnum.ANULADA || cuenta.getEstado() == EstadoCuentaEnum.COBRADA || cuenta.getEstado() == EstadoCuentaEnum.PAGADA) {
            return false;
        }
        return cuenta.getSaldoPendiente().compareTo(BigDecimal.ZERO) > 0;
    }

    private long diasAtraso(LocalDate fechaVencimiento, LocalDate hoy) {
        if (fechaVencimiento == null) {
            return 0;
        }
        long dias = ChronoUnit.DAYS.between(fechaVencimiento, hoy);
        return Math.max(dias, 0);
    }

    private BigDecimal sumarBigDecimal(List<BigDecimal> valores) {
        if (CollectionUtils.isEmpty(valores)) {
            return BigDecimal.ZERO;
        }
        return valores.stream()
                .filter(valor -> valor != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal defaultBigDecimal(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }
}

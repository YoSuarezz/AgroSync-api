package com.agrosync.domain.reportes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DashboardReportDomain {

    private CashFlow cashFlow;
    private Cartera cuentasCobrar;
    private Cartera cuentasPagar;
    private Inventario inventario;
    private Ventas ventas;
    private Compras compras;
    private Margen margen;
    private TopRelaciones topRelaciones;
    private List<LoteRentabilidad> rentabilidadLotes;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public DashboardReportDomain() {
        setCashFlow(new CashFlow());
        setCuentasCobrar(new Cartera());
        setCuentasPagar(new Cartera());
        setInventario(new Inventario());
        setVentas(new Ventas());
        setCompras(new Compras());
        setMargen(new Margen());
        setTopRelaciones(new TopRelaciones());
        setRentabilidadLotes(new ArrayList<>());
    }

    public DashboardReportDomain(CashFlow cashFlow, Cartera cuentasCobrar, Cartera cuentasPagar, Inventario inventario, Ventas ventas, Compras compras, Margen margen, TopRelaciones topRelaciones, List<LoteRentabilidad> rentabilidadLotes, LocalDate fechaInicio, LocalDate fechaFin) {
        setCashFlow(cashFlow);
        setCuentasCobrar(cuentasCobrar);
        setCuentasPagar(cuentasPagar);
        setInventario(inventario);
        setVentas(ventas);
        setCompras(compras);
        setMargen(margen);
        setTopRelaciones(topRelaciones);
        setRentabilidadLotes(rentabilidadLotes);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
    }

    public static DashboardReportDomain create(CashFlow cashFlow, Cartera cuentasCobrar, Cartera cuentasPagar, Inventario inventario, Ventas ventas, Compras compras, Margen margen, TopRelaciones topRelaciones, List<LoteRentabilidad> rentabilidadLotes, LocalDate fechaInicio, LocalDate fechaFin) {
        return new DashboardReportDomain(cashFlow, cuentasCobrar, cuentasPagar, inventario, ventas, compras, margen, topRelaciones, rentabilidadLotes, fechaInicio, fechaFin);
    }

    public CashFlow getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(CashFlow cashFlow) {
        this.cashFlow = cashFlow;
    }

    public Cartera getCuentasCobrar() {
        return cuentasCobrar;
    }

    public void setCuentasCobrar(Cartera cuentasCobrar) {
        this.cuentasCobrar = cuentasCobrar;
    }

    public Cartera getCuentasPagar() {
        return cuentasPagar;
    }

    public void setCuentasPagar(Cartera cuentasPagar) {
        this.cuentasPagar = cuentasPagar;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Ventas getVentas() {
        return ventas;
    }

    public void setVentas(Ventas ventas) {
        this.ventas = ventas;
    }

    public Compras getCompras() {
        return compras;
    }

    public void setCompras(Compras compras) {
        this.compras = compras;
    }

    public Margen getMargen() {
        return margen;
    }

    public void setMargen(Margen margen) {
        this.margen = margen;
    }

    public TopRelaciones getTopRelaciones() {
        return topRelaciones;
    }

    public void setTopRelaciones(TopRelaciones topRelaciones) {
        this.topRelaciones = topRelaciones;
    }

    public List<LoteRentabilidad> getRentabilidadLotes() {
        return rentabilidadLotes;
    }

    public void setRentabilidadLotes(List<LoteRentabilidad> rentabilidadLotes) {
        this.rentabilidadLotes = defaultList(rentabilidadLotes);
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public static class CashFlow {
        private BigDecimal totalVentas = BigDecimal.ZERO;
        private BigDecimal totalCompras = BigDecimal.ZERO;
        private BigDecimal totalCobros = BigDecimal.ZERO;
        private BigDecimal totalAbonos = BigDecimal.ZERO;
        private BigDecimal ingresosTotales = BigDecimal.ZERO;
        private BigDecimal egresosTotales = BigDecimal.ZERO;
        private BigDecimal neto = BigDecimal.ZERO;
        private BigDecimal saldoCartera = BigDecimal.ZERO;
        private BigDecimal saldoCuentasPorPagar = BigDecimal.ZERO;
        private BigDecimal proyeccionCaja = BigDecimal.ZERO;
        private List<MetodoPagoDetalle> metodosPago = new ArrayList<>();
        private List<Tendencia> tendenciaMensual = new ArrayList<>();

        public BigDecimal getTotalVentas() {
            return totalVentas;
        }

        public void setTotalVentas(BigDecimal totalVentas) {
            this.totalVentas = defaultDecimal(totalVentas);
        }

        public BigDecimal getTotalCompras() {
            return totalCompras;
        }

        public void setTotalCompras(BigDecimal totalCompras) {
            this.totalCompras = defaultDecimal(totalCompras);
        }

        public BigDecimal getTotalCobros() {
            return totalCobros;
        }

        public void setTotalCobros(BigDecimal totalCobros) {
            this.totalCobros = defaultDecimal(totalCobros);
        }

        public BigDecimal getTotalAbonos() {
            return totalAbonos;
        }

        public void setTotalAbonos(BigDecimal totalAbonos) {
            this.totalAbonos = defaultDecimal(totalAbonos);
        }

        public BigDecimal getIngresosTotales() {
            return ingresosTotales;
        }

        public void setIngresosTotales(BigDecimal ingresosTotales) {
            this.ingresosTotales = defaultDecimal(ingresosTotales);
        }

        public BigDecimal getEgresosTotales() {
            return egresosTotales;
        }

        public void setEgresosTotales(BigDecimal egresosTotales) {
            this.egresosTotales = defaultDecimal(egresosTotales);
        }

        public BigDecimal getNeto() {
            return neto;
        }

        public void setNeto(BigDecimal neto) {
            this.neto = defaultDecimal(neto);
        }

        public BigDecimal getSaldoCartera() {
            return saldoCartera;
        }

        public void setSaldoCartera(BigDecimal saldoCartera) {
            this.saldoCartera = defaultDecimal(saldoCartera);
        }

        public BigDecimal getSaldoCuentasPorPagar() {
            return saldoCuentasPorPagar;
        }

        public void setSaldoCuentasPorPagar(BigDecimal saldoCuentasPorPagar) {
            this.saldoCuentasPorPagar = defaultDecimal(saldoCuentasPorPagar);
        }

        public BigDecimal getProyeccionCaja() {
            return proyeccionCaja;
        }

        public void setProyeccionCaja(BigDecimal proyeccionCaja) {
            this.proyeccionCaja = defaultDecimal(proyeccionCaja);
        }

        public List<MetodoPagoDetalle> getMetodosPago() {
            return metodosPago;
        }

        public void setMetodosPago(List<MetodoPagoDetalle> metodosPago) {
            this.metodosPago = defaultList(metodosPago);
        }

        public List<Tendencia> getTendenciaMensual() {
            return tendenciaMensual;
        }

        public void setTendenciaMensual(List<Tendencia> tendenciaMensual) {
            this.tendenciaMensual = defaultList(tendenciaMensual);
        }
    }

    public static class MetodoPagoDetalle {
        private String metodo;
        private BigDecimal ingresos = BigDecimal.ZERO;
        private BigDecimal egresos = BigDecimal.ZERO;

        public MetodoPagoDetalle() {
        }

        public MetodoPagoDetalle(String metodo, BigDecimal ingresos, BigDecimal egresos) {
            setMetodo(metodo);
            setIngresos(ingresos);
            setEgresos(egresos);
        }

        public static MetodoPagoDetalle create(String metodo, BigDecimal ingresos, BigDecimal egresos) {
            return new MetodoPagoDetalle(metodo, ingresos, egresos);
        }

        public String getMetodo() {
            return metodo;
        }

        public void setMetodo(String metodo) {
            this.metodo = metodo;
        }

        public BigDecimal getIngresos() {
            return ingresos;
        }

        public void setIngresos(BigDecimal ingresos) {
            this.ingresos = defaultDecimal(ingresos);
        }

        public BigDecimal getEgresos() {
            return egresos;
        }

        public void setEgresos(BigDecimal egresos) {
            this.egresos = defaultDecimal(egresos);
        }
    }

    public static class Tendencia {
        private String periodo;
        private BigDecimal ingresos = BigDecimal.ZERO;
        private BigDecimal egresos = BigDecimal.ZERO;
        private BigDecimal neto = BigDecimal.ZERO;

        public Tendencia() {
        }

        public Tendencia(String periodo, BigDecimal ingresos, BigDecimal egresos, BigDecimal neto) {
            setPeriodo(periodo);
            setIngresos(ingresos);
            setEgresos(egresos);
            setNeto(neto);
        }

        public static Tendencia create(String periodo, BigDecimal ingresos, BigDecimal egresos, BigDecimal neto) {
            return new Tendencia(periodo, ingresos, egresos, neto);
        }

        public String getPeriodo() {
            return periodo;
        }

        public void setPeriodo(String periodo) {
            this.periodo = periodo;
        }

        public BigDecimal getIngresos() {
            return ingresos;
        }

        public void setIngresos(BigDecimal ingresos) {
            this.ingresos = defaultDecimal(ingresos);
        }

        public BigDecimal getEgresos() {
            return egresos;
        }

        public void setEgresos(BigDecimal egresos) {
            this.egresos = defaultDecimal(egresos);
        }

        public BigDecimal getNeto() {
            return neto;
        }

        public void setNeto(BigDecimal neto) {
            this.neto = defaultDecimal(neto);
        }
    }

    public static class Cartera {
        private BigDecimal totalPendiente = BigDecimal.ZERO;
        private BigDecimal totalVencido = BigDecimal.ZERO;
        private Double tasaRecuperacion = 0.0;
        private List<AgingBucket> antiguedad = new ArrayList<>();
        private List<EntidadSaldo> principalesEntidades = new ArrayList<>();
        private List<CuentaItem> cuentas = new ArrayList<>();
        private Integer cuentasVencidas = 0;

        public BigDecimal getTotalPendiente() {
            return totalPendiente;
        }

        public void setTotalPendiente(BigDecimal totalPendiente) {
            this.totalPendiente = defaultDecimal(totalPendiente);
        }

        public BigDecimal getTotalVencido() {
            return totalVencido;
        }

        public void setTotalVencido(BigDecimal totalVencido) {
            this.totalVencido = defaultDecimal(totalVencido);
        }

        public Double getTasaRecuperacion() {
            return tasaRecuperacion;
        }

        public void setTasaRecuperacion(Double tasaRecuperacion) {
            this.tasaRecuperacion = tasaRecuperacion == null ? 0.0 : tasaRecuperacion;
        }

        public List<AgingBucket> getAntiguedad() {
            return antiguedad;
        }

        public void setAntiguedad(List<AgingBucket> antiguedad) {
            this.antiguedad = defaultList(antiguedad);
        }

        public List<EntidadSaldo> getPrincipalesEntidades() {
            return principalesEntidades;
        }

        public void setPrincipalesEntidades(List<EntidadSaldo> principalesEntidades) {
            this.principalesEntidades = defaultList(principalesEntidades);
        }

        public List<CuentaItem> getCuentas() {
            return cuentas;
        }

        public void setCuentas(List<CuentaItem> cuentas) {
            this.cuentas = defaultList(cuentas);
        }

        public Integer getCuentasVencidas() {
            return cuentasVencidas;
        }

        public void setCuentasVencidas(Integer cuentasVencidas) {
            this.cuentasVencidas = cuentasVencidas == null ? 0 : cuentasVencidas;
        }
    }

    public static class AgingBucket {
        private String rango;
        private BigDecimal monto = BigDecimal.ZERO;
        private Integer cuentas = 0;

        public AgingBucket() {
        }

        public AgingBucket(String rango, BigDecimal monto, Integer cuentas) {
            setRango(rango);
            setMonto(monto);
            setCuentas(cuentas);
        }

        public static AgingBucket create(String rango, BigDecimal monto, Integer cuentas) {
            return new AgingBucket(rango, monto, cuentas);
        }

        public String getRango() {
            return rango;
        }

        public void setRango(String rango) {
            this.rango = rango;
        }

        public BigDecimal getMonto() {
            return monto;
        }

        public void setMonto(BigDecimal monto) {
            this.monto = defaultDecimal(monto);
        }

        public Integer getCuentas() {
            return cuentas;
        }

        public void setCuentas(Integer cuentas) {
            this.cuentas = cuentas == null ? 0 : cuentas;
        }
    }

    public static class EntidadSaldo {
        private UUID id;
        private String nombre;
        private BigDecimal saldoPendiente = BigDecimal.ZERO;

        public EntidadSaldo() {
        }

        public EntidadSaldo(UUID id, String nombre, BigDecimal saldoPendiente) {
            setId(id);
            setNombre(nombre);
            setSaldoPendiente(saldoPendiente);
        }

        public static EntidadSaldo create(UUID id, String nombre, BigDecimal saldoPendiente) {
            return new EntidadSaldo(id, nombre, saldoPendiente);
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public BigDecimal getSaldoPendiente() {
            return saldoPendiente;
        }

        public void setSaldoPendiente(BigDecimal saldoPendiente) {
            this.saldoPendiente = defaultDecimal(saldoPendiente);
        }
    }

    public static class CuentaItem {
        private UUID id;
        private String numeroCuenta;
        private String tercero;
        private BigDecimal montoTotal = BigDecimal.ZERO;
        private BigDecimal saldoPendiente = BigDecimal.ZERO;
        private LocalDate fechaVencimiento;
        private LocalDate fechaEmision;
        private String estado;
        private Long diasAtraso = 0L;

        public CuentaItem() {
        }

        public CuentaItem(UUID id, String numeroCuenta, String tercero, BigDecimal montoTotal, BigDecimal saldoPendiente, LocalDate fechaVencimiento, LocalDate fechaEmision, String estado, Long diasAtraso) {
            setId(id);
            setNumeroCuenta(numeroCuenta);
            setTercero(tercero);
            setMontoTotal(montoTotal);
            setSaldoPendiente(saldoPendiente);
            setFechaVencimiento(fechaVencimiento);
            setFechaEmision(fechaEmision);
            setEstado(estado);
            setDiasAtraso(diasAtraso);
        }

        public static CuentaItem create(UUID id, String numeroCuenta, String tercero, BigDecimal montoTotal, BigDecimal saldoPendiente, LocalDate fechaVencimiento, LocalDate fechaEmision, String estado, Long diasAtraso) {
            return new CuentaItem(id, numeroCuenta, tercero, montoTotal, saldoPendiente, fechaVencimiento, fechaEmision, estado, diasAtraso);
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getNumeroCuenta() {
            return numeroCuenta;
        }

        public void setNumeroCuenta(String numeroCuenta) {
            this.numeroCuenta = numeroCuenta;
        }

        public String getTercero() {
            return tercero;
        }

        public void setTercero(String tercero) {
            this.tercero = tercero;
        }

        public BigDecimal getMontoTotal() {
            return montoTotal;
        }

        public void setMontoTotal(BigDecimal montoTotal) {
            this.montoTotal = defaultDecimal(montoTotal);
        }

        public BigDecimal getSaldoPendiente() {
            return saldoPendiente;
        }

        public void setSaldoPendiente(BigDecimal saldoPendiente) {
            this.saldoPendiente = defaultDecimal(saldoPendiente);
        }

        public LocalDate getFechaVencimiento() {
            return fechaVencimiento;
        }

        public void setFechaVencimiento(LocalDate fechaVencimiento) {
            this.fechaVencimiento = fechaVencimiento;
        }

        public LocalDate getFechaEmision() {
            return fechaEmision;
        }

        public void setFechaEmision(LocalDate fechaEmision) {
            this.fechaEmision = fechaEmision;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public Long getDiasAtraso() {
            return diasAtraso;
        }

        public void setDiasAtraso(Long diasAtraso) {
            this.diasAtraso = diasAtraso == null ? 0L : diasAtraso;
        }
    }

    public static class Inventario {
        private Integer totalDisponibles = 0;
        private Integer totalVendidos = 0;
        private BigDecimal pesoTotal = BigDecimal.ZERO;
        private BigDecimal valorInventario = BigDecimal.ZERO;
        private BigDecimal valorPotencialVenta = BigDecimal.ZERO;
        private BigDecimal margenPotencial = BigDecimal.ZERO;
        private List<Distribucion> porSexo = new ArrayList<>();
        private List<Distribucion> porLote = new ArrayList<>();
        private List<AnimalAntiguo> animalesAntiguos = new ArrayList<>();

        public Integer getTotalDisponibles() {
            return totalDisponibles;
        }

        public void setTotalDisponibles(Integer totalDisponibles) {
            this.totalDisponibles = totalDisponibles == null ? 0 : totalDisponibles;
        }

        public Integer getTotalVendidos() {
            return totalVendidos;
        }

        public void setTotalVendidos(Integer totalVendidos) {
            this.totalVendidos = totalVendidos == null ? 0 : totalVendidos;
        }

        public BigDecimal getPesoTotal() {
            return pesoTotal;
        }

        public void setPesoTotal(BigDecimal pesoTotal) {
            this.pesoTotal = defaultDecimal(pesoTotal);
        }

        public BigDecimal getValorInventario() {
            return valorInventario;
        }

        public void setValorInventario(BigDecimal valorInventario) {
            this.valorInventario = defaultDecimal(valorInventario);
        }

        public BigDecimal getValorPotencialVenta() {
            return valorPotencialVenta;
        }

        public void setValorPotencialVenta(BigDecimal valorPotencialVenta) {
            this.valorPotencialVenta = defaultDecimal(valorPotencialVenta);
        }

        public BigDecimal getMargenPotencial() {
            return margenPotencial;
        }

        public void setMargenPotencial(BigDecimal margenPotencial) {
            this.margenPotencial = defaultDecimal(margenPotencial);
        }

        public List<Distribucion> getPorSexo() {
            return porSexo;
        }

        public void setPorSexo(List<Distribucion> porSexo) {
            this.porSexo = defaultList(porSexo);
        }

        public List<Distribucion> getPorLote() {
            return porLote;
        }

        public void setPorLote(List<Distribucion> porLote) {
            this.porLote = defaultList(porLote);
        }

        public List<AnimalAntiguo> getAnimalesAntiguos() {
            return animalesAntiguos;
        }

        public void setAnimalesAntiguos(List<AnimalAntiguo> animalesAntiguos) {
            this.animalesAntiguos = defaultList(animalesAntiguos);
        }
    }

    public static class Distribucion {
        private String etiqueta;
        private Long cantidad = 0L;
        private BigDecimal total = BigDecimal.ZERO;

        public Distribucion() {
        }

        public Distribucion(String etiqueta, Long cantidad, BigDecimal total) {
            setEtiqueta(etiqueta);
            setCantidad(cantidad);
            setTotal(total);
        }

        public static Distribucion create(String etiqueta, Long cantidad, BigDecimal total) {
            return new Distribucion(etiqueta, cantidad, total);
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public void setEtiqueta(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        public Long getCantidad() {
            return cantidad;
        }

        public void setCantidad(Long cantidad) {
            this.cantidad = cantidad == null ? 0L : cantidad;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = defaultDecimal(total);
        }
    }

    public static class AnimalAntiguo {
        private UUID id;
        private String numeroAnimal;
        private String lote;
        private Long diasEnInventario = 0L;
        private BigDecimal peso = BigDecimal.ZERO;
        private LocalDateTime fechaIngreso;

        public AnimalAntiguo() {
        }

        public AnimalAntiguo(UUID id, String numeroAnimal, String lote, Long diasEnInventario, BigDecimal peso, LocalDateTime fechaIngreso) {
            setId(id);
            setNumeroAnimal(numeroAnimal);
            setLote(lote);
            setDiasEnInventario(diasEnInventario);
            setPeso(peso);
            setFechaIngreso(fechaIngreso);
        }

        public static AnimalAntiguo create(UUID id, String numeroAnimal, String lote, Long diasEnInventario, BigDecimal peso, LocalDateTime fechaIngreso) {
            return new AnimalAntiguo(id, numeroAnimal, lote, diasEnInventario, peso, fechaIngreso);
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getNumeroAnimal() {
            return numeroAnimal;
        }

        public void setNumeroAnimal(String numeroAnimal) {
            this.numeroAnimal = numeroAnimal;
        }

        public String getLote() {
            return lote;
        }

        public void setLote(String lote) {
            this.lote = lote;
        }

        public Long getDiasEnInventario() {
            return diasEnInventario;
        }

        public void setDiasEnInventario(Long diasEnInventario) {
            this.diasEnInventario = diasEnInventario == null ? 0L : diasEnInventario;
        }

        public BigDecimal getPeso() {
            return peso;
        }

        public void setPeso(BigDecimal peso) {
            this.peso = defaultDecimal(peso);
        }

        public LocalDateTime getFechaIngreso() {
            return fechaIngreso;
        }

        public void setFechaIngreso(LocalDateTime fechaIngreso) {
            this.fechaIngreso = fechaIngreso;
        }
    }

    private static BigDecimal defaultDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private static <T> List<T> defaultList(List<T> value) {
        return value == null ? new ArrayList<>() : value;
    }

    // Nuevos bloques estratégicos
    public static class Ventas {
        private BigDecimal totalVentas = BigDecimal.ZERO;
        private BigDecimal ticketPromedio = BigDecimal.ZERO;
        private Long animalesVendidos = 0L;
        private BigDecimal precioPromedioKilo = BigDecimal.ZERO;
        private List<EntidadSaldo> topClientes = new ArrayList<>();
        private Long clientesNuevos = 0L;
        private Long clientesRecurrentes = 0L;
        private List<Tendencia> tendencia = new ArrayList<>();

        public BigDecimal getTotalVentas() {
            return totalVentas;
        }

        public void setTotalVentas(BigDecimal totalVentas) {
            this.totalVentas = defaultDecimal(totalVentas);
        }

        public BigDecimal getTicketPromedio() {
            return ticketPromedio;
        }

        public void setTicketPromedio(BigDecimal ticketPromedio) {
            this.ticketPromedio = defaultDecimal(ticketPromedio);
        }

        public Long getAnimalesVendidos() {
            return animalesVendidos;
        }

        public void setAnimalesVendidos(Long animalesVendidos) {
            this.animalesVendidos = animalesVendidos == null ? 0L : animalesVendidos;
        }

        public BigDecimal getPrecioPromedioKilo() {
            return precioPromedioKilo;
        }

        public void setPrecioPromedioKilo(BigDecimal precioPromedioKilo) {
            this.precioPromedioKilo = defaultDecimal(precioPromedioKilo);
        }

        public List<EntidadSaldo> getTopClientes() {
            return topClientes;
        }

        public void setTopClientes(List<EntidadSaldo> topClientes) {
            this.topClientes = defaultList(topClientes);
        }

        public Long getClientesNuevos() {
            return clientesNuevos;
        }

        public void setClientesNuevos(Long clientesNuevos) {
            this.clientesNuevos = clientesNuevos == null ? 0L : clientesNuevos;
        }

        public Long getClientesRecurrentes() {
            return clientesRecurrentes;
        }

        public void setClientesRecurrentes(Long clientesRecurrentes) {
            this.clientesRecurrentes = clientesRecurrentes == null ? 0L : clientesRecurrentes;
        }

        public List<Tendencia> getTendencia() {
            return tendencia;
        }

        public void setTendencia(List<Tendencia> tendencia) {
            this.tendencia = defaultList(tendencia);
        }
    }

    public static class Compras {
        private BigDecimal totalCompras = BigDecimal.ZERO;
        private BigDecimal compraPromedio = BigDecimal.ZERO;
        private Long animalesComprados = 0L;
        private BigDecimal precioPromedioKiloCompra = BigDecimal.ZERO;
        private List<EntidadSaldo> topProveedores = new ArrayList<>();
        private BigDecimal frecuenciaMensual = BigDecimal.ZERO;
        private List<Tendencia> tendencia = new ArrayList<>();

        public BigDecimal getTotalCompras() {
            return totalCompras;
        }

        public void setTotalCompras(BigDecimal totalCompras) {
            this.totalCompras = defaultDecimal(totalCompras);
        }

        public BigDecimal getCompraPromedio() {
            return compraPromedio;
        }

        public void setCompraPromedio(BigDecimal compraPromedio) {
            this.compraPromedio = defaultDecimal(compraPromedio);
        }

        public Long getAnimalesComprados() {
            return animalesComprados;
        }

        public void setAnimalesComprados(Long animalesComprados) {
            this.animalesComprados = animalesComprados == null ? 0L : animalesComprados;
        }

        public BigDecimal getPrecioPromedioKiloCompra() {
            return precioPromedioKiloCompra;
        }

        public void setPrecioPromedioKiloCompra(BigDecimal precioPromedioKiloCompra) {
            this.precioPromedioKiloCompra = defaultDecimal(precioPromedioKiloCompra);
        }

        public List<EntidadSaldo> getTopProveedores() {
            return topProveedores;
        }

        public void setTopProveedores(List<EntidadSaldo> topProveedores) {
            this.topProveedores = defaultList(topProveedores);
        }

        public BigDecimal getFrecuenciaMensual() {
            return frecuenciaMensual;
        }

        public void setFrecuenciaMensual(BigDecimal frecuenciaMensual) {
            this.frecuenciaMensual = defaultDecimal(frecuenciaMensual);
        }

        public List<Tendencia> getTendencia() {
            return tendencia;
        }

        public void setTendencia(List<Tendencia> tendencia) {
            this.tendencia = defaultList(tendencia);
        }
    }

    public static class Margen {
        private BigDecimal margenBruto = BigDecimal.ZERO;
        private BigDecimal margenNeto = BigDecimal.ZERO;
        private BigDecimal margenPorKilo = BigDecimal.ZERO;
        private BigDecimal puntoEquilibrio = BigDecimal.ZERO;
        private List<Tendencia> tendencia = new ArrayList<>();

        public BigDecimal getMargenBruto() {
            return margenBruto;
        }

        public void setMargenBruto(BigDecimal margenBruto) {
            this.margenBruto = defaultDecimal(margenBruto);
        }

        public BigDecimal getMargenNeto() {
            return margenNeto;
        }

        public void setMargenNeto(BigDecimal margenNeto) {
            this.margenNeto = defaultDecimal(margenNeto);
        }

        public BigDecimal getMargenPorKilo() {
            return margenPorKilo;
        }

        public void setMargenPorKilo(BigDecimal margenPorKilo) {
            this.margenPorKilo = defaultDecimal(margenPorKilo);
        }

        public BigDecimal getPuntoEquilibrio() {
            return puntoEquilibrio;
        }

        public void setPuntoEquilibrio(BigDecimal puntoEquilibrio) {
            this.puntoEquilibrio = defaultDecimal(puntoEquilibrio);
        }

        public List<Tendencia> getTendencia() {
            return tendencia;
        }

        public void setTendencia(List<Tendencia> tendencia) {
            this.tendencia = defaultList(tendencia);
        }
    }

    public static class TopRelaciones {
        private List<EntidadSaldo> topClientesVolumen = new ArrayList<>();
        private List<EntidadSaldo> topClientesFrecuencia = new ArrayList<>();
        private List<EntidadSaldo> topProveedoresVolumen = new ArrayList<>();
        private List<EntidadSaldo> topProveedoresFrecuencia = new ArrayList<>();

        public List<EntidadSaldo> getTopClientesVolumen() {
            return topClientesVolumen;
        }

        public void setTopClientesVolumen(List<EntidadSaldo> topClientesVolumen) {
            this.topClientesVolumen = defaultList(topClientesVolumen);
        }

        public List<EntidadSaldo> getTopClientesFrecuencia() {
            return topClientesFrecuencia;
        }

        public void setTopClientesFrecuencia(List<EntidadSaldo> topClientesFrecuencia) {
            this.topClientesFrecuencia = defaultList(topClientesFrecuencia);
        }

        public List<EntidadSaldo> getTopProveedoresVolumen() {
            return topProveedoresVolumen;
        }

        public void setTopProveedoresVolumen(List<EntidadSaldo> topProveedoresVolumen) {
            this.topProveedoresVolumen = defaultList(topProveedoresVolumen);
        }

        public List<EntidadSaldo> getTopProveedoresFrecuencia() {
            return topProveedoresFrecuencia;
        }

        public void setTopProveedoresFrecuencia(List<EntidadSaldo> topProveedoresFrecuencia) {
            this.topProveedoresFrecuencia = defaultList(topProveedoresFrecuencia);
        }
    }

    public static class LoteRentabilidad {
        private String contramarca;
        private String numeroLote;
        private LocalDate fecha;
        private BigDecimal costoTotal = BigDecimal.ZERO;
        private BigDecimal ingresosTotales = BigDecimal.ZERO;
        private BigDecimal gananciaNeta = BigDecimal.ZERO;
        private BigDecimal margen = BigDecimal.ZERO;
        private BigDecimal roi = BigDecimal.ZERO;
        private BigDecimal precioPromedioCompraKg = BigDecimal.ZERO;
        private BigDecimal precioPromedioVentaKg = BigDecimal.ZERO;
        private Long tiempoRotacionDias = 0L;

        public String getContramarca() {
            return contramarca;
        }

        public void setContramarca(String contramarca) {
            this.contramarca = contramarca;
        }

        public String getNumeroLote() {
            return numeroLote;
        }

        public void setNumeroLote(String numeroLote) {
            this.numeroLote = numeroLote;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public BigDecimal getCostoTotal() {
            return costoTotal;
        }

        public void setCostoTotal(BigDecimal costoTotal) {
            this.costoTotal = defaultDecimal(costoTotal);
        }

        public BigDecimal getIngresosTotales() {
            return ingresosTotales;
        }

        public void setIngresosTotales(BigDecimal ingresosTotales) {
            this.ingresosTotales = defaultDecimal(ingresosTotales);
        }

        public BigDecimal getGananciaNeta() {
            return gananciaNeta;
        }

        public void setGananciaNeta(BigDecimal gananciaNeta) {
            this.gananciaNeta = defaultDecimal(gananciaNeta);
        }

        public BigDecimal getMargen() {
            return margen;
        }

        public void setMargen(BigDecimal margen) {
            this.margen = defaultDecimal(margen);
        }

        public BigDecimal getRoi() {
            return roi;
        }

        public void setRoi(BigDecimal roi) {
            this.roi = defaultDecimal(roi);
        }

        public BigDecimal getPrecioPromedioCompraKg() {
            return precioPromedioCompraKg;
        }

        public void setPrecioPromedioCompraKg(BigDecimal precioPromedioCompraKg) {
            this.precioPromedioCompraKg = defaultDecimal(precioPromedioCompraKg);
        }

        public BigDecimal getPrecioPromedioVentaKg() {
            return precioPromedioVentaKg;
        }

        public void setPrecioPromedioVentaKg(BigDecimal precioPromedioVentaKg) {
            this.precioPromedioVentaKg = defaultDecimal(precioPromedioVentaKg);
        }

        public Long getTiempoRotacionDias() {
            return tiempoRotacionDias;
        }

        public void setTiempoRotacionDias(Long tiempoRotacionDias) {
            this.tiempoRotacionDias = tiempoRotacionDias == null ? 0L : tiempoRotacionDias;
        }
    }
}

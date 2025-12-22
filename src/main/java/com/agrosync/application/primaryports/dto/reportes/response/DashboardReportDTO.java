package com.agrosync.application.primaryports.dto.reportes.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DashboardReportDTO {

    private CashFlowDTO flujoCaja;
    private CarteraDTO cuentasCobrar;
    private CarteraDTO cuentasPagar;
    private InventarioDTO inventario;
    private VentasDTO ventas;
    private ComprasDTO compras;
    private MargenDTO margen;
    private TopRelacionesDTO topRelaciones;
    private List<LoteRentabilidadDTO> rentabilidadLotes = new ArrayList<>();
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public DashboardReportDTO() {
        setFlujoCaja(new CashFlowDTO());
        setCuentasCobrar(new CarteraDTO());
        setCuentasPagar(new CarteraDTO());
        setInventario(new InventarioDTO());
    }

    public DashboardReportDTO(CashFlowDTO flujoCaja, CarteraDTO cuentasCobrar, CarteraDTO cuentasPagar, InventarioDTO inventario, LocalDate fechaInicio, LocalDate fechaFin) {
        setFlujoCaja(flujoCaja);
        setCuentasCobrar(cuentasCobrar);
        setCuentasPagar(cuentasPagar);
        setInventario(inventario);
        setFechaInicio(fechaInicio);
        setFechaFin(fechaFin);
    }

    public CashFlowDTO getFlujoCaja() {
        return flujoCaja;
    }

    public void setFlujoCaja(CashFlowDTO flujoCaja) {
        this.flujoCaja = flujoCaja;
    }

    public CarteraDTO getCuentasCobrar() {
        return cuentasCobrar;
    }

    public void setCuentasCobrar(CarteraDTO cuentasCobrar) {
        this.cuentasCobrar = cuentasCobrar;
    }

    public CarteraDTO getCuentasPagar() {
        return cuentasPagar;
    }

    public void setCuentasPagar(CarteraDTO cuentasPagar) {
        this.cuentasPagar = cuentasPagar;
    }

    public InventarioDTO getInventario() {
        return inventario;
    }

    public void setInventario(InventarioDTO inventario) {
        this.inventario = inventario;
    }

    public VentasDTO getVentas() {
        return ventas;
    }

    public void setVentas(VentasDTO ventas) {
        this.ventas = ventas;
    }

    public ComprasDTO getCompras() {
        return compras;
    }

    public void setCompras(ComprasDTO compras) {
        this.compras = compras;
    }

    public MargenDTO getMargen() {
        return margen;
    }

    public void setMargen(MargenDTO margen) {
        this.margen = margen;
    }

    public TopRelacionesDTO getTopRelaciones() {
        return topRelaciones;
    }

    public void setTopRelaciones(TopRelacionesDTO topRelaciones) {
        this.topRelaciones = topRelaciones;
    }

    public List<LoteRentabilidadDTO> getRentabilidadLotes() {
        return rentabilidadLotes;
    }

    public void setRentabilidadLotes(List<LoteRentabilidadDTO> rentabilidadLotes) {
        this.rentabilidadLotes = rentabilidadLotes;
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

    public static class CashFlowDTO {
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
        private List<MetodoPagoDetalleDTO> metodosPago = new ArrayList<>();
        private List<TendenciaDTO> tendenciaMensual = new ArrayList<>();

        public BigDecimal getTotalVentas() {
            return totalVentas;
        }

        public void setTotalVentas(BigDecimal totalVentas) {
            this.totalVentas = totalVentas;
        }

        public BigDecimal getTotalCompras() {
            return totalCompras;
        }

        public void setTotalCompras(BigDecimal totalCompras) {
            this.totalCompras = totalCompras;
        }

        public BigDecimal getTotalCobros() {
            return totalCobros;
        }

        public void setTotalCobros(BigDecimal totalCobros) {
            this.totalCobros = totalCobros;
        }

        public BigDecimal getTotalAbonos() {
            return totalAbonos;
        }

        public void setTotalAbonos(BigDecimal totalAbonos) {
            this.totalAbonos = totalAbonos;
        }

        public BigDecimal getIngresosTotales() {
            return ingresosTotales;
        }

        public void setIngresosTotales(BigDecimal ingresosTotales) {
            this.ingresosTotales = ingresosTotales;
        }

        public BigDecimal getEgresosTotales() {
            return egresosTotales;
        }

        public void setEgresosTotales(BigDecimal egresosTotales) {
            this.egresosTotales = egresosTotales;
        }

        public BigDecimal getNeto() {
            return neto;
        }

        public void setNeto(BigDecimal neto) {
            this.neto = neto;
        }

        public BigDecimal getSaldoCartera() {
            return saldoCartera;
        }

        public void setSaldoCartera(BigDecimal saldoCartera) {
            this.saldoCartera = saldoCartera;
        }

        public BigDecimal getSaldoCuentasPorPagar() {
            return saldoCuentasPorPagar;
        }

        public void setSaldoCuentasPorPagar(BigDecimal saldoCuentasPorPagar) {
            this.saldoCuentasPorPagar = saldoCuentasPorPagar;
        }

        public BigDecimal getProyeccionCaja() {
            return proyeccionCaja;
        }

        public void setProyeccionCaja(BigDecimal proyeccionCaja) {
            this.proyeccionCaja = proyeccionCaja;
        }

        public List<MetodoPagoDetalleDTO> getMetodosPago() {
            return metodosPago;
        }

        public void setMetodosPago(List<MetodoPagoDetalleDTO> metodosPago) {
            this.metodosPago = metodosPago;
        }

        public List<TendenciaDTO> getTendenciaMensual() {
            return tendenciaMensual;
        }

        public void setTendenciaMensual(List<TendenciaDTO> tendenciaMensual) {
            this.tendenciaMensual = tendenciaMensual;
        }
    }

    public static class MetodoPagoDetalleDTO {
        private String metodo;
        private BigDecimal ingresos = BigDecimal.ZERO;
        private BigDecimal egresos = BigDecimal.ZERO;

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
            this.ingresos = ingresos;
        }

        public BigDecimal getEgresos() {
            return egresos;
        }

        public void setEgresos(BigDecimal egresos) {
            this.egresos = egresos;
        }
    }

    public static class TendenciaDTO {
        private String periodo;
        private BigDecimal ingresos = BigDecimal.ZERO;
        private BigDecimal egresos = BigDecimal.ZERO;
        private BigDecimal neto = BigDecimal.ZERO;

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
            this.ingresos = ingresos;
        }

        public BigDecimal getEgresos() {
            return egresos;
        }

        public void setEgresos(BigDecimal egresos) {
            this.egresos = egresos;
        }

        public BigDecimal getNeto() {
            return neto;
        }

        public void setNeto(BigDecimal neto) {
            this.neto = neto;
        }
    }

    public static class CarteraDTO {
        private BigDecimal totalPendiente = BigDecimal.ZERO;
        private BigDecimal totalVencido = BigDecimal.ZERO;
        private Double tasaRecuperacion = 0.0;
        private List<AgingBucketDTO> antiguedad = new ArrayList<>();
        private List<EntidadSaldoDTO> principalesEntidades = new ArrayList<>();
        private List<CuentaItemDTO> cuentas = new ArrayList<>();
        private Integer cuentasVencidas = 0;

        public BigDecimal getTotalPendiente() {
            return totalPendiente;
        }

        public void setTotalPendiente(BigDecimal totalPendiente) {
            this.totalPendiente = totalPendiente;
        }

        public BigDecimal getTotalVencido() {
            return totalVencido;
        }

        public void setTotalVencido(BigDecimal totalVencido) {
            this.totalVencido = totalVencido;
        }

        public Double getTasaRecuperacion() {
            return tasaRecuperacion;
        }

        public void setTasaRecuperacion(Double tasaRecuperacion) {
            this.tasaRecuperacion = tasaRecuperacion;
        }

        public List<AgingBucketDTO> getAntiguedad() {
            return antiguedad;
        }

        public void setAntiguedad(List<AgingBucketDTO> antiguedad) {
            this.antiguedad = antiguedad;
        }

        public List<EntidadSaldoDTO> getPrincipalesEntidades() {
            return principalesEntidades;
        }

        public void setPrincipalesEntidades(List<EntidadSaldoDTO> principalesEntidades) {
            this.principalesEntidades = principalesEntidades;
        }

        public List<CuentaItemDTO> getCuentas() {
            return cuentas;
        }

        public void setCuentas(List<CuentaItemDTO> cuentas) {
            this.cuentas = cuentas;
        }

        public Integer getCuentasVencidas() {
            return cuentasVencidas;
        }

        public void setCuentasVencidas(Integer cuentasVencidas) {
            this.cuentasVencidas = cuentasVencidas;
        }
    }

    public static class AgingBucketDTO {
        private String rango;
        private BigDecimal monto = BigDecimal.ZERO;
        private Integer cuentas = 0;

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
            this.monto = monto;
        }

        public Integer getCuentas() {
            return cuentas;
        }

        public void setCuentas(Integer cuentas) {
            this.cuentas = cuentas;
        }
    }

    public static class EntidadSaldoDTO {
        private UUID id;
        private String nombre;
        private BigDecimal saldoPendiente = BigDecimal.ZERO;

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
            this.saldoPendiente = saldoPendiente;
        }
    }

    public static class CuentaItemDTO {
        private UUID id;
        private String numeroCuenta;
        private String tercero;
        private BigDecimal montoTotal = BigDecimal.ZERO;
        private BigDecimal saldoPendiente = BigDecimal.ZERO;
        private LocalDate fechaVencimiento;
        private LocalDate fechaEmision;
        private String estado;
        private Long diasAtraso = 0L;

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
            this.montoTotal = montoTotal;
        }

        public BigDecimal getSaldoPendiente() {
            return saldoPendiente;
        }

        public void setSaldoPendiente(BigDecimal saldoPendiente) {
            this.saldoPendiente = saldoPendiente;
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
            this.diasAtraso = diasAtraso;
        }
    }

    public static class InventarioDTO {
        private Integer totalDisponibles = 0;
        private Integer totalVendidos = 0;
        private BigDecimal pesoTotal = BigDecimal.ZERO;
        private BigDecimal valorInventario = BigDecimal.ZERO;
        private BigDecimal valorPotencialVenta = BigDecimal.ZERO;
        private BigDecimal margenPotencial = BigDecimal.ZERO;
        private List<DistribucionDTO> porSexo = new ArrayList<>();
        private List<DistribucionDTO> porLote = new ArrayList<>();
        private List<AnimalAntiguoDTO> animalesAntiguos = new ArrayList<>();

        public Integer getTotalDisponibles() {
            return totalDisponibles;
        }

        public void setTotalDisponibles(Integer totalDisponibles) {
            this.totalDisponibles = totalDisponibles;
        }

        public Integer getTotalVendidos() {
            return totalVendidos;
        }

        public void setTotalVendidos(Integer totalVendidos) {
            this.totalVendidos = totalVendidos;
        }

        public BigDecimal getPesoTotal() {
            return pesoTotal;
        }

        public void setPesoTotal(BigDecimal pesoTotal) {
            this.pesoTotal = pesoTotal;
        }

        public BigDecimal getValorInventario() {
            return valorInventario;
        }

        public void setValorInventario(BigDecimal valorInventario) {
            this.valorInventario = valorInventario;
        }

        public BigDecimal getValorPotencialVenta() {
            return valorPotencialVenta;
        }

        public void setValorPotencialVenta(BigDecimal valorPotencialVenta) {
            this.valorPotencialVenta = valorPotencialVenta;
        }

        public BigDecimal getMargenPotencial() {
            return margenPotencial;
        }

        public void setMargenPotencial(BigDecimal margenPotencial) {
            this.margenPotencial = margenPotencial;
        }

        public List<DistribucionDTO> getPorSexo() {
            return porSexo;
        }

        public void setPorSexo(List<DistribucionDTO> porSexo) {
            this.porSexo = porSexo;
        }

        public List<DistribucionDTO> getPorLote() {
            return porLote;
        }

        public void setPorLote(List<DistribucionDTO> porLote) {
            this.porLote = porLote;
        }

        public List<AnimalAntiguoDTO> getAnimalesAntiguos() {
            return animalesAntiguos;
        }

        public void setAnimalesAntiguos(List<AnimalAntiguoDTO> animalesAntiguos) {
            this.animalesAntiguos = animalesAntiguos;
        }
    }

    public static class DistribucionDTO {
        private String etiqueta;
        private Long cantidad = 0L;
        private BigDecimal total = BigDecimal.ZERO;

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
            this.cantidad = cantidad;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }
    }

    public static class AnimalAntiguoDTO {
        private UUID id;
        private String numeroAnimal;
        private String lote;
        private Long diasEnInventario = 0L;
        private BigDecimal peso = BigDecimal.ZERO;
        private LocalDateTime fechaIngreso;

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
            this.diasEnInventario = diasEnInventario;
        }

        public BigDecimal getPeso() {
            return peso;
        }

        public void setPeso(BigDecimal peso) {
            this.peso = peso;
        }

        public LocalDateTime getFechaIngreso() {
            return fechaIngreso;
        }

        public void setFechaIngreso(LocalDateTime fechaIngreso) {
            this.fechaIngreso = fechaIngreso;
        }
    }

    // Nuevos bloques estratégicos
    public static class VentasDTO {
        private BigDecimal totalVentas = BigDecimal.ZERO;
        private BigDecimal ticketPromedio = BigDecimal.ZERO;
        private Long animalesVendidos = 0L;
        private BigDecimal precioPromedioKilo = BigDecimal.ZERO;
        private List<EntidadSaldoDTO> topClientes = new ArrayList<>();
        private Long clientesNuevos = 0L;
        private Long clientesRecurrentes = 0L;
        private List<TendenciaDTO> tendencia = new ArrayList<>();

        public BigDecimal getTotalVentas() {
            return totalVentas;
        }

        public void setTotalVentas(BigDecimal totalVentas) {
            this.totalVentas = totalVentas;
        }

        public BigDecimal getTicketPromedio() {
            return ticketPromedio;
        }

        public void setTicketPromedio(BigDecimal ticketPromedio) {
            this.ticketPromedio = ticketPromedio;
        }

        public Long getAnimalesVendidos() {
            return animalesVendidos;
        }

        public void setAnimalesVendidos(Long animalesVendidos) {
            this.animalesVendidos = animalesVendidos;
        }

        public BigDecimal getPrecioPromedioKilo() {
            return precioPromedioKilo;
        }

        public void setPrecioPromedioKilo(BigDecimal precioPromedioKilo) {
            this.precioPromedioKilo = precioPromedioKilo;
        }

        public List<EntidadSaldoDTO> getTopClientes() {
            return topClientes;
        }

        public void setTopClientes(List<EntidadSaldoDTO> topClientes) {
            this.topClientes = topClientes;
        }

        public Long getClientesNuevos() {
            return clientesNuevos;
        }

        public void setClientesNuevos(Long clientesNuevos) {
            this.clientesNuevos = clientesNuevos;
        }

        public Long getClientesRecurrentes() {
            return clientesRecurrentes;
        }

        public void setClientesRecurrentes(Long clientesRecurrentes) {
            this.clientesRecurrentes = clientesRecurrentes;
        }

        public List<TendenciaDTO> getTendencia() {
            return tendencia;
        }

        public void setTendencia(List<TendenciaDTO> tendencia) {
            this.tendencia = tendencia;
        }
    }

    public static class ComprasDTO {
        private BigDecimal totalCompras = BigDecimal.ZERO;
        private BigDecimal compraPromedio = BigDecimal.ZERO;
        private Long animalesComprados = 0L;
        private BigDecimal precioPromedioKiloCompra = BigDecimal.ZERO;
        private List<EntidadSaldoDTO> topProveedores = new ArrayList<>();
        private BigDecimal frecuenciaMensual = BigDecimal.ZERO;
        private List<TendenciaDTO> tendencia = new ArrayList<>();

        public BigDecimal getTotalCompras() {
            return totalCompras;
        }

        public void setTotalCompras(BigDecimal totalCompras) {
            this.totalCompras = totalCompras;
        }

        public BigDecimal getCompraPromedio() {
            return compraPromedio;
        }

        public void setCompraPromedio(BigDecimal compraPromedio) {
            this.compraPromedio = compraPromedio;
        }

        public Long getAnimalesComprados() {
            return animalesComprados;
        }

        public void setAnimalesComprados(Long animalesComprados) {
            this.animalesComprados = animalesComprados;
        }

        public BigDecimal getPrecioPromedioKiloCompra() {
            return precioPromedioKiloCompra;
        }

        public void setPrecioPromedioKiloCompra(BigDecimal precioPromedioKiloCompra) {
            this.precioPromedioKiloCompra = precioPromedioKiloCompra;
        }

        public List<EntidadSaldoDTO> getTopProveedores() {
            return topProveedores;
        }

        public void setTopProveedores(List<EntidadSaldoDTO> topProveedores) {
            this.topProveedores = topProveedores;
        }

        public BigDecimal getFrecuenciaMensual() {
            return frecuenciaMensual;
        }

        public void setFrecuenciaMensual(BigDecimal frecuenciaMensual) {
            this.frecuenciaMensual = frecuenciaMensual;
        }

        public List<TendenciaDTO> getTendencia() {
            return tendencia;
        }

        public void setTendencia(List<TendenciaDTO> tendencia) {
            this.tendencia = tendencia;
        }
    }

    public static class MargenDTO {
        private BigDecimal margenBruto = BigDecimal.ZERO;
        private BigDecimal margenNeto = BigDecimal.ZERO;
        private BigDecimal margenPorKilo = BigDecimal.ZERO;
        private BigDecimal puntoEquilibrio = BigDecimal.ZERO;
        private List<TendenciaDTO> tendencia = new ArrayList<>();

        public BigDecimal getMargenBruto() {
            return margenBruto;
        }

        public void setMargenBruto(BigDecimal margenBruto) {
            this.margenBruto = margenBruto;
        }

        public BigDecimal getMargenNeto() {
            return margenNeto;
        }

        public void setMargenNeto(BigDecimal margenNeto) {
            this.margenNeto = margenNeto;
        }

        public BigDecimal getMargenPorKilo() {
            return margenPorKilo;
        }

        public void setMargenPorKilo(BigDecimal margenPorKilo) {
            this.margenPorKilo = margenPorKilo;
        }

        public BigDecimal getPuntoEquilibrio() {
            return puntoEquilibrio;
        }

        public void setPuntoEquilibrio(BigDecimal puntoEquilibrio) {
            this.puntoEquilibrio = puntoEquilibrio;
        }

        public List<TendenciaDTO> getTendencia() {
            return tendencia;
        }

        public void setTendencia(List<TendenciaDTO> tendencia) {
            this.tendencia = tendencia;
        }
    }

    public static class TopRelacionesDTO {
        private List<EntidadSaldoDTO> topClientesVolumen = new ArrayList<>();
        private List<EntidadSaldoDTO> topClientesFrecuencia = new ArrayList<>();
        private List<EntidadSaldoDTO> topProveedoresVolumen = new ArrayList<>();
        private List<EntidadSaldoDTO> topProveedoresFrecuencia = new ArrayList<>();

        public List<EntidadSaldoDTO> getTopClientesVolumen() {
            return topClientesVolumen;
        }

        public void setTopClientesVolumen(List<EntidadSaldoDTO> topClientesVolumen) {
            this.topClientesVolumen = topClientesVolumen;
        }

        public List<EntidadSaldoDTO> getTopClientesFrecuencia() {
            return topClientesFrecuencia;
        }

        public void setTopClientesFrecuencia(List<EntidadSaldoDTO> topClientesFrecuencia) {
            this.topClientesFrecuencia = topClientesFrecuencia;
        }

        public List<EntidadSaldoDTO> getTopProveedoresVolumen() {
            return topProveedoresVolumen;
        }

        public void setTopProveedoresVolumen(List<EntidadSaldoDTO> topProveedoresVolumen) {
            this.topProveedoresVolumen = topProveedoresVolumen;
        }

        public List<EntidadSaldoDTO> getTopProveedoresFrecuencia() {
            return topProveedoresFrecuencia;
        }

        public void setTopProveedoresFrecuencia(List<EntidadSaldoDTO> topProveedoresFrecuencia) {
            this.topProveedoresFrecuencia = topProveedoresFrecuencia;
        }
    }

    public static class LoteRentabilidadDTO {
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
            this.costoTotal = costoTotal;
        }

        public BigDecimal getIngresosTotales() {
            return ingresosTotales;
        }

        public void setIngresosTotales(BigDecimal ingresosTotales) {
            this.ingresosTotales = ingresosTotales;
        }

        public BigDecimal getGananciaNeta() {
            return gananciaNeta;
        }

        public void setGananciaNeta(BigDecimal gananciaNeta) {
            this.gananciaNeta = gananciaNeta;
        }

        public BigDecimal getMargen() {
            return margen;
        }

        public void setMargen(BigDecimal margen) {
            this.margen = margen;
        }

        public BigDecimal getRoi() {
            return roi;
        }

        public void setRoi(BigDecimal roi) {
            this.roi = roi;
        }

        public BigDecimal getPrecioPromedioCompraKg() {
            return precioPromedioCompraKg;
        }

        public void setPrecioPromedioCompraKg(BigDecimal precioPromedioCompraKg) {
            this.precioPromedioCompraKg = precioPromedioCompraKg;
        }

        public BigDecimal getPrecioPromedioVentaKg() {
            return precioPromedioVentaKg;
        }

        public void setPrecioPromedioVentaKg(BigDecimal precioPromedioVentaKg) {
            this.precioPromedioVentaKg = precioPromedioVentaKg;
        }

        public Long getTiempoRotacionDias() {
            return tiempoRotacionDias;
        }

        public void setTiempoRotacionDias(Long tiempoRotacionDias) {
            this.tiempoRotacionDias = tiempoRotacionDias;
        }
    }
}

package com.agrosync.application.primaryports.mapper.reportes;

import com.agrosync.application.primaryports.dto.reportes.request.ReporteDashboardFiltroDTO;
import com.agrosync.application.primaryports.dto.reportes.response.DashboardReportDTO;
import com.agrosync.domain.reportes.DashboardReportDomain;
import com.agrosync.domain.reportes.ReporteDashboardFiltroDomain;

import java.util.List;
import java.util.stream.Collectors;

public final class ReportesDTOMapper {

    private ReportesDTOMapper() {
    }

    public static ReporteDashboardFiltroDomain toDomain(ReporteDashboardFiltroDTO dto) {
        if (dto == null) {
            return new ReporteDashboardFiltroDomain();
        }
        return ReporteDashboardFiltroDomain.create(
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getMetodoPago(),
                dto.getClienteId(),
                dto.getProveedorId(),
                dto.getSuscripcionId()
        );
    }

    public static DashboardReportDTO toDTO(DashboardReportDomain domain) {
        if (domain == null) {
            return new DashboardReportDTO();
        }

        DashboardReportDTO dto = new DashboardReportDTO();
        dto.setFechaInicio(domain.getFechaInicio());
        dto.setFechaFin(domain.getFechaFin());

        dto.setFlujoCaja(mapCashFlow(domain.getCashFlow()));
        dto.setCuentasCobrar(mapCartera(domain.getCuentasCobrar()));
        dto.setCuentasPagar(mapCartera(domain.getCuentasPagar()));
        dto.setInventario(mapInventario(domain.getInventario()));
        dto.setVentas(mapVentas(domain.getVentas()));
        dto.setCompras(mapCompras(domain.getCompras()));
        dto.setMargen(mapMargen(domain.getMargen()));
        dto.setTopRelaciones(mapTopRelaciones(domain.getTopRelaciones()));
        dto.setRentabilidadLotes(mapRentabilidadLotes(domain.getRentabilidadLotes()));

        return dto;
    }

    private static DashboardReportDTO.CashFlowDTO mapCashFlow(DashboardReportDomain.CashFlow cashFlow) {
        DashboardReportDTO.CashFlowDTO dto = new DashboardReportDTO.CashFlowDTO();
        if (cashFlow == null) {
            return dto;
        }
        dto.setTotalVentas(cashFlow.getTotalVentas());
        dto.setTotalCompras(cashFlow.getTotalCompras());
        dto.setTotalCobros(cashFlow.getTotalCobros());
        dto.setTotalAbonos(cashFlow.getTotalAbonos());
        dto.setIngresosTotales(cashFlow.getIngresosTotales());
        dto.setEgresosTotales(cashFlow.getEgresosTotales());
        dto.setNeto(cashFlow.getNeto());
        dto.setSaldoCartera(cashFlow.getSaldoCartera());
        dto.setSaldoCuentasPorPagar(cashFlow.getSaldoCuentasPorPagar());
        dto.setProyeccionCaja(cashFlow.getProyeccionCaja());

        List<DashboardReportDTO.MetodoPagoDetalleDTO> metodos = cashFlow.getMetodosPago()
                .stream()
                .map(metodo -> {
                    DashboardReportDTO.MetodoPagoDetalleDTO metodoDTO = new DashboardReportDTO.MetodoPagoDetalleDTO();
                    metodoDTO.setMetodo(metodo.getMetodo());
                    metodoDTO.setIngresos(metodo.getIngresos());
                    metodoDTO.setEgresos(metodo.getEgresos());
                    return metodoDTO;
                }).collect(Collectors.toList());
        dto.setMetodosPago(metodos);

        List<DashboardReportDTO.TendenciaDTO> tendencia = cashFlow.getTendenciaMensual()
                .stream()
                .map(item -> {
                    DashboardReportDTO.TendenciaDTO tendenciaDTO = new DashboardReportDTO.TendenciaDTO();
                    tendenciaDTO.setPeriodo(item.getPeriodo());
                    tendenciaDTO.setIngresos(item.getIngresos());
                    tendenciaDTO.setEgresos(item.getEgresos());
                    tendenciaDTO.setNeto(item.getNeto());
                    return tendenciaDTO;
                }).collect(Collectors.toList());
        dto.setTendenciaMensual(tendencia);

        return dto;
    }

    private static DashboardReportDTO.CarteraDTO mapCartera(DashboardReportDomain.Cartera cartera) {
        DashboardReportDTO.CarteraDTO dto = new DashboardReportDTO.CarteraDTO();
        if (cartera == null) {
            return dto;
        }
        dto.setTotalPendiente(cartera.getTotalPendiente());
        dto.setTotalVencido(cartera.getTotalVencido());
        dto.setTasaRecuperacion(cartera.getTasaRecuperacion());
        dto.setCuentasVencidas(cartera.getCuentasVencidas());

        dto.setAntiguedad(cartera.getAntiguedad().stream().map(bucket -> {
            DashboardReportDTO.AgingBucketDTO bucketDTO = new DashboardReportDTO.AgingBucketDTO();
            bucketDTO.setRango(bucket.getRango());
            bucketDTO.setMonto(bucket.getMonto());
            bucketDTO.setCuentas(bucket.getCuentas());
            return bucketDTO;
        }).collect(Collectors.toList()));

        dto.setPrincipalesEntidades(cartera.getPrincipalesEntidades().stream().map(entidad -> {
            DashboardReportDTO.EntidadSaldoDTO entidadDTO = new DashboardReportDTO.EntidadSaldoDTO();
            entidadDTO.setId(entidad.getId());
            entidadDTO.setNombre(entidad.getNombre());
            entidadDTO.setSaldoPendiente(entidad.getSaldoPendiente());
            return entidadDTO;
        }).collect(Collectors.toList()));

        dto.setCuentas(cartera.getCuentas().stream().map(cuenta -> {
            DashboardReportDTO.CuentaItemDTO cuentaDTO = new DashboardReportDTO.CuentaItemDTO();
            cuentaDTO.setId(cuenta.getId());
            cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
            cuentaDTO.setTercero(cuenta.getTercero());
            cuentaDTO.setMontoTotal(cuenta.getMontoTotal());
            cuentaDTO.setSaldoPendiente(cuenta.getSaldoPendiente());
            cuentaDTO.setFechaVencimiento(cuenta.getFechaVencimiento());
            cuentaDTO.setFechaEmision(cuenta.getFechaEmision());
            cuentaDTO.setEstado(cuenta.getEstado());
            cuentaDTO.setDiasAtraso(cuenta.getDiasAtraso());
            return cuentaDTO;
        }).collect(Collectors.toList()));

        return dto;
    }

    private static DashboardReportDTO.InventarioDTO mapInventario(DashboardReportDomain.Inventario inventario) {
        DashboardReportDTO.InventarioDTO dto = new DashboardReportDTO.InventarioDTO();
        if (inventario == null) {
            return dto;
        }
        dto.setTotalDisponibles(inventario.getTotalDisponibles());
        dto.setTotalVendidos(inventario.getTotalVendidos());
        dto.setPesoTotal(inventario.getPesoTotal());
        dto.setValorInventario(inventario.getValorInventario());
        dto.setValorPotencialVenta(inventario.getValorPotencialVenta());
        dto.setMargenPotencial(inventario.getMargenPotencial());

        dto.setPorSexo(inventario.getPorSexo().stream().map(item -> {
            DashboardReportDTO.DistribucionDTO distribucionDTO = new DashboardReportDTO.DistribucionDTO();
            distribucionDTO.setEtiqueta(item.getEtiqueta());
            distribucionDTO.setCantidad(item.getCantidad());
            distribucionDTO.setTotal(item.getTotal());
            return distribucionDTO;
        }).collect(Collectors.toList()));

        dto.setPorLote(inventario.getPorLote().stream().map(item -> {
            DashboardReportDTO.DistribucionDTO distribucionDTO = new DashboardReportDTO.DistribucionDTO();
            distribucionDTO.setEtiqueta(item.getEtiqueta());
            distribucionDTO.setCantidad(item.getCantidad());
            distribucionDTO.setTotal(item.getTotal());
            return distribucionDTO;
        }).collect(Collectors.toList()));

        dto.setAnimalesAntiguos(inventario.getAnimalesAntiguos().stream().map(animal -> {
            DashboardReportDTO.AnimalAntiguoDTO animalDTO = new DashboardReportDTO.AnimalAntiguoDTO();
            animalDTO.setId(animal.getId());
            animalDTO.setNumeroAnimal(animal.getNumeroAnimal());
            animalDTO.setLote(animal.getLote());
            animalDTO.setDiasEnInventario(animal.getDiasEnInventario());
            animalDTO.setPeso(animal.getPeso());
            animalDTO.setFechaIngreso(animal.getFechaIngreso());
            return animalDTO;
        }).collect(Collectors.toList()));

        return dto;
    }

    private static DashboardReportDTO.VentasDTO mapVentas(DashboardReportDomain.Ventas ventas) {
        DashboardReportDTO.VentasDTO dto = new DashboardReportDTO.VentasDTO();
        if (ventas == null) {
            return dto;
        }
        dto.setTotalVentas(ventas.getTotalVentas());
        dto.setTicketPromedio(ventas.getTicketPromedio());
        dto.setAnimalesVendidos(ventas.getAnimalesVendidos());
        dto.setPrecioPromedioKilo(ventas.getPrecioPromedioKilo());
        dto.setClientesNuevos(ventas.getClientesNuevos());
        dto.setClientesRecurrentes(ventas.getClientesRecurrentes());

        dto.setTopClientes(ventas.getTopClientes().stream().map(ReportesDTOMapper::mapEntidadSaldo).collect(Collectors.toList()));
        dto.setTendencia(ventas.getTendencia().stream().map(ReportesDTOMapper::mapTendencia).collect(Collectors.toList()));
        return dto;
    }

    private static DashboardReportDTO.ComprasDTO mapCompras(DashboardReportDomain.Compras compras) {
        DashboardReportDTO.ComprasDTO dto = new DashboardReportDTO.ComprasDTO();
        if (compras == null) {
            return dto;
        }
        dto.setTotalCompras(compras.getTotalCompras());
        dto.setCompraPromedio(compras.getCompraPromedio());
        dto.setAnimalesComprados(compras.getAnimalesComprados());
        dto.setPrecioPromedioKiloCompra(compras.getPrecioPromedioKiloCompra());
        dto.setFrecuenciaMensual(compras.getFrecuenciaMensual());
        dto.setTopProveedores(compras.getTopProveedores().stream().map(ReportesDTOMapper::mapEntidadSaldo).collect(Collectors.toList()));
        dto.setTendencia(compras.getTendencia().stream().map(ReportesDTOMapper::mapTendencia).collect(Collectors.toList()));
        return dto;
    }

    private static DashboardReportDTO.MargenDTO mapMargen(DashboardReportDomain.Margen margen) {
        DashboardReportDTO.MargenDTO dto = new DashboardReportDTO.MargenDTO();
        if (margen == null) {
            return dto;
        }
        dto.setMargenBruto(margen.getMargenBruto());
        dto.setMargenNeto(margen.getMargenNeto());
        dto.setMargenPorKilo(margen.getMargenPorKilo());
        dto.setPuntoEquilibrio(margen.getPuntoEquilibrio());
        dto.setTendencia(margen.getTendencia().stream().map(ReportesDTOMapper::mapTendencia).collect(Collectors.toList()));
        return dto;
    }

    private static DashboardReportDTO.TopRelacionesDTO mapTopRelaciones(DashboardReportDomain.TopRelaciones topRelaciones) {
        DashboardReportDTO.TopRelacionesDTO dto = new DashboardReportDTO.TopRelacionesDTO();
        if (topRelaciones == null) {
            return dto;
        }
        dto.setTopClientesVolumen(topRelaciones.getTopClientesVolumen().stream().map(ReportesDTOMapper::mapEntidadSaldo).collect(Collectors.toList()));
        dto.setTopClientesFrecuencia(topRelaciones.getTopClientesFrecuencia().stream().map(ReportesDTOMapper::mapEntidadSaldo).collect(Collectors.toList()));
        dto.setTopProveedoresVolumen(topRelaciones.getTopProveedoresVolumen().stream().map(ReportesDTOMapper::mapEntidadSaldo).collect(Collectors.toList()));
        dto.setTopProveedoresFrecuencia(topRelaciones.getTopProveedoresFrecuencia().stream().map(ReportesDTOMapper::mapEntidadSaldo).collect(Collectors.toList()));
        return dto;
    }

    private static List<DashboardReportDTO.LoteRentabilidadDTO> mapRentabilidadLotes(List<DashboardReportDomain.LoteRentabilidad> lotes) {
        return defaultList(lotes).stream().map(lote -> {
            DashboardReportDTO.LoteRentabilidadDTO dto = new DashboardReportDTO.LoteRentabilidadDTO();
            dto.setContramarca(lote.getContramarca());
            dto.setNumeroLote(lote.getNumeroLote());
            dto.setFecha(lote.getFecha());
            dto.setCostoTotal(lote.getCostoTotal());
            dto.setIngresosTotales(lote.getIngresosTotales());
            dto.setGananciaNeta(lote.getGananciaNeta());
            dto.setMargen(lote.getMargen());
            dto.setRoi(lote.getRoi());
            dto.setPrecioPromedioCompraKg(lote.getPrecioPromedioCompraKg());
            dto.setPrecioPromedioVentaKg(lote.getPrecioPromedioVentaKg());
            dto.setTiempoRotacionDias(lote.getTiempoRotacionDias());
            return dto;
        }).collect(Collectors.toList());
    }

    private static DashboardReportDTO.EntidadSaldoDTO mapEntidadSaldo(DashboardReportDomain.EntidadSaldo entidad) {
        DashboardReportDTO.EntidadSaldoDTO dto = new DashboardReportDTO.EntidadSaldoDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setSaldoPendiente(entidad.getSaldoPendiente());
        return dto;
    }

    private static DashboardReportDTO.TendenciaDTO mapTendencia(DashboardReportDomain.Tendencia tendencia) {
        DashboardReportDTO.TendenciaDTO dto = new DashboardReportDTO.TendenciaDTO();
        dto.setPeriodo(tendencia.getPeriodo());
        dto.setIngresos(tendencia.getIngresos());
        dto.setEgresos(tendencia.getEgresos());
        dto.setNeto(tendencia.getNeto());
        return dto;
    }

    private static <T> List<T> defaultList(List<T> value) {
        return value == null ? new java.util.ArrayList<>() : value;
    }
}

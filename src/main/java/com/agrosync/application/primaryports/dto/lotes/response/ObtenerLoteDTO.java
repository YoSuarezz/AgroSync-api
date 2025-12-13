package com.agrosync.application.primaryports.dto.lotes.response;

import com.agrosync.application.primaryports.dto.animales.response.ObtenerAnimalDTO;
import com.agrosync.crosscutting.helpers.NumericHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObtenerLoteDTO {

    private UUID id;
    private String numeroLote;
    private String contramarca;
    private LocalDate fecha;
    private BigDecimal pesoTotal;
    private Integer cantidadAnimales;
    private UUID compraId;
    private String numeroCompra;
    private List<ObtenerAnimalDTO> animales;

    public ObtenerLoteDTO() {
        setId(UUIDHelper.getDefault());
        setNumeroLote(TextHelper.EMPTY);
        setContramarca(TextHelper.EMPTY);
        this.fecha = null;
        setPesoTotal(BigDecimal.ZERO);
        setCantidadAnimales(0);
        setCompraId(null);
        setNumeroCompra(TextHelper.EMPTY);
        setAnimales(new ArrayList<>());
    }

    public ObtenerLoteDTO(UUID id, String numeroLote, String contramarca, LocalDate fecha, BigDecimal pesoTotal,
                          Integer cantidadAnimales, UUID compraId, String numeroCompra, List<ObtenerAnimalDTO> animales) {
        setId(id);
        setNumeroLote(numeroLote);
        setContramarca(contramarca);
        setFecha(fecha);
        setPesoTotal(pesoTotal);
        setCantidadAnimales(cantidadAnimales);
        setCompraId(compraId);
        setNumeroCompra(numeroCompra);
        setAnimales(animales);
    }

    public static ObtenerLoteDTO create(UUID id, String numeroLote, String contramarca, LocalDate fecha, BigDecimal pesoTotal,
                                        Integer cantidadAnimales, UUID compraId, String numeroCompra, List<ObtenerAnimalDTO> animales) {
        return new ObtenerLoteDTO(id, numeroLote, contramarca, fecha, pesoTotal, cantidadAnimales, compraId, numeroCompra, animales);
    }

    public static ObtenerLoteDTO create(UUID id, String numeroLote, LocalDate fecha, BigDecimal pesoTotal) {
        return new ObtenerLoteDTO(id, numeroLote, null, fecha, pesoTotal, 0, null, null, new ArrayList<>());
    }

    public static ObtenerLoteDTO create() {
        return new ObtenerLoteDTO();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = TextHelper.applyTrim(numeroLote);
    }

    public String getContramarca() {
        return contramarca;
    }

    public void setContramarca(String contramarca) {
        this.contramarca = TextHelper.applyTrim(contramarca);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(BigDecimal pesoTotal) {
        this.pesoTotal = NumericHelper.getDefault(pesoTotal, BigDecimal.ZERO);
    }

    public Integer getCantidadAnimales() {
        return cantidadAnimales;
    }

    public void setCantidadAnimales(Integer cantidadAnimales) {
        this.cantidadAnimales = cantidadAnimales != null ? cantidadAnimales : 0;
    }

    public UUID getCompraId() {
        return compraId;
    }

    public void setCompraId(UUID compraId) {
        this.compraId = compraId;
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = TextHelper.applyTrim(numeroCompra);
    }

    public List<ObtenerAnimalDTO> getAnimales() {
        return animales;
    }

    public void setAnimales(List<ObtenerAnimalDTO> animales) {
        this.animales = animales != null ? animales : new ArrayList<>();
    }
}

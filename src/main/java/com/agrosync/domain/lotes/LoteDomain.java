package com.agrosync.domain.lotes;

import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.animales.AnimalDomain;
import com.agrosync.domain.compras.CompraDomain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LoteDomain extends BaseDomain {

    private CompraDomain compra;
    private String numeroLote;
    private String contramarca;
    private LocalDate fecha;
    private BigDecimal pesoTotal;
    private List<AnimalDomain> animales;
    private UUID suscripcionId;

    public LoteDomain() {
        super();
    }

    public LoteDomain(UUID id, CompraDomain compra, String numeroLote, String contramarca, LocalDate fecha, BigDecimal pesoTotal, List<AnimalDomain> animales, UUID suscripcionId) {
        super(id);
        setCompra(compra);
        setNumeroLote(numeroLote);
        setContramarca(contramarca);
        setFecha(fecha);
        setPesoTotal(pesoTotal);
        setAnimales(animales);
        setSuscripcionId(suscripcionId);
    }

    public CompraDomain getCompra() {
        return compra;
    }

    public void setCompra(CompraDomain compra) {
        this.compra = compra;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getContramarca() {
        return contramarca;
    }

    public void setContramarca(String contramarca) {
        this.contramarca = contramarca;
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
        this.pesoTotal = pesoTotal;
    }

    public List<AnimalDomain> getAnimales() {
        return animales;
    }

    public void setAnimales(List<AnimalDomain> animales) {
        this.animales = animales;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}

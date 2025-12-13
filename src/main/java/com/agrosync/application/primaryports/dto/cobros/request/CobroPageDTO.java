package com.agrosync.application.primaryports.dto.cobros.request;

import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.enums.cuentas.MetodoPagoEnum;
import com.agrosync.crosscutting.helpers.ObjectHelper;

import java.util.UUID;

public class CobroPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private ObtenerCobroDTO cobro;
    private MetodoPagoEnum metodoPago;
    private UUID cuentaCobrarId;
    private UUID suscripcionId;

    public CobroPageDTO() {
    }

    public CobroPageDTO(int page, int size, String sortBy, String sortDirection, ObtenerCobroDTO cobro,
            MetodoPagoEnum metodoPago, UUID cuentaCobrarId, UUID suscripcionId) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setCobro(cobro);
        setMetodoPago(metodoPago);
        setCuentaCobrarId(cuentaCobrarId);
        setSuscripcionId(suscripcionId);
    }

    public static CobroPageDTO create(int page, int size, String sortBy, String sortDirection, ObtenerCobroDTO cobro,
            MetodoPagoEnum metodoPago, UUID cuentaCobrarId, UUID suscripcionId) {
        return new CobroPageDTO(page, size, sortBy, sortDirection, cobro, metodoPago, cuentaCobrarId, suscripcionId);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = Math.max(size, 1);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public ObtenerCobroDTO getCobro() {
        return cobro;
    }

    public void setCobro(ObtenerCobroDTO cobro) {
        this.cobro = ObjectHelper.getDefault(cobro, ObtenerCobroDTO.create());
    }

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public UUID getCuentaCobrarId() {
        return cuentaCobrarId;
    }

    public void setCuentaCobrarId(UUID cuentaCobrarId) {
        this.cuentaCobrarId = cuentaCobrarId;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}

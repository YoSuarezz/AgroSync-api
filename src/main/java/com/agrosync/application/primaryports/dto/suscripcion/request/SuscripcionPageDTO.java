package com.agrosync.application.primaryports.dto.suscripcion.request;

public class SuscripcionPageDTO {

    private int page;
    private int size;
    private String sortBy;
    private String sortDirection;
    private String nombreEmpresa;
    private String nit;

    public SuscripcionPageDTO(int page, int size, String sortBy, String sortDirection, String nombreEmpresa, String nit) {
        setPage(page);
        setSize(size);
        setSortBy(sortBy);
        setSortDirection(sortDirection);
        setNombreEmpresa(nombreEmpresa);
        setNit(nit);
    }

    public SuscripcionPageDTO() {
    }

    public static SuscripcionPageDTO create(int page, int size, String sortBy, String sortDirection, String nombreEmpresa, String nit) {
        return new SuscripcionPageDTO(page, size, sortBy, sortDirection, nombreEmpresa, nit);
    }

    public static SuscripcionPageDTO create() {
        return new SuscripcionPageDTO();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
}

package com.agrosync.application.secondaryports.entity.compras;

import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.lotes.LoteEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "compra")
public class CompraEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero_compra")
    private String numeroCompra; // CO - contramarcaLote - 4 digitos aleatorios

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor")
    private UsuarioEntity proveedor;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    @Column(name = "precio_total_compra")
    private BigDecimal precioTotalCompra;

    @OneToOne(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private LoteEntity lote;

    @OneToOne(mappedBy = "compra", cascade = CascadeType.ALL)
    private CuentaPagarEntity cuentaPagar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    public CompraEntity() {
        setId(UUIDHelper.getDefault());
        setNumeroCompra(TextHelper.EMPTY);
        setProveedor(UsuarioEntity.create());
        setFechaCompra(LocalDate.now());
        setPrecioTotalCompra(BigDecimal.ZERO);
        setLote(LoteEntity.create());
        setCuentaPagar(CuentaPagarEntity.create());
        setSuscripcion(SuscripcionEntity.create());
    }

    public CompraEntity(UUID id, String numeroCompra, UsuarioEntity proveedor, LocalDate fechaCompra, BigDecimal precioTotalCompra, LoteEntity lote, CuentaPagarEntity cuentaPagar, SuscripcionEntity suscripcion) {
        setId(id);
        setNumeroCompra(numeroCompra);
        setProveedor(proveedor);
        setFechaCompra(fechaCompra);
        setPrecioTotalCompra(precioTotalCompra);
        setLote(lote);
        setCuentaPagar(cuentaPagar);
        setSuscripcion(suscripcion);
    }

    public static CompraEntity create(UUID id, String numeroCompra, UsuarioEntity proveedor, LocalDate fechaCompra, BigDecimal precioTotalCompra, LoteEntity lote, CuentaPagarEntity cuentaPagar, SuscripcionEntity suscripcion) {
        return new CompraEntity(id, numeroCompra, proveedor, fechaCompra, precioTotalCompra, lote, cuentaPagar, suscripcion);
    }

    public static CompraEntity create(UUID id) {
        return new CompraEntity(id, TextHelper.EMPTY, UsuarioEntity.create(), LocalDate.now(), BigDecimal.ZERO, LoteEntity.create(), CuentaPagarEntity.create(), SuscripcionEntity.create());
    }

    public static CompraEntity create() {
        return new CompraEntity(UUIDHelper.getDefault(), TextHelper.EMPTY, UsuarioEntity.create(), LocalDate.now(), BigDecimal.ZERO, LoteEntity.create(), CuentaPagarEntity.create(), SuscripcionEntity.create());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(String numeroCompra) {
        this.numeroCompra = TextHelper.applyTrim(numeroCompra);
    }

    public UsuarioEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(UsuarioEntity proveedor) {
        this.proveedor = ObjectHelper.getDefault(proveedor, UsuarioEntity.create());
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public BigDecimal getPrecioTotalCompra() {
        return precioTotalCompra;
    }

    public void setPrecioTotalCompra(BigDecimal precioTotalCompra) {
        this.precioTotalCompra = precioTotalCompra;
    }

    public LoteEntity getLote() {
        return lote;
    }

    public void setLote(LoteEntity lote) {
        this.lote = ObjectHelper.getDefault(lote, LoteEntity.create());
    }

    public CuentaPagarEntity getCuentaPagar() {
        return cuentaPagar;
    }

    public void setCuentaPagar(CuentaPagarEntity cuentaPagar) {
        this.cuentaPagar = ObjectHelper.getDefault(cuentaPagar, CuentaPagarEntity.create());
    }

    public SuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(SuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }
}

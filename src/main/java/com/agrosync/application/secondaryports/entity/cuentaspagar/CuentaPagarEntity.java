package com.agrosync.application.secondaryports.entity.cuentaspagar;

import com.agrosync.application.primaryports.enums.EstadoCuenta;
import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.abonos.AbonoEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cuenta_pagar")
public class CuentaPagarEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero_cuenta")
    private String numeroCuenta; // CXP - numeroCompra

    @OneToOne
    @JoinColumn(name = "id_compra")
    private CompraEntity compra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor")
    private UsuarioEntity proveedor;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Column(name = "saldo_pendiente")
    private BigDecimal saldoPendiente;

    @OneToMany(mappedBy = "cuentaPagar", cascade = CascadeType.ALL)
    private List<AbonoEntity> abonos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCuenta estado;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    public CuentaPagarEntity() {
        setId(UUIDHelper.getDefault());
        setNumeroCuenta(TextHelper.EMPTY);
        setCompra(CompraEntity.create());
        setProveedor(UsuarioEntity.create());
        setMontoTotal(BigDecimal.ZERO);
        setSaldoPendiente(BigDecimal.ZERO);
        setAbonos(new ArrayList<>());
        setEstado(EstadoCuenta.ANULADA);
        setFechaEmision(LocalDate.now());
        setFechaVencimiento(LocalDate.now());
    }

    public CuentaPagarEntity(UUID id, String numeroCuenta, CompraEntity compra, UsuarioEntity proveedor, BigDecimal montoTotal, BigDecimal saldoPendiente, List<AbonoEntity> abonos, EstadoCuenta estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        setId(id);
        setNumeroCuenta(numeroCuenta);
        setCompra(compra);
        setProveedor(proveedor);
        setMontoTotal(montoTotal);
        setSaldoPendiente(saldoPendiente);
        setAbonos(abonos);
        setEstado(estado);
        setFechaEmision(fechaEmision);
        setFechaVencimiento(fechaVencimiento);
    }

    public static CuentaPagarEntity create(UUID id, String numeroCuenta, CompraEntity compra, UsuarioEntity proveedor, BigDecimal montoTotal, BigDecimal saldoPendiente, List<AbonoEntity> abonos, EstadoCuenta estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        return new CuentaPagarEntity(id, numeroCuenta, compra, proveedor, montoTotal, saldoPendiente, abonos, estado, fechaEmision, fechaVencimiento);
    }

    public static CuentaPagarEntity create(UUID id) {
        return new CuentaPagarEntity(id, TextHelper.EMPTY, CompraEntity.create(), UsuarioEntity.create(), BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>(), EstadoCuenta.ANULADA, LocalDate.now(), LocalDate.now());
    }

    public static CuentaPagarEntity create() {
        return new CuentaPagarEntity(UUIDHelper.getDefault(), TextHelper.EMPTY, CompraEntity.create(), UsuarioEntity.create(), BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>(), EstadoCuenta.ANULADA, LocalDate.now(), LocalDate.now());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = TextHelper.applyTrim(numeroCuenta);
    }

    public CompraEntity getCompra() {
        return compra;
    }

    public void setCompra(CompraEntity compra) {
        this.compra = compra;
    }

    public UsuarioEntity getProveedor() {
        return proveedor;
    }

    public void setProveedor(UsuarioEntity proveedor) {
        this.proveedor = proveedor;
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

    public List<AbonoEntity> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<AbonoEntity> abonos) {
        this.abonos = abonos;
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        this.estado = estado;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}

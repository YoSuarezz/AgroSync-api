package com.agrosync.application.secondaryports.entity.cuentascobrar;

import com.agrosync.application.primaryports.enums.cuentas.EstadoCuenta;
import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.cobros.CobroEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cuenta_cobrar")
public class CuentaCobrarEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero_cuenta")
    private String numeroCuenta; // CXC - numeroVenta

    @OneToOne
    @JoinColumn(name = "id_venta")
    private VentaEntity venta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    private UsuarioEntity cliente;

    @Column(name = "monto_total")
    private BigDecimal montoTotal;

    @Column(name = "saldo_pendiente")
    private BigDecimal saldoPendiente;

    @OneToMany(mappedBy = "cuentaCobrar")
    private List<CobroEntity> cobros;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoCuenta estado;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    public CuentaCobrarEntity() {
        setId(UUIDHelper.getDefault());
        setNumeroCuenta(TextHelper.EMPTY);
        setVenta(null);
        setCliente(null);
        setMontoTotal(BigDecimal.ZERO);
        setSaldoPendiente(BigDecimal.ZERO);
        setCobros(new ArrayList<>());
        setEstado(EstadoCuenta.ANULADA);
        setFechaEmision(LocalDate.now());
        setFechaVencimiento(LocalDate.now());
    }

    public CuentaCobrarEntity(UUID id, String numeroCuenta, VentaEntity venta, UsuarioEntity cliente, BigDecimal montoTotal, BigDecimal saldoPendiente, List<CobroEntity> cobros, EstadoCuenta estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        setId(id);
        setNumeroCuenta(numeroCuenta);
        setVenta(venta);
        setCliente(cliente);
        setMontoTotal(montoTotal);
        setSaldoPendiente(saldoPendiente);
        setCobros(cobros);
        setEstado(estado);
        setFechaEmision(fechaEmision);
        setFechaVencimiento(fechaVencimiento);
    }

    public static CuentaCobrarEntity create(UUID id, String numeroCuenta, VentaEntity venta, UsuarioEntity cliente, BigDecimal montoTotal, BigDecimal saldoPendiente, List<CobroEntity> cobros, EstadoCuenta estado, LocalDate fechaEmision, LocalDate fechaVencimiento) {
        return new CuentaCobrarEntity(id, numeroCuenta, venta, cliente, montoTotal, saldoPendiente, cobros, estado, fechaEmision, fechaVencimiento);
    }

    public static CuentaCobrarEntity create(UUID id) {
        return new CuentaCobrarEntity(id, TextHelper.EMPTY, null, null, BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>(), EstadoCuenta.ANULADA, LocalDate.now(), LocalDate.now());
    }

    public static CuentaCobrarEntity create() {
        return new CuentaCobrarEntity(UUIDHelper.getDefault(), TextHelper.EMPTY, null, null, BigDecimal.ZERO, BigDecimal.ZERO, new ArrayList<>(), EstadoCuenta.ANULADA, LocalDate.now(), LocalDate.now());
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

    public VentaEntity getVenta() {
        return venta;
    }

    public void setVenta(VentaEntity venta) {
        this.venta = venta;
    }

    public UsuarioEntity getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioEntity cliente) {
        this.cliente = cliente;
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

    public List<CobroEntity> getCobros() {
        return cobros;
    }

    public void setCobros(List<CobroEntity> cobros) {
        this.cobros = cobros;
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

package com.agrosync.application.secondaryports.entity.cobros;

import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cobro")
public class CobroEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_cobrar")
    private CuentaCobrarEntity cuentaCobrar;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "fecha_cobro")
    private LocalDateTime fechaCobro;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPagoEnum metodoPago;

    @Column(name = "concepto")
    private String concepto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    public CobroEntity() {
        setId(UUIDHelper.getDefault());
        setCuentaCobrar(CuentaCobrarEntity.create());
        setMonto(BigDecimal.ZERO);
        setFechaCobro(LocalDateTime.now());
        setMetodoPago(MetodoPagoEnum.OTRO);
        setConcepto(TextHelper.EMPTY);
        setSuscripcion(SuscripcionEntity.create());
    }

    public CobroEntity(UUID id, CuentaCobrarEntity cuentaCobrar, BigDecimal monto, LocalDateTime fechaCobro, MetodoPagoEnum metodoPago, String concepto, SuscripcionEntity suscripcion) {
        setId(id);
        setCuentaCobrar(cuentaCobrar);
        setMonto(monto);
        setFechaCobro(fechaCobro);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
        setSuscripcion(suscripcion);
    }

    public static CobroEntity create(UUID id, CuentaCobrarEntity cuentaCobrar, BigDecimal monto, LocalDateTime fechaCobro, MetodoPagoEnum metodoPago, String concepto, SuscripcionEntity suscripcion) {
        return new CobroEntity(id, cuentaCobrar, monto, fechaCobro, metodoPago, concepto, suscripcion);
    }

    public static CobroEntity create(UUID id) {
        return new CobroEntity(id, CuentaCobrarEntity.create(), BigDecimal.ZERO, LocalDateTime.now(), MetodoPagoEnum.OTRO, TextHelper.EMPTY, SuscripcionEntity.create());
    }

    public static CobroEntity create() {
        return new CobroEntity(UUIDHelper.getDefault(), CuentaCobrarEntity.create(), BigDecimal.ZERO, LocalDateTime.now(), MetodoPagoEnum.OTRO, TextHelper.EMPTY, SuscripcionEntity.create());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public CuentaCobrarEntity getCuentaCobrar() {
        return cuentaCobrar;
    }

    public void setCuentaCobrar(CuentaCobrarEntity cuentaCobrar) {
        this.cuentaCobrar = ObjectHelper.getDefault(cuentaCobrar, CuentaCobrarEntity.create());
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(LocalDateTime fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public MetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = TextHelper.applyTrim(concepto);
    }

    public SuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(SuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }
}

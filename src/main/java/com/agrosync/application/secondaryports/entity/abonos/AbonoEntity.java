package com.agrosync.application.secondaryports.entity.abonos;

import com.agrosync.domain.enums.cuentas.MetodoPagoEnum;
import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "abono")
public class AbonoEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta_pagar")
    private CuentaPagarEntity cuentaPagar;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPagoEnum metodoPago;

    @Column(name = "concepto")
    private String concepto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    public AbonoEntity() {
        setId(UUIDHelper.getDefault());
        setCuentaPagar(CuentaPagarEntity.create());
        setMonto(BigDecimal.ZERO);
        setFechaPago(LocalDateTime.now());
        setMetodoPago(MetodoPagoEnum.OTRO);
        setConcepto("");
        setSuscripcion(SuscripcionEntity.create());
    }

    public AbonoEntity(UUID id, CuentaPagarEntity cuentaPagar, BigDecimal monto, LocalDateTime fechaPago, MetodoPagoEnum metodoPago, String concepto, SuscripcionEntity suscripcion) {
        setId(id);
        setCuentaPagar(cuentaPagar);
        setMonto(monto);
        setFechaPago(fechaPago);
        setMetodoPago(metodoPago);
        setConcepto(concepto);
        setSuscripcion(suscripcion);
    }

    public static AbonoEntity create(UUID id, CuentaPagarEntity cuentaPagar, BigDecimal monto, LocalDateTime fechaPago, MetodoPagoEnum metodoPago, String concepto, SuscripcionEntity suscripcion) {
        return new AbonoEntity(id, cuentaPagar, monto, fechaPago, metodoPago, concepto, suscripcion);
    }

    public static AbonoEntity create(UUID id) {
        return new AbonoEntity(id, CuentaPagarEntity.create(), BigDecimal.ZERO, LocalDateTime.now(), MetodoPagoEnum.OTRO, "", SuscripcionEntity.create());
    }

    public static AbonoEntity create() {
        return new AbonoEntity(UUIDHelper.getDefault(), CuentaPagarEntity.create(), BigDecimal.ZERO, LocalDateTime.now(), MetodoPagoEnum.OTRO, "", SuscripcionEntity.create());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public CuentaPagarEntity getCuentaPagar() {
        return cuentaPagar;
    }

    public void setCuentaPagar(CuentaPagarEntity cuentaPagar) {
        this.cuentaPagar = cuentaPagar;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
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
        this.concepto = concepto;
    }

    public SuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(SuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }
}

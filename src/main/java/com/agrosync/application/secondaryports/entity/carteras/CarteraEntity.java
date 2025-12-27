package com.agrosync.application.secondaryports.entity.carteras;

import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.suscripcion.SuscripcionEntity;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cartera", schema = "agryxo")
public class CarteraEntity extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @Column(name = "saldo_actual")
    private BigDecimal saldoActual;

    @Column(name = "total_cuentas_pagar")
    private BigDecimal totalCuentasPagar;

    @Column(name = "total_cuentas_cobrar")
    private BigDecimal totalCuentasCobrar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    public CarteraEntity() {
        setId(UUIDHelper.getDefault());
        setUsuario(null);
        setSaldoActual(BigDecimal.ZERO);
        setTotalCuentasPagar(BigDecimal.ZERO);
        setTotalCuentasCobrar(BigDecimal.ZERO);
        setSuscripcion(SuscripcionEntity.create());
    }

    public CarteraEntity(UUID id, UsuarioEntity usuario, BigDecimal saldoActual, BigDecimal totalCuentasPagar, BigDecimal totalCuentasCobrar, SuscripcionEntity suscripcion) {
        setId(id);
        setUsuario(usuario);
        setSaldoActual(saldoActual);
        setTotalCuentasPagar(totalCuentasPagar);
        setTotalCuentasCobrar(totalCuentasCobrar);
        setSuscripcion(suscripcion);
    }

    public static CarteraEntity create(UUID id, UsuarioEntity usuario, BigDecimal saldoActual, BigDecimal totalCuentasPagar, BigDecimal totalCuentasCobrar, SuscripcionEntity suscripcion) {
        return new CarteraEntity(id, usuario, saldoActual, totalCuentasPagar, totalCuentasCobrar, suscripcion);
    }

    public static CarteraEntity create(UUID id) {
        return new CarteraEntity(id, null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, SuscripcionEntity.create());
    }

    public static CarteraEntity create() {
        return new CarteraEntity(UUIDHelper.getDefault(), null, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, SuscripcionEntity.create());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(BigDecimal saldoActual) {
        this.saldoActual = saldoActual;
    }

    public BigDecimal getTotalCuentasPagar() {
        return totalCuentasPagar;
    }

    public void setTotalCuentasPagar(BigDecimal totalCuentasPagar) {
        this.totalCuentasPagar = totalCuentasPagar;
    }

    public BigDecimal getTotalCuentasCobrar() {
        return totalCuentasCobrar;
    }

    public void setTotalCuentasCobrar(BigDecimal totalCuentasCobrar) {
        this.totalCuentasCobrar = totalCuentasCobrar;
    }

    public SuscripcionEntity getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(SuscripcionEntity suscripcion) {
        this.suscripcion = suscripcion;
    }
}

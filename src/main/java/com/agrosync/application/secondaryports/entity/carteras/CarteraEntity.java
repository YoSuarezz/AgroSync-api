package com.agrosync.application.secondaryports.entity.carteras;

import com.agrosync.application.secondaryports.entity.Auditoria;
import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cartera")
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

    public CarteraEntity() {
        setId(UUIDHelper.getDefault());
        setUsuario(UsuarioEntity.create());
        setSaldoActual(BigDecimal.ZERO);
        setTotalCuentasPagar(BigDecimal.ZERO);
        setTotalCuentasCobrar(BigDecimal.ZERO);
    }

    public CarteraEntity(UUID id, UsuarioEntity usuario, BigDecimal saldoActual, BigDecimal totalCuentasPagar, BigDecimal totalCuentasCobrar) {
        setId(id);
        setUsuario(usuario);
        setSaldoActual(saldoActual);
        setTotalCuentasPagar(totalCuentasPagar);
        setTotalCuentasCobrar(totalCuentasCobrar);
    }

    public static CarteraEntity create(UUID id, UsuarioEntity usuario, BigDecimal saldoActual, BigDecimal totalCuentasPagar, BigDecimal totalCuentasCobrar) {
        return new CarteraEntity(id, usuario, saldoActual, totalCuentasPagar, totalCuentasCobrar);
    }

    public static CarteraEntity create(UUID id) {
        return new CarteraEntity(id, UsuarioEntity.create(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public static CarteraEntity create() {
        return new CarteraEntity(UUIDHelper.getDefault(), UsuarioEntity.create(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
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
        this.usuario = ObjectHelper.getDefault(usuario, UsuarioEntity.create());
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
}

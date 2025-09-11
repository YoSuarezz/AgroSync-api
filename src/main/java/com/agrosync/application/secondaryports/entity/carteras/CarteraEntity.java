package com.agrosync.application.secondaryports.entity.carteras;

import com.agrosync.application.secondaryports.entity.usuarios.UsuarioEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cartera")
public class CarteraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartera")
    private Long id;

    @JoinColumn(name = "id_usuario")
    @OneToOne(cascade = CascadeType.ALL)
    private UsuarioEntity usuario;

    @Column(name = "saldo")
    private BigDecimal saldo;

    public CarteraEntity() {
        setId(id);
        setUsuario(usuario);
        setSaldo(BigDecimal.ZERO);
    }

    public CarteraEntity(Long id, UsuarioEntity usuario, BigDecimal saldo) {
        setId(id);
        setUsuario(usuario);
        setSaldo(saldo);
    }

    public static CarteraEntity create(Long id, UsuarioEntity usuario, BigDecimal saldo) {
        return new CarteraEntity(id, usuario, saldo);
    }

    public static CarteraEntity create(Long id, UsuarioEntity usuario) {
        return new CarteraEntity(id, usuario, BigDecimal.ZERO);
    }

//    public static CarteraEntity create(Long id) {
//        return new CarteraEntity(id, ObjectHelper.getDefault(UsuarioEntity.create()), BigDecimal.ZERO);
//    }

    public static CarteraEntity create() {
        return new CarteraEntity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}

package com.agrosync.application.secondaryports.entity.usuarios;

import com.agrosync.application.primaryports.enums.EstadoUsuarioEnum;
import com.agrosync.application.secondaryports.entity.carteras.CarteraEntity;
import com.agrosync.application.secondaryports.entity.compras.CompraEntity;
import com.agrosync.application.secondaryports.entity.cuentascobrar.CuentaCobrarEntity;
import com.agrosync.application.secondaryports.entity.cuentaspagar.CuentaPagarEntity;
import com.agrosync.application.secondaryports.entity.ventas.VentaEntity;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario")
    private TipoUsuarioEntity tipoUsuario;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private CarteraEntity cartera;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<CompraEntity> compras;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<CuentaPagarEntity> cuentasPagar;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<VentaEntity> ventas;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<CuentaCobrarEntity> cuentasCobrar;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoUsuarioEnum estado;

    public UsuarioEntity() {
        setId(UUIDHelper.getDefault());
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipoUsuario(TipoUsuarioEntity.create());
        setCartera(CarteraEntity.create());
        setCompras(new ArrayList<>());
        setCuentasPagar(new ArrayList<>());
        setVentas(new ArrayList<>());
        setCuentasCobrar(new ArrayList<>());
        setEstado(EstadoUsuarioEnum.ACTIVO);
    }

    public UsuarioEntity(UUID id, String nombre, String telefono, TipoUsuarioEntity tipoUsuario, CarteraEntity cartera, List<CompraEntity> compras, List<CuentaPagarEntity> cuentasPagar, List<VentaEntity> ventas, List<CuentaCobrarEntity> cuentasCobrar, EstadoUsuarioEnum estado) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
        setCartera(cartera);
        setCompras(compras);
        setCuentasPagar(cuentasPagar);
        setVentas(ventas);
        setCuentasCobrar(cuentasCobrar);
        setEstado(estado);
    }

    public static UsuarioEntity create(UUID id, String nombre, String telefono, TipoUsuarioEntity tipoUsuario, CarteraEntity cartera, List<CompraEntity> compras, List<CuentaPagarEntity> cuentasPagar, List<VentaEntity> ventas, List<CuentaCobrarEntity> cuentasCobrar, EstadoUsuarioEnum estado) {
        return new UsuarioEntity(id, nombre, telefono, tipoUsuario, cartera, compras, cuentasPagar, ventas, cuentasCobrar, estado);
    }

    public static UsuarioEntity create(UUID id) {
        return new UsuarioEntity(id, TextHelper.EMPTY, TextHelper.EMPTY, TipoUsuarioEntity.create(), CarteraEntity.create(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), EstadoUsuarioEnum.ACTIVO);
    }

    public static UsuarioEntity create() {
        return new UsuarioEntity(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, TipoUsuarioEntity.create(), CarteraEntity.create(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), EstadoUsuarioEnum.ACTIVO);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = UUIDHelper.getDefault(id, UUIDHelper.getDefault());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = TextHelper.applyTrim(nombre);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = TextHelper.applyTrim(telefono);
    }

    public TipoUsuarioEntity getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEntity tipoUsuario) {
        this.tipoUsuario = ObjectHelper.getDefault(tipoUsuario, TipoUsuarioEntity.create());
    }

    public CarteraEntity getCartera() {
        return cartera;
    }

    public void setCartera(CarteraEntity cartera) {
        this.cartera = ObjectHelper.getDefault(cartera, CarteraEntity.create());
    }

    public List<CompraEntity> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraEntity> compras) {
        this.compras = compras;
    }

    public List<CuentaPagarEntity> getCuentasPagar() {
        return cuentasPagar;
    }

    public void setCuentasPagar(List<CuentaPagarEntity> cuentasPagar) {
        this.cuentasPagar = cuentasPagar;
    }

    public List<VentaEntity> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaEntity> ventas) {
        this.ventas = ventas;
    }

    public List<CuentaCobrarEntity> getCuentasCobrar() {
        return cuentasCobrar;
    }

    public void setCuentasCobrar(List<CuentaCobrarEntity> cuentasCobrar) {
        this.cuentasCobrar = cuentasCobrar;
    }

    public EstadoUsuarioEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuarioEnum estado) {
        this.estado = estado;
    }
}

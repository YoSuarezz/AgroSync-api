package com.agrosync.domain.usuarios;

import com.agrosync.domain.enums.usuarios.EstadoUsuarioEnum;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.ventas.VentaDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDomain extends BaseDomain {

    private String nombre;
    private String telefono;
    private TipoUsuarioEnum tipoUsuario;
    private CarteraDomain cartera;
    private List<CompraDomain> compras;
    private List<CuentaPagarDomain> cuentasPagar;
    private List<VentaDomain> ventas;
    private List<CuentaCobrarDomain> cuentasCobrar;
    private EstadoUsuarioEnum estado;
    private UUID suscripcionId;

    public UsuarioDomain() {
        super();
    }

    public UsuarioDomain(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario, CarteraDomain cartera, List<CompraDomain> compras, List<CuentaPagarDomain> cuentasPagar, List<VentaDomain> ventas, List<CuentaCobrarDomain> cuentasCobrar, EstadoUsuarioEnum estado, UUID suscripcionId) {
        super(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
        setCartera(cartera);
        setCompras(compras);
        setCuentasPagar(cuentasPagar);
        setVentas(ventas);
        setCuentasCobrar(cuentasCobrar);
        setEstado(estado);
        setSuscripcionId(suscripcionId);
    }

    public static UsuarioDomain create(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario, CarteraDomain cartera, List<CompraDomain> compras, List<CuentaPagarDomain> cuentasPagar, List<VentaDomain> ventas, List<CuentaCobrarDomain> cuentasCobrar, EstadoUsuarioEnum estado, UUID suscripcionId) {
        return new UsuarioDomain(id, nombre, telefono, tipoUsuario, cartera, compras, cuentasPagar, ventas, cuentasCobrar, estado, suscripcionId);
    }

    public static UsuarioDomain create(UUID id) {
        return new UsuarioDomain(id, TextHelper.EMPTY, TextHelper.EMPTY, TipoUsuarioEnum.CLIENTE, new CarteraDomain(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), EstadoUsuarioEnum.ACTIVO, UUIDHelper.getDefault());
    }

    public static UsuarioDomain create() {
        return new UsuarioDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, TipoUsuarioEnum.CLIENTE, new CarteraDomain(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), EstadoUsuarioEnum.ACTIVO, UUIDHelper.getDefault());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public CarteraDomain getCartera() {
        return cartera;
    }

    public void setCartera(CarteraDomain cartera) {
        this.cartera = cartera;
    }

    public List<CompraDomain> getCompras() {
        return compras;
    }

    public void setCompras(List<CompraDomain> compras) {
        this.compras = compras;
    }

    public List<CuentaPagarDomain> getCuentasPagar() {
        return cuentasPagar;
    }

    public void setCuentasPagar(List<CuentaPagarDomain> cuentasPagar) {
        this.cuentasPagar = cuentasPagar;
    }

    public List<VentaDomain> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaDomain> ventas) {
        this.ventas = ventas;
    }

    public List<CuentaCobrarDomain> getCuentasCobrar() {
        return cuentasCobrar;
    }

    public void setCuentasCobrar(List<CuentaCobrarDomain> cuentasCobrar) {
        this.cuentasCobrar = cuentasCobrar;
    }

    public EstadoUsuarioEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuarioEnum estado) {
        this.estado = estado;
    }

    public UUID getSuscripcionId() {
        return suscripcionId;
    }

    public void setSuscripcionId(UUID suscripcionId) {
        this.suscripcionId = suscripcionId;
    }
}

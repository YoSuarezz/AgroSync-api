package com.agrosync.domain.usuarios;

import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.BaseDomain;
import com.agrosync.domain.abonos.AbonoDomain;
import com.agrosync.domain.carteras.CarteraDomain;
import com.agrosync.domain.cobros.CobroDomain;
import com.agrosync.domain.compras.CompraDomain;
import com.agrosync.domain.cuentascobrar.CuentaCobrarDomain;
import com.agrosync.domain.cuentaspagar.CuentaPagarDomain;
import com.agrosync.domain.enums.usuarios.EstadoUsuarioEnum;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;
import com.agrosync.domain.ventas.VentaDomain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioDetalladoDomain extends BaseDomain {

    private String nombre;
    private String telefono;
    private TipoUsuarioEnum tipoUsuario;
    private EstadoUsuarioEnum estado;

    private CarteraDomain cartera;
    private List<CompraDomain> compras;
    private List<CuentaPagarDomain> cuentasPagar;
    private List<AbonoDomain> abonos;
    private List<VentaDomain> ventas;
    private List<CuentaCobrarDomain> cuentasCobrar;
    private List<CobroDomain> cobros;


    public UsuarioDetalladoDomain() {
        super();
    }

    public UsuarioDetalladoDomain(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario, EstadoUsuarioEnum estado, CarteraDomain cartera,
                                  List<CompraDomain> compras, List<CuentaPagarDomain> cuentasPagar, List<AbonoDomain> abonos,
                                  List<VentaDomain> ventas, List<CuentaCobrarDomain> cuentasCobrar, List<CobroDomain> cobros) {
        super(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
        setEstado(estado);
        setCartera(cartera);
        setCompras(compras);
        setCuentasPagar(cuentasPagar);
        setAbonos(abonos);
        setVentas(ventas);
        setCuentasCobrar(cuentasCobrar);
        setCobros(cobros);
    }

    public static UsuarioDetalladoDomain create(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario, EstadoUsuarioEnum estado, CarteraDomain cartera,
                                       List<CompraDomain> compras, List<CuentaPagarDomain> cuentasPagar, List<AbonoDomain> abonos,
                                       List<VentaDomain> ventas, List<CuentaCobrarDomain> cuentasCobrar, List<CobroDomain> cobros) {
        return new UsuarioDetalladoDomain(id, nombre, telefono, tipoUsuario, estado, cartera, compras, cuentasPagar, abonos, ventas, cuentasCobrar, cobros);
    }

    public static UsuarioDetalladoDomain create(UUID id) {
        return new UsuarioDetalladoDomain(id, TextHelper.EMPTY, TextHelper.EMPTY, TipoUsuarioEnum.CLIENTE, EstadoUsuarioEnum.ACTIVO, new CarteraDomain(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public static UsuarioDetalladoDomain create() {
        return new UsuarioDetalladoDomain(UUIDHelper.getDefault(), TextHelper.EMPTY, TextHelper.EMPTY, TipoUsuarioEnum.CLIENTE, EstadoUsuarioEnum.ACTIVO, new CarteraDomain(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
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

    public List<AbonoDomain> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<AbonoDomain> abonos) {
        this.abonos = abonos;
    }

    public List<CobroDomain> getCobros() {
        return cobros;
    }

    public void setCobros(List<CobroDomain> cobros) {
        this.cobros = cobros;
    }
}

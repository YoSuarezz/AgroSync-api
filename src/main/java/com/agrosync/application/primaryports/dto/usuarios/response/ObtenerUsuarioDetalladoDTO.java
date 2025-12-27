package com.agrosync.application.primaryports.dto.usuarios.response;

import com.agrosync.application.primaryports.dto.abonos.response.ObtenerAbonoDTO;
import com.agrosync.application.primaryports.dto.carteras.response.ObtenerCarteraDTO;
import com.agrosync.application.primaryports.dto.cobros.response.ObtenerCobroDTO;
import com.agrosync.application.primaryports.dto.compras.response.ObtenerCompraDTO;
import com.agrosync.application.primaryports.dto.cuentascobrar.response.ObtenerCuentaCobrarDTO;
import com.agrosync.application.primaryports.dto.cuentaspagar.response.ObtenerCuentaPagarDTO;
import com.agrosync.application.primaryports.dto.ventas.response.ObtenerVentasDTO;
import com.agrosync.crosscutting.helpers.ObjectHelper;
import com.agrosync.crosscutting.helpers.TextHelper;
import com.agrosync.crosscutting.helpers.UUIDHelper;
import com.agrosync.domain.enums.usuarios.EstadoUsuarioEnum;
import com.agrosync.domain.enums.usuarios.TipoUsuarioEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ObtenerUsuarioDetalladoDTO {

    private UUID id;
    private String nombre;
    private String telefono;
    private TipoUsuarioEnum tipoUsuario;
    private EstadoUsuarioEnum estado;

    private ObtenerCarteraDTO cartera;
    private List<ObtenerCompraDTO> compras;
    private List<ObtenerCuentaPagarDTO> cuentasPagar;
    private List<ObtenerAbonoDTO> abonos;
    private List<ObtenerVentasDTO> ventas;
    private List<ObtenerCuentaCobrarDTO> cuentasCobrar;
    private List<ObtenerCobroDTO> cobros;

    public ObtenerUsuarioDetalladoDTO() {
        setId(UUIDHelper.getDefault());
        setNombre(TextHelper.EMPTY);
        setTelefono(TextHelper.EMPTY);
        setTipoUsuario(null);
        setEstado(null);

        setCartera(ObtenerCarteraDTO.create());
        setCompras(new ArrayList<>());
        setCuentasPagar(new ArrayList<>());
        setVentas(new ArrayList<>());
        setCuentasCobrar(new ArrayList<>());
    }



    public ObtenerUsuarioDetalladoDTO(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario,
                                      EstadoUsuarioEnum estado, ObtenerCarteraDTO cartera, List<ObtenerCompraDTO> compras,
                                      List<ObtenerCuentaPagarDTO> cuentasPagar, List<ObtenerAbonoDTO> abonos, List<ObtenerVentasDTO> ventas,
                                      List<ObtenerCuentaCobrarDTO> cuentasCobrar, List<ObtenerCobroDTO> cobros)  {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono);
        setTipoUsuario(tipoUsuario);
        setEstado(estado);
        setCartera(cartera);
        setCompras(compras);
        setCuentasPagar(cuentasPagar);
        setVentas(ventas);
        setCuentasCobrar(cuentasCobrar);
    }

    public static ObtenerUsuarioDetalladoDTO create(UUID id, String nombre, String telefono, TipoUsuarioEnum tipoUsuario,
                                                    EstadoUsuarioEnum estado, ObtenerCarteraDTO cartera, List<ObtenerCompraDTO> compras,
                                                    List<ObtenerCuentaPagarDTO> cuentasPagar, List<ObtenerAbonoDTO> abonos, List<ObtenerVentasDTO> ventas,
                                                    List<ObtenerCuentaCobrarDTO> cuentasCobrar, List<ObtenerCobroDTO> cobros) {
        return new ObtenerUsuarioDetalladoDTO(id, nombre, telefono, tipoUsuario, estado, cartera, compras,
                                                cuentasPagar, abonos, ventas, cuentasCobrar, cobros);
    }

    public static ObtenerUsuarioDetalladoDTO create() {
        return new ObtenerUsuarioDetalladoDTO();
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
        this.telefono = telefono;
    }

    public TipoUsuarioEnum getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuarioEnum tipoUsuario) {
        this.tipoUsuario = ObjectHelper.getDefault(tipoUsuario, null);
    }

    public EstadoUsuarioEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuarioEnum estado) {
        this.estado = ObjectHelper.getDefault(estado, null);
    }

    public ObtenerCarteraDTO getCartera() {
        return cartera;
    }

    public void setCartera(ObtenerCarteraDTO cartera) {
        this.cartera = ObjectHelper.getDefault(cartera, ObtenerCarteraDTO.create());
    }

    public List<ObtenerCompraDTO> getCompras() {
        return compras;
    }

    public void setCompras(List<ObtenerCompraDTO> compras) {
        this.compras = ObjectHelper.getDefault(compras, new ArrayList<>());
    }

    public List<ObtenerCuentaPagarDTO> getCuentasPagar() {
        return cuentasPagar;
    }

    public void setCuentasPagar(List<ObtenerCuentaPagarDTO> cuentasPagar) {
        this.cuentasPagar = ObjectHelper.getDefault(cuentasPagar, new ArrayList<>());
    }

    public List<ObtenerAbonoDTO> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<ObtenerAbonoDTO> abonos) {
        this.abonos = abonos;
    }

    public List<ObtenerVentasDTO> getVentas() {
        return ventas;
    }

    public void setVentas(List<ObtenerVentasDTO> ventas) {
        this.ventas = ObjectHelper.getDefault(ventas, new ArrayList<>());
    }

    public List<ObtenerCuentaCobrarDTO> getCuentasCobrar() {
        return cuentasCobrar;
    }

    public void setCuentasCobrar(List<ObtenerCuentaCobrarDTO> cuentasCobrar) {
        this.cuentasCobrar = ObjectHelper.getDefault(cuentasCobrar, new ArrayList<>());
    }

    public List<ObtenerCobroDTO> getCobros() {
        return cobros;
    }

    public void setCobros(List<ObtenerCobroDTO> cobros) {
        this.cobros = cobros;
    }
}

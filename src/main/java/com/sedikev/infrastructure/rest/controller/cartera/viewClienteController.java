package com.sedikev.infrastructure.rest.controller.cartera;

import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.dto.VentaDTO;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.application.mapper.VentaMapper;
import com.sedikev.application.service.CarteraFacadeImpl;
import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.application.service.VentaFacadeImpl;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class viewClienteController {

    @Autowired private NavigationService navigationService;
    @Autowired private UsuarioFacadeImpl usuarioFacade;
    @Autowired private UsuarioMapper usuarioMapper;
    @Autowired private CarteraFacadeImpl carteraFacade;
    @Autowired private CarteraMapper carteraMapper;
    @Autowired private VentaFacadeImpl ventaFacade;
    @Autowired private VentaMapper ventaMapper;

    @FXML private TextField nombreField;
    @FXML private TextField telefonoField;
    @FXML private Button buscarButton;
    @FXML private Button regresarButton;

    @FXML private TableView<CarteraDTO> carteraTableView;
    @FXML private TableColumn<CarteraDTO, String> nombreColumn;
    @FXML private TableColumn<CarteraDTO, BigDecimal> saldoColumn;
    @FXML private TableColumn<CarteraDTO, Void> accionesColumn;

    @FXML private TableView<VentaDTO> ventasTableView;
    @FXML private TableColumn<VentaDTO, Long> ventaIdColumn;
    @FXML private TableColumn<VentaDTO, String> ventaFechaColumn;
    @FXML private TableColumn<VentaDTO, BigDecimal> ventaTotalColumn;

    private List<CarteraDTO> allClientes;  // cache de clientes
    DecimalFormat formato = new DecimalFormat("#,##0");// Variable para almacenar el precio total


    @FXML
    private void initialize() {
        // Columnas cartera
        nombreColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsuario().getNombre()));
        saldoColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getSaldo()));
        saldoColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    if (item.compareTo(BigDecimal.ZERO) < 0)       setStyle("-fx-text-fill: red;");
                    else if (item.compareTo(BigDecimal.ZERO) > 0)  setStyle("-fx-text-fill: #27ae60;");
                    else                                           setStyle("-fx-text-fill: black;");
                }
            }
        });
        buscarButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        // Columnas ventas
        ventaIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ventaFechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));  // asumir String o formateado
        ventaTotalColumn.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));  // asumir String o formateado

        // Vista inicial
        ventasTableView.setVisible(false);
        regresarButton.setVisible(false);

        cargarClientes();

        // Click en fila de cliente
        carteraTableView.setRowFactory(tv -> {
            TableRow<CarteraDTO> row = new TableRow<>();
            row.setOnMouseClicked(evt -> {
                if (!row.isEmpty() && evt.getClickCount() == 1) {
                    mostrarVentas(row.getItem());
                }
            });
            return row;
        });
    }

    private void cargarClientes() {
        Map<Long, CarteraDTO> carteraMap = carteraFacade.findAll().stream()
                .map(carteraMapper::toDTO)
                .collect(Collectors.toMap(c -> c.getUsuario().getId(), c -> c));

        allClientes = usuarioFacade.findAll().stream()
                .filter(u -> u.getTipo_usuario().equalsIgnoreCase("cliente"))
                .map(u -> {
                    CarteraDTO dto = carteraMap.get(u.getId());
                    if (dto == null) {
                        dto = new CarteraDTO();
                        dto.setUsuario(usuarioMapper.toDTO(u));
                        dto.setSaldo(BigDecimal.ZERO);
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        carteraTableView.getItems().setAll(allClientes);
    }

    @FXML
    private void buscarClientes(ActionEvent event) {
        String nombreFiltro   = nombreField.getText().trim().toLowerCase();
        String telefonoFiltro = telefonoField.getText().trim();

        List<CarteraDTO> filtrados = allClientes.stream()
                .filter(c -> c.getUsuario().getNombre().toLowerCase().contains(nombreFiltro))
                .filter(c -> telefonoFiltro.isEmpty() ||
                        c.getUsuario().getTelefono().contains(telefonoFiltro))
                .collect(Collectors.toList());

        carteraTableView.getItems().setAll(filtrados);
    }

    private void mostrarVentas(CarteraDTO cliente) {
        List<VentaDTO> ventas = ventaFacade.findByClienteId(cliente.getUsuario().getId()).stream()
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());

        ventasTableView.getItems().setAll(ventas);

        // Ocultar clientes y filtros
        carteraTableView.setVisible(false);
        nombreField.setVisible(false);
        telefonoField.setVisible(false);
        buscarButton.setVisible(false);

        // Mostrar ventas y botón regresar
        ventasTableView.setVisible(true);
        regresarButton.setVisible(true);
    }

    @FXML
    private void regresar(ActionEvent e) {
        // Restaurar vista de clientes
        carteraTableView.setVisible(true);
        nombreField.setVisible(true);
        telefonoField.setVisible(true);
        buscarButton.setVisible(true);

        ventasTableView.setVisible(false);
        regresarButton.setVisible(false);
    }

    // — Métodos de navegación (sin cambios) —
    @FXML private void goRegisterLote(ActionEvent e){ navigationService.navigateTo("/fxml/loteRegister.fxml", (Node)e.getSource()); }
    @FXML private void goViewLote   (ActionEvent e){ navigationService.navigateTo("/fxml/viewLote.fxml",   (Node)e.getSource()); }
    @FXML private void goRegisterSale(ActionEvent e){ navigationService.navigateTo("/fxml/ventaRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewSale   (ActionEvent e){ navigationService.navigateTo("/fxml/viewVenta.fxml", (Node)e.getSource()); }
    @FXML private void goRegisterUser(ActionEvent e){ navigationService.navigateTo("/fxml/usuarioRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewUser   (ActionEvent e){ navigationService.navigateTo("/fxml/viewUsuario.fxml",(Node)e.getSource()); }
    @FXML private void goViewSupplier(ActionEvent e){ navigationService.navigateTo("/fxml/viewProveedorCartera.fxml",(Node)e.getSource()); }
    @FXML private void goViewClient (ActionEvent e){ navigationService.navigateTo("/fxml/viewClienteCartera.fxml",(Node)e.getSource()); }
}

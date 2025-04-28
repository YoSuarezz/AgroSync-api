package com.sedikev.infrastructure.rest.controller.cartera;

import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.application.dto.PagoDTO;
import com.sedikev.application.dto.VentaDTO;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.mapper.PagoMapper;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.application.mapper.VentaMapper;
import com.sedikev.application.service.PagoFacadeImpl;
import com.sedikev.application.service.VentaFacadeImpl;
import com.sedikev.application.service.CarteraFacadeImpl;
import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class viewClienteController {

    @Autowired private NavigationService navigationService;
    @Autowired private UsuarioFacadeImpl usuarioFacade;
    @Autowired private UsuarioMapper usuarioMapper;
    @Autowired private CarteraFacadeImpl carteraFacade;
    @Autowired private CarteraMapper carteraMapper;
    @Autowired private VentaFacadeImpl ventaService;
    @Autowired private VentaMapper ventaMapper;
    @Autowired private PagoMapper pagoMapper;
    @Autowired private PagoFacadeImpl pagoService;

    @FXML private TextField nombreField;
    @FXML private TextField telefonoField;
    @FXML private Button buscarButton;
    @FXML private Button regresarButton;  // Botón de regreso

    // Tablas para la vista
    @FXML private TableView<CarteraDTO> carteraTableView;
    @FXML private TableColumn<CarteraDTO, String> nombreColumn;
    @FXML private TableColumn<CarteraDTO, BigDecimal> saldoColumn;
    @FXML private TableColumn<CarteraDTO, Void> accionesColumn;
    @FXML private TableView<VentaDTO> historialVentasTableView;

    @FXML private Tab historialComprasTab;
    @FXML private Tab historialPagosTab;
    @FXML private TableView<VentaDTO> historialComprasTableView;
    @FXML private TableView<PagoDTO> historialPagosTableView;

    @FXML private TextField montoPagoField;
    @FXML private Button botonPago;

    private List<CarteraDTO> allClientes;  // lista cacheada
    private CarteraDTO selectedCartera;     // cartera seleccionada actualmente

    @FXML
    private void initialize() {
        // Configuración de columnas para la tabla de cartera
        nombreColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsuario().getNombre()));
        saldoColumn.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getSaldo()));

        // Colorear saldo
        saldoColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    if (item.compareTo(BigDecimal.ZERO) < 0) {
                        setStyle("-fx-text-fill: red;");
                    } else if (item.compareTo(BigDecimal.ZERO) > 0) {
                        setStyle("-fx-text-fill: #27ae60;");
                    } else {
                        setStyle("-fx-text-fill: black;");
                    }
                }
            }
        });

        // Configuración para capturar el clic en cada fila de la tabla
        carteraTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                CarteraDTO selectedCartera = carteraTableView.getSelectionModel().getSelectedItem();
                if (selectedCartera != null) {
                    mostrarHistorialDeCliente(selectedCartera);
                }
            }
        });

        // Botón buscar verde
        buscarButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        // Ocultar Tab y botón de regreso al inicio
        // Ocultar Tab y botón de regreso al inicio
        historialComprasTab.setDisable(true);
        historialPagosTab.setDisable(true);
        regresarButton.setVisible(false);

        // Ocultar el campo de monto a pagar y el botón de realizar pago
        montoPagoField.setVisible(false);
        botonPago.setVisible(false);


        // Cargar la lista de clientes inicialmente
        cargarClientes();
    }

    @FXML
    private void mostrarHistorialDeCliente(CarteraDTO carteraDTO) {
        this.selectedCartera = carteraDTO;

        // Deshabilitar la tabla de clientes y mostrar las Tabs
        carteraTableView.setVisible(false);
        nombreField.setVisible(false);
        telefonoField.setVisible(false);
        buscarButton.setVisible(false);

        // Habilitar las Tabs y el botón de regreso
        historialComprasTab.setDisable(false);
        historialPagosTab.setDisable(false);
        regresarButton.setVisible(true);

        // Hacer visibles los campos para pago
        montoPagoField.setVisible(true);
        botonPago.setVisible(true);

        // Cargar historial de ventas
        List<VentaDTO> ventas = cargarHistorialVentas(carteraDTO.getUsuario().getId());
        historialVentasTableView.setItems(FXCollections.observableArrayList(ventas));

        // Cargar historial de pagos
        List<PagoDTO> pagos = cargarHistorialPagos(carteraDTO.getUsuario().getId());
        historialPagosTableView.setItems(FXCollections.observableArrayList(pagos));
    }

    // Méto-do que carga el historial de ventas (compras) para el cliente
    private List<VentaDTO> cargarHistorialVentas(Long usuarioId) {
        return ventaService.findAll().stream()
                .filter(venta -> venta.getUsuario().getId().equals(usuarioId))
                .map(ventaMapper::toDTO)
                .collect(Collectors.toList());
    }

    private List<PagoDTO> cargarHistorialPagos(Long usuarioId) {
        return pagoService.findByUsuarioId(usuarioId).stream()
                .map(pagoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Método para realizar un pago
    @FXML
    private void realizarPago(ActionEvent event) {
        try {
            BigDecimal montoPago = new BigDecimal(montoPagoField.getText());

            // Verificar que el monto sea mayor que cero
            if (montoPago.compareTo(BigDecimal.ZERO) <= 0) {
                mostrarAlerta("Error", "El monto del pago debe ser mayor a cero.", Alert.AlertType.ERROR);
                return;
            }

            // Crear un nuevo pago
            PagoDTO nuevoPago = new PagoDTO();
            nuevoPago.setCantidad(montoPago);
            nuevoPago.setFecha(LocalDate.now());
            nuevoPago.setTipoPago("abono");  // Tipo de pago: "abono"

            // Guardar el pago y asociarlo con la venta y el cliente
            pagoService.save(pagoMapper.toDomain(nuevoPago));

            // Actualizar el historial de pagos
            cargarHistorialPagos(selectedCartera.getUsuario().getId());

            // Mostrar mensaje de éxito
            mostrarAlerta("Éxito", "Pago realizado correctamente", Alert.AlertType.INFORMATION);
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Ingrese un monto válido", Alert.AlertType.ERROR);
        }
    }

    // Método para cargar la lista de clientes
    private void cargarClientes() {
        Map<Long, CarteraDTO> carteraMap = carteraFacade.findAll().stream()
                .map(carteraMapper::toDTO)
                .collect(Collectors.toMap(c -> c.getUsuario().getId(), c -> c));

        allClientes = usuarioFacade.findAll().stream()
                .filter(u -> u.getTipo_usuario().equalsIgnoreCase("cliente"))
                .map(u -> {
                    CarteraDTO dto = carteraMap.get(u.getId());
                    if (dto == null) {
                        // Si no hay cartera, saldo = 0
                        dto = new CarteraDTO();
                        dto.setUsuario(usuarioMapper.toDTO(u));
                        dto.setSaldo(BigDecimal.ZERO);
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        // Asignar los clientes a la tabla
        carteraTableView.getItems().setAll(allClientes);
    }

    // Método para buscar clientes en la tabla
    @FXML
    private void buscarClientes(ActionEvent event) {
        String nombreFiltro = nombreField.getText().trim().toLowerCase();
        String telefonoFiltro = telefonoField.getText().trim();

        List<CarteraDTO> filtrados = allClientes.stream()
                .filter(c -> c.getUsuario().getNombre().toLowerCase().contains(nombreFiltro))
                .filter(c -> telefonoFiltro.isEmpty() || c.getUsuario().getTelefono().contains(telefonoFiltro))
                .collect(Collectors.toList());

        carteraTableView.getItems().setAll(filtrados);
    }

    // Méto-do para regresar a la vista de clientes
    @FXML
    private void regresarAClientes(ActionEvent event) {
        // Revertir la vista a la tabla de clientes
        carteraTableView.setVisible(true);
        nombreField.setVisible(true);
        telefonoField.setVisible(true);
        buscarButton.setVisible(true);

        // Deshabilitar las Tabs y el botón de regreso
        historialComprasTab.setDisable(true);
        historialPagosTab.setDisable(true);
        regresarButton.setVisible(false);

        // Ocultar los campos de pago
        montoPagoField.setVisible(false);
        botonPago.setVisible(false);
    }

    // Mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // — Métodos de navegación —
    @FXML private void goRegisterLote(ActionEvent e){ navigationService.navigateTo("/fxml/loteRegister.fxml", (Node)e.getSource()); }
    @FXML private void goViewLote   (ActionEvent e){ navigationService.navigateTo("/fxml/viewLote.fxml",   (Node)e.getSource()); }
    @FXML private void goRegisterSale(ActionEvent e){ navigationService.navigateTo("/fxml/ventaRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewSale   (ActionEvent e){ navigationService.navigateTo("/fxml/viewVenta.fxml", (Node)e.getSource()); }
    @FXML private void goRegisterUser(ActionEvent e){ navigationService.navigateTo("/fxml/usuarioRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewUser   (ActionEvent e){ navigationService.navigateTo("/fxml/viewUsuario.fxml",(Node)e.getSource()); }
    @FXML private void goViewSupplier(ActionEvent e){ navigationService.navigateTo("/fxml/viewProveedorCartera.fxml",(Node)e.getSource()); }
    @FXML private void goViewClient (ActionEvent e){ navigationService.navigateTo("/fxml/viewClienteCartera.fxml",(Node)e.getSource()); }
}
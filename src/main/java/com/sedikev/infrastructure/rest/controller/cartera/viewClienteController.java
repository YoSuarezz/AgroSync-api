package com.sedikev.infrastructure.rest.controller.cartera;

import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.application.service.CarteraFacadeImpl;
import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class viewClienteController {

    @Autowired private NavigationService navigationService;
    @Autowired private UsuarioFacadeImpl usuarioFacade;
    @Autowired private UsuarioMapper usuarioMapper;
    @Autowired private CarteraFacadeImpl carteraFacade;
    @Autowired private CarteraMapper carteraMapper;

    @FXML private TextField nombreField;
    @FXML private TextField telefonoField;
    @FXML private Button buscarButton;

    @FXML private TableView<CarteraDTO> carteraTableView;
    @FXML private TableColumn<CarteraDTO, String> nombreColumn;
    @FXML private TableColumn<CarteraDTO, BigDecimal> saldoColumn;
    @FXML private TableColumn<CarteraDTO, Void> accionesColumn;

    private List<CarteraDTO> allClientes;  // lista cacheada

    @FXML
    private void initialize() {
        // Configuración de columnas
        nombreColumn.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getUsuario().getNombre())
        );
        saldoColumn.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getSaldo())
        );

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

        // Botón buscar verde
        buscarButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        // Cargo la lista inicial
        cargarClientes();
    }

    private void cargarClientes() {
        // 1) Mapa de carteras existentes por usuarioId
        Map<Long, CarteraDTO> carteraMap = carteraFacade.findAll().stream()
                .map(carteraMapper::toDTO)
                .collect(Collectors.toMap(
                        c -> c.getUsuario().getId(),
                        c -> c
                ));

        // 2) Obtengo todos los usuarios y filtro solo "Cliente"
        allClientes = usuarioFacade.findAll().stream()
                .filter(u -> u.getTipo_usuario().equalsIgnoreCase("cliente"))
                .map(u -> {
                    CarteraDTO dto = carteraMap.get(u.getId());
                    if (dto == null) {
                        // si no hay cartera, saldo = 0
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
                .filter(c -> c.getUsuario().getNombre()
                        .toLowerCase()
                        .contains(nombreFiltro))
                .filter(c -> telefonoFiltro.isEmpty() ||
                        c.getUsuario().getTelefono()
                                .contains(telefonoFiltro))
                .collect(Collectors.toList());

        carteraTableView.getItems().setAll(filtrados);
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
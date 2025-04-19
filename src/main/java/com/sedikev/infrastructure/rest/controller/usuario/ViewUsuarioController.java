package com.sedikev.infrastructure.rest.controller.usuario;

import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewUsuarioController {

    @Autowired private NavigationService navigationService;
    @Autowired private UsuarioFacadeImpl usuarioFacade;
    @Autowired private UsuarioMapper usuarioMapper;

    @FXML private TextField nombreField;
    @FXML private TextField telefonoField;
    @FXML private ChoiceBox<String> tipoChoiceBox;
    @FXML private Button buscarButton;

    @FXML private TableView<UsuarioDTO> usuarioTableView;
    @FXML private TableColumn<UsuarioDTO, String> nombreColumn;
    @FXML private TableColumn<UsuarioDTO, String> tipoColumn;
    @FXML private TableColumn<UsuarioDTO, String> telefonoColumn;
    @FXML private TableColumn<UsuarioDTO, String> accionesColumn;

    @FXML
    private void initialize() {
        // 1) Configuro columnas
        nombreColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tipoColumn.setCellValueFactory(c   -> new SimpleStringProperty(c.getValue().getTipo_usuario()));
        telefonoColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getTelefono()));

        // 2) ChoiceBox con “Todos” + roles
        tipoChoiceBox.getItems().addAll("Todos", "Proveedor", "Cliente");
        tipoChoiceBox.setValue("Todos");

        // 3) Cargo todos al inicio
        cargarUsuarios();
    }

    @FXML
    private void cargarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioFacade.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
        usuarioTableView.getItems().setAll(usuarios);
    }

    @FXML
    private void buscarUsuarios(ActionEvent event) {
        String nombreFiltro   = nombreField.getText().trim().toLowerCase();
        String telefonoFiltro = telefonoField.getText().trim();
        String tipoFiltro     = tipoChoiceBox.getValue().toLowerCase();

        List<UsuarioDTO> filtrados = usuarioFacade.findAll().stream()
                .map(usuarioMapper::toDTO)
                .filter(u -> u.getNombre().toLowerCase().contains(nombreFiltro))
                .filter(u -> telefonoFiltro.isEmpty() ||
                        u.getTelefono().contains(telefonoFiltro))
                .filter(u -> tipoFiltro.equals("todos") ||
                        u.getTipo_usuario().toLowerCase().equals(tipoFiltro))
                .collect(Collectors.toList());

        usuarioTableView.getItems().setAll(filtrados);
    }

    // ——— Métodos de navegación (igual que antes) ———
    @FXML private void goRegisterLote(ActionEvent e) { navigationService.navigateTo("/fxml/loteRegister.fxml", (Node)e.getSource()); }
    @FXML private void goViewLote   (ActionEvent e) { navigationService.navigateTo("/fxml/viewLote.fxml",   (Node)e.getSource()); }
    @FXML private void goRegisterSale(ActionEvent e){ navigationService.navigateTo("/fxml/ventaRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewSale   (ActionEvent e) { navigationService.navigateTo("/fxml/viewVenta.fxml", (Node)e.getSource()); }
    @FXML private void goRegisterUser(ActionEvent e){ navigationService.navigateTo("/fxml/usuarioRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewUser   (ActionEvent e) { navigationService.navigateTo("/fxml/viewUsuario.fxml",(Node)e.getSource()); }
    @FXML private void goViewClient (ActionEvent e) { navigationService.navigateTo("/fxml/viewClienteCartera.fxml",(Node)e.getSource()); }
    @FXML private void goViewSupplier(ActionEvent e){ navigationService.navigateTo("/fxml/viewProveedorCartera.fxml",(Node)e.getSource()); }
}
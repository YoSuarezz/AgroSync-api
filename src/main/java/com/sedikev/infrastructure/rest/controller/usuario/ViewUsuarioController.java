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

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private UsuarioFacadeImpl usuarioFacade;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @FXML
    private Button id_registerLote1;

    @FXML
    private Button id_registerSale1;

    @FXML
    private Button id_registerUser1;

    @FXML
    private Button id_viewClient1;

    @FXML
    private Button id_viewLote1;

    @FXML
    private Button id_viewSale1;

    @FXML
    private Button id_viewSupplier1;

    @FXML
    private Button id_viewUser1;

    @FXML
    private TextField nombreField;
    @FXML
    private ChoiceBox<String> tipoChoiceBox;
    @FXML
    private TableView<UsuarioDTO> usuarioTableView;
    @FXML
    private TableColumn<UsuarioDTO, String> nombreColumn;
    @FXML
    private TableColumn<UsuarioDTO, String> tipoColumn;
    @FXML
    private TableColumn<UsuarioDTO, String> telefonoColumn;
    @FXML
    private TableColumn<UsuarioDTO, String> accionesColumn;

    @FXML
    private void initialize() {
        // Configuración de las columnas
        nombreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tipoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo_usuario()));
        telefonoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

        // Configurar ChoiceBox con los tipos de usuario
        tipoChoiceBox.getItems().addAll("Proveedor", "Cliente", "Admin");
        tipoChoiceBox.setValue("Proveedor"); // Puedes establecer un valor por defecto

        // Cargar todos los usuarios al inicio
        cargarUsuarios();
    }

    @FXML
    private void cargarUsuarios() {
        // Obtener todos los usuarios del servicio
        List<UsuarioDTO> usuarios = usuarioFacade.findAll().stream()
                // Usar el mapeador para convertir de UsuarioDomain a UsuarioDTO
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());

        // Asignar los usuarios a la tabla
        usuarioTableView.getItems().setAll(usuarios);
    }


    @FXML
    private void buscarUsuarios() {
        // Obtener el texto del campo de nombre y el valor del tipo seleccionado
        String nombre = nombreField.getText().toLowerCase();
        String tipo = tipoChoiceBox.getValue().toLowerCase();

        // Obtener todos los usuarios y filtrarlos por nombre y tipo
        List<UsuarioDTO> usuarios = usuarioFacade.findAll().stream()
                // Convertir de UsuarioDomain a UsuarioDTO
                .map(usuarioMapper::toDTO)
                .filter(usuario -> usuario.getNombre().toLowerCase().contains(nombre) && usuario.getTipo_usuario().toLowerCase().contains(tipo))
                .collect(Collectors.toList());

        // Actualizar la tabla con los resultados filtrados
        usuarioTableView.getItems().setAll(usuarios);
    }


    @FXML
    private void goRegisterLote(ActionEvent event) {
        navigationService.navigateTo("/fxml/loteRegister.fxml", (Node) event.getSource());
    }

    @FXML
    void goRegisterSale(ActionEvent event) {
        navigationService.navigateTo("/fxml/ventaRegister.fxml", (Node) event.getSource());
    }

    @FXML
    void goRegisterUser(ActionEvent event) {
        navigationService.navigateTo("/fxml/usuarioRegister.fxml", (Node) event.getSource());
    }

    @FXML
    void goViewClient(ActionEvent event) {
        navigationService.navigateTo("/fxml/viewClienteCartera.fxml", (Node) event.getSource());
    }

    @FXML
    void goViewLote(ActionEvent event) {
        navigationService.navigateTo("/fxml/viewLote.fxml", (Node) event.getSource());
    }

    @FXML
    void goViewSale(ActionEvent event) {
        navigationService.navigateTo("/fxml/viewVenta.fxml", (Node) event.getSource());
    }

    @FXML
    void goViewSupplier(ActionEvent event) {
        navigationService.navigateTo("/fxml/viewProveedorCartera.fxml", (Node) event.getSource());
    }

    @FXML
    void goViewUser(ActionEvent event) {
        navigationService.navigateTo("/fxml/viewUsuario.fxml", (Node) event.getSource());
    }

}

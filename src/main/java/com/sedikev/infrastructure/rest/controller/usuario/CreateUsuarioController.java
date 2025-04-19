package com.sedikev.infrastructure.rest.controller.usuario;

import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CreateUsuarioController {

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private UsuarioFacadeImpl usuarioFacade;

    @FXML
    private TextField nombreField;
    @FXML
    private TextField telefonoField;
    @FXML
    private ComboBox<String> tipoComboBox;
    @FXML
    private Button registrarUsuarioButton;

    @FXML
    private Button id_registerLote;

    @FXML
    private Button id_registerSale;

    @FXML
    private Button id_registerUser;

    @FXML
    private Button id_viewClient;

    @FXML
    private Button id_viewLote;

    @FXML
    private Button id_viewSale;

    @FXML
    private Button id_viewSupplier;

    @FXML
    private Button id_viewUser;

    @FXML
    private void initialize() {
        // Inicializa el ChoiceBox con los tipos de usuario posibles
        tipoComboBox.getItems().addAll("Proveedor", "Cliente");
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        String nombre     = nombreField.getText().trim();
        String telefono   = telefonoField.getText().trim();
        String tipoUsuario = tipoComboBox.getValue();

        if (nombre.isEmpty() || telefono.isEmpty() || tipoUsuario == null) {
            showAlert(Alert.AlertType.WARNING, "Datos incompletos",
                    "Todos los campos deben ser llenados.");
            return;
        }

        UsuarioDomain nuevo = new UsuarioDomain();
        nuevo.setNombre(nombre);
        nuevo.setTelefono(telefono);
        nuevo.setTipo_usuario(tipoUsuario);

        try {
            UsuarioDomain registrado = usuarioFacade.save(nuevo);
            showAlert(Alert.AlertType.INFORMATION, "Éxito",
                    "Usuario registrado: " + registrado.getNombre());
            clearForm();  // <— aquí limpio los campos tras un registro exitoso
        }
        catch (BusinessSedikevException ex) {
            showAlert(Alert.AlertType.ERROR, "Error de validación", ex.getMessage());
        }
        catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "No se pudo registrar el usuario: " + ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearForm() {
        nombreField.clear();
        telefonoField.clear();
        tipoComboBox.getSelectionModel().clearSelection();
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


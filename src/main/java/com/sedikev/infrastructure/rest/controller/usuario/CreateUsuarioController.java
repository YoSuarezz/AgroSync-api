package com.sedikev.infrastructure.rest.controller.usuario;

import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
        // Obtener los valores del formulario
        String nombre = nombreField.getText();
        String telefono = telefonoField.getText();
        String tipoUsuario = tipoComboBox.getValue();

        // Validar que todos los campos estén llenos
        if (nombre.isEmpty() || telefono.isEmpty() || tipoUsuario == null) {
            // Mostrar algún mensaje de error (por ejemplo, usando un Alert)
            System.out.println("Todos los campos deben ser llenados.");
            return;
        }

        // Crear el objeto UsuarioDomain
        UsuarioDomain nuevoUsuario = new UsuarioDomain();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setTipo_usuario(tipoUsuario);

        // Llamar al service para registrar el usuario
        try {
            UsuarioDomain usuarioRegistrado = usuarioFacade.save(nuevoUsuario);
            // Si el usuario fue registrado exitosamente, redirigir a otra vista o mostrar un mensaje
            System.out.println("Usuario registrado: " + usuarioRegistrado.getNombre());
        } catch (Exception e) {
            // Manejar errores (por ejemplo, si el usuario ya existe)
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
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


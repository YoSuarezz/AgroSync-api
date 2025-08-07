package com.sedikev.infrastructure.rest.controller.usuario;

import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import com.sedikev.infrastructure.rest.advice.ParameterReceiver;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class CreateUsuarioController implements ParameterReceiver {

    @Autowired
    private NavigationService navigationService;

    @Autowired
    private UsuarioFacadeImpl usuarioFacade;

    private Long usuarioId = null;

    @FXML
    private TextField id_nombre;
    @FXML
    private TextField id_telefono;
    @FXML
    private ComboBox<String> id_tipo;
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
        id_tipo.getItems().addAll("Proveedor", "Cliente");

        // Verificar si estamos en modo edición (cuando se navega desde ViewLoteController)
        Node node = id_registerUser; // O cualquier nodo de la escena
        Map<String, Object> parameters = navigationService.getParameters(node);

        if (parameters != null && parameters.containsKey("usuarioId")) {
            Long usuarioId = (Long) parameters.get("usuarioId");
            cargarDatosUsuario(usuarioId); // Méto-do que carga los datos del lote y sus animales
        }
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {
        try {
            validarCampoUsuario();

            if (usuarioId != null) {
                // Modo edición
                UsuarioDomain usuario = usuarioFacade.findById(usuarioId);
                usuario.setNombre(id_nombre.getText());
                usuario.setTelefono(id_telefono.getText());
                usuario.setTipo_usuario(id_tipo.getValue());

                usuarioFacade.update(usuario);

                mostrarAlerta("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
            } else {
                // Modo creación
                UsuarioDomain usuario = new UsuarioDomain();
                usuario.setNombre(id_nombre.getText());
                usuario.setTelefono(id_telefono.getText());
                usuario.setTipo_usuario(id_tipo.getValue());

                UsuarioDomain usuarioDomain = usuarioFacade.save(usuario);

                mostrarAlerta("Éxito", "Usuario creado correctamente", Alert.AlertType.INFORMATION);
            }

            limpiarFormulario();
            navigationService.navigateTo("/fxml/viewUsuario.fxml", (Node) event.getSource());

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void validarCampoUsuario() {
        if (id_nombre.getText().isEmpty() || id_telefono.getText().isEmpty() || id_tipo.getValue() == null) {
            throw new IllegalArgumentException("Tipo, nombre y telefono son obligatorios");
        }
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        if (parameters != null && parameters.containsKey("usuarioId")) {
            usuarioId = (Long) parameters.get("usuarioId");
            cargarDatosUsuario(usuarioId);
        }
    }

    private void cargarDatosUsuario(Long usuarioId) {
        if (usuarioId == null) return;

        try {
            // Cargar datos del usuario desde la base de datos
            UsuarioDomain usuario = usuarioFacade.findById(usuarioId);
            if (usuario == null) {
                mostrarAlerta("Error", "No se encontró el lote especificado", Alert.AlertType.ERROR);
                return;
            }

            // Cargar campos del formulario
            Platform.runLater(() -> {
                id_nombre.setText(String.valueOf(usuario.getNombre()));
                id_telefono.setText(String.valueOf(usuario.getTelefono()));
                id_tipo.getSelectionModel().select(usuario.getTipo_usuario());
            });

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar datos del usuario: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void limpiarFormulario() {
        id_nombre.clear();
        id_telefono.clear();
        id_tipo.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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


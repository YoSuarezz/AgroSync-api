package com.sedikev.infrastructure.rest.controller.lote;

import com.sedikev.infrastructure.rest.advice.NavigationService;
import com.sedikev.domain.service.LoteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CreateLoteController {

    // Servicios
    @Autowired
    private NavigationService navigationService;

    @Autowired
    private LoteService loteService;

    // Campos del formulario
    @FXML private TextField id_contramarca;
    @FXML private TextField id_peso;
    @FXML private TextField id_precio_kilo;
    @FXML private TextField id_proveedor;
    @FXML private TextField id_sexo;

    // Botones de acciones
    @FXML private Button id_addAnimal;
    @FXML private Button id_createLote;

    // Botones de navegación
    @FXML private Button id_registerLote;
    @FXML private Button id_registerSale;
    @FXML private Button id_registerUser;
    @FXML private Button id_viewClient;
    @FXML private Button id_viewLote;
    @FXML private Button id_viewSale;
    @FXML private Button id_viewSupplier;
    @FXML private Button id_viewUser;

    // Métodos de navegación
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

    // Métodos específicos de creación de lotes
    @FXML
    void addAnimal(ActionEvent event) {
        try {
            validarCamposAnimal();


            mostrarAlerta("Éxito", "Animal agregado correctamente", AlertType.INFORMATION);
            limpiarCamposAnimal();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Peso y precio deben ser números válidos", AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage(), AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo agregar el animal: " + e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    void createLote(ActionEvent event) {
        try {
            validarCampoLote();

            mostrarAlerta("Éxito", "Lote creado correctamente", AlertType.INFORMATION);

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage(), AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear el lote: " + e.getMessage(), AlertType.ERROR);
        }
    }

    // Métodos auxiliares
    private void validarCamposAnimal() {
        if (id_sexo.getText().isEmpty() ||
                id_peso.getText().isEmpty() ||
                id_precio_kilo.getText().isEmpty() ||
                id_proveedor.getText().isEmpty()) {
            throw new IllegalArgumentException("Todos los campos del animal son obligatorios");
        }
    }

    private void validarCampoLote() {
        if (id_contramarca.getText().isEmpty()) {
            throw new IllegalArgumentException("La contramarca es obligatoria");
        }
    }

    private void limpiarCamposAnimal() {
        id_sexo.clear();
        id_peso.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
package com.sedikev;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component // Añade esta anotación
public class JavaFxController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Lógica de inicialización
    }

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
    private void goRegisterLote() {
        try {
            // Cargar la vista de registro de lotes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loteRegister.fxml"));
            Parent root = loader.load();

            // Obtener la escena actual desde el botón
            Stage stage = (Stage) id_registerLote.getScene().getWindow();

            // Configurar la nueva escena
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar el error adecuadamente
            showErrorAlert("No se pudo cargar la vista de registro de lotes");
        }
    }

    @FXML
    void goRegisterSale(ActionEvent event) {

    }

    @FXML
    void goRegisterUser(ActionEvent event) {

    }

    @FXML
    void goViewClient(ActionEvent event) {

    }

    @FXML
    void goViewLote(ActionEvent event) {

    }

    @FXML
    void goViewSale(ActionEvent event) {

    }

    @FXML
    void goViewSupplier(ActionEvent event) {

    }

    @FXML
    void goViewUser(ActionEvent event) {

    }

    private void showErrorAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

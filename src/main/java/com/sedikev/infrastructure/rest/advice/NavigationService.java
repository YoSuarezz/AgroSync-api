package com.sedikev.infrastructure.rest.advice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NavigationService {

    @Autowired
    private ApplicationContext applicationContext;

    public void navigateTo(String fxmlPath, Node node) {
        try {
            // 1. Crear el FXMLLoader
            FXMLLoader loader = new FXMLLoader();

            // 2. Configurar la ubicación del FXML
            loader.setLocation(getClass().getResource(fxmlPath));

            // 3. Configurar Spring como factory de controladores
            loader.setControllerFactory(applicationContext::getBean);

            // 4. Cargar el archivo FXML
            Parent root = loader.load();

            // 5. Obtener el escenario actual
            Stage stage = (Stage) node.getScene().getWindow();

            // 6. Configurar la nueva escena
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error al cargar la vista: " + fxmlPath);
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

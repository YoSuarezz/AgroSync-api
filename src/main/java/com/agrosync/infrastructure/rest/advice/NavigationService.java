package com.agrosync.infrastructure.rest.advice;

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
import java.util.Map;

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

    public void navigateWithParameters(String fxmlPath, Node node, Map<String, Object> parameters) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(applicationContext::getBean);

            Parent root = loader.load();

            // Obtener el controlador y pasar los parámetros
            Object controller = loader.getController();
            if (controller instanceof ParameterReceiver) {
                ((ParameterReceiver) controller).setParameters(parameters);
            }

            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Error al cargar la vista con parámetros: " + fxmlPath);
        }
    }

    public Map<String, Object> getParameters(Node node) {
        Scene scene = node.getScene();
        if (scene != null && scene.getWindow() instanceof Stage) {
            Stage stage = (Stage) scene.getWindow();
            if (stage.getScene() != null && stage.getScene().getRoot() != null) {
                Object userData = stage.getScene().getRoot().getUserData();
                if (userData instanceof Map) {
                    return (Map<String, Object>) userData;
                }
            }
        }
        return null;
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

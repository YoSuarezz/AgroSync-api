package com.sedikev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent root;

    public static void main(String[] args) {
        // Este método lanza la aplicación JavaFX
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        // Aquí iniciamos Spring Boot
        springContext = SpringApplication.run(SedikevApplication.class);

        // Cargamos el FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        loader.setControllerFactory(springContext::getBean);
        root = loader.load();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sedikev App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Cerramos el contexto de Spring al salir
        springContext.close();
    }
}

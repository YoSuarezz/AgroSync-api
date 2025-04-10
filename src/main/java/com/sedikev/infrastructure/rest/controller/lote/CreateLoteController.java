package com.sedikev.infrastructure.rest.controller.lote;

import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.model.UsuarioDomain;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CreateLoteController {

    // Servicios
    @Autowired
    private NavigationService navigationService;

    @Autowired
    private LoteService loteService;

    // Lista temporal de animales
    private final List<AnimalDomain> animalesEnLote = new ArrayList<>();
    private int slotCounter = 1;

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

            AnimalDomain animal = new AnimalDomain();
            animal.setPeso(new BigDecimal(id_peso.getText()));
            animal.setSexo(id_sexo.getText().trim().toLowerCase());
            animal.setNum_lote(slotCounter);

            animalesEnLote.add(animal);
            slotCounter++;

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

            if (animalesEnLote.isEmpty()) {
                throw new IllegalArgumentException("Debes agregar al menos un animal al lote");
            }
            UsuarioDomain usuario = new UsuarioDomain();
            LoteDomain lote = new LoteDomain();
            lote.setContramarca(Integer.parseInt(id_contramarca.getText()));
            lote.setPrecio_kilo(new BigDecimal(id_precio_kilo.getText()));
            usuario.setNombre(id_proveedor.getText().trim());
            lote.setAnimales(animalesEnLote);
            lote.setUsuario(usuario);

            loteService.save(lote);

            mostrarAlerta("Éxito", "Lote creado correctamente", AlertType.INFORMATION);

            animalesEnLote.clear();
            slotCounter = 1;
            limpiarCamposAnimal();
            id_contramarca.clear();
            id_precio_kilo.clear();
            id_proveedor.clear();
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage(), AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo crear el lote: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void validarCamposAnimal() {
        String pesoTexto = id_peso.getText();
        String sexo = id_sexo.getText();

        if (pesoTexto.isEmpty() || sexo.isEmpty()) {
            throw new IllegalArgumentException("Peso y sexo del animal son obligatorios");
        }

        BigDecimal peso;
        try {
            peso = new BigDecimal(pesoTexto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El peso debe ser un número válido");
        }

        if (peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero");
        }

        if (!sexo.equalsIgnoreCase("macho") && !sexo.equalsIgnoreCase("hembra")) {
            throw new IllegalArgumentException("El sexo debe ser 'macho' o 'hembra'");
        }
    }


    private void validarCampoLote() {
        if (id_contramarca.getText().isEmpty() ||
                id_precio_kilo.getText().isEmpty() ||
                id_proveedor.getText().isEmpty()) {
            throw new IllegalArgumentException("Contramarca, precio por kilo y proveedor son obligatorios");
        }

        int contramarca;
        BigDecimal precioKilo;
        try {
            contramarca = Integer.parseInt(id_contramarca.getText());
            precioKilo = new BigDecimal(id_precio_kilo.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Contramarca debe ser número entero y precio debe ser válido");
        }

        if (contramarca <= 0) {
            throw new IllegalArgumentException("La contramarca debe ser mayor que cero");
        }

        if (precioKilo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio por kilo debe ser mayor que cero");
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
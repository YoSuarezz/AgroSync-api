package com.sedikev.infrastructure.rest.controller.venta;

import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class viewVentaController {

    @Autowired
    private NavigationService navigationService;

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

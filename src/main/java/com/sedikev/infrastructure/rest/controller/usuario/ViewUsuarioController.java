package com.sedikev.infrastructure.rest.controller.usuario;

import com.sedikev.application.primaryports.dto.UsuarioDTO;
import com.sedikev.application.primaryports.mapper.UsuarioMapper;
import com.sedikev.application.primaryports.service.UsuarioFacadeImpl;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ViewUsuarioController {

    @Autowired private NavigationService navigationService;
    @Autowired private UsuarioFacadeImpl usuarioFacade;
    @Autowired private UsuarioMapper usuarioMapper;

    @FXML private TextField nombreField;
    @FXML private TextField telefonoField;
    @FXML private ChoiceBox<String> tipoChoiceBox;
    @FXML private Button buscarButton;

    @FXML private TableView<UsuarioDTO> usuarioTableView;
    @FXML private TableColumn<UsuarioDTO, String> nombreColumn;
    @FXML private TableColumn<UsuarioDTO, String> tipoColumn;
    @FXML private TableColumn<UsuarioDTO, String> telefonoColumn;
    @FXML private TableColumn<UsuarioDTO, Void> editarColumn;

    @FXML
    private void initialize() {
        // 1) Configuro columnas
        nombreColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        tipoColumn.setCellValueFactory(c   -> new SimpleStringProperty(c.getValue().getTipo_usuario()));
        telefonoColumn.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getTelefono()));
        editarColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Editar");

            {
                editBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-cursor: hand;");
                editBtn.setOnAction(event -> {
                    UsuarioDTO usuario = getTableView().getItems().get(getIndex());
                    System.out.println("Intentando editar usuario ID: " + usuario.getId());
                    editarUsuario(usuario);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableView().getItems().isEmpty() || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    UsuarioDTO usuario = getTableView().getItems().get(getIndex());
                    editBtn.setDisable(usuario == null || usuario.getId() == null);
                    setGraphic(editBtn);
                }
            }
        });

        // 2) ChoiceBox con “Todos” + roles
        tipoChoiceBox.getItems().addAll("Todos", "Proveedor", "Cliente");
        tipoChoiceBox.setValue("Todos");

        // 3) Cargo todos al inicio
        cargarUsuarios();
    }

    private void editarUsuario(UsuarioDTO usuario) {
        try {
            System.out.println("Preparando para editar lote ID: " + usuario.getId());

            // Verificar existencia del lote en la base de datos
            UsuarioDomain usuarioDomain = usuarioFacade.findById(usuario.getId());
            if (usuarioDomain == null) {
                mostrarAlerta("Error", "El usuario con ID " + usuario.getId() + " no existe", Alert.AlertType.ERROR);
                return;
            }

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("usuarioId", usuarioDomain.getId());
            parameters.put("modoEdicion", true);  // Añadir flag de edición

            System.out.println("Navegando a usuarioRegister con parámetros: " + parameters);

            navigationService.navigateWithParameters("/fxml/usuarioRegister.fxml",
                    usuarioTableView.getScene().getRoot(), parameters);
        } catch (Exception e) {
            System.err.println("Error al editar usuario:");
            e.printStackTrace();
            mostrarAlerta("Error de Sistema",
                    "No se pudo iniciar la edición:\n" +
                            e.getClass().getSimpleName() + ": " + e.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cargarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioFacade.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
        usuarioTableView.getItems().setAll(usuarios);
    }

    @FXML
    private void buscarUsuarios(ActionEvent event) {
        String nombreFiltro   = nombreField.getText().trim().toLowerCase();
        String telefonoFiltro = telefonoField.getText().trim();
        String tipoFiltro     = tipoChoiceBox.getValue().toLowerCase();

        List<UsuarioDTO> filtrados = usuarioFacade.findAll().stream()
                .map(usuarioMapper::toDTO)
                .filter(u -> u.getNombre().toLowerCase().contains(nombreFiltro))
                .filter(u -> telefonoFiltro.isEmpty() ||
                        u.getTelefono().contains(telefonoFiltro))
                .filter(u -> tipoFiltro.equals("todos") ||
                        u.getTipo_usuario().toLowerCase().equals(tipoFiltro))
                .collect(Collectors.toList());

        usuarioTableView.getItems().setAll(filtrados);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ——— Métodos de navegación (igual que antes) ———
    @FXML private void goRegisterLote(ActionEvent e) { navigationService.navigateTo("/fxml/loteRegister.fxml", (Node)e.getSource()); }
    @FXML private void goViewLote   (ActionEvent e) { navigationService.navigateTo("/fxml/viewLote.fxml",   (Node)e.getSource()); }
    @FXML private void goRegisterSale(ActionEvent e){ navigationService.navigateTo("/fxml/ventaRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewSale   (ActionEvent e) { navigationService.navigateTo("/fxml/viewVenta.fxml", (Node)e.getSource()); }
    @FXML private void goRegisterUser(ActionEvent e){ navigationService.navigateTo("/fxml/usuarioRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewUser   (ActionEvent e) { navigationService.navigateTo("/fxml/viewUsuario.fxml",(Node)e.getSource()); }
    @FXML private void goViewClient (ActionEvent e) { navigationService.navigateTo("/fxml/viewClienteCartera.fxml",(Node)e.getSource()); }
    @FXML private void goViewSupplier(ActionEvent e){ navigationService.navigateTo("/fxml/viewProveedorCartera.fxml",(Node)e.getSource()); }
}
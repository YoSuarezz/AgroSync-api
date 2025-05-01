package com.sedikev.infrastructure.rest.controller.cartera;

import com.sedikev.application.dto.CarteraDTO;
import com.sedikev.application.dto.LoteDTO;
import com.sedikev.application.mapper.CarteraMapper;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.application.service.CarteraFacadeImpl;
import com.sedikev.application.service.LoteFacadeImpl;
import com.sedikev.application.service.UsuarioFacadeImpl;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class viewProveedorController {

    @Autowired private NavigationService navigationService;
    @Autowired private UsuarioFacadeImpl usuarioFacade;
    @Autowired private UsuarioMapper usuarioMapper;
    @Autowired private CarteraFacadeImpl carteraFacade;
    @Autowired private CarteraMapper carteraMapper;
    @Autowired private LoteFacadeImpl loteFacade;
    @Autowired private LoteMapper loteMapper;

    @FXML private TextField nombreField;
    @FXML private TextField telefonoField;
    @FXML private Button buscarButton;
    @FXML private Button regresarButton;

    @FXML private TableView<CarteraDTO> proveedoresTableView;
    @FXML private TableColumn<CarteraDTO, String> provNombreColumn;
    @FXML private TableColumn<CarteraDTO, BigDecimal> provSaldoColumn;

    @FXML private TableView<LoteDTO> lotesTableView;
    @FXML private TableColumn<LoteDTO, Long> loteIdColumn;
    @FXML private TableColumn<LoteDTO, BigDecimal> loteValorColumn;
    @FXML private TableColumn<LoteDTO, String> loteFechaColumn;

    private List<CarteraDTO> allProveedores;

    @FXML
    private void initialize() {
        // Columnas proveedores
        provNombreColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsuario().getNombre()));
        provSaldoColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getSaldo()));
        provSaldoColumn.setCellFactory(col -> new TableCell<>() {
            @Override protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null); setStyle("");
                } else {
                    setText(item.toString());
                    // saldo negativo (les debemos) en rojo, positivo (ellos nos deben) verde
                    if (item.compareTo(BigDecimal.ZERO) < 0) setStyle("-fx-text-fill: red;");
                    else if (item.compareTo(BigDecimal.ZERO) > 0) setStyle("-fx-text-fill: #27ae60;");
                    else setStyle("");
                }
            }
        });
        buscarButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");
        regresarButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        // Columnas lotes
        loteIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loteValorColumn.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        loteFechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        // Vista inicial
        lotesTableView.setVisible(false);
        regresarButton.setVisible(false);

        cargarProveedores();

        // click fila proveedor → mostrar lotes
        proveedoresTableView.setRowFactory(tv -> {
            TableRow<CarteraDTO> row = new TableRow<>();
            row.setOnMouseClicked(evt -> {
                if (!row.isEmpty() && evt.getClickCount() == 1) {
                    mostrarLotes(row.getItem());
                }
            });
            return row;
        });
    }

    private void cargarProveedores() {
        Map<Long, CarteraDTO> map = carteraFacade.findAll().stream()
                .map(carteraMapper::toDTO)
                .collect(Collectors.toMap(c -> c.getUsuario().getId(), c -> c));

        allProveedores = usuarioFacade.findAll().stream()
                .filter(u -> u.getTipo_usuario().equalsIgnoreCase("proveedor"))
                .map(u -> {
                    CarteraDTO dto = map.get(u.getId());
                    if (dto == null) {
                        dto = new CarteraDTO();
                        dto.setUsuario(usuarioMapper.toDTO(u));
                        dto.setSaldo(BigDecimal.ZERO);
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        proveedoresTableView.getItems().setAll(allProveedores);
    }

    @FXML
    private void buscarProveedores(ActionEvent e) {
        String nombreFiltro   = nombreField.getText().trim().toLowerCase();
               String telefonoFiltro = telefonoField.getText().trim();
        List<CarteraDTO> filtrados = allProveedores.stream()
                .filter(c -> c.getUsuario().getNombre()
                        .toLowerCase()
                        .contains(nombreFiltro))
                           .filter(c -> telefonoFiltro.isEmpty()
                                       || c.getUsuario().getTelefono().contains(telefonoFiltro))
                .collect(Collectors.toList());
        proveedoresTableView.getItems().setAll(filtrados);
    }

    private void mostrarLotes(CarteraDTO proveedor) {
        List<LoteDTO> lotes = loteFacade.findByProveedorId(proveedor.getUsuario().getId()).stream()
                .map(loteMapper::toDTO)
                .collect(Collectors.toList());
        lotesTableView.getItems().setAll(lotes);

        // ocultar proveedores
        proveedoresTableView.setVisible(false);
        nombreField.setVisible(false);
        buscarButton.setVisible(false);
        telefonoField.setVisible(false);

        // mostrar lotes + regresar
        lotesTableView.setVisible(true);
        regresarButton.setVisible(true);
    }

    @FXML
    private void regresar(ActionEvent e) {
        // restaurar vista proveedores
        proveedoresTableView.setVisible(true);
        nombreField.setVisible(true);
        buscarButton.setVisible(true);
        telefonoField.setVisible(true);

        lotesTableView.setVisible(false);
        regresarButton.setVisible(false);
    }

    // navegación lateral...
    @FXML private void goRegisterLote(ActionEvent e){ navigationService.navigateTo("/fxml/loteRegister.fxml", (Node)e.getSource()); }
    @FXML private void goViewLote   (ActionEvent e){ navigationService.navigateTo("/fxml/viewLote.fxml",   (Node)e.getSource()); }
    @FXML private void goRegisterSale(ActionEvent e){ navigationService.navigateTo("/fxml/ventaRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewSale   (ActionEvent e){ navigationService.navigateTo("/fxml/viewVenta.fxml", (Node)e.getSource()); }
    @FXML private void goRegisterUser(ActionEvent e){ navigationService.navigateTo("/fxml/usuarioRegister.fxml",(Node)e.getSource()); }
    @FXML private void goViewUser   (ActionEvent e){ navigationService.navigateTo("/fxml/viewUsuario.fxml",(Node)e.getSource()); }
    @FXML private void goViewClient (ActionEvent e){ navigationService.navigateTo("/fxml/viewClienteCartera.fxml",(Node)e.getSource()); }
    @FXML private void goViewSupplier(ActionEvent e){ navigationService.navigateTo("/fxml/viewProveedorCartera.fxml",(Node)e.getSource()); }
}

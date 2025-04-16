package com.sedikev.infrastructure.rest.controller.lote;

import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.crosscutting.exception.custom.BusinessSedikevException;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.model.UsuarioDomain;
import com.sedikev.domain.service.AnimalService;
import com.sedikev.domain.service.LoteService;
import com.sedikev.domain.service.UsuarioService;
import com.sedikev.infrastructure.adapter.entity.LoteEntity;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CreateLoteController {

    // Servicios inyectados
    @Autowired private NavigationService navigationService;
    @Autowired private LoteService loteService; // Se inyecta la fachada LoteFacadeImpl
    @Autowired private AnimalService animalService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioMapper usuarioMapper;

    // Lista de animales del lote
    private final List<AnimalDomain> animalesEnLote = new ArrayList<>();
    private final ObservableList<AnimalDomain> animalesObservableList = FXCollections.observableArrayList();
    private int slotCounter = 1;

    // Controles de la vista
    @FXML private TextField id_contramarca;
    @FXML private TextField id_peso;
    @FXML private TextField id_precio_kilo;
    @FXML private ComboBox<UsuarioDTO> comboProveedor;
    @FXML private ComboBox<String> comboSexo;
    @FXML private TableView<AnimalDomain> id_tableViewAnimales;
    @FXML private TableColumn<AnimalDomain, Integer> slotColumn;
    @FXML private TableColumn<AnimalDomain, BigDecimal> pesoColumn;
    @FXML private TableColumn<AnimalDomain, String> sexoColumn;

    // Navegación
    @FXML private Button id_registerLote, id_registerSale, id_registerUser;
    @FXML private Button id_viewClient, id_viewLote, id_viewSale, id_viewSupplier, id_viewUser;

    @FXML
    public void initialize() {
        cargarProveedores();

        // Configurar columnas de tabla
        slotColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getNum_lote()).asObject());
        pesoColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPeso()));
        sexoColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSexo()));

        comboSexo.setItems(FXCollections.observableArrayList("Macho", "Hembra"));
        id_tableViewAnimales.setItems(animalesObservableList);
    }

    private void cargarProveedores() {
        List<UsuarioDTO> proveedores = usuarioService.findAll().stream()
                .map(usuarioMapper::toDTO)
                .filter(u -> "proveedor".equalsIgnoreCase(u.getTipo_usuario()))
                .collect(Collectors.toList());

        comboProveedor.setItems(FXCollections.observableArrayList(proveedores));
        comboProveedor.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(UsuarioDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        comboProveedor.setButtonCell(new ListCell<>() {
            @Override protected void updateItem(UsuarioDTO item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
    }

    @FXML
    void addAnimal(ActionEvent event) {
        try {
            validarCamposAnimal();

            AnimalDomain animal = new AnimalDomain();
            animal.setPeso(new BigDecimal(id_peso.getText()));
            animal.setSexo(comboSexo.getValue().toLowerCase());
            animal.setNum_lote(slotCounter++);

            animalesEnLote.add(animal);
            animalesObservableList.add(animal);

            mostrarAlerta("Éxito", "El Animal fue agregado exitosamente", AlertType.INFORMATION);
            limpiarCamposAnimal();

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    void createLote(ActionEvent event) {
        try {
            validarCampoLote();
            LoteDomain lote = new LoteDomain();
            lote.setContramarca(Integer.parseInt(id_contramarca.getText()));
            lote.setPrecio_kilo(new BigDecimal(id_precio_kilo.getText()));
            UsuarioDTO selectedUsuarioDTO = comboProveedor.getValue();
            UsuarioDomain usuarioDomain = usuarioMapper.toDomain(selectedUsuarioDTO);
            lote.setUsuario(usuarioDomain);
            System.out.println(lote);

            LoteDomain loteSaved = loteService.save(lote);

            for (AnimalDomain animal : animalesEnLote) {
                animal.setIdLote(loteSaved.getId()); // Asignar el ID del lote
                animalService.save(animal); // Guardar cada animal
            }



            mostrarAlerta("Éxito", "Lote creado correctamente", AlertType.INFORMATION);
            limpiarFormulario();

        } catch (IllegalArgumentException | BusinessSedikevException e) {
            mostrarAlerta("Error", e.getMessage(), AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error inesperado: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void validarCamposAnimal() {
        if (id_peso.getText().isEmpty() || comboSexo.getValue() == null) {
            throw new IllegalArgumentException("Peso y sexo del animal son obligatorios");
        }

        BigDecimal peso = new BigDecimal(id_peso.getText());
        if (peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero");
        }

        String sexo = comboSexo.getValue();
        if (!sexo.equalsIgnoreCase("macho") && !sexo.equalsIgnoreCase("hembra")) {
            throw new IllegalArgumentException("El sexo debe ser 'Macho' o 'Hembra'");
        }
    }

    private void validarCampoLote() {
        if (id_contramarca.getText().isEmpty() || id_precio_kilo.getText().isEmpty() || comboProveedor.getValue() == null) {
            throw new IllegalArgumentException("Contramarca, precio por kilo y proveedor son obligatorios");
        }

        int contramarca = Integer.parseInt(id_contramarca.getText());
        BigDecimal precioKilo = new BigDecimal(id_precio_kilo.getText());

        if (contramarca <= 0) {
            throw new IllegalArgumentException("La contramarca debe ser mayor que cero");
        }

        if (precioKilo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio por kilo debe ser mayor que cero");
        }

        if (animalesEnLote.isEmpty()) {
            throw new IllegalArgumentException("Debes agregar al menos un animal al lote");
        }
    }

    private void limpiarCamposAnimal() {
        id_peso.clear();
        comboSexo.setValue(null);
    }

    private void limpiarFormulario() {
        animalesEnLote.clear();
        animalesObservableList.clear();
        slotCounter = 1;
        id_contramarca.clear();
        id_precio_kilo.clear();
        comboProveedor.setValue(null);
        limpiarCamposAnimal();
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Navegación
    @FXML void goRegisterLote(ActionEvent e) { navigationService.navigateTo("/fxml/loteRegister.fxml", (Node) e.getSource()); }
    @FXML void goRegisterSale(ActionEvent e) { navigationService.navigateTo("/fxml/ventaRegister.fxml", (Node) e.getSource()); }
    @FXML void goRegisterUser(ActionEvent e) { navigationService.navigateTo("/fxml/usuarioRegister.fxml", (Node) e.getSource()); }
    @FXML void goViewClient(ActionEvent e) { navigationService.navigateTo("/fxml/viewClienteCartera.fxml", (Node) e.getSource()); }
    @FXML void goViewLote(ActionEvent e) { navigationService.navigateTo("/fxml/viewLote.fxml", (Node) e.getSource()); }
    @FXML void goViewSale(ActionEvent e) { navigationService.navigateTo("/fxml/viewVenta.fxml", (Node) e.getSource()); }
    @FXML void goViewSupplier(ActionEvent e) { navigationService.navigateTo("/fxml/viewProveedorCartera.fxml", (Node) e.getSource()); }
    @FXML void goViewUser(ActionEvent e) { navigationService.navigateTo("/fxml/viewUsuario.fxml", (Node) e.getSource()); }
}
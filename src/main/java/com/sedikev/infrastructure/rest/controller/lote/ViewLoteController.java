package com.sedikev.infrastructure.rest.controller.lote;

import com.sedikev.application.dto.AnimalDTO;
import com.sedikev.application.dto.LoteDTO;
import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.mapper.AnimalMapper;
import com.sedikev.application.mapper.LoteMapper;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.domain.service.AnimalService;
import com.sedikev.domain.service.LoteService;
import com.sedikev.domain.service.UsuarioService;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ViewLoteController {

    @Autowired
    private NavigationService navigationService;
    @Autowired
    private LoteService loteService;
    @Autowired
    private LoteMapper loteMapper;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private AnimalMapper animalMapper;

    @FXML
    private Button id_registerLote;
    @FXML
    private Button id_viewLote;
    @FXML
    private Button id_searchLote;
    @FXML
    private Button id_regresarLote;
    @FXML
    private ComboBox<UsuarioDTO> comboProveedor;
    @FXML
    private TextField contramarcaField;
    @FXML
    private TextField semanaField;
    @FXML
    private TextField añoField;
    @FXML
    private DatePicker fechaField;

    @FXML
    private TableView<LoteDTO> loteTableView;

    @FXML
    private Label precioKiloLabel;
    @FXML
    private TableColumn<LoteDTO, String> proveedorColumn;
    @FXML
    private TableColumn<LoteDTO, Integer> contramarcaColumn;
    @FXML
    private TableColumn<LoteDTO, String> precioColumn;
    @FXML
    private TableColumn<LoteDTO, LocalDate> fechaColumn;
    @FXML
    private TableView<AnimalDTO> animalesTableView;

    @FXML
    private Label precioTotalLabel;
    @FXML
    private Label kilajeTotalLabel;
    @FXML
    private Label proveedorLabel;
    @FXML
    private TableColumn<AnimalDTO, Integer> animalPosColumn;
    @FXML
    private TableColumn<AnimalDTO, Integer> contramarcaAnimalColumn;
    @FXML
    private TableColumn<AnimalDTO, Integer> semanaColumn;
    @FXML
    private TableColumn<AnimalDTO, BigDecimal> pesoColumn;
    @FXML
    private TableColumn<AnimalDTO, String> sexoColumn;

    private ObservableList<LoteDTO> loteObservableList = FXCollections.observableArrayList();

    // Instanciamos DecimalFormat para usar en los valores
    private DecimalFormat format = new DecimalFormat("#,###.##");

    @FXML
    public void initialize() {
        cargarProveedores();

        UsuarioDTO ninguno = new UsuarioDTO();
        ninguno.setNombre("Ninguno");
        comboProveedor.getItems().add(0, ninguno); // Añadimos la opción al principio del ComboBox

        // Configurar columnas de la tabla de lotes
        proveedorColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUsuario().getNombre()));
        contramarcaColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getContramarca()));
        precioColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPrecio_kilo()).asString());
        fechaColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getFecha()));
        // Configurar las columnas de la tabla de Animales
        animalPosColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(animalesTableView.getItems().indexOf(cell.getValue()) + 1));
        contramarcaAnimalColumn.setCellValueFactory(cell -> {
            LoteDTO lote = loteMapper.toDTO(loteService.findById(cell.getValue().getIdLote()));
            return new SimpleObjectProperty<>(lote.getContramarca());
        });
        semanaColumn.setCellValueFactory(cell -> {
            // Obtenemos el lote asociado al animal usando su idLote
            LoteDTO lote = loteMapper.toDTO(loteService.findById(cell.getValue().getIdLote())); // Convertimos LoteDomain a LoteDTO
            int semana = 0;
            if (lote != null && lote.getFecha() != null) {
                semana = lote.getFecha().get(WeekFields.ISO.weekOfYear());
            }
            return new SimpleObjectProperty<>(semana); // Semana del lote
        });
        pesoColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPeso()));
        sexoColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSexo()));

        // Inicializar la tabla con todos los lotes
        cargarLotes();
    }

    @FXML
    private void cargarLotes() {
        // Obtener todos los lotes
        List<LoteDTO> lotes = loteService.findAll().stream()
                .map(loteMapper::toDTO)
                .collect(Collectors.toList());

        loteObservableList.setAll(lotes);
        loteTableView.setItems(FXCollections.observableArrayList(lotes));
    }

    @FXML
    private void searchLotes() {
        String contramarca = contramarcaField.getText();
        String semana = semanaField.getText();
        String año = añoField.getText();
        LocalDate fecha = fechaField.getValue();
        UsuarioDTO proveedorSeleccionado = comboProveedor.getValue();

        // Si se selecciona "Ninguno", lo consideramos como null
        final UsuarioDTO proveedor = (proveedorSeleccionado != null && "Ninguno".equals(proveedorSeleccionado.getNombre()))
                ? null
                : proveedorSeleccionado;

        // Filtrar los lotes con los parámetros de búsqueda
        List<LoteDTO> lotesFiltrados = loteObservableList.stream()
                .filter(lote -> (contramarca.isEmpty() || lote.getContramarca().toString().contains(contramarca)))
                .filter(lote -> (semana.isEmpty() || (lote.getFecha() != null && lote.getFecha().get(WeekFields.ISO.weekOfYear()) == Integer.parseInt(semana))))
                .filter(lote -> (año.isEmpty() || (lote.getFecha() != null && lote.getFecha().getYear() == Integer.parseInt(año))))
                .filter(lote -> (fecha == null || (lote.getFecha() != null && lote.getFecha().equals(fecha))))
                .filter(lote -> (proveedor == null || lote.getUsuario().getNombre().equalsIgnoreCase(proveedor.getNombre())))
                .collect(Collectors.toList());

        loteTableView.setItems(FXCollections.observableArrayList(lotesFiltrados));
    }

    @FXML
    private void onLoteClicked() {
        LoteDTO selectedLote = loteTableView.getSelectionModel().getSelectedItem();
        if (selectedLote != null) {
            // Llamar al servicio para obtener los animales del lote seleccionado
            List<AnimalDTO> animales = animalService.findByLote(selectedLote.getId()).stream()
                    .map(animalMapper::toDTO)
                    .collect(Collectors.toList());

            // Mostrar los animales en la tabla
            animalesTableView.setItems(FXCollections.observableArrayList(animales));

            // Mostrar la información fija del lote
            proveedorLabel.setText("Proveedor: " + selectedLote.getUsuario().getNombre());
            precioKiloLabel.setText("Precio por Kilo: $" + format.format(selectedLote.getPrecio_kilo()));

            // Calcular el kilaje total y el precio total
            BigDecimal kilajeTotal = animales.stream()
                    .map(AnimalDTO::getPeso)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal precioTotal = kilajeTotal.multiply(selectedLote.getPrecio_kilo());

            // Actualizar los Label de Kilaje Total y Precio Total
            kilajeTotalLabel.setText("Kilaje Total: " + format.format(kilajeTotal));
            precioTotalLabel.setText("Precio Total: $" + format.format(precioTotal));

            // Hacer visibles los Label de Proveedor, Precio por Kilo, Kilaje Total y Precio Total
            proveedorLabel.setVisible(true);
            precioKiloLabel.setVisible(true);
            kilajeTotalLabel.setVisible(true);
            precioTotalLabel.setVisible(true);

            // Cambiar visibilidad: ocultar tabla de lotes y mostrar tabla de animales
            loteTableView.setVisible(false);
            animalesTableView.setVisible(true);

            // Cambiar la visibilidad de los botones
            id_searchLote.setVisible(false);  // Ocultar el botón "Buscar"
            id_regresarLote.setVisible(true); // Mostrar el botón "Regresar"
        }
    }

    @FXML
    private void regresarAViewLotes(ActionEvent event) {
        // Volver a mostrar la tabla de lotes y ocultar la de animales
        loteTableView.setVisible(true);
        animalesTableView.setVisible(false);

        // Ocultar los detalles del lote
        proveedorLabel.setVisible(false);  // Ocultar el Label del Proveedor
        precioKiloLabel.setVisible(false); // Ocultar el Label del Precio por Kilo
        kilajeTotalLabel.setVisible(false); // Ocultar el Label del Kilaje Total
        precioTotalLabel.setVisible(false); // Ocultar el Label del Precio Total

        // Cambiar la visibilidad de los botones
        id_searchLote.setVisible(true);   // Mostrar el botón "Buscar"
        id_regresarLote.setVisible(false);  // Ocultar el botón "Regresar"
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

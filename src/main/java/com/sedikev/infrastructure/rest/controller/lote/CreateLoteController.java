package com.sedikev.infrastructure.rest.controller.lote;

import com.sedikev.application.dto.UsuarioDTO;
import com.sedikev.application.mapper.UsuarioMapper;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.model.LoteDomain;
import com.sedikev.domain.service.AnimalService;
import com.sedikev.domain.service.LoteService;
import com.sedikev.domain.service.UsuarioService;
import com.sedikev.infrastructure.rest.advice.NavigationService;
import com.sedikev.infrastructure.rest.advice.ParameterReceiver;
import javafx.application.Platform;
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
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CreateLoteController implements ParameterReceiver {

    // Servicios inyectados
    @Autowired private NavigationService navigationService;
    @Autowired private LoteService loteService;
    @Autowired private AnimalService animalService;
    @Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioMapper usuarioMapper;

    // Lista de animales del lote
    private List<AnimalDomain> animalesEnLote = new ArrayList<>();
    private ObservableList<AnimalDomain> animalesObservableList = FXCollections.observableArrayList();
    private int slotCounter = 1;
    private Long loteId = null;

    // Controles de la vista
    @FXML private TextField id_contramarca;
    @FXML private TextField id_peso;
    @FXML private TextField id_precio_kilo_animal;
    @FXML private TextField id_slot;

    @FXML private ComboBox<UsuarioDTO> comboProveedor;
    @FXML private ComboBox<String> comboSexo;
    @FXML private TableView<AnimalDomain> id_tableViewAnimales;
    @FXML private TableColumn<AnimalDomain, Integer> slotColumn;
    @FXML private TableColumn<AnimalDomain, BigDecimal> pesoColumn;
    @FXML private TableColumn<AnimalDomain, String> sexoColumn;
    @FXML private TableColumn<AnimalDomain, Integer> contramarcaColumn;
    @FXML private TableColumn<AnimalDomain, Integer> semanaColumn;
    @FXML private TableColumn<AnimalDomain, Void> eliminarColumn;
    @FXML private TableColumn<AnimalDomain, BigDecimal> precioKiloColumn;
    @FXML private Button id_regresarLote;

    @FXML private Label precioTotalLabel;  // Mostrar precio total del lote

    private BigDecimal precioTotal = BigDecimal.ZERO;

    DecimalFormat formato = new DecimalFormat("#,##0");// Variable para almacenar el precio total


    // Navegación
    @FXML private Button id_registerLote, id_registerSale, id_registerUser;
    @FXML private Button id_viewClient, id_viewLote, id_viewSale, id_viewSupplier, id_viewUser;


    @FXML
    public void initialize() {
        cargarProveedores();

        // Verificar si estamos en modo edición (cuando se navega desde ViewLoteController)
        Node node = id_registerLote; // O cualquier nodo de la escena
        Map<String, Object> parameters = navigationService.getParameters(node);

        animalesEnLote.clear();
        animalesObservableList.clear();
        slotCounter = 1;
        loteId = null;

        if (parameters != null && parameters.containsKey("loteId")) {
            Long loteId = (Long) parameters.get("loteId");
            cargarDatosLote(loteId); // Méto-do que carga los datos del lote y sus animales
        } else {
            id_slot.setText(String.valueOf(slotCounter));
        }

        // Configurar columnas de tabla
        slotColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getSlot()).asObject());
        pesoColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPeso()));
        sexoColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSexo()));
        precioKiloColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPrecioKiloCompra()));
        semanaColumn.setCellValueFactory(cell -> {
            // Usar la fecha del lote que está siendo editado/creado
            LocalDate fechaLote = loteId != null ?
                    loteService.findById(loteId).getFecha() :
                    LocalDate.now();

            int semana = fechaLote.get(WeekFields.ISO.weekOfYear());
            return new SimpleIntegerProperty(semana).asObject();
        });
        contramarcaColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(
                id_contramarca.getText().isEmpty() ? 0 : Integer.parseInt(id_contramarca.getText())
        ).asObject());
        eliminarColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteBtn = new Button("X");

            {
                deleteBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                deleteBtn.setOnAction(event -> {
                    AnimalDomain animal = getTableView().getItems().get(getIndex());
                    eliminarAnimal(animal);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });

        comboSexo.setItems(FXCollections.observableArrayList("Macho", "Hembra"));
        id_tableViewAnimales.setItems(animalesObservableList);

        id_contramarca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                id_tableViewAnimales.refresh();
            }
        });
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        if (parameters != null && parameters.containsKey("loteId")) {
            loteId = (Long) parameters.get("loteId");
            cargarDatosLote(loteId);
        }
    }

    private void cargarDatosLote(Long loteId) {
        if (loteId == null) return;

        try {
            id_regresarLote.setVisible(true);
            LoteDomain lote = loteService.findById(loteId);
            if (lote == null) {
                mostrarAlerta("Error", "No se encontró el lote especificado", AlertType.ERROR);
                return;
            }

            // Cargar campos del formulario
            Platform.runLater(() -> {
                id_contramarca.setText(String.valueOf(lote.getContramarca()));

                // Seleccionar proveedor en ComboBox
                if (lote.getUsuario() != null) {
                    UsuarioDTO usuarioDTO = usuarioMapper.toDTO(lote.getUsuario());
                    comboProveedor.getSelectionModel().select(usuarioDTO);
                }
            });

            // Cargar animales del lote
            List<AnimalDomain> animales = animalService.findByLote(loteId);
            Platform.runLater(() -> {
                animalesEnLote.clear();
                animalesObservableList.clear();

                animalesEnLote.addAll(animales);
                animalesObservableList.addAll(animales);

                recalcularPrecioTotalLote();
                precioTotalLabel.setText(String.valueOf(precioTotal));


                // Actualizar contador de slots
                slotCounter = animales.stream()
                        .mapToInt(AnimalDomain::getSlot)
                        .max()
                        .orElse(0) + 1;
                id_slot.setText(String.valueOf(slotCounter));
                id_tableViewAnimales.refresh();
            });

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar datos del lote: " + e.getMessage(), AlertType.ERROR);
        }
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
    private void addAnimal(ActionEvent event) {
        try {
            validarCamposAnimal();

            AnimalDomain animal = new AnimalDomain();
            animal.setIdLote(loteId);
            animal.setPeso(new BigDecimal(id_peso.getText()));
            animal.setSexo(comboSexo.getValue().toLowerCase());
            animal.setSlot(Integer.valueOf(id_slot.getText()));
            animal.setPrecioKiloCompra(new BigDecimal(id_precio_kilo_animal.getText()));

            // Importante: Asegúrate de que el animal NO tenga una venta asociada.
            animal.setIdVenta(null); // Aquí la venta está en null (lo importante).

            // Agregar el animal a la lista
            animalesObservableList.add(animal);

            // Recalcular precio total del lote
            recalcularPrecioTotalLote();
            slotCounter++;

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

            if (animalesObservableList.isEmpty()) {
                mostrarAlerta("Error", "Debe agregar al menos un animal al lote", AlertType.ERROR);
                return;  // No se crea el lote si no hay animales
            }

            // Si hay animales, calculamos el precio total antes de crear el lote
            recalcularPrecioTotalLote();  // Esto recalcula el precio total

            if (loteId != null) {
                // Modo edición
                LoteDomain lote = loteService.findById(loteId);
                lote.setContramarca(Integer.parseInt(id_contramarca.getText()));
                lote.setUsuario(usuarioMapper.toDomain(comboProveedor.getValue()));
                lote.setPrecioTotal(precioTotal);  // Aseguramos que se guarda el precio total calculado

                loteService.update(lote);

                // Actualizar animales
                animalService.deleteByLote(loteId);
                for (AnimalDomain animal : animalesObservableList) {
                    animal.setId(null);  // El ID se genera automáticamente
                    animalService.save(animal);
                }

                mostrarAlerta("Éxito", "Lote actualizado correctamente", AlertType.INFORMATION);
            } else {
                // Modo creación
                LoteDomain lote = new LoteDomain();
                lote.setContramarca(Integer.parseInt(id_contramarca.getText()));
                lote.setFecha(LocalDate.now());
                lote.setUsuario(usuarioMapper.toDomain(comboProveedor.getValue()));
                lote.setPrecioTotal(precioTotal);  // Asignamos el precio total calculado

                // Guarda el lote primero
                LoteDomain loteSaved = loteService.save(lote);  // Lote guardado con éxito

                // Ahora guarda los animales sin la venta asociada
                for (AnimalDomain animal : animalesObservableList) {
                    animal.setIdLote(loteSaved.getId());  // Asociar al lote guardado
                    System.out.println(animal.toString());
                    animalService.save(animal);
                }

                mostrarAlerta("Éxito", "Lote creado correctamente", AlertType.INFORMATION);
            }

            limpiarFormulario();
            navigationService.navigateTo("/fxml/viewLote.fxml", (Node) event.getSource());

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void validarCamposAnimal() {
        if (id_peso.getText().isEmpty() || comboSexo.getValue() == null || id_precio_kilo_animal.getText().isEmpty() || id_slot.getText().isEmpty()) {
            throw new IllegalArgumentException("Slot, peso, precio por kilo y sexo del animal son obligatorios");
        }

        BigDecimal peso = new BigDecimal(id_peso.getText());
        if (peso.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero");
        }

        String sexo = comboSexo.getValue();
        if (!sexo.equalsIgnoreCase("macho") && !sexo.equalsIgnoreCase("hembra")) {
            throw new IllegalArgumentException("El sexo debe ser 'Macho' o 'Hembra'");
        }

        // Validación del slot (nueva)
        int slot = Integer.parseInt(id_slot.getText());
        if (slot <= 0) {
            throw new IllegalArgumentException("El slot debe ser mayor que cero");
        }

        // Verificar si el slot ya existe en la lista
        boolean slotExiste = animalesObservableList.stream()
                .anyMatch(animal -> animal.getSlot() == slot);

        if (slotExiste) {
            throw new IllegalArgumentException("El slot " + slot + " ya está en uso por otro animal");
        }
    }

    private void validarCampoLote() {
        if (id_contramarca.getText().isEmpty() || comboProveedor.getValue() == null) {
            throw new IllegalArgumentException("Contramarca y proveedor son obligatorios");
        }

        int contramarca = Integer.parseInt(id_contramarca.getText());
        if (contramarca <= 0) {
            throw new IllegalArgumentException("La contramarca debe ser mayor que cero");
        }

        if (animalesObservableList.isEmpty()) {
            throw new IllegalArgumentException("Debes agregar al menos un animal al lote");
        }
    }

    private void limpiarCamposAnimal() {
        id_peso.clear();
        comboSexo.setValue(null);
        id_precio_kilo_animal.clear();  // Limpiar el campo de precio por kilo
        id_slot.setText(String.valueOf(slotCounter));
    }

    private void limpiarFormulario() {
        animalesEnLote.clear();
        animalesObservableList.clear();
        slotCounter = 1;
        id_contramarca.clear();
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

    private void eliminarAnimal(AnimalDomain animal) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar");
        confirm.setHeaderText(null);
        confirm.setContentText("¿Estás seguro de eliminar este animal?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Eliminar el animal de la lista
            animalesObservableList.remove(animal);

            // Recalcular el precio total después de la eliminación
            recalcularPrecioTotalLote();

            // Refrescar la tabla para reflejar el cambio
            id_tableViewAnimales.refresh();
        }
    }

    private void recalcularPrecioTotalLote() {
        // Reiniciar el precio total
        precioTotal = BigDecimal.ZERO;

        System.out.println(animalesObservableList);
        // Recalcular el precio total sumando el precio de todos los animales
        for (AnimalDomain animal : animalesObservableList) {
            BigDecimal precioAnimal = animal.getPeso().multiply(animal.getPrecioKiloCompra());
            precioTotal = precioTotal.add(precioAnimal);
        }

        // Asegurarse de que el precio total no sea negativo
        if (precioTotal.compareTo(BigDecimal.ZERO) < 0) {
            precioTotal = BigDecimal.ZERO;
        }
        System.out.println(precioTotal);
        // Actualizar el precio total en el label
        precioTotalLabel.setText(formato.format(precioTotal));  // Usando el formato para mostrar el precio
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
package com.sedikev.infrastructure.rest.controller.venta;

import com.sedikev.application.primaryports.dto.AnimalDTO;
import com.sedikev.application.primaryports.dto.usuarios.UsuarioDTO;
import com.sedikev.application.primaryports.mapper.AnimalMapper;
import com.sedikev.application.primaryports.mapper.UsuarioMapper;
import com.sedikev.domain.model.AnimalDomain;
import com.sedikev.domain.model.VentaDomain;
import com.sedikev.domain.service.AnimalService;
import com.sedikev.domain.service.LoteService;
import com.sedikev.domain.service.UsuarioService;
import com.sedikev.domain.service.VentaService;
import com.sedikev.application.secondaryports.entity.LoteEntity;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CreateVentaController implements ParameterReceiver {

    @Autowired
    private NavigationService navigationService;
    @Autowired
    private VentaService ventaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private LoteService loteService;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private AnimalMapper animalMapper;


    private List<AnimalDomain> animalesEnVenta = new ArrayList<>();
    private ObservableList<AnimalDomain> animalesObservableList = FXCollections.observableArrayList();
    private Long ventaId = null;
    BigDecimal precioTotal = BigDecimal.ZERO;
    DecimalFormat formato = new DecimalFormat("#,##0");// Variable para almacenar el precio total


    @FXML
    private ComboBox<AnimalDTO> comboAnimal;

    @FXML
    private ComboBox<UsuarioDTO> comboCliente;

    @FXML
    private TableColumn<AnimalDomain, Integer> contramarcaColumn;

    @FXML
    private TableColumn<AnimalDomain, Void> eliminarColumn;

    @FXML
    private TextField id_contramarca;

    @FXML
    private TextField id_precio_kilo;

    @FXML
    private Button id_registerLote;

    @FXML
    private Button id_registerSale;

    @FXML
    private Button id_registerUser;

    @FXML
    private TextField id_semana;

    @FXML
    private TableView<AnimalDomain> id_tableViewAnimales;

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
    private TableColumn<AnimalDomain, BigDecimal> pesoColumn;

    @FXML
    private TableColumn<AnimalDomain, BigDecimal> precioxkiloColumn;

    @FXML
    private TableColumn<AnimalDomain, Integer> semanaColumn;

    @FXML
    private TableColumn<AnimalDomain, String> sexoColumn;

    @FXML
    private Button id_regresarVenta;
    @FXML
    private Label precioTotalLabel;




    @FXML
    public void initialize() {
        //cargarClientes();

        // Agregar listeners a los campos de filtro
        id_contramarca.textProperty().addListener((obs, oldVal, newVal) -> actualizarAnimalesDisponibles());
        id_semana.textProperty().addListener((obs, oldVal, newVal) -> actualizarAnimalesDisponibles());

        // Verificar si estamos en modo edición (cuando se navega desde ViewVentaController)
        Node node = id_registerSale; // O cualquier nodo de la escena
        Map<String, Object> parameters = navigationService.getParameters(node);

        animalesEnVenta.clear();
        animalesObservableList.clear();
        ventaId = null;

        if (parameters != null && parameters.containsKey("ventaId")) {
            Long ventaId = (Long) parameters.get("ventaId");
            cargarDatosVenta(ventaId); // Méto-do que carga los datos del lote y sus animales
        }

        // Configurar columnas de tabla
        contramarcaColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(
                id_contramarca.getText().isEmpty() ? 0 : Integer.parseInt(id_contramarca.getText())
        ).asObject());
        semanaColumn.setCellValueFactory(cell -> new SimpleIntegerProperty(
                id_semana.getText().isEmpty() ? 0 : Integer.parseInt(id_semana.getText())
        ).asObject());
        precioxkiloColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPrecioKiloVenta()));
        sexoColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSexo()));
        pesoColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPeso()));
        semanaColumn.setCellValueFactory(cell -> {
            // Usar la fecha del lote que está siendo editado/creado
            LocalDate fechaLote = cell.getValue().getIdLote() != null ?
                    loteService.findById(cell.getValue().getIdLote()).getFecha() :
                    LocalDate.now();

            int semana = fechaLote.get(WeekFields.ISO.weekOfYear());
            return new SimpleIntegerProperty(semana).asObject();
        });
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
        id_tableViewAnimales.setItems(animalesObservableList);

    }

    //private void cargarClientes() {
//        List<UsuarioDTO> clientes = usuarioService.findAll().stream()
//                .map(usuarioMapper::toDTO)
//                .filter(u -> "cliente".equalsIgnoreCase(u.getTipo_usuario()))
//                .collect(Collectors.toList());
//
//        comboCliente.setItems(FXCollections.observableArrayList(clientes));
//        comboCliente.setCellFactory(lv -> new ListCell<>() {
//            @Override protected void updateItem(UsuarioDTO item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(empty || item == null ? null : item.getNombre());
//            }
//        });
//        comboCliente.setButtonCell(new ListCell<>() {
//            @Override protected void updateItem(UsuarioDTO item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(empty || item == null ? null : item.getNombre());
//            }
//        });
//    }

    private void actualizarAnimalesDisponibles() {
        try {
            // 1. Obtener parámetros de búsqueda
            Integer contramarca = id_contramarca.getText().isEmpty() ? null : Integer.parseInt(id_contramarca.getText());
            Integer semana = id_semana.getText().isEmpty() ? null : Integer.parseInt(id_semana.getText());

            // 2. Buscar lote con manejo seguro de Optional
            Optional<LoteEntity> loteOpt = loteService.findByContramarcaAndSemana(contramarca, semana);
            if (!loteOpt.isPresent()) {
                comboAnimal.setItems(FXCollections.emptyObservableList());
                return;
            }

            // 3. Obtener animales del lote
            List<AnimalDomain> animales = animalService.findByLote(loteOpt.get().getId());

            // 4. Configurar cómo se muestran los items en el ComboBox
            comboAnimal.setCellFactory(lv -> new ListCell<AnimalDTO>() {
                @Override
                protected void updateItem(AnimalDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Formato personalizado: "Slot: X - Peso: Y kg"
                        setText(String.format("Slot: %d - Peso: %s kg",
                                item.getSlot(),
                                item.getPeso().setScale(2, RoundingMode.HALF_UP)));
                    }
                }
            });

            // 5. Configurar texto del botón del ComboBox
            comboAnimal.setButtonCell(new ListCell<AnimalDTO>() {
                @Override
                protected void updateItem(AnimalDTO item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("Slot: %d", item.getSlot()));
                    }
                }
            });

            // 6. Convertir y cargar los animales
            List<AnimalDTO> animalesDTO = animales.stream()
                    .map(animal -> {
                        AnimalDTO dto = animalMapper.toDTO(animal);
                        // Asignar el slot al DTO si no se mapea automáticamente
                        dto.setSlot(animal.getSlot());
                        return dto;
                    })
                    .collect(Collectors.toList());

            Platform.runLater(() -> {
                AnimalDTO seleccionActual = comboAnimal.getValue();
                comboAnimal.setItems(FXCollections.observableArrayList(animalesDTO));

                // Restaurar selección previa si todavía está disponible
                if (seleccionActual != null) {
                    Optional<AnimalDTO> match = animalesDTO.stream()
                            .filter(a -> a.getId().equals(seleccionActual.getId()))
                            .findFirst();
                    comboAnimal.setValue(match.orElse(null));
                }
            });

        } catch (NumberFormatException e) {
            comboAnimal.setItems(FXCollections.emptyObservableList());
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar animales: " + e.getMessage(), Alert.AlertType.ERROR);
            comboAnimal.setItems(FXCollections.emptyObservableList());
        }
    }

    private void cargarDatosVenta(Long loteId) {
        if (loteId == null) return;

        try {
            id_regresarVenta.setVisible(true);
            VentaDomain venta = ventaService.findById(loteId);
            if (venta == null) {
                mostrarAlerta("Error", "No se encontró la venta especificada", Alert.AlertType.ERROR);
                return;
            }

            // Cargar campos del formulario
            Platform.runLater(() -> {
                // Seleccionar proveedor en ComboBox
                if (venta.getUsuario() != null) {
                    UsuarioDTO usuarioDTO = usuarioMapper.toDTO(venta.getUsuario());
                    comboCliente.getSelectionModel().select(usuarioDTO);
                }
            });

            // Cargar animales del lote
            List<AnimalDomain> animales = animalService.findByVenta(loteId);
            Platform.runLater(() -> {
                animalesEnVenta.clear();
                animalesObservableList.clear();

                animalesEnVenta.addAll(animales);
                animalesObservableList.addAll(animales);

                id_tableViewAnimales.refresh();
            });

        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar datos de la venta: " + e.getMessage(), Alert.AlertType.ERROR);
        }
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
            recalcularPrecioTotalVenta();

            // Refrescar la tabla para reflejar el cambio
            id_tableViewAnimales.refresh();
        }
    }

    @FXML
    private void addAnimal(ActionEvent event) {
        try {
            validarCamposAnimal();

            new AnimalDTO();
            AnimalDTO animal = comboAnimal.getValue();
            animal.setPrecioKiloVenta(new BigDecimal(id_precio_kilo.getText()));

            // Agregar el animal a la lista
            animalesObservableList.add(animalMapper.toDomain(animal));

            // Recalcular precio total del lote
            recalcularPrecioTotalVenta();

            mostrarAlerta("Éxito", "El Animal fue agregado exitosamente", Alert.AlertType.INFORMATION);
            limpiarCamposAnimal();

        } catch (IllegalArgumentException e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void createVenta(ActionEvent event) {
        try {
            validarCampoVenta();

            if (animalesObservableList.isEmpty()) {
                mostrarAlerta("Error", "Debe agregar al menos un animal a la venta", Alert.AlertType.ERROR);
                return;  // No se crea el lote si no hay animales
            }

            // Si hay animales, calculamos el precio total antes de crear el lote
            recalcularPrecioTotalVenta();  // Esto recalcula el precio total

            if (ventaId != null) {
                // Modo edición
                VentaDomain venta = ventaService.findById(ventaId);
                venta.setUsuario(usuarioMapper.toDomain(comboCliente.getValue()));
                venta.setPrecioVenta(precioTotal);  // Aseguramos que se guarda el precio total calculado

                // Actualizar animales
                venta.setAnimales(animalesObservableList);
                ventaService.update(venta);

                mostrarAlerta("Éxito", "Lote actualizado correctamente", Alert.AlertType.INFORMATION);
            } else {
                // Modo creación
                VentaDomain venta = new VentaDomain();
                venta.setUsuario(usuarioMapper.toDomain(comboCliente.getValue()));
                venta.setPrecioVenta(precioTotal);  // Aseguramos que se guarda el precio total calculado
                venta.setEstado("Pagado");
                venta.setFecha(LocalDate.now());

                // Guardar la venta primero para obtener su ID
                VentaDomain ventaGuardada = ventaService.save(venta);

                // Paso 2: Actualizar cada animal con la venta
                for (AnimalDomain animal : animalesObservableList) {
                    animal.setIdVenta(ventaGuardada.getId()); // Establecer la relación
                    animalService.update(animal); // Guardar cada animal
                }


                mostrarAlerta("Éxito", "Lote creado correctamente", Alert.AlertType.INFORMATION);
            }

            limpiarFormulario();
            navigationService.navigateTo("/fxml/viewLote.fxml", (Node) event.getSource());

        } catch (Exception e) {
            mostrarAlerta("Error", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void validarCamposAnimal() {

//        BigDecimal precioxkilo = new BigDecimal(id_precio_kilo.getText());
//        if (precioxkilo.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("El precio debe ser mayor que cero");
//        }

//        if (comboAnimal.getValue() == null) {
//            throw new IllegalArgumentException("Debe seleccionar un animal");
//        }

//        // Validar el precio por kilo
//        if (id_precio_kilo.getText().isEmpty()) {
//            throw new IllegalArgumentException("El precio por kilo es obligatorio");
//        }

    }

    private void validarCampoVenta() {
        if (comboCliente.getValue() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
    }

    private void recalcularPrecioTotalVenta() {
        precioTotal = BigDecimal.ZERO;

        for (AnimalDomain animal : animalesObservableList) {
            // Verificar que ni el peso ni el precio sean nulos
            if (animal.getPeso() != null && animal.getPrecioKiloVenta() != null) {
                BigDecimal precioAnimal = animal.getPeso().multiply(animal.getPrecioKiloVenta());
                precioTotal = precioTotal.add(precioAnimal);
            }
        }

        // Asegurar que el precio total no sea negativo
        precioTotal = precioTotal.max(BigDecimal.ZERO);

        // Actualizar el label
        precioTotalLabel.setText(formato.format(precioTotal));
    }

    private void limpiarCamposAnimal() {
        id_contramarca.clear();
        comboAnimal.setValue(null);
        id_precio_kilo.clear();  // Limpiar el campo de precio por kilo
        id_semana.clear();  // Limpiar el campo de precio por kilo
    }

    private void limpiarFormulario() {
        animalesEnVenta.clear();
        animalesObservableList.clear();
        comboCliente.setValue(null);
        limpiarCamposAnimal();
    }

    @Override
    public void setParameters(Map<String, Object> parameters) {
        if (parameters != null && parameters.containsKey("ventaId")) {
            ventaId = (Long) parameters.get("ventaId");
            cargarDatosVenta(ventaId);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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

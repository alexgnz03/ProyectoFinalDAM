package engine.ui.inGameMenu;

import dbo.ObjetosData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;

public class IGInventoryController {

    @FXML
    private TableColumn<InventarioItem, String> nombreObjeto;

    @FXML
    private TableColumn<InventarioItem, Integer> cantidadObjeto;

    @FXML
    private TableView<InventarioItem> tablaInventario;

    @FXML
    private TextArea descripcionObjeto;

    private ObjetosData objetosData;

    public void initialize() {
        objetosData = new ObjetosData();

        // Configuración de las columnas
        nombreObjeto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cantidadObjeto.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        // Obtener los datos de la base de datos
        List<InventarioItem> inventario = objetosData.obtenerDatosInventario();

        // Crear una lista observable para la tabla
        ObservableList<InventarioItem> data = FXCollections.observableArrayList(inventario);

        // Establecer los datos en la tabla
        tablaInventario.setItems(data);

        // Manejar eventos de selección en la tabla
        tablaInventario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                descripcionObjeto.setText(newValue.getDescripcion());
            }
        });
    }
}
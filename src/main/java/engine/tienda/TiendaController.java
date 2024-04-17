package engine.tienda;

import dbo.ObjetosData;
import engine.ui.inGameMenu.InventarioItem;
import engine.world.Maps;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class TiendaController {

    Stage stage;

    Maps maps = new Maps();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextArea descripcionText;

    @FXML
    private ImageView fondoImage;

    @FXML
    private TableColumn<TiendaItem, String> nombreColumn;

    @FXML
    private ImageView objetoImage;

    @FXML
    private TableView<TiendaItem> objetosTable;

    @FXML
    private TableColumn<TiendaItem, String> precioColumn;

    @FXML
    private Button salirButton;

    @FXML
    private AnchorPane view;

    private ObjetosData objetosData;

    public void initialize() {
        objetosData = new ObjetosData();

        // Configuración de las columnas
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Obtener los datos de la base de datos
        List<TiendaItem> tienda = objetosData.obtenerDatosObjeto();

        // Crear una lista observable para la tabla
        ObservableList<TiendaItem> data = FXCollections.observableArrayList(tienda);

        // Establecer los datos en la tabla
        objetosTable.setItems(data);

        // Manejar eventos de selección en la tabla
        objetosTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                descripcionText.setText(newValue.getDescripcion());
            }
        });
    }

    @FXML
    void comprarAction(ActionEvent event) {
        // Obtener el objeto seleccionado
        TiendaItem objetoSeleccionado = objetosTable.getSelectionModel().getSelectedItem();
        if (objetoSeleccionado != null) {
            try {
                // Insertar el objeto seleccionado en la tabla de inventario
                objetosData.insertarDatosInventario(objetoSeleccionado.getCodigo()); // Asumiendo que siempre compras 1 unidad
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar cualquier error al insertar en la base de datos
            }
        }
    }

    @FXML
    void salirAction(ActionEvent event) {
        maps.setStage(stage);
        maps.arcade(stage);
        maps.timerStart();
    }

}

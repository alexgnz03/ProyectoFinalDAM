package engine.ui.inGameMenu;

import dbo.ObjetosData;
import dbo.PlayerData;
import engine.MusicPlayer;
import engine.tienda.TiendaItem;
import engine.ui.PlayerState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class IGInventoryController {

    InGameMenu gameMenu;
    public void setGameMenu(InGameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    @FXML
    private TableColumn<InventarioItem, String> nombreObjeto;

    @FXML
    private TableColumn<InventarioItem, Integer> cantidadObjeto;

    @FXML
    private TableView<InventarioItem> tablaInventario;

    @FXML
    private TextArea descripcionObjeto;

    @FXML
    private ImageView objectImage;

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

    @FXML
    void salirAction(ActionEvent event) {
        gameMenu.cargarMenu();

    }

    @FXML
    void usarAction(ActionEvent event) throws IOException {
        // Obtener el objeto seleccionado
        InventarioItem objetoSeleccionado = tablaInventario.getSelectionModel().getSelectedItem();
        if (objetoSeleccionado != null && PlayerData.cargarDato(0) < 100) {
            try {
                // Obtener la estadística del objeto seleccionado
                int codObjeto = objetoSeleccionado.getCodigo();
                int estadistica = objetosData.obtenerEstadisticaObjeto(codObjeto);
                int cantidad = objetoSeleccionado.getCantidad();

                if (cantidad > 0){
                    // Curar al Player
                    int nuevaEstadistica = PlayerData.cargarDato(0) + estadistica;
                    if (nuevaEstadistica > 100) {
                        nuevaEstadistica = 100;
                    }
                    PlayerData.guardarDato(0, nuevaEstadistica);
                    PlayerState.actualizarSalud(PlayerData.cargarDato(0));

                    MusicPlayer efectos;
                    efectos = new MusicPlayer("/Effects/heal.mp3");
                    efectos.play();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), event2 -> {
                        efectos.stop();
                    }));
                    timeline.play();

                    //Actualizar tabla invewntario
                    objetosData.actualizarCantidadInventario(codObjeto, 1);

                    System.out.println("Estadística del objeto seleccionado: " + PlayerData.cargarDato(0));

                    // Recargar y actualizar la tabla con los nuevos datos del inventario
                    List<InventarioItem> inventarioActualizado = objetosData.obtenerDatosInventario();
                    tablaInventario.getItems().clear();
                    tablaInventario.getItems().addAll(inventarioActualizado);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar cualquier error al obtener la estadística del objeto
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            MusicPlayer efectos;
            efectos = new MusicPlayer("/Effects/notRun.mp3");
            efectos.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event2 -> {
                efectos.stop();
            }));
            timeline.play();
        }
    }

}
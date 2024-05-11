package engine.tienda;

import dbo.ObjetosData;
import dbo.PlayerData;
import engine.world.Maps_BSalud;
import engine.world.Maps_LaLaguna;
import engine.world.Maps_Teresitas;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TiendaController {

    Stage stage;

    Maps_BSalud mapsBSalud = new Maps_BSalud();
    Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();
    Maps_Teresitas mapsTeresitas = new Maps_Teresitas();
    private int I;

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
    private Label dineroLabel;

    @FXML
    private Label dineroQuant;

    @FXML
    private Label euroLabel;

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
        try {
            dineroQuant.setText(String.valueOf(PlayerData.cargarDato(7)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        switch (I){
            case 1:
                fondoImage.setImage(new Image("mrsnack.png"));
                break;
            case 2:
                fondoImage.setImage(new Image("alteza.png"));
                break;
            case 3:
                fondoImage.setImage(new Image("tiendaPlaya.png"));
                break;
        }
    }

    public void FadeIn() {
        Rectangle nuevoContenido = new Rectangle(800, 800, Color.BLACK);
        nuevoContenido.setOpacity(100); // Iniciar con opacidad 0 para el FadeIn

        view.getChildren().add(nuevoContenido); // Agregar el nuevo contenido

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), nuevoContenido);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();
    }

    public void IrisTransition(){
        Rectangle nuevoContenido = new Rectangle(800, 800, Color.BLACK);
        nuevoContenido.setOpacity(100); // Iniciar con opacidad 0 para el FadeIn

        view.getChildren().add(nuevoContenido); // Agregar el nuevo contenido

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.40), nuevoContenido);
        scaleTransition.setFromX(1); // Escala inicial en X
        scaleTransition.setFromY(1); // Escala inicial en Y
        scaleTransition.setToX(0); // Escala final en X
        scaleTransition.setToY(0); // Escala final en Y

        // Invertimos el valor de la interpolación para simular un iris que se abre
        scaleTransition.setInterpolator(Interpolator.LINEAR);

        // Reproducimos la animación
        scaleTransition.play();
    }

    private void devolverAMundo(){
        switch (I) {
            case 1:
                mapsLaLaguna.setStage(stage);
                mapsLaLaguna.trinidad03(stage);
                mapsLaLaguna.timerStart();
                break;
            case 2:
                mapsBSalud.calleInstituto2(stage);
                break;
            case 3:
                mapsTeresitas.setStage(stage);
                mapsTeresitas.teresitas07(stage);
                mapsTeresitas.timerStart();
                break;
            default:
                mapsBSalud.calleInstituto(stage);
        }
    }

    @FXML
    void comprarAction(ActionEvent event) throws IOException {
        // Obtener el objeto seleccionado
        TiendaItem objetoSeleccionado = objetosTable.getSelectionModel().getSelectedItem();
        if (objetoSeleccionado != null && PlayerData.cargarDato(7) >= objetoSeleccionado.getPrecio()) {
            try {
                // Insertar el objeto seleccionado en la tabla de inventario
                objetosData.insertarDatosInventario(objetoSeleccionado.getCodigo()); // Asumiendo que siempre compras 1 unidad
                PlayerData.guardarDato(7, (int) (PlayerData.cargarDato(7) - objetoSeleccionado.getPrecio()));
                dineroQuant.setText(String.valueOf(PlayerData.cargarDato(7)));
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar cualquier error al insertar en la base de datos
            }
        }
    }

    @FXML
    void salirAction(ActionEvent event) {
        devolverAMundo();
    }

    public void setI(int i) {
        I = i;
    }

}

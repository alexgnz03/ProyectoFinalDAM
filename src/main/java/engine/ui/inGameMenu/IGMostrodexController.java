package engine.ui.inGameMenu;

import dbo.MonsterLoader;
import dbo.ObjetosData;
import dbo.PlayerData;
import engine.MusicPlayer;
import engine.ui.PlayerState;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class IGMostrodexController {

    InGameMenu gameMenu;
    public void setGameMenu(InGameMenu gameMenu){
        this.gameMenu = gameMenu;
    }

    @FXML
    private TableColumn<MostroDexItem, Integer> atMagMostro;

    @FXML
    private TableColumn<MostroDexItem, Integer> ataqueMostro;

    @FXML
    private TableColumn<MostroDexItem, Integer> defMagMostro;

    @FXML
    private TableColumn<MostroDexItem, Integer> defensaMostro;

    @FXML
    private TextArea descripcionMostro;

    @FXML
    private TableColumn<MostroDexItem, Integer> idMostro;

    @FXML
    private ImageView mostroImage;

    @FXML
    private Label mostrodexLabel;

    @FXML
    private TableColumn<MostroDexItem, String> nombreMostro;

    @FXML
    private TableView<MostroDexItem> tablaMostroDex;

    @FXML
    private Pane view;

    private MonsterLoader mostro;

    public void initialize() {
        mostro = new MonsterLoader();

        // Configuración de las columnas
        idMostro.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        nombreMostro.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ataqueMostro.setCellValueFactory(new PropertyValueFactory<>("ataque"));
        defensaMostro.setCellValueFactory(new PropertyValueFactory<>("defensa"));
        atMagMostro.setCellValueFactory(new PropertyValueFactory<>("atMagico"));
        defMagMostro.setCellValueFactory(new PropertyValueFactory<>("defMagica"));

        // Obtener los datos de la base de datos
        List<MostroDexItem> mostroDex = mostro.obtenerDatosMostroDex();

        // Crear una lista observable para la tabla
        ObservableList<MostroDexItem> data = FXCollections.observableArrayList(mostroDex);

        // Establecer los datos en la tabla
        tablaMostroDex.setItems(data);

        // Manejar eventos de selección en la tabla
        tablaMostroDex.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                descripcionMostro.setText(newValue.getDescripcion());
                mostroImage.setImage(new Image(newValue.getImagenMostro()));
            }
        });
    }

    @FXML
    void salirAction(ActionEvent event) {
        gameMenu.cargarMenu();

    }

}
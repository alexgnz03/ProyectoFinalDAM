package engine.combate.peleitas;
import controllers.MainMenuController;
import engine.world.Maps;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOverController {

    Stage stage;
    Maps maps = new Maps();
    MainMenuController mainMenu = new MainMenuController();

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void reintentarAction(ActionEvent event) {
        maps.setY(65);
        maps.setX(550);
        maps.paradaGuagua(stage);
    }

    @FXML
    void volverAction(ActionEvent event) {
        Platform.exit();
    }

    public void gameOverPantalla(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameOver.fxml"));
        GameOverController gameOver = new GameOverController();
        gameOver.setStage(stage);
        loader.setController(gameOver);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Game Over");
        stage.setScene(scene);
        stage.show();
    }
}

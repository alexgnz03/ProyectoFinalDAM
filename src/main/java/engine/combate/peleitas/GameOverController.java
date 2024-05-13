package engine.combate.peleitas;
import controllers.MainMenuController;
import engine.world.Maps_BSalud;
import engine.world.Maps_LaLaguna;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameOverController {

    Stage stage;
    Maps_BSalud mapsBSalud = new Maps_BSalud();

    Maps_LaLaguna maps = new Maps_LaLaguna();
    MainMenuController mainMenu = new MainMenuController();

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void reintentarAction(ActionEvent event) {
        maps.setY(430);
        maps.setX(315);
        maps.musica();
        maps.setStage(stage);
        maps.casa(stage);
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

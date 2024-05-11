package engine.combate.peleitas;
import controllers.MainMenuController;
import engine.world.Maps_BSalud;
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
    MainMenuController mainMenu = new MainMenuController();

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    void reintentarAction(ActionEvent event) {
        mapsBSalud.setY(65);
        mapsBSalud.setX(550);
        mapsBSalud.paradaGuagua(stage);
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

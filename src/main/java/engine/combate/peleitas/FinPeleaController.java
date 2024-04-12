package engine.combate.peleitas;

import engine.combate.peleitas.FightController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class FinPeleaController {

    @FXML
    private Text dineroTexto;

    @FXML
    private AnchorPane finPeleaPane;

    @FXML
    private Text mensajeTexto;

    @FXML
    private Text resultadoTexto;

    // Referencia al FightController
    private FightController fightController;

    // Setter para establecer la referencia al FightController
    public void setFightController(FightController fightController) {
        this.fightController = fightController;
    }



    @FXML
    void continuarButtonEvent(ActionEvent event) {

    }
    @FXML
    private void initializeFin(){
        int dineroObtenido = 0;
        Jugador jugador = fightController.getJugador();
        Monstruo monstruo = fightController.getMonstruo();
        if (monstruo.Muerto()){
            dineroObtenido = monstruo.getMoney();
            resultadoTexto.setText("Victoria");
            mensajeTexto.setText("Felicidades!");

        } else if (jugador.Muerto()) {
            resultadoTexto.setText("Derrota");
            mensajeTexto.setText("No te rindas!");

        } else {
            resultadoTexto.setText("Escapaste");
            mensajeTexto.setText("Corre!");
        }

        jugador.sumaDinero(dineroObtenido);
        dineroTexto.setText(String.valueOf(dineroObtenido));
    }


}

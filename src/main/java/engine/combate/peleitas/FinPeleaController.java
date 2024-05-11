package engine.combate.peleitas;

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
            System.out.println("Felicidades fin de combate");

        } else if (jugador.Muerto()) {
            resultadoTexto.setText("Derrota");
            mensajeTexto.setText("No te rindas!");
            System.out.println("Felicidades fin de combate 2");

        } else {
            resultadoTexto.setText("Escapaste");
            mensajeTexto.setText("Corre!");
            System.out.println("Felicidades fin de combate 3");
        }

        jugador.sumaDinero(dineroObtenido);
        dineroTexto.setText(String.valueOf(dineroObtenido));
    }


}

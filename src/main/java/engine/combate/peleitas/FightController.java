package engine.combate.peleitas;

import controllers.FinalController;
import controllers.MainMenuController;
import dbo.PlayerData;
import engine.MusicPlayer;
import engine.objects.Player;
import engine.world.Maps;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.*;

public class FightController {


    @FXML
    private Button botonAtaque;

    @FXML
    private Button botonEscapar;

    @FXML
    private Button botonFe;

    @FXML
    private Button botonMagia;

    @FXML
    private AnchorPane fightPane;

    @FXML
    private ImageView imagenJugador;

    @FXML
    private ImageView imagenMonstruo;

    @FXML
    private ProgressBar jugadorBarraVida;

    @FXML
    private Text jugadorVida;

    @FXML
    private ProgressBar monstruoBarraVida;

    @FXML
    private Text monstruoVida;

    @FXML
    private Text nombreMonstruo;

    @FXML
    private Text monstruoDamage;

    @FXML
    private Label qteTextLabel;

    @FXML
    private Text jugadorDamage;
    private Jugador jugador = new Jugador();
    private Monstruo monstruo = new Monstruo();
    private Fran fran = new Fran();
    boolean timerRunning;
    Stage stage;
    Maps maps = new Maps();

    Player player;
    private int I;
    private int tipoDeCombate;

    private boolean prioridadMonstruo;

    private boolean combateFran;

    private Timeline qteTimeline;
    private Random random;
    private KeyCode qteKey;
    private boolean qteActive;

    double x;
    double y;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public FightController(int tipoDeCombate, double x, double y){
        this.tipoDeCombate = tipoDeCombate;
        this.x = x;
        this.y = y;

        System.out.println("Valor de I en el constructor: " + tipoDeCombate);
    }
    @FXML
    private void initialize() {
        System.out.println("Inicializando la pelea...");
        System.out.println("Valor de I en el constructor: " + tipoDeCombate);
        qteTextLabel.setVisible(false);
        if (tipoDeCombate == 1){
            double progresoJugador = jugador.getVida() / jugador.getVida_maxima();
            jugadorBarraVida.setProgress(progresoJugador);
            aplicarColorBarra(jugadorBarraVida, progresoJugador);
            jugadorVida.setText(String.valueOf(jugador.getVida()));

            imagenMonstruo.setImage(monstruo.getEnemy_image());
            System.out.println("Imagen del monstruo configurada: " + monstruo.getEnemy_image());
            nombreMonstruo.setText(monstruo.getName());

            double progresoMonstruo = monstruo.getVida() / monstruo.getVida_maxima();
            monstruoBarraVida.setProgress(progresoMonstruo);
            aplicarColorBarra(monstruoBarraVida, progresoMonstruo);
            monstruoVida.setText(String.valueOf(monstruo.getVida()));

            if (monstruo.getVelocidad() > jugador.getVelocidad()) {
                prioridadMonstruo = true;
                botonAtaque.fire();
            }
        } else if (tipoDeCombate == 2) {
                double progresoJugador = jugador.getVida() / jugador.getVida_maxima();
                jugadorBarraVida.setProgress(progresoJugador);
                aplicarColorBarra(jugadorBarraVida, progresoJugador);
                jugadorVida.setText(String.valueOf(jugador.getVida()));

                imagenMonstruo.setImage(fran.getEnemy_image());
                nombreMonstruo.setText(fran.getName());

                double progresoMonstruo = fran.getVida() / fran.getVida_maxima();
                monstruoBarraVida.setProgress(progresoMonstruo);
                aplicarColorBarra(monstruoBarraVida, progresoMonstruo);
                monstruoVida.setText(String.valueOf(fran.getVida()));

                if (fran.getVelocidad() > jugador.getVelocidad()) {
                    prioridadMonstruo = true;
                    botonAtaque.fire();
                }
                
        }
    }

    private void generarReporteMonstruo() {
        GenerateMonstruoReport.generateReport(monstruo);
    }

    public void combateNormal(){

    }

    private void iniciarQTE() {
        qteActive = false;
        random = new Random();
        KeyCode[] letterKeys = {KeyCode.A, KeyCode.B, KeyCode.C, KeyCode.D, KeyCode.E, KeyCode.F, KeyCode.G, KeyCode.H, KeyCode.I, KeyCode.J, KeyCode.K, KeyCode.L, KeyCode.M, KeyCode.N, KeyCode.O, KeyCode.P, KeyCode.Q, KeyCode.R, KeyCode.S, KeyCode.T, KeyCode.U, KeyCode.V, KeyCode.W, KeyCode.X, KeyCode.Y, KeyCode.Z};
        KeyCode[] specialKeys = {KeyCode.SPACE};
        KeyCode[] allKeys = Arrays.copyOf(letterKeys, letterKeys.length + specialKeys.length);
        System.arraycopy(specialKeys, 0, allKeys, letterKeys.length, specialKeys.length);

        qteKey = allKeys[random.nextInt(allKeys.length)]; // Obtener una tecla aleatoria
        mostrarQTEKey(qteKey);

        // Hacer visible el label del QTE
        qteTextLabel.setVisible(true);

        // Configurar el temporizador para el QTE
        qteTimeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> {
            // Detener el temporizador del QTE
            qteTimeline.stop();

            // Ocultar el label del QTE
            qteTextLabel.setVisible(false);

            // Determinar si el QTE fue exitoso o no
            boolean qteSuccess = qteActive;

            // Realizar el ataque y ajustar el daño según el éxito o fracaso del QTE
            int damage = jugador.magicSkill(monstruo, 1, qteSuccess);

            // Aplicar el daño al enemigo
            accion(damage);

            // Resto del código para manejar el ataque...
        }));
        qteTimeline.play();

        // Manejar evento de teclado para detectar si el usuario presiona la tecla correcta durante el QTE
        fightPane.setOnKeyPressed(event -> {
            if (event.getCode() == qteKey) {
                // El usuario presionó la tecla correcta
                qteActive = true;
            }
        });
    }
    private void mostrarQTEKey(KeyCode keyCode) {
        // Mostrar la tecla en algún lugar de la pantalla
        qteTextLabel.setText(keyCode.toString());
    }

    @FXML
    void actionButtonEvent(ActionEvent event) throws IOException {
        if (event.getSource().equals(botonAtaque)) {
            accion(jugador.damageFisico(monstruo));
            MusicPlayer efectos;
            efectos = new MusicPlayer("/Effects/ataque.mp3");
            efectos.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), event2 -> {
                efectos.stop();
            }));
            timeline.play();

        } else if (event.getSource().equals(botonMagia)) {
            // Iniciar el Quick Time Event
            iniciarQTE();
        } else if (event.getSource().equals(botonFe)) {
            accion(jugador.damageSkill(monstruo, 1));
            MusicPlayer efectos;
            efectos = new MusicPlayer("/Effects/ataqueCritico.mp3");
            efectos.play();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), event4 -> {
                efectos.stop();
            }));
            timeline.play();
        } else if (event.getSource().equals(botonEscapar)) {
            Random random = new Random();
            double probabilidad = random.nextDouble();
            if (probabilidad <= 0.5) {
                MusicPlayer efectos;
                efectos = new MusicPlayer("/Effects/runAway.mp3");
                efectos.play();
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1.5), event2 -> {
                    efectos.stop();
                }));
                timeline2.play();
                System.out.println("control 9: " + x + " i: " + I);
                try {
                    PlayerData.guardarDato(0, (int) jugador.getVida());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                devolverAMundo(x, y);
            } else {
                prioridadMonstruo = true;
                accion(15);
                MusicPlayer efectos;
                efectos = new MusicPlayer("/Effects/notRun.mp3");
                efectos.play();
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1.5), event2 -> {
                    efectos.stop();
                }));
                timeline2.play();
            }

        }
    }

    private KeyFrame[] accionJugador(int damage) {
        double progresoJugador = jugadorBarraVida.getProgress();
        double progresoMonstruo = monstruoBarraVida.getProgress();

        KeyValue monstruoVidaBarraK = new KeyValue(monstruoBarraVida.progressProperty(), monstruo.getVida() / monstruo.getVida_maxima());
        KeyValue monstruoVidaK = new KeyValue(monstruoVida.textProperty(), String.valueOf(monstruo.getVida()));
        KeyValue botonAtaqueK = new KeyValue(botonAtaque.disableProperty(), true);
        KeyValue botonMagiaK = new KeyValue(botonMagia.disableProperty(), true);
        KeyValue botonFeK = new KeyValue(botonFe.disableProperty(), true);
        KeyValue botonEscaparK = new KeyValue(botonEscapar.disableProperty(), true);

        KeyFrame frame0s = new KeyFrame(new Duration(0), botonAtaqueK, botonMagiaK, botonFeK, botonEscaparK, monstruoVidaBarraK, monstruoVidaK);

        KeyValue jugadorDamageK = new KeyValue(jugadorDamage.textProperty(), String.valueOf(damage));

        KeyFrame frame15s = new KeyFrame(new Duration(1350), jugadorDamageK);

        KeyFrame frame175s = new KeyFrame(new Duration(1550), monstruoVidaBarraK, jugadorDamageK);

        monstruo.calcularVida(damage);
        monstruoVidaBarraK = new KeyValue(monstruoBarraVida.progressProperty(), monstruo.getVida() / monstruo.getVida_maxima());
        monstruoVidaK = new KeyValue(monstruoVida.textProperty(), String.valueOf(monstruo.getVida()));
        botonAtaqueK = new KeyValue(botonAtaque.disableProperty(), false);
        botonMagiaK = new KeyValue(botonMagia.disableProperty(), false);
        botonFeK = new KeyValue(botonFe.disableProperty(), false);
        botonEscaparK = new KeyValue(botonEscapar.disableProperty(), false);

        KeyFrame frame200s = new KeyFrame(new Duration(1800), botonAtaqueK, botonMagiaK, botonFeK, botonEscaparK, monstruoVidaBarraK, monstruoVidaK);
        aplicarColorBarra(jugadorBarraVida, progresoJugador);
        aplicarColorBarra(monstruoBarraVida, progresoMonstruo);
        return new KeyFrame[]{frame0s, frame15s, frame175s, frame200s};
    }

    private KeyFrame[] accionMonstruo(int time) {
        if (monstruo.Muerto()) return new KeyFrame[0];

        double progresoJugador = jugadorBarraVida.getProgress();
        double progresoMonstruo = monstruoBarraVida.getProgress();

        KeyValue jugadorVidaBarraK = new KeyValue(jugadorBarraVida.progressProperty(), jugador.getVida() / jugador.getVida_maxima());
        KeyValue jugadorVidaK = new KeyValue(jugadorVida.textProperty(), String.valueOf(jugador.getVida()));
        KeyValue botonAtaqueK = new KeyValue(botonAtaque.disableProperty(), true);
        KeyValue botonMagiaK = new KeyValue(botonMagia.disableProperty(), true);
        KeyValue botonFeK = new KeyValue(botonFe.disableProperty(), true);
        KeyValue botonEscaparK = new KeyValue(botonEscapar.disableProperty(), true);

        KeyFrame frame0s = new KeyFrame(new Duration(0), botonAtaqueK, botonMagiaK, botonFeK, botonEscaparK, jugadorVidaBarraK, jugadorVidaK);

        int damage = monstruo.iaAccion(jugador);

        jugador.calcularVida(damage);
        KeyValue monstruoDamageK = new KeyValue(monstruoDamage.textProperty(), String.valueOf(damage));

        KeyFrame frame1350s = new KeyFrame(new Duration(time + 1350), monstruoDamageK);

        KeyFrame frame1550s = new KeyFrame(new Duration(time + 1550), jugadorVidaBarraK, monstruoDamageK);

        jugadorVidaBarraK = new KeyValue(jugadorBarraVida.progressProperty(), jugador.getVida() / jugador.getVida_maxima());
        monstruoDamageK = new KeyValue(monstruoDamage.textProperty(), null);
        botonAtaqueK = new KeyValue(botonAtaque.disableProperty(), false);
        botonMagiaK = new KeyValue(botonMagia.disableProperty(), false);
        botonFeK = new KeyValue(botonFe.disableProperty(), false);
        botonEscaparK = new KeyValue(botonEscapar.disableProperty(), false);
        jugadorVidaK = new KeyValue(jugadorVida.textProperty(), String.valueOf(jugador.getVida()));

        KeyFrame frame1800s = new KeyFrame(new Duration(time + 1800), monstruoDamageK, jugadorVidaBarraK, botonAtaqueK, botonMagiaK, botonFeK, botonEscaparK, jugadorVidaK);
        aplicarColorBarra(jugadorBarraVida, progresoJugador);
        aplicarColorBarra(monstruoBarraVida, progresoMonstruo);
        return new KeyFrame[]{frame0s, frame1350s, frame1550s, frame1800s};
    }

    private void accion(int damage) {
        Timeline timeline = new Timeline();
        List<KeyFrame> keyFrames = new ArrayList<>();

        if (!prioridadMonstruo) {
            Collections.addAll(keyFrames, accionJugador(damage));
        }

        Collections.addAll(keyFrames, accionMonstruo(prioridadMonstruo ? 0 : 1800));

        timeline.getKeyFrames().addAll(keyFrames);

        prioridadMonstruo = false;

        timeline.play();

        timeline.setOnFinished(e -> {
            if (jugador.Muerto()) {
                try {
                    jugador.setVida(getJugador().getVida_maxima());
                    GameOverController gameOver = new GameOverController();
                    gameOver.gameOverPantalla(stage);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else if (monstruo.Muerto()) {
                MusicPlayer efectos;
                efectos = new MusicPlayer("/Effects/Win.mp3");
                efectos.play();
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event2 -> {
                    efectos.stop();
                }));
                timeline2.play();
                System.out.println("control 9-2: " + x + " i: " + I);
                try {
                    PlayerData.guardarDato(0, (int) jugador.getVida());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                devolverAMundo(x, y);
            }
        });
    }

    private void aplicarColorBarra(ProgressBar barra, double progreso) {
        if (progreso <= 0.25) {
            barra.setStyle("-fx-accent: red;");
        } else if (progreso <= 0.5) {
            barra.setStyle("-fx-accent: yellow;");
        } else {
            barra.setStyle("-fx-accent: green;");
        }
    }

    private void devolverAMundo(double x, double y){
        System.out.println("control 10: " + x + " i: " + I);
        maps.setStage(stage);
        maps.setX(x - 24);
        maps.setY(y - 24);

        if (maps.getTimer() != null){
            maps.getTimer().stop();
        }

        System.out.println("control 11: " + x + " i: " + I);

        switch (I) {
            case 1:
                maps.calleInstituto(stage);
                break;
            case 2:
                maps.calleInstituto2(stage);
                break;
            case 3:
                maps.plaza(stage);
                break;
            case 4:
                maps.plaza2(stage);
                break;
            case 5:
                maps.arcade(stage);
                break;
            case 6:
                maps.paradaGuagua(stage);
                break;
            case 7:
                maps.institutoPlaza(stage);
                break;
            case 8:
                maps.placita(stage);
                break;
            case 9:
                maps.lobbyInstituto(stage);
                break;
            case 10:
                maps.lobbyInstituto2(stage);
                break;
            case 11:
                maps.subidaInstituto(stage);
                break;
            case 12:
                maps.subidaInstituto2(stage);
                break;
            case 13:
                maps.lobbyAulas(stage);
                break;
            case 14:
                maps.lobbyAulas2(stage);
                break;
            default:
                maps.calleInstituto(stage);
        }

        maps.timerStart();
        System.out.println("control 12: " + x + " i: " + I);

    }


    public void mainMenuPantalla(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Final.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        FinalController controller = loader.getController();
        controller.setStage(stage);
        controller.setWorld(new Maps());
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Monstruo getMonstruo() {
        return monstruo;
    }

    public void setI(int i) {
        I = i;
    }

    public void setCombateFran(boolean combateFran) {
        this.combateFran = combateFran;
    }
}
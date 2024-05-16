package engine.combate;

import engine.dbo.MonsterLoader;
import engine.dbo.PlayerData;
import engine.EffectPlayer;
import engine.entities.Player;
import engine.world.Maps_BSalud;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

//El FightController define el funcionamiento y lógica de las peleas. Se podría usar un Util pero a lo mejor enrevesaría demasiado el código.
public class FightController {

    @FXML
    private Button botonEscapar;

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
    private HBox manoDeCartasContainer;

    @FXML
    private Text jugadorDamage;

    @FXML
    private ImageView fondoImage;


    private Jugador jugador = new Jugador();
    MonsterLoader monsterLoader = new MonsterLoader();

    int idDelMonstruo = new Random().nextInt(6) + 1;
    Monstruo monstruo = monsterLoader.cargarMonstruo(idDelMonstruo);
    boolean timerRunning;
    Stage stage;
    Maps_BSalud mapsBSalud = new Maps_BSalud();

    Player player;
    private int I;
    private int tipoDeCombate;

    private boolean prioridadMonstruo;

    private boolean combateFran;

    private Timeline qteTimeline;
    private Random random;
    private KeyCode qteKey;
    private boolean qteActive;

    private boolean monstruoMuerto = false;

    private List<Carta> manoDeCartas;

    private boolean cartasActivas = true;

    Image playerStandardImage = new Image("Player/Up2_HD.png");
    Image playerAttackImage = new Image("Player/Up3_HD.png");
    Image playerMagicImage = new Image("Player/Right2_HD.png");

    double x;
    double y;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public FightController(int tipoDeCombate, double x, double y){
        this.tipoDeCombate = tipoDeCombate;
        this.x = x;
        this.y = y;

        // Inicializar la mano de cartas con 5 cartas aleatorias
        manoDeCartas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            manoDeCartas.add(generarCartaAleatoria());
        }

        System.out.println("Valor de I en el constructor: " + tipoDeCombate);
    }

    private void ataqueAnimation(){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.25), imagenJugador);
        translateTransition.setFromX(20); // Posición inicial X (derecha)
        translateTransition.setToX(0); // Posición final X (izquierda)
        translateTransition.play();
        imagenJugador.setImage(playerAttackImage);
    }

    private Carta generarCartaAleatoria() {
        // Generar una carta aleatoria
        Random random = new Random();
        // Modificar el rango de índices para dar más probabilidad a ciertos tipos de carta
        int indice = random.nextInt(100); // Número aleatorio entre 0 y 99
        TipoCarta tipo;
        // Ajustar las probabilidades de acuerdo al rango de índices
        if (indice < 60) { // 60% de probabilidad para cartas débiles
            tipo = TipoCarta.values()[random.nextInt(2)]; // Cartas débiles
        } else if (indice < 90) { // 30% de probabilidad para cartas medias
            tipo = TipoCarta.values()[random.nextInt(4) + 2]; // Cartas medias
        } else { // 10% de probabilidad para cartas fuertes
            tipo = TipoCarta.values()[random.nextInt(3) + 6]; // Cartas fuertes
        }
        return new Carta(tipo);
    }

    // Método para jugar una carta
    public void jugarCarta(Carta carta) {
        // Implementa la lógica para manejar la carta seleccionada
        cartasActivas = false;

        Timeline tm = new Timeline(new KeyFrame(Duration.seconds(0.75), e -> {
            imagenJugador.setImage(playerStandardImage);
        }));

        int index = manoDeCartas.indexOf(carta);
        if (index != -1) {
            manoDeCartas.set(index, generarCartaAleatoria());
        } else {
            // Carta no encontrada en la lista
            // Aquí puedes manejar el caso de la carta no encontrada si es necesario
        }
        // Por ejemplo, podrías llamar a un método específico dependiendo del tipo de carta
        switch (carta.getTipo()) {
            case ATAQUEdebil:
                ataqueAnimation();
                accion(jugador.damageFisico(monstruo)/2);
                EffectPlayer efectos;
                efectos = new EffectPlayer("/Effects/ataque.mp3");
                efectos.play();
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), event2 -> {
                    efectos.stop();
                    actualizarContenedorCartas();
                    cartasActivas = true;
                    imagenJugador.setImage(playerStandardImage);
                }));
                timeline.play();
                break;
            case MAGIAdebil:
                imagenJugador.setImage(playerMagicImage);
                iniciarQTE(1);

                tm.play();
                break;
            case ATAQUEmedio:
                ataqueAnimation();
                accion(jugador.damageFisico(monstruo));
                EffectPlayer efectos2;
                efectos2 = new EffectPlayer("/Effects/ataque.mp3");
                efectos2.play();
                Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(0.75), event2 -> {
                    efectos2.stop();
                    actualizarContenedorCartas();
                    cartasActivas = true;
                    imagenJugador.setImage(playerStandardImage);
                }));
                timeline3.play();
                break;
            case MAGIAmedia:
                imagenJugador.setImage(playerMagicImage);
                iniciarQTE(2);
                tm.play();
                break;
            case FEmedia:
                imagenJugador.setImage(playerAttackImage);
                accion(jugador.damageSkill(monstruo, 1, 1));
                EffectPlayer efectos3;
                efectos3 = new EffectPlayer("/Effects/ataqueCritico.mp3");
                efectos3.play();
                Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(0.75), event4 -> {
                    efectos3.stop();
                    actualizarContenedorCartas();
                    cartasActivas = true;
                    imagenJugador.setImage(playerStandardImage);
                }));
                timeline4.play();
                break;
            case CURAmedia:
                jugador.setVida(jugador.getVida()+10);
                EffectPlayer efectosm;
                efectosm = new EffectPlayer("/Effects/heal.mp3");
                efectosm.play();
                Timeline timelinem = new Timeline(new KeyFrame(Duration.seconds(0.75), event2 -> {
                    efectosm.stop();
                    actualizarContenedorCartas();
                    cartasActivas = true;
                }));
                timelinem.play();
                break;
            case ATAQUEfuerte:
                ataqueAnimation();
                accion(jugador.damageFisico(monstruo));
                EffectPlayer efectos4;
                efectos4 = new EffectPlayer("/Effects/ataque.mp3");
                efectos4.play();
                Timeline timeline5 = new Timeline(new KeyFrame(Duration.seconds(0.75), event2 -> {
                    efectos4.stop();
                    actualizarContenedorCartas();
                    cartasActivas = true;
                    imagenJugador.setImage(playerStandardImage);
                }));
                timeline5.play();
                break;
            case MAGIAfuerte:
                imagenJugador.setImage(playerMagicImage);
                iniciarQTE(3);
                tm.play();
                break;
            case FEfuerte:
                imagenJugador.setImage(playerAttackImage);
                accion(jugador.damageSkill(monstruo, 1, 2));
                EffectPlayer efectos5;
                efectos5 = new EffectPlayer("/Effects/ataqueCritico.mp3");
                efectos5.play();
                Timeline timeline6 = new Timeline(new KeyFrame(Duration.seconds(0.75), event4 -> {
                    efectos5.stop();
                    actualizarContenedorCartas();
                    cartasActivas = true;
                    imagenJugador.setImage(playerStandardImage);
                }));
                timeline6.play();
                break;
        }
        // Reemplazar la carta jugada con una nueva carta aleatoria
        actualizarContenedorCartas();
        cartasActivas = true;
    }

    // Método para actualizar el contenedor de cartas en la interfaz gráfica
    private void actualizarContenedorCartas() {
        // Limpiar el contenedor antes de agregar las nuevas cartas
        manoDeCartasContainer.getChildren().clear();

        // Agregar cada carta al contenedor como un botón
        for (Carta carta : manoDeCartas) {
            Button button = new Button(carta.getTipo().toString()); // Mostrar el tipo de carta como texto en el botón

            switch (carta.getTipo()) {
                case ATAQUEdebil:
                    button.getStyleClass().add("botonBronce");
                    button.setText("Ataque Débil");
                    break;
                case MAGIAdebil:
                    button.getStyleClass().add("botonBronce");
                    button.setText("Magia Débil");
                    break;
                case ATAQUEmedio:
                    button.getStyleClass().add("botonPlata");
                    button.setText("Ataque Medio");
                    break;
                case MAGIAmedia:
                    button.getStyleClass().add("botonPlata");
                    button.setText("Magia Media");
                    break;
                case FEmedia:
                    button.getStyleClass().add("botonPlata");
                    button.setText("Fe Media");
                    break;
                case CURAmedia:
                    button.getStyleClass().add("botonPlata");
                    button.setText("Cura Media");
                    break;
                case ATAQUEfuerte:
                    button.getStyleClass().add("botonOro");
                    button.setText("Ataque Fuerte");
                    break;
                case MAGIAfuerte:
                    button.getStyleClass().add("botonOro");
                    button.setText("Magia Fuerte");
                    break;
                case FEfuerte:
                    button.getStyleClass().add("botonOro");
                    button.setText("Fe Fuerte");
                    break;
            }

            if (cartasActivas) {
                // Activar el botón solo si las cartas están activas
                Carta cartaActual = carta;
                button.setOnAction(event -> jugarCarta(cartaActual));
            } else {
                // Desactivar el botón si las cartas están inactivas
                button.setDisable(true);
            }
            // Agregar el botón al contenedor
            manoDeCartasContainer.getChildren().add(button);
        }
    }

    @FXML
    private void initialize() {
        actualizarContenedorCartas();
        qteTextLabel.setVisible(false);

        fondoImage.setImage(new Image("Combate/parque.jpeg"));

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

        }
    }


    private void iniciarQTE(int potencia) {

        qteActive = false;
        random = new Random();
        KeyCode[] letterKeys = {KeyCode.A, KeyCode.B, KeyCode.C, KeyCode.D, KeyCode.E, KeyCode.F, KeyCode.G, KeyCode.H, KeyCode.I, KeyCode.J, KeyCode.K, KeyCode.L, KeyCode.M, KeyCode.N, KeyCode.O, KeyCode.P, KeyCode.Q, KeyCode.R, KeyCode.S, KeyCode.T, KeyCode.U, KeyCode.V, KeyCode.W, KeyCode.X, KeyCode.Y, KeyCode.Z};
        KeyCode[] allKeys = Arrays.copyOf(letterKeys, letterKeys.length);

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

            if (potencia == 1){
                damage = damage/2;
            } else if (potencia == 2){
                damage = damage;
            } else if (potencia == 3){
                damage = damage*2;
            }

            // Aplicar el daño al enemigo
            accion(damage);
        }));
        qteTimeline.play();


        // Manejar evento de teclado para detectar si el usuario presiona la tecla correcta durante el QTE
        fightPane.setOnKeyPressed(event -> {
            if (event.getCode() == qteKey) {
                // El usuario presionó la tecla correcta
                qteActive = true;
                imagenJugador.setImage(playerAttackImage);
            }
        });

    }
    private void mostrarQTEKey(KeyCode keyCode) {
        // Mostrar la tecla en algún lugar de la pantalla que ya pondré
        qteTextLabel.setText(keyCode.toString());
    }

    //No recuerdo por qué se maneja el botón así, creo que esto venía heredado del IESCanarias Simulator
    @FXML
    void actionButtonEvent(ActionEvent event) throws IOException {
        if (event.getSource().equals(botonEscapar)) {
            Random random = new Random();
            double probabilidad = random.nextDouble();
            if (probabilidad <= 0.5) {
                EffectPlayer efectos;
                efectos = new EffectPlayer("/Effects/runAway.mp3");
                efectos.play();
                Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1.5), event2 -> {
                    efectos.stop();
                }));
                timeline2.play();
                try {
                    PlayerData.guardarDato(0, (int) jugador.getVida());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                devolverAMundo(x, y);
            } else {
                prioridadMonstruo = true;
                accion(50);
                EffectPlayer efectos;
                efectos = new EffectPlayer("/Effects/notRun.mp3");
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
        KeyValue botonEscaparK = new KeyValue(botonEscapar.disableProperty(), true);

        KeyFrame frame0s = new KeyFrame(new Duration(0), botonEscaparK, monstruoVidaBarraK, monstruoVidaK);

        KeyValue jugadorDamageK = new KeyValue(jugadorDamage.textProperty(), String.valueOf(damage));

        botonEscaparK = new KeyValue(botonEscapar.disableProperty(), false);

        KeyFrame frame10s = new KeyFrame(new Duration(800), botonEscaparK);

        KeyFrame frame15s = new KeyFrame(new Duration(1350), jugadorDamageK);

        KeyFrame frame175s = new KeyFrame(new Duration(1550), monstruoVidaBarraK, jugadorDamageK);

        monstruo.calcularVida(damage);
        monstruoVidaBarraK = new KeyValue(monstruoBarraVida.progressProperty(), monstruo.getVida() / monstruo.getVida_maxima());
        monstruoVidaK = new KeyValue(monstruoVida.textProperty(), String.valueOf(monstruo.getVida()));

        KeyFrame frame200s = new KeyFrame(new Duration(1800), botonEscaparK, monstruoVidaBarraK, monstruoVidaK);
        aplicarColorBarra(jugadorBarraVida, progresoJugador);
        aplicarColorBarra(monstruoBarraVida, progresoMonstruo);
        return new KeyFrame[]{frame0s, frame10s, frame15s, frame175s, frame200s};
    }

    private KeyFrame[] accionMonstruo(int time) {
        if (monstruo.Muerto()) return new KeyFrame[0];

        double progresoJugador = jugadorBarraVida.getProgress();
        double progresoMonstruo = monstruoBarraVida.getProgress();

        KeyValue jugadorVidaBarraK = new KeyValue(jugadorBarraVida.progressProperty(), jugador.getVida() / jugador.getVida_maxima());
        KeyValue jugadorVidaK = new KeyValue(jugadorVida.textProperty(), String.valueOf(jugador.getVida()));

        KeyFrame frame0s = new KeyFrame(new Duration(0), jugadorVidaBarraK, jugadorVidaK);

        int damage = monstruo.iaAccion(jugador);

        jugador.calcularVida(damage);
        KeyValue monstruoDamageK = new KeyValue(monstruoDamage.textProperty(), String.valueOf(damage));

        KeyFrame frame1350s = new KeyFrame(new Duration(time + 1350), monstruoDamageK);

        KeyFrame frame1550s = new KeyFrame(new Duration(time + 1550), jugadorVidaBarraK, monstruoDamageK);

        jugadorVidaBarraK = new KeyValue(jugadorBarraVida.progressProperty(), jugador.getVida() / jugador.getVida_maxima());
        monstruoDamageK = new KeyValue(monstruoDamage.textProperty(), null);
        jugadorVidaK = new KeyValue(jugadorVida.textProperty(), String.valueOf(jugador.getVida()));

        KeyFrame frame1800s = new KeyFrame(new Duration(time + 1800), monstruoDamageK, jugadorVidaBarraK, jugadorVidaK);

        aplicarColorBarra(jugadorBarraVida, progresoJugador);
        aplicarColorBarra(monstruoBarraVida, progresoMonstruo);

        // Ejecutar el método para actualizar el contenedor de cartas y habilitar el botón de escapar
        actualizarContenedorCartas();
        botonEscapar.setDisable(false);

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
            } else if (monstruo.Muerto() && !monstruoMuerto) {
                monsterLoader.actualizarRegistrado(idDelMonstruo);
                monstruoMuerto = true;
                EffectPlayer efectos;
                efectos = new EffectPlayer("/Effects/Win.mp3");
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
        mapsBSalud.setStage(stage);
        mapsBSalud.setX(x - 24);
        mapsBSalud.setY(y - 24);
        mapsBSalud.musica();

        if (mapsBSalud.getTimer() != null){
            mapsBSalud.getTimer().stop();
        }

        switch (I) {
            case 1:
                mapsBSalud.calleInstituto(stage);
                break;
            case 2:
                mapsBSalud.calleInstituto2(stage);
                break;
            case 3:
                mapsBSalud.plaza(stage);
                break;
            case 4:
                mapsBSalud.plaza2(stage);
                break;
            case 5:
                mapsBSalud.arcade(stage);
                break;
            case 6:
                mapsBSalud.paradaGuagua(stage);
                break;
            case 7:
                mapsBSalud.institutoPlaza(stage);
                break;
            case 8:
                mapsBSalud.placita(stage);
                break;
            case 9:
                mapsBSalud.lobbyInstituto(stage);
                break;
            case 10:
                mapsBSalud.lobbyInstituto2(stage);
                break;
            case 11:
                mapsBSalud.subidaInstituto(stage);
                break;
            case 12:
                mapsBSalud.subidaInstituto2(stage);
                break;
            case 13:
                mapsBSalud.lobbyAulas(stage);
                break;
            case 14:
                mapsBSalud.lobbyAulas2(stage);
                break;
            default:
                mapsBSalud.calleInstituto(stage);
        }

        mapsBSalud.timerStart();

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
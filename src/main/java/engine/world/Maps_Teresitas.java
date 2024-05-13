package engine.world;


import com.google.gson.Gson;
import engine.MusicPlayerSt;
import engine.combate.peleitas.FightController;
import engine.jobs.keyLifeGuard.GameController;
import engine.miniDoomII.MinijuegoController;
import engine.objects.Camera;
import engine.objects.Elements;
import engine.objects.NPC;
import engine.objects.Player;
import engine.tienda.TiendaController;
import engine.ui.Dialog;
import engine.ui.FPSMonitor;
import engine.ui.PlayerState;
import engine.ui.inGameMenu.InGameMenu;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Maps_Teresitas {
    private Pane root = new Pane();
    private Pane dialogRoot = new Pane();
    private LinkedList<ObstacleTile> barrier;
    private Player player;

    private NPC npc;

    private AnimationTimer timer;

    private Elements elements;

    TiendaController tiendaController;

    private Scene scene;

    private double x = 290;

    private double y = 400;

    private Image BackgroundImage;
    private BackgroundSize size = new BackgroundSize(-1, -1, false, false, false, false);

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    private ImageView character_image = new ImageView(new Image("Down2.png"));

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    private Dialog dialog;

    private PlayerState playerState;

    private int sumadorCombate = 0;
    Elements car;

    private Stage stage;

    private int i = 0;

    //Constructor

    public Maps_Teresitas() {

        this.barrier = new LinkedList<>();
        //timerStart();
    }

    public void musica(){
        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/teresitasMusic.mp3");
        MusicPlayerSt.setVolume(volumen);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void timerStart(){
        timer = new AnimationTimer() {
            public void handle(long l) {
                mapsChanger();
            }
        };
        this.timer.start();
    }

    public void worldBasics(Image Background){
        this.root = new Pane();
        dialogRoot = new Pane();

        //Colisiones
        this.barrier = new LinkedList<ObstacleTile>();

        //
        this.stage.setTitle("Las Teresitas");

        // Crear un nuevo Pane y agregar ambos root y dialogRoot a este nuevo Pane
        root.setPrefSize(BackgroundImage.getWidth(), BackgroundImage.getHeight());
        dialogRoot.setPrefSize(BackgroundImage.getWidth(), BackgroundImage.getHeight());
        Pane mainPane = new Pane();
        mainPane.getChildren().addAll(root, dialogRoot);
        mainPane.setPrefSize(BackgroundImage.getWidth(), BackgroundImage.getHeight());

        //Background
        Background background = new Background(new BackgroundImage[]{new BackgroundImage(Background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)});
        this.root.setBackground(background);

        this.scene = new Scene(mainPane, 800.0, 800.0); // Usar el nuevo Pane como raíz de la escena
        this.stage.setScene(this.scene);
        this.stage.show();

        //Mostrar FPS
        //FPSMonitor fps = new FPSMonitor(stage);
    }

    public void playerBasics(){
        //player
        character_image.setLayoutX(x);
        character_image.setLayoutY(y);
        this.root.getChildren().add(character_image);

        player = new Player(this.root, this.scene, this.barrier, character_image);
        player.setY(205);
        player.setX(350);
        Camera camera = new Camera(this.root, player);

        //Dialog
        dialog = new Dialog(this.dialogRoot, stage, this.scene);

        //InGameMenu
        InGameMenu inGameMenu = new InGameMenu(this.dialogRoot, stage, this.scene);

        //PlayerState
        playerState = new PlayerState(this.dialogRoot, stage, this.scene);

        this.dialogRoot.getChildren().add(playerState.getHealth());
        this.dialogRoot.getChildren().add(playerState.getStamina());
        this.dialogRoot.getChildren().add(dialog.getDialog());
        this.dialogRoot.getChildren().add(dialog.getDialogText());
        this.dialogRoot.getChildren().add(inGameMenu.getSmartphone());
        this.dialogRoot.getChildren().add(inGameMenu.getMenuPane());

        dialogRoot.toFront();

        player.addMenu(inGameMenu);
    }

    public void carBasics() {
        car = new Elements(this.root, stage, 13, 0, 350);
        car.elements_image.setOpacity(0);

        this.elements = car;
        car.elementsBasicsNC(car.getX(), car.getY());
        car.carTimer(player);
        car.timer.start();
    }

    //Teresitas01
    public void teresitas01(Stage stage) {

        i = 0;

        BackgroundImage = new Image("/Teresitas/teresitas01.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas01.json");

        Elements element = new Elements(this.root, stage, 2, 520, 405);

        //Elements
        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 50, 180, barrier);

        playerBasics();
        player.setY(205);
        player.setX(350);

        player.addElements(element);
        element.addDialogs(dialog, "Parada de la 026", "parada");

        timerStart();

    }

    //Teresitas02
    public void teresitas02(Stage stage) {

        i = 1;

        BackgroundImage = new Image("/Teresitas/teresitas02.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas02.json");


        playerBasics();

    }

    //Teresitas03
    public void teresitas03(Stage stage) {

        i = 2;

        BackgroundImage = new Image("/Teresitas/teresitas03.png");

        worldBasics(BackgroundImage);

        System.out.println("2x: " + player.getX() + " y: " + player.getY());

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas03.json");


        playerBasics();

        System.out.println("2x: " + player.getX() + " y: " + player.getY());

    }

    //Teresitas04
    public void teresitas04(Stage stage) {

        i = 3;

        BackgroundImage = new Image("/Teresitas/teresitas04.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas04.json");

        NPC npc = new NPC(this.root, stage,10, 320, 570, "Right", barrier);
        this.npc = npc;

        playerBasics();
        player.addNPC(npc);
        npc.addDialogs(dialog, "Mañana tengo que evaluar unas presentaciones\nen el trabajo.", "Pero puedo venir hoy a la playa porque ya preparé todo \nlo que tenía que preparar.");
    }

    //Teresitas05
    public void teresitas05(Stage stage) {

        i = 4;

        BackgroundImage = new Image("/Teresitas/teresitas05.png");

        worldBasics(BackgroundImage);

        NPC npc = new NPC(this.root, stage,5, 470, 231, "Left", barrier);
        this.npc = npc;

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas05.json");


        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "¿Eh? ¿Quieres trabajar como socorrista?\nInteresante.", "Por cierto, mírate Jujutsu, Gojo muere.");

    }
    //Teresitas06
    public void teresitas06(Stage stage) {

        i = 5;

        BackgroundImage = new Image("/Teresitas/teresitas06.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas06.json");

        Elements tienda = new Elements(this.root, stage, 18, 140, 270);
        this.elements = tienda;
        tienda.elementsBasics(tienda.getX(), tienda.getY(), 91, 172, barrier);

        playerBasics();
        player.addElements(tienda);
        tienda.addDialogs(dialog, "Ven aquí chaval, a partir de hoy serás\nel nuevo socorrista, felicidades.", "¿Eh, que no sabes nadar?","Eso no importa, tú ponte aquí y ya está.");
    }
    //Teresitas07
    public void teresitas07(Stage stage) {

        i = 6;

        BackgroundImage = new Image("/Teresitas/teresitas07.png");

        worldBasics(BackgroundImage);

        NPC npc = new NPC(this.root, stage,18, 300, 110, "Down", barrier);
        this.npc = npc;

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas07.json");

        Elements tienda = new Elements(this.root, stage, 17, 468, 414);
        this.elements = tienda;
        tienda.elementsBasics(tienda.getX(), tienda.getY(), 88, 172, barrier);

        playerBasics();
        player.addElements(tienda);
        player.addNPC(npc);
        npc.addDialogs(dialog, "Odiame por ser otaku.", "Adelante,estas en tu derecho,abla a mis espaldas\ny az de mi vida un infierno,lo aguantare con\nuna sonridsa en la cara,insulta al anime,a", "algun amigo tanbien otaku,y el infierno te parecera un\nhotel conparado conmigo");
    }
    //Teresitas08
    public void teresitas08(Stage stage) {

        i = 7;

        BackgroundImage = new Image("/Teresitas/teresitas08.png");

        worldBasics(BackgroundImage);

        NPC npc = new NPC(this.root, stage,1, 300, 110, "Down", barrier);
        this.npc = npc;

        NPC npc2 = new NPC(this.root, stage,2, 170, 505, "Right", barrier);
        this.npc = npc2;

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Teresitas/teresitas08.json");


        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Mi colega el JuanFran está cogiendo olas.","Le viene bien, trabaja demasiado, debe descansar...");
        player.addNPC(npc2);
        npc2.addDialogs(dialog, "Creo que están buscando a socorristas para contratar.", "Me lo ofreció insistentemente y casi que\ntuve que huir de allí.", "Al chico se le veía bastante desesperado.");

    }

    /*public void doom(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tablero.fxml"));
        MinijuegoController minijuego = new MinijuegoController();
        minijuego.setStage(stage);
        loader.setController(minijuego);
        Parent root = loader.load();
        stage.setTitle("Mini Doom II");
        stage.setScene(new Scene(root, 800, 800));

        BackgroundImage = new Image("doomFondo.png");

        Background background = new Background(new BackgroundImage[]{new BackgroundImage(BackgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)});
        this.root.setBackground(background);

        stage.show();

        MinijuegoController controlador = loader.getController();
        controlador.actualizarTiempo();

    }*/

    public void combate(Stage stage, int i) throws Exception {
        System.out.println("control 1: " + player.getX() + " i: " + i);
        timer.stop();
        player.getTimer().stop();
        System.out.println("control 2: " + player.getX() + " i: " + i);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fight.fxml"));
        FightController fight = new FightController(1, player.getX(), player.getY());

        System.out.println("control 3: " + player.getX() + " i: " + i);
        fight.setI(i);
        fight.setStage(stage);

        System.out.println("control 4: " + player.getX() + " i: " + i);
        loader.setController(fight);
        Parent root = loader.load();

        System.out.println("control 5: " + player.getX() + " i: " + i);
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Fight");

        System.out.println("control 6: " + player.getX() + " i: " + i);
        stage.setScene(scene);
        stage.show();

        System.out.println("control 7: " + player.getX() + " i: " + i);

        System.out.println("control 8: " + player.getX() + " i: " + i);
    }

    public void tienda(Stage stage, int id) throws Exception {
        FadeOut(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tiendaMenu.fxml"));
            TiendaController tiendaController = new TiendaController();
            tiendaController.setStage(stage);
            tiendaController.setI(id);
            loader.setController(tiendaController);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root, 800, 800);
            stage.setTitle("Tienda");
            stage.setScene(scene);
            stage.show();

            //controller.FadeIn();


            player = new Player(this.root, scene, this.barrier, character_image);
        });
    }

    public void pantallaCarga (Stage stage, int I) throws Exception {
        BackgroundImage = new Image("pantallaCarga.png");
        final String[] CAMINAR_IMAGENES = {"Down1.png", "Down2.png", "Down3.png"};
        final int DURACION_FRAME_MILLIS = 100; // Duración de cada frame en milisegundos
        final int[] indiceImagenActual = {0};
        ImageView animacion = new ImageView();
        animacion.setX(528);
        animacion.setY(730);
        Image[] imagenes = new Image[CAMINAR_IMAGENES.length];
        for (int i = 0; i < CAMINAR_IMAGENES.length; i++) {
            imagenes[i] = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + CAMINAR_IMAGENES[i])));
        }
        // Crear la animación
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(DURACION_FRAME_MILLIS), event -> {
                    indiceImagenActual[0] = (indiceImagenActual[0] + 1) % CAMINAR_IMAGENES.length;
                    animacion.setImage(imagenes[indiceImagenActual[0]]);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        worldBasics(BackgroundImage);
        this.root.getChildren().add(animacion);
        timeline.play();
        switch (I){
            case 1:
                tienda(stage, 3);
                break;
            case 2:
                trabajoSocorrista(stage);
                break;
            case 3:
                //doom(stage);
                break;
        }

//        playerBasics();
    }

    public void trabajoSocorrista(Stage stage) throws Exception {
        FadeOut(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Jobs/keyLifeGuard.fxml"));
            GameController controller = new GameController();
            controller.setStage(stage);
            loader.setController(controller);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root, 800, 800);
            scene.setOnKeyPressed(controller::handleKeyPressed);
            stage.setTitle("Socorrista");
            stage.setScene(scene);
            stage.show();

            //controller.FadeIn();
        });
    }

    public static void FadeOut(Runnable onFadeOutComplete) {
        Rectangle nuevoContenido = new Rectangle(800, 800, Color.BLACK);
        nuevoContenido.setOpacity(0); // Iniciar con opacidad 0 para el FadeIn

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), nuevoContenido);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Agregar un evento para manejar el final de la transición
        fadeIn.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Llamar a la acción después de que la transición haya terminado
                onFadeOutComplete.run();
            }
        });
    }

    public void createObstacleTile(double w, double h, double x, double y) {
        ObstacleTile tile = new ObstacleTile(w, h, x, y);
        this.root.getChildren().add(tile);
        this.barrier.add(tile);

    }

    private void cargarColisionesDesdeJSON(String rutaMapa) {
        Gson gson = new Gson();
        try (InputStream inputStream = getClass().getResourceAsStream(rutaMapa);
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            List<List<Double>> colisiones = gson.fromJson(reader, List.class);
            for (List<Double> colision : colisiones) {
                double w = colision.get(0);
                double h = colision.get(1);
                double x = colision.get(2);
                double y = colision.get(3);
                this.createObstacleTile(w, h, x, y);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la ruta");
        }
    }

    public int getSumadorCombate() {
        return sumadorCombate;
    }

    public void setSumadorCombate(int sumadorCombate) {
        this.sumadorCombate = sumadorCombate;
    }

//    private boolean shouldStartRandomCombat() {
//        // Verificar si el personaje se está moviendo
//        if (!player.isMoving()) {
//            return false;
//        }
//        double probability = 0.001;
//
//        // Generar un número aleatorio entre 0 y 1
//        double randomValue = random.nextDouble();
//
//        // Verificar si el número aleatorio es menor que la probabilidad
//        return randomValue < probability;
//    }ç



    private void startRandomCombat() {
        System.out.println("¡Combate aleatorio!");
        System.out.println(player.getX());

        this.root = new Pane();

        try {
            combate(stage, i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mapsSelector(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MapsSelector.fxml"));
        MapSelector controller = new MapSelector();
        controller.setStage(stage);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Tienda");
        stage.setScene(scene);
        stage.show();
    }

    private void mapsChanger() {
//        if (shouldStartRandomCombat() && i != 6 && i != 9) {
//            startRandomCombat();
//        }
        //teresitas01
        if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 0) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            teresitas02(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 50.0 && i == 0) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX()-24;
            y = player.getY()+620;
            teresitas03(stage);
            timer.start();
        }
        //teresitas02
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 1) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            teresitas01(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 50.0 && i == 1) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX()-24;
            y = player.getY()+620;
            teresitas04(stage);
            timer.start();
        }
        //teresitas03
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            teresitas04(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 50.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX()-24;
            y = player.getY()+620;
            teresitas05(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 700.0 && player.getY() <= 800.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 24;
            y = player.getY() - 650;
            teresitas01(stage);
            timer.start();
        }
        //teresitas04
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            teresitas03(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 50.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() -24;
            y = player.getY()+620;
            teresitas06(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 24;
            y = player.getY() - 650;
            teresitas02(stage);
            timer.start();
        }
        //teresitas05
        if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 4) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            teresitas06(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 50.0 && i == 4) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() -24;
            y = player.getY()+620;
            teresitas07(stage);

            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 4) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 24;
            y = player.getY() - 650;
            teresitas03(stage);
            timer.start();
        }
        //teresitas06
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 5) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            teresitas05(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 50.0 && i == 5) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX()-24;
            y = player.getY()+620;
            teresitas08(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 5) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 24;
            y = player.getY() - 650;
            teresitas04(stage);
            timer.start();
        }
        //teresitas07
        else if (player.getX() >= 750.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            teresitas08(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 24;
            y = player.getY() - 650;
            teresitas05(stage);
            timer.start();
        }
        //teresitas08
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 7) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            teresitas07(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 7) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 24;
            y = player.getY() - 650;
            teresitas06(stage);
            timer.start();
        }
        else {
            //System.out.println("x: " + x + ", y: " + y + ", i: " + this.i);
        }

    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
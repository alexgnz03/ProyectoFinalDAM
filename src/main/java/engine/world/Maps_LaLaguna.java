package engine.world;


import com.google.gson.Gson;
import engine.MusicPlayerSt;
import engine.entities.*;
import engine.jobs.mcclicker.ClickerController;
import engine.miniDoomII.MinijuegoController;
//import engine.jobs.minijuego1.JuegoController;
//import engine.jobs.minijuego2.GameController;
import engine.tienda.TiendaController;
import engine.ui.Dialog;
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

public class Maps_LaLaguna {
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

    private ImageView character_image = new ImageView(new Image("Player/Down2.png"));

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

    public Maps_LaLaguna() {

        this.barrier = new LinkedList<>();

    }

    public void musica(){
        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/lalagunaMusic.mp3");
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

        this.stage.setTitle("La Laguna");

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

    //Intercambiador
    public void intercambiador(Stage stage) {

        i = 0;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/intercambiador.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/intercambiador.json");

        // Elements
        Elements parada = new Elements(this.root, stage, 2, 26, 482);

        this.elements = parada;
        parada.elementsBasics(parada.getX(), parada.getY(), 50, 180, barrier);


        playerBasics();
        player.setY(65);
        player.setX(550);

        //Elements

        Elements element = new Elements(this.root, stage, 5, 624, 486);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 91, 314, barrier);

        player.addElements(element);
        player.addElements(parada);

        System.out.println("Character x + y = " + player.getX() + " " + player.getY());

        timerStart();
    }

    //Trinidad01
    public void trinidad01(Stage stage) {

        i = 1;


        BackgroundImage = new Image("Maps/Fondos/LaLaguna/trinidad01.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/trinidad01.json");

        //NPCs
        NPC npc = new NPC(this.root, stage,13, 692, 201, "left", barrier);
        this.npc = npc;

        playerBasics();

        carBasics();

        Elements front = new Elements(this.root, stage, 6, 0, 0);

        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());
        player.addElements(front);

        player.addNPC(npc);
        npc.addDialogs(dialog, "Tengo un restaurante aquí en La Laguna.", "Pero desde que abrieron ese McDon't ya nadie va...", "...");
    }

    //Trinidad02
    public void trinidad02(Stage stage) {

        i = 2;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/trinidad02.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/trinidad02.json");

        //NPCs
        NPC npc = new NPC(this.root, stage,17, 174, 110, "Down", barrier);
        this.npc = npc;

        NPC npc2 = new NPC(this.root, stage,18, 250, 110, "Down", barrier);
        this.npc = npc2;

        Elements tranvia = new Elements(this.root, stage, 12, 0, 468);
        this.elements = tranvia;
        tranvia.elementsBasics(tranvia.getX(), tranvia.getY(), 272, 92, barrier);

        playerBasics();
        carBasics();

        Elements front = new Elements(this.root, stage, 7, 0, 0);

        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());

        player.addElements(front);
        player.addElements(tranvia);

        player.addNPC(npc);
        player.addNPC(npc2);
        npc.addDialogs(dialog, "No es Gracias, es 'arigatou'", "No es Adiós, es 'Sayonara'", "No es idiota, es 'Baka'", "No es Te Quiero, es 'Daisuki'", "No es Te Amo, es 'Aishiteru'", "No es Hola, es 'Konnichiwa'", "No es Buenos Días, es 'Ohayo'", "No es Buenas Noches, es 'Kombawa'", "No es ser raro, es ser OTAKU <3");
        npc2.addDialogs(dialog, "He venido con mi novio a comer al McDon't pero lleva un\nrato balbuceando cosas sin sentido", "Como siga así lo dejo.");

        timerStart();
    }

    //Trinidad03
    public void trinidad03(Stage stage) {

        i = 3;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/trinidad03.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/trinidad03.json");

        Elements tranvia = new Elements(this.root, stage, 11, 144, 468);
        this.elements = tranvia;
        tranvia.elementsBasics(tranvia.getX(), tranvia.getY(), 656, 88, barrier);

        NPC npc = new NPC(this.root, stage,3, 357, 563, "Up", barrier);
        this.npc = npc;

        NPC npc2 = new NPC(this.root, stage,7, 200, 108, "Left", barrier);
        this.npc = npc2;

        playerBasics();

        player.addNPC(npc);
        player.addNPC(npc2);

        carBasics();

        Elements front = new Elements(this.root, stage, 8, 0, 0);
        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());

        player.addElements(front);
        player.addElements(tranvia);

        npc.addDialogs(dialog,"...");
        npc2.addDialogs(dialog,"¿Eh, vas al Mr Snack?","No te lo recomiendo. \nEstá carísimo últimamente.", "Pero bueno, allá tú con tus decisiones...");
    }

    //Trinidad04
    public void trinidad04(Stage stage) {

        i = 4;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/trinidad04.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/trinidad04.json");

        playerBasics();

        carBasics();

        Elements front = new Elements(this.root, stage, 9, 0, 0);

        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());
        player.addElements(front);

    }

    //Trinidad05
    public void trinidad05(Stage stage) {

        i = 5;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/trinidad05.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/trinidad05.json");

        NPC npc = new NPC(this.root, stage,16, 173, 160, "Down", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);

        Elements front = new Elements(this.root, stage, 10, 664, 0);

        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());
        player.addElements(front);

        npc.addDialogs(dialog,"*Respira agitadamente* Nya~ Ho-Ho-Hola... ^w^");
    }

    //TrinidadCatedral
    public void trinidadCatedral(Stage stage) {

        i = 6;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/trinidadCatedral.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/trinidadCatedral.json");


        playerBasics();

    }

    //mcdont
    public void mcdont(Stage stage) {

        i = 7;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/mcdont.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/mcdont.json");

        //NPCs
        Elements dependiente = new Elements(this.root, stage, 14, 445, 530);
        this.elements = dependiente;
        dependiente.elementsBasics(dependiente.getX(), dependiente.getY(), 48, 48, barrier);

        playerBasics();

        player.addElements(dependiente);
        dependiente.addDialogs(dialog, "¿Eh? \n¿Vienes a trabajar aquí?", "Bueno, está bien, tú sabrás \nlo que haces supongo...");
    }

    //casa
    public void casa(Stage stage) {

        i = 8;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/casa.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/casa.json");

        //NPCs
        Elements cama = new Elements(this.root, stage, 15, 184, 388);
        this.elements = cama;
        cama.elementsBasics(cama.getX(), cama.getY(), 84, 52, barrier);

        playerBasics();

        player.addElements(cama);
        cama.addDialogs(dialog, "STAMINA RECUPERADA");
        timerStart();
    }

    //catedral01
    public void catedral01(Stage stage) {

        i = 9;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/catedral01.png");
        worldBasics(BackgroundImage);

        NPC npc = new NPC(this.root, stage,15, 550, 565, "Left", barrier);
        this.npc = npc;

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/catedral01.json");

        playerBasics();
        player.addNPC(npc);

        npc.addDialogs(dialog, "¿Por qué hay un salón arcade al lado de la iglesia?", "Osea no tiene sentido. En la vida real no hay nada de eso \nahí.", "¿De hecho por qué hay un salón arcade en pleno 2024?", "¿Y que sentido tiene que por jugar a una máquina \narcade te aumenten estadísticas de combate?", "¿Que clase de persona diseñó este videojuego?");

    }

    //catedral02
    public void catedral02(Stage stage) {

        i = 10;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/catedral02.png");
        worldBasics(BackgroundImage);

        NPC npc = new NPC(this.root, stage,11, 405, 460, "Right", barrier);
        this.npc = npc;
        NPC npc2 = new NPC(this.root, stage,9, 465, 460, "Left", barrier);
        this.npc = npc2;

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/catedral02.json");

        playerBasics();

        player.addNPC(npc);

        npc.addDialogs(dialog, "(No entiendes absolutamente nada de lo que están \ndiciendo)");
        player.addNPC(npc2);
        npc2.addDialogs(dialog, "(No entiendes absolutamente nada de lo que están \ndiciendo)");
    }

    //arcade
    public void arcade(Stage stage) {

        i = 11;

        BackgroundImage = new Image("Maps/Fondos/arcade.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/arcade.json");

        Elements element = new Elements(this.root, stage, 1, 545, 340);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 60, 87, barrier);

        playerBasics();
        player.addElements(element);
        element.addDialogs(dialog, "AVISO: Jugar cuesta 20€, comprueba que tengas\n dinero suficiente...");
    }

    //aeropuerto
    public void aeropuerto(Stage stage) {

        i = 12;

        BackgroundImage = new Image("Maps/Fondos/LaLaguna/aeropuerto.png");
        worldBasics(BackgroundImage);

        Elements azafata = new Elements(this.root, stage, 19, 435, 325);
        this.elements = azafata;
        azafata.elementsBasics(azafata.getX(), azafata.getY(), 48, 48, barrier);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/LaLaguna/aeropuerto.json");


        playerBasics();

        player.addElements(azafata);
        azafata.addDialogs(dialog, "¿Eh? Este billete cuesta 5000€", "Lo siento. Pero vuelve cuando tengas el dinero.");

        timerStart();
    }

    public void pantallaCarga (Stage stage, int I) throws Exception {
        BackgroundImage = new Image("Maps/pantallaCarga.png");
        final String[] CAMINAR_IMAGENES = {"Player/Down1.png", "Player/Down2.png", "Player/Down3.png"};
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
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(event -> {
            try {
                switch (I) {
                    case 1:
                        tienda(stage, 1);
                        break;
                    case 2:
                        mcClicker(stage);
                        break;
                    case 3:
                        doom(stage);
                        break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        pause.play();

//        playerBasics();
    }

    public void tienda(Stage stage, int id) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tienda/tiendaMenu.fxml"));
            tiendaController = new TiendaController();
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


            player = new Player(this.root, scene, this.barrier, character_image);
    }

    public void mcClicker(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Jobs/mcClicker.fxml"));
            ClickerController controller = new ClickerController();
            controller.setStage(stage);
            loader.setController(controller);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root, 800, 800);
            stage.setTitle("McClicker");
            stage.setScene(scene);
            stage.show();
    }

    public void doom(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Doom2mini/doomView.fxml"));
        MinijuegoController minijuego = new MinijuegoController(1);
        minijuego.setStage(stage);
        loader.setController(minijuego);
        Parent root = loader.load();
        stage.setTitle("Doom II mini");
        stage.setScene(new Scene(root, 800, 800));

        BackgroundImage = new Image("Doom2mini/doomFondo.png");

        Background background = new Background(new BackgroundImage[]{new BackgroundImage(BackgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)});
        this.root.setBackground(background);

        stage.show();

        MinijuegoController controlador = loader.getController();
        controlador.actualizarTiempo();

        player = new Player(this.root, scene, this.barrier, character_image);

    }

    public void mapsSelector(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Maps/MapSelector/MapsSelector.fxml"));
        MapSelector controller = new MapSelector();
        controller.setStage(stage);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Tienda");
        stage.setScene(scene);
        stage.show();
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

    private void mapsChanger() {
        //Trinidad01
        if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 1) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            trinidad02(stage);
            timer.start();
        }
        //trinidad02
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            trinidad03(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            trinidad01(stage);
            timer.start();
        }
        else if (player.getX() >= 732.0 && player.getX() <= 800.0 && player.getY() >= 63.0 && player.getY() <= 143.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 320;
            y = 720;
            mcdont(stage);
            timer.start();
        }
        //trinidad03
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            trinidad04(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            trinidad02(stage);
            timer.start();
        }
        else if (player.getX() >= 88.0 && player.getX() <= 143.0 && player.getY() >= 63.0 && player.getY() <= 143.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 500;
            y = 50;
            try {
                pantallaCarga(stage, 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            //timer.start();
        }
        //trinidad04
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 4) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            trinidad05(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 4) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            trinidad03(stage);
            timer.start();
        }
        //trinidad05
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 5) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 710;
            y = 408;
            trinidadCatedral(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 5) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            trinidad04(stage);
            timer.start();
        }
        //trinidadCatedral
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 700;
            y = 723;
            catedral01(stage);
            timer.start();
        }
        else if (player.getX() >= 328.0 && player.getX() <= 384.0 && player.getY() >= 256.0 && player.getY() <= 336.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 460;
            y = 700;
            casa(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 100;
            y = 693;
            trinidad05(stage);
            timer.start();
        }
        //mcdont
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 775.0 && player.getY() <= 800.0 && i == 7) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 635;
            y = 150;
            trinidad02(stage);
            timer.start();
        }
        //casa
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 775.0 && player.getY() <= 800.0 && i == 8) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 328;
            y = 357;
            trinidadCatedral(stage);
            timer.start();
        }
        //catedral01
        else if (player.getX() >= 0.0 && player.getX() <= 23.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 9) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            catedral02(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 9) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 100;
            y = 406;
            trinidadCatedral(stage);
            timer.start();
        }
        else if (player.getX() >= 703.0 && player.getX() <= 755.0 && player.getY() >= 0.0 && player.getY() <= 672.0 && i == 9) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 370;
            y = 725;
            arcade(stage);
            timer.start();
        }
        //catedral02
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 10) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            catedral01(stage);
            timer.start();
        }
        //arcade
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 11) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 700;
            y = 680;
            catedral01(stage);
            timer.start();
        }
        //aeropuerto
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 12) {
            // Hacer algo si las escenas son iguales
            System.out.println("Hello");
            player.getTimer().stop();
            timer.stop();

            x = 700;
            y = 680;
            try {
                mapsSelector(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
package engine.world;


import com.google.gson.Gson;
import dbo.ObjetosData;
import dbo.PlayerData;
import engine.combate.peleitas.FightController;
import engine.minijuego.MinijuegoController;
import engine.objects.Camera;
import engine.objects.Elements;
import engine.objects.NPC;
import engine.objects.Player;
import engine.tienda.TiendaController;
import engine.ui.Dialog;
import engine.ui.FPSMonitor;
import engine.ui.PlayerState;
import engine.ui.inGameMenu.InGameMenu;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
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

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Maps2 {
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

    public Maps2() {

        this.barrier = new LinkedList<>();
        //timerStart();
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
        this.stage.setTitle("Aparcamientos");

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
        FPSMonitor fps = new FPSMonitor(stage);
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

        BackgroundImage = new Image("intercambiador.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/LaLaguna/intercambiador.json");

        //NPCs

        NPC npc = new NPC(this.root, stage, 2, 630, 150,"left", barrier);
        this.npc = npc;

        // Elements
        playerBasics();
        player.setY(65);
        player.setX(550);

        //Elements

        Elements element = new Elements(this.root, stage, 5, 624, 486);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 91, 314, barrier);

        player.addNPC(npc);
        player.addElements(element);
        npc.addDialogs(dialog, "...", "pepe", "cxzxc", "Hola mi amigo", "Cómo estás hoy", "Yo muy bien");

        System.out.println("Character x + y = " + player.getX() + " " + player.getY());

        timerStart();

        //Debug
//        ObjetosData ob = new ObjetosData();
//        try {
//            //ob.insertarDatosObjeto("refresco", "bebida refrescante", 5);
//            ob.mostrarDatosObjeto();
////            ob.insertarDatosInventario(8);
////            ob.mostrarDatosInventario();
//        } catch (SQLException ex){
//            System.out.println("Error al insertar: " + ex);
//        }
    }

    //Trinidad01
    public void trinidad01(Stage stage) {

        i = 1;

        BackgroundImage = new Image("trinidad01.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("src/main/resources/Maps/LaLaguna/trinidad01.json");

        playerBasics();

        carBasics();

        Elements front = new Elements(this.root, stage, 6, 0, 0);

        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());
        player.addElements(front);

        timerStart();
    }

    //Trinidad02
    public void trinidad02(Stage stage) {

        i = 2;

        BackgroundImage = new Image("trinidad02.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("src/main/resources/Maps/LaLaguna/trinidad02.json");

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

    }

    //Trinidad03
    public void trinidad03(Stage stage) {

        i = 3;

        BackgroundImage = new Image("trinidad03.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("src/main/resources/Maps/LaLaguna/trinidad03.json");

        Elements tranvia = new Elements(this.root, stage, 11, 144, 468);
        this.elements = tranvia;
        tranvia.elementsBasics(tranvia.getX(), tranvia.getY(), 656, 88, barrier);

        playerBasics();

        carBasics();

        Elements front = new Elements(this.root, stage, 8, 0, 0);
        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());

        player.addElements(front);
        player.addElements(tranvia);

    }

    //Trinidad04
    public void trinidad04(Stage stage) {

        i = 4;

        BackgroundImage = new Image("trinidad04.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("src/main/resources/Maps/LaLaguna/trinidad04.json");


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

        BackgroundImage = new Image("trinidad05.png");
        worldBasics(BackgroundImage);

        car.timer.stop();

        cargarColisionesDesdeJSON("src/main/resources/Maps/LaLaguna/trinidad05.json");


        playerBasics();

        Elements front = new Elements(this.root, stage, 10, 664, 0);

        this.elements = front;
        front.elementsBasicsNC(front.getX(), front.getY());
        player.addElements(front);

    }

    //TrinidadCatedral
    public void trinidadCatedral(Stage stage) {

        i = 6;

        BackgroundImage = new Image("trinidadCatedral.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("src/main/resources/Maps/LaLaguna/trinidadCatedral.json");


        playerBasics();

    }

    //placita
    public void placita(Stage stage) {

        i = 8;

        BackgroundImage = new Image("placita.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/placita.json");

        //NPCs
        NPC npc = new NPC(this.root, stage, 4, 425, 640,"down", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "...");

    }

    public void minijuego(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tablero.fxml"));
        MinijuegoController minijuego = new MinijuegoController();
        minijuego.setStage(stage);
        loader.setController(minijuego);
        Parent root = loader.load();
        stage.setTitle("Minijuego de Dianas");
        stage.setScene(new Scene(root, 800, 800));

        BackgroundImage = new Image("doomFondo.png");

        Background background = new Background(new BackgroundImage[]{new BackgroundImage(BackgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)});
        this.root.setBackground(background);

        stage.show();

        MinijuegoController controlador = loader.getController();
        controlador.actualizarTiempo();

    }

    public void tienda(Stage stage, int id) throws Exception {
        FadeOut(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tiendaMenu.fxml"));
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

            //controller.FadeIn();


            player = new Player(this.root, scene, this.barrier, character_image);
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
        try (FileReader reader = new FileReader(rutaMapa)) {
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
//        if (!isMoving()) {
//            return false;
//        }
//        double probability = 0.001;
//
//        // Generar un número aleatorio entre 0 y 1
//        double randomValue = random.nextDouble();
//
//        // Verificar si el número aleatorio es menor que la probabilidad
//        return randomValue < probability;
//    }
//
//    private void startRandomCombat() {
//        System.out.println("¡Combate aleatorio!");
//        System.out.println(x);
//
//        this.timer.stop();
//        this.root = new Pane();
//
//        mapsInstance.setX(290);
//        mapsInstance.setY(102);
//        try {
//            sumador += 1;
//            mapsInstance.combate(stage, i);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

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
                tienda(stage, 1);
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

            x = player.getX() + 680;
            y = player.getY() - 24;
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
        //trinidad05
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            trinidadCatedral(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            trinidad05(stage);
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
package engine.world;


import dbo.ObjetosData;
import dbo.PlayerData;
import engine.combate.peleitas.FightController;
import engine.minijuego.MinijuegoController;
import engine.objects.Camera;
import engine.objects.Player;
import engine.objects.Elements;
import engine.objects.NPC;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.google.gson.Gson;
import javafx.util.Duration;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Maps {
    private Pane root = new Pane();
    private Pane dialogRoot = new Pane();
    private LinkedList<ObstacleTile> barrier;
    private Player player;

    private NPC npc;

    private AnimationTimer timer;

    private Elements elements;

    private Random random = new Random();

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

    private Stage stage;
    private int sumador;
    private int i = 0;

    //Constructor

    public Maps() {

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

    //Parada guagua
    public void paradaGuagua(Stage stage) {

        i = 6;

        BackgroundImage = new Image("paradaGuagua.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/paradaGuagua.json");

        Elements element = new Elements(this.root, stage, 2, 675, 30);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 50, 180, barrier);

        //NPCs

//        NPC npc = new NPC(this.root, stage, scene, 2, 630, 150,"left", barrier);
//        this.npc = npc;

        playerBasics();
        player.setY(65);
        player.setX(550);

        player.addElements(element);
        element.addDialogs(dialog, "Parada de la 026", "parada");
//        player.addNPC(npc);
//        npc.addDialogs(dialog, "...", "pepe", "cxzxc", "Hola mi amigo", "Cómo estás hoy", "Yo muy bien");

        System.out.println("Character x + y = " + player.getX() + " " + player.getY());

        timerStart();

        //Debug
        ObjetosData ob = new ObjetosData();
//        try {
//            //ob.insertarDatosObjeto("refresco", "bebida refrescante", 5);
//            ob.mostrarDatosObjeto();
////            ob.insertarDatosInventario(8);
////            ob.mostrarDatosInventario();
//        } catch (SQLException ex){
//            System.out.println("Error al insertar: " + ex);
//        }
    }

    public void calleInstituto(Stage stage) {

        try {
            PlayerData.guardarDato(0, 20);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        i = 1;

        BackgroundImage = new Image("calleInstituto.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/calleInstituto.json");

        //NPCs

        NPC npc3 = new NPC(this.root, stage, 3, 300, 550, "right", barrier);

        this.npc = npc3;

        playerBasics();

        player.addNPC(npc3);

        npc3.addDialogs(dialog, "Me cago en Java");

        System.out.println(player.getNPCs()); //DEBUG

    }

    //Instituto-plaza
    public void calleInstituto2(Stage stage) {

        i = 2;

        BackgroundImage = new Image("calleinstituto2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/calleInstituto2.json");

        //NPCs

        NPC npc = new NPC(this.root, stage,6, 100, 250, "right", barrier);

        this.npc = npc;

        NPC npc2 = new NPC(this.root, stage, 2, 400, 650,"left", barrier);

        this.npc = npc2;

        NPC npc3 = new NPC(this.root, stage, 1, 50, 150,"up", barrier);

        this.npc = npc3;

        playerBasics();

        player.addNPC(npc);
        player.addNPC(npc2);
        player.addNPC(npc3);

        npc.addDialogs(dialog, "¿Cuál es la forma orientada a objetos para volverse rico? \nHerencia");
        npc2.addDialogs(dialog, "Tuviste piedad, gracias :D");
        npc3.addDialogs(dialog, "4 días para conseguir que los diálogos funcionen :')");

        System.out.println(player.getNPCs());


    }

    //Plaza Alteza
    public void plaza(Stage stage) {

        //DEBUG
        try {
            PlayerData.guardarDato(7, 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //

        i = 3;

        BackgroundImage = new Image("plaza.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/plaza.json");

        //NPCs

        NPC npc = new NPC(this.root, stage, 7, 330, 540,"up", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "...");

    }
    public void plaza2(Stage stage) {

        i = 4;

        BackgroundImage = new Image("plaza2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/plaza2.json");

        //NPCs

        NPC npc = new NPC(this.root, stage, 8, 40, 310,"right", barrier);
        this.npc = npc;

        NPC npc2 = new NPC(this.root, stage, 5, 370, 530,"up", barrier);
        this.npc = npc2;

        playerBasics();

        player.addNPC(npc);
        player.addNPC(npc2);

        npc.addDialogs(dialog, "...");
        npc2.addDialogs(dialog, "...");
    }

    public void arcade(Stage stage) {

        i = 5;

        BackgroundImage = new Image("arcade.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/arcade.json");

        Elements element = new Elements(this.root, stage, 1, 545, 340);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 60, 87, barrier);

        playerBasics();
        player.addElements(element);

    }

    //instituto-plaza
    public void institutoPlaza(Stage stage) {

        i = 7;

        BackgroundImage = new Image("instituto-plaza.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/institutoPlaza.json");

        //NPCs
        NPC npc = new NPC(this.root, stage, 1, 290, 265,"right", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "...");
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

    //Lobby
    public void lobbyInstituto(Stage stage) {

        i = 9;

        BackgroundImage = new Image("lobby_instituto.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/lobbyInstituto.json");

        //Elements
        Elements element = new Elements(this.root, stage, 4, 204, 145);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 60, 148, barrier);

        //NPCs

        NPC npc = new NPC(this.root, stage, 12, 370, 350,"left", barrier);

        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Tengo sueño");
        player.addElements(element);
        element.addDialogs(dialog, "La secretaría está cerrada.\nVuelva en otro momento a ver si tiene más suerte. \nCampeón.");

    }

    //Lobby 2
    public void lobbyInstituto2(Stage stage) {

        i = 10;

        BackgroundImage = new Image("lobby2_instituto.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/lobbyInstituto2.json");

        Elements element = new Elements(this.root, stage,3, 425, 30);

        Elements element2 = new Elements(this.root, stage, 3, 650, 630);

        Elements element3 = new Elements(this.root, stage, 3, 110, 30);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 50, 70, barrier);

        this.elements = element2;
        element2.elementsBasics(element2.getX(), element2.getY(), 50, 70, barrier);

        this.elements = element3;
        element3.elementsBasics(element3.getX(), element3.getY(), 50, 70, barrier);

        playerBasics();

        player.addElements(element);
        player.addElements(element2);
        player.addElements(element3);
        element.addDialogs(dialog, "QUEDA PROHIBIDO EL USO DE MÓVILES");
        element2.addDialogs(dialog, "QUEDA PROHIBIDO EL USO DE MÓVILES");
        element3.addDialogs(dialog, "QUEDA PROHIBIDO EL USO DE MÓVILES");
    }

    //subidaInstituto
    public void subidaInstituto(Stage stage) {

        i = 11;

        BackgroundImage = new Image("subida_instituto.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/subidaInstituto.json");

        playerBasics();

    }

    //subidaInstituto2
    public void subidaInstituto2(Stage stage) {

        i = 12;

        BackgroundImage = new Image("subida_instituto2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/subidaInstituto2.json");

        //NPCs

        NPC npc = new NPC(this.root, stage, 9, 80, 235,"right", barrier);
        this.npc = npc;

        //Elements

        Elements element = new Elements(this.root, stage, 3, 450, 30);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 50, 70, barrier);

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Tengo sueño");
        player.addElements(element);
        element.addDialogs(dialog, "QUEDA PROHIBIDO EL USO DE MÓVILES");
    }

    //lobbyAulas
    public void lobbyAulas(Stage stage) {

        i = 13;

        BackgroundImage = new Image("lobby_aulas.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/lobbyAulas.json");

        //Elements
        Elements element = new Elements(this.root, stage, 4, 628, 32);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 60, 148, barrier);

        playerBasics();

        player.addElements(element);
        element.addDialogs(dialog, "CERRADO. \nPara ir al baño, vete a tu casa o yo que sé XD");
    }

    //lobbyAulas2
    public void lobbyAulas2(Stage stage) {

        i = 14;

        BackgroundImage = new Image("lobby_aulas2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/lobbyAulas2.json");

        //NPCs

        NPC npc = new NPC(this.root, stage, 10, 375, 370,"up", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Tengo sueño");
    }

    //Aula
    public void aula(Stage stage) {

        i = 15;

        BackgroundImage = new Image("aula.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("src/main/resources/Maps/B_LaSalud/aula.json");

        //NPCs
        NPC npc = new NPC(this.root, stage, 11, 185, 235,"down", barrier);

        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Tengo sueño");
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

    public void combateFinal(Stage stage, int i) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FinalFight.fxml"));
        FightController fight = new FightController(2, x, y);
        fight.setStage(stage);
        fight.setCombateFran(true);
        fight.setI(i);
        loader.setController(fight);
        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Combate final");
        stage.setScene(scene);
        stage.show();

        player = new Player(this.root, scene, this.barrier, character_image);
    }

    public void tienda(Stage stage) throws Exception {
        FadeOut(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tiendaMenu.fxml"));
            TiendaController controller = new TiendaController();
            controller.setStage(stage);
            loader.setController(controller);
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

    private boolean shouldStartRandomCombat() {
        // Verificar si el personaje se está moviendo
        if (!player.isMoving()) {
            return false;
        }
        double probability = 0.001;

        // Generar un número aleatorio entre 0 y 1
        double randomValue = random.nextDouble();

        // Verificar si el número aleatorio es menor que la probabilidad
        return randomValue < probability;
    }

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

    private void mapsChanger() {
        if (shouldStartRandomCombat() && i != 6 && i != 9) {
            startRandomCombat();
        }
        //CalleInstituto
        if (player.getX() >= 137.0 && player.getX() <= 257.0 && player.getY() >= 757.0 && player.getY() <= 778.0 && i == 0) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 300;
            y = 300;
            calleInstituto(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 23.0 && player.getY() <= 43.0 && i == 1) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 470;
            y = 719;
            calleInstituto2(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 757.0 && player.getY() <= 778.0 && i == 1) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 75;
            y = 70;
            placita(stage);
            timer.start();
        }
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 1) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 25;
            y = 454;
            lobbyInstituto(stage);
            timer.start();
        }

        //calleInstituto2
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 757.0 && player.getY() <= 778.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 500;
            y = 73;
            calleInstituto(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 30.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 640;
            y = player.getY() - 24;
            institutoPlaza(stage);
            timer.start();

        } else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 2) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 25;
            y = 583;
            paradaGuagua(stage);
            timer.start();
        }

        //institutoPlaza
        else if (player.getX() >= 770.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 7) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            calleInstituto2(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 757.0 && player.getY() <= 778.0 && i == 7) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 620;
            y = 45;
            plaza(stage);
            timer.start();
        }

        //plaza1
        else if (player.getX() >= 23.0 && player.getX() <= 47.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() + 680;
            y = player.getY() - 24;
            plaza2(stage);
            timer.start();
        }
        else if (player.getX() >= 460.0 && player.getX() <= 800.0 && player.getY() >= 23.0 && player.getY() <= 47.0 && i == 3) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 370;
            y = 719;
            institutoPlaza(stage);
            timer.start();
        }

        //plaza2
        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 4) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = player.getX() - 680;
            y = player.getY() - 24;
            plaza(stage);
            timer.start();
        }
        else if (player.getX() >= 472.0 && player.getX() <= 535.0 && player.getY() >= 0.0 && player.getY() <= 62.0 && i == 4) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 370;
            y = 725;
            //arcade(stage);
            try {
                mapsSelector(stage);
            }catch (Exception ex){
            }
            //timer.start();
        }

        //arcade
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 750.0 && player.getY() <= 800.0 && i == 5) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 472;
            y = 70;
            plaza2(stage);
            timer.start();
        }

        //Parada Guagua
        else if (player.getX() >= 0.0 && player.getX() <= 30.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 6) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 730;
            y = 100;
            calleInstituto2(stage);
            timer.start();
        }

        //Placita
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 23.0 && player.getY() <= 43.0 && i == 8) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 422;
            y = 707;
            calleInstituto(stage);
            timer.start();
        }

        //Lobby
        else if (player.getX() >= 0.0 && player.getX() <= 30.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 9) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 694;
            y = 652;
            calleInstituto(stage);
            timer.start();
        }

        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 9) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 25;
            y = 690;
            lobbyInstituto2(stage);
            timer.start();
        }

        //lobbyInstituto2.json
        else if (player.getX() >= 0.0 && player.getX() <= 30.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 10) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 730;
            y = 272;
            lobbyInstituto(stage);
            timer.start();
        }

        else if (player.getX() >= 240.0 && player.getX() <= 348.0 && player.getY() >= 20.0 && player.getY() <= 108.0 && i == 10) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 180;
            y = 720;
            subidaInstituto(stage);
            timer.start();
        }

        //subidaInstituto
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 757.0 && player.getY() <= 778.0 && i == 11) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 290;
            y = 102;
            lobbyInstituto2(stage);
            timer.start();
        }
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 50.0 && i == 11) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 180;
            y = 720;
            subidaInstituto2(stage);
            timer.start();
        }

        //subidaInstituto2
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 757.0 && player.getY() <= 778.0 && i == 12) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 290;
            y = 102;
            subidaInstituto(stage);
            timer.start();
        }
        else if (player.getX() >= 156.0 && player.getX() <= 212.0 && player.getY() >= 0.0 && player.getY() <= 120.0 && i == 12) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 350;
            y = 720;
            lobbyAulas(stage);
            timer.start();
        }

        //lobbyAulas
        else if (player.getX() >= 0.0 && player.getX() <= 800.0 && player.getY() >= 757.0 && player.getY() <= 778.0 && i == 13) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 290;
            y = 102;
            subidaInstituto2(stage);
            timer.start();
        }
        else if (player.getX() >= 450.0 && player.getX() <= 700.0 && player.getY() >= 0.0 && player.getY() <= 116.0 && i == 13) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 300;
            y = 230;
            lobbyAulas2(stage);
            timer.start();
        }

        //lobbyAulas2.json
        else if (player.getX() >= 284.0 && player.getX() <= 404.0 && player.getY() >= 178.0 && player.getY() <= 220.0 && i == 14) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 487;
            y = 121;
            lobbyAulas(stage);
            timer.start();
        }
        else if (player.getX() >= 770.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 14) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 725;
            y = 230;
            aula(stage);
            timer.start();
        }

        //Aula
        else if (player.getX() >= 770.0 && player.getX() <= 800.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 15) {
            // Hacer algo si las escenas son iguales
            player.getTimer().stop();
            timer.stop();

            x = 710;
            y = 380;
            lobbyAulas2(stage);
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

    public AnimationTimer getTimer() {
        return timer;
    }

    public void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }
}
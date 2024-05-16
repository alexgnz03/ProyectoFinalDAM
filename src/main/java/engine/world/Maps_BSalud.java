package engine.world;


import com.google.gson.Gson;
import engine.dbo.CVData;
import engine.dbo.ObjetosData;
import engine.dbo.PlayerData;
import engine.MusicPlayerSt;
import engine.combate.FightController;
import engine.entities.*;
import engine.miniDoomII.MinijuegoController;
import engine.jobs.secretarytyping.SecretaryController;
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
import javafx.scene.control.TextField;
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
import java.util.Random;

public class Maps_BSalud {
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

    InGameMenu inGameMenu;

    private int sumadorCombate = 0;

    private Stage stage;
    private int sumador;
    private int i = 0;

    //Constructor

    public Maps_BSalud() {

        this.barrier = new LinkedList<>();
        //timerStart();
    }

    public void musica(){
        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/lasaludMusic.mp3");
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
        this.stage.setTitle("Barrio de La Salud");

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
        inGameMenu = new InGameMenu(this.dialogRoot, stage, this.scene);
        inGameMenu.setStage(stage);

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

    //Mapas

    //Parada guagua
    public void paradaGuagua(Stage stage) {

        i = 6;

        PlayerData p = new PlayerData();
        CVData c = new CVData();

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/paradaGuagua.png");

        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/paradaGuagua.json");

        Elements element = new Elements(this.root, stage, 2, 675, 30);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 50, 180, barrier);

        //NPCs

        NPC npc = new NPC(this.root, stage, 14, 680, 225, "Left", barrier);
        this.npc = npc;

        playerBasics();
        player.setY(65);
        player.setX(550);

        player.addElements(element);

        player.addNPC(npc);
        npc.addDialogs(dialog, "Llevo aquí dos meses y la 026 sigue sin pasar...");
        timerStart();
    }

    public void calleInstituto(Stage stage) {

        i = 1;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/calleInstituto.png");
        worldBasics(BackgroundImage);

        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/calleInstituto.json");

        //NPCs

        NPC npc3 = new NPC(this.root, stage, 3, 300, 250, "right", barrier);

        this.npc = npc3;

        playerBasics();

        player.addNPC(npc3);

        npc3.addDialogs(dialog, "¿Por qué hay un instituto aquí?");

        System.out.println(player.getNPCs()); //DEBUG

    }

    //Instituto-plaza
    public void calleInstituto2(Stage stage) {

        i = 2;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/calleinstituto2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/calleInstituto2.json");

        //NPCs

        NPC npc = new NPC(this.root, stage,6, 100, 250, "right", barrier);

        this.npc = npc;

        playerBasics();

        player.addNPC(npc);

        npc.addDialogs(dialog, "Lo de empresas... A veces la vida es muy injusta...");

    }

    //Plaza Alteza
    public void plaza(Stage stage) {

        //DEBUG

        i = 3;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/plaza.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/plaza.json");

        //NPCs

        NPC npc = new NPC(this.root, stage, 13, 330, 540,"up", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Estoy estudiando primero de DAM y queda poco \npara los finales. \nEs mucho estrés...", "Que ganitas de estar en segundo que solo son \n6 meses y seguro que es más relajado.");

    }
    public void plaza2(Stage stage) {

        i = 4;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/plaza2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/plaza2.json");

        //NPCs

        NPC npc = new NPC(this.root, stage, 8, 40, 310,"right", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);

        npc.addDialogs(dialog, "...", "...","...","...","...","¿Vas a seguir mirándome?");
    }

    public void arcade(Stage stage) {

        i = 5;

        BackgroundImage = new Image("Maps/Fondos/arcade.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/arcade.json");

        Elements element = new Elements(this.root, stage, 1, 545, 340);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 60, 87, barrier);

        playerBasics();
        player.addElements(element);
        element.addDialogs(dialog, "AVISO: Jugar cuesta 20€, comprueba que tengas\n dinero suficiente...");

    }

    //instituto-plaza
    public void institutoPlaza(Stage stage) {

        i = 7;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/instituto-plaza.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/institutoPlaza.json");

        //NPCs
        NPC npc = new NPC(this.root, stage, 1, 290, 265,"right", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "¿20 pavos un monster? ¿Hasta donde vamos a \nllegar?", "Y encima han quitado el power coco del alteza.", "Esto es una vergüenza...");
    }

    //placita
    public void placita(Stage stage) {

        i = 8;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/placita.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/placita.json");

        //NPCs
        NPC npc = new NPC(this.root, stage, 4, 425, 640,"down", barrier);
        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "¿Sabes? Yo estudié aquí...", "Pero tuve que dejarlo por cosas más\nimportantes.", "...", "O eso creo.");

    }

    //Lobby
    public void lobbyInstituto(Stage stage) {

        i = 9;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/lobby_instituto.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/lobbyInstituto.json");

        //Elements
        Elements element = new Elements(this.root, stage, 4, 204, 145);

        this.elements = element;
        element.elementsBasics(element.getX(), element.getY(), 60, 148, barrier);

        Elements secretario = new Elements(this.root, stage, 16, 210, 262);
        this.elements = secretario;
        secretario.elementsBasics(secretario.getX(), secretario.getY(), 48, 48, barrier);

        //NPCs

        NPC npc = new NPC(this.root, stage, 12, 380, 380,"left", barrier);

        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Deja la puerta abierta porfa.");
        player.addElements(element);
        player.addElements(secretario);
        element.addDialogs(dialog, "La secretaría está cerrada.\nVuelva en otro momento a ver si tiene más suerte. \nCampeón.");
        secretario.addDialogs(dialog, "¿Seguro que quieres trabajar aquí chaval?\nAllá tú, pero es muy duro eh...", "3 horas al día durante 3 días a la semana.\nUn infierno.", "¿Te parece poco?\nPues que sepas que trabajo más que\nla mayoría de profesores aquí.");

    }

    //Lobby 2
    public void lobbyInstituto2(Stage stage) {

        i = 10;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/lobby2_instituto.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/lobbyInstituto2.json");

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

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/subida_instituto.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/subidaInstituto.json");

        playerBasics();

    }

    //subidaInstituto2
    public void subidaInstituto2(Stage stage) {

        i = 12;

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/subida_instituto2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/subidaInstituto2.json");

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

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/lobby_aulas.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/lobbyAulas.json");

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

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/lobby_aulas2.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/lobbyAulas2.json");

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

        BackgroundImage = new Image("Maps/Fondos/B_LaSalud/aula.png");
        worldBasics(BackgroundImage);

        //Colisiones
        cargarColisionesDesdeJSON("/Maps/Colisiones/B_LaSalud/aula.json");

        //NPCs
        NPC npc = new NPC(this.root, stage, 11, 185, 235,"down", barrier);

        this.npc = npc;

        playerBasics();

        player.addNPC(npc);
        npc.addDialogs(dialog, "Tengo sueño");
    }

    // Utilidades extra
    public void doom(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Doom2mini/doomView.fxml"));
        MinijuegoController minijuego = new MinijuegoController(2);
        minijuego.setStage(stage);
        loader.setController(minijuego);
        Parent root = loader.load();
        stage.setTitle("Minijuego de Dianas");
        stage.setScene(new Scene(root, 800, 800));

        BackgroundImage = new Image("Doom2mini/doomFondo.png");

        Background background = new Background(new BackgroundImage[]{new BackgroundImage(BackgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size)});
        this.root.setBackground(background);

        stage.show();

        MinijuegoController controlador = loader.getController();
        controlador.actualizarTiempo();

    }

    public void combate(Stage stage, int i) throws Exception {

        timer.stop();
        player.getTimer().stop();


        double volumen;
        volumen = MusicPlayerSt.getVolume();
        MusicPlayerSt.play("/Music/fightMusic.mp3");
        System.out.println("Volumen: " + MusicPlayerSt.getVolume());
        MusicPlayerSt.setVolume(volumen);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Combate/fight.fxml"));
        FightController fight = new FightController(1, player.getX(), player.getY());

        fight.setI(i);
        fight.setStage(stage);

        loader.setController(fight);
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 800);
        stage.setTitle("Fight");

        stage.setScene(scene);
        stage.show();

    }

    public void tienda(Stage stage, int id) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Tienda/tiendaMenu.fxml"));
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
        switch (I){
            case 1:
                tienda(stage, 2);
                break;
            case 2:
                trabajoSecretario(stage);
                break;
            case 3:
                doom(stage);
                break;
        }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void trabajoSecretario(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Jobs/secretaryTyping.fxml"));
            SecretaryController controller = new SecretaryController();
            controller.setStage(stage);
            loader.setController(controller);
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root, 800, 800);

            stage.setTitle("Secretario");
            stage.setScene(scene);
            stage.show();

            TextField textField = controller.getPalabraTextField();
            textField.setOnKeyPressed(event -> {
                if (event.getCode().toString().equals("ENTER")) {
                    controller.comprobarPalabra();
                }
            });

            player = new Player(this.root, scene, this.barrier, character_image);
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

    private boolean shouldStartRandomCombat() {
        // Verificar si el personaje se está moviendo
        if (!player.isMoving()) {
            return false;
        }
        double probability = 0.0015;

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


    // Cambiar mapa según la posición del player y el mapa actual
    private void mapsChanger() {
        if (shouldStartRandomCombat() && i != 6 && i != 9 && i != 5) {
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
            arcade(stage);
            timer.start();
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

//        else if (player.getX() >= 757.0 && player.getX() <= 778.0 && player.getY() >= 0.0 && player.getY() <= 800.0 && i == 9) {
//            // Hacer algo si las escenas son iguales
//            player.getTimer().stop();
//            timer.stop();
//
//            x = 25;
//            y = 690;
//            lobbyInstituto2(stage);
//            timer.start();
//        }

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
package engine.entities;

import engine.menu.IntroduccionController;
import engine.dbo.MonsterLoader;
import engine.dbo.PlayerData;
import engine.ui.Dialog;
import engine.ui.PlayerState;
import engine.world.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Elements {
    double x = 0;
    double y = 0;
    Pane root = new Pane();
    Stage stage;
    public AnimationTimer timer;
    public String elementSprite;
    public ImageView character_image  = new ImageView(new Image("Player/Down2.png"));
    LinkedList<ObstacleTile> barrier;
    ArrayList<Image> testImageList;
    int upCount;
    int downCount;
    int rightCount;
    int leftCount;
    int i = 0;
    MonsterLoader mostrodbo = new MonsterLoader();
    double w;
    double h;
    int id;
    ObstacleTile tile;

    Maps_BSalud mapsBSalud = new Maps_BSalud();

    Maps_LaLaguna mapsLaLaguna = new Maps_LaLaguna();

    Maps_Teresitas mapsTeresitas = new Maps_Teresitas();

    public ImageView elements_image  = new ImageView(new Image("Player/Down2.png"));

    ArrayList<String> frases = new ArrayList<>();
    int nFrases;
    int nActualFrases = 1;


    private List<Dialog> dialogs = new ArrayList<>();

    //Añadir diálogos a los elementos
    public void addDialogs(Dialog dialog, String... frases) {
        dialogs.add(dialog);
        int i;
        for (i = 0 ; i < frases.length ; i++) {
            this.frase = frases[i];
            this.frases.add(frases[i]);
            System.out.println("Frase: " + frases[i]);
            System.out.println("Frases: " + this.frases.get(i));
            System.out.println("Nº de Frases: " + (i + 1));

        }
        System.out.println("Nº final de Frases: " + (i));
        this.nFrases = i;
    }

    public List<Dialog> getDialogs() {
        return dialogs;
    }

    String frase = "Hola";

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public Elements(Pane root, Stage stage, int ID, double x, double y)

    {

        this.character_image = character_image;
        this.root = root;
        this.stage = stage;
        this.testImageList = new ArrayList();
        this.upCount = 0;
        this.downCount = 0;
        this.rightCount = 0;
        this.leftCount = 0;
        this.barrier = new LinkedList<>();

        this.x = x;
        this.y = y;
        this.id = ID;

        if (ID == 0) {

        }

        ElementsSprites(ID);

    }

    //TODO Revisar si esto se podría integrar en el constructor
    public void elementsBasics(double x, double y, double w, double h, LinkedList<ObstacleTile> barrier){
        this.w = w;
        this.h = h;
        elements_image.setLayoutX(x);
        elements_image.setLayoutY(y);
        this.root.getChildren().add(elements_image);
        this.barrier = barrier;
        this.createObstacleTile(w, h, x, y); //(Width, Height, x, y)

    }

    //Este se usa para los que no tienen colisión (Ej: coches, techos de la trinidad...)
    public void elementsBasicsNC(double x, double y){
        elements_image.setLayoutX(x);
        elements_image.setLayoutY(y);
        this.root.getChildren().add(elements_image);
    }

    //Interacción con cada uno de los elementos
    public void elementInteraction(double x, double y) {
        double elementX = this.x - 24;
        double elementY = this.y - 24;
        //Arcade
        if ((x >= elementX && x <= this.x + 120) && (y >= elementY && y <= this.y + 87) && id == 1) {
                try {
                    if (PlayerData.cargarDato(7) > 200){
                        int dinero = PlayerData.cargarDato(7)-200;
                        PlayerData.guardarDato(7, dinero);
                        mapsLaLaguna.setStage(stage);
                        mapsLaLaguna.pantallaCarga(stage, 3);
                    } else{
                        for (Dialog dialog : getDialogs()) {
                            dialog.autoDialog("Jugar cuesta 200€. No tienes suficiente\ndinero.", 3);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

        }
        //Parada 026
        else if (((x >= elementX && x <= this.x + 80) && (y >= elementY && y <= this.y + 180) && id == 2)){
                try {
                    mapsBSalud.mapsSelector(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
        //Carteles móviles
        else if (((x >= elementX && x <= this.x + 70) && (y >= elementY && y <= this.y + 85) && id == 3)){
            for (Dialog dialog : getDialogs()){
                useDialogs(dialog);
            }
        }
        //Puerta
        else if (((x >= elementX && x <= this.x + 200) && (y >= elementY && y <= this.y + 148) && id == 4)){
            for (Dialog dialog : getDialogs()){
                useDialogs(dialog);
            }
        }
        //TranviaIntercambiador
        else if (((x >= elementX && x <= this.x + 111) && (y >= elementY && y <= this.y + 360) && id == 5)){
            try {
                mapsLaLaguna.setX(240);
                mapsLaLaguna.setY(625);
                mapsLaLaguna.setStage(stage);
                mapsLaLaguna.trinidad02(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //TranviaTrinidad02
        else if (((x >= elementX && x <= this.x + 656) && (y >= elementY && y <= this.y + 88) && id == 11)){
            try {
                mapsLaLaguna.setX(450);
                mapsLaLaguna.setY(605);
                mapsLaLaguna.setStage(stage);
                mapsLaLaguna.intercambiador(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //TranviaTrinidad01
        else if (((x >= elementX && x <= this.x + 272) && (y >= elementY && y <= this.y + 92) && id == 12)){
            try {
                mapsLaLaguna.setX(450);
                mapsLaLaguna.setY(605);
                mapsLaLaguna.setStage(stage);
                mapsLaLaguna.intercambiador(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //dependiente mcdont
        else if (((x >= elementX && x <= this.x + 48) && (y >= elementY && y <= this.y + 48) && id == 14)){
            try {
                if(PlayerData.cargarDato(6) > 5) {
                    for (Dialog dialog : getDialogs()){
                        useDialogs(dialog);
                        if (dialog.getDialog().getOpacity() == 0){
                            try {
                                mapsLaLaguna.setStage(stage);
                                mapsLaLaguna.pantallaCarga(stage, 2);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                } else {
                    for (Dialog dialog : getDialogs()) {
                        dialog.autoDialog("No te queda suficiente stamina, recúperate\ndurmiendo y vuelve más tarde.", 4);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //cama
        else if (((x >= elementX && x <= this.x + 84) && (y >= elementY && y <= this.y + 52) && id == 15)){
            try {
                PlayerData.guardarDato(6, 100);
                PlayerState.actualizarStamina(100);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (Dialog dialog : getDialogs()){
                useDialogs(dialog);
            }
        }
        //secretario
        else if (((x >= elementX && x <= this.x + 48) && (y >= elementY && y <= this.y + 48) && id == 16)){
            try {
                if(PlayerData.cargarDato(6) > 5) {
                    if (mostrodbo.obtenerCantidadRegistrosMostroDex() >= 6) {
                        for (Dialog dialog : getDialogs()){
                            useDialogs(dialog);
                            if (dialog.getDialog().getOpacity() == 0){
                                try {
                                    mapsBSalud.setStage(stage);
                                    mapsBSalud.pantallaCarga(stage, 2);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } else {
                        for (Dialog dialog : getDialogs()) {
                            dialog.autoDialog("Lo siento pero aún no puedes trabajar aquí.", 3);
                        }
                    }
                } else {
                    for (Dialog dialog : getDialogs()) {
                        dialog.autoDialog("No te queda suficiente stamina, recúperate\ndurmiendo y vuelve más tarde.", 4);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //tiendaPlaya
        else if (((x >= elementX && x <= this.x + 88) && (y >= elementY && y <= this.y + 172) && id == 17)){
            try {
                mapsTeresitas.setStage(stage);
                mapsTeresitas.pantallaCarga(stage, 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //Socorrista
        else if (((x >= elementX && x <= this.x + 100) && (y >= elementY && y <= this.y + 190) && id == 18)){
            try {
                if(PlayerData.cargarDato(6) > 5) {
                    if (mostrodbo.obtenerCantidadRegistrosMostroDex() >= 3) {
                        for (Dialog dialog : getDialogs()){
                            useDialogs(dialog);
                            if (dialog.getDialog().getOpacity() == 0){
                                try {
                                    mapsTeresitas.setStage(stage);
                                    mapsTeresitas.pantallaCarga(stage, 2);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    } else {
                        for (Dialog dialog : getDialogs()) {
                            dialog.autoDialog("Lo siento pero aún no puedes trabajar aquí.", 3);
                        }
                    }
                } else {
                    for (Dialog dialog : getDialogs()) {
                        dialog.autoDialog("No te queda suficiente stamina, recúperate\ndurmiendo y vuelve más tarde.", 4);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //Aeropuerto
        else if (((x >= elementX && x <= this.x + 100) && (y >= elementY && y <= this.y + 190) && id == 19)){
            try {
                if (PlayerData.cargarDato(7) < 5000){
                    for (Dialog dialog : getDialogs()){
                        useDialogs(dialog);}
                } else {
                    // Cargar la escena del menú de configuración
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Menu/Introduccion.fxml"));
                    IntroduccionController controller = new IntroduccionController(2);
                    controller.setStage(stage);
                    loader.setController(controller);
                    Parent root = loader.load();

                    // Pasar el Stage actual al controlador del menú de configuración
                    controller.setStage(stage);

                    // Establecer la escena del menú de configuración en el Stage actual
                    Scene scene = new Scene(root, 800, 800);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println(this.id + " No se interactua ");
        }

    }

    //Movimiento del coche
    public void carTimer(Player player) {
        x = 800;
        elements_image.setX(800);
        elements_image.setOpacity(1);
        carSprite();
        timer = new AnimationTimer() {
            public void handle(long now) {

                double newX = x;
                double borde = generarNumeroAleatorio(400, 600);
                if (x < -borde){
                    x = 800;

                    // Cambiar sprite del coche
                    carSprite();
                }

                if (player.getY() < y || player.getY() > y + 179){
                    x = x-4;

                    elements_image.setX(x);

                } else{
                    if ((player.getX() >= newX-24 && player.getX() <= newX + 230) && (player.getY() > y+25 && player.getY() <= y + 113) && id == 13) {
                        elements_image.setOpacity(0.2);
                    } else{
                        elements_image.setOpacity(1);
                    }
                }
            }
        };
    }

    private void carSprite(){
        String[] sprites = {"/Elements/coche.png", "/Elements/coche2.png", "/Elements/coche3.png"};
        Random random = new Random();
        int numeroAleatorio = random.nextInt(3);
        elements_image.setImage(new Image(sprites[numeroAleatorio]));
    }

    public void carStop(){
        this.timer.stop();
    }

    //Método para volver semitransparente los techos de la trinidad cuando el Player está encima
    public void trinidadInteraction(double x, double y) {
        double elementX = this.x - 24;
        double elementY = this.y - 24;
        //trinidad01
        if ((x >= elementX && x <= this.x + 251) && (y >= elementY && y <= this.y + 275) && id == 6) {
            elements_image.setOpacity(0.2);
        }
        //trinidad02, 03, 04
        else if ((x >= elementX && x <= this.x + 800) && (y >= elementY && y <= this.y + 275) && (id == 7 || id == 8 || id == 9)) {
            elements_image.setOpacity(0.2);
        }
        //trinidad05
        else if ((x >= elementX && x <= this.x + 196) && (y >= elementY && y <= this.y + 275) && id == 10) {
            elements_image.setOpacity(0.2);
        }
        else {
            elements_image.setOpacity(1);
        }
    }

    public void createObstacleTile(double w, double h, double x, double y) {
        tile = new ObstacleTile(w, h, x, y);
        this.root.getChildren().add(tile);
        this.barrier.add(tile);

    }

    // Pillar los sprites desde el archivo properties
    private void ElementsSprites(int ID){

        // Obtener el ClassLoader
        ClassLoader classLoader = getClass().getClassLoader();
        Properties prop = new Properties();

        // Cargar el archivo de propiedades desde el classpath
        try (InputStream inputStream = classLoader.getResourceAsStream("Properties/elements_rutas.properties")) {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                System.err.println("No se pudo encontrar el archivo npc_rutas.properties en el classpath.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Obtener las rutas de las imágenes de los NPC según el ID proporcionado
        if (ID >= 1 && ID <= 19) {
            String rutaString = prop.getProperty(String.valueOf(ID));
            if (rutaString != null) {
                String[] rutas = rutaString.split(",");

                // Asignar las rutas a las variables standingDown, standingUp, standingLeft y standingRight
                elementSprite = rutas[0];
                elements_image.setImage(new Image(elementSprite));
            } else {
                // Manejar el caso cuando el ID no tiene una ruta asociada
            }
        } else {
            // Manejar el caso cuando el ID no está en el rango esperado
        }
    }

    public void useDialogs(Dialog dialog){
        if (dialog.getDialog().getOpacity() == 0) {
            dialog.invokeDialog(frases.get(i));

        } else if (dialog.getDialog().getOpacity() == 0.7){
            //Parar la timeline anterior
            dialog.getTimeline().stop();

            if ((nActualFrases) != nFrases){
                dialog.invokeDialog(frases.get(nActualFrases));
                nActualFrases++;
            } else {
                dialog.closeDialog();
                nActualFrases = 1;
            }
        }
    }

    public static double generarNumeroAleatorio(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
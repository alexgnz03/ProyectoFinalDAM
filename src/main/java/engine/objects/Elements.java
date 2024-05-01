package engine.objects;

import engine.ui.Dialog;
import engine.world.Maps;
import engine.world.Maps2;
import engine.world.ObstacleTile;
import engine.world.World;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Elements {
    double x = 0;
    double y = 0;
    Pane root = new Pane();
    Stage stage;
    public AnimationTimer timer;
    public String elementSprite;
    public ImageView character_image  = new ImageView(new Image("Down2.png"));
    LinkedList<ObstacleTile> barrier;
    boolean moveUp;
    boolean moveRight;
    boolean moveDown;
    boolean moveLeft;
    ArrayList<Image> testImageList;
    int switchWhenZero = 0;
    int upCount;
    int downCount;
    int rightCount;
    int leftCount;
    int i = 0;
    int mx = 0;
    int my = 0;
    int dx = 0;
    int dy = 0;
    //Dialog dialog = new Dialog();

    double w;
    double h;
    World worldInstance = new World();
    int id;
    int ChaX = 0;
    int ChaY = 0;
    ObstacleTile tile;

    Maps maps = new Maps();

    Maps2 maps2 = new Maps2();

    public ImageView elements_image  = new ImageView(new Image("Down2.png"));

    //private Maps mapsInstance = new Maps();

    ArrayList<String> frases = new ArrayList<>();
    int nFrases;
    int nActualFrases = 1;


    private List<Dialog> dialogs = new ArrayList<>();

    //TODO Seguir esto
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

    public void elementsBasics(double x, double y, double w, double h, LinkedList<ObstacleTile> barrier){
        this.w = w;
        this.h = h;
        elements_image.setLayoutX(x);
        elements_image.setLayoutY(y);
        this.root.getChildren().add(elements_image);
        this.barrier = barrier;
        this.createObstacleTile(w, h, x, y); //(Width, Height, x, y)
        System.out.println("COLISION CREADA");

    }

    public void elementsBasicsNC(double x, double y){
        elements_image.setLayoutX(x);
        elements_image.setLayoutY(y);
        this.root.getChildren().add(elements_image);
    }

    public void elementInteraction(double x, double y) {
        double elementX = this.x - 24;
        double elementY = this.y - 24;
        //Arcade
        if ((x >= elementX && x <= this.x + 120) && (y >= elementY && y <= this.y + 87) && id == 1) {
            try {
                //mapsInstance.minijuego(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //Parada 026
        else if (((x >= elementX && x <= this.x + 50) && (y >= elementY && y <= this.y + 180) && id == 2)){
            for (Dialog dialog : getDialogs()){
                useDialogs(dialog);
                try {
                    maps.mapsSelector(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
                maps2.setStage(stage);
                maps2.trinidad01(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //TranviaTrinidad02
        else if (((x >= elementX && x <= this.x + 656) && (y >= elementY && y <= this.y + 88) && id == 11)){
            try {
                maps2.setStage(stage);
                maps2.intercambiador(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //TranviaTrinidad01
        else if (((x >= elementX && x <= this.x + 272) && (y >= elementY && y <= this.y + 92) && id == 12)){
            try {
                maps2.setStage(stage);
                maps2.intercambiador(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println(this.id + " No se interactua ");
        }

    }

    public static double generarNumeroAleatorio(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    //TODO Seguir esto
    public void carTimer(Player player) {
        x = 800;
        elements_image.setX(x);
        timer = new AnimationTimer() {
            public void handle(long now) {

                double newX = x;
                double borde = generarNumeroAleatorio(400, 600);
                if (x < -borde){
                    x = 800;
                }

                if (player.getY() < y || player.getY() > y + 179){
                    x = x-1.5;

                    elements_image.setX(x);

                } else{
                    if ((player.getX() >= newX-24 && player.getX() <= newX + 230) && (player.getY() > y && player.getY() <= y + 113) && id == 13) {
                        elements_image.setOpacity(0.2);
                    } else{
                        elements_image.setOpacity(1);
                    }
                }
                System.out.println("pY: " + player.getY() + " Y: " + y );
                System.out.println(x);
                System.out.println(newX);
            }
        };
    }

    public void carStop(){
        this.timer.stop();
    }

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
        if (ID >= 1 && ID <= 13) {
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
                System.out.println("Está entrando al else if: " + (nActualFrases) + nFrases);
                dialog.invokeDialog(frases.get(nActualFrases));
                nActualFrases++;
            } else {
                dialog.closeDialog();
                nActualFrases = 1;
            }
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

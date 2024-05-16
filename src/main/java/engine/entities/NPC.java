package engine.entities;

import engine.ui.Dialog;
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

public class NPC {
    private double x = 0;
    private double y = 0;
    private Pane root = new Pane();
    private Stage stage;
    private String standingDown;
    private String standingUp;
    private String standingLeft;
    private String standingRight;
    private ImageView character_image = new ImageView(new Image("Player/Down2.png"));
    private LinkedList<ObstacleTile> barrier;
    private ArrayList<Image> testImageList;
    private int id;
    private String frase = "Hola";
    ArrayList<String> frases = new ArrayList<>();
    int nFrases;
    int nActualFrases = 1;
    private String shadowRoute = "Player/shadow.png";
    private ImageView shadow = new ImageView(new Image(shadowRoute));


    private List<Dialog> dialogs = new ArrayList<>();

    //Método para añadir diálogos a los NPCs
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


    public NPC(Pane root, Stage stage, int ID, double x, double y, String defaultDirection, LinkedList<ObstacleTile> barrier)
    {

        this.root = root;
        this.stage = stage;
        this.testImageList = new ArrayList();
        this.barrier = new LinkedList<>();
        this.x = x;
        this.y = y;
        this.id = ID;

        shadow.setImage(new Image(shadowRoute));
        this.root.getChildren().add(shadow);
        shadow.setX(x+10);
        shadow.setY(y+42);

        NPCSprites(ID, defaultDirection);

        createObstacleTile(48, 48, this.x, this.y);

        NPCBasics(character_image, barrier);

    }

    private void createObstacleTile(double w, double h, double x, double y) {
        ObstacleTile tile = new ObstacleTile(w, h, x, y);
        this.root.getChildren().add(tile);
        this.barrier.add(tile);

    }

    // Define las interacciones con los NPCs
    public void npcInteraction(double x, double y) {
        System.out.println("x: " + x + " npcX: " + this.x + " y: " + y + " npcY: " + this.y);
        if ((x >= this.x - 40 && x <= this.x + 80) && (y >= this.y - 25 && y <= this.y + 55)) {
            for (Dialog dialog : getDialogs()){
                int i = 0;
                if (dialog.getDialog().getOpacity() == 0 && id != 11) {
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

            if (y < this.y || x < this.x) {

                if ((this.x-x) < (this.y-y)){

                    this.character_image.setImage(new Image(this.standingUp));
                } else if ((this.y-y) < (this.x-x)) {

                    this.character_image.setImage(new Image(this.standingLeft));
                }

            } else if (y > this.y || x > this.x) {

                if ((this.x-x) > (this.y-y)) {

                    this.character_image.setImage(new Image(this.standingDown));
                } else if ((this.y-y) > (this.x-x)) {

                    this.character_image.setImage(new Image(this.standingRight));
                }
            }
        }
        else {
        }

    }

    public void NPCBasics(ImageView npc_image, LinkedList<ObstacleTile> barrier){
        npc_image.setLayoutX(x);
        npc_image.setLayoutY(y);
        this.root.getChildren().add(npc_image);
        this.barrier = barrier;
        this.createObstacleTile(40, 40, x + 5, y); //(Width, Height, x, y)
    }

    // Cargar los sprites desde el properties de los NPCs
    private void NPCSprites(int ID, String defaultDirection){

        // Obtener el ClassLoader
        ClassLoader classLoader = getClass().getClassLoader();
        Properties prop = new Properties();

        // Cargar el archivo de propiedades desde el classpath
        try (InputStream inputStream = classLoader.getResourceAsStream("Properties/npc_rutas.properties")) {
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
        if (ID >= 1 && ID <= 18) {
            String rutaString = prop.getProperty(String.valueOf(ID));
            if (rutaString != null) {
                String[] rutas = rutaString.split(",");

                // Asignar las rutas a las variables standingDown, standingUp, standingLeft y standingRight
                standingDown = rutas[0];
                standingUp = rutas[1];
                standingLeft = rutas[2];
                standingRight = rutas[3];
                switch (defaultDirection.toLowerCase()) {
                    case "up":
                        character_image.setImage(new Image(standingUp));
                        break;
                    case "down":
                        character_image.setImage(new Image(standingDown));
                        break;
                    case "left":
                        character_image.setImage(new Image(standingLeft));
                        break;
                    case "right":
                        character_image.setImage(new Image(standingRight));
                        break;
                    default:
                        // Manejar el caso cuando la dirección por defecto no es válida
                        break;
                }
            } else {
                // Manejar el caso cuando el ID no tiene una ruta asociada
            }
        } else {
            // Manejar el caso cuando el ID no está en el rango esperado
        }
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}



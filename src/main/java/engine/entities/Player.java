package engine.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import engine.ui.inGameMenu.InGameMenu;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

// Esta clase se encarga del movimiento y animación del Player
public class Player {
    private Pane root;

    private AnimationTimer timer;
    private String standingDown = "Player/Down2.png";
    private String standingUp = "Player/Up2.png";
    private String standingLeft = "Player/Left2.png";
    private String standingRight = "Player/Right2.png";
    private String shadowRoute = "Player/shadow.png";
    private ImageView character_image;
    private ImageView shadow = new ImageView(new Image(shadowRoute));
    private LinkedList<ObstacleTile> barrier;
    private boolean moveUp;
    private boolean moveRight;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean run;
    private ArrayList<Image> walkingUpImageList;
    private ArrayList<Image> walkingDownImageList;
    private ArrayList<Image> walkingRightImageList;
    private ArrayList<Image> walkingLeftImageList;
    private int switchWhenZero = 0;
    private int upCount;
    private int downCount;
    private int rightCount;
    private int leftCount;
    private double x;
    private double y;
    private int i = 0;
    private int mx = 0;
    private int my = 0;

    private List<NPC> npcs = new ArrayList<>();
    public void addNPC(NPC npc) {
        npcs.add(npc);
    }
    public List<NPC> getNPCs() {
        return npcs;
    }

    private List<Elements> ob = new ArrayList<>();
    public void addElements(Elements obs) {
        ob.add(obs);
    }
    public List<Elements> getElements() {
        return ob;
    }

    private List<InGameMenu> menus = new ArrayList<>();
    public void addMenu(InGameMenu menu) {
        menus.add(menu);
    }
    public List<InGameMenu> getMenus() {
        return menus;
    }

    public Player(Pane root, Scene scene, LinkedList<ObstacleTile> barrier, final ImageView character_image) {
        this.barrier = barrier;
        this.character_image = character_image;
        this.root = root;

        shadow.setImage(new Image(shadowRoute));
        this.root.getChildren().add(shadow);

        spritesStarter();
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));

        timerStart();
    }

    private void timerStart() {
        timer = new AnimationTimer() {
            public void handle(long now) {
                int dx = 0;
                int dy = 0;

                if (Player.this.moveUp) {
                    dy -= 4;
                    my = -4;
                    mx = 0;
                    if (Player.this.switchWhenZero == 0) {
                        character_image.setImage(Player.this.walkingUpImageList.get(Player.this.upCount % 3));
                        ++Player.this.upCount;
                        Player.this.switchWhenZero = 4;
                    } else {
                        --Player.this.switchWhenZero;
                    }
                } else if (Player.this.moveDown) {
                    dy += 4;
                    my = 4;
                    mx = 0;
                    if (Player.this.switchWhenZero == 0) {
                        character_image.setImage(Player.this.walkingDownImageList.get(Player.this.downCount % 3));
                        ++Player.this.downCount;
                        Player.this.switchWhenZero = 4;
                    } else {
                        --Player.this.switchWhenZero;
                    }
                } else if (Player.this.moveRight) {
                    dx += 4;
                    mx = 4;
                    my = 0;
                    if (Player.this.switchWhenZero == 0) {
                        character_image.setImage(Player.this.walkingRightImageList.get(Player.this.rightCount % 3));
                        ++Player.this.rightCount;
                        Player.this.switchWhenZero = 4;
                    } else {
                        --Player.this.switchWhenZero;
                    }
                } else if (Player.this.moveLeft) {
                    dx -= 4;
                    mx = -4;
                    my = 0;
                    if (Player.this.switchWhenZero == 0) {
                        character_image.setImage(Player.this.walkingLeftImageList.get(Player.this.leftCount % 3));
                        ++Player.this.leftCount;
                        Player.this.switchWhenZero = 4;
                    } else {
                        --Player.this.switchWhenZero;
                    }
                }

                if (run) {
                    dx *= 2;
                    dy *= 2;
                }

                Player.this.moveCharacter(dx, dy);

                for (Elements elements : getElements()) {
                    elements.trinidadInteraction(x, y);
                }

                shadow.setX(x-14.3);
                shadow.setY(y+18);

            }
        };

        this.timer.start();
    }

    private boolean interacting = false;

    private void handleKeyPress(KeyCode code) {

        switch (code) {
            case UP, W -> moveUp = true;
            case DOWN, S -> moveDown = true;
            case RIGHT, D -> moveRight = true;
            case LEFT, A -> moveLeft = true;
            case SHIFT -> run = true;
            case SPACE -> handleSpaceKeyPress();
            case M -> handleMenuKeyPress();
        }
    }

    private void handleKeyRelease(KeyCode code) {
        switch (code) {
            case UP, W -> moveUp = false;
            case DOWN, S -> moveDown = false;
            case RIGHT, D -> moveRight = false;
            case LEFT, A -> moveLeft = false;
            case SHIFT -> run = false;
        }
    }

    private void handleSpaceKeyPress() {
        for (NPC npc : getNPCs()) {
            npc.npcInteraction(x, y);
        }
        for (Elements elements : getElements()) {
            elements.elementInteraction(x, y);
        }
    }

    private void handleMenuKeyPress() {
        for (InGameMenu menu : getMenus()) {

            if (menu.getMenuOpen() == false){
                menu.invokeInGameMenu();
            }
            else {
                menu.closeInGameMenu();
            }


        }
    }

    private void moveCharacter(int dx, int dy) {

//        if (shouldStartRandomCombat()) {
//            startRandomCombat();
//        }

        if (dx == 0 && dy == 0 && my == -8) {
            this.character_image.setImage(new Image(this.standingUp));
        }
        else if (dx == 0 && dy == 0 && my == 8) {
            this.character_image.setImage(new Image(this.standingDown));
        }
        else if (dx == 0 && dy == 0 && mx == -8) {
            this.character_image.setImage(new Image(this.standingLeft));
        }
        else if (dx == 0 && dy == 0 && mx == 8) {
            this.character_image.setImage(new Image(this.standingRight));
        }

        else {
            double cx = this.character_image.getBoundsInLocal().getWidth() / 2.0;
            double cy = this.character_image.getBoundsInLocal().getHeight() / 2.0;
            x = cx + this.character_image.getLayoutX() + (double)dx;
            y = cy + this.character_image.getLayoutY() + (double)dy;
            //System.out.println(mapsInstance.getX() + " " + mapsInstance.getY());



            if (x - cx >= 0.0 && x + cx <= 800.0 && y - cy >= 0.0 && y + cy <= 800.0 && !this.checkCollision(x - cx, y - cy)) {
                this.character_image.relocate(x - cx, y - cy);
            }

        }

    }

    private boolean checkCollision(double wantsToGoToThisX, double wantsToGoToThisY) {
        Iterator<ObstacleTile> it = this.barrier.iterator();

        boolean inside;
        do {
            if (!it.hasNext()) {
                return false;
            }

            ObstacleTile t = (ObstacleTile)it.next();
            double spriteMinX = wantsToGoToThisX + 5.0;
            double spriteMinY = (wantsToGoToThisY + 5.0) + 24.0;
            double spriteMaxX = wantsToGoToThisX + this.character_image.getBoundsInLocal().getWidth() - 5.0;
            double spriteMaxY = wantsToGoToThisY + this.character_image.getBoundsInLocal().getHeight() - 1.0;
            double tMinX = t.getX();
            double tMinY = t.getY();
            double tMaxX = t.getX() + t.getWidth();
            double tMaxY = t.getY() + t.getHeight();
            inside = spriteMaxX > tMinX && spriteMinX < tMaxX && spriteMaxY > tMinY && spriteMinY < tMaxY;

        } while(!inside);

        return true;
    }

    private void spritesStarter(){
        walkingUpImageList = new ArrayList();
        walkingUpImageList.add(new Image("Player/Up1.png"));
        walkingUpImageList.add(new Image("Player/Up2.png"));
        walkingUpImageList.add(new Image("Player/Up3.png"));
        walkingDownImageList = new ArrayList();
        walkingDownImageList.add(new Image("Player/Down1.png"));
        walkingDownImageList.add(new Image("Player/Down2.png"));
        walkingDownImageList.add(new Image("Player/Down3.png"));
        walkingRightImageList = new ArrayList();
        walkingRightImageList.add(new Image("Player/Right1.png"));
        walkingRightImageList.add(new Image("Player/Right2.png"));
        walkingRightImageList.add(new Image("Player/Right3.png"));
        walkingLeftImageList = new ArrayList();
        walkingLeftImageList.add(new Image("Player/Left1.png"));
        walkingLeftImageList.add(new Image("Player/Left2.png"));
        walkingLeftImageList.add(new Image("Player/Left3.png"));
    }

    public boolean isMoving() {
        return moveUp || moveDown || moveRight || moveLeft;
    }

    //Getters y Setters


    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }
}

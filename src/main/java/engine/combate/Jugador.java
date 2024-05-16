package engine.combate;

import engine.dbo.PlayerData;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Random;

public class Jugador {
    private int money;
    private Image jugador_image;
    private String[] skill = new String[2];

    private double vida;
    private double vida_maxima;
    private double ataque;
    private double defensa;
    private double ataque_magico;
    private double defensa_magica;
    private double velocidad;

    // Pillamos los datos del Player del archivo PlayerData
    public Jugador(){
        this.money = 0;
        this.jugador_image = null;
        this.skill = new String[]{"Habilidad1", "Habilidad2"};
        try {
            this.vida = (double) PlayerData.cargarDato(0);
            this.vida_maxima = 100;
            this.ataque = PlayerData.cargarDato(1);
            this.defensa = PlayerData.cargarDato(0);
            this.ataque_magico = PlayerData.cargarDato(3);
            this.defensa_magica = PlayerData.cargarDato(4);
            this.velocidad = PlayerData.cargarDato(5);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getMoney() {
        return money;
    }

    public Image getJugador_image() {
        return jugador_image;
    }

    public String[] getSkill() {
        return skill;
    }

    public void calcularVida(int vidaQuitada) {
        this.vida -= vidaQuitada;
    }
    public double getVida() {
        return vida;
    }

    public double getVida_maxima() {
        return vida_maxima;
    }

    public double getAtaque() {
        return ataque;
    }

    public double getDefensa() {
        return defensa;
    }

    public double getAtaque_magico() {
        return ataque_magico;
    }

    public double getDefensa_magica() {
        return defensa_magica;
    }

    public double getVelocidad() {return velocidad;}

    //Setters
    public void setMoney(int money) {
        this.money = money;
    }

    public void sumaDinero(int dineroNuevo) {
        this.money += dineroNuevo;
    }

    public void setJugador_image(Image jugador_image) {
        this.jugador_image = jugador_image;
    }

    public void setSkill(String[] skill) {
        this.skill = skill;
    }

    public void setVida(double vida) {
        this.vida = vida;
    }

    public void setVida_maxima(double vida_maxima) {
        this.vida_maxima = vida_maxima;
    }

    public void setAtaque(double ataque) {
        this.ataque = ataque;
    }

    public void setDefensa(double defensa) {
        this.defensa = defensa;
    }

    public void setAtaque_magico(double ataque_magico) {
        this.ataque_magico = ataque_magico;
    }

    public void setDefensa_magica(double defensa_magica) {
        this.defensa_magica = defensa_magica;
    }

    public void setVelocidad(double velocidad) {this.velocidad = velocidad;}

    //Acciones

    public int damageFisico (Monstruo monstruo){
        double ataque = this.ataque;
        double defensaMonstruo = monstruo.getDefensa();
        double vidaMonstruo = monstruo.getVida();

        int fallo = new Random().nextInt(99) + 1;
        if (fallo > 90) return 0;

        double damage = Math.max(0, ataque - defensaMonstruo) + new Random().nextInt(5);

        if (vidaMonstruo - damage <= 0){
            damage = vidaMonstruo;
        }

        return (int) damage;

    }

    public int magicSkill(Monstruo monstruo, int skill, boolean qteSuccess) {
        double ataqueMagico = this.ataque_magico;
        double defensaMagicaMonstruo = monstruo.getDefensa_magica();
        double vidaMonstruo = monstruo.getVida();
        int critico = new Random().nextInt(99) + 1;

        double damageExtra = 1.0;

//        int fallo = new java.util.Random().nextInt(99) + 1;
//        if (fallo > 90) return 0;

        if (skill == 1) {
            damageExtra = 0.75;
            if (critico > 92) {
                damageExtra = 1.5;
            }
        }

        System.out.println("QTE: " + qteSuccess);
        // Ajustar el multiplicador de daño dependiendo de si se acierta o no el QTE
        if (qteSuccess) {
            // Si se acierta el QTE, reducir el daño
            damageExtra *= 1.5; // Reducir el daño a la mitad
        } else {
            // Si se falla el QTE, reducir el daño aún más
            damageExtra *= 0.25; // Reducir el daño a una cuarta parte
        }

        double damage = Math.max(0, ataqueMagico * damageExtra - defensaMagicaMonstruo) + new Random().nextInt(5);
        //si la vida restante del monstruo menos el daño hecho por el monstruo es igual o menor a 0, el daño sera igual a la vida restante del Monstruo.
        if (vidaMonstruo - damage <= 0){
            damage = vidaMonstruo;
        }

        return (int) damage;
    }

    public int damageSkill (Monstruo monstruo, int skill, int potencia){
        double ataqueMagico = this.ataque_magico;
        double defensaMagicaMonstruo = monstruo.getDefensa_magica();
        double vidaMonstruo = monstruo.getVida();
        int critico = new Random().nextInt(9) + 1;

        double damageExtra = 1.0;

        int fallo = new Random().nextInt(99) + 1;
        if (fallo > 90) return 0;

        if (skill == 1) {
            damageExtra = 0.75;
            if (critico > 5) {
                damageExtra = 2.5;
            }
        }

        double damage = Math.max(0, ataqueMagico * damageExtra - defensaMagicaMonstruo) + new Random().nextInt(5);
        //si la vida restante del monstruo menos el daño hecho por el monstruo es igual o menor a 0, el daño sera igual a la vida restante del Monstruo.
        if (vidaMonstruo - damage <= 0){
            damage = vidaMonstruo;
        }

        if (potencia == 1){
            damage = damage/2;
        }

        return (int) damage;
    }

    public void quitarVida(){
        vida = vida-10;
    }

    public boolean Muerto() {
        return this.vida <= 0;
    }
}

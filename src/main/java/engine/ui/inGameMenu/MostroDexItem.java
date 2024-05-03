package engine.ui.inGameMenu;

public class MostroDexItem {
    private int codigo;
    private String nombre;
    private int ataque;
    private int defensa;
    private int atMagico;
    private int defMagica;
    private String descripcion;
    private String imagenMostro;

    public MostroDexItem(int codigo, String nombre, int ataque, int defensa, int atMagico, int defMagica, String descripcion, String imagenMostro) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.atMagico = atMagico;
        this.defMagica = defMagica;
        this.descripcion = descripcion;
        this.imagenMostro = imagenMostro;
    }

    // Métodos getters y setters para acceder a las propiedades
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getAtMagico() {
        return atMagico;
    }

    public void setAtMagico(int atMagico) {
        this.atMagico = atMagico;
    }

    public int getDefMagica() {
        return defMagica;
    }

    public void setDefMagica(int defMagica) {
        this.defMagica = defMagica;
    }

    public String getImagenMostro() {
        return imagenMostro;
    }

    public void setImagenMostro(String imagenMostro) {
        this.imagenMostro = imagenMostro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
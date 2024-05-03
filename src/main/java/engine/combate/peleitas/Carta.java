package engine.combate.peleitas;

public class Carta {
    private TipoCarta tipo;

    public Carta(TipoCarta tipo) {
        this.tipo = tipo;
    }

    // Getters y setters


    public TipoCarta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCarta tipo) {
        this.tipo = tipo;
    }
}


package RPGMonstro.model.domain;


public enum Ameaca {
    Trivial(10),
    Baixa(15),
    Moderada(20),
    Severa(30),
    Extrema(40);
    
    public final int valorXP;
    
    private Ameaca(int valorXP) {
        this.valorXP = valorXP;
    }
}

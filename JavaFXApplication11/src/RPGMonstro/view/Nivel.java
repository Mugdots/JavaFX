
package RPGMonstro.model.domain;

import RPGMonstro.model.domain.Criatura;
import java.util.List;

public class Nivel {
    private int cd_nivel;
    private int criatura_nivel_num;
    private int XP_gasto;
    private List<Criatura> criatura_nivel_encontro;

    
    public Nivel() {
        
    }

    public Nivel(int cd_nivel, int nivel_criatura_id, int XP_gasto, List<Criatura> criatura_nivel_encontro) {
        this.cd_nivel = cd_nivel;
        this.criatura_nivel_num = nivel_criatura_id;
        this.XP_gasto = XP_gasto;
        this.criatura_nivel_encontro = criatura_nivel_encontro;
    }

    public int getCd_nivel() {
        return cd_nivel;
    }

    public void setCd_nivel(int cd_nivel) {
        this.cd_nivel = cd_nivel;
    }

    public int getCriatura_nivel_num() {
        return criatura_nivel_num;
    }

    public void setCriatura_nivel_num(int criatura_nivel_num) {
        this.criatura_nivel_num = criatura_nivel_num;
    }

    public int getXP_gasto() {
        return XP_gasto;
    }

    public void setXP_gasto(int XP_gasto) {
        this.XP_gasto = XP_gasto;
    }

    public List<Criatura> getCriatura_nivel_encontro() {
        return criatura_nivel_encontro;
    }

    public void setCriatura_nivel_encontro(List<Criatura> criatura_nivel_encontro) {
        this.criatura_nivel_encontro = criatura_nivel_encontro;
    }
    
}
    
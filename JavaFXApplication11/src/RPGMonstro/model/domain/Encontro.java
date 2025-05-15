package RPGMonstro.model.domain;

public class Encontro {
    private int cd_encontro;
    private int nivel_grupo_encontro;
    private int tamanho_grupo_encontro;
    private int saldo_XP_encontro;
    private int gasto_XP_encontro;
    private String ameaca_encontro;
    
    public Encontro() {

    }

    public Encontro(int nivel_grupo_encontro, int tamanho_grupo_encontro, int saldo_XP_encontro, String dificuldade_encontro, int gasto_XP_encontro) {
        this.nivel_grupo_encontro = nivel_grupo_encontro;
        this.tamanho_grupo_encontro = tamanho_grupo_encontro;
        this.saldo_XP_encontro = saldo_XP_encontro;
        this.ameaca_encontro = dificuldade_encontro;
        this.gasto_XP_encontro = gasto_XP_encontro;
    }

    public int getCd_encontro() {
        return cd_encontro;
    }

    public void setCd_encontro(int cd_encontro) {
        this.cd_encontro = cd_encontro;
    }

    
    public int getNivel_grupo_encontro() {
        return nivel_grupo_encontro;
    }

    public void setNivel_grupo_encontro(int nivel_grupo_encontro) {
        this.nivel_grupo_encontro = nivel_grupo_encontro;
    }

    public int getTamanho_grupo_encontro() {
        return tamanho_grupo_encontro;
    }

    public void setTamanho_grupo_encontro(int tamanho_grupo_encontro) {
        this.tamanho_grupo_encontro = tamanho_grupo_encontro;
    }

    public int getSaldo_XP_encontro() {
        return saldo_XP_encontro;
    }

    public void setSaldo_XP_encontro(int saldo_XP_encontro) {
        this.saldo_XP_encontro = saldo_XP_encontro;
    }

    public String getAmeaca_encontro() {
        return ameaca_encontro;
    }

    public void setAmeaca_encontro(String ameaca_encontro) {
        this.ameaca_encontro = ameaca_encontro;
    }
    
    public int getGasto_XP_encontro() {
        return this.gasto_XP_encontro;
    }

    public void setGasto_XP_encontro(int gasto_XP_encontro) {
        this.gasto_XP_encontro = gasto_XP_encontro;
    }

}

package RPGMonstro.model.domain;

import java.util.List;



public class Criatura {    
    private int cd_criatura;
    public int nivel_criatura;
    private String nome_criatura; 
    private String tamanho_criatura; 
    private String raridade_criatura;
    private String deslocamento_criatura;
    private String sentido_criatura;
    private int pts_vida_criatura;
    private int classe_armadura_criatura;
 
    public Criatura() {
        
    }
    public Criatura (int nv_criatura, String nome_criatura, String tamanho_criatura, 
            String raridade_criatura, String deslocamento_criatura, 
            String sentido_criatura, int pts_vida_criatura, 
            int classe_armadura_criatura) {
        this.nivel_criatura = nv_criatura;
        this.nome_criatura = nome_criatura;
        this.tamanho_criatura = tamanho_criatura;
        this.raridade_criatura = raridade_criatura;
        this.deslocamento_criatura = deslocamento_criatura;
        this.sentido_criatura = sentido_criatura;
        this.pts_vida_criatura = pts_vida_criatura;
        this.classe_armadura_criatura = classe_armadura_criatura;
                                         
    }
     public void setCd_criatura(int cd_criatura) {
        this.cd_criatura = cd_criatura;
    }

    public void setNivel_criatura(int nivel_criatura) {
        this.nivel_criatura = nivel_criatura;
    }

    public void setNome_criatura(String nome_criatura) {
        this.nome_criatura = nome_criatura;
    }

    public void setTamanho_criatura(String tamanho_criatura) {
        this.tamanho_criatura = tamanho_criatura;
    }

    public void setRaridade_criatura(String raridade_criatura) {
        this.raridade_criatura = raridade_criatura;
    }

    public void setDeslocamento_criatura(String deslocamento_criatura) {
        this.deslocamento_criatura = deslocamento_criatura;
    }

    public void setSentido_criatura(String sentido_criatura) {
        this.sentido_criatura = sentido_criatura;
    }

    public void setPts_vida_criatura(int pts_vida_criatura) {
        this.pts_vida_criatura = pts_vida_criatura;
    }

    public void setClasse_armadura_criatura(int classe_armadura_criatura) {
        this.classe_armadura_criatura = classe_armadura_criatura;
    }
    
    public int getCd_criatura() {
        return cd_criatura;
    }

    public int getNivel_criatura() {
        return nivel_criatura;
    }

    public String getNome_criatura() {
        return nome_criatura;
    }

    public String getTamanho_criatura() {
        return tamanho_criatura;
    }

    public String getRaridade_criatura() {
        return raridade_criatura;
    }

    public String getDeslocamento_criatura() {
        return deslocamento_criatura;
    }

    public String getSentido_criatura() {
        return sentido_criatura;
    }

    public int getPts_vida_criatura() {
        return pts_vida_criatura;
    }

    public int getClasse_armadura_criatura() {
        return classe_armadura_criatura;
    }


}

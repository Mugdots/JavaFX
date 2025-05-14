/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.model.domain;

/**
 *
 * @author PC
 */
public class Criatura_Encontro {
    private int cd_encontro_CE;
    private int cd_criatura_CE;
    private int quant; 

    public int getCd_encontro_CE() {
        return cd_encontro_CE;
    }

    public void setCd_encontro_CE(int cd_encontro_CE) {
        this.cd_encontro_CE = cd_encontro_CE;
    }

    public int getCd_criatura_CE() {
        return cd_criatura_CE;
    }

    public void setCd_criatura_CE(int cd_criatura_CE) {
        this.cd_criatura_CE = cd_criatura_CE;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
    
}

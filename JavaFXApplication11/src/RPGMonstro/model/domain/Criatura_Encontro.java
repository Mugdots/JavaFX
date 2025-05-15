/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.model.domain;


public class Criatura_Encontro {
    private Encontro encontro_CE;
    private Criatura criatura_CE;
    private int quant; 
    
    public Criatura_Encontro() {
        
    }

    public Encontro getEncontro_CE() {
        return encontro_CE;
    }

    public void setEncontro_CE(Encontro encontro_CE) {
        this.encontro_CE = encontro_CE;
    }

    public Criatura getCriatura_CE() {
        return criatura_CE;
    }

    public void setCriatura_CE(Criatura criatura_CE) {
        this.criatura_CE = criatura_CE;
    }


    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }
    
    public Integer getNivelCriaturaCE() {
        return criatura_CE != null ? criatura_CE.getNivel_criatura() : 0;
    }
}

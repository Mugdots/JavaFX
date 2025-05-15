/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.model.domain;

/**
 *
 * @author 20231si008
 */
public class Criatura_Quantidade {
    private Criatura criatura;
    private int quantidade;

    public Criatura_Quantidade() {
        
    }
    
    public Criatura_Quantidade(Criatura criatura, int quantidade) {
        this.criatura = criatura;
        this.quantidade = quantidade;
    }

    public Criatura getCriatura() {
        return criatura;
    }

    public void setCriatura(Criatura criatura) {
        this.criatura = criatura;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}

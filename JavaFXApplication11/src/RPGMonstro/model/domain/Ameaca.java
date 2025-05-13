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

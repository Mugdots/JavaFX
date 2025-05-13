/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.model.domain;

import java.util.List;

public class Nivel {
    private int cd_nivel;
    private int nivel_criatura_id;
    private int XP_gasto;
    private int criatura_max_nivel;
    private List<Criatura> criatura_nivel_encontro;

    
    public Nivel() {
        
    }

    public Nivel(int cd_nivel, int nivel_criatura_id, int XP_gasto, int criatura_max_nivel, List<Criatura> criatura_nivel_encontro) {
        this.cd_nivel = cd_nivel;
        this.nivel_criatura_id = nivel_criatura_id;
        this.XP_gasto = XP_gasto;
        this.criatura_max_nivel = criatura_max_nivel;
        this.criatura_nivel_encontro = criatura_nivel_encontro;
    }

    public int getCd_nivel() {
        return cd_nivel;
    }

    public void setCd_nivel(int cd_nivel) {
        this.cd_nivel = cd_nivel;
    }

    public int getNivel_criatura_id() {
        return nivel_criatura_id;
    }

    public void setNivel_criatura_id(int nivel_criatura_id) {
        this.nivel_criatura_id = nivel_criatura_id;
    }

    public int getXP_gasto() {
        return XP_gasto;
    }

    public void setXP_gasto(int XP_gasto) {
        this.XP_gasto = XP_gasto;
    }

    public int getCriatura_max_nivel() {
        return criatura_max_nivel;
    }

    public void setCriatura_max_nivel(int criatura_max_nivel) {
        this.criatura_max_nivel = criatura_max_nivel;
    }

    public List<Criatura> getCriatura_nivel_encontro() {
        return criatura_nivel_encontro;
    }

    public void setCriatura_nivel_encontro(List<Criatura> criatura_nivel_encontro) {
        this.criatura_nivel_encontro = criatura_nivel_encontro;
    }
    
}
    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.model.dao;


import RPGMonstro.model.domain.Criatura;
import RPGMonstro.model.domain.Nivel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NivelDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Nivel nivel) {
        String sql = "INSERT INTO nivel(nivel_criatura_id, XP_gasto, criatura_max_nivel VALUES(?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, nivel.getNivel_criatura_id());
            stmt.setInt(2, nivel.getXP_gasto());
            stmt.setInt(3, nivel.getCriatura_max_nivel());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NivelDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Nivel Nivel) {   
        String sql = "UPDATE nivel SET nivel_criatura=?, XP_gasto=?, criatura_max_nivel=? WHERE cd_nivel=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Nivel.getNivel_criatura_id());
            stmt.setInt(2, Nivel.getXP_gasto());
            stmt.setInt(3, Nivel.getCriatura_max_nivel());
            stmt.setInt(4, Nivel.getCd_nivel());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NivelDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Nivel Nivel) {
        String sql = "DELETE FROM nivel WHERE cd_nivel=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Nivel.getCd_nivel());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NivelDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Nivel> listar() {
        String sql = "SELECT * FROM nivel";
        List<Nivel> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Nivel nivel = new Nivel();
                List<Criatura> criaturas = new ArrayList();
                nivel.setCd_nivel(resultado.getInt("cd_nivel"));
                nivel.setNivel_criatura_id(resultado.getInt("nivel_criatura_id"));
                nivel.setXP_gasto(resultado.getInt("XP_gasto"));
                nivel.setCriatura_max_nivel(resultado.getInt("criatura_max_nivel"));
                
                CriaturaDAO criaturaDAO = new CriaturaDAO();
                criaturaDAO.setConnection(connection);
                criaturas = criaturaDAO.ListarCriaturaPorNivel(nivel);
                nivel.setCriatura_nivel_encontro(criaturas);
                retorno.add(nivel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NivelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Nivel buscar(Nivel nivel) {
        String sql = "SELECT * FROM Nivel WHERE cd_Nivel=?";
        Nivel retorno = new Nivel();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, nivel.getCd_nivel());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                nivel.setCd_nivel(resultado.getInt("cd_nivel"));
                nivel.setNivel_criatura_id(resultado.getInt("nivel_criatura_id"));
                nivel.setXP_gasto(resultado.getInt("XP_gasto"));
                nivel.setCriatura_max_nivel(resultado.getInt("criatura_max_nivel"));
                retorno = nivel;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NivelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
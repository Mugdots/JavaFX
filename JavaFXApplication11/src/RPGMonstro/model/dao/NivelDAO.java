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
        String sql = "INSERT INTO nivel(nivel_criatura_id, XP_gasto VALUES(?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, nivel.getCriatura_nivel_num());
            stmt.setInt(2, nivel.getXP_gasto());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NivelDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Nivel Nivel) {   
        String sql = "UPDATE nivel SET nivel_criatura=?, XP_gasto=? WHERE cd_nivel=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Nivel.getCriatura_nivel_num());
            stmt.setInt(2, Nivel.getXP_gasto());
            stmt.setInt(3, Nivel.getCd_nivel());
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
                nivel.setCriatura_nivel_num(resultado.getInt("criatura_nivel_num"));
                nivel.setXP_gasto(resultado.getInt("XP_gasto"));
                
                CriaturaDAO criaturaDAO = new CriaturaDAO();
                criaturaDAO.setConnection(connection);
                //criaturas = criaturaDAO.ListarCriaturaPorNivel(nivel);
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
                nivel.setCriatura_nivel_num(resultado.getInt("criatura_nivel_num"));
                nivel.setXP_gasto(resultado.getInt("XP_gasto"));
                retorno = nivel;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NivelDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
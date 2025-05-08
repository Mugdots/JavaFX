
package RPGMonstro.model.dao;

import RPGMonstro.model.domain.Encontro;
import RPGMonstro.model.domain.Nivel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EncontroDAO {
    private Connection connection;

    public Connection getConexao() {
        return connection;
    }

    public void setConexao(Connection conexao) {
        this.connection = connection;
    }
    
  
    public boolean inserir(Encontro encontro) {
          String sql = "INSERT INTO criatura(nivel_grupo_encontro, tamanho_grupo_encontro, saldo_XP_encontro, dificuldade_encontro) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, encontro.getNivel_grupo_encontro());
            stmt.setInt(2, encontro.getTamanho_grupo_encontro());
            stmt.setInt(3, encontro.getSaldo_XP_encontro());
            stmt.setString(4, encontro.getAmeaca_encontro());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EncontroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    public boolean alterar(Encontro encontro) {
        String sql = "UPDATE encontro SET nivel_grupo_encontro=?, tamanho_grupo_encontro=?, saldo_XP_encontro=?, dificuldade_encontro=? WHERE cd_encontro=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, encontro.getNivel_grupo_encontro());
            stmt.setInt(2, encontro.getTamanho_grupo_encontro());
            stmt.setInt(3, encontro.getSaldo_XP_encontro());
            stmt.setString(4, encontro.getAmeaca_encontro());
            stmt.setInt(5, encontro.getCd_encontro());
            stmt.execute();
        return true;
        } catch (SQLException ex) {
            Logger.getLogger(EncontroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public boolean remover(Encontro encontro) {
        String sql = "DELETE FROM encontro WHERE cd_encontro=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, encontro.getCd_encontro());
            stmt.execute();
        return true;
        } catch (SQLException ex) {
            Logger.getLogger(EncontroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public List<Encontro> listar() {
        String sql = "SELECT * FROM encontro";
        List<Encontro> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Encontro encontro = new Encontro();
                List<Nivel> nivel = new ArrayList();
                encontro.setCd_encontro(resultado.getInt("cd_criatura"));
                encontro.setNivel_grupo_encontro(resultado.getInt("nivel_grupo_encontro"));
                encontro.setTamanho_grupo_encontro(resultado.getInt("tamanho_grupo_encontro"));
                encontro.setSaldo_XP_encontro(resultado.getInt("saldo_xp_encontro"));
                encontro.setAmeaca_encontro(resultado.getString("dificuldade_encontro"));
                
                NivelDAO nivelDAO = new NivelDAO();
                nivelDAO.setConnection(connection);
                nivel = nivelDAO.listar();
                encontro.setNivel_criatura_encontro(nivel);
                
                retorno.add(encontro);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
    public Encontro buscar(Encontro encontro) {
        String sql = "SELECT * FROM encontro WHERE cd_criatura=?";
        Encontro retorno = new Encontro();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                encontro.setCd_encontro(resultado.getInt("cd_criatura"));
                encontro.setNivel_grupo_encontro(resultado.getInt("nivel_grupo_encontro"));
                encontro.setTamanho_grupo_encontro(resultado.getInt("tamanho_grupo_encontro"));
                encontro.setSaldo_XP_encontro(resultado.getInt("saldo_xp_encontro"));
                encontro.setAmeaca_encontro(resultado.getString("dificuldade_encontro"));
                retorno = encontro;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncontroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
    
}



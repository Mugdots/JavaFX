package RPGMonstro.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import RPGMonstro.model.domain.Criatura;
import RPGMonstro.model.domain.Encontro;


public class CriaturaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Criatura criatura) {
        String sql = "INSERT INTO criatura(nivel_criatura, nome_criatura, "
                + "tamanho_criatura, raridade_criatura, deslocamento_criatura, "
                + "sentido_criatura, pts_vida_criatura, classe_armadura_criatura) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, criatura.getNivel_criatura());
            stmt.setString(2, criatura.getNome_criatura());
            stmt.setString(3, criatura.getTamanho_criatura());
            stmt.setString(4, criatura.getRaridade_criatura());
            stmt.setString(5, criatura.getDeslocamento_criatura());
            stmt.setString(6, criatura.getSentido_criatura());
            stmt.setInt(7, criatura.getPts_vida_criatura());
            stmt.setInt(8, criatura.getClasse_armadura_criatura());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Criatura criatura) {   
        String sql = "UPDATE criatura SET nivel_criatura=?, nome_criatura=?, "
                + "tamanho_criatura=?, raridade_criatura=?, deslocamento_criatura=?, "
                + "sentido_criatura=?, pts_vida_criatura=?, classe_armadura_criatura=? WHERE cd_criatura=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, criatura.getNivel_criatura());
            stmt.setString(2, criatura.getNome_criatura());
            stmt.setString(3, criatura.getTamanho_criatura());
            stmt.setString(4, criatura.getRaridade_criatura());
            stmt.setString(5, criatura.getDeslocamento_criatura());
            stmt.setString(6, criatura.getSentido_criatura());
            stmt.setInt(7, criatura.getPts_vida_criatura());
            stmt.setInt(8, criatura.getClasse_armadura_criatura());
            stmt.setInt(9, criatura.getCd_criatura());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Criatura criatura) {
        String sql = "DELETE FROM criatura WHERE cd_criatura=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, criatura.getCd_criatura());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Criatura> listar() {
        String sql = "SELECT * FROM criatura";
        List<Criatura> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Criatura criatura = new Criatura();
                criatura.setCd_criatura(resultado.getInt("cd_criatura"));
                criatura.setNivel_criatura(resultado.getInt("nivel_criatura"));
                criatura.setNome_criatura(resultado.getString("nome_criatura"));
                criatura.setTamanho_criatura(resultado.getString("tamanho_criatura"));
                criatura.setRaridade_criatura(resultado.getString("raridade_criatura"));
                criatura.setDeslocamento_criatura(resultado.getString("deslocamento_criatura"));
                criatura.setSentido_criatura(resultado.getString("sentido_criatura"));
                criatura.setPts_vida_criatura(resultado.getInt("pts_vida_criatura"));
                criatura.setClasse_armadura_criatura(resultado.getInt("classe_armadura_criatura"));
                retorno.add(criatura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Criatura buscar(Criatura criatura) {
        String sql = "SELECT * FROM criatura WHERE cd_criatura=?";
        Criatura retorno = new Criatura();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, criatura.getCd_criatura());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                criatura.setCd_criatura(resultado.getInt("cd_criatura"));
                criatura.setNivel_criatura(resultado.getInt("nivel_criatura"));
                criatura.setNome_criatura(resultado.getString("nome_criatura"));
                criatura.setTamanho_criatura(resultado.getString("tamanho_criatura"));
                criatura.setRaridade_criatura(resultado.getString("raridade_criatura"));
                criatura.setDeslocamento_criatura(resultado.getString("deslocamento_criatura"));
                criatura.setSentido_criatura(resultado.getString("sentido_criatura"));
                criatura.setPts_vida_criatura(resultado.getInt("pts_vida_criatura"));
                criatura.setClasse_armadura_criatura(resultado.getInt("classe_armadura_criatura"));
                retorno = criatura;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
    
    public List<Criatura> ListarCriaturaPorNivel(int nivel) {
        String sql = "SELECT * FROM criatura WHERE nivel_criatura=?";
        List<Criatura> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            System.out.println(nivel);
            stmt.setInt(1, nivel);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Criatura criatura = new Criatura();
                criatura.setCd_criatura(resultado.getInt("cd_criatura"));
                criatura.setNivel_criatura(resultado.getInt("nivel_criatura"));
                criatura.setNome_criatura(resultado.getString("nome_criatura"));
                criatura.setTamanho_criatura(resultado.getString("tamanho_criatura"));
                criatura.setRaridade_criatura(resultado.getString("raridade_criatura"));
                criatura.setDeslocamento_criatura(resultado.getString("deslocamento_criatura"));
                criatura.setSentido_criatura(resultado.getString("sentido_criatura"));
                criatura.setPts_vida_criatura(resultado.getInt("pts_vida_criatura"));
                criatura.setClasse_armadura_criatura(resultado.getInt("classe_armadura_criatura"));  
                retorno.add(criatura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
    public List<Criatura> ListarCriaturaPorEncontro(Encontro encontro) {
        String sql = "SELECT c.* FROM criatura c, criatura_encontro ce WHERE c.cd_criatura = ce.cd_criatura_CE AND ce.cd_encontro_CE = ?";
        List<Criatura> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, encontro.getCd_encontro());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Criatura criatura = new Criatura();
                criatura.setCd_criatura(resultado.getInt("cd_criatura"));
                criatura.setNivel_criatura(resultado.getInt("nivel_criatura"));
                criatura.setNome_criatura(resultado.getString("nome_criatura"));
                criatura.setTamanho_criatura(resultado.getString("tamanho_criatura"));
                criatura.setRaridade_criatura(resultado.getString("raridade_criatura"));
                criatura.setDeslocamento_criatura(resultado.getString("deslocamento_criatura"));
                criatura.setSentido_criatura(resultado.getString("sentido_criatura"));
                criatura.setPts_vida_criatura(resultado.getInt("pts_vida_criatura"));
                criatura.setClasse_armadura_criatura(resultado.getInt("classe_armadura_criatura"));  
                retorno.add(criatura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    
}

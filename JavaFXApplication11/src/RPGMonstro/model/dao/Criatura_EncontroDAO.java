package RPGMonstro.model.dao;

import RPGMonstro.model.domain.Criatura;
import RPGMonstro.model.domain.Criatura_Encontro;
import RPGMonstro.model.domain.Encontro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Criatura_EncontroDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(Criatura_Encontro criatura_encontro) {
        String sql = "INSERT INTO criatura_encontro(cd_encontro_CE, cd_criatura_CE, quant_criatura_encontro) VALUES(?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, criatura_encontro.getEncontro_CE().getCd_encontro());
            stmt.setInt(2, criatura_encontro.getCriatura_CE().getCd_criatura());
            stmt.setInt(3, criatura_encontro.getQuant());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public boolean alterar(Criatura_Encontro criatura_encontro) {   
        String sql = "UPDATE criatura SET quant_criatura_encontro=?  WHERE cd_encontro_CE=? AND cd_criatura_CE=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, criatura_encontro.getQuant());
            stmt.setInt(2, criatura_encontro.getEncontro_CE().getCd_encontro());
            stmt.setInt(3, criatura_encontro.getCriatura_CE().getCd_criatura());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(Criatura_Encontro criatura_encontro) {
        String sql = "DELETE FROM criatura_encontro WHERE cd_encontro_CE=? AND cd_criatura_CE=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, criatura_encontro.getEncontro_CE().getCd_encontro());
            stmt.setInt(2, criatura_encontro.getCriatura_CE().getCd_criatura());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Criatura_Encontro> listar(Encontro encontro) {
        String sql = "SELECT * FROM criatura_encontro WHERE cd_encontro_CE=?";
        List<Criatura_Encontro> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, encontro.getCd_encontro());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Criatura c1 = new Criatura();
                Encontro e1 = new Encontro();
                Criatura_Encontro criatura_encontro = new Criatura_Encontro();
                
                criatura_encontro.setQuant(resultado.getInt("quant_criatura_encontro"));
                
                c1.setCd_criatura(resultado.getInt("cd_criatura_CE"));
                CriaturaDAO criaturaDAO = new CriaturaDAO();
                criaturaDAO.setConnection(connection);
                c1 = criaturaDAO.buscar(c1);
                e1.setCd_encontro(resultado.getInt("cd_encontro_CE"));
                EncontroDAO encontroDAO = new EncontroDAO();
                encontroDAO.setConnection(connection);
                e1 = encontroDAO.buscar(e1);
                
                criatura_encontro.setCriatura_CE(c1);
                criatura_encontro.setEncontro_CE(e1);
                retorno.add(criatura_encontro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CriaturaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}

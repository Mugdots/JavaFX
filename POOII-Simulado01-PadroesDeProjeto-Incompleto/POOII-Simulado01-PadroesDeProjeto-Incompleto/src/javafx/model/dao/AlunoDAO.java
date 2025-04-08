package javafx.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.model.domain.Aluno;

public class AlunoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Aluno aluno) {
        String sql = "INSERT INTO alunos(nome, matricula, idade) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setInt(3, aluno.getIdade());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Aluno aluno) {
        String sql = "UPDATE alunos SET nome=?, matricula=?, idade=? WHERE codigo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getMatricula());
            stmt.setInt(3, aluno.getIdade());
            stmt.setInt(4, aluno.getCodigo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Aluno aluno) {
        String sql = "DELETE FROM alunos WHERE codigo=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, aluno.getCodigo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Aluno> listar() {
        String sql = "SELECT * FROM alunos";
        List<Aluno> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Aluno aluno = new Aluno();
                aluno.setCodigo(resultado.getInt("codigo"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setMatricula(resultado.getString("matricula"));
                aluno.setIdade(resultado.getInt("idade"));
                retorno.add(aluno);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Aluno buscar(Aluno aluno) {
        String sql = "SELECT * FROM alunos WHERE codigo=?";
        Aluno retorno = new Aluno();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, aluno.getCodigo());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                aluno.setCodigo(resultado.getInt("codigo"));
                aluno.setNome(resultado.getString("nome"));
                aluno.setMatricula(resultado.getString("matricula"));
                aluno.setIdade(resultado.getInt("idade"));
                retorno = aluno;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}

package javafx.model.domain;

public class Aluno {
    private int codigo;
    private String nome;
    private String matricula;
    private int idade;

    public Aluno(){
    }
    
    public Aluno(String nome, String matricula, int idade){
        this.nome = nome;
        this.matricula = matricula;
        this.idade = idade;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    @Override
    public String toString(){
        return this.nome;
    }

}

package javafx.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.model.dao.AlunoDAO;
import javafx.model.database.Database;
import javafx.model.database.DatabaseFactory;
import javafx.model.domain.Aluno;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLCadastroAlunoController implements Initializable {

    @FXML
    private TableView<Aluno> tableViewAlunos;
    @FXML
    private TableColumn<Aluno, String> tableColumnAlunoNome;
    @FXML
    private TableColumn<Aluno, String> tableColumnAlunoMatricula;
    @FXML
    private TextField textFieldAlunoNome;
    @FXML
    private TextField textFieldAlunoMatricula;
    @FXML
    private TextField textFieldAlunoIdade;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonLimpar;

    private ObservableList<Aluno> observableListAlunos;
    private List<Aluno> listAlunos;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableColumnAlunoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnAlunoMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));

        alunoDAO.setConnection(connection);

        carregarTableViewAlunos();

        // Listener acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewAlunos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewAlunos(newValue));
    }

    public void carregarTableViewAlunos() {
        listAlunos = alunoDAO.listar();
        observableListAlunos = FXCollections.observableArrayList(listAlunos);
        tableViewAlunos.setItems(observableListAlunos);
    }

    public void selecionarItemTableViewAlunos(Aluno aluno) {
        if (aluno != null) {
            textFieldAlunoNome.setText(aluno.getNome());
            textFieldAlunoMatricula.setText(aluno.getMatricula());
            textFieldAlunoIdade.setText(String.valueOf(aluno.getIdade()));
        } else {
            limparTextFields();
        }
    }
    
    //Método reponsável por limpar todos os TextField's
    public void limparTextFields() {
        textFieldAlunoNome.setText("");
        textFieldAlunoMatricula.setText("");
        textFieldAlunoIdade.setText("");
    }

    //Retorna true: caso todos os campos tenham sido preenchidos
    //Retorna false: caso exista algum campo sem preenchimento
    public boolean validouTextFields() {
        String errorMessage = "";

        if (textFieldAlunoNome.getText() == null || textFieldAlunoNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldAlunoMatricula.getText() == null || textFieldAlunoMatricula.getText().length() == 0) {
            errorMessage += "Matrícula inválida!\n";
        }
        if (textFieldAlunoIdade.getText() == null || textFieldAlunoIdade.getText().length() == 0) {
            errorMessage += "Idade inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    //Retorna true: caso não exista aluno selecionado na tabela tableViewAlunos
    //Retorna false: caso exista aluno selecionado na tabela tableViewAlunos
    public boolean limpouSelecaoTableViewAlunos() {
        boolean limpou = tableViewAlunos.getSelectionModel().getSelectedItem() == null;
        if (limpou) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cadastro de Aluno");
            alert.setTitle("Problemas na Inserção do Aluno");
            alert.setContentText("Antes de inserir você deve limpar a seleção da Tabela");
            alert.show();
            return false;
        }
    }

    @FXML
    public void handleButtonLimpar() {
        tableViewAlunos.getSelectionModel().clearSelection();
    }

    @FXML
    public void handleButtonInserir() {
        if (limpouSelecaoTableViewAlunos()) {        
            if(validouTextFields()) {
                Aluno aluno = new Aluno();
                aluno.setNome(textFieldAlunoNome.getText());
                aluno.setMatricula(textFieldAlunoMatricula.getText());
                aluno.setIdade(Integer.parseInt(textFieldAlunoIdade.getText()));
                
                alunoDAO.inserir(aluno);
                carregarTableViewAlunos();
                limparTextFields();
            }
        }
    }

    @FXML
    public void handleButtonAlterar() {
        if(validouTextFields()) {
            Aluno aluno = tableViewAlunos.getSelectionModel().getSelectedItem();
            aluno.setNome(textFieldAlunoNome.getText());
            aluno.setMatricula(textFieldAlunoMatricula.getText());
            aluno.setIdade(Integer.parseInt(textFieldAlunoIdade.getText()));
            alunoDAO.alterar(aluno);
            carregarTableViewAlunos();
        }
    
    }

    @FXML
    public void handleButtonRemover() {
        if(validouTextFields()) {
            Aluno aluno = tableViewAlunos.getSelectionModel().getSelectedItem();
            alunoDAO.remover(aluno);
            carregarTableViewAlunos();
        }
    }

    
}

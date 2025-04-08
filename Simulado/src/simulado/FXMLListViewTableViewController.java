/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulado;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLListViewTableViewController implements Initializable {

    @FXML
    private TableView<Aluno> tableViewAluno;
    @FXML
    private TableColumn<Aluno, String> tableColumnNome;
    @FXML
    private TableColumn<Aluno, String> tableColumnMatricula;
    @FXML
    private TableColumn<Aluno, Integer> tableColumnIdade;
    @FXML
    private ListView<Aluno> listViewAluno;

    private ObservableList<Aluno> observableListAluno;
    
    private final List<Aluno> listAluno = new ArrayList<Aluno>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       carregarListAluno();
       
      tableViewAluno.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarTableViewAlunos(newValue));
    }
    
    public void carregarListAluno() {
       Aluno a1 = new Aluno("Daniel", "20231si008", 21);
       Aluno a2 = new Aluno("Gustavo", "20121si003", 18);
       Aluno a3 = new Aluno("Alex", "20241si021", 16);
       tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
       tableColumnMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
       tableColumnIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
       listAluno.add(a1);
       listAluno.add(a2);
       listAluno.add(a3);
       
       observableListAluno = FXCollections.observableArrayList(listAluno);
       tableViewAluno.setItems(observableListAluno);
   }
    
    public void selecionarTableViewAlunos(Aluno aluno) {
        if (aluno != null) {
            System.out.println("Aluno selecionado no ListView: " + aluno.getNome());
        } else {
            System.out.println("Cliente NULL!");
        }
    }
    
    
   
    public void handleButtonInserir() {
        Aluno aluno = tableViewAluno.getSelectionModel().getSelectedItem();
        if (aluno != null) {
            tableViewAluno.getItems().remove(aluno);
            listViewAluno.getItems().add(aluno);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um aluno na Tabela");
            alert.show();
        }
        
   }
    public void handleButtonRemover() {
        Aluno aluno = listViewAluno.getSelectionModel().getSelectedItem();
        if (aluno != null) {            
            listViewAluno.getItems().remove(aluno);
            tableViewAluno.getItems().add(aluno);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um aluno na Lista");
            alert.show();
        }
        
       
   }
}


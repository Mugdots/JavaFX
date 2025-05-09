/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.Controller;

import RPGMonstro.model.dao.CriaturaDAO;
import RPGMonstro.model.database.Database;
import RPGMonstro.model.database.DatabaseFactory;
import RPGMonstro.model.domain.Criatura;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLAnchorPaneCadastroCriaturaController implements Initializable {

    @FXML
    private TableView<Criatura> tableViewCriatura;
    @FXML
    private TableColumn<Criatura, String> tableColumnNomeCriatura;
    @FXML
    private TableColumn<Criatura, String> tableColumnRaridadeCriatura;
    @FXML
    private TableColumn<Criatura, String> tableColumnTamanhoCriatura;
    @FXML
    private TableColumn<Criatura, Integer> tableColumnNivelCriatura;
    @FXML
    private Label labelNomeCriatura;
    @FXML
    private Label labelNivelCriatura;
    @FXML
    private Label labelRaridadeCriatura;
    @FXML
    private Label labelTamanhoCriatura;
    @FXML
    private Label labelCACriatura;
    @FXML
    private Label labelPVCriatura;
    @FXML
    private Label labelSentidoCriatura;
    @FXML
    private Label labelDeslocamentoCriatura;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;

    private List<Criatura> listCriatura;
    private ObservableList<Criatura> observableListCriatura;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CriaturaDAO criaturaDAO = new CriaturaDAO();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criaturaDAO.setConnection (connection);
        //carregarTableViewCriatura();
        
        //selecionarItemTableViewCriatura(null);
        
        //tableViewCriatura.getSelectionModel().selectedItemProperty().addListener(
        //        (observable, oldValue, newValue) -> selecionarItemTableViewCriatura(newValue));
        
    }    
    
    public void carregarTableViewCriatura() {
        tableColumnNomeCriatura.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnRaridadeCriatura.setCellValueFactory(new PropertyValueFactory<>("raridade"));
        tableColumnTamanhoCriatura.setCellValueFactory(new PropertyValueFactory<>("tamanho"));
        tableColumnNivelCriatura.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        
        listCriatura = criaturaDAO.listar();
        
        observableListCriatura = FXCollections.observableArrayList(listCriatura);
        tableViewCriatura.setItems(observableListCriatura);
    }
    
    public void selecionarItemTableViewCriatura(Criatura criatura) {
        if (criatura != null) {
            labelNomeCriatura.setText(String.valueOf(criatura.getNome_criatura()));
            labelNivelCriatura.setText(String.valueOf(criatura.getNivel_criatura()));
            labelRaridadeCriatura.setText(String.valueOf(criatura.getRaridade_criatura()));
            labelTamanhoCriatura.setText(String.valueOf(criatura.getTamanho_criatura()));
            labelCACriatura.setText(String.valueOf(criatura.getClasse_armadura_criatura()));
            labelPVCriatura.setText(String.valueOf(criatura.getPts_vida_criatura()));
            labelSentidoCriatura.setText(String.valueOf(criatura.getSentido_criatura()));
            labelDeslocamentoCriatura.setText(String.valueOf(criatura.getDeslocamento_criatura()));
        } else {
            labelNomeCriatura.setText("");
            labelNivelCriatura.setText("");
            labelRaridadeCriatura.setText("");
            labelTamanhoCriatura.setText("");
            labelCACriatura.setText("");
            labelPVCriatura.setText("");
            labelSentidoCriatura.setText("");
            labelDeslocamentoCriatura.setText("");
            
        }
    }
    
    
}

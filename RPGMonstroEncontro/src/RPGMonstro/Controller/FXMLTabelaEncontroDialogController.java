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
import javafx.stage.Stage;

/**
 *
 * @author 20231si008
 */
public class FXMLTabelaEncontroDialogController implements Initializable {
    
    @FXML
    private TableView<Criatura> tableViewCriaturaPorNivel;
    @FXML
    private TableColumn<Criatura, String> tableColumnNome;
    @FXML
    private TableColumn<Criatura, Integer> tableColumnPV;
    @FXML
    private TableColumn<Criatura, Integer> tableColumnCA;
    @FXML
    private TableColumn<Criatura, String> tableColumnTamanho;
    private TableColumn<Criatura, String> tableColumnSentido;
    private TableColumn<Criatura, String> tableColumnDeslocamento;
    @FXML
    private TableColumn<Criatura, String> tableColumnRaridade;

    @FXML
    private Button buttonConfimar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Label labelNome;
    @FXML
    private Label labelDeslocamento;
    @FXML
    private Label labelSentido;
    @FXML
    private Label labelRaridade;

    
    private Stage dialogStage;
    private boolean botaoConfimarClicado = false;
    private List<Criatura> listCriatura;
    private ObservableList<Criatura> observableListCriatura;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CriaturaDAO criaturaDAO = new CriaturaDAO();
    
    private Criatura criatura;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criaturaDAO.setConnection (connection);
        carregarTableViewCriatura();
        
        selecionarItemTableViewCriatura(null);
        
        
        tableViewCriaturaPorNivel.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCriatura(newValue));
    }

    public Stage getStage() {
        return dialogStage;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public boolean oBotaofoiClicado() {
        return botaoConfimarClicado;
    }
    
    @FXML
    private void handleButtonConfimar() {
        criatura = tableViewCriaturaPorNivel.getSelectionModel().getSelectedItem();
        botaoConfimarClicado = true;
        dialogStage.close();
    }
        
    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }
    
    public void carregarTableViewCriatura() {
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome_criatura"));
        tableColumnPV.setCellValueFactory(new PropertyValueFactory<>("pts_vida_criatura"));
        tableColumnCA.setCellValueFactory(new PropertyValueFactory<>("classe_armadura_criatura"));
        tableColumnSentido.setCellValueFactory(new PropertyValueFactory<>("sentido_criatura"));
        tableColumnDeslocamento.setCellValueFactory(new PropertyValueFactory<>("deslocamento_criatura"));
        tableColumnRaridade.setCellValueFactory(new PropertyValueFactory<>("raridade_criatura"));
        tableColumnTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanho_criatura"));
        
        listCriatura = criaturaDAO.ListarCriaturaPorNivel(20);
        observableListCriatura = FXCollections.observableArrayList(listCriatura);
        tableViewCriaturaPorNivel.setItems(observableListCriatura);
    }
    
    public void selecionarItemTableViewCriatura(Criatura criatura) {
        if (criatura != null) {
            labelNome.setText(String.valueOf(criatura.getNome_criatura()));
            labelRaridade.setText(String.valueOf(criatura.getRaridade_criatura()));
            labelSentido.setText(String.valueOf(criatura.getSentido_criatura()));
            labelDeslocamento.setText(String.valueOf(criatura.getDeslocamento_criatura()));
        } else {
            labelNome.setText("");
            labelRaridade.setText("");
            labelSentido.setText("");
            labelDeslocamento.setText("");
            
        }
    }

}

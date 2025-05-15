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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author 20231si008
 */
public class FXMLAnchorPaneTabelaEncontroDialogController implements Initializable {
    
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
    @FXML
    private TableColumn<Criatura, String> tableColumnRaridade;

    @FXML
    private Label labelNome;
    @FXML
    private Label labelDeslocamento;
    @FXML
    private Label labelSentido;
    @FXML
    private Label labelRaridade;
    @FXML
    private Label labelId;
    @FXML
    private Spinner<Integer> spinnerQuantCriatura;
    private Stage dialogStage;
    private boolean botaoConfimarClicado = false;
    
    private List<Criatura> listCriatura;
    private ObservableList<Criatura> observableListCriatura;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CriaturaDAO criaturaDAO = new CriaturaDAO();
    
    private Criatura criatura;
    private int nivelCriatura;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarSpinnerQuantidadeCriatura();
    }
    
    public boolean oBotaofoiClicado() {
        return botaoConfimarClicado;
    } 

    public Stage getStage() {
        return dialogStage;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setCriatura(Criatura criatura) {
        this.criatura = criatura;
    }
    
    public Integer getQuantidade() {
        return spinnerQuantCriatura.getValue();
    }

    public void setNivelCriatura(int nivel) {
        this.nivelCriatura = nivel;
        criaturaDAO.setConnection (connection);
        carregarTableViewCriatura();
        
        selecionarItemTableViewCriatura(null);
        
        
        tableViewCriaturaPorNivel.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCriatura(newValue));
    }
    
    public void carregarSpinnerQuantidadeCriatura() {
        SpinnerValueFactory<Integer> valueSpinnerQuant = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        spinnerQuantCriatura.setValueFactory(valueSpinnerQuant);
    }

    public void carregarTableViewCriatura() {
        listCriatura = criaturaDAO.ListarCriaturaPorNivel(nivelCriatura);    
        if (listCriatura != null) {
            tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome_criatura"));
            tableColumnPV.setCellValueFactory(new PropertyValueFactory<>("pts_vida_criatura"));
            tableColumnCA.setCellValueFactory(new PropertyValueFactory<>("classe_armadura_criatura"));
            tableColumnRaridade.setCellValueFactory(new PropertyValueFactory<>("raridade_criatura"));
            tableColumnTamanho.setCellValueFactory(new PropertyValueFactory<>("tamanho_criatura"));


            observableListCriatura = FXCollections.observableArrayList(listCriatura);
            tableViewCriaturaPorNivel.setItems(observableListCriatura);
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erro no Cadastro");
            alert1.setHeaderText("Campos Inválidos, por favor, corrija..." );
            alert1.show();
            dialogStage.close();
        }
    }
    
    @FXML
    private void handleButtonConfimar() {
        Criatura criatura1 = tableViewCriaturaPorNivel.getSelectionModel().getSelectedItem();
        if (criatura1 != null) {
            criatura.setCd_criatura(criatura1.getCd_criatura());
            criatura.setNome_criatura(criatura1.getNome_criatura());
            criatura.setNivel_criatura(criatura1.getNivel_criatura());
            criatura.setClasse_armadura_criatura(criatura1.getClasse_armadura_criatura());
            criatura.setPts_vida_criatura(criatura1.getPts_vida_criatura());
            criatura.setDeslocamento_criatura(criatura1.getDeslocamento_criatura());
            criatura.setRaridade_criatura(criatura1.getRaridade_criatura());
            criatura.setSentido_criatura(criatura1.getSentido_criatura());
            criatura.setTamanho_criatura(criatura1.getTamanho_criatura());
            botaoConfimarClicado = true;    
            dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nada foi Selecionado");
            alert.show();
            botaoConfimarClicado = false; 
        }
    }
        
    @FXML
    private void handleButtonCancelar() {
        dialogStage.close();
    }
    

    public void selecionarItemTableViewCriatura(Criatura criatura) {
        if (criatura != null) {
            labelNome.setText(String.valueOf(criatura.getNome_criatura()));
            labelRaridade.setText(String.valueOf(criatura.getRaridade_criatura()));
            labelSentido.setText(String.valueOf(criatura.getSentido_criatura()));
            labelDeslocamento.setText(String.valueOf(criatura.getDeslocamento_criatura()));
            labelId.setText(String.valueOf(criatura.getCd_criatura()));
        } else {
            labelNome.setText("");
            labelRaridade.setText("");
            labelSentido.setText("");
            labelDeslocamento.setText("");
            
        }
    }

}

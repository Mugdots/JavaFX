/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.Controller;

import RPGMonstro.model.dao.CriaturaDAO;
import RPGMonstro.model.dao.Criatura_EncontroDAO;
import RPGMonstro.model.dao.EncontroDAO;
import RPGMonstro.model.database.Database;
import RPGMonstro.model.database.DatabaseFactory;
import RPGMonstro.model.domain.Criatura;
import RPGMonstro.model.domain.Criatura_Encontro;
import RPGMonstro.model.domain.Encontro;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class FXMLAnchorPaneProcessoEncontroController implements Initializable {

    @FXML
    private TableView<Encontro> tableViewEncontro;
    @FXML
    private TableColumn<Encontro, Integer> tableColumnNivelGrupo;
    @FXML
    private TableColumn<Encontro, Integer> tableColumnTamanhoGrupo;
    @FXML
    private TableColumn<Encontro, String> tableColumnAmeacaEncontro;
    @FXML
    private AnchorPane AnchorPaneLabelCriaturasNivel;
    @FXML
    private TableColumn<Criatura, String> tableColumnNomeCriatura;
    @FXML
    private TableColumn<Criatura, Integer> tableColumnNivelCriatura;
    @FXML
    private TableColumn<Criatura, String> tableColumnRaridadeCriatura;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonAlterar;
    @FXML
    private TableView<Criatura> tableViewCriatura;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final CriaturaDAO criaturaDAO = new CriaturaDAO();
    private final EncontroDAO encontroDAO = new EncontroDAO();
    private final Criatura_EncontroDAO criatura_encontroDAO = new Criatura_EncontroDAO();
    private List<Criatura> listCriatura;
    private ObservableList<Criatura> observableListCriatura;
    private List<Encontro> listEncontro;
    private ObservableList<Encontro> observableListEncontro;
    @FXML
    private AnchorPane anchorPane;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criaturaDAO.setConnection (connection);
        encontroDAO.setConnection(connection);
        criatura_encontroDAO.setConnection(connection);
        carregarTableViewEncontro();
        //selecionarItemTableViewEncontro(null);
        
        tableViewEncontro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewEncontro(newValue));
        
    }    

    @FXML
    private void handleButtonInserir(ActionEvent event) throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/RPGMonstro/view/FXMLAnchorPaneTabelaEncontro.fxml"));
        anchorPane.getChildren().setAll(a);
        
    }

    @FXML
    private void handleButtonRemover(ActionEvent event) {
        Encontro e1 = tableViewEncontro.getSelectionModel().getSelectedItem();
        if (e1 != null) {
            List<Criatura_Encontro> listCriatura_Encontro = criatura_encontroDAO.listar();
            for (Criatura_Encontro ce : listCriatura_Encontro) {
                criatura_encontroDAO.remover(ce);
            }
            
            encontroDAO.remover(e1);
            carregarTableViewEncontro();
        } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Por favor, escolha uma criatura na Tabela");
                al.show();
        }
    }

    @FXML
    private void handleButtonAlterar(ActionEvent event) {
    }

    private void carregarTableViewEncontro() {
        tableColumnNivelGrupo.setCellValueFactory(new PropertyValueFactory<>("nivel_grupo_encontro"));
        tableColumnTamanhoGrupo.setCellValueFactory(new PropertyValueFactory<>("tamanho_grupo_encontro"));
        tableColumnAmeacaEncontro.setCellValueFactory(new PropertyValueFactory<>("ameaca_encontro"));
        
        
        listEncontro = encontroDAO.listar();        
        observableListEncontro = FXCollections.observableArrayList(listEncontro);
        tableViewEncontro.setItems(observableListEncontro);    
    }
    
     private void carregarTableViewCriatura(Encontro encontro) {
        tableColumnNomeCriatura.setCellValueFactory(new PropertyValueFactory<>("nome_criatura"));
        tableColumnRaridadeCriatura.setCellValueFactory(new PropertyValueFactory<>("raridade_criatura"));
        tableColumnNivelCriatura.setCellValueFactory(new PropertyValueFactory<>("nivel_criatura"));
        
        listCriatura = criaturaDAO.ListarCriaturaPorEncontro(encontro);
        observableListCriatura = FXCollections.observableArrayList(listCriatura);
        tableViewCriatura.setItems(observableListCriatura);    
    }

    private void selecionarItemTableViewEncontro(Encontro encontro) {
        carregarTableViewCriatura(encontro);
        
    }
    
}

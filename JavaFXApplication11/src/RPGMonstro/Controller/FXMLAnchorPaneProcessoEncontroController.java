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
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableView<Criatura_Encontro> tableViewCriatura;
    @FXML
    private TableColumn<Criatura_Encontro, String> tableColumnNomeCriatura;
    @FXML
    private TableColumn<Criatura_Encontro, Integer> tableColumnNivelCriatura;
    @FXML
    private TableColumn<Criatura_Encontro, String> tableColumnRaridadeCriatura;
    
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonAlterar;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final EncontroDAO encontroDAO = new EncontroDAO();
    private final Criatura_EncontroDAO criatura_encontroDAO = new Criatura_EncontroDAO();
    
    private List<Encontro> listEncontro;
    private ObservableList<Encontro> observableListEncontro;
    
    private List<Criatura_Encontro> listCE;
    private ObservableList<Criatura_Encontro> observableListCE;
    
    
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TableColumn<Criatura_Encontro, Integer> tableColumnQuantidadeCriatura;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        encontroDAO.setConnection(connection);
        criatura_encontroDAO.setConnection(connection);
        carregarTableViewEncontro();
        
        tableViewEncontro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewEncontro(newValue));
        
    }    

    @FXML
    private void handleButtonInserir(ActionEvent event) throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/RPGMonstro/view/FXMLAnchorPaneTabelaEncontro.fxml"));
        anchorPane.getChildren().setAll(a);
        
    }

    @FXML
    private void handleButtonRemover() throws IOException {
        try {
            connection.setAutoCommit(false);
            Encontro e1 = tableViewEncontro.getSelectionModel().getSelectedItem();
            System.out.println(e1.getCd_encontro());
            if (e1 != null) {
                List<Criatura_Encontro> listCriatura_Encontro = criatura_encontroDAO.listar(e1);
                listCriatura_Encontro.forEach((ce) -> {
                    criatura_encontroDAO.remover(ce);
                });
                encontroDAO.remover(e1);
                connection.commit();                
                carregarTableViewEncontro();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Por favor, escolha uma criatura na Tabela");
                al.show();
                connection.rollback();
            }
        } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLAnchorPaneTabelaEncontroController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLAnchorPaneTabelaEncontroController.class.getName()).log(Level.SEVERE, null, ex);
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
        tableColumnNomeCriatura.setCellValueFactory(cellData -> {
            Criatura criatura = cellData.getValue().getCriatura_CE();
            String nome = (criatura != null) ? criatura.getNome_criatura(): "";
            return new SimpleStringProperty(nome);
        });
        
        tableColumnRaridadeCriatura.setCellValueFactory(cellData -> {
            Criatura criatura = cellData.getValue().getCriatura_CE();
            String raridade = (criatura != null) ? criatura.getRaridade_criatura(): "";
            return new SimpleStringProperty(raridade);
        });
        
        tableColumnNivelCriatura.setCellValueFactory(cellData -> {
            Criatura criatura = cellData.getValue().getCriatura_CE();
            Integer nivel = (criatura != null) ? criatura.getNivel_criatura(): 0;
            return new SimpleIntegerProperty(nivel).asObject();
        });
        tableColumnQuantidadeCriatura.setCellValueFactory(new PropertyValueFactory<>("quant"));
        
        listCE = criatura_encontroDAO.listar(encontro);
        observableListCE = FXCollections.observableArrayList(listCE);
        tableViewCriatura.setItems(observableListCE);    
    }

    private void selecionarItemTableViewEncontro(Encontro encontro) {
        if (encontro != null) {
            carregarTableViewCriatura(encontro);
        } else {
            tableViewCriatura.getItems().clear();
        }
    }
    
}

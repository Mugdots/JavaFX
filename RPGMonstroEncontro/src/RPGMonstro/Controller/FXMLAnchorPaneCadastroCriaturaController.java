package RPGMonstro.Controller;


import RPGMonstro.model.domain.Criatura;
import RPGMonstro.model.dao.CriaturaDAO;
import RPGMonstro.model.database.Database;
import RPGMonstro.model.database.DatabaseFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


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
        carregarTableViewCriatura();
        
        selecionarItemTableViewCriatura(null);
        
        
        tableViewCriatura.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewCriatura(newValue));
        
    }    
    
    public void carregarTableViewCriatura() {
        tableColumnNomeCriatura.setCellValueFactory(new PropertyValueFactory<>("nome_criatura"));
        tableColumnRaridadeCriatura.setCellValueFactory(new PropertyValueFactory<>("raridade_criatura"));
        tableColumnTamanhoCriatura.setCellValueFactory(new PropertyValueFactory<>("tamanho_criatura"));
        tableColumnNivelCriatura.setCellValueFactory(new PropertyValueFactory<>("nivel_criatura"));
        
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
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Criatura c1 = new Criatura();
        boolean botaoConfimarClicado = mostrarFXMLAnchorPaneCadastroCriaturaDialog(c1);
        if (botaoConfimarClicado) {
            criaturaDAO.inserir(c1);
            carregarTableViewCriatura();
        }
    }
    
    @FXML       
    public void handleButtonAlterar() throws IOException {
        Criatura c1 = tableViewCriatura.getSelectionModel().getSelectedItem();
        if (c1 != null) {
            boolean botaoConfimarClicado = mostrarFXMLAnchorPaneCadastroCriaturaDialog(c1);
            if (botaoConfimarClicado) {
                criaturaDAO.alterar(c1);
                carregarTableViewCriatura();
            } 
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Por favor, escolha uma criatura na Tabela");
            al.show();
        }
    }
    
    @FXML         
    public void handleButtonRemover() throws IOException {
        Criatura c1 = tableViewCriatura.getSelectionModel().getSelectedItem();
        if (c1 != null) {
            criaturaDAO.remover(c1);
            carregarTableViewCriatura();
        } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Por favor, escolha uma criatura na Tabela");
                al.show();
        }
    }
    
    public boolean mostrarFXMLAnchorPaneCadastroCriaturaDialog(Criatura criatura) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroCriaturaDialogController.class.getResource("/RPGMonstro/view/FXMLAnchorPaneCadastroCriaturaDialog.fxml"));
        AnchorPane pagina = (AnchorPane) loader.load();
        
        // Cliando um Estágio de Diálogo
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Criatura");
        Scene cena = new Scene(pagina);
        dialogStage.setScene(cena);
        
        // Setando o cliente no Controller
        FXMLAnchorPaneCadastroCriaturaDialogController controlador = loader.getController();
        controlador.setDialogStage(dialogStage);
        controlador.setCriatura(criatura);
        
        // Mostrar o Dialog e esperar até que o usuário o fecha
        dialogStage.showAndWait();
        return controlador.oBotaofoiClicado();
    }
}

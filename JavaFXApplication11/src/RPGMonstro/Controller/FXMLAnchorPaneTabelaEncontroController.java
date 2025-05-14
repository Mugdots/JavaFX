package RPGMonstro.Controller;

import RPGMonstro.model.dao.CriaturaDAO;
import RPGMonstro.model.dao.Criatura_EncontroDAO;
import RPGMonstro.model.dao.EncontroDAO;
import RPGMonstro.model.database.Database;
import RPGMonstro.model.database.DatabaseFactory;
import RPGMonstro.model.domain.Ameaca;
import RPGMonstro.model.domain.Criatura;
import RPGMonstro.model.domain.Criatura_Encontro;
import RPGMonstro.model.domain.Encontro;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLAnchorPaneTabelaEncontroController implements Initializable {
  
    @FXML
    private ChoiceBox<Ameaca> choiceBoxAmeacaEncontro;
    @FXML
    private ChoiceBox<Criatura> choiceBoxCriatura;
    @FXML
    private Label labelSaldoXP;
    @FXML
    private Label labelGasto;
    @FXML
    private Label labelRestante;
    @FXML
    private Label labelNivel;
    @FXML
    private Label labelXPNivel;
    @FXML
    private Label labelCriaturaMaxima;
    @FXML
    private Label labelCriaturaRestante;
    @FXML
    private Spinner<Integer> spinnerTamanhoCriatura;
    @FXML
    private Spinner<Integer> spinnerNivelCriatura;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
   
    
    private List<Ameaca> listAmeacaEncontro = new ArrayList();
    private ObservableList<Ameaca> observableListAmeacaEncontro;
    List<Criatura> listCriaturaPorNivel = new ArrayList();
    ObservableList<Criatura> observableListCriaturaPorNivel;
    
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final EncontroDAO encontroDAO = new EncontroDAO();
    private final CriaturaDAO criaturaDAO = new CriaturaDAO();
    private final Criatura_EncontroDAO criatura_encontroDAO = new Criatura_EncontroDAO();
    @FXML
    private AnchorPane anchoPaneEncontro;
    
    private int xpGasto = 0;
    Integer[] nivelXP = {10, 15, 20, 30, 40, 60, 80, 120, 160};
    @FXML
    private Button buttonInserirEncontro;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criaturaDAO.setConnection (connection);
        spinnerNivelCriatura.setValueFactory(carregarSpinnerEncontro(1, 20, 1));
        spinnerTamanhoCriatura.setValueFactory(carregarSpinnerEncontro(2, 10, 4));
        carregarChoiceBoxAmeacaEncontro();
        carregarCriadorDesignEncontro();
        
        //selecionarItemTableViewNivel(null);
        
        
        //tableViewNivel.getSelectionModel().selectedItemProperty().addListener(
        //        (observable, oldValue, newValue) -> selecionarItemTableViewNivel(newValue));
        
    }    
    @FXML
    public void carregarCriadorDesignEncontro() {
        int tamanhoGrupoEncontro = spinnerTamanhoCriatura.getValue();
        int nivelGrupoEncontro = spinnerNivelCriatura.getValue(); 
        Ameaca ameacaEncontro = choiceBoxAmeacaEncontro.getSelectionModel().getSelectedItem();
        
        int xp = (tamanhoGrupoEncontro * ameacaEncontro.valorXP);

        int a = 0;
        for (int i = -3; i < 25; i++) {
            if (i >= nivelGrupoEncontro - 4 && i <= nivelGrupoEncontro + 4) {
                if (xp >= nivelXP[a] && i != -2 && i != -3) {
                    int tamanhoListaCriatura = listCriaturaPorNivel.size();
                    int criaturaMax = xp / nivelXP[a];
                    labelNivel.setText(String.valueOf(i));
                    labelXPNivel.setText(String.valueOf(nivelXP[a]));
                    labelCriaturaMaxima.setText(String.valueOf(criaturaMax));
                    labelCriaturaRestante.setText(String.valueOf(criaturaMax - tamanhoListaCriatura));                    
                    labelSaldoXP.setText(String.valueOf(xp));
                    labelGasto.setText(String.valueOf(xpGasto));
                    labelRestante.setText(String.valueOf(xp - xpGasto));
                }
                a++;     
            }
        }
    }

    public void carregarChoiceBoxCriaturasPorNivel() {
        observableListCriaturaPorNivel = FXCollections.observableArrayList(listCriaturaPorNivel);
        choiceBoxCriatura.setItems(observableListCriaturaPorNivel);
    }
        
    public void carregarChoiceBoxAmeacaEncontro() {
        Collections.addAll(listAmeacaEncontro, Ameaca.values());
        observableListAmeacaEncontro = FXCollections.observableArrayList(listAmeacaEncontro);
        choiceBoxAmeacaEncontro.setItems(observableListAmeacaEncontro);
        choiceBoxAmeacaEncontro.setValue(Ameaca.Moderada);
    }
    
    public SpinnerValueFactory<Integer> carregarSpinnerEncontro(int a, int b, int c) {
        SpinnerValueFactory<Integer> valueFactoryNivelEncontro = new SpinnerValueFactory.IntegerSpinnerValueFactory(a, b);
        valueFactoryNivelEncontro.setValue(c);
        return valueFactoryNivelEncontro;
    }

    @FXML
    private void handleButtonInserirCriatura() throws IOException {
        Criatura c1 = new Criatura();
        boolean botaoConfimarClicado = mostrarFXMLAnchorPaneTabelaEncontroDialog(c1);
        if (botaoConfimarClicado) {
            listCriaturaPorNivel.add(c1);
            xpGasto += Integer.valueOf(labelXPNivel.getText());
            carregarChoiceBoxCriaturasPorNivel();
            carregarCriadorDesignEncontro();
        }
    }

    @FXML
    private void handleButtonRemoverCriatura() {
        Criatura c1 = choiceBoxCriatura.getSelectionModel().getSelectedItem();
        if (c1 != null) {
            listCriaturaPorNivel.remove(c1);
            carregarCriadorDesignEncontro();
        }
    }
    
    
    
    @FXML
    private void handlebuttonInserirEncontro() throws IOException {
        Encontro encontro = new Encontro();
        try {
            connection.setAutoCommit(false);
            encontroDAO.setConnection(connection);
            criatura_encontroDAO.setConnection(connection);
            encontro.setAmeaca_encontro(String.valueOf(choiceBoxAmeacaEncontro.getSelectionModel().getSelectedItem()));
            encontro.setGasto_XP_encontro(xpGasto);
            encontro.setNivel_grupo_encontro(spinnerNivelCriatura.getValue());
            encontro.setSaldo_XP_encontro(Integer.valueOf(labelSaldoXP.getText()));
            encontro.setTamanho_grupo_encontro(spinnerTamanhoCriatura.getValue());
            encontroDAO.inserir(encontro);

            
            for (Criatura criatura: listCriaturaPorNivel) {
                System.out.println(encontro.getCd_encontro());
                Criatura_Encontro ce = new Criatura_Encontro();
                ce.setCd_criatura_CE(criatura.getCd_criatura());
                ce.setCd_encontro_CE(encontro.getCd_encontro());
                ce.setQuant(1);
                criatura_encontroDAO.inserir(ce);
            }
            connection.commit();
        } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLAnchorPaneTabelaEncontroController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLAnchorPaneTabelaEncontroController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    public boolean mostrarFXMLAnchorPaneTabelaEncontroDialog(Criatura criatura) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneTabelaEncontroDialogController.class.getResource("/RPGMonstro/view/FXMLAnchorPaneTabelaEncontroDialog.fxml"));
        AnchorPane pagina = (AnchorPane) loader.load();

        // Cliando um Estágio de Diálogo
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Seleção de Criaturas");
        Scene cena = new Scene(pagina);
        dialogStage.setScene(cena);

        // Setando o cliente no Controller
        FXMLAnchorPaneTabelaEncontroDialogController controlador = loader.getController();
        controlador.setNivelCriatura(Integer.valueOf(labelNivel.getText())); // Nível da criatura
        controlador.setDialogStage(dialogStage);
        controlador.setCriatura(criatura);

        // Mostrar o Dialog e esperar até que o usuário o fecha
        dialogStage.showAndWait();
        return controlador.oBotaofoiClicado();
    }
}
     

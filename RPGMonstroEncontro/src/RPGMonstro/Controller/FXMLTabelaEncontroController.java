package RPGMonstro.Controller;

import RPGMonstro.model.dao.CriaturaDAO;
import RPGMonstro.model.dao.EncontroDAO;
import RPGMonstro.model.database.Database;
import RPGMonstro.model.database.DatabaseFactory;
import RPGMonstro.model.domain.Ameaca;
import RPGMonstro.model.domain.Criatura;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
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

public class FXMLTabelaEncontroController implements Initializable {
  
    @FXML
    private Spinner<Integer> spinnerTamanhoCriatura;
    @FXML
    private Spinner<Integer> spinnerNivelCriatura;
    @FXML
    private ChoiceBox<Ameaca> choiceBoxAmeacaEncontro;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private ChoiceBox<?> choiceBoxAmeacaEncontro1;
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
    
    List<Ameaca> listAmeacaEncontro = new ArrayList();
    ObservableList<Ameaca> observableListAmeacaEncontro;
    
    private List<Criatura> listNivelCriatura;
    private ObservableList<Criatura> observableListNivelCriatura;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    private final EncontroDAO encontroDAO = new EncontroDAO();
    private final CriaturaDAO criaturaDAO = new CriaturaDAO();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criaturaDAO.setConnection (connection);
        spinnerNivelCriatura.setValueFactory(carregarSpinnerEncontro(1, 20, 1));
        spinnerTamanhoCriatura.setValueFactory(carregarSpinnerEncontro(2, 10, 4));
        carregarComboBoxAmeacaEncontro();
        carregarTableViewNivel();
        
        //selecionarItemTableViewNivel(null);
        
        
        //tableViewNivel.getSelectionModel().selectedItemProperty().addListener(
        //        (observable, oldValue, newValue) -> selecionarItemTableViewNivel(newValue));
        
    }    
    @FXML
    public void carregarTableViewNivel() {
        int tamanhoGrupoEncontro = spinnerTamanhoCriatura.getValue();
        int nivelGrupoEncontro = spinnerNivelCriatura.getValue(); 
        Ameaca ameacaEncontro = choiceBoxAmeacaEncontro.getSelectionModel().getSelectedItem();
        
        int xp = (tamanhoGrupoEncontro * ameacaEncontro.valorXP);
        Integer[] nivelXP = {10, 15, 20, 30, 40, 60, 80, 120, 160};

                //List<Criatura> criaturaPorNivel = new ArrayList<>();
                //criaturaPorNivel = criaturaDAO.ListarCriaturaPorNivel(i);
                //observableListNivelCriatura = FXCollections.observableArrayList(criaturaPorNivel);
                //tableViewNivel.setItems(observableListNivelCriatura);
                //if (criaturaPorNivel != null) {
                //    for (Criatura criatura : criaturaPorNivel) {
                //        System.out.println(criatura.getNome_criatura());
        //testeBotao.getChildren().clear();
        int a = 0;
        for (int i = -3; i < 25; i++) {
            if (i >= nivelGrupoEncontro - 4 && i <= nivelGrupoEncontro + 4) {
                if (xp >= nivelXP[a] && i != -2 && i != -3) {
                    
                    labelNivel.setText(String.valueOf(i));
                    labelXPNivel.setText(String.valueOf(nivelXP[a]));
                    labelCriaturaMaxima.setText(String.valueOf(xp / nivelXP[a]));
                    labelCriaturaRestante.setText("4");
                    labelSaldoXP.setText(String.valueOf(xp));
                    labelGasto.setText("23");
                    labelRestante.setText("123");
                            
                    
                    //Button buttonInserir = new Button("-");
                    //Button buttonRemover = new Button("+");
                    //buttonRemover.setLayoutX(20);
                    //buttonRemover.setLayoutY(50 + ((a + 1)* 50));
                    //buttonRemover.setOnAction()
                    //buttonInserir.setLayoutX(30);
                    //buttonInserir.setLayoutY(50 + ((a + 1) * 50));
                    //buttonInserir.setOnAction()


                    //testeBotao.getChildren().add(buttonInserir);
                    //testeBotao.getChildren().add(buttonRemover);
                }
                a++;     
            }
        }
    }
    
        
    public void carregarComboBoxAmeacaEncontro() {
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
        boolean botaoConfimarClicado = mostrarFXMLTabelaEncontroDialog(c1);
        if (botaoConfimarClicado) {
            listNivelCriatura.add(c1);
            carregarTableViewNivel();
        }
    }

    @FXML
    private void handleButtonRemoverCriatura(ActionEvent event) {
    }
    
    public boolean mostrarFXMLTabelaEncontroDialog(Criatura criatura) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLTabelaEncontroDialogController.class.getResource("/RPGMonstro/view/FXMLTabelaEncontroDialog.fxml"));
        AnchorPane pagina = (AnchorPane) loader.load();
        
        // Cliando um Estágio de Diálogo
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Inserção de Criatura");
        Scene cena = new Scene(pagina);
        dialogStage.setScene(cena);
        
        // Setando o cliente no Controller
        FXMLTabelaEncontroDialogController controlador = loader.getController();
        controlador.setDialogStage(dialogStage);
        //controlador.setCriatura(criatura);
        
        // Mostrar o Dialog e esperar até que o usuário o fecha
        dialogStage.showAndWait();
        return controlador.oBotaofoiClicado();
    }
    
}
     

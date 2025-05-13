package RPGMonstro.Controller;

import RPGMonstro.model.dao.CriaturaDAO;
import RPGMonstro.model.dao.EncontroDAO;
import RPGMonstro.model.dao.NivelDAO;
import RPGMonstro.model.database.Database;
import RPGMonstro.model.database.DatabaseFactory;
import RPGMonstro.model.domain.Ameaca;
import RPGMonstro.model.domain.Criatura;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

public class FXMLTabelaEncontroController implements Initializable {
  
    @FXML
    private Spinner<Integer> spinnerTamanhoCriatura;
    @FXML
    private Spinner<Integer> spinnerNivelCriatura;

    @FXML
    private AnchorPane testeBotao;

    @FXML
    private ChoiceBox<Ameaca> choiceBoxAmeacaEncontro;
    
    private List<Ameaca> listAmeacaEncontro = new ArrayList<>();
    private ObservableList observableListAmeacaEncontro;
    
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
        testeBotao.getChildren().clear();
        int a = 0;
        for (int i = -3; i < 25; i++) {
            if (i >= nivelGrupoEncontro - 4 && i <= nivelGrupoEncontro + 4) {
                if (xp >= nivelXP[a] && i != -2 && i != -3) {


                    Label labelXP_Encontro = new Label(''+ xp);
                    Label labelMaxCriaturas_Encontro = new Label(''+ int(xp / i));
                    Label labelCriaturaRestantes_Encontro = new Label(''+ int(xp / i));

                    Button buttonInserir = new Button("-");
                    Button buttonRemover = new Button("+");
                    buttonRemover.setLayoutX(20);
                    buttonRemover.setLayoutY(50 + ((a + 1)* 50));
                    buttonRemover.setOnAction()
                    buttonInserir.setLayoutX(30);
                    buttonInserir.setLayoutY(50 + ((a + 1) * 50))
                    buttonInserir.setOnAction()


                    testeBotao.getChildren().add(buttonInserir);
                    testeBotao.getChildren().add(buttonRemover);
                } 
                a++;     
            }
        }
    }
    
    
    public void carregarComboBoxAmeacaEncontro() {
        Collections.addAll(listAmeacaEncontro, Ameaca.values());
        observableListAmeacaEncontro = FXCollections.observableArrayList(listAmeacaEncontro);
        choiceBoxAmeacaEncontro.setItems(observableListAmeacaEncontro); 
    }
    
    public SpinnerValueFactory<Integer> carregarSpinnerEncontro(int a, int b, int c) {
        SpinnerValueFactory<Integer> valueFactoryNivelEncontro = new SpinnerValueFactory.IntegerSpinnerValueFactory(a, b);
        valueFactoryNivelEncontro.setValue(c);
        return valueFactoryNivelEncontro;
    }
     
}

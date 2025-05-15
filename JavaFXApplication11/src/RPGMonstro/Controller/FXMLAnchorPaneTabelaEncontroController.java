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
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FXMLAnchorPaneTabelaEncontroController implements Initializable {
  
    @FXML
    private ChoiceBox<Criatura> choiceBoxCriatura;
    @FXML
    private ChoiceBox<Ameaca> choiceBoxAmeacaEncontro;
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
    @FXML
    private Button buttonInserirEncontro;
    @FXML
    private Button buttonProximo;
    @FXML
    private AnchorPane anchoPaneEncontro;
    
    HashMap<Criatura, Integer> criaturaQuantidade = new HashMap<>();
    HashMap<Integer, Integer> xpPorNivel = new HashMap<>();
    
    private ObservableList<Ameaca> observableListAmeacaEncontro;
    
    
    List<Criatura> listCriaturaPorNivel = new ArrayList();
    List<Criatura> listCriaturaEncontro = new ArrayList();
    ObservableList<Criatura> observableListCriaturaEncontro;
    private int indiceNivelAtual = 0;
    private int xpGasto = 0;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final EncontroDAO encontroDAO = new EncontroDAO();
    private final CriaturaDAO criaturaDAO = new CriaturaDAO();
    private final Criatura_EncontroDAO criatura_encontroDAO = new Criatura_EncontroDAO();
    
    private final List<Ameaca> listAmeacaEncontro = new ArrayList();
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Integer[] nivelXP = {10, 15, 20, 30, 40, 60, 80, 120, 160};
        for (int i = -4; i < 5; i++) {
            xpPorNivel.put(i, nivelXP[i + 4]);
        } 
        criaturaDAO.setConnection (connection);
        spinnerNivelCriatura.setValueFactory(carregarSpinnerEncontro(1, 20, 1));
        spinnerTamanhoCriatura.setValueFactory(carregarSpinnerEncontro(2, 10, 4));
        carregarChoiceBoxAmeacaEncontro();
        carregarCriadorDesignEncontro();   
    }    
    
    @FXML
    public void carregarCriadorDesignEncontro() {
        int tamanhoGrupoEncontro = spinnerTamanhoCriatura.getValue();
        int nivelGrupoEncontro = spinnerNivelCriatura.getValue(); 
        Ameaca ameacaEncontro = choiceBoxAmeacaEncontro.getSelectionModel().getSelectedItem();
        int xp = (tamanhoGrupoEncontro * ameacaEncontro.valorXP);
        int difNivel = -4;
        indiceNivelAtual = nivelGrupoEncontro - 4;
        
        if (indiceNivelAtual < -1) {
            difNivel = -1 * indiceNivelAtual - 5;
            indiceNivelAtual = -1; 
        }        
        int tamanhoListaCriatura = listCriaturaPorNivel.size();
        int criaturaMax = xp / xpPorNivel.get(difNivel);
        labelNivel.setText(String.valueOf(indiceNivelAtual));
        labelXPNivel.setText(String.valueOf(xpPorNivel.get(difNivel)));
        labelCriaturaMaxima.setText(String.valueOf(criaturaMax));
        labelCriaturaRestante.setText(String.valueOf(criaturaMax - tamanhoListaCriatura));                    
        labelSaldoXP.setText(String.valueOf(xp));
        labelGasto.setText(String.valueOf(xpGasto));
        labelRestante.setText(String.valueOf(xp - xpGasto));
    
    }
    
    @FXML 
    private void handleButtonConfimarProximoNivel() throws IOException {
        listCriaturaPorNivel.clear();
        int nivelGrupoEncontro = spinnerNivelCriatura.getValue();         
        int xp = Integer.valueOf(labelSaldoXP.getText()) - xpGasto;
        
        int proxNivel = indiceNivelAtual + 1;
        
        if (indiceNivelAtual < nivelGrupoEncontro + 4 && xp >= xpPorNivel.get(proxNivel - nivelGrupoEncontro)) {
            indiceNivelAtual = proxNivel;
            
            int difNivel = indiceNivelAtual - nivelGrupoEncontro;
            if ((nivelGrupoEncontro + difNivel) < -1) {
                difNivel = -1 * (nivelGrupoEncontro - 4) - 5;
                
            }    
            int criaturaMax =  (xp) / xpPorNivel.get(difNivel);
            labelRestante.setText(String.valueOf(xp));
            labelNivel.setText(String.valueOf(indiceNivelAtual));
            labelXPNivel.setText(String.valueOf(xpPorNivel.get(difNivel)));
            labelCriaturaMaxima.setText(String.valueOf(criaturaMax));
            labelCriaturaRestante.setText(String.valueOf(criaturaMax - listCriaturaPorNivel.size()));                    
            labelGasto.setText(String.valueOf(xpGasto));
            
        }
 
    }
    
    @FXML
    public void carregarCriador() {
        listCriaturaPorNivel.clear();
        int nivelGrupoEncontro = spinnerNivelCriatura.getValue();         
        int xp = Integer.valueOf(labelSaldoXP.getText()) - xpGasto;
        int difNivel = indiceNivelAtual - nivelGrupoEncontro;
            if ((nivelGrupoEncontro + difNivel) < -1) {
                difNivel = -1 * (nivelGrupoEncontro - 4) - 5;
                
            }    
            int criaturaMax =  (xp) / xpPorNivel.get(difNivel);
            labelRestante.setText(String.valueOf(xp));
            
            labelNivel.setText(String.valueOf(indiceNivelAtual));
            labelXPNivel.setText(String.valueOf(xpPorNivel.get(difNivel)));
            labelCriaturaMaxima.setText(String.valueOf(criaturaMax));
            labelCriaturaRestante.setText(String.valueOf(criaturaMax - listCriaturaPorNivel.size()));                    
            labelGasto.setText(String.valueOf(xpGasto));
        
    }
    

    public void carregarChoiceBoxCriaturasPorNivel() {
        observableListCriaturaEncontro = FXCollections.observableArrayList(listCriaturaEncontro);
        choiceBoxCriatura.setItems(observableListCriaturaEncontro);
        choiceBoxCriatura.setConverter(new StringConverter<Criatura>() {
            @Override
            public String toString(Criatura criatura) {
                return "Nivel: " + criatura.getNivel_criatura() + " - " + criatura.getNome_criatura() + " - Quantidade: " + criaturaQuantidade.get(criatura);
            }

            @Override
            public Criatura fromString(String string) {
               return null;
            }
        });
    }
        
    public void carregarChoiceBoxAmeacaEncontro() {
        Collections.addAll(listAmeacaEncontro, Ameaca.values());
        observableListAmeacaEncontro = FXCollections.observableArrayList(listAmeacaEncontro);
        choiceBoxAmeacaEncontro.setItems(observableListAmeacaEncontro);
        choiceBoxAmeacaEncontro.setValue(Ameaca.Moderada);
        choiceBoxAmeacaEncontro.setOnAction(event -> carregarCriadorDesignEncontro());
    }
    public SpinnerValueFactory<Integer> carregarSpinnerEncontro(int a, int b, int c) {
        SpinnerValueFactory<Integer> valueFactoryNivelEncontro = new SpinnerValueFactory.IntegerSpinnerValueFactory(a, b);
        valueFactoryNivelEncontro.setValue(c);
        return valueFactoryNivelEncontro;
    }

    
    
    @FXML
    private void handleButtonInserirCriatura() throws IOException {
        Criatura c1 = new Criatura();
        if (Integer.valueOf(labelRestante.getText()) >= Integer.valueOf(labelXPNivel.getText())) {
            boolean botaoConfimarClicado = mostrarFXMLAnchorPaneTabelaEncontroDialog(c1);
            if (botaoConfimarClicado) {   
                    listCriaturaPorNivel.add(c1);
                    listCriaturaEncontro.add(c1);
                    carregarChoiceBoxCriaturasPorNivel();
                    carregarCriador();
            }     
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Não e mais Possível colocar Criaturas");
            alert.show();
        }
        
        
    }

    @FXML
    private void handleButtonRemoverCriatura() {
        Criatura c1 = choiceBoxCriatura.getSelectionModel().getSelectedItem();
        if (c1 != null) {
            listCriaturaEncontro.remove(c1);
            int dif = c1.getNivel_criatura() - spinnerNivelCriatura.getValue();
            xpGasto -= xpPorNivel.get(dif) * criaturaQuantidade.get(c1);
            carregarChoiceBoxCriaturasPorNivel();
            carregarCriador();
        }
    }
    
    
    
    @FXML
    private void handlebuttonInserirEncontro() throws IOException {
        Encontro encontro = new Encontro();
        try {
            connection.setAutoCommit(false);
            encontroDAO.setConnection(connection);
            criatura_encontroDAO.setConnection(connection);
            if (validarEntradaDadosEncontro()) {
                encontro.setAmeaca_encontro(String.valueOf(choiceBoxAmeacaEncontro.getSelectionModel().getSelectedItem()));
                encontro.setSaldo_XP_encontro(Integer.valueOf(labelSaldoXP.getText()));
                encontro.setGasto_XP_encontro(xpGasto);
                encontro.setNivel_grupo_encontro(spinnerNivelCriatura.getValue());
                encontro.setTamanho_grupo_encontro(spinnerTamanhoCriatura.getValue());
                encontroDAO.inserir(encontro);
            } else {
                connection.rollback();
            } 
            for (Criatura criatura :listCriaturaEncontro) {
                Criatura_Encontro ce = new Criatura_Encontro();
                ce.setCd_criatura_CE(criatura.getCd_criatura());
                ce.setCd_encontro_CE(encontro.getCd_encontro());
                ce.setQuant(criaturaQuantidade.get(criatura));
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
    
    
    private boolean validarEntradaDadosEncontro() {
        String mensagemErro = "";
        if (labelSaldoXP.getText() == null 
                || Integer.valueOf(labelSaldoXP.getText()) <= 0) {
            mensagemErro += "Saldo Invalido";
        }
        
        if (choiceBoxAmeacaEncontro.getSelectionModel().getSelectedItem() == null) {
            mensagemErro += "Ameaça não Escolhida";
        }

        if (xpGasto <= 0) {
            mensagemErro += "Gasto de Xp Invalido";
        }
        
        if (spinnerNivelCriatura.getValue() == null) {
            mensagemErro += "nivel não escolhido";
        }
            
        if (spinnerTamanhoCriatura.getValue() == null) {
            mensagemErro += "Tamanho não escolhido";
        }
        
        if (listCriaturaEncontro.isEmpty()) {
            mensagemErro += "Nenhuma criatura selecionada para o Encontro";
            
        } else {
            mensagemErro = listCriaturaEncontro.stream().filter((criatura) -> 
                    (criaturaQuantidade.get(criatura) <= 0)).map((_item) -> 
                            "Quantidade não Selecionado").reduce(mensagemErro, String::concat);
        }

        if (mensagemErro.length() == 0) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Erro no Cadastro");
            al.setHeaderText("Campos Inválidos, por favor, corrija..." );
            al.setContentText(mensagemErro);
            al.show();
            return false;
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
        if (controlador.oBotaofoiClicado()) {
            criaturaQuantidade.put(criatura, controlador.getQuantidade());
            xpGasto = xpGasto + Integer.valueOf(labelXPNivel.getText()) * controlador.getQuantidade();
        }
        return controlador.oBotaofoiClicado();
    }
}
     

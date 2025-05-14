package RPGMonstro.Controller;

import RPGMonstro.model.domain.Criatura;
import RPGMonstro.model.domain.Raridade;
import RPGMonstro.model.domain.Tamanho;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FXMLAnchorPaneCadastroCriaturaDialogController implements Initializable {

    @FXML
    private TextField textFieldNomeCriatura;
    @FXML
    private TextField textFieldPVCriatura;
    @FXML
    private TextField textFieldCACriatura;
    @FXML
    private TextField textFieldDeslocamentoCriatura;
    @FXML
    private TextField textFieldSentidoCriatura;
    @FXML
    private ChoiceBox<Tamanho> ChoiceBoxTamanhoCriatura;
    @FXML
    private ChoiceBox<Raridade> ChoiceBoxRaridadeCriatura;
    @FXML
    private Spinner<Integer> spinnerNivelCriatura;
    @FXML
    private Button buttonConfimar;
    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private boolean botaoConfimarClicado = false;
    private Criatura criatura;
    
    private List<Tamanho> listTamanho = new ArrayList();
    private ObservableList<Tamanho> observableListTamanho;
    
    private List<Raridade> listRaridade = new ArrayList();
    private ObservableList<Raridade> observableListRaridade;
   
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Stage getStage() {
        return dialogStage;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setCriatura(Criatura criatura) {
        this.criatura = criatura;
        this.textFieldNomeCriatura.setText(criatura.getNome_criatura());
        this.textFieldSentidoCriatura.setText(criatura.getSentido_criatura());
        this.textFieldDeslocamentoCriatura.setText(criatura.getDeslocamento_criatura());
        this.textFieldCACriatura.setText(String.valueOf(criatura.getClasse_armadura_criatura()));
        this.textFieldPVCriatura.setText(String.valueOf(criatura.getPts_vida_criatura()));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(-1, 25);
        valueFactory.setValue(-1);
        this.spinnerNivelCriatura.setValueFactory(valueFactory);
        this.ChoiceBoxTamanhoCriatura.setItems(carregarComboBoxTamanho());
        this.ChoiceBoxRaridadeCriatura.setItems(carregarComboBoxRaridade());
    }
    
    public boolean oBotaofoiClicado() {
        return botaoConfimarClicado;
    }

    public void handleButtonConfimar() {
        if (validarEntradaDados()) {
            criatura.setNome_criatura(textFieldNomeCriatura.getText());
            criatura.setTamanho_criatura(String.valueOf(ChoiceBoxTamanhoCriatura.getSelectionModel().getSelectedItem()));
            criatura.setRaridade_criatura(String.valueOf(ChoiceBoxRaridadeCriatura.getSelectionModel().getSelectedItem()));
            criatura.setSentido_criatura(textFieldSentidoCriatura.getText());
            criatura.setNivel_criatura(spinnerNivelCriatura.getValue());
            criatura.setPts_vida_criatura(Integer.parseInt(textFieldPVCriatura.getText()));
            criatura.setClasse_armadura_criatura(Integer.parseInt(textFieldCACriatura.getText()));
            criatura.setDeslocamento_criatura(textFieldDeslocamentoCriatura.getText());
            botaoConfimarClicado = true;
            dialogStage.close();
        }
    }
       
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    public ObservableList<Tamanho> carregarComboBoxTamanho() {
        Collections.addAll(listTamanho, Tamanho.values());
        observableListTamanho = FXCollections.observableArrayList(listTamanho);
        
       return observableListTamanho;
    }
        
    
    public ObservableList<Raridade> carregarComboBoxRaridade() {
        Collections.addAll(listRaridade, Raridade.values());
        observableListRaridade = FXCollections.observableArrayList(listRaridade);
        
       return observableListRaridade;
    }
    
    private boolean validarEntradaDados() {
        String mensagemErro = "";
        if (textFieldNomeCriatura.getText() == null 
                || textFieldNomeCriatura.getText().length() == 0) {
            mensagemErro += "nome invalido";
        }

        if (textFieldSentidoCriatura.getText() == null 
                || textFieldSentidoCriatura.getText().length() == 0) {
            mensagemErro += "cpf invalido";
        }


        if (textFieldDeslocamentoCriatura.getText() == null 
                || textFieldDeslocamentoCriatura.getText().length() == 0) {
            mensagemErro += "telefone invalido";
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
}

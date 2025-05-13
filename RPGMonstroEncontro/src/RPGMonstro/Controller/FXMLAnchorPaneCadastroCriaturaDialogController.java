/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.Controller;

import RPGMonstro.model.domain.Criatura;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import RPGMonstro.model.domain.Raridade;
import RPGMonstro.model.domain.Tamanho;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author PC
 */
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
    private Spinner<Integer> spinnerNivelCriatura;
    @FXML
    private ChoiceBox<Tamanho> ChoiceBoxTamanhoCriatura;
    @FXML
    private ChoiceBox<Raridade> ChoiceBoxRaridadeCriatura;
   
    @FXML
    private Button buttonConfimar;
    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private boolean botaoConfimarClicado = false;
    private Criatura criatura;
    
    
    List<Tamanho> listTamanho = new ArrayList();
    ObservableList<Tamanho> observableListTamanho;
    
    List<Raridade> listRaridade = new ArrayList();
    ObservableList<Raridade> observableListRaridade;
   
    /**
     * Initializes the controller class.
     */
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

 
    public void handleButtonCancelar() {
        dialogStage.close();
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

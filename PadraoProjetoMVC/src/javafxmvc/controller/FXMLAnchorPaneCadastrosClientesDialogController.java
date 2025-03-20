/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Cliente;


public class FXMLAnchorPaneCadastrosClientesDialogController implements Initializable {
    
    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteCPF;
    @FXML
    private Label labelClienteTelefone;
    
    @FXML
    private TextField textFieldClienteNome;
    @FXML
    private TextField textFieldClienteCPF;
    @FXML
    private TextField textFieldClienteTelefone;

    @FXML
    private Button buttonConfimar;
    @FXML
    private Button buttonCancelar;
    
    private Stage dialogStage;
    private boolean botaoConfimarClicado = false;
    private Cliente cliente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public Stage getDialogStage() {
        return dialogStage;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.textFieldClienteNome.setText(cliente.getNome());
        this.textFieldClienteCPF.setText(cliente.getCpf());
        this.textFieldClienteTelefone.setText(cliente.getTelefone());
    }
    
    public boolean oBotaofoiClicado() {
        return botaoConfimarClicado;
    }
    
    @FXML 
    public void handleButtonConfimar() {
        if (validarEntradaDados()) {
            cliente.setNome(textFieldClienteNome.getText());
            cliente.setCpf(textFieldClienteCPF.getText());
            cliente.setTelefone(textFieldClienteTelefone.getText());
            
            botaoConfimarClicado = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }
    
    private boolean validarEntradaDados() {
        String mensagemErro = "";
        if (textFieldClienteNome.getText() == null 
                || textFieldClienteNome.getText().length() == 0) {
            mensagemErro += "nome invalido";
        }
        
        if (textFieldClienteCPF.getText() == null 
                || textFieldClienteNome.getText().length() == 0) {
            mensagemErro += "cpf invalido";
        }
        
        
        if (textFieldClienteTelefone.getText() == null 
                || textFieldClienteTelefone.getText().length() == 0) {
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

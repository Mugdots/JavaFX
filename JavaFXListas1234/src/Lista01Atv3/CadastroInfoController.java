/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista01Atv3;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 20231SI008
 */
public class CadastroInfoController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private TextField textnome;
    @FXML
    private TextField textidade;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clicarBotaoAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Cliente");
        alert.setHeaderText("Informações sobre o Cadastro");
        alert.setContentText("Cliente " + textnome.getText() + " foi cadastrado com sucesso!");
        alert.show();
        
    }
    
    
    
}

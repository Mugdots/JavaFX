/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista01Atv2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author 20231SI008
 */
public class FXMLButtonAlertController implements Initializable {
    
    
    @FXML
    private Button button;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void clicarBotaoMostrarAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Este é o título do Alerta");
        alert.setHeaderText("Mensagem");
        alert.setContentText("Você clicou no botão!");
        alert.show();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista01Atv1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author 20231si008
 */
public class FXMLButtonLabelController implements Initializable {
    
    @FXML
    private Label label;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        label.setText("Clicou no Botão!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        label.setText("Quando o botão for clicado este texto mudará!");
    }    
    
}

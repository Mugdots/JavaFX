/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLBoxMainController implements Initializable {

    @FXML
    private MenuItem menuItemCadastroTracos;
    @FXML
    private MenuItem menuItemCadastroCriaturas;
    @FXML
    private MenuItem menuItemProcessosCriadorEncontros;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void handleMenuItemCadastrosCriatura() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/RPGMonstro/view/FXMLAnchorPaneCadastroCriatura.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemProcessoTabelaEncontro() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/RPGMonstro/view/FXMLTabelaEncontro.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
}
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista03Atv1;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


public class FXMLController implements Initializable {

    @FXML
    private MenuItem mICadastrosCliente;
    @FXML
    private MenuItem mIRelatorioVendaspMes;    
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void carregarFXMLBoxCadastroClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLAnchorPaneCadastrosClientes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void carregarFXMLAnchorPaneRelatorioVendaspMes() throws IOException {
       AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLAnchorPaneRelatoriosVendasPorMes.fxml"));
       anchorPane.getChildren().setAll(a);
    }
    
    
}

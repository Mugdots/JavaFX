package RPGMonstro.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;


public class FXMLBoxMainController implements Initializable {

    @FXML
    private MenuItem menuItemCadastroTracos;
    @FXML
    private MenuItem menuItemProcessosCriadorEncontros;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuItem menuItemGraficoEncontrosPorNivelCriatura;

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
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/RPGMonstro/view/FXMLAnchorPaneProcessoEncontro.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemGraficoEncontrosPorNivelCriatura() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/RPGMonstro/view/FXMLAnchorPaneGraficosEncontrosPorNivelCriatura.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
}
    

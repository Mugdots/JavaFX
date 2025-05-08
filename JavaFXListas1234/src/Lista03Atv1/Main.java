
package Lista03Atv1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    VBox mainPane = (VBox) FXMLLoader.load(getClass().getResource("FXMLBoxMain.fxml"));
    Scene scene = new Scene(mainPane);
    stage.setTitle("Sistema de Vendas - Múltiplos Formulários");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
    }
    public static void main(String[] args) {
    launch(args);
   }
}
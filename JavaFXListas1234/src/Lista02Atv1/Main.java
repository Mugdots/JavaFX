package Lista02Atv1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 20231si008
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent raiz = FXMLLoader.load(getClass().getResource("FXMLComboBox.fxml"));
        Scene cena = new Scene(raiz);
        stage.setScene(cena);
        stage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

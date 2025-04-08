/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulado;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author 20231si008
 */
public class Simulado extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent raiz = FXMLLoader.load(getClass().getResource("FXMLListViewTableView.fxml"));
        Scene cena = new Scene(raiz);
        stage.setScene(cena);
        stage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
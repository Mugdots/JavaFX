package javafxmvc.view;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author 20231si008
 */
public class PadraoProjetoMVC extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("AAAA");
        Parent root = FXMLLoader.load(getClass().getResource("FXMLVBoxMain.fxml"));
        System.out.println("BBBB");
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Sistemas de Vendas (Java MVC)");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

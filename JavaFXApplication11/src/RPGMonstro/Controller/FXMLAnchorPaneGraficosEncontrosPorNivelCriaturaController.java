/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RPGMonstro.Controller;

import RPGMonstro.model.dao.EncontroDAO;
import RPGMonstro.model.database.Database;
import RPGMonstro.model.database.DatabaseFactory;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class FXMLAnchorPaneGraficosEncontrosPorNivelCriaturaController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChartEncontros;
    @FXML
    private NumberAxis numberAxisEncontros;
    @FXML
    private CategoryAxis CategoryAxisNivelCriatura;
    
    
    private ObservableList<String> observableListNiveis = FXCollections.observableArrayList();
    
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection conexao = database.conectar();
    private final EncontroDAO encontroDAO = new EncontroDAO();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] vetorNiveis = {"-1","0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25"};
        observableListNiveis.addAll(Arrays.asList(vetorNiveis));
        CategoryAxisNivelCriatura.setCategories(observableListNiveis);
        
        encontroDAO.setConnection(conexao);
        
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Quantidade por Nível");

        for (String nivel : vetorNiveis) {
            Integer quantidade = encontroDAO.listarEncontroPorNumeroNivel(Integer.valueOf(nivel));
            series.getData().add(new XYChart.Data<>(nivel, quantidade));
        }
        barChartEncontros.getData().add(series);        
    }
}
  
    
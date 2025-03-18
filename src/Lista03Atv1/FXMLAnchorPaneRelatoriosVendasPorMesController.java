/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista03Atv1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLAnchorPaneRelatoriosVendasPorMesController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis xAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("2016");
        series.getData().add(new XYChart.Data<>("Janeiro", 20));
        series.getData().add(new XYChart.Data<>("Fevereiro", 40));
        series.getData().add(new XYChart.Data<>("Março", 60));
        barChart.getData().add(series);
    }

}
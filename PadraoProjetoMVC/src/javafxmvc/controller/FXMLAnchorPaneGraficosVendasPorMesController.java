/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafxmvc.model.dao.VendaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;

/**
 * FXML Controller class
 *
 * @author 20231SI008
 */
public class FXMLAnchorPaneGraficosVendasPorMesController implements Initializable {

    
    @FXML
    private NumberAxis NumberAxisValor;
    @FXML
    private CategoryAxis CategoryAxisMes;
    @FXML
    private BarChart<String, Integer> BarChartVendas;
    
    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
    
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection conexao = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String[] vetorMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        observableListMeses.addAll(Arrays.asList(vetorMeses));
        CategoryAxisMes.setCategories(observableListMeses);
        
        vendaDAO.setConnection(conexao);
        Map<Integer, ArrayList> dados = vendaDAO.listarQuantidadeVendasPorMes();
        for (Map.Entry<Integer, ArrayList> dadosItens : dados.entrySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series();
            series.setName(dadosItens.getKey().toString());
            for (int i = 0; i < dadosItens.getValue().size(); i = i + 2) {
                String mes;
                Integer quantidade;
                mes = retornarNomeMes((int) dadosItens.getValue().get(i));
                quantidade = (Integer) dadosItens.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
        BarChartVendas.getData().add(series);
        }
    
    }    
    
    public String retornarNomeMes(int mes) {
        switch(mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "";
            
        }
    }
    
}

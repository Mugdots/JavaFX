/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista02Atv1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.util.List;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLComboBoxController implements Initializable {
    
    @FXML
    private ComboBox<Cliente> comboBox;
            
    @FXML
    private Button botao;
            
    
    private List<Cliente> listaCliente = new ArrayList<>();
    
    private ObservableList<Cliente> observableListClientes;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarComboBoxClientes();
    }    
    
    public void carregarComboBoxClientes() {
        
        Cliente c1 = new Cliente("Daniel", "3221-3213");
        Cliente c2 = new Cliente("João", "321321-12");
        
        listaCliente.add(c1);
        listaCliente.add(c2);
        
        observableListClientes = FXCollections.observableArrayList(listaCliente);
        comboBox.setItems(observableListClientes);
    }
    
    public void selecionarItemComboBoxClientes() {
        Cliente cliente = comboBox.getSelectionModel().getSelectedItem();
        System.out.println("Cliente selecionado: " + cliente.getNome());
    }
    
    public void removerItemComboBoxClientes() {
        Cliente cliente = comboBox.getSelectionModel().getSelectedItem();
        System.out.println("Cliente removido: " + cliente.getNome());
        comboBox.getItems().remove(cliente);
    }
    
}

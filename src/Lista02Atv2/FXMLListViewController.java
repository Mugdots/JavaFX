/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista02Atv2;

import Lista02Atv1.Cliente;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLListViewController implements Initializable {
    
    @FXML
    private ListView<Cliente> listViewClientes;
    
    @FXML
    private ObservableList<Cliente> observableClientes;
    
    @FXML
    private Button removerItemButton;

    private List<Cliente> listaCliente = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarListViewClientes();
    }    
    
    @FXML
    public void carregarListViewClientes() {
        Cliente c1 = new Cliente("Daniel", "321321-3213");
        Cliente c2 = new Cliente("João", "31321-2131");
        
        listaCliente.add(c1);
        listaCliente.add(c2);
        
        observableClientes = FXCollections.observableArrayList(listaCliente);
        listViewClientes.setItems(observableClientes);
    }
    @FXML
    private void selecionarItemListViewClientes() {
        Cliente cliente = listViewClientes.getSelectionModel().getSelectedItem();
        System.out.println("Clientes Selecionados: " + cliente.getNome());
   
    }
    @FXML
    private void removerItemListClientes() {
        Cliente cliente = listViewClientes.getSelectionModel().getSelectedItem();
        System.out.println("Clientes Removido: " + cliente.getNome());
        listViewClientes.getItems().remove(cliente);
    }
    
}

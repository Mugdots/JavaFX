/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ItemDeVenda;
import javafxmvc.model.domain.Produto;
import javafxmvc.model.domain.Venda;

/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLAnchorPaneProcessosVendasDialogController implements Initializable {

    @FXML
    private TextField textFieldVendaValor;
    @FXML
    private DatePicker datePickerVendaData;
    @FXML
    private CheckBox checkBoxVendaPago;
    @FXML
    private ComboBox comboBoxVendaCliente;
    @FXML
    private TableView<ItemDeVenda> tableViewItensDeVenda;
    @FXML
    private TableColumn<ItemDeVenda,Produto> tableColumnItemDeVendaProduto;
    @FXML
    private TableColumn<ItemDeVenda, Integer> tableColumnItemDeVendaQuantidade;
    @FXML
    private TableColumn<ItemDeVenda, Double> tableColumnItemDeVendaValor;
    @FXML
    private ComboBox comboBoxVendaProduto;
    @FXML
    private TextField textFieldVendaItemDeVendaQuantidade;
    @FXML
    private Button buttonAdicionar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonConfirmar;

    private List<Cliente> listClientes;
    private List<Produto> listProdutos;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<Produto> observableListProdutos;
    private ObservableList<ItemDeVenda> observableListItensDeVenda;
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
     
    private Stage dialogStage;
    private boolean botaoConfimarClicado = false;
    private Venda venda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        produtoDAO.setConnection(connection);
        carregarComboBoxCliente();
        carregarComboBoxProduto();
        tableColumnItemDeVendaProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tableColumnItemDeVendaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnItemDeVendaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }    
    
    public void carregarComboBoxCliente() {
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxVendaCliente.setItems(observableListClientes);
    
    }
            
    public void carregarComboBoxProduto() {
        listProdutos = produtoDAO.listar();
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        comboBoxVendaProduto.setItems(observableListProdutos);
    }
    
    public Stage getDialogStage() {
        return dialogStage;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    public Venda getVenda() {
        return this.venda;
    }
    
    public void setVenda(Venda venda) {
        this.venda = venda;
        
        comboBoxVendaCliente.getSelectionModel().select(venda.getCliente());
        datePickerVendaData.setValue(venda.getData());
        checkBoxVendaPago.setSelected(venda.getPago());
        observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
        tableViewItensDeVenda.setItems(observableListItensDeVenda);
        textFieldVendaValor.setText(String.format("%.2f", venda.getValor()));
    }
 
    public boolean oBotaofoiClicado() {
        return botaoConfimarClicado;
    }
    
    @FXML
    public void handleButtonAdicionar() {
        Produto produto;
        ItemDeVenda itemDeVenda = new ItemDeVenda();
        if (comboBoxVendaProduto.getSelectionModel().getSelectedItem() != null) {
            produto = (Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem();
            
            if (produto.getQuantidade() >= Integer.parseInt(textFieldVendaItemDeVendaQuantidade.getText())) {
                itemDeVenda.setProduto((Produto) comboBoxVendaProduto.getSelectionModel().getSelectedItem());
                itemDeVenda.setQuantidade(Integer.parseInt(textFieldVendaItemDeVendaQuantidade.getText()));
                itemDeVenda.setValor(itemDeVenda.getProduto().getPreco() * itemDeVenda.getQuantidade());
                
                venda.getItensDeVenda().add(itemDeVenda);
                venda.setValor(venda.getValor() + itemDeVenda.getValor());
                
                observableListItensDeVenda = FXCollections.observableArrayList(venda.getItensDeVenda());
                tableViewItensDeVenda.setItems(observableListItensDeVenda);
                textFieldVendaValor.setText(String.format("%.2f", venda.getValor()));
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Problemas na escolha do Produto");
                alerta.setContentText("Não existe a quantidade de produtos disponíveis no estoque!");
                alerta.show();
            }  
        }
    }
    
    @FXML 
    public void handleButtonConfimar() {
        if (validarEntradaDeDados()) {
            venda.setCliente((Cliente)comboBoxVendaCliente.getSelectionModel().getSelectedItem());
            venda.setPago(checkBoxVendaPago.isSelected());
            venda.setData(datePickerVendaData.getValue());
            
            botaoConfimarClicado = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }
    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (comboBoxVendaCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente inválido!\n";
        }
        if (datePickerVendaData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (observableListItensDeVenda == null) {
            errorMessage += "Itens de Venda inválidos!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no Cadastro");
            alert.setHeaderText("Campos Inválidos, por favor, corrija");
            alert.show();
            return false;
        }
    }
}

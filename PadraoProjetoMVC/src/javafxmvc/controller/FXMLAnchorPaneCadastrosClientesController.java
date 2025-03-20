/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;

/**
 * FXML Controller class
 *
 * @author 20231si008
 */
public class FXMLAnchorPaneCadastrosClientesController implements Initializable {

    @FXML
    private TableView<Cliente> tableViewCliente;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteCPF;
    @FXML
    private Label labelClienteCodigo;
    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteCPF;
    @FXML
    private Label labelClienteTelefone;
    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonInserir;

    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection (connection);

        carregarTableViewClientes();
        // Limpando a exibição dos detalhes do cliente
        selecionarItemTableViewClientes(null);
        // Listener acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewCliente.getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));
    }
    
    public void carregarTableViewClientes() {
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        listClientes = clienteDAO.listar();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableViewCliente.setItems(observableListClientes);
    }
    
    public void selecionarItemTableViewClientes(Cliente cliente) {
        if (cliente != null) {
            labelClienteCodigo.setText(String.valueOf(cliente.getCdCliente()));
            labelClienteNome.setText(cliente.getNome());
            labelClienteCPF.setText(cliente.getCpf());
            labelClienteTelefone.setText(cliente.getTelefone());
        } else {
            labelClienteCodigo.setText("");
            labelClienteNome.setText("");
            labelClienteCPF.setText("");
            labelClienteTelefone.setText("");
        }
    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Cliente c1 = new Cliente();
        boolean botaoConfimarClicado = mostrarFXMLAnchorPaneCadastrosClientesDialog(c1);
        if (botaoConfimarClicado) {
            clienteDAO.inserir(c1);
            carregarTableViewClientes();
            
        }
    }
    
    @FXML
    public void handleButtonAlterar() throws IOException {
        Cliente c1 = tableViewCliente.getSelectionModel().getSelectedItem();
        if (c1 != null) {
            boolean botaoConfimarClicado = mostrarFXMLAnchorPaneCadastrosClientesDialog(c1);
            if (botaoConfimarClicado) {
                clienteDAO.alterar(c1);
                carregarTableViewClientes();
            } 
        } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Por favor, escolha um cliente na Tabela");
                al.show();
            }
    }
    
    @FXML
    public void handleButtonRemover() throws IOException {
        Cliente c1 = tableViewCliente.getSelectionModel().getSelectedItem();
        if (c1 != null) {
            clienteDAO.remover(c1);
            carregarTableViewClientes();
        } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setContentText("Por favor, escolha um cliente na Tabela");
                al.show();
        }
    }
    
    public boolean mostrarFXMLAnchorPaneCadastrosClientesDialog(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosClientesDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosClientesDialog.fxml"));
        AnchorPane pagina = (AnchorPane) loader.load();
        
        // Cliando um Estágio de Diálogo
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Clientes");
        Scene cena = new Scene(pagina);
        dialogStage.setScene(cena);
        
        // Setando o cliente no Controller
        FXMLAnchorPaneCadastrosClientesDialogController controlador = loader.getController();
        controlador.setDialogStage(dialogStage);
        controlador.setCliente(cliente);
        
        // Mostrar o Dialog e esperar até que o usuário o fecha
        dialogStage.showAndWait();
        return controlador.oBotaofoiClicado();
    }
    
    
    
    
} 
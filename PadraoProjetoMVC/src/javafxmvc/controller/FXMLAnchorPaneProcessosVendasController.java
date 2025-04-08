package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
import javafxmvc.model.dao.ItemDeVendaDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.dao.VendaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ItemDeVenda;
import javafxmvc.model.domain.Produto;
import javafxmvc.model.domain.Venda;
import java.util.logging.Logger;

public class FXMLAnchorPaneProcessosVendasController implements Initializable {

    @FXML
    private TableView<Venda> tableViewVendas;
    @FXML
    private TableColumn<Venda, Integer> tableColumnVendaCodigo;
    @FXML
    private TableColumn<Venda, Date> tableColumnVendaData;
    @FXML
    private TableColumn<Venda, Cliente> tableColumnVendaCliente;
    @FXML
    private Label labelVendaCodigo;
    @FXML
    private Label labelVendaData;
    @FXML
    private Label labelVendaValor;
    @FXML
    private Label labelVendaPago;
    @FXML
    private Label labelVendaCliente;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private Button buttonAlterar;

    private List<Venda> listVendas;
    private ObservableList<Venda> observableListVendas;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();   
    // Atributos para manipulação no banco de dados
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final ItemDeVendaDAO itemDVDAO = new ItemDeVendaDAO();
    

    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection (connection);

        carregarTableViewVendas();
        // Limpando a exibição dos detalhes de vendas
        selecionarItemTableViewVendas(null);
        // Listener acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewVendas.getSelectionModel().selectedItemProperty().addListener(
                        (observable, oldValue, newValue) -> selecionarItemTableViewVendas(newValue));
    }
    
    public void carregarTableViewVendas() {
        tableColumnVendaCodigo.setCellValueFactory(new PropertyValueFactory<>("cd_venda"));
        tableColumnVendaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnVendaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        listVendas = vendaDAO.listar();
        observableListVendas = FXCollections.observableArrayList(listVendas);
        tableViewVendas.setItems(observableListVendas);
    }
    
    public void selecionarItemTableViewVendas(Venda v1) {
        if (v1 != null) {
            labelVendaCodigo.setText(String.valueOf(v1.getCdVenda()));
            labelVendaData.setText(String.valueOf(v1.getData().format(DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy"))));
            labelVendaValor.setText(String.format("%.2f", v1.getValor()));
            labelVendaPago.setText(String.valueOf(v1.getPago()));
            labelVendaCliente.setText(v1.getCliente().toString());
            
        } else {
            labelVendaCodigo.setText("");
            labelVendaData.setText("");
            labelVendaValor.setText("");
            labelVendaPago.setText("");
            labelVendaCliente.setText("");
        }
    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Venda v1 = new Venda();
        List<ItemDeVenda> listItensDeVenda = new ArrayList<>();
        v1.setItensDeVenda(listItensDeVenda);
        boolean botaoConfimarClicado = mostrarFXMLAnchorPaneProcessosVendasDialog(v1);
        if (botaoConfimarClicado) {
            try {
                connection.setAutoCommit(false);
                vendaDAO.setConnection(connection);
                vendaDAO.inserir(v1);
                itemDVDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                for (ItemDeVenda listItemDeVenda : v1.getItensDeVenda()) {
                    Produto prod = listItemDeVenda.getProduto();
                    listItemDeVenda.setVenda(vendaDAO.buscarUltimaVenda());
                    itemDVDAO.inserir(listItemDeVenda);
                    prod.setQuantidade(prod.getQuantidade() - listItemDeVenda.getQuantidade());
                    produtoDAO.alterar(prod);               
                }
                connection.commit();
                carregarTableViewVendas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    
    @FXML
    public void handleButtonRemover() throws IOException {
        Venda v1 = tableViewVendas.getSelectionModel().getSelectedItem();
        if (v1 != null) {
            try {
                connection.setAutoCommit(false);
                vendaDAO.setConnection(connection);
                itemDVDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                for (ItemDeVenda listItemDeVenda : v1.getItensDeVenda()) {
                    Produto prod = listItemDeVenda.getProduto();
                    prod.setQuantidade(prod.getQuantidade() + listItemDeVenda.getQuantidade());
                    produtoDAO.alterar(prod);
                    itemDVDAO.remover(listItemDeVenda);
                }
                vendaDAO.remover(v1);
                connection.commit();
                carregarTableViewVendas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch(SQLException ex1) {
                    Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null, ex);
            }  
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setContentText("Por favor, escolha um cliente na Tabela");
            al.show();
        }
    }
    
    
    @FXML
    public void handleButtonAlterar() throws IOException {
        Venda v1 = tableViewVendas.getSelectionModel().getSelectedItem();
        if (v1 != null) {
            try {
                connection.setAutoCommit(false);
                vendaDAO.setConnection(connection);
                itemDVDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                
                // Alterar a venda devido a alteração do valor
                vendaDAO.alterar(v1);
                List<ItemDeVenda> listItemDeVendaRemover = itemDVDAO.listarPorVenda(v1);
                // Remover todos os itens de venda anteriomente associados a venda
                for (ItemDeVenda ItemDeVendaRemover : listItemDeVendaRemover) {
                    itemDVDAO.remover(ItemDeVendaRemover);
                    
                    Produto prod = ItemDeVendaRemover.getProduto();
                    prod.setQuantidade(prod.getQuantidade() + ItemDeVendaRemover.getQuantidade());
                    produtoDAO.alterar(prod);
                }
                
                // Inserir todos os itens de venda anteriomente associados a venda
                for (ItemDeVenda listItemDeVenda : v1.getItensDeVenda()) {
                    Produto prod = listItemDeVenda.getProduto();
                    prod = produtoDAO.buscar(prod);
                    listItemDeVenda.setVenda(v1);
                    itemDVDAO.inserir(listItemDeVenda);
                    prod.setQuantidade(prod.getQuantidade() - listItemDeVenda.getQuantidade());
                    produtoDAO.alterar(prod);
                }
                connection.commit();
                carregarTableViewVendas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch(SQLException ex1) {
                    Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLAnchorPaneProcessosVendasController.class.getName()).log(Level.SEVERE, null, ex);
            }
            vendaDAO.alterar(v1);
        }
    }
    
    public boolean mostrarFXMLAnchorPaneProcessosVendasDialog(Venda venda) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneProcessosVendasDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneProcessosVendasDialog.fxml"));
        AnchorPane pagina = (AnchorPane) loader.load();
        
        // Cliando um Estágio de Diálogo
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Vendas");
        Scene cena = new Scene(pagina);
        dialogStage.setScene(cena);
        
        // Setando o cliente no Controller
        FXMLAnchorPaneProcessosVendasDialogController controlador = loader.getController();
        controlador.setDialogStage(dialogStage);
        controlador.setVenda(venda);
        
        // Mostrar o Dialog e esperar até que o usuário o fecha
        dialogStage.showAndWait();
        return controlador.oBotaofoiClicado();
    }
    
    
}

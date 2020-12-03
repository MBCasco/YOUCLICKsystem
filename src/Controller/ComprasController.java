package Controller;

import Models.compras;
import Models.producto;
import Models.proveedores;
import Models.pago;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ComprasController  extends MenuController implements Initializable {
    @FXML
    private TableView<compras> tablaCompras;
    @FXML
    private TableColumn<compras, Integer> col_IDCompra;
    @FXML
    private TableColumn<compras, String> col_proveedor;
    @FXML
    private TableColumn<compras, String> col_producto;
    @FXML
    private TableColumn<compras, Integer> col_cantidad;
    @FXML
    private TableColumn<compras, String> col_fechaP;
    @FXML
    private TableColumn<compras, String> col_fechaR;
    @FXML
    private TextField txt_ID;
    @FXML
    private ComboBox<proveedores> CBXProveedor;
    @FXML
    private ComboBox<producto> CBXProducto;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtFechaPedido;
    @FXML
    private TextField txtFechaLlego;
    @FXML
    private TextField fieldFilter;
    @FXML
    private TextField txtEliminar;

    ObservableList<compras> ListaCompra;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void prueba() throws IOException {
        pago();
    }

    public void UpdateTable(){
        col_IDCompra.setCellValueFactory(new PropertyValueFactory<>("idCompra"));
        col_proveedor.setCellValueFactory(new PropertyValueFactory<>("empresaProveedor"));
        col_producto.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_fechaP.setCellValueFactory(new PropertyValueFactory<>("fechaP"));
        col_fechaR.setCellValueFactory(new PropertyValueFactory<>("fechaR"));

        ListaCompra = connect.getdatacompras();
        tablaCompras.setItems(ListaCompra);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

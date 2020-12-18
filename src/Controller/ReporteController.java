package Controller;

import Models.compras;
import Models.reporte;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class ReporteController extends MenuController implements Initializable {

    @FXML
    private TableView<reporte> tableReporte;
    @FXML
    private TableColumn<reporte, String> empresaReporte;
    @FXML
    private TableColumn<reporte, Integer> nCompraReporte;
    @FXML
    private TableColumn<reporte, String> fPedidoReporte;
    @FXML
    private TableColumn<reporte, String> fLlegadaReporte;
    @FXML
    private TableColumn<reporte, Integer> cantidadReporte;
    @FXML
    private TableColumn<reporte, String> tPagoReporte;
    @FXML
    private TableColumn<reporte, Integer> totalReporte;
    @FXML
    private TableColumn<reporte, String> productoReporte;

    ObservableList<reporte> listaR;
    Connection conn = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTableC();
    }


    public void UpdateTableC(){
        nCompraReporte.setCellValueFactory(new PropertyValueFactory<>("IDCompra"));
        empresaReporte.setCellValueFactory(new PropertyValueFactory<>("empresaProveedor"));
        productoReporte.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        totalReporte.setCellValueFactory(new PropertyValueFactory<>("totalPago"));
        tPagoReporte.setCellValueFactory(new PropertyValueFactory<>("desTipoPago"));
        cantidadReporte.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fPedidoReporte.setCellValueFactory(new PropertyValueFactory<>("FechaPedido"));
        fLlegadaReporte.setCellValueFactory(new PropertyValueFactory<>("FechaLlegada"));

        listaR = connect.getDataReporte();
        tableReporte.setItems(listaR);
    }

    public void salir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaPrincipal.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

}

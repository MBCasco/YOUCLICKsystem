package Controller;

import ComboBoxController.productos;
import ComboBoxController.proveedor;
import Models.compras;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
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
    private TextField txtiD;
    @FXML
    private TextField txtcantidad;
    @FXML
    private DatePicker DataFechaP;
    @FXML
    private DatePicker DataFechaR;
    @FXML
    private ComboBox<proveedor> CBXProveedor;
    @FXML
    private ComboBox<productos> CBXProducto;
    @FXML
    private TextField txtEliminar;
    @FXML
    private TextField filterField;

    ObservableList<compras> ListaCompra;
    ObservableList<proveedor> listCPV = proveedor.getproveedor();
    ObservableList<productos> listCPR = productos.getproductos();
    ObservableList<compras> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");


    public void prueba() throws IOException {
        pago();
    }
    public void InComboBox(){
        CBXProducto.setItems(listCPR);
        CBXProveedor.setItems(listCPV);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InComboBox();
        UpdateTable();
    }

    public void AddCompra(){
        conn = connect.conDB();
        String sql = "INSERT INTO compra (cantidad, FechaPedido, FechaLlegada, IDProveedor, IDProducto) VALUES (?,?,?,?,?)";
        //if (validateFields() & limite() & validateName() & validateEmail() & validateDireccion()) {
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtcantidad.getText());
                pst.setString(2, DataFechaP.getValue().format(formatter));
                pst.setString(3, DataFechaR.getValue().format(formatter));
                pst.setString(4, String.valueOf(CBXProveedor.getSelectionModel().getSelectedItem().getIDProveedor()));
                pst.setString(5, String.valueOf(CBXProducto.getSelectionModel().getSelectedItem().getIDProducto()));
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó exitosamente");
                alert.showAndWait();

                UpdateTable();
                Search_compra();

            } catch (Exception e) {
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Por favor revise los campos que esten llenos correctamente");
                alert.showAndWait();
            }
        //}
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
    public void Delete(){
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Confirmar");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro ¿Qué quieres eliminar esta compra?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                conn = connect.conDB();
                try {
                    String Ssql = "DELETE FROM compra WHERE IDCompra = ?";
                    PreparedStatement prest = conn.prepareStatement(Ssql);
                    prest.setString(1, txtEliminar.getText());

                    if (prest.executeUpdate() > 0){
                        Alert alert1 =new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Informacion");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Se elimino con éxito");
                        alert1.showAndWait();
                        UpdateTable();
                    }
                }catch (Exception e){
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Error");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Hubo un error al eliminar,  por favor inténtelo de nuevo." +
                            "\nEste campo solo permite eliminar por ID.");
                }
            }
        });
    }

    void Search_compra(){
        col_IDCompra.setCellValueFactory(new PropertyValueFactory<compras,Integer>("idCompra"));
        col_proveedor.setCellValueFactory(new PropertyValueFactory<compras,String>("empresaProveedor"));

        dataList = connect.getdatacompras();
        tablaCompras.setItems(dataList);
        FilteredList<compras> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getEmpresaProveedor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (String.valueOf(person.getIdCompra()).indexOf(lowerCaseFilter)!=-1)
                    return true;

                else
                    return false;
            });
        });
        SortedList<compras> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablaCompras.comparatorProperty());
        tablaCompras.setItems(sortedData);
    }


}

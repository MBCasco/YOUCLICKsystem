package Controller;

import ComboBoxController.productos;
import ComboBoxController.proveedor;
import Models.compras;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class ComprasController  extends MenuController implements Initializable {
    private static Object txtStock;
    @FXML
    private TableView<compras> tablaCompras;
    @FXML
    private TableColumn<compras, Integer> col_IDCompra;
    @FXML
    private TableColumn<compras, proveedor> col_proveedor;
    @FXML
    private TableColumn<compras, productos> col_producto;
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
    @FXML
    private Button btn_registrar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Tab tab_pago;

    ObservableList<compras> ListaCompra;
    ObservableList<proveedor> listCPV = proveedor.getproveedor();
    ObservableList<productos> listCPR = productos.getproductos();
    ObservableList<compras> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");


    public void prueba() throws IOException {
        pago();
    }

    public static int value (int value){
        txtStock = value;
        System.out.println(txtStock);
        return value;
    }

    public void InComboBox(){
        CBXProducto.setItems(listCPR);
        CBXProveedor.setItems(listCPV);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InComboBox();
        UpdateTable();
        checkBtnStatus(0);
        txtStatus(0);
        Search_compra();
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
    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_registrar.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
            tab_pago.setDisable(true);
        }
        if (check == 0){
            btn_registrar.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
            tab_pago.setDisable(true);
        }
    }
    private void txtStatus(int checkT){
        if (checkT == 1){
            txtcantidad.setDisable(true);
            DataFechaP.setDisable(true);
            DataFechaR.setDisable(false);
            CBXProveedor.setDisable(true);
            CBXProducto.setDisable(true);
        }
        if (checkT == 0){
            txtcantidad.setDisable(false);
            DataFechaP.setDisable(false);
            DataFechaR.setDisable(false);
            CBXProveedor.setDisable(false);
            CBXProducto.setDisable(false);
        }
    }
    @FXML
    private void clearFields() {
        txtiD.clear();
        CBXProveedor.setValue(null);
        CBXProducto.setValue(null);
        txtcantidad.clear();
        DataFechaP.setValue(null);
        DataFechaR.setValue(null);
        txtEliminar.clear();
        checkBtnStatus(0);
        txtStatus(0);

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
    public void DeleteC(){
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

                        if (prest.executeUpdate() > 0) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Informacion");
                            alert1.setHeaderText(null);
                            alert1.setContentText("Se elimino con éxito");
                            alert1.showAndWait();
                            UpdateTable();
                        }
                    } catch (Exception e) {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Hubo un error al eliminar,  por favor inténtelo de nuevo." +
                                "\nEste campo solo permite eliminar por ID.");
                    }
            }
        });
    }
    public void Edit(){
        //if (validateFields() & limite() & validateName() & validateEmail() & validateDireccion()) {
            try {
                conn = connect.conDB();
                String value1 = txtiD.getText();
                String value2 = DataFechaR.getValue().format(formatter);

                String sql = "UPDATE compra SET FechaLlegada ='" + value2 + "' where IDCompra='" + value1 + "' ";;
                pst = conn.prepareStatement(sql);
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Se actualizó exitosamente");
                alert.showAndWait();

                UpdateTable();
                Search_compra();
                clearFields();

            } catch (Exception e) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al actualizar, revise que todos los campos estén llenados correctamente");
                alert.showAndWait();
            }
        //}
    }

    void Search_compra(){
        col_IDCompra.setCellValueFactory(new PropertyValueFactory<>("idCompra"));
        col_proveedor.setCellValueFactory(new PropertyValueFactory<>("empresaProveedor"));

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

    @FXML
    public void getSelected(MouseEvent event) {
        index = tablaCompras.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        PagoController.value(col_IDCompra.getCellData(index));

        txtiD.setText(col_IDCompra.getCellData(index).toString());
        CBXProveedor.setValue(col_proveedor.getCellData(index));
        CBXProducto.setValue(col_producto.getCellData(index));
        txtcantidad.setText(col_cantidad.getCellData(index).toString());
        txtEliminar.setText(col_IDCompra.getCellData(index).toString());
        checkBtnStatus(1);
        txtStatus(1);

    }

}

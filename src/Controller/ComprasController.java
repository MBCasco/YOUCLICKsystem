package Controller;

import ComboBoxController.proveedor;
import Models.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComprasController  extends MenuController implements Initializable {
    private static Object TxtIDCompra;
    @FXML
    private TableView<productoSS> tablaProductos;
    @FXML
    private TableColumn<productoSS, Integer> col_idpro;
    @FXML
    private TableColumn<productoSS, String> col_NombrePro;
    @FXML
    private TableColumn<productoSS, Double> col_PrecioP;
    @FXML
    private TableColumn<productoSS, Integer> col_StockP;

    /////////////////////////////////////////////////////////////////////
    @FXML
    private TableView<detallecompra> tablaDetalleCompra;
    @FXML
    private TableColumn<detallecompra, Integer> col_IDCompra;
    @FXML
    private TableColumn<detallecompra, proveedor> col_proveedor;
    @FXML
    private TableColumn<detallecompra, String> col_producto;
    @FXML
    private TableColumn<detallecompra, Integer> col_cantidad;
    @FXML
    private TableColumn<detallecompra, Integer> col_IDC;

    /////////////////////////////////////////////////////////////////////////
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
    private TextField txtPrecio;
    @FXML
    private TextField txtIdPro;
    @FXML
    private TextField txtBuscarP;
    @FXML
    private Button btn_agregarP;
    @FXML
    private Button btn_compra;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Tab tab_pago;

    //////////////////////////////////////////////////////////////
    @FXML
    private TextField txtSubTotal;
    @FXML
    private TextField txtImpuesto1;
    @FXML
    private TextField txtTotal;
    @FXML



    ObservableList<detallecompra> listaC;
    ObservableList<proveedor> listCPV = proveedor.getproveedor();
    ObservableList<compras> compras;
    ObservableList<productoSS> listPS;
    ObservableList<productoSS> datalistPS;
    ObservableList<compras> compras1;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    String id4Delete = null;
    boolean x = false;
    int exist = 0;
    int ID;

    public void prueba() throws IOException {
        pago();
    }

    public void InComboBox(){
        CBXProveedor.setItems(listCPV);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Search_producto();
        InComboBox();
        UpdateTableC();
        UpdateTableP();
        checkBtnStatus(0);
        txtStatus(0);
        exist = 0;
        ID = 0;
    }

    public void pruebareporte() throws SQLException, IOException {
        report();
    }

    public void clearmini() {
        txtIdPro.clear();
        txtBuscarP.clear();
        txtPrecio.clear();
        txtcantidad.clear();
    }

    public void pagar() throws SQLException {
        PagoController.value2(txtTotal.getText());
        PagoController.value(ID);
        trasferir();
        tab_pago.setDisable(false);
    }


    public void AddProducto() throws SQLException {
        conn = connect.conDB();
        if(exist == 0){
            CrearCompra();
            compras = connect.getdatacomprast();
            int n = compras.size();
            if (n < 1){
                ID = 1;
            }else {
                ID = compras.get(n-1).getIDCompra();
            }

            exist = 1;
            Integer value1 = ID;

            pst = conn.prepareStatement("update comprat set IDDetalleCompra= '" + value1 + "  'where IDCompra='" + value1 + "' ");
            pst.execute();
            pst = conn.prepareStatement("update comprat set IDPago= '" + value1 + "  'where IDCompra='" + value1 + "' ");
            pst.execute();

        }

        try {

            String sql = "insert into detallecomprat (IDCompra, Cantidad, IDProducto, IDProveedor)values(?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, ID);
            pst.setString(2, txtcantidad.getText());
            pst.setString(3, txtIdPro.getText());
            pst.setString(4, String.valueOf(CBXProveedor.getSelectionModel().getSelectedItem().getIDProveedor()));
            pst.execute();
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("tranza");
            alert.setHeaderText(null);
            alert.setContentText("Se agregó exitosamente");
            alert.showAndWait();
            UpdateTableP();
            UpdateTableC();
            updatecampos();
            clearmini();

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }
    }

    public void trasferir() throws SQLException {

        String sql2 = "insert into detallecompra select * from detallecomprat where IDCompra = ?;";
        try {
            pst = conn.prepareStatement(sql2);
            pst.setInt(1,ID);
            pst.execute();

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }

        String sql1 = "insert into compra select * from comprat where IDCompra = ?;";
        try {
            pst = conn.prepareStatement(sql1);
            pst.setInt(1,ID);
            pst.execute();
            System.out.println("entrada");

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");

        }
    }

    public void CrearCompra(){
        conn = connect.conDB();

        String sql = "INSERT INTO comprat (FechaPedido, FechaLlegada, IDProveedor, IDProducto, totalCompra, impuesto, subtotalCompra) VALUES (?,?,?,?,?,?,?)";
        if (validateFields() & validateCantidad()) {
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, DataFechaP.getValue().format(formatter));
                pst.setString(2, DataFechaR.getValue().format(formatter));
                pst.setString(3, String.valueOf(CBXProveedor.getSelectionModel().getSelectedItem().getIDProveedor()));
                pst.setString(4, txtIdPro.getText());
                pst.setDouble(5, 0.0);
                pst.setDouble(6, 0.0);
                pst.setDouble(7, 0.0);
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("hola");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó exitosamente");
                alert.showAndWait();
                UpdateTableP();
                UpdateTableC();

            } catch (Exception e) {
                //Alert alert =new Alert(Alert.AlertType.ERROR);
                //alert.setTitle("Error");
                //alert.setHeaderText(null);
               // alert.setContentText("valio papita");
                //alert.showAndWait();
            }
        }
    }

    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_agregarP.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
            //tab_pago.setDisable(true);
        }
        if (check == 0){
            btn_agregarP.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
            //tab_pago.setDisable(true);
        }
    }

    private void txtStatus(int checkT){
        if (checkT == 1){
            txtcantidad.setDisable(true);
            DataFechaP.setDisable(true);
            DataFechaR.setDisable(false);
            CBXProveedor.setDisable(true);
        }
        if (checkT == 0){
            txtcantidad.setDisable(false);
            DataFechaP.setDisable(false);
            DataFechaR.setDisable(false);
            CBXProveedor.setDisable(false);
        }
    }

    @FXML
    private void clearFields() {
        Search_producto();
        txtiD.clear();
        CBXProveedor.setValue(null);
        txtcantidad.clear();
        DataFechaP.setValue(null);
        DataFechaR.setValue(null);
        txtIdPro.clear();
        txtPrecio.clear();
        txtBuscarP.clear();
        checkBtnStatus(0);
        txtStatus(0);

    }

    public void UpdateTableC(){
        col_IDCompra.setCellValueFactory(new PropertyValueFactory<>("IDProducto"));
        col_proveedor.setCellValueFactory(new PropertyValueFactory<>("empresaProveedor"));
        col_producto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        col_IDC.setCellValueFactory(new PropertyValueFactory<>("IDDetalleCompra"));

        System.out.println(ID);
        listaC = connect.getDataComprat(ID);
        tablaDetalleCompra.setItems(listaC);
    }

    public void UpdateTableP(){
        col_idpro.setCellValueFactory(new PropertyValueFactory<>("idpro"));
        col_NombrePro.setCellValueFactory(new PropertyValueFactory<>("nombrepro"));
        col_PrecioP.setCellValueFactory(new PropertyValueFactory<>("preciopro"));
        col_StockP.setCellValueFactory(new PropertyValueFactory<>("stock"));

        listPS = connect.getDataProductoSS();
        tablaProductos.setItems(listPS);
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
                        String Ssql = "DELETE FROM detallecomprat WHERE IDDetalleCompra = ?";
                        PreparedStatement prest = conn.prepareStatement(Ssql);
                       prest.setString(1, id4Delete);

                        if (prest.executeUpdate() > 0) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Informacion");
                            alert1.setHeaderText(null);
                            alert1.setContentText("Se elimino con éxito");
                            alert1.showAndWait();
                            UpdateTableC();
                            updatecampos();
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
            try {
                conn = connect.conDB();
                String value1 = id4Delete;
                String value2 = txtcantidad.getText();

                String sql = "UPDATE detallecomprat SET Cantidad ='" + value2 + "' where IDDetalleCompra='" + value1 + "' ";
                pst = conn.prepareStatement(sql);
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Se actualizó exitosamente");
                alert.showAndWait();

                UpdateTableC();
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

    @FXML
    public void getSelected(MouseEvent event) {
        index = tablaDetalleCompra.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        id4Delete = col_IDC.getCellData(index).toString();
        txtcantidad.setText(col_cantidad.getCellData(index).toString());
        checkBtnStatus(1);
        txtStatus(1);

    }

    @FXML
    public void getSelectedP(javafx.scene.input.MouseEvent event) {
        index = tablaProductos.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }

        txtIdPro.setText(col_idpro.getCellData(index).toString());
        txtPrecio.setText(col_PrecioP.getCellData(index).toString());
        txtcantidad.clear();
        checkBtnStatus(0);
    }

    @FXML
    void Search_producto(){
        col_idpro.setCellValueFactory(new PropertyValueFactory<>("idpro"));
        col_NombrePro.setCellValueFactory(new PropertyValueFactory<>("nombrepro"));

        datalistPS = connect.getDataProductoSS();
        tablaProductos.setItems(datalistPS);
        FilteredList<productoSS> filteredData = new FilteredList<>(datalistPS, b -> true);
        txtBuscarP.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNombrepro().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (String.valueOf(person.getIdpro()).indexOf(lowerCaseFilter)!=-1)
                    return true;

                else
                    return false;
            });
        });
        SortedList<productoSS> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind((ObservableValue<? extends Comparator<? super productoSS>>) tablaProductos.comparatorProperty());
        tablaProductos.setItems(sortedData);
    }

    public void updatecampos(){
        compras1 = connect.getdatacomprast();
        txtTotal.setText((compras1.get(ID-1).getTotalCompra()));
        txtImpuesto1.setText((compras1.get(ID-1).getImpuesto()));
        txtSubTotal.setText((compras1.get(ID-1).getSubtotalCompra()));
    }

    //Validaciones
    private boolean validateCantidad(){
        Pattern p = Pattern.compile("[0-9]{1,8}");
        Matcher m = p.matcher(txtcantidad.getText().trim());

        if(m.find() && m.group().equals(txtcantidad.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Cantidad");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \n-Este campo solo acepta numeros" +
                    " \n-Que el numero que ingreso sea entero" +
                    " \n-Y el numero que ingreso contenga maximo 8 digitos");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateFields(){
        if (txtcantidad.getText().isEmpty() | CBXProveedor.getSelectionModel().isEmpty()){

            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }

}

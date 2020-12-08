package Controller;

import ComboBoxController.marca;
import Models.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class FacturaController extends MenuController implements Initializable {

    @FXML
    private TableView<inventario> tableInventario;

    @FXML
    private TableColumn<inventario, Integer> ColID;

    @FXML
    private TableColumn<inventario, String> ColNombre;

    @FXML
    private TableColumn<inventario, Integer> ColStock;

    @FXML
    private TableColumn<inventario, String> ColDescrip;

    @FXML
    private TableColumn<inventario, Double> ColPrecio;

    @FXML
    private TableColumn<inventario, marca> ColMarca;

    @FXML
    private DatePicker dp;

    ///////////////////////////////////////////////////////////

    @FXML
    private TableView<factura> tablaFactura;

    @FXML
    private TableColumn<factura, Integer> ColIDF;
    @FXML
    private TableColumn<factura, Integer> ColIDP;

    @FXML
    private TableColumn<factura, String> ColProcducto;

    @FXML
    private TableColumn<factura, Integer> ColCantidad;

    @FXML
    private TableColumn<factura, String> SubTotal;

    @FXML
    private TableColumn<factura, Double> ColPrecioF;

    @FXML
    private TableColumn<factura, Double> colSubTotal;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private TableView<clientes> table_clientes;

    @FXML
    private TableColumn<clientes, Integer> col_cliente;

    @FXML
    private TableColumn<clientes, String> col_nombre;

    @FXML
    private TableColumn<clientes, String> col_direccion;

    @FXML
    private TableColumn<clientes, Integer> col_telefono;

    @FXML
    private TableColumn<clientes, String> col_correo;

    @FXML
    private TableColumn<clientes, String> col_Sexo;



    @FXML
    private TextField txt_cantidad;
    @FXML
    private TextField txt_IDP;
    @FXML
    private TextField txt_precio;
    @FXML
    private TextField txt_stock;
    @FXML
    private TextField txt_IDCliente;
    @FXML
    private TextField txt_nombreCliente;
    @FXML
    private ComboBox<empleados> comEmpleados;
    @FXML
    private Button btn_seleccionarCliente;
    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_facturar;
    @FXML
    private Button btn_clean;
    @FXML
    private Label labelStock;


    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String id4Delete = null;

    ObservableList<inventario> listI;
    ObservableList<factura> listF;
    ObservableList<clientes> listM;
    ObservableList<empleados> listaEmpleados = connect.getdataempleados();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UpdateTableI();
        UpdateTableF();
        UpdateTable();
        intCombox ();
        clearfields();



    }

    private void checkBtnStatus(int check) {
        if (check == 0){
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
            btn_agregar.setDisable(false);
            txt_stock.setVisible(true);
            txt_stock.setVisible(true);
            labelStock.setVisible(true);
        }
        if (check == 1){
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
            btn_agregar.setDisable(true);
            txt_stock.setVisible(false);
            labelStock.setVisible(false);
        }
    }

    public void clearfields(){
        hideInventario(false);
        txt_cantidad.clear();
        txt_precio.clear();
        txt_stock.clear();
        txt_IDP.clear();
        txt_nombreCliente.clear();
        txt_IDCliente.clear();
        dp.setValue(null);
        checkBtnStatus(0);
        txt_IDCliente.setVisible(false);
        txt_nombreCliente.setVisible(false);
        labelStock.setVisible(true);
        btn_seleccionarCliente.setVisible(true);



    }

    public void Edit(){

    }

    public void intCombox (){
        comEmpleados.setItems(listaEmpleados);

    }


    public void hideInventario(boolean x){
        table_clientes.setVisible(x);
        tableInventario.setVisible(!x);
        tablaFactura.setDisable(x);
        btn_actualizar.setDisable(x);
        btn_eliminar.setDisable(x);
        btn_agregar.setDisable(x);
    }


    public void selectCliente(){
        hideInventario(true);

    }

    public void AddProducto() {
        conn = connect.conDB();
        String sql = "insert into detallefactura (IDFactura,Cantidad, IDProducto)values(?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, 1);
            pst.setString(2, txt_cantidad.getText());
            pst.setString(3, txt_IDP.getText());
            pst.execute();
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Se agregó exitosamente");
            alert.showAndWait();
            UpdateTableI();
            UpdateTableF();

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }

    }

    public void Delete(){
        conn = connect.conDB();
        try {
            String sql = "DELETE from detallefactura WHERE IDDetalleFactura = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id4Delete);
            pst.execute();

        }catch (Exception e){
        }

        UpdateTableF();

    }



    public void UpdateTableI(){
        ColID.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        ColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        ColPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));


        listI = connect.getdatainventario();
        tableInventario.setItems(listI);

    }

    public void UpdateTableF(){
        ColIDF.setCellValueFactory(new PropertyValueFactory<factura,Integer>("IDDetalleFactura"));
        ColIDP.setCellValueFactory(new PropertyValueFactory<factura,Integer>("IDProducto"));
        ColProcducto.setCellValueFactory(new PropertyValueFactory<factura,String>("nombre"));
        ColPrecioF.setCellValueFactory(new PropertyValueFactory<factura,Double>("precio"));
        ColCantidad.setCellValueFactory(new PropertyValueFactory<factura,Integer>("cantidad"));

        listF = connect.getDataFactura();
        tablaFactura.setItems(listF);
    }

    public void UpdateTable(){
        col_cliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        col_Sexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        listM = connect.getdataclientes();
        table_clientes.setItems(listM);
    }



    @FXML
    public void getSelected(javafx.scene.input.MouseEvent event) {
        index = tableInventario.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }


        txt_IDP.setText(ColID.getCellData(index).toString());
        txt_precio.setText(ColPrecio.getCellData(index).toString());
        txt_stock.setText(ColStock.getCellData(index).toString());
        txt_cantidad.clear();
        checkBtnStatus(0);

    }

    @FXML
    public void getSelectedF(javafx.scene.input.MouseEvent event) {
        index = tablaFactura.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }
        id4Delete = ColIDF.getCellData(index).toString();
        txt_IDP.setText(ColIDP.getCellData(index).toString());
        txt_precio.setText(ColPrecioF.getCellData(index).toString());
        txt_cantidad.setText(ColCantidad.getCellData(index).toString());
        checkBtnStatus(1);

    }

    @FXML
    public void getSelectedC(javafx.scene.input.MouseEvent event) {
        index = table_clientes.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }
        txt_IDCliente.setText(col_cliente.getCellData(index).toString());
        txt_nombreCliente.setText(col_nombre.getCellData(index));
        btn_seleccionarCliente.setVisible(false);
        txt_IDCliente.setVisible(true);
        txt_nombreCliente.setVisible(true);
        hideInventario(false);

    }
}

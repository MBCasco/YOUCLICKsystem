package Controller;

import ComboBoxController.marca;
import Models.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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
    private TableView<detallefactura> tablaFactura;

    @FXML
    private TableColumn<detallefactura, Integer> ColIDF;
    @FXML
    private TableColumn<detallefactura, Integer> ColIDP;

    @FXML
    private TableColumn<detallefactura, String> ColProcducto;

    @FXML
    private TableColumn<detallefactura, Integer> ColCantidad;

    @FXML
    private TableColumn<detallefactura, String> SubTotal;

    @FXML
    private TableColumn<detallefactura, Double> ColPrecioF;

    @FXML
    private TableColumn<detallefactura, Double> colSubTotal;
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
    @FXML
    private TextField txt_search;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String id4Delete = null;
    boolean x = false;

    ObservableList<inventario> listI;
    ObservableList<inventario> dataList;
    ObservableList<clientes> dataListc;
    ObservableList<detallefactura> listF;
    ObservableList<clientes> listM;
    ObservableList<empleados> listaEmpleados = connect.getdataempleados();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    int exist = 0;
    ObservableList<factura> factura;
    int ID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Search_producto();
        UpdateTableI();
        UpdateTableF();
        UpdateTable();
        intCombox ();
        clearfields();
        exist = 0;
        tableInventario.setDisable(true);
        ID = 0;

    }


    private void checkBtnStatus(int check) {
        if (check == 0){
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
            btn_agregar.setDisable(false);
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
        Search_producto();
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
        exist = 0;

    }
    public void clearmini(){
        txt_cantidad.clear();
        txt_precio.clear();
        txt_stock.clear();
        txt_IDP.clear();
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
        Search_cliente();
        txt_search.setPromptText("Buscar Cliente");


    }




    public void AddProducto() throws SQLException {

        if ((txt_IDCliente.getText() != null && comEmpleados.getValue() !=null & dp.getValue() != null)){

            System.out.println("entro");
            conn = connect.conDB();

            if(exist == 0){
                CrearFactura();
                factura = connect.getDataFacturat();
                int n = factura.size();
                ID = factura.get(n-1).getIDFactura();
                exist = 1;
                Integer value1 = ID;

                String sqls = "update facturat set IDDetalleFactura= '" + value1 + "  'where IDFactura='" + value1 + "' ";

                pst = conn.prepareStatement(sqls);
                pst.execute();
                System.out.println("exist cambio" + exist);

            }


            try {

                String sql = "insert into detallefacturat (IDFactura,Cantidad, IDProducto)values(?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, ID);
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
                clearmini();

            } catch (Exception e) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
            }
        }


    }

    public void trasferir() throws SQLException {

       String sql1 = "insert into factura select * from facturat where IDFactura = ?;";
        try {
            pst = conn.prepareStatement(sql1);
            pst.setInt(1,ID);
            pst.execute();

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }


       String sql2 = "insert into detallefactura select * from detallefacturat where IDFactura = ?;";
        try {
            pst = conn.prepareStatement(sql2);
            pst.setInt(1,ID);
            pst.execute();

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }

    }

    public void CrearFactura(){
        conn = connect.conDB();
        String sql = "insert into facturat (fechaFactura, IDCliente, IDEmpleado, totalFactura, impuesto, subtotalFactura)values(?,?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, dp.getValue().format(formatter));
            pst.setInt(2, Integer.parseInt(txt_IDCliente.getText()));
            pst.setInt(3, comEmpleados.getSelectionModel().getSelectedItem().getIdEmpleado());
            pst.setDouble(4,12.0 );
            pst.setDouble(5, 12.0);
            pst.setDouble(6, 12.0);

            pst.execute();
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Se genero");
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
            String sql = "DELETE from detallefacturat WHERE IDDetalleFactura = ?";
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
        ColIDF.setCellValueFactory(new PropertyValueFactory<detallefactura,Integer>("IDDetalleFactura"));
        ColIDP.setCellValueFactory(new PropertyValueFactory<detallefactura,Integer>("IDProducto"));
        ColProcducto.setCellValueFactory(new PropertyValueFactory<detallefactura,String>("nombre"));
        ColPrecioF.setCellValueFactory(new PropertyValueFactory<detallefactura,Double>("precio"));
        ColCantidad.setCellValueFactory(new PropertyValueFactory<detallefactura,Integer>("cantidad"));
        System.out.println(ID);
        listF = connect.getDataDetalleFacturat(ID);
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
        txt_search.setPromptText("Buscar Producto");
        hideInventario(false);
        tableInventario.setDisable(false);

    }

    @FXML
    void Search_producto(){
        ColID.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        ColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        dataList = connect.getdatainventario();
        tableInventario.setItems(dataList);
        FilteredList<inventario> filteredData = new FilteredList<>(dataList, b -> true);
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (String.valueOf(person.getIdProducto()).indexOf(lowerCaseFilter)!=-1)
                    return true;

                else
                    return false;
            });
        });
        SortedList<inventario> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind((ObservableValue<? extends Comparator<? super inventario>>) tableInventario.comparatorProperty());
        tableInventario.setItems(sortedData);
    }


    @FXML
    void Search_cliente(){
        col_cliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        dataListc = connect.getdataclientes();
        table_clientes.setItems(dataListc);
        FilteredList<clientes> filteredData = new FilteredList<>(dataListc, b -> true);
        txt_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (String.valueOf(person.getIdCliente()).indexOf(lowerCaseFilter)!=-1)
                    return true;

                else
                    return false;
            });
        });
        SortedList<clientes> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_clientes.comparatorProperty());
        table_clientes.setItems(sortedData);
    }
}

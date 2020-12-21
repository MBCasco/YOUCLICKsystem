package Controller;

import ComboBoxController.TipoPago;
import ComboBoxController.marca;
import Models.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Button btnRegistar;
    @FXML
    private Button btn_facturar;
    @FXML
    private Button btn_clean;

    @FXML
    private Button btn_finalizarFactura;
    @FXML
    private Button btn_limpiar;
    @FXML
    private Label labelStock;
    @FXML
    private TextField txt_search;
    @FXML
    private TextField txt_subTotal;
    @FXML
    private TextField txt_isv;
    @FXML
    private TextField txt_total;
    @FXML
    private TextField txt_fecha;
    @FXML
    private TextField txt_IDFactura;


    int indexF = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    String id4Delete = null;
    boolean x = false;
    String productselected ;

    ObservableList<inventario> listI;
    ObservableList<inventario> dataListI;
    ObservableList<clientes> dataListc;
    ObservableList<detallefactura> listF;
    ObservableList<clientes> listM;
    ObservableList<empleados> listaEmpleados = connect.getdataempleados();
    DateTimeFormatter formatterF = DateTimeFormatter.ofPattern("MM/dd/yy");
    int exist = 0;
    ObservableList<factura> factura;
    ObservableList<factura> factura2;

    int ID;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Search_producto();
        UpdateTableI();
        UpdateTableF();
        UpdateTableC();
        intComboxF();
        try {
            clearfields();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exist = 0;
        tableInventario.setDisable(true);
        btn_actualizar.setDisable(true);
        btn_eliminar.setDisable(true);
        btn_facturar.setDisable(true);
        btn_clean.setDisable(true);
        ID = 0;
        //////////////
        intCombox();
        pago.setDisable(true);
        prueba4();
        txtNumTarje.setDisable(true);
        txtNPT.setDisable(true);
        txtCodST.setDisable(true);
        DataFT.setDisable(true);
        btnRegistar.setDisable(true);
        btn_finalizarFactura.setDisable(true);
        btn_limpiar.setDisable(true);
        txtCantidadPagada.setDisable(true);

        int generado = 0;
        btn_agregar.setDisable(true);

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

    public void clearfields() throws IOException {
        Search_producto();
        hideInventario(false);
        txt_cantidad.clear();
        txt_precio.clear();
        txt_stock.clear();
        txt_IDP.clear();
        txt_nombreCliente.clear();
        txt_IDCliente.clear();
        txt_fecha.clear();

        txt_IDCliente.setVisible(false);
        txt_nombreCliente.setVisible(false);
        labelStock.setVisible(true);
        btn_seleccionarCliente.setVisible(true);


        pago.setDisable(true);
        if(exist != 0){ newlaunch();}
        exist = 0;
    }

    public void clearmini(){
        txt_cantidad.clear();
        txt_precio.clear();
        txt_stock.clear();
        txt_IDP.clear();
    }

    public void Edit(){

        if (validatenumero()){
            if ((txt_IDCliente.getText() != null && comEmpleados.getValue() !=null )) {
                try{
                    conn = connect.conDB();

                    String value1 = id4Delete;
                    String value2 = txt_cantidad.getText();

                    String sql = ("update detallefacturat set Cantidad= '"+value2+"' where IDDetalleFactura='"+value1+"' ");

                    pst = conn.prepareStatement(sql);
                    pst.execute();

                    Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setHeaderText(null);
                    alert.setContentText("Se actualizó la factura exitosamente");
                    alert.showAndWait();
                    UpdateTableF();
                    clearmini();

                }catch(Exception e){
                    Alert alert =new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Hubo un error al actualizar, revise que todos los campos estén llenos correctamente");
                    alert.showAndWait();
                }}
        }

    }

    public void intComboxF(){
        comEmpleados.setItems(listaEmpleados);

    }


    public void hideInventario(boolean x){
        table_clientes.setVisible(x);
        tableInventario.setVisible(!x);
        tablaFactura.setDisable(x);
        btn_actualizar.setDisable(x);
        btn_eliminar.setDisable(x);
        btn_agregar.setDisable(!x);
    }


    public void selectCliente(){
        hideInventario(true);
        Search_cliente();
        txt_search.setPromptText("Buscar Cliente");


    }


    public void updatecampos(){
        factura2 = connect.getDataFacturat();
        txt_subTotal.setText(factura2.get(ID-1).getSubtotalFactura());
        txt_isv.setText(factura2.get(ID-1).getImpuesto());
        txt_total.setText(factura2.get(ID-1).getTotalFactura());
        txt_fecha.setText(factura2.get(ID-1).getFechaFactura());

    }

    private boolean validatenumero() {
        Pattern pa = Pattern.compile("[0-9]{1,8}");
        Matcher ma = pa.matcher(txt_cantidad.getText().trim());

        if(ma.find() && ma.group().equals(txt_cantidad.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar cantidad");
            alert.setHeaderText(null);
            alert.setContentText("Este campo solo permite numeros enteros");
            alert.showAndWait();
            clearmini();
            btn_agregar.setDisable(true);
            return false;

        }
    }

    public void AddProducto() throws SQLException {

        if (validatenumero()){
            if ((txt_IDCliente.getText() != null && comEmpleados.getValue() !=null )) {
                conn = connect.conDB();

                if (exist == 0) {
                    CrearFactura();
                    factura = connect.getDataFacturat();
                    int n = factura.size();
                    if (n < 1) {
                        ID = 1;
                    } else {
                        ID = factura.get(n - 1).getIDFactura();
                    }

                    exist = 1;
                    Integer value1 = ID;

                    String sqls = "update facturat set IDDetalleFactura= '" + value1 + "  'where IDFactura='" + value1 + "' ";
                    String sqls2 = "update facturat set IDPago= '" + value1 + "  'where IDFactura='" + value1 + "' ";

                    pst = conn.prepareStatement(sqls);
                    pst.execute();
                    pst = conn.prepareStatement(sqls2);
                    pst.execute();
                    txt_IDFactura.setText(String.valueOf(ID));
                    btn_facturar.setDisable(false);
                    btn_clean.setDisable(false);

                }

                try {
                    if (Integer.parseInt(txt_cantidad.getText()) <= Integer.parseInt(txt_stock.getText())){


                        String sql = "insert into detallefacturat (IDFactura,Cantidad,IDProducto)values(?,?,?)";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, ID);
                        pst.setString(2, txt_cantidad.getText());
                        pst.setString(3, txt_IDP.getText());
                        pst.execute();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Agregado");
                        alert.setHeaderText(null);
                        alert.setContentText("Se agregó exitosamente");
                        alert.showAndWait();
                        btn_agregar.setDisable(true);
                        UpdateTableI();
                        UpdateTableF();
                        updatecampos();
                        clearmini();

                    }else{ Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("No hay stock Suficiente");
                        alert.showAndWait();}



                } catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                }

            } else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Falta agregar Empleado");
                alert.show();
            }

        }


    }

    public void realizarPago() throws IOException {
        pago.setDisable(false);
        value(ID);
        UpdateTable();
        txtTotal.setText(txt_total.getText());
        txt_totalpagar.setText(txt_total.getText());
    }

    public void trasferir() throws SQLException, IOException, ClassNotFoundException, JRException {

        if(Double.parseDouble(txt_totalacumulado.getText()) != Double.parseDouble(txt_totalpagar.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("El valor a pagar no coincide con el valor pagado");
            alert.show();
        }else{

        String sql2 = "insert into detallefactura select * from detallefacturat where IDFactura = ?;";
        try {
            pst = conn.prepareStatement(sql2);
            pst.setInt(1,ID);
            pst.execute();

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }

       String sql1 = "insert into factura select * from facturat where IDFactura = ?;";
        try {
            pst = conn.prepareStatement(sql1);
            pst.setInt(1,ID);
            pst.execute();
        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");

        }

        String sql3 = "insert into pagofactura select * from pagofacturat where IDFactura = ?;";
        try {
            pst = conn.prepareStatement(sql3);
            pst.setInt(1,ID);
            pst.execute();

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }

        String sql4 = "insert into detalledepagofactura select * from detalledepagofacturat where IDPago = ?;";
        try {
            pst = conn.prepareStatement(sql4);
            pst.setInt(1,ID);
            pst.execute();
        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");

        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Factura Completa");
        alert.setHeaderText(null);
        alert.setContentText("Factura procesada correctamente");
        alert.showAndWait();
        FacturaImpresa();


        newlaunch();
        }
    }

    public void newlaunch() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaFactura.fxml"));
        stage.setTitle("Factura");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

    public void CrearFactura(){
        conn = connect.conDB();
        String sql = "insert into facturat(fechaFactura, IDCliente, IDEmpleado, totalFactura, impuesto, subtotalFactura)values(now(),?,?,?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(txt_IDCliente.getText()));
            pst.setInt(2, comEmpleados.getSelectionModel().getSelectedItem().getIdEmpleado());
            pst.setDouble(3, 0.0);
            pst.setDouble(4, 0.0);
            pst.setDouble(5, 0.0);

            pst.execute();
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
            updatecampos();
            clearmini();

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
        listF = connect.getDataDetalleFacturat(ID);
        tablaFactura.setItems(listF);
    }

    public void UpdateTableC(){
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
        indexF = tableInventario.getSelectionModel().getSelectedIndex();
        if(indexF <= -1){

            return;
        }

        productselected = ColID.getCellData(indexF).toString();
        txt_IDP.setText(ColID.getCellData(indexF).toString());
        txt_precio.setText(ColPrecio.getCellData(indexF).toString());
        txt_stock.setText(ColStock.getCellData(indexF).toString());
        txt_cantidad.clear();
        checkBtnStatus(0);
    }

    @FXML
    public void getSelectedF(javafx.scene.input.MouseEvent event) {
        indexF = tablaFactura.getSelectionModel().getSelectedIndex();
        if(indexF <= -1){

            return;
        }
        id4Delete = ColIDF.getCellData(indexF).toString();
        txt_IDP.setText(ColIDP.getCellData(indexF).toString());
        txt_precio.setText(ColPrecioF.getCellData(indexF).toString());
        txt_cantidad.setText(ColCantidad.getCellData(indexF).toString());
        checkBtnStatus(1);

    }

    @FXML
    public void getSelectedC(javafx.scene.input.MouseEvent event) {
        indexF = table_clientes.getSelectionModel().getSelectedIndex();
        if(indexF <= -1){

            return;
        }

        txt_IDCliente.setText(col_cliente.getCellData(indexF).toString());
        txt_nombreCliente.setText(col_nombre.getCellData(indexF));
        btn_seleccionarCliente.setVisible(false);
        txt_IDCliente.setVisible(true);
        txt_nombreCliente.setVisible(true);
        txt_search.setPromptText("Buscar Producto");
        hideInventario(false);
        tableInventario.setDisable(false);
        btn_agregar.setDisable(true);
        btn_actualizar.setDisable(true);
        btn_eliminar.setDisable(true);

    }

    @FXML
    void Search_producto(){
        ColID.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        ColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        dataListI = connect.getdatainventario();
        tableInventario.setItems(dataListI);
        FilteredList<inventario> filteredData = new FilteredList<>(dataListI, b -> true);
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







    //////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Object TxtIDCompra;
    @FXML
    private TableView<pago> tablaPago;
    @FXML
    private TableColumn<pago, Integer> col_IDPago;
    @FXML
    private TableColumn<pago, TipoPago> col_desTipoPago;
    @FXML
    private TableColumn<pago, Double> col_cantidadPagada;
    @FXML
    private TableColumn<pago, Double> col_porcentajePagado;
    @FXML
    private TableColumn<pago, Double> col_totalPago;

    @FXML
    private TextField txtIDTP;
    @FXML
    private ComboBox<TipoPago> CBXTP;
    @FXML
    private TextField txtCantidadPagada;
    @FXML
    private TextField txtProcentajeP;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField fieldFilter;
    @FXML
    private TextField txtNumTarje;
    @FXML
    private TextField txtCodST;
    @FXML
    private DatePicker DataFT;
    @FXML
    private TextField txtNPT;
    @FXML
    private TextField txt_eliminarPago;
    @FXML
    private TextField txt_totalacumulado;
    @FXML
    private TextField txt_totalpagar;


    @FXML
    private Tab pago;




    private ObservableList<pago> listPA;
    private ObservableList<TipoPago> listTP = TipoPago.getdataTP();
    private ObservableList<pago> dataListPA;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

    int index = -1;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst4 = null;
    int IDPago = 0;
    int generado;


    public static int value(int value) {
        TxtIDCompra = value;
        return value;
    }

    public void intCombox() {
        CBXTP.setItems(listTP);
    }

    public void add_pago() {
        conn = connect.conDB();

        if(generado == 0 ){

            try {
                pst2 = conn.prepareStatement("insert into pagofacturat (IDFactura,totalPago) values(?,?)");
                pst2.setInt(1, ID);
                pst2.setString(2, txtTotal.getText());
                pst2.execute();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó el pago exitosamente");
                alert.showAndWait();
                UpdateTable();
                generado = 1;
                btn_finalizarFactura.setDisable(false);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }


        if (CBXTP.getValue().getIDTipoPago() == 2) {
            if (validateFieldsPT()  & validateCantidad() & validateTotal() & validateCodTarjeta() & validateNumeroTarjeta() & validateName()) {
                try {
                    pst = conn.prepareStatement("insert into tarjeta (CODSEGTARJETA, numeroDeTarjeta, nombrePropietarioTarjeta, fechaExpiracion) values (?,?,?,?)");
                    pst.setString(1, txtCodST.getText());
                    pst.setString(2, txtNumTarje.getText());
                    pst.setString(3, txtNPT.getText());
                    pst.setString(4, DataFT.getValue().format(formatter));
                    pst.execute();
                    UpdateTable();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    pst1 = conn.prepareStatement("insert into detalledepagofacturat(IDPago,cantidadPagada, IDTipoPago, CODSEGTARJETA ) values (?,?,?,?)");
                    pst1.setInt(1, ID);
                    pst1.setString(2, txtCantidadPagada.getText());
                    pst1.setString(3, String.valueOf(CBXTP.getSelectionModel().getSelectedItem().getIDTipoPago()));
                    pst1.setString(4, txtCodST.getText());
                    pst1.execute();
                    UpdateTable();
                    updatecampospago();
                    btn_finalizarFactura.setDisable(false);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        if (CBXTP.getValue().getIDTipoPago() == 1) {
            if (validateFieldsPE() /*& limite()*/ & validateCantidad()  & validateTotal()) {
                try {
                    pst3 = conn.prepareStatement("insert into detalledepagofacturat (IDPago,cantidadPagada,IDTipoPago) values (?,?,?)");
                    pst3.setInt(1, ID);
                    pst3.setString(2, txtCantidadPagada.getText());
                    pst3.setString(3, String.valueOf(CBXTP.getSelectionModel().getSelectedItem().getIDTipoPago()));
                    pst3.execute();
                    btn_finalizarFactura.setDisable(false);
                    UpdateTable();
                    updatecampospago();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    public void UpdateTable() {
        col_IDPago.setCellValueFactory(new PropertyValueFactory<>("IDPago"));
        col_desTipoPago.setCellValueFactory(new PropertyValueFactory<>("desTipoPago"));
        col_cantidadPagada.setCellValueFactory(new PropertyValueFactory<>("cantidadPagada"));
        col_porcentajePagado.setCellValueFactory(new PropertyValueFactory<>("porcentajePagado"));
        col_totalPago.setCellValueFactory(new PropertyValueFactory<>("totalPago"));
        listPA = connect.getdatapagoF(ID);
        tablaPago.setItems(listPA);
    }


    public void DeleteDetallePago(){
        conn = connect.conDB();
        try {
            String sql = "DELETE from detalledepagofacturat WHERE IDDetalleDePago = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, txt_eliminarPago.getText());
            pst.execute();
            UpdateTable();
            updatecampospago();

        }catch (Exception e){
        }


    }


    void Search_pago() {
        col_IDPago.setCellValueFactory(new PropertyValueFactory<>("IDPago"));

        dataListPA = connect.getdatapagoF((Integer) TxtIDCompra);
        tablaPago.setItems(dataListPA);
        FilteredList<pago> filteredDataPago = new FilteredList<>(dataListPA, b -> true);
        fieldFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataPago.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(person.getIDPago()).contains(lowerCaseFilter))
                    return true;

                else
                    return false;
            });
        });
        SortedList<pago> sortedData = new SortedList<>(filteredDataPago);
        sortedData.comparatorProperty().bind(tablaPago.comparatorProperty());
        tablaPago.setItems(sortedData);
    }

    @FXML
    private void clearFields() {

        prueba4();
        txtNumTarje.clear();
        txtNPT.clear();
        txtCodST.clear();
        DataFT.setDisable(true);
        btnRegistar.setDisable(false);
        btn_finalizarFactura.setDisable(false);
        btn_limpiar.setDisable(true);
        txtCantidadPagada.clear();

    }

    @FXML
    public void getSelectedPago(MouseEvent event) {
        index = tablaPago.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txt_eliminarPago.setText(col_IDPago.getCellData(index).toString());

    }
    //Validaciones

    private boolean validateCantidad() {
        Pattern p = Pattern.compile("[0-9]+(.[0-9]+)");
        Matcher m = p.matcher(txtCantidadPagada.getText().trim());

        if (m.find() && m.group().equals(txtCantidadPagada.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Cantidad");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta numeros decimales");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validatePorcentaje() {
        Pattern p = Pattern.compile("[0-9]{1,3}");
        Matcher m = p.matcher(txtProcentajeP.getText().trim());


        if (m.find() && m.group().equals(txtProcentajeP.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Porcentaje");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta enteros" +
                    " \nQue el porcentaje contenga maximo 3 digitos ");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateTotal() {
        Pattern p = Pattern.compile("[0-9]+(.[0-9]+)");
        Matcher m = p.matcher(txtTotal.getText().trim());

        if (m.find() && m.group().equals(txtTotal.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Total");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta numeros decimales");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateCodTarjeta() {
        Pattern p = Pattern.compile("[0-9]{3}");
        Matcher m = p.matcher(txtCodST.getText().trim());


        if (m.find() && m.group().equals(txtCodST.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Codigo de tarjeta");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta enteros" +
                    " \nQue el codigo de la tarjeta contenga 3 digitos ");
            alert.showAndWait();
            return false;
        }
    }



    private boolean validateNumeroTarjeta() {
        Pattern p = Pattern.compile("[0-9]{16}");
        Matcher m = p.matcher(txtNumTarje.getText().trim());


        if (m.find() && m.group().equals(txtNumTarje.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Numero de tarjeta");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta enteros" +
                    " \nQue el numero de la tarjeta contenga 16 digitos ");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateName() {
        Pattern p = Pattern.compile("^([A-Z]{1}[a-z]+[ ]*){2,4}$");
        Matcher m = p.matcher(txtNPT.getText());


        if (m.find() && m.group().equals(txtNPT.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar nombre");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente información: " +
                    " \nDeberá escribir un nombre que contenga:" +
                    " \nPrimera letra mayúscula" +
                    " \nAl menos un apellido" +
                    " \nEste campo solo letras");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateFieldsPT() {
        if (CBXTP.getSelectionModel().isEmpty() | txtCantidadPagada.getText().isEmpty() | txtTotal.getText().isEmpty() | txtCodST.getText().isEmpty() | txtNumTarje.getText().isEmpty() | txtNPT.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }
    private boolean validateFieldsPE() {
        if (CBXTP.getSelectionModel().isEmpty() | txtCantidadPagada.getText().isEmpty() | txtTotal.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }

    private boolean validateCantidadE() {
        Pattern p = Pattern.compile("[0-9]+(.[0-9]+)");
        Matcher m = p.matcher(txtCantidadPagada.getText().trim());

        if (m.find() && m.group().equals(txtCantidadPagada.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Cantidad");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \n-Este campo solo acepta numeros decimales");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validatePorcentajeE() {
        Pattern p = Pattern.compile("[0-9]{1,3}");
        Matcher m = p.matcher(txtProcentajeP.getText().trim());


        if (m.find() && m.group().equals(txtProcentajeP.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Porcentaje");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta enteros" +
                    " \nQue el porcentaje contenga maximo 3 digitos ");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateTotalE() {
        Pattern p = Pattern.compile("[0-9]+(.[0-9]+)");
        Matcher m = p.matcher(txtTotal.getText().trim());

        if (m.find() && m.group().equals(txtTotal.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Total");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \n-Este campo solo acepta numeros decimales");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateFieldsE() {
        if (txtCantidadPagada.getText().isEmpty() | txtProcentajeP.getText().isEmpty() | txtTotal.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }


    public void prueba4(){

        CBXTP.valueProperty().addListener((ov, p1, p2) -> {
            if(p2.getIDTipoPago() == 1 ){
                txtNumTarje.setDisable(true);
                txtNPT.setDisable(true);
                txtCodST.setDisable(true);
                DataFT.setDisable(true);
                txtCantidadPagada.setDisable(false);
                btnRegistar.setDisable(false);

                btn_limpiar.setDisable(false);


            }else{
                txtNumTarje.setDisable(false);
                txtNPT.setDisable(false);
                txtCodST.setDisable(false);
                DataFT.setDisable(false);
                txtCantidadPagada.setDisable(false);
                btnRegistar.setDisable(false);

                btn_limpiar.setDisable(false);
            }
        });
    }

    public void updatecampospago(){
        factura2 = connect.getDataFacturat();
        txt_subTotal.setText(factura2.get(ID-1).getSubtotalFactura());
        txt_isv.setText(factura2.get(ID-1).getImpuesto());
        txt_total.setText(factura2.get(ID-1).getTotalFactura());
        txt_fecha.setText(factura2.get(ID-1).getFechaFactura());

        ObservableList<String> hola = connect.pagoAcumulado(ID);
        txt_totalacumulado.setText(hola.get(0));
    }

    public void FacturaImpresa() throws JRException, ClassNotFoundException, SQLException {

        JasperReport reporte;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria", "root", "");
            reporte = JasperCompileManager.compileReport("src/Blank_Letter.jrxml");
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("IDFacturaParameter", ID);

            JasperPrint jp = JasperFillManager.fillReport(reporte, parameters, conn);

            JasperViewer.viewReport(jp, false);
        }catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, "Ocurrio este error " + e.getMessage() );
        }
    }
}



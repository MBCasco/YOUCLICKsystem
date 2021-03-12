package Controller;

import ComboBoxController.TipoPago;
import Models.pago;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PagoController extends MenuController implements Initializable {
    private static Object TxtIDCompra;

    @FXML
    private TableView<pago> tablaPago;
    @FXML
    private TableColumn<pago, Integer> col_IDPago;
    @FXML
    private TableColumn<pago, Integer> col_IDCompra;
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

    private static Object txtTotal2;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField fieldDelete;
    @FXML
    private TextField txtNumTarje;
    @FXML
    private TextField txtCodST;
    //@FXML
    //private DatePicker DataFT;
    @FXML
    private TextField txtFecha;
    @FXML
    private TextField txtNPT;

    private ObservableList<pago> listPA;
    private ObservableList<TipoPago> listTP = TipoPago.getdataTP();
    private ObservableList<pago> dataListPA;
    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

    int index = -1;
    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst4 = null;
    String id4Delete = null;
    int ID;
    final Calendar calendar = Calendar.getInstance();
    final java.util.Date  date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(date);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intCombox();
        UpdateTable();
        prueba4();
        updatetotal();
        txtCantidadPagada.setDisable(true);
        txtNumTarje.setDisable(true);
        txtNPT.setDisable(true);
        txtCodST.setDisable(true);
        //ataFT.setDisable(true);
        txtFecha.setDisable(true);
        ID = 0;
    }

    public static String value2 (String value){
        txtTotal2 = value;
        return value;
    }

    public static int value3 (int value){
        TxtIDCompra = value;
        return value;
    }

    public void updatetotal (){
        txtTotal.setText(String.valueOf(txtTotal2));
    }

    public void pruebaP() throws IOException {
        compra();
    }

    public static int value(int value) {
        TxtIDCompra = value;
        System.out.println(TxtIDCompra);
        return value;
    }

    public void intCombox() {
        CBXTP.setItems(listTP);
    }

    public void add_pago() {
        conn = connect.conDB();
        if (CBXTP.getValue().getIDTipoPago() == 2) {
            if (validateFieldsPT()  & validateCantidad() & validateTotal() & validateCodTarjeta() & validateNumeroTarjeta() & validateName()) {
                try {
                    pst = conn.prepareStatement("insert into tarjeta (CODSEGTARJETA, numeroDeTarjeta, nombrePropietarioTarjeta, fechaExpiracion) values (?,?,?,?)");
                    pst.setString(1, txtCodST.getText());
                    pst.setString(2, txtNumTarje.getText());
                    pst.setString(3, txtNPT.getText());
                    //pst.setString(4, DataFT.getValue().format(formatter));
                    pst.setString(4, txtFecha.getText());
                    pst.execute();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    pst = conn.prepareStatement("insert into detalledepagocomprat (IDPago,cantidadPagada, porcentajePagado, IDTipoPago, CODSEGTARJETA) values (?,?,?,?,?)");
                    pst.setString(1, TxtIDCompra.toString());
                    pst.setString(2, txtCantidadPagada.getText());
                    pst.setDouble(3, 0.0);
                    pst.setString(4, String.valueOf(CBXTP.getSelectionModel().getSelectedItem().getIDTipoPago()));
                    pst.setString(5, txtCodST.getText());
                    pst.execute();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    pst = conn.prepareStatement("insert into pagocomprat (IDCompra,totalPago) values(?,?)");
                    pst.setString(1, TxtIDCompra.toString());
                    pst.setString(2, txtTotal.getText());
                    pst.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Se agregó exitosamente");
                    alert.showAndWait();
                    UpdateTable();

                } catch (Exception e) {
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\PAGO_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.SEVERE);
                        myLog.logger.severe(e.getMessage() + " : " + e.getCause());
                    } catch (IOException ex) {
                        Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        if (CBXTP.getValue().getIDTipoPago() == 1) {
            if (validateFieldsPE() /*& limite()*/ & validateCantidad()  & validateTotal()) {
                try {
                    pst = conn.prepareStatement("insert into detalledepagocomprat (IDPago,cantidadPagada, porcentajePagado, IDTipoPago) values (?,?,?,?)");
                    pst.setString(1, TxtIDCompra.toString());
                    pst.setString(2, txtCantidadPagada.getText());
                    pst.setDouble(3, 0.0);
                    pst.setString(4, String.valueOf(CBXTP.getSelectionModel().getSelectedItem().getIDTipoPago()));
                    pst.execute();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    pst = conn.prepareStatement("insert into pagocomprat (IDCompra,totalPago) values(?,?)");
                    pst.setString(1, TxtIDCompra.toString());
                    pst.setString(2, txtTotal.getText());
                    pst.execute();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setHeaderText(null);
                    alert.setContentText("Se agregó el pago exitosamente");
                    alert.showAndWait();
                    UpdateTable();

                } catch (Exception e) {
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\PAGO_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.SEVERE);
                        myLog.logger.severe(e.getMessage() + " : " + e.getCause());
                    } catch (IOException ex) {
                        Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

        listPA = connect.getdatapago((Integer) TxtIDCompra);
        tablaPago.setItems(listPA);
    }

    public void deleteP(){
        Toolkit.getDefaultToolkit().beep();
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro ¿Qué quieres eliminar esta pago?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                conn = connect.conDB();
                try {
                    String Ssql = "DELETE FROM detalledepagocomprat WHERE IDPago = ?";
                    PreparedStatement prest = conn.prepareStatement(Ssql);
                    prest.setString(1, id4Delete);

                    if (prest.executeUpdate() > 0) {
                        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert1.setTitle("Confirmación");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Se elimino el pago con éxito");
                        alert1.showAndWait();
                        UpdateTable();
                    }
                } catch (Exception e) {
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\PAGO_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.SEVERE);
                        myLog.logger.severe(e.getMessage() + " : " + e.getCause());
                    } catch (IOException ex) {
                        Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @FXML
    private void clearFields() {
        txtCantidadPagada.clear();
        //txtProcentajeP.clear();
        txtNumTarje.clear();
        txtCodST.clear();
        txtNPT.clear();
        fieldDelete.clear();
        //DataFT.setValue(null);
        txtFecha.clear();
    }

    @FXML
    public void getSelectedPago(MouseEvent event) {
        index = tablaPago.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        id4Delete = col_IDPago.getCellData(index).toString();

        txtCantidadPagada.setText(col_cantidadPagada.getCellData(index).toString());
        txtTotal.setText(col_totalPago.getCellData(index).toString());
    }

    public void prueba4(){

        CBXTP.valueProperty().addListener((ov, p1, p2) -> {
            if(p2.getIDTipoPago() == 1 ){
                txtCantidadPagada.setDisable(false);
                txtNumTarje.setDisable(true);
                txtNPT.setDisable(true);
                txtCodST.setDisable(true);
                //DataFT.setDisable(true);
                txtFecha.setDisable(true);

            }else{
                txtCantidadPagada.setDisable(false);
                txtNumTarje.setDisable(false);
                txtNPT.setDisable(false);
                txtCodST.setDisable(false);
                //DataFT.setDisable(false);
                txtFecha.setDisable(false);
            }
        });
    }

    public void trasferirp(){
        String sql2 = "insert into detalledepagocompra select * from detalledepagocomprat where IDPago = ?";
        try {
            pst = conn.prepareStatement(sql2);
            pst.setString(1, TxtIDCompra.toString());
            pst.execute();
            System.out.println(TxtIDCompra.toString());

        } catch (Exception e) {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
        }

        String sql1 = "insert into pagocompra select * from pagocomprat where IDPago = ?";
        try {
            pst = conn.prepareStatement(sql1);
            pst.setString(1, TxtIDCompra.toString());
            pst.execute();
            System.out.println(TxtIDCompra.toString());
            System.out.println("entrada");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setContentText("Su pago fue terminado con exito");
            alert.showAndWait();
            compra();

        } catch (Exception e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\PAGO_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.SEVERE);
                myLog.logger.severe(e.getMessage() + " : " + e.getCause());
            } catch (IOException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /*
        VALIDACIONES
     */
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
        Pattern p = Pattern.compile("[0-9]{3,4}");
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
        Pattern p = Pattern.compile("^([A-Z]{1}[a-z ñáéíóú]+[ ]*){2,4}$");
        Matcher m = p.matcher(txtNPT.getText());


        if (m.find() && m.group().equals(txtNPT.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
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
        if (txtCantidadPagada.getText().isEmpty() | txtTotal.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }
}



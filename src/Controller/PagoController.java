package Controller;

import ComboBoxController.TipoPago;
import Models.pago;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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


    private ObservableList<pago> listPA;
    private ObservableList<TipoPago> listTP = TipoPago.getdataTP();
    private ObservableList<pago> dataListPA;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");

    int index = -1;
    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    PreparedStatement pst3 = null;
    PreparedStatement pst4 = null;
    int IDPago = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intCombox();
        UpdateTable();
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
            if (validateFieldsPT() /*& limite()*/ & validateCantidad() & validatePorcentaje() & validateTotal() & validateCodTarjeta() & validateNumeroTarjeta() & validateName()) {
                try {
                    pst = conn.prepareStatement("insert into tarjeta (CODSEGTARJETA, numeroDeTarjeta, nombrePropietarioTarjeta, fechaExpiracion) values (?,?,?,?)");
                    pst.setString(1, txtCodST.getText());
                    pst.setString(2, txtNumTarje.getText());
                    pst.setString(3, txtNPT.getText());
                    pst.setString(4, DataFT.getValue().format(formatter));
                    pst.execute();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    pst1 = conn.prepareStatement("insert into detalledepago(cantidadPagada, porcentajePagado, IDTipoPago, CODSEGTARJETA) values (?,?,?,?)");
                    pst1.setString(1, txtCantidadPagada.getText());
                    pst1.setString(2, txtProcentajeP.getText());
                    pst1.setString(3, String.valueOf(CBXTP.getSelectionModel().getSelectedItem().getIDTipoPago()));
                    pst1.setString(4, txtCodST.getText());
                    pst1.execute();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    pst2 = conn.prepareStatement("insert into pago (IDCompra, totalPago, IDDetalleDePago) values(?,?,LAST_INSERT_ID())");
                    pst2.setString(1, TxtIDCompra.toString());
                    pst2.setString(2, txtTotal.getText());
                    pst2.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Se agregó exitosamente");
                    alert.showAndWait();
                    Search_pago();
                    UpdateTable();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }

        if (CBXTP.getValue().getIDTipoPago() == 1) {
            if (validateFieldsPE() /*& limite()*/ & validateCantidad() & validatePorcentaje() & validateTotal()) {
                try {
                    pst3 = conn.prepareStatement("insert into detalledepago (cantidadPagada, porcentajePagado, IDTipoPago) values (?,?,?)");
                    pst3.setString(1, txtCantidadPagada.getText());
                    pst3.setString(2, txtProcentajeP.getText());
                    pst3.setString(3, String.valueOf(CBXTP.getSelectionModel().getSelectedItem().getIDTipoPago()));
                    pst3.execute();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    pst4 = conn.prepareStatement("insert into pago (IDCompra, totalPago, IDDetalleDePago) values(?,?,LAST_INSERT_ID())");
                    pst4.setString(1, TxtIDCompra.toString());
                    pst4.setString(2, txtTotal.getText());
                    pst4.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setHeaderText(null);
                    alert.setContentText("Se agregó exitosamente");
                    alert.showAndWait();
                    Search_pago();
                    UpdateTable();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        }
    }


    public void UpdateTable() {
        col_IDPago.setCellValueFactory(new PropertyValueFactory<>("IDPago"));
        col_IDCompra.setCellValueFactory(new PropertyValueFactory<>("IDCompra"));
        col_desTipoPago.setCellValueFactory(new PropertyValueFactory<>("desTipoPago"));
        col_cantidadPagada.setCellValueFactory(new PropertyValueFactory<>("cantidadPagada"));
        col_porcentajePagado.setCellValueFactory(new PropertyValueFactory<>("porcentajePagado"));
        col_totalPago.setCellValueFactory(new PropertyValueFactory<>("totalPago"));

        listPA = connect.getdatapago((Integer) TxtIDCompra);
        tablaPago.setItems(listPA);
    }


    void Search_pago() {
        col_IDPago.setCellValueFactory(new PropertyValueFactory<>("IDPago"));

        dataListPA = connect.getdatapago((Integer) TxtIDCompra);
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
        txtIDTP.clear();
        txtCantidadPagada.clear();
        txtProcentajeP.clear();
        txtTotal.clear();
        CBXTP.setValue(null);
        fieldFilter.clear();

    }

    @FXML
    public void getSelectedPago(MouseEvent event) {
        index = tablaPago.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        txtIDTP.setText(col_IDPago.getCellData(index).toString());
        CBXTP.setValue(col_desTipoPago.getCellData(index));
        txtCantidadPagada.setText(col_cantidadPagada.getCellData(index).toString());
        txtProcentajeP.setText(col_porcentajePagado.getCellData(index).toString());
        txtTotal.setText(col_totalPago.getCellData(index).toString());

    }
    //Validaciones

    private boolean validateCantidad() {
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Total");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \n-Este campo solo acepta numeros decimales");
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
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
            alert.setContentText("Por favor ingresar un nombre válido \n" +
                    " Deberá escribir un nombre que contenga:\n" +
                    " - Primera letra mayúscula\n" +
                    " - Al menos un apellido");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateFieldsPT() {
        if (CBXTP.getSelectionModel().isEmpty() | txtCantidadPagada.getText().isEmpty() | txtProcentajeP.getText().isEmpty() | txtTotal.getText().isEmpty() | txtCodST.getText().isEmpty() | txtNumTarje.getText().isEmpty() | txtNPT.getText().isEmpty()) {

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
        if (CBXTP.getSelectionModel().isEmpty() | txtCantidadPagada.getText().isEmpty() | txtProcentajeP.getText().isEmpty() | txtTotal.getText().isEmpty()) {

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
}

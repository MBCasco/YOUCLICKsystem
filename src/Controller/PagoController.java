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
    private TableColumn<pago,Double> col_porcentajePagado;
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

    public static int value (int value){
        TxtIDCompra = value;
        System.out.println(TxtIDCompra);
        return value;
    }
    public void intCombox() {
        CBXTP.setItems(listTP);
    }

    public void add_pago(){
        conn = connect.conDB();
        //if (validateFields() & limite() & validateName() & validateDetalles()  & validateNumber() & validateEmail() ) {{
        if(CBXTP.getValue().getIDTipoPago() == 2){
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

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó exitosamente");
                alert.showAndWait();
                Search_pago();
                UpdateTable();

            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }else{

        }
        if(CBXTP.getValue().getIDTipoPago() == 1){
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

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó exitosamente");
                alert.showAndWait();
                Search_pago();
                UpdateTable();

            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }


    public void UpdateTable(){
        col_IDPago.setCellValueFactory(new PropertyValueFactory<>("IDPago"));
        col_IDCompra.setCellValueFactory(new PropertyValueFactory<>("IDCompra"));
        col_desTipoPago.setCellValueFactory(new PropertyValueFactory<>("desTipoPago"));
        col_cantidadPagada.setCellValueFactory(new PropertyValueFactory<>("cantidadPagada"));
        col_porcentajePagado.setCellValueFactory(new PropertyValueFactory<>("porcentajePagado"));
        col_totalPago.setCellValueFactory(new PropertyValueFactory<>("totalPago"));

        listPA = connect.getdatapago((Integer) TxtIDCompra);
        tablaPago.setItems(listPA);
    }


    void Search_pago(){
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
        if(index <= -1){
            return;
        }

        txtIDTP.setText(col_IDPago.getCellData(index).toString());
        CBXTP.setValue(col_desTipoPago.getCellData(index));
        txtCantidadPagada.setText(col_cantidadPagada.getCellData(index).toString());
        txtProcentajeP.setText(col_porcentajePagado.getCellData(index).toString());
        txtTotal.setText(col_totalPago.getCellData(index).toString());

}
}

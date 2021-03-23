package Controller;

import Controller.connect;
import Models.proveedores;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProveedoresController extends MenuController implements Initializable {

    @FXML
    private TableView<proveedores> table_proveedores;
    @FXML
    private TableColumn<proveedores, Integer> col_id;
    @FXML
    private TableColumn<proveedores, String> col_nombre;
    @FXML
    private TableColumn<proveedores, String> col_correo;
    @FXML
    private TableColumn<proveedores, String> col_direccion;

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_correo;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_idProveedor;
    @FXML
    private TextField filterField;
    @FXML
    private TextField txt_eliminar;
    @FXML
    private Button btn_registrar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_clearFields;
    @FXML
    private Tab tab_contacto;


    ObservableList<proveedores> listM;
    ObservableList<proveedores> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    final Calendar calendar = Calendar.getInstance();
    final java.util.Date  date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(date);

    public void prueba() throws IOException {
        contactos();

    }

    public void Add_proveedor(){
        conn = connect.conDB();
        String sql = "insert into proveedores(empresaProveedor,correoProveedor,direccionProveedor)values(?,?,?)";
        if (validateFields() & limite() & validateName() & validateEmail() & validateDireccion() ) {

            if( existeCorreo() & existeNombre()){
                return;
            }


            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_nombre.getText());
                pst.setString(2, txt_correo.getText());
                pst.setString(3, txt_direccion.getText());
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó proveedor exitosamente");
                alert.showAndWait();

                UpdateTable();
                Search_proveedor();
                clearFields();

            } catch (SQLException e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\PROVEEDORES_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe( e.getMessage() + " Causado por: " + e.getCause());
                } catch (IOException ex) {
                    Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @FXML
    private void clearFields() {
        txt_idProveedor.clear();
        txt_nombre.clear();
        txt_correo.clear();
        txt_direccion.clear();
        txt_eliminar.clear();
        checkBtnStatus(0);

    }

    public void Delete(){

        Toolkit.getDefaultToolkit().beep();
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro ¿Qué quieres eliminar este proveedor?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                conn = connect.conDB();
                try {
                    String Ssql = "DELETE FROM PROVEEDORES WHERE IDProveedor = ?";
                    PreparedStatement prest = conn.prepareStatement(Ssql);
                    prest.setString(1, txt_eliminar.getText());

                    if (prest.executeUpdate() > 0){
                        Alert alert1 =new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Elimidado");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Se elimino proveedor con éxito");
                        alert1.showAndWait();
                        UpdateTable();
                        clearFields();
                    }
                }catch (SQLException e){
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\PROVEEDORES_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.SEVERE);
                        myLog.logger.severe( e.getMessage() + " Causado por: " + e.getCause());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombreP"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correoP"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccionP"));

        listM = connect.getdataproveedor();
        table_proveedores.setItems(listM);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        UpdateTable();
        Search_proveedor();
        clearFields();
        checkBtnStatus(0);
    }

    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_registrar.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
            tab_contacto.setDisable(true);
        }
        if (check == 0){
            btn_registrar.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
            tab_contacto.setDisable(true);
        }
    }

    public void Edit(){

        if (validateName() & validateEmail() & validateDireccion() & validateFields() & limite()) {

            try {
                conn = connect.conDB();
                String value1 = txt_idProveedor.getText();
                String value2 = txt_nombre.getText();
                String value3 = txt_correo.getText();
                String value4 = txt_direccion.getText();

                String sql = "update PROVEEDORES set empresProveedor= '" + value2 + "', correoProveedor= '" + value3 + "', direccionProveedor= '" + value4 + "' where IDProveedor='" + value1 + "' ";
                pst = conn.prepareStatement(sql);
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se actualizó proveedor exitosamente");
                alert.showAndWait();

                UpdateTable();
                Search_proveedor();
                clearFields();

            } catch (SQLException e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\PROVEEDORES_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe( e.getMessage() + " Causado por: " + e.getCause());
                } catch (IOException ex) {
                    Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    @FXML
    void Search_proveedor(){
        col_id.setCellValueFactory(new PropertyValueFactory<proveedores,Integer>("idProveedor"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<proveedores,String>("nombreP"));

        dataList = connect.getdataproveedor();
        table_proveedores.setItems(dataList);
        FilteredList<proveedores> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNombreP().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (String.valueOf(person.getIdProveedor()).indexOf(lowerCaseFilter)!=-1)
                    return true;

                else
                    return false;
            });
        });
        SortedList<proveedores> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_proveedores.comparatorProperty());
        table_proveedores.setItems(sortedData);
    }

    @FXML
    public void getSelected(javafx.scene.input.MouseEvent event) {
        index = table_proveedores.getSelectionModel().getSelectedIndex();
        if(index <= -1){

            return;
        }
        ProveedoresContactoController.value(col_id.getCellData(index));

        txt_idProveedor.setText(col_id.getCellData(index).toString());
        txt_nombre.setText(col_nombre.getCellData(index));
        txt_correo.setText(col_correo.getCellData(index));
        txt_direccion.setText(col_direccion.getCellData(index));
        txt_eliminar.setText(col_id.getCellData(index).toString());
        checkBtnStatus(1);

    }

    public void reporteProveedor() throws JRException, ClassNotFoundException, SQLException {

        JasperReport reporte;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria", "root", "");
            reporte = JasperCompileManager.compileReport("src/Reportes/Proveedor.jrxml");
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("proveedorParameter", 1);

            JasperPrint jp = JasperFillManager.fillReport(reporte, parameters, conn);

            JasperViewer.viewReport(jp, false);
        }catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, "Ocurrio este error " + e.getMessage() );
        }
    }

    /*
        VALIDACIONES
     */
    private boolean validateName(){
        Pattern p = Pattern.compile("[A-Za-z 0-9 ñáéíóú]+");
        Matcher m = p.matcher(txt_nombre.getText());

        if(m.find() && m.group().equals(txt_nombre.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Nombre");
            alert.setHeaderText(null);
            alert.setContentText("Deberá escribir un nombre que contenga: " +
                    " \nEste campo solo acepta letras y numeros");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateEmail(){
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(txt_correo.getText());

        if(m.find() && m.group().equals(txt_correo.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Correo");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente información: " +
                    " \nIngresar un correo valido: " +
                    " \nejemplo@gmail.com");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateDireccion(){
        Pattern p = Pattern.compile("[A-Za-z 0-9]+");
        Matcher m = p.matcher(txt_direccion.getText());

        if(m.find() && m.group().equals(txt_direccion.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar direcion");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente información: " +
                    " \nIngresar un correo valido: " +
                    " \nejemplo@gmail.com");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateFields(){
        if (txt_nombre.getText().isEmpty() | txt_correo.getText().isEmpty() | txt_direccion.getText().isEmpty()){

            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }

    private boolean limite(){
        if(txt_nombre.getText().length() >= 35){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supera Limite Permitido");
            alert.setHeaderText("Error");
            alert.setContentText("Supero el Limite de caracteres.+" +
                    " \n El limite de caracteres es de 35");
            alert.showAndWait();
            return false;
        }
        if(txt_direccion.getText().length() >= 50){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supera Limite Permitido");
            alert.setHeaderText("Error");
            alert.setContentText("Supero el Limite de caracteres.+" +
                    " \n El limite de caracteres es de 50");
            alert.showAndWait();
            return false;
        }
        return true;

    }

    private boolean existeCorreo() {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from proveedores where correoProveedor = '" +txt_correo.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("El correo que ingresó ¡Ya existe!");
                alert.setHeaderText(null);
                alert.setContentText("El correo: " + txt_correo.getText() + " ya existe");
                alert.show();
                return true;
            } else {
                return false;
            }

            } catch (Exception e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\PROVEEDORES_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.SEVERE);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (Exception ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    private boolean existeNombre() {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from proveedores where empresaProveedor = '" + txt_nombre.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("El nombre que ingresó ¡Ya existe!");
                alert.setHeaderText(null);
                alert.setContentText("El nombre: " + txt_nombre.getText() + " ya existe");
                alert.show();
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\PROVEEDORES_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.SEVERE);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (Exception ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

}

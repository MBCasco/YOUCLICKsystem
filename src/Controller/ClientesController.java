package Controller;

import Models.clientes;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientesController extends MenuController implements Initializable {
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
    private TextField txt_nombre;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_correo;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField filterField;
    @FXML
    private TextField txt_eliminar;
    @FXML
    private ComboBox<String> Sexo;

    @FXML
    private Button btn_registrar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_reporte;
    @FXML
    private Button btn_clear;



    int value;


    ObservableList<clientes> listM;
    ObservableList<clientes> dataList;
    ObservableList<String> listsexo = connect.getdatasexo();

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    final Calendar calendar = Calendar.getInstance();
    final java.util.Date  date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(date);


    private void habilitar(){

        conn = connect.conDB();

        try {
            String sql = "select * from acceso where FuncionesClientes";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){

                if(rs.getString("FuncionesClientes").contains("R")){
                    btn_registrar.setVisible(true);
                }
                if(rs.getString("FuncionesClientes").contains("A")){
                    btn_actualizar.setVisible(true);
                }
                if(rs.getString("FuncionesClientes").contains("E")){
                    btn_eliminar.setVisible(true);
                }
                if(rs.getString("FuncionesClientes").contains("I")){
                    btn_reporte.setVisible(true);
                }

            }
        } catch (SQLException e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\CLIENTES_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (Exception ex) {
                Logger.getLogger(ClientesController.class.getName()).log(Level.ALL, null, ex);
            }
        }
    }


    //Agregar Clientes
    public void Add_clientes() throws IOException {

            conn = connect.conDB();
            String sql = "insert into cliente (nombreCliente,dirreccionCliente,telefonoCLiente,correoCliente,IDSexo)values(?,?,?,?,?)";

            int codS = 1;

            if (validateName() & validateDireccion()  & validateNumber() & validateEmail() & validateFields() & limite()) {

                if (existeTelefono() & existeCorreo()) {
                    return;
                }

                try {
                    pst = conn.prepareStatement(sql);

                    pst.setString(1, txt_nombre.getText());
                    pst.setString(2, txt_direccion.getText());
                    pst.setString(3, txt_telefono.getText());
                    pst.setString(4, txt_correo.getText());

                    if (Sexo.getValue().equals("Femenino")) {
                        codS = 1;
                    } else if (Sexo.getValue().equals("Masculino")) {
                        codS = 2;
                    }
                    pst.setInt(5, codS);
                    pst.execute();


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setHeaderText(null);
                    alert.setContentText("Se agregó cliente exitosamente");
                    alert.showAndWait();

                    clearFields();
                    UpdateTable();
                    Search_cliente();

                } catch (Exception e) {
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\CLIENTES_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.ALL);
                        myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
                    } catch (Exception ex) {
                        Logger.getLogger(ClientesController.class.getName()).log(Level.ALL, null, ex);
                    }
                }

            }

    }

    //Limpiar los campos
    @FXML
    private void clearFields() {
        txt_id.clear();
        txt_nombre.clear();
        txt_direccion.clear();
        txt_correo.clear();
        txt_telefono.clear();
        txt_eliminar.clear();
        Sexo.setValue(null);
        txt_eliminar.clear();
        checkBtnStatus(0);
    }

    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_registrar.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
        }
        if (check == 0){
            btn_registrar.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
        }
    }

    //Eliminar Cliente
    public void Delete(){

            Toolkit.getDefaultToolkit().beep();
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Estás seguro ¿Qué quieres eliminar este cliente?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    conn = connect.conDB();
                    try {
                        String Ssql = "DELETE FROM cliente WHERE IDCliente = ?";
                        PreparedStatement prest = conn.prepareStatement(Ssql);
                        prest.setString(1, txt_eliminar.getText());

                        if (prest.executeUpdate() > 0){
                            Alert alert1 =new Alert(Alert.AlertType.ERROR);
                            alert1.setTitle("Eliminado");
                            alert1.setHeaderText(null);
                            alert1.setContentText("Se eliminò cliente con éxito");
                            alert1.showAndWait();
                            UpdateTable();
                            clearFields();
                        }

                    }catch (Exception e){
                        try {
                            Log myLog;
                            String nombreArchivo = "src\\Log\\CLIENTES_"+fecha+".txt";
                            myLog = new Log(nombreArchivo);
                            myLog.logger.setLevel(Level.ALL);
                            myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
                        } catch (Exception ex) {
                            Logger.getLogger(ClientesController.class.getName()).log(Level.ALL, null, ex);
                        }
                    }
                }
            });

    }

    //Actualizar la tabla
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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Sexo.setItems(listsexo);
        UpdateTable();
        Search_cliente();
        clearFields();
        checkBtnStatus(0);
        habilitar();

    }

    //Editar Clientes
    public void Edit(){
            int codS = 1;
            if (validateFields() & limite() & validateName() & validateDireccion()  & validateNumber() & validateEmail()) {


                try {
                    conn = connect.conDB();

                    String value1 = txt_id.getText();
                    String value2 = txt_nombre.getText();
                    String value3 = txt_direccion.getText();
                    String value4 = txt_telefono.getText();
                    String value5 = txt_correo.getText();

                    if (Sexo.getValue().equals("Femenino")) {
                        codS = 1;
                    } else if (Sexo.getValue().equals("Masculino")) {
                        codS = 2;
                    }
                    int value6 = codS;

                    String sql = ("update cliente set nombreCliente= '" + value2 + "', dirreccionCliente= '" +
                            value3 + "', telefonoCliente= '" + value4 + "', correoCliente= '" + value5 + "', IDSexo= '" + value6 + " ' where IDCliente='" + value1 + "' ");

                    pst = conn.prepareStatement(sql);
                    pst.execute();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmación");
                    alert.setHeaderText(null);
                    alert.setContentText("Se actualizó el cliente exitosamente");
                    alert.showAndWait();

                    UpdateTable();
                    Search_cliente();
                    clearFields();

                } catch (SQLException e) {
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\CLIENTES_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.ALL);
                        myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
                    } catch (Exception ex) {
                        Logger.getLogger(ClientesController.class.getName()).log(Level.ALL, null, ex);
                    }
                }
            }
    }

    //Buscar clientes
    @FXML
    void Search_cliente(){
        col_cliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        dataList = connect.getdataclientes();
        table_clientes.setItems(dataList);
        FilteredList<clientes> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
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

    //Seleccionar en la tabla
    @FXML
    public void getSelected(javafx.scene.input.MouseEvent event) {
        index = table_clientes.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        txt_id.setText(col_cliente.getCellData(index).toString());
        txt_nombre.setText(col_nombre.getCellData(index));
        txt_direccion.setText(col_direccion.getCellData(index));
        txt_telefono.setText(col_telefono.getCellData(index).toString());
        txt_correo.setText(col_correo.getCellData(index));
        Sexo.setValue(col_Sexo.getCellData(index));
        txt_eliminar.setText(String.valueOf(value));
        checkBtnStatus(1);


    }

    public void reporteCli() throws JRException, ClassNotFoundException, SQLException {

        JasperReport reporte;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria", "root", "");
            reporte = JasperCompileManager.compileReport("src/Reportes/Cliente.jrxml");
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("clienteParameter", 1);

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
        Pattern p = Pattern.compile("^([A-Z]{1}[a-z ñáéíóú]+[ ]*){2,4}$");
        //Pattern p = Pattern.compile("^([A-Za-z ñáéíóú]{2,4})$");
        Matcher m = p.matcher(txt_nombre.getText());



        if(m.find() && m.group().equals(txt_nombre.getText()) ){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar nombre");
            alert.setHeaderText(null);
            alert.setContentText("Deberá escribir un nombre que contenga: " +
                    " \nPrimera letra mayúscula" +
                    " \nAl menos un apellido" +
                    " \nEste campo solo letras" +
                    " \nPor ejemplo: Ricardo Reyes");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateNumber(){
        Pattern p = Pattern.compile("[0-9]{8}");
        Matcher m = p.matcher(txt_telefono.getText().trim());
        Pattern  pp = Pattern.compile("[23789]");
        Matcher  mm = pp.matcher(txt_telefono.getText().substring(0,1));

        if(m.find() && m.group().equals(txt_telefono.getText()) &&  mm.matches()){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Número");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente información: " +
                    " \nQue el telèfono comience con: 2,3,7,8 o 9 " +
                    " \nQue el telèfono contenga maximo 8 digitos " +
                    " \nEste campo solo acepta nùmeros");
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
            alert.setTitle("Validar dirección");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente información: " +
                    " \nEste campo solo letras y nùmeros");
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

    private boolean validateFields(){
        if (txt_nombre.getText().isEmpty() | txt_direccion.getText().isEmpty() | txt_telefono.getText().isEmpty() | txt_correo.getText().isEmpty() | Sexo.getSelectionModel().isEmpty()){

            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.show();

            return false;
        }
        return true;
    }

    private boolean limite(){
        if(txt_nombre.getText().length() >= 35){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supera Lìmite Permitido");
            alert.setHeaderText("Error");
            alert.setContentText("EL nombre superò el Lìmite de caracteres." +
                    " \n El lìmite de caracteres es de 35");
            alert.showAndWait();
            return false;
        }
        if(txt_direccion.getText().length() >= 50){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supera Lìmite Permitido");
            alert.setHeaderText("Error");
            alert.setContentText("Superò el Lìmite de caracteres." +
                    " \n El lìmite de caracteres es de 50");
            alert.showAndWait();
            return false;
        }
        return true;
    }


    public void numero (){
        if (txt_telefono.getText().length() >= 8) {

            Toolkit.getDefaultToolkit().beep();
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alcanzó el lìmite");
            alert.setHeaderText(null);
            alert.setContentText("Alcanzó el lìmite de números permitidos");
            alert.showAndWait();
        }

    }


    private boolean existeTelefono() {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from cliente where telefonoCliente = '" +txt_telefono.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("El número de teléfono que ingresó ¡Ya existe!");
                alert.setHeaderText(null);
                alert.setContentText("El Teléfono: " + txt_telefono.getText() + " ya existe");
                alert.show();
                return true;
            } else {
                return false;
            }

            } catch (Exception e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\CLIENTES_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (Exception ex) {
                Logger.getLogger(ClientesController.class.getName()).log(Level.ALL, null, ex);
            }

        }
        return false;
    }

    private boolean existeCorreo() {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from cliente where correoCliente = '" +txt_correo.getText() + "'";
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
                String nombreArchivo = "src\\Log\\CLIENTES_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (Exception ex) {
                Logger.getLogger(ClientesController.class.getName()).log(Level.ALL, null, ex);
            }
            }
        return false;
    }

}




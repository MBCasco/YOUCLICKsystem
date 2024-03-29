package Controller;

import Models.clientes;
import Models.empleados;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmpleadosController extends MenuController implements Initializable {
    @FXML
    private TableView<empleados> table_empleados;
    @FXML
    private TableColumn<empleados, Integer> col_id;
    @FXML
    private TableColumn<empleados, String> col_nombre;
    @FXML
    private TableColumn<empleados, String> col_direccion;
    @FXML
    private TableColumn<empleados, Integer> col_telefono;
    @FXML
    private TableColumn<empleados, String> col_correo;
    @FXML
    private TableColumn<empleados, String> col_cargo;
    @FXML
    private TableColumn<empleados, String> col_sexo;

    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_correo;
    @FXML
    private TextField txt_id;
    @FXML
    private TextField filterField;
    @FXML
    private TextField txt_eliminar;
    @FXML
    private TextField txt_telefono;
    @FXML
    private ComboBox<String> Cargo;
    @FXML
    private ComboBox<String> Sexo;
    @FXML
    private DatePicker date_inicio;
    @FXML
    private DatePicker date_final;

    @FXML
    private Button btn_registrar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_reporte;
    @FXML
    private Button btn_cargohistorico;


    ObservableList<empleados> listE;
    ObservableList<empleados> dataList;
    ObservableList<String> listsexo = connect.getdatasexo();
    ObservableList<String> listcargo = connect.getdatacargo();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");

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

                if (rs.getString("FuncionesEmpleados").contains("R")) {
                    btn_registrar.setVisible(true);
                }
                if (rs.getString("FuncionesEmpleados").contains("C")) {
                    btn_cargohistorico.setVisible(true);
                }
                if (rs.getString("FuncionesEmpleados").contains("A")) {
                    btn_actualizar.setVisible(true);
                }
                if (rs.getString("FuncionesEmpleados").contains("E")) {
                    btn_eliminar.setVisible(true);
                }
                if (rs.getString("FuncionesEmpleados").contains("I")) {
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

    public void Add_Empleados() {
        conn = connect.conDB();

        String sql = "insert into empleado (IDEmpleado,nombreEmpleado,direccionEmpleado,telefonoEmpleado,correoEmpleado,fechaInicial,fechaFinal,IDCargo,IDSexo)values(NULL,?,?,?,?,?,NULL,?,?)";
        int codS = 1;
        int codC = 1;


        if( validateFields() & limite() & validateName() & validateDireccion() & validateNumber()&  validateEmail() & validateSexo() & validateCargo() & validateInicio() ) { //

           if(existeTelefono() & existeCorreo()){
                return;
            }



            try {
                pst = conn.prepareStatement(sql);

                pst.setString(1, txt_nombre.getText());
                pst.setString(2, txt_direccion.getText());
                pst.setString(3, txt_telefono.getText());
                pst.setString(4, txt_correo.getText());
                pst.setString(5, date_inicio.getValue().format(formatter));



                if ( Sexo.getValue().equals("Femenino")){
                    codS = 1;
                }else if (Sexo.getValue().equals("Masculino")){
                    codS = 2;
                }

                if(Cargo.getValue().equals("Gestor de inventario")){
                    codC = 4;
                }else if (Cargo.getValue().equals("Vendedor")){
                    codC = 3;
                }else if (Cargo.getValue().equals("Gerente")){
                    codC = 2;
                }else if (Cargo.getValue().equals("Cajero")){
                    codC = 1;
                }

                pst.setInt(6, codC);
                pst.setInt(7, codS);
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó empleado exitosamente");
                alert.showAndWait();
                clearFields();
                UpdateTable();
                search_empleado();

            } catch (SQLException e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\EMPLEADOS_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.ALL);
                    myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
                } catch (Exception ex) {
                    Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
                }

                //JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @FXML
    private void clearFields() {
        txt_nombre.clear();
        txt_direccion.clear();
        txt_correo.clear();
        txt_telefono.clear();
        txt_id.clear();
        txt_eliminar.clear();
        Sexo.setValue(null);
        Cargo.setValue(null);
        txt_eliminar.clear();
        date_inicio.setValue(null);
        date_final.setValue(null);
        checkBtnStatus(0);
    }

    public void Delete(){

        Toolkit.getDefaultToolkit().beep();
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro ¿Qué quieres eliminar este empleado?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                conn = connect.conDB();
                try {
                    String Ssql = "DELETE FROM empleado WHERE IDEmpleado = ?";
                    PreparedStatement prest = conn.prepareStatement(Ssql);
                    prest.setString(1, txt_eliminar.getText());

                    if (prest.executeUpdate() > 0){
                        Alert alert1 =new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Eliminado");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Se elimino empleado con éxito");
                        alert1.showAndWait();
                        UpdateTable();
                        clearFields();
                    }

                }catch (SQLException e){
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\EMPLEADOS_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.ALL);
                        myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
                    } catch (Exception ex) {
                        Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
                    }
                }
            }
        });

    }

    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombreE"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<>("direccionE"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<>("telefonoE"));
        col_correo.setCellValueFactory(new PropertyValueFactory<>("correoE"));
        col_cargo.setCellValueFactory(new PropertyValueFactory<>("cargoE"));
        col_sexo.setCellValueFactory(new PropertyValueFactory<>("sexoE"));

        listE = connect.getdataempleados();
        table_empleados.setItems(listE);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        CMBBOX();
        UpdateTable();
        search_empleado();
        clearFields();
        checkBtnStatus(0);
        habilitar();
    }

    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_registrar.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
            date_final.setDisable(false);
        }
        if (check == 0){
            date_final.setDisable(true);
            btn_registrar.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
        }
    }

    public void CMBBOX(){
        Cargo.setItems(listcargo);
        Sexo.setItems(listsexo);
    }

    public void Edit(){


        int codS = 1;
        int codC = 1;

        if ( validateName() &validateDireccion() & validateNumber() &  validateEmail()& validateSexo() & validateCargo() & validateInicio() & validateFinal() ) { //



            try {
                conn = connect.conDB();
                String value1 = txt_id.getText();
                String value2 = txt_nombre.getText();
                String value3 = txt_direccion.getText();
                String value4 = txt_telefono.getText();
                String value5 = txt_correo.getText();
                String value6 = date_inicio.getValue().format(formatter);
                String value7 = date_final.getValue().format(formatter);

                if ( Sexo.getValue().equals("Femenino")){
                    codS = 1;
                }else if (Sexo.getValue().equals("Masculino")){
                    codS = 2;
                }

                if(Cargo.getValue().equals("Gestor de inventario")){
                    codC = 4;
                }else if (Cargo.getValue().equals("Vendedor")){
                    codC = 3;
                }else if (Cargo.getValue().equals("Gerente")){
                    codC = 2;
                }else if (Cargo.getValue().equals("Cajero")){
                    codC = 1;
                }

                int value8 = codC;
                int value9 = codS;

                String sql = (" UPDATE empleado SET nombreEmpleado= '" + value2 + "', direccionEmpleado= '" +
                        value3 + "', telefonoEmpleado= '" + value4 + "', correoEmpleado= '" + value5 + "', fechaInicial= '" + value6 + "', fechaFinal= '" + value7 + "', IDCargo= '" + value8 + "', IDSexo= '" + value9 + "' WHERE IDEmpleado='" + value1 + "' ");

                pst = conn.prepareStatement(sql);
                pst.execute();
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se actualizó el empleado exitosamente");
                alert.showAndWait();
                UpdateTable();
                search_empleado();
                clearFields();
            } catch (SQLException e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\EMPLEADOS_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.ALL);
                    myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
                } catch (Exception ex) {
                    Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
                }
            }
        }
    }

    @FXML
    void search_empleado(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombreE"));

        dataList = connect.getdataempleados();
        table_empleados.setItems(dataList);
        FilteredList<empleados> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNombreE().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else return String.valueOf(person.getIdEmpleado()).indexOf(lowerCaseFilter) != -1;
            });
        });
        SortedList<empleados> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_empleados.comparatorProperty());
        table_empleados.setItems(sortedData);
    }

    @FXML
    public void getSelected(javafx.scene.input.MouseEvent event) {
        index = table_empleados.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_nombre.setText(col_nombre.getCellData(index));
        txt_direccion.setText(col_direccion.getCellData(index));
        txt_telefono.setText(col_telefono.getCellData(index).toString());
        txt_correo.setText(col_correo.getCellData(index));
        Cargo.setValue(col_cargo.getCellData(index));
        Sexo.setValue(col_sexo.getCellData(index));
        txt_eliminar.setText(col_id.getCellData(index).toString());
        checkBtnStatus(1);
    }

    public void reporteEmpleado() throws JRException, ClassNotFoundException, SQLException {

        JasperReport reporte;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria", "root", "");
            reporte = JasperCompileManager.compileReport("src/Reportes/Empleado.jrxml");
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("empleadoParameter", 1);

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
        Matcher m = p.matcher(txt_nombre.getText());


        if(m.find() && m.group().equals(txt_nombre.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
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
                    " \nQue el telefono comience con: 2,3,7,8 o 9 " +
                    " \nQue el telefono contenga maximo 8 digitos " +
                    " \nEste campo solo acepta numeros");
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
                    " \nEste campo solo letras");
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
        if (txt_nombre.getText().isEmpty() | txt_telefono.getText().isEmpty() | txt_direccion.getText().isEmpty()
                | txt_correo.getText().isEmpty() ){

            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }

    private boolean validateInicio() {

        if (date_inicio.getEditor().getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fecha de inicio");
            alert.setHeaderText(null);
            alert.setContentText("Selecione una fecha inicio del cargo");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateFinal() {

        if (date_final.getEditor().getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fecha final");
            alert.setHeaderText(null);
            alert.setContentText("Selecione una fecha final del cargo");
            alert.showAndWait();
            return false;
        }
        return true;
    }


    private boolean validateSexo() {

        if (Sexo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en el género");
            alert.setHeaderText(null);
            alert.setContentText("Selecione género del empleado");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCargo() {

        if (Cargo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en el cargo");
            alert.setHeaderText(null);
            alert.setContentText("Selecione cargo del empleado");
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

    public void numero (){
        if (txt_telefono.getText().length() >= 8) {
            Toolkit.getDefaultToolkit().beep();
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Alcanzó el limite");
            alert.setHeaderText(null);
            alert.setContentText("Alcanzó el limite de números admitidos");
            alert.showAndWait();
        }
    }

    private boolean existeTelefono() {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from empleado where telefonoEmpleado = '" +txt_telefono.getText() + "'";
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
                String nombreArchivo = "src\\Log\\EMPLEADOS_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (Exception ex) {
                Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
            }
        }
        return false;
    }

    private boolean existeCorreo() {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from empleado where correoEmpleado = '" +txt_correo.getText() + "'";
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
                String nombreArchivo = "src\\Log\\EMPLEADOS_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (Exception ex) {
                Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
            }
        }
        return false;
    }


    public void CargoH (javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaCargoHistorico.fxml"));
        stage.setTitle("Cargo Histórico");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

}

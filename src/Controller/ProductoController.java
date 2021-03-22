package Controller;

import ComboBoxController.categoria;
import ComboBoxController.marca;
import Models.producto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductoController extends MenuController implements Initializable {

    @FXML
    private TableView<producto> tablaProductos;

    @FXML
    private TableColumn<producto, Integer> col_producto;

    @FXML
    private TableColumn<producto, String> col_nombre;

    @FXML
    private TableColumn<producto, Integer> col_stock;

    @FXML
    private TableColumn<producto, String> col_descripcion;

    @FXML
    private TableColumn<producto, String> col_ubicacion;

    @FXML
    private TableColumn<producto, Double> col_precio;

    @FXML
    private TableColumn<producto, marca> col_marca;

    @FXML
    private TableColumn<producto, categoria> col_categoria;

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNombreP;
    @FXML
    private TextField txtDescrpcionP;
    @FXML
    private TextField txtUbicacion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private ComboBox<categoria> comCat;
    @FXML
    private ComboBox<marca>comMarca;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtEliminar;
    @FXML
    private Button btn_registrar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_clear;


    ObservableList<producto> listP;
    ObservableList<marca> listaMarca = connect.getdatamarca();
    ObservableList<categoria> listcategoria = categoria.getdatacategoria();
    ObservableList<producto> dataListP;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    final Calendar calendar = Calendar.getInstance();
    final java.util.Date  date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(date);


    public void addProducto() throws SQLException {
        try {

        conn = connect.conDB();

        if (validateFields() & validateName() & validateNumberStock() & validateDescripcion() & validateUbicacion() & validateNumberprecio() & limite() & validateMarca() & validateCategoria()) {

            if(existeNombre()){
                return;
            }

            try {
                assert conn != null;
                PreparedStatement ps = conn.prepareStatement("INSERT INTO producto (nombre,descripcionProducto,precio,IDMarca,IDCategoria) VALUES (?,?,?,?,?)");

                ps.setString(1, txtNombreP.getText());
                ps.setString(2, txtDescrpcionP.getText());
                ps.setString(3, txtPrecio.getText());
                ps.setString(4, String.valueOf(comMarca.getSelectionModel().getSelectedItem().getIDMarca()));
                ps.setString(5, String.valueOf(comCat.getSelectionModel().getSelectedItem().getIDCategoria()));
                ps.execute();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó el producto exitosamente");
                alert.showAndWait();

            } catch (Exception e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\PRODUCTO_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe(e.getMessage() + "Causado por: " + e.getCause());
                } catch (IOException ex) {
                    Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                PreparedStatement ps1 = conn.prepareStatement("INSERT INTO inventario (stock,ubicacion, IDProducto) VALUES (?,?,LAST_INSERT_ID())");

                ps1.setString(1, txtStock.getText());
                ps1.setString(2, txtUbicacion.getText());
                ps1.execute();

                UpdateTable();
                clearFields();

            } catch (Exception e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\PRODUCTO_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe(e.getMessage() + "Causado por: " + e.getCause());
                } catch (IOException ex) {
                    Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        } catch (Exception e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\PRODUCTO_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.SEVERE);
                myLog.logger.severe(e.getMessage() + "Causado por: " + e.getCause());
            } catch (IOException ex) {
                Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    public void intCombox (){
        comMarca.setItems(listaMarca);
        comCat.setItems(listcategoria);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intCombox();
        UpdateTable();
        checkBtnStatus(0);
    }

    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_registrar.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
            txtStock.setDisable(true);
        }
        if (check == 0){
            btn_registrar.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
            txtStock.setDisable(false);
        }
    }

    public void UpdateTable(){
        col_producto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        col_ubicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        col_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        col_marca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        col_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        listP = connect.getdataproducto();
        tablaProductos.setItems(listP);
    }

    public void Delete(){
        Toolkit.getDefaultToolkit().beep();
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro ¿Qué quieres eliminar este producto?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                conn = connect.conDB();
                try {
                    String Ssql = "DELETE FROM producto WHERE IDProducto = ?";
                    PreparedStatement prest = conn.prepareStatement(Ssql);
                    prest.setString(1, txtEliminar.getText());

                    if (prest.executeUpdate() > 0){
                        Alert alert1 =new Alert(Alert.AlertType.CONFIRMATION);
                        alert1.setTitle("Confirmación");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Se elimino producto con éxito");
                        alert1.showAndWait();
                        UpdateTable();
                        clearFields();
                    }
                }catch (Exception e){
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\PRODUCTO_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.SEVERE);
                        myLog.logger.severe(e.getMessage() + "Causado por: " + e.getCause());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void Edit(){
        try{
        String value1 = txtID.getText();
        if (validateFields() & validateName() & validateNumberStock() & validateDescripcion() & validateUbicacion() & validateNumberprecio() & limite() & validateMarca() & validateCategoria()) {
            try {
                conn = connect.conDB();

                String value2 = txtNombreP.getText();
                String value3 = txtDescrpcionP.getText();
                String value4 = txtPrecio.getText();
                String value5 = String.valueOf(comMarca.getSelectionModel().getSelectedItem().getIDMarca());
                String value6 = String.valueOf(comCat.getSelectionModel().getSelectedItem().getIDCategoria());

                String sql = "UPDATE producto SET nombre= '" + value2 + "', descripcionProducto= '" + value3 + "',precio= '" + value4 + "',IDMarca= '" + value5 + "', IDCategoria= '" + value6 + "' WHERE IDProducto='" + value1 + "' ";

                pst = conn.prepareStatement(sql);
                pst.execute();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se actualizó producto exitosamente");
                alert.showAndWait();



            } catch (Exception e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\PRODUCTO_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe(e.getMessage() + "Causado por: " + e.getCause());
                } catch (IOException ex) {
                    Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                conn = connect.conDB();
                String value7 = txtStock.getText();
                String value8 = txtUbicacion.getText();

                String sql1 = "UPDATE inventario SET  stock= '" + value7 + "', ubicacion= '" + value8 + "' WHERE IDProducto = '" + value1 + "'";

                pst = conn.prepareStatement(sql1);
                pst.execute();
                UpdateTable();
                clearFields();

            } catch (Exception e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\PRODUCTO_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe(e.getMessage() + "Causado por: " + e.getCause());
                } catch (IOException ex) {
                    Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    } catch (Exception e) {
        try {
            Log myLog;
            String nombreArchivo = "src\\Log\\PRODUCTO_"+fecha+".txt";
            myLog = new Log(nombreArchivo);
            myLog.logger.setLevel(Level.SEVERE);
            myLog.logger.severe(e.getMessage() + "Causado por: " + e.getCause());
        } catch (IOException ex) {
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    }

    public void getSelected (javafx.scene.input.MouseEvent event) {

        index = tablaProductos.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }

        txtID.setText(col_producto.getCellData(index).toString());
        txtNombreP.setText(col_nombre.getCellData(index));
        txtStock.setText(col_stock.getCellData(index).toString());
        txtUbicacion.setText(col_ubicacion.getCellData(index));
        txtDescrpcionP.setText(col_descripcion.getCellData(index));
        txtPrecio.setText(col_precio.getCellData(index).toString());
        txtEliminar.setText(col_producto.getCellData(index).toString());


        checkBtnStatus(1);

    }

    public void PrecioH (javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaPrecioHistoricoDeProductos.fxml"));
        stage.setTitle("Precio Historico");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

    public void agregarMarca (javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaMarca.fxml"));
        stage.setTitle("Marca");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

    @FXML
    private void clearFields() {
        txtID.clear();
        txtNombreP.clear();
        txtDescrpcionP.clear();
        txtStock.clear();
        txtUbicacion.clear();
        txtPrecio.clear();
        comMarca.setValue(null);
        comCat.setValue(null);
        txtEliminar.clear();
        checkBtnStatus(0);
    }

    /*
        ////////////
        VALIDACIONES
        ////////////
     */

    private boolean validateName(){
        Pattern p = Pattern.compile("^([A-Z]{1}[a-z]+[ ]*)([A-Za-z ñáéíóú]+)$");
        Matcher m = p.matcher(txtNombreP.getText());

        if(m.find() && m.group().equals(txtNombreP.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Nombre");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta letras" +
                    " \nEl nombre escrito debe llevar la primera letra en mayuscula " +
                    " \nEj: Alambre");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateNumberStock(){
        Pattern p = Pattern.compile("[0-9]{1,8}");
        Matcher m = p.matcher(txtStock.getText().trim());

        if(m.find() && m.group().equals(txtStock.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Stock");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta numeros" +
                    " \nQue el numero que ingreso sea entero" +
                    " \nY el numero que ingreso contenga maximo 8 digitos");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateDescripcion(){
        Pattern p = Pattern.compile("[A-Za-z ]+");
        Matcher m = p.matcher(txtDescrpcionP.getText());

        if(m.find() && m.group().equals(txtDescrpcionP.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Descripcion");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta letras" +
                    " \nIngresar una descripcion válida" +
                    " \nEj: Alambre de amarre");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateUbicacion(){
        Pattern p = Pattern.compile("[0-9]{1,8}");
        Matcher m = p.matcher(txtUbicacion.getText().trim());


        if(m.find() && m.group().equals(txtUbicacion.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Pasillo");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nIngrese solo el numero del pasillo" +
                    " \nEste campo solo acepta numeros" +
                    " \nEl numero que ingreso debe contener maximo 2 digitos");
            alert.showAndWait();


            return false;
        }
    }

    private boolean validateNumberprecio(){
        Pattern p = Pattern.compile("[0-9]+(.[0-9]+)");
        Matcher m = p.matcher(txtPrecio.getText().trim());

        if(m.find() && m.group().equals(txtPrecio.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Precio");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo numeros decimales");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateMarca() {

        if (comMarca.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en la marca");
            alert.setHeaderText(null);
            alert.setContentText("Selecione marca para el producto");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateCategoria() {

        if (comCat.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en la categoria");
            alert.setHeaderText(null);
            alert.setContentText("Selecione categoria para el producto");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validateFields(){
        if (txtNombreP.getText().isEmpty() | txtStock.getText().isEmpty()  | txtDescrpcionP.getText().isEmpty() | txtUbicacion.getText().isEmpty() | txtPrecio.getText().isEmpty()){

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
        if(txtNombreP.getText().length() >= 35){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supera Limite Permitido");
            alert.setHeaderText("Error");
            alert.setContentText("Superó el Limite de caracteres.+" +
                    " \n El limite de caracteres es de 35");
            alert.showAndWait();
            return false;
        }
        if(txtDescrpcionP.getText().length() >= 50){
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

    private boolean existeNombre() {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from producto where nombre = '" +txtNombreP.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("El nombre que ingresó ¡Ya existe!");
                alert.setHeaderText(null);
                alert.setContentText("El nombre: " + txtNombreP.getText() + " ya existe");
                alert.show();
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }

}


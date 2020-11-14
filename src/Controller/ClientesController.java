package Controller;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
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
    private TableColumn<clientes, Integer> col_Sexo;


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
    private ComboBox Sexo;


    ObservableList<clientes> listM;
    ObservableList<clientes> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_clientes() {
        conn = connect.conDB();
        String sql = "insert into cliente (nombreCliente,dirreccionCliente,telefonoCLiente,correoCliente,IDSexo)values(?,?,?,?,?)";
        if (validateFields() & validateDireccion() & validateEmail() & validateName() & validateNumber()){
            try {
                pst = conn.prepareStatement(sql);

                pst.setString(1, txt_nombre.getText());
                pst.setString(2, txt_direccion.getText());
                pst.setString(3, txt_telefono.getText());
                pst.setString(4, txt_correo.getText());
                pst.setString(5, Sexo.getValue().toString());
                pst.execute();


                    JOptionPane.showMessageDialog(null, "Agregado");
                    clearFields();

                UpdateTable();
                Search_cliente();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }


    }

    private boolean validateNumber(){
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(txt_telefono.getText());

        if(m.find() && m.group().equals(txt_telefono.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Número");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un número válido");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateName(){
        Pattern p = Pattern.compile("[A-Za-z]");
        Matcher m = p.matcher(txt_nombre.getText());

        if(m.find() && m.group().equals(txt_nombre.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Nombre");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un nombre válido");
            alert.showAndWait();

            return false;
        }
    }
    private boolean validateDireccion(){
        Pattern p = Pattern.compile("[A-Za-z]");
        Matcher m = p.matcher(txt_direccion.getText());

        if(m.find() && m.group().equals(txt_direccion.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar direcion");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar una direccion válida");
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
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Correo");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un correo válido");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateFields(){
        if (txt_nombre.getText().isEmpty() | txt_direccion.getText().isEmpty() | txt_telefono.getText().isEmpty() | txt_correo.getText().isEmpty() ){

            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacio vacío, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }

    private void clearFields() {
        txt_id.clear();
        txt_nombre.clear();
        txt_direccion.clear();
        txt_correo.clear();
        txt_telefono.clear();
        txt_eliminar.clear();
        //Sexo.Items.clear();
    }

    public void Delete(){
        conn = connect.conDB();
        String sql = "Delete from cliente where IDCliente = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_eliminar.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Eliminado");
            UpdateTable();
            Search_cliente();
            clearFields();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void UpdateTable(){
        col_cliente.setCellValueFactory(new PropertyValueFactory<clientes,Integer>("idCliente"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<clientes,String>("nombre"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<clientes,String>("direccion"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<clientes,Integer>("telefono"));
        col_correo.setCellValueFactory(new PropertyValueFactory<clientes,String>("correo"));
        col_Sexo.setCellValueFactory(new PropertyValueFactory<clientes,Integer>("Sexo"));

        listM = connect.getdataclientes();
        table_clientes.setItems(listM);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        Sexo.getItems().addAll("1","2");
        UpdateTable();
        Search_cliente();
        clearFields();
    }

    public void Edit(){
        try{
            conn = connect.conDB();


            String value1 = txt_id.getText();
            String value2 = txt_nombre.getText();
            String value3 = txt_direccion.getText();
            String value4 = txt_telefono.getText();
            String value5 = txt_correo.getText();
            String value6 = Sexo.getValue().toString();

            String sql = "update cliente set nombreCliente= '"+value2+"', dirreccionCliente= '"+
                    value3+"', telefonoCliente= '"+value4+"', correoCliente= '"+value5+", IDSexo= '"+value6+" ' where IDCliente='"+value1+"' ";

            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            UpdateTable();
            Search_cliente();
            clearFields();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void Search_cliente(){
        col_cliente.setCellValueFactory(new PropertyValueFactory<clientes,Integer>("idCliente"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<clientes,String>("nombre"));

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

    @FXML
    public void getSelected(javafx.scene.input.MouseEvent event) {
        index = table_clientes.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_id.setText(col_cliente.getCellData(index).toString());
        txt_nombre.setText(col_nombre.getCellData(index).toString());
        txt_direccion.setText(col_direccion.getCellData(index).toString());
        txt_telefono.setText(col_telefono.getCellData(index).toString());
        txt_correo.setText(col_correo.getCellData(index).toString());
        Sexo.setValue(col_Sexo.getCellData(index).toString());
    }

}
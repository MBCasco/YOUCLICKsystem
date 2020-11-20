package Controller;

import Controller.connect;
import Models.proveedores;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
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

    ObservableList<proveedores> listM;
    ObservableList<proveedores> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void prueba() throws IOException {
        contactos();

    }

    public void Add_proveedor(){
        conn = connect.conDB();
        String sql = "insert into proveedores(empresaProveedor,correoProveedor,direccionProveedor)values(?,?,?)";
        if (validateFields() &validateName() & validateEmail() & validateDireccion()) {
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_nombre.getText());
                pst.setString(2, txt_correo.getText());
                pst.setString(3, txt_direccion.getText());
                pst.execute();

                JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente");
                UpdateTable();
                Search_proveedor();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void clearFields() {
        txt_idProveedor.clear();
        txt_nombre.clear();
        txt_correo.clear();
        txt_direccion.clear();
    }

    public void Delete(){
        conn = connect.conDB();
        String sql = "delete from PROVEEDORES where IDProveedor = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_eliminar.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Eliminado");
            UpdateTable();
            Search_proveedor();
            clearFields();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
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
    }

    public void Edit(){
        if (validateFields() &validateName() & validateEmail() & validateDireccion()) {
            try {
                conn = connect.conDB();
                String value1 = txt_idProveedor.getText();
                String value2 = txt_nombre.getText();
                String value3 = txt_correo.getText();
                String value4 = txt_direccion.getText();

                String sql = "update PROVEEDORES set empresaProveedor= '" + value2 + "', correoProveedor= '" + value3 + "', direccionProveedor= '" + value4 + "' where IDProveedor='" + value1 + "' ";
                pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Actualizado");
                UpdateTable();
                Search_proveedor();
                clearFields();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
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
        txt_nombre.setText(col_nombre.getCellData(index).toString());
        txt_correo.setText(col_correo.getCellData(index).toString());
        txt_direccion.setText(col_direccion.getCellData(index).toString());
    }
    /*
        ////////////
        VALIDACIONES
        ////////////
     */

    private boolean validateName(){
        Pattern p = Pattern.compile("[A-Za-z ]+");
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

    private boolean validateEmail(){
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(txt_correo.getText());

        if(m.find() && m.group().equals(txt_correo.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Correo");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un correo válido" +
                    " ejemplo@gmail.com");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateDireccion(){
        Pattern p = Pattern.compile("[A-Za-z ]+");
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
}

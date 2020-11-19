package Controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class EmpleadosController extends MenuController implements Initializable{

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
    private ComboBox Sexo;

    @FXML
    private TextField txt_telefono;

    @FXML
    private ComboBox Cargo;

    @FXML
    private TextField txt_fechaInicio;

    @FXML
    private TextField txt_fechaFinal;


    ObservableList<empleados> listM;
    ObservableList<empleados> dataList;
    ObservableList<String> listsexo = connect.getdatasexo();
    ObservableList<String> listcargo = connect.getdatacargo();

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_Empleados() {
        conn = connect.conDB();

        String sql = "insert into empleado (nombreEmpleado,direccionEmpleado,correoEmpleado,telefonoEmpleado,IDCargo,IDSexo)values(?,?,?,?,?,?)";

            try {
                pst = conn.prepareStatement(sql);

                pst.setString(1, txt_nombre.getText());
                pst.setString(2, txt_direccion.getText());
                pst.setString(3, txt_correo.getText());
                pst.setString(4, txt_telefono.getText());
                pst.setString(5, Cargo.getValue().toString());
                pst.setString(6, Sexo.getValue().toString());
                pst.execute();


                JOptionPane.showMessageDialog(null, "Agregado");
                clearFields();
                UpdateTable();
                search_empleado();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);

            }
    }

    private void clearFields() {
        txt_nombre.clear();
        txt_direccion.clear();
        txt_correo.clear();
        txt_telefono.clear();
        txt_id.clear();
        txt_eliminar.clear();
        //Sexo.Items.clear();
    }

    public void Delete(){
        conn = connect.conDB();
        String sql = "Delete from empleado where IDEmpleado = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_eliminar.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Eliminado");
            UpdateTable();
            search_empleado();
            clearFields();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<empleados,Integer>("idEmpleado"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<empleados,String>("nombreE"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<empleados,String>("direccionE"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<empleados,Integer>("telefonoE"));
        col_correo.setCellValueFactory(new PropertyValueFactory<empleados,String>("correoE"));
        col_cargo.setCellValueFactory(new PropertyValueFactory<empleados,String>("cargoE"));
        col_sexo.setCellValueFactory(new PropertyValueFactory<empleados,String>("sexoE"));

        listM = connect.getdataempleados();
        table_empleados.setItems(listM);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        Sexo.setItems(listsexo);
        Cargo.setItems(listcargo);


        UpdateTable();
        search_empleado();
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
            String value6 = Cargo.getValue().toString();
            String value7 = Sexo.getValue().toString();

            String sql = "update empleados set nombreEmpleado= '"+value2+"', dirreccionEmpleado= '"+
                    value3+"', telefonoEmpleado= '"+value4+"', correoEmpleado= '"+value5+", IDCargo= '"+value6+"', IDSexo= '"+value7+"' where IDEmpleado='"+value1+"' ";

            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Actualizado");
            UpdateTable();
            search_empleado();
            clearFields();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void search_empleado(){
        col_id.setCellValueFactory(new PropertyValueFactory<empleados,Integer>("idEmpleado"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<empleados,String>("nombreE"));

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
                }else if (String.valueOf(person.getIdEmpleado()).indexOf(lowerCaseFilter)!=-1)
                    return true;

                else
                    return false;
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
        txt_nombre.setText(col_nombre.getCellData(index).toString());
        txt_direccion.setText(col_direccion.getCellData(index).toString());
        txt_telefono.setText(col_telefono.getCellData(index).toString());
        txt_correo.setText(col_correo.getCellData(index).toString());
        Cargo.setValue(col_cargo.getCellData(index).toString());
        Sexo.setValue(col_sexo.getCellData(index).toString());
    }

}

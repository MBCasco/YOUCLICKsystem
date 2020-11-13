package Controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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




public class ProveedoresController extends MenuController implements Initializable {



    @FXML
    private TableView<proveedores> table_proveedores;

    @FXML
    private TableColumn<proveedores, Integer> col_id;

    @FXML
    private TableColumn<proveedores, String> col_nombre;

    @FXML
    private TableColumn<proveedores, String> col_direccion;

    @FXML
    private TableColumn<proveedores, String> col_correo;

    @FXML
    private TableColumn<proveedores, Integer> col_idContacto;

    @FXML
    private TextField txt_nombre;

    @FXML
    private TextField txt_direccion;

    @FXML
    private TextField txt_correo;

    @FXML
    private TextField txt_idContacto;

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

    public void Add_proveedor(){
        conn = connect.conDB();
        String sql = "insert into PROVEEDORES (empresaProveedor,correoProveedor,direccionProveedor,IDCOntactoProveedor)values(?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_nombre.getText());
            pst.setString(2, txt_correo.getText());
            pst.setString(3, txt_direccion.getText());
            pst.setString(4, txt_idContacto.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente");
            UpdateTable();
            Search_proveedor();
            clearFields();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void clearFields() {
        txt_idProveedor.clear();
        txt_nombre.clear();
        txt_direccion.clear();
        txt_correo.clear();
        txt_idContacto.clear();
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
        col_id.setCellValueFactory(new PropertyValueFactory<proveedores,Integer>("idProveedor"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<proveedores,String>("nombreP"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<proveedores,String>("direccionP"));
        col_correo.setCellValueFactory(new PropertyValueFactory<proveedores,String>("correoP"));
        col_idContacto.setCellValueFactory(new PropertyValueFactory<proveedores,Integer>("idContacto"));

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
        try{
            conn = connect.conDB();
            String value1 = txt_idProveedor.getText();
            String value2 = txt_nombre.getText();
            String value3 = txt_direccion.getText();
            String value4 = txt_correo.getText();
            String value5 = txt_idContacto.getText();

            String sql = "update PROVEEDORES set IDProveedor= '"+value1+"', nombreProveedor= '"+value2+"', direccionCliente= '"+
                    value3+"', correoProveedor= '"+value4+"', IDContacto= '"+value5+"' where IDProveedor='"+value1+"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Actualizado");
            UpdateTable();
            Search_proveedor();
            clearFields();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
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
        txt_idProveedor.setText(col_id.getCellData(index).toString());
        txt_nombre.setText(col_nombre.getCellData(index).toString());
        txt_direccion.setText(col_direccion.getCellData(index).toString());
        txt_correo.setText(col_correo.getCellData(index).toString());
        txt_idContacto.setText(col_idContacto.getCellData(index).toString());
    }







}

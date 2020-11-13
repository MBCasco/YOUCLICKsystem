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

public class ClientesController extends MenuController implements Initializable {
    @FXML
    private TableView<clientes> table_clientes;

    @FXML
    private TableColumn<clientes, Integer> col_id;

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
    private TextField txt_Sexo;


    ObservableList<clientes> listM;
    ObservableList<clientes> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void Add_clientes(){
        conn = connect.conDB();
        String sql = "insert into cliente (nombreCliente,dirreccionCliente,telefonoCLiente,correoCliente,IDSexo)values(?,?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);

            pst.setString(1, txt_nombre.getText());
            pst.setString(2, txt_direccion.getText());
            pst.setString(3, txt_telefono.getText());
            pst.setString(4, txt_correo.getText());
            pst.setString(5, txt_Sexo.getText());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Users Add succes");
            UpdateTable();
            Search_cliente();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Delete(){
        conn = connect.conDB();
        String sql = "Delete from cliente where IDClientes = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Eliminado");
            UpdateTable();
            Search_cliente();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<clientes,Integer>("idCliente"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<clientes,String>("nombre"));
        col_direccion.setCellValueFactory(new PropertyValueFactory<clientes,String>("direccion"));
        col_telefono.setCellValueFactory(new PropertyValueFactory<clientes,Integer>("telefono"));
        col_correo.setCellValueFactory(new PropertyValueFactory<clientes,String>("correo"));
        col_Sexo.setCellValueFactory(new PropertyValueFactory<clientes,String>("Sexo"));

        listM = connect.getdataclientes();
        table_clientes.setItems(listM);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        UpdateTable();
        Search_cliente();
    }

    public void Edit(){
        try{
            conn = connect.conDB();
            String value1 = txt_id.getText();
            String value2 = txt_nombre.getText();
            String value3 = txt_direccion.getText();
            String value4 = txt_telefono.getText();
            String value5 = txt_correo.getText();
            String value6 = txt_Sexo.getText();

            String sql = "update cliente set IDCliente= '"+value1+"', nombreCliente= '"+value2+"', dirreccionCliente= '"+
                    value3+"', telefonoCliente= '"+value4+"', correoCliente= '"+value5+", IDSexo= '"+value6+"' where IDCliente='"+value1+"' ";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Update");
            UpdateTable();
            Search_cliente();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @FXML
    void Search_cliente(){
        col_id.setCellValueFactory(new PropertyValueFactory<clientes,Integer>("idClientes"));
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
        txt_id.setText(col_id.getCellData(index).toString());
        txt_nombre.setText(col_nombre.getCellData(index));
        txt_direccion.setText(col_direccion.getCellData(index));
        txt_telefono.setText(col_telefono.getCellData(index).toString());
        txt_correo.setText(col_correo.getCellData(index));
        txt_Sexo.setText(col_Sexo.getCellData(index));
    }
}

package Controller;

import Models.categoria;
import Models.marca;
import Models.producto;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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


    ObservableList<producto> listP;
    ObservableList<marca> listaMarca = marca.getdatamarca();
    ObservableList<categoria> listcategoria = categoria.getdatacategoria();
    ObservableList<producto> dataListP;


    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;


    public void addProducto() throws SQLException {
        conn = connect.conDB();

        //if (validateFields() & validateDireccion() & validateEmail() & validateName() & validateNumber()){
        try{
            assert conn != null;
            PreparedStatement ps = conn.prepareStatement("INSERT INTO producto (nombre,descripcionProducto,precio,IDMarca,IDCategoria) VALUES (?,?,?,?,?)");

            ps.setString(1, txtNombreP.getText());
            ps.setString(2, txtDescrpcionP.getText());
            ps.setString(3, txtPrecio.getText());
            ps.setString(4, String.valueOf(comMarca.getSelectionModel().getSelectedItem().getIDMarca()));
            ps.setString(5, String.valueOf(comCat.getSelectionModel().getSelectedItem().getIDCategoria()));
            ps.execute();

        }catch(Exception e){
            System.out.println(e);
        }
        try {
            PreparedStatement ps1 = conn.prepareStatement("INSERT INTO inventario (stock,ubicacion, IDProducto) VALUES (?,?,LAST_INSERT_ID())");

            ps1.setString(1, txtStock.getText());
            ps1.setString(2, txtUbicacion.getText());
            ps1.execute();

        }catch(Exception e){
            System.out.println(e);
        }
        //}
        UpdateTable();
    }


    public void intCombox (){
        comMarca.setItems(listaMarca);
        comCat.setItems(listcategoria);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intCombox();
        UpdateTable();
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
        conn = connect.conDB();
        String sql = "Delete from producto where IDProducto = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtEliminar.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Elimino su producto con exito");
            UpdateTable();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Edit(){

        String value1 = txtID.getText();
        assert conn != null;

        try{
            conn = connect.conDB();

            String value2 = txtNombreP.getText();
            String value3 = txtDescrpcionP.getText();
            String value4 = txtPrecio.getText();
            String value5 = String.valueOf(comMarca.getSelectionModel().getSelectedItem().getIDMarca());
            String value6 = String.valueOf(comCat.getSelectionModel().getSelectedItem().getIDCategoria());

            String sql =  "UPDATE producto SET nombre= '" + value2 + "', descripcionProducto= '" + value3 + "', precio= '" + value4 + "',IDMarca= '" + value5 + "', IDCategoria= '" + value6 + "' WHERE IDProducto='" + value1 + "' ";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Se actualizo con exito");

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            conn = connect.conDB();
            String value8 = txtStock.getText();
            String value9 = txtUbicacion.getText();

            String sql1 = "UPDATE inventario SET stock= '"+value8+"', ubicacion= '"+value9+"' WHERE IDProducto = '"+value1+"'";

            pst = conn.prepareStatement(sql1);
            pst.execute();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

        UpdateTable();

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
        comMarca.setValue(col_marca.getCellData(index));
        comCat.setValue(col_categoria.getCellData(index));

    }

    public void PrecioH (javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaPrecioHistoricoDeProductos.fxml"));
        stage.setTitle("Precio Historico");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }




}

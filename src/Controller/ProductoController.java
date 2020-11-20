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
    private ComboBox<categoria> comCategoria;
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
            ps.setString(4, String.valueOf(comMarca.getSelectionModel().getSelectedIndex()));
            ps.setString(5, String.valueOf(comCategoria.getSelectionModel().getSelectedIndex()));
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
        comCategoria.setItems(listcategoria);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       intCombox();
       UpdateTable();
    }
    public void UpdateTable(){
        col_producto.setCellValueFactory(new PropertyValueFactory<producto,Integer>("idProducto"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<producto,String>("nombre"));
        col_stock.setCellValueFactory(new PropertyValueFactory<producto, Integer>("stock"));
        col_descripcion.setCellValueFactory(new PropertyValueFactory<producto,String>("descripcion"));
        col_ubicacion.setCellValueFactory(new PropertyValueFactory<producto,String>("ubicacion"));
        col_precio.setCellValueFactory(new PropertyValueFactory<producto,Double>("precio"));
        col_marca.setCellValueFactory(new PropertyValueFactory<producto,marca>("marca"));
        col_categoria.setCellValueFactory(new PropertyValueFactory<producto,categoria>("categoria"));

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
        try{
            conn = connect.conDB();

            String value1 = txtID.getText();
            String value2 = txtNombreP.getText();
            String value3 = txtDescrpcionP.getText();
            String value4 = txtPrecio.getText();
            String value5 = String.valueOf(comMarca.getSelectionModel().getSelectedIndex());
            String value6 = String.valueOf(comCategoria.getSelectionModel().getSelectedIndex());

            String sql = "update productos set nombre= '"+value2+"', dirreccionCliente= '"+
                    value3+"', telefonoCliente= '"+value4+"', correoCliente= '"+value5+", IDSexo= '"+value6+" ' where IDProducto='"+value1+"' ";

            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Se actualizo con exito");
            UpdateTable();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        try{
            conn = connect.conDB();
                    String value1 =txtID.getText();
                    String value7 = txtStock.getText();
                    String value8 = txtUbicacion.getText();
            String sql = "update inventario set stock= '"+value7+"' , ubicacio= '"+value8+"' where IDProducto = '"+value1+"'";

            UpdateTable();

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void PrecioH (javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaPrecioHistoricoDeProductos.fxml"));
        stage.setTitle("Precio Historico");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }




}

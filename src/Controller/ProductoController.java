package Controller;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductoController  extends MenuController implements Initializable {

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
    private TableColumn<producto, String> col_marca;

    @FXML
    private TableColumn<producto, String> col_categoria;

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
    private ComboBox comCategoriaP;
    @FXML
    private ComboBox comMarca;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtEliminar;


    ObservableList<producto> listP;
    ObservableList<String> listmarca = connect.getdatamarca();
    ObservableList<String> listcategoria = connect.getdatacategoria();


    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;



    public void addProducto() throws SQLException {
        conn = connect.conDB();

        //if (validateFields() & validateDireccion() & validateEmail() & validateName() & validateNumber()){
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO producto (nombre, descripcionProducto, precio, IDMarca, IDCategoria, IDPrecioHistorico ) VALUES (?,?,?,?,?,?)");

            ps.setString(1, txtNombreP.getText() );
            ps.setString(3, txtDescrpcionP.getText());
            ps.setString(5, txtPrecio.getText());
            ps.setString(6, comMarca.getId());
            ps.setString(7, comCategoriaP.getId());
            ps.execute();


            ps = conn.prepareStatement("INSERT INTO inventario (stock, ubicacion, IDProducto) VALUES ((?,?, last_insert_id)");

            ps.setString(2, txtStock.getText());
            ps.setString(4, txtUbicacion.getText());
            ps.execute();

        }catch(Exception e){
            System.out.println(e);
        }
        //}

    }


    public void intCombox (){
        comMarca.setItems(listmarca);
        comCategoriaP.setItems(listcategoria);
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
        col_marca.setCellValueFactory(new PropertyValueFactory<producto,String>("marca"));
        col_categoria.setCellValueFactory(new PropertyValueFactory<producto,String>("categoria"));

        listP = connect.getdataproducto();
        tablaProductos.setItems(listP);
    }
    public void Delete(){
        conn = connect.conDB();
        String sql = "Delete from productos where IDProductos = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txtEliminar.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Elimino su producto con exito");
            UpdateTable();
            //Search_cliente();
            //clearFields();
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
    private boolean validateNumber(){
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(txtPrecio.getText().trim());

        if(m.find() && m.group().equals(txtPrecio.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Número");
            alert.setHeaderText(null);
            alert.setContentText("En este campo debe dijitar un numero" +
                    " y el campo no acepta espacios en blanco");
            alert.showAndWait();

            return false;
        }
    }


}

package Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

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
    private TableColumn<producto, Integer> col_precio;

    @FXML
    private TableColumn<producto, Integer> col_marca;

    @FXML
    private TableColumn<producto, Integer> col_categoria;

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
    private ComboBox comMarca;
    @FXML
    private ComboBox comCategoriaP;
    @FXML
    private TextField txtStock;

    ObservableList<producto> listP;
    ObservableList<producto> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public ProductoController(){
        try {
            consultarMarca();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addProducto() throws SQLException {
        conn = connect.conDB();
        String sql = "insert into producto (nombreProducto,descripcionProducto,IDMarca,IDCategoria,IDPrecioHistorico) values (?,?,?,?,?) ";
        String sql1 = "insert into inventario (stockActual, ubicacion) values (?,?)";
        String marca = comMarca.getValue().toString();
        Statement st2 = conn.createStatement();
        String consulta = "Select IDMarca from producto where ";
        //if (validateFields() & validateDireccion() & validateEmail() & validateName() & validateNumber()){
            try {
                pst = conn.prepareStatement(sql);

                pst.setString(1, txtNombreP.getText());
                pst.setString(2, txtDescrpcionP.getText());
                //pst.setString(3, comMarca.toString());
                pst.setString(5, txtPrecio.getText());
                pst.execute();


                JOptionPane.showMessageDialog(null, "Agrego su producto con exito");
                //clearFields();

                //UpdateTable();
                //Search_cliente();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        //}
        String Marca = comMarca.getValue().toString();
        String idMarca = " ";
        Statement st = conn.createStatement();
        String sql2 = ("SELECT nombreMarca FROM marca ORDER BY nombreMarca ASC");
        ResultSet rs = null;
        try {
            rs = st.executeQuery(sql2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(rs.next()){
            try {
                idMarca = rs.getString("nombreMarca");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }


    public void consultarMarca () throws SQLException {
      String sql = ("SELECT nombreMarca FROM marca ORDER BY nombreMarca ASC");

      try{
          PreparedStatement pat = conn.prepareStatement(sql);
          ResultSet resul = pat.executeQuery();


          comMarca.setValue("Seleccione una marca");

          while (resul.next()){
              comMarca.setValue(resul.getString("nombreMarca"));
          }

      }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
      }finally {
          if (conn != null){
            conn.close();
          }else{
              JOptionPane.showMessageDialog(null, "Error");
          }
      }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

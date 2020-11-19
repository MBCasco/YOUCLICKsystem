
package Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class connect {
    Connection conn = null;

    public static Connection conDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria1","root","140120101305");
            return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    public static ObservableList<clientes> getdataclientes(){
        Connection conn = conDB();
        ObservableList<clientes> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cliente");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new clientes( Integer.parseInt(rs.getString("IDCliente")), rs.getString("nombreCliente"), rs.getString("dirreccionCliente"), Integer.parseInt(rs.getString("telefonoCliente")), rs.getString("correoCliente"), Integer.parseInt(rs.getString("IDSexo"))));
            }
        }catch(Exception e){
        }
        return list;
    }


    public static ObservableList<producto> getdataproducto(){
        Connection conn = conDB();
        ObservableList<producto> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDProducto, p.nombre, i.stock, p.descripcionProducto, i.ubicacion, p.precio, m.nombreMarca, c.nombreCategoria FROM producto AS p INNER JOIN inventario AS i ON i.IDProducto = p.IDProducto INNER JOIN marca AS m ON m.IDMarca = p.IDMarca INNER JOIN categoria AS c ON p.IDCategoria = c.IDCategoria;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new producto (Integer.parseInt(rs.getString("IDProducto")), rs.getString("nombre"), Integer.parseInt(rs.getString("stock")), rs.getString("descripcionProducto"), rs.getString("ubicacion"), Double.parseDouble(rs.getString("precio")), rs.getString("nombreMarca"), rs.getString("nombreCategoria")));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<precioHistorico> getdataprecioHistorico() {
        Connection conn = conDB();
        ObservableList<precioHistorico> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRECIOHISTORICO");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new precioHistorico( Integer.parseInt(rs.getString("IDPrecioHistorico")), rs.getString("nombre"),Double.parseDouble(rs.getString("precio")) , rs.getString("FechaInicial"), rs.getString("FechaFinal")));
            }
        }catch(Exception e){
        }
        return list;

    }

    public static ObservableList<String> getdatamarca(){
        Connection conn = conDB();
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM marca");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new String(rs.getString("nombreMarca")));
            }
        }catch(Exception e){
        }

        return list;
    }
    public static ObservableList<String> getdatacategoria(){
        Connection conn = conDB();
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new String((rs.getString("nombreCategoria"))));
            }
        }catch(Exception e){
        }
        return list;
    }




}


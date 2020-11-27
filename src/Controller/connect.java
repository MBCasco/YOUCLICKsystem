
package Controller;
import Models.*;
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria","root","qwerty123456789");
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
            PreparedStatement ps = conn.prepareStatement("SELECT c.IDCliente, c.nombreCliente, c.dirreccionCliente, c.telefonoCliente, c.correoCliente, s.descripcionSexo FROM cliente AS c INNER JOIN sexo AS s ON s.IDSexo = c.IDSexo");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new clientes( Integer.parseInt(rs.getString("IDCliente")), rs.getString("nombreCliente"),  Integer.parseInt(rs.getString("telefonoCliente")), rs.getString("dirreccionCliente"), rs.getString("correoCliente"), rs.getString("descripcionSexo")));
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
    public static ObservableList<proveedores> getdataproveedor(){
        Connection conn = conDB();
        ObservableList<proveedores> list = FXCollections.observableArrayList();
        try{
            PreparedStatement psp = conn.prepareStatement("select * from proveedores");
            ResultSet rsP = psp.executeQuery();
            while(rsP.next()){
                list.add(new proveedores(rsP.getInt("IDProveedor"),rsP.getString("empresaProveedor"), rsP.getString("correoProveedor"), rsP.getString("direccionProveedor")));
            }
        }catch(Exception e){
        }
        return list;
    }


    public static ObservableList<contacto> getdatacontacto(int value){
        Connection conn = conDB();
        ObservableList<contacto> list = FXCollections.observableArrayList();
        try{
            PreparedStatement psc = conn.prepareStatement("SELECT * FROM contactoproveedor where IDProveedor = ?");
            psc.setInt(1,value);
            ResultSet rsP = psc.executeQuery();

            while(rsP.next()){
                list.add(new contacto( Integer.parseInt(rsP.getString("IDContactoProveedor")), Integer.parseInt(rsP.getString("IDProveedor")), rsP.getString("nombreDeContacto"), rsP.getString("Detalles"), Integer.parseInt(rsP.getString("Telefono")), rsP.getString("Correo")));
            }
        }catch(Exception e){
        }
        return list;
    }
    public static ObservableList<empleados> getdataempleados(){
        Connection conn = conDB();
        ObservableList<empleados> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT e.IDEmpleado, e.nombreEmpleado, e.direccionEmpleado, e.telefonoEmpleado, e.correoEmpleado, c.nombreCargo, s.descripcionSexo FROM empleado AS e INNER JOIN cargo AS c ON c.IDCargo = e.IDCargo INNER JOIN sexo AS s ON s.IDSexo = e.IDSexo;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new empleados(Integer.parseInt(rs.getString("IDEmpleado")),  rs.getString("nombreEmpleado"),rs.getString("direccionEmpleado"),Integer.parseInt(rs.getString("telefonoEmpleado")), rs.getString("correoEmpleado"), rs.getString("nombreCargo"), rs.getString("descripcionSexo")));
            }
        }catch(Exception e){
        }
        return list;
    }
    public static ObservableList<inventario> getdatainventario(){
        Connection conn = conDB();
        ObservableList<inventario> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDProducto, p.nombre, i.stock, p.descripcionProducto, i.ubicacion, p.precio, m.nombreMarca, c.nombreCategoria FROM producto AS p INNER JOIN inventario AS i ON i.IDProducto = p.IDProducto INNER JOIN marca AS m ON m.IDMarca = p.IDMarca INNER JOIN categoria AS c ON p.IDCategoria = c.IDCategoria;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new inventario (Integer.parseInt(rs.getString("IDProducto")), rs.getString("nombre"), Integer.parseInt(rs.getString("stock")), rs.getString("descripcionProducto"), rs.getString("ubicacion"), Double.parseDouble(rs.getString("precio")), rs.getString("nombreMarca"), rs.getString("nombreCategoria")));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<String> getdatasexo(){
        Connection conn = conDB();
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT descripcionSexo FROM sexo");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new String((rs.getString("descripcionSexo"))));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<String> getdatacargo(){
        Connection conn = conDB();
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT nombreCargo FROM cargo");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new String((rs.getString("nombreCargo"))));
            }
        }catch(Exception e){
        }
        return list;
    }


}


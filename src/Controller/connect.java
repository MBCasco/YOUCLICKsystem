
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferreteria","root","");
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

    public static ObservableList<proveedores> getdataproveedor(){
        Connection conn = conDB();
        ObservableList<proveedores> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PROVEEDORES");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new proveedores( Integer.parseInt(rs.getString("IDProveedor")), rs.getString("empresaProveedor"), rs.getString("correoProveedor"), rs.getString("direccionProveedor"), Integer.parseInt(rs.getString("IDContactoProveedor"))));
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


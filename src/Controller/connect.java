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
                Class.forName("com.mysql.jdbc.Driver");
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
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM cliente");
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    list.add(new clientes(Integer.parseInt(rs.getString("IDCliente")), rs.getString("nombreCliente"), rs.getString("dirreccionCliente"), Integer.parseInt(rs.getString("telefonoCliente")), rs.getString("correoCliente"), Integer.parseInt(rs.getString("IDSexo"))));
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

    }


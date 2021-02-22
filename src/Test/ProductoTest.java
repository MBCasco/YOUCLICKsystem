package Test;

import Controller.connect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductoTest {

    Connection conn = null;

    ProductoTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    ResultSet rs = null;
    PreparedStatement pst = null;

    public void AgregarProducto(String idProducto, String nombre, String descripcion,  String precio, String marca, String categoria){
        if((idProducto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del producto.","ID Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombre.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del producto.","Nombre Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if((descripcion.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el descripcion del producto","Descripcion Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if((precio.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el precio del producto.","Precio Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((marca.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el marca del producto","Marca Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((categoria.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el categoria del producto","Categoria Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("INSERT INTO producto (nombre,descripcionProducto,precio,IDMarca,IDCategoria) VALUES (?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, precio);
            ps.setString(4, marca);
            ps.setString(5, categoria);


            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha guardado la información del producto");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }
    }
/*
    public void ActualizarProducto(String idProducto, String nombre, String descripcion,  String precio, String marca, String categoria){
        if((idProducto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del producto.","ID Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombre.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del producto.","Nombre Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if((descripcion.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el descripcion del producto","Descripcion Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if((precio.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el precio del producto.","Precio Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((marca.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el marca del producto","Marca Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((categoria.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el categoria del producto","Categoria Producto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("UPDATE producto SET nombre= '" + value2 + "', descripcionProducto= '" + value3 + "', ubicacion= '"
                    + value4 + "',precio= '" + value5 + "',IDMarca= '" + value6 + "', IDCategoria= '"
                    + value7 + "' WHERE IDProducto='" + value1 + "' ";
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, precio);
            ps.setString(4, marca);
            ps.setString(5, categoria);


            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actualizado la información del producto");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al actualizar la información");
        }

    }

 */

    public void EliminarProducto(String idProducto){
        if (idProducto.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null,"¡Debe seleccionar el ID del producto que desea eliminar! "
                    + "\n","¡AVISO!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro del producto " + idProducto + "", "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {

            try {
                Statement st2 = conn.createStatement();
                String sql = "DELETE FROM producto WHERE IDProducto = ?";

                int rs2 = st2.executeUpdate(sql);
                System.out.println(rs2);
                if(rs2 > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del producto" + idProducto + " correctamente");

                }else {
                    JOptionPane.showMessageDialog(null, "¡Error al eliminar la información!");

                }

            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al borrar la información del producto.",
                        "¡Error al Borrar!", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    public void BuscarProducto(){
        try {
            String sql = "SELECT * FROM producto";
            Statement stmt = conn.createStatement();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                String []datos= new String[2];
                datos[0] =rs.getString("IDProducto");
                datos[1] =rs.getString("nombre");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

}

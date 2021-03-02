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

    public void AgregarProducto(String idProducto, String nombre, String descripcion, String precio, String marca, String categoria, String stock, String ubicacion, String idInventario){
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

        if(!validarLongitudMax(nombre,35)){
            JOptionPane.showMessageDialog(null, "El teléfono del Empleado debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(descripcion,50)){
            JOptionPane.showMessageDialog(null, "La descripcion del producto sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombre)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneNumeros(descripcion)){
            JOptionPane.showMessageDialog(null, "Ingrese una descripción que solo contenga letras");
            return;
        }

        if(contieneLetras(stock)){
            JOptionPane.showMessageDialog(null, "El stock solo debe contener números ");
            return;
        }

        if(contieneLetras(ubicacion)){
            JOptionPane.showMessageDialog(null, "La ubicación solo debe contener números ");
            return;
        }

        if(contieneLetras(idProducto)){
            JOptionPane.showMessageDialog(null, "El idProducto solo debe contener números ");
            return;
        }

        try {
            assert conn != null;

            PreparedStatement ps;

            ps = conn.prepareStatement("INSERT INTO producto (nombre,descripcionProducto,precio,IDMarca,IDCategoria) VALUES (?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setString(3, precio);
            ps.setString(4, marca);
            ps.setString(5, categoria);
            ps.execute();


            JOptionPane.showMessageDialog(null, "Se ha guardado la información del producto");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }

        try {
            PreparedStatement ps1;

            ps1 = conn.prepareStatement("INSERT INTO inventario (stock,ubicacion, IDProducto) VALUES (?,?,LAST_INSERT_ID())");

            ps1.setString(1, stock);
            ps1.setString(2, ubicacion);
            ps1.execute();

            JOptionPane.showMessageDialog(null, "Se ha guardado la información del inventario");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }

    }

    public void ActualizarProducto(String idProducto, String nombre, String descripcion, String precio, String marca, String categoria){
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

        if(!validarLongitudMax(nombre,35)){
            JOptionPane.showMessageDialog(null, "El teléfono del Empleado debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(descripcion,50)){
            JOptionPane.showMessageDialog(null, "La descripcion del producto sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombre)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneNumeros(descripcion)){
            JOptionPane.showMessageDialog(null, "Ingrese una descripción que solo contenga letras");
            return;
        }

        if(contieneLetras(idProducto)){
            JOptionPane.showMessageDialog(null, "El idProducto solo debe contener números ");
            return;
        }

        try {


            String sql = ("UPDATE producto SET nombre= '" + nombre + "', descripcionProducto= '" + descripcion + "', precio= '" + precio + "',IDMarca= '" + marca + "', IDCategoria= '"
                    + categoria + "' WHERE IDProducto='" + idProducto + "' ");


            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Se ha actualizado la información del producto");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al actualizar la información");
        }

    }


    public void EliminarProducto(String idProducto){
        if (idProducto.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null,"¡Debe seleccionar el ID del producto que desea eliminar! "
                    + "\n","¡AVISO!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro del producto " + idProducto + "", "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {

            try {
                String sql = "DELETE FROM producto WHERE IDProducto = ?";
                PreparedStatement prest = conn.prepareStatement(sql);
                prest.setString(1, idProducto);

                if(prest.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del producto: " + idProducto + " correctamente");

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


    private boolean validarLongitudMax(String texto, int longitud) {
        if (texto.length() <= longitud) {
            return true;
        } else {
            return false;
        }
    }


    private boolean contieneNumeros(String texto){
        for (int i = 0; i < texto.length(); i++) {
            if(Character.isDigit(texto.charAt(i)))
                return true;
        }
        return false;
    }


    private boolean contieneLetras(String texto){
        for (int i = 0; i < texto.length(); i++) {
            if(Character.isLetter(texto.charAt(i)))
                return true;
        }
        return false;
    }



}

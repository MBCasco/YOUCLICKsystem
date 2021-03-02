package Test;

import org.junit.jupiter.api.Test;
import Controller.connect;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTestTest {

    Connection conn = null;

    ProductoTestTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void agregarProducto() {
        System.out.println("Agregar Producto");
        String idProducto = "3";
        String nombre = "mmm";
        String descripcion = "Grandes";
        String precio = "40.00";
        String marca = "1";
        String categoria = "";
        String stock = "200";
        String ubicacion= "2";
        String idInventario = "1";

        ProductoTest instance = new ProductoTest();
        instance.AgregarProducto(idProducto, nombre,  descripcion,  precio, marca, categoria, stock, ubicacion, idInventario);
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from producto where IDProducto  = '"+idProducto+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idProducto,rs.getString("IDProducto"));
                assertEquals(nombre,rs.getString("nombre"));
                assertEquals(descripcion,rs.getString("descripcionProducto"));
                assertEquals(precio,rs.getString("precio"));
                assertEquals(marca,rs.getString("IDMarca"));
                assertEquals(categoria,rs.getString("IDCategoria"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Statement st1 = conn.createStatement();
            String sql = "Select * from inventario where IDInventario  = '"+idInventario+"'";
            ResultSet rs = st1.executeQuery(sql);
            if(rs.next()){
                assertEquals(stock,rs.getString("stock"));
                assertEquals(ubicacion,rs.getString("ubicacion"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void actualizarProducto() {
        System.out.println("Actualizar Producto");
        String idProducto = "0";
        String nombre = "martillo";
        String descripcion = "Mango de madera";
        String precio= "100.10";
        String marca = "1";
        String categoria = "1";

        ProductoTest instance = new ProductoTest();
        instance.ActualizarProducto(idProducto,nombre,descripcion,precio,marca, categoria);
        try {
            Statement st = conn.createStatement();
            String sql = ("Update * from producto where IDProducto  = '"+idProducto+"'");

            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idProducto,rs.getString("IDProducto"));
                assertEquals(nombre,rs.getString("nombre"));
                assertEquals(descripcion,rs.getString("descripcionProducto"));
                assertEquals(precio,rs.getString("precio"));
                assertEquals(marca,rs.getString("IDMarca"));
                assertEquals(categoria,rs.getString("IDCategoria"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void eliminarProducto() {
        System.out.println("Eliminar Producto");
        String idProducto = "1";
        ProductoTest instance = new ProductoTest();
        instance.EliminarProducto(idProducto);
    }


}
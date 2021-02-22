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
        String nombre = "Martillo";
        String descripcion = "Grandes";
        String precio = "40.00";
        String marca = "1";
        String categoria = "1";

        ProductoTest instance = new ProductoTest();
        instance.AgregarProducto(idProducto, nombre,  descripcion,  precio, marca, categoria);
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
    }

    @Test
    void actualizarProducto() {
    }

    @Test
    void eliminarProducto() {
        System.out.println("Eliminar Producto");
        String idProducto = "";
        ProductoTest instance = new ProductoTest();
        instance.EliminarProducto(idProducto);
    }

    @Test
    void buscarProducto() {
        System.out.println("Buscar Producto");
        ProductoTest instance = new ProductoTest();
        instance.BuscarProducto();
    }
}
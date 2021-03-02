package Test;

import org.junit.jupiter.api.Test;
import Controller.connect;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class ProveedoresTestTest {

    Connection conn = null;

    ProveedoresTestTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void agregarProveedor() {
        System.out.println("Agregar Proveedor");
        String idProveedor = "1";
        String nombreP = "Eleven 7";
        String correoP = "hsdbhb@gmail.com";
        String direccionP = "robles";

        ProveedoresTest instance = new ProveedoresTest();
        instance.AgregarProveedor(idProveedor, nombreP, correoP, direccionP);
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from proveedores where IDProveedor  = '"+idProveedor+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idProveedor,rs.getString("IDProveedor"));
                assertEquals(nombreP,rs.getString("empresaProveedor"));
                assertEquals(correoP,rs.getString("correoProveedor"));
                assertEquals(direccionP,rs.getString("direccionProveedor"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void actualizarProveedor() {
        System.out.println("Actualizar Proveedor");
        String idProveedor = "1";
        String nombreP = "eleven 9";
        String correoP = "holi@gmail.com";
        String direccionP = "hola";

        ProveedoresTest instance = new ProveedoresTest();
        instance.ActualizarProveedor(idProveedor, nombreP, correoP, direccionP);
        try {
            Statement st = conn.createStatement();
            String sql = "Update * from proveedores where IDProveedor  = '"+idProveedor+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idProveedor,rs.getString("IDProveedor"));
                assertEquals(nombreP,rs.getString("empresaProveedor"));
                assertEquals(correoP,rs.getString("correoProveedor"));
                assertEquals(direccionP,rs.getString("direccionProveedor"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void eliminarProveedor() {
        System.out.println("Eliminar Proveedor");
        String idProveedor = "1";
        ProveedoresTest instance = new ProveedoresTest();
        instance.EliminarProveedor(idProveedor);
    }

    @Test
    void buscarProveedor() {
        System.out.println("Buscar Proveedor");
        ProveedoresTest instance = new ProveedoresTest();
        instance.BuscarProveedor();
    }
}
package Test;

import Controller.connect;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

class ProveedoresContactoTestTest {

    Connection conn = null;

    ProveedoresContactoTestTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void agregarProveedorContacto() {
        System.out.println("Agregar Proveedor Contacto");
        String IdContacto = "1";
        String IProveedor = "1";
        String NombreContacto = "Ricardo Reyes";
        String Detalles = "Administradoor";
        String TelefonoContacto = "98586515";
        String CorreoContacto = "rricardo@gmail.com";
        ProveedoresContactoTest instance = new ProveedoresContactoTest();
        instance.AgregarProveedorContacto(IdContacto, IProveedor, NombreContacto, Detalles, TelefonoContacto, CorreoContacto);
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from contactoproveedor where IDContactoProveedor  = '"+IdContacto+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(IdContacto,rs.getString("IDContactoProveedor"));
                assertEquals(IProveedor,rs.getString("IDProveedor"));
                assertEquals(NombreContacto,rs.getString("nombreDeContacto"));
                assertEquals(Detalles,rs.getString("Detalles"));
                assertEquals(TelefonoContacto,rs.getString("Telefono"));
                assertEquals(CorreoContacto,rs.getString("Correo"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void actualizarProveedorContacto() {
        System.out.println("Agregar Proveedor Contacto");
        String IdContacto = "1";
        String IProveedor = "1";
        String NombreContacto = "Meylin Baca";
        String Detalles = "Administrador";
        String TelefonoContacto = "95728187";
        String CorreoContacto = "meylin@gmail.com";
        ProveedoresContactoTest instance = new ProveedoresContactoTest();
        instance.ActualizarProveedorContacto(IdContacto, IProveedor, NombreContacto, Detalles, TelefonoContacto, CorreoContacto);
        try {
            Statement st = conn.createStatement();
            String sql = "Update * from contactoproveedor where IDContactoProveedor  = '"+IdContacto+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(IdContacto,rs.getString("IDContactoProveedor"));
                assertEquals(IProveedor,rs.getString("IDProveedor"));
                assertEquals(NombreContacto,rs.getString("nombreDeContacto"));
                assertEquals(Detalles,rs.getString("Detalles"));
                assertEquals(TelefonoContacto,rs.getString("Telefono"));
                assertEquals(CorreoContacto,rs.getString("Correo"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void eliminarProveedorContacto() {
        System.out.println("Eliminar Proveedor");
        String IdContacto = "1";
        ProveedoresContactoTest instance = new ProveedoresContactoTest();
        instance.EliminarProveedorContacto(IdContacto);
    }


}
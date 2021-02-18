package Test;

import static org.junit.jupiter.api.Assertions.*;
import Controller.connect;
import Test.ClientesTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

class ClientesTestTest {

    Connection conn = null;

    ClientesTestTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void agregarCliente() {
        System.out.println("Agregar Cliente");
        String idCliente = "5";
        String nombre = "Esther Galo";
        String telefono = "98625237";
        String direccion = "casita2";
        String correo = "esther@gmail.com";
        String sexo = "1";
        ClientesTest instance = new ClientesTest();
        instance.AgregarCliente(idCliente, nombre, telefono,  direccion, correo, sexo);
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from cliente where IDCliente  = '"+idCliente+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idCliente,rs.getString("IDCliente"));
                assertEquals(nombre,rs.getString("nombreCliente"));
                assertEquals(telefono,rs.getString("telefonoCliente"));
                assertEquals(direccion,rs.getString("direccionCliente"));
                assertEquals(correo,rs.getString("correoCliente"));
                assertEquals(sexo,rs.getString("IDSexo"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @org.junit.jupiter.api.Test
    void eliminarCliente() {
        System.out.println("Eliminar Cliente");
        String idCliente = "";
        String nombre = "";
        String telefono = "";
        String direccion = "";
        String correo = "";
        String sexo = "";
        ClientesTest instance = new ClientesTest();
        instance.EliminarCliente(idCliente, nombre, telefono,  direccion, correo, sexo);
    }

    @org.junit.jupiter.api.Test
    void actualizarCliente() {
        System.out.println("Agregar Cliente");
        String idCliente = "";
        String nombre = "";
        String telefono = "";
        String direccion = "";
        String correo = "";
        String sexo = "";
        ClientesTest instance = new ClientesTest();
        instance.ActualizarCliente(idCliente, nombre, telefono,  direccion, correo, sexo);
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from cliente where IDCliente  = '"+idCliente+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idCliente,rs.getString("IDCliente"));
                assertEquals(nombre,rs.getString("nombreCliente"));
                assertEquals(telefono,rs.getString("telefonoCliente"));
                assertEquals(direccion,rs.getString("direccionCliente"));
                assertEquals(correo,rs.getString("correoCliente"));
                assertEquals(sexo,rs.getString("IDSexo"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void buscarCliente() {
        System.out.println("Buscar Cliente");
        ClientesTest instance = new ClientesTest();
        instance.BuscarCliente();
        // TODO review the generated test code and remove the default call to fail.
    }
}
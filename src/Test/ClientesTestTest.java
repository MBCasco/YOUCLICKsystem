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
        String idCliente = "13";
        String nombre = "Meylin Baca";
        String telefono = "78954565";
        String direccion = "Loarque";
        String correo = "wwww@gmail.com";
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
        String idCliente = "9";
        ClientesTest instance = new ClientesTest();
        instance.EliminarCliente(idCliente);
    }

    @org.junit.jupiter.api.Test
    void actualizarCliente() {
        System.out.println("Actualizar Cliente");
        String idCliente = "11";
        String nombre = "Rich lozar";
        String telefono = "90123252";
        String direccion = "cat 2";
        String correo = "shsc@gmail.com";
        String sexo = "2";
        ClientesTest instance = new ClientesTest();
        instance.ActualizarCliente(idCliente, nombre, telefono,  direccion, correo, sexo);
        try {
            Statement st = conn.createStatement();
            String sql = ("Update * from cliente where IDCliente  = '"+idCliente+"'");

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
    }
}
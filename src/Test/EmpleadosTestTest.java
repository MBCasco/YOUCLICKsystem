package Test;

import Controller.connect;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadosTestTest {

    Connection conn = null;

    EmpleadosTestTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void agregarEmpleado() {
        System.out.println("Agregar Empleado");
        String idEmpleado = "2";
        String nombreE = "Ricardo Reyes";
        String direccionE = "los robles";
        String telefonoE = "32659885";
        String correoE = "rr@gmail.com";
        String fechaI = "2021-03-19";
        String cargoE = "1";
        String sexoE = "2";
        EmpleadosTest instance = new EmpleadosTest();
        instance.AgregarEmpleado(idEmpleado, nombreE, direccionE, telefonoE, correoE, fechaI, cargoE, sexoE);
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from empleado where IDEmpleado  = '"+idEmpleado+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idEmpleado,rs.getString("IDEmpleado"));
                assertEquals(nombreE,rs.getString("nombreEmpleado"));
                assertEquals(direccionE,rs.getString("direccionEmpleado"));
                assertEquals(telefonoE,rs.getString("telefonoEmpleado"));
                assertEquals(correoE,rs.getString("correoEmpleado"));
                assertEquals(fechaI,rs.getString("fechaInicial"));
                assertEquals(cargoE,rs.getString("IDcargo"));
                assertEquals(sexoE,rs.getString("IDSexo"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void actualizarEmpleado() {
        System.out.println("Agregar Empleado");
        String idEmpleado = "2";
        String nombreE = "Ricardo Reyes";
        String direccionE = "los robles";
        String telefonoE = "32659885";
        String correoE = "rr@gmail.com";
        String fechaI = "2021-03-19";
        String fechaF = "2021-04-19";
        String cargoE = "1";
        String sexoE = "2";
        EmpleadosTest instance = new EmpleadosTest();
        instance.ActualizarEmpleado(idEmpleado, nombreE, direccionE, telefonoE, correoE, fechaI, fechaF, cargoE, sexoE);
        try {
            Statement st = conn.createStatement();
            String sql = "Update * from empleado where IDEmpleado  = '"+idEmpleado+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                assertEquals(idEmpleado,rs.getString("IDEmpleado"));
                assertEquals(nombreE,rs.getString("nombreEmpleado"));
                assertEquals(direccionE,rs.getString("direccionEmpleado"));
                assertEquals(telefonoE,rs.getString("telefonoEmpleado"));
                assertEquals(correoE,rs.getString("correoEmpleado"));
                assertEquals(fechaI,rs.getString("fechaInicial"));
                assertEquals(fechaF,rs.getString("fechaFinal"));
                assertEquals(cargoE,rs.getString("IDcargo"));
                assertEquals(sexoE,rs.getString("IDSexo"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void eliminarEmpleado() {
        System.out.println("Eliminar Empleado");
        String idEmpleado = "2";
        EmpleadosTest instance = new EmpleadosTest();
        instance.EliminarEmpleado(idEmpleado);
    }

    @Test
    void buscarEmpleado() {
        System.out.println("Buscar Empleado");
        EmpleadosTest instance = new EmpleadosTest();
        instance.BuscarEmpleado();
    }
}
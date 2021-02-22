package Test;

import Controller.connect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmpleadosTest {

    Connection conn = null;

    EmpleadosTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    ResultSet rs = null;
    PreparedStatement pst = null;

    public void AgregarEmpleado(String idEmpleado, String nombreE, String direccionE , String telefonoE, String correoE , String fechaI, String cargoE, String sexoE ){
        if((idEmpleado.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del empleado.","ID Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombreE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del empleado.","Nombre Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccionE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el direccion del empleado","Direccion Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((telefonoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el telefono del empleado.","Telefono Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if((correoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del empleado","Correo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((fechaI.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el Fecha Inicial del empleado","Fecha Inicial Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((cargoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el cargo del empleado","Cargo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((sexoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el sexo del empleado","Sexo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("insert into empleado (nombreEmpleado,direccionEmpleado,telefonoEmpleado,correoEmpleado,fechaInicial,IDCargo,IDSexo)values(?,?,?,?,?,?,?)");
            ps.setString(1, nombreE);
            ps.setString(2, direccionE);
            ps.setString(3, telefonoE);
            ps.setString(4, correoE);
            ps.setString(5, fechaI);
            ps.setString(6, cargoE);
            ps.setString(7, sexoE);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha guardado la información del empleado");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }
    }

    public void ActualizarEmpleado(String idEmpleado, String nombreE, String direccionE , String telefonoE, String correoE , String fechaI, String fechaF, String cargoE, String sexoE ){
        if((idEmpleado.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del empleado.","ID Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombreE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del empleado.","Nombre Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccionE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el direccion del empleado","Direccion Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((telefonoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el telefono del empleado.","Telefono Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if((correoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del empleado","Correo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((fechaI.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el Fecha Inicial del empleado","Fecha Inicial Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((fechaF.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el Fecha Final del empleado","Fecha Final Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((cargoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el cargo del empleado","Cargo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((sexoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el sexo del empleado","Sexo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            PreparedStatement ps;

            ps = conn.prepareStatement(" UPDATE empleado SET nombreEmpleado= '" + nombreE + "', direccionEmpleado= '"
                    + direccionE + "', telefonoEmpleado= '" + telefonoE + "', correoEmpleado= '"
                    + correoE + "', fechaInicial= '" + fechaI + "', fechaFinal= '"
                    + fechaF + "', IDCargo= '" + cargoE + "', IDSexo= '"
                    + sexoE + "' WHERE IDEmpleado='" + idEmpleado + "' ");

            ps.setString(1, nombreE);
            ps.setString(2, direccionE);
            ps.setString(3, telefonoE);
            ps.setString(4, correoE);
            ps.setString(5, fechaI);
            ps.setString(6, fechaF);
            ps.setString(7, cargoE);
            ps.setString(8, sexoE);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actualizado la información del empleado");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }


    }

    public void EliminarEmpleado(String idEmpleado){
        if (idEmpleado.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null,"¡Debe seleccionar el ID del empleado que desea eliminar! "
                    + "\n","¡AVISO!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro del empleado " + idEmpleado + "", "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {

            try {
                Statement st2 = conn.createStatement();
                String sql = "DELETE FROM empleado WHERE IDEmpleado = ?";

                int rs2 = st2.executeUpdate(sql);
                System.out.println(rs2);
                if(rs2 > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del empleado" + idEmpleado + " correctamente");

                }else {
                    JOptionPane.showMessageDialog(null, "¡Error al eliminar la información!");

                }

            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al borrar la información del empleado.",
                        "¡Error al Borrar!", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    public void BuscarEmpleado(){
        try {
            String sql = "SELECT * FROM empleado";
            Statement stmt = conn.createStatement();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                String []datos= new String[2];
                datos[0] =rs.getString("IDEmpleado");
                datos[1] =rs.getString("nombreEmpleado");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }

    }
}

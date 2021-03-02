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

        if(existecorreo(correoE)){
            return;
        }

        if(existeTelefono(telefonoE)){
            return;
        }

        if(contieneLetras(sexoE)){
            JOptionPane.showMessageDialog(null, "El género solo debe contener números ");
            return;
        }

        if(contieneLetras(cargoE)){
            JOptionPane.showMessageDialog(null, "El cargo solo debe contener números ");
            return;
        }

        if(!validarSexo(sexoE)){
            JOptionPane.showMessageDialog(null, " Sexo solo puede contener valores entre 1 y 2", "Valor", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarCargo(cargoE)){
            JOptionPane.showMessageDialog(null, " El Cargo solo puede contener valores entre 1 y 4", "Valor", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(nombreE,35)){
            JOptionPane.showMessageDialog(null, "El nombre del Empleado sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(direccionE,50)){
            JOptionPane.showMessageDialog(null, "La dirección del Empleado sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(telefonoE,8)){
            JOptionPane.showMessageDialog(null, "El teléfono del Empleado debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombreE)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }


        if(contieneLetras(telefonoE)){
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números ");
            return;
        }

        if(contieneLetras(idEmpleado)){
            JOptionPane.showMessageDialog(null, "El idEmpleado solo debe contener números ");
            return;
        }

        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("insert into empleado (IDEmpleado,nombreEmpleado,direccionEmpleado,telefonoEmpleado,correoEmpleado,fechaInicial,fechaFinal,IDCargo,IDSexo)values(NULL,?,?,?,?,?,NULL,?,?)");
            ps.setString(1, nombreE);
            ps.setString(2, direccionE);
            ps.setString(3, telefonoE);
            ps.setString(4, correoE);
            ps.setString(5, fechaI);
            ps.setString(6, cargoE);
            ps.setString(7, sexoE);


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

        if((cargoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el cargo del empleado","Cargo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((sexoE.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el sexo del empleado","Sexo Empleado requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(existecorreo(correoE)){
            return;
        }

        if(existeTelefono(telefonoE)){
            return;
        }

        if(contieneLetras(sexoE)){
            JOptionPane.showMessageDialog(null, "El género solo debe contener números ");
            return;
        }

        if(contieneLetras(cargoE)){
            JOptionPane.showMessageDialog(null, "El cargo solo debe contener números ");
            return;
        }

        if(!validarSexo(sexoE)){
            JOptionPane.showMessageDialog(null, " Sexo solo puede contener valores entre 1 y 2", "Valor", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarCargo(cargoE)){
            JOptionPane.showMessageDialog(null, " El Cargo solo puede contener valores entre 1 y 4", "Valor", JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if(!validarLongitudMax(nombreE,35)){
            JOptionPane.showMessageDialog(null, "El nombre del Empleado sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(direccionE,50)){
            JOptionPane.showMessageDialog(null, "La dirección del Empleado sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(telefonoE,8)){
            JOptionPane.showMessageDialog(null, "El teléfono del Empleado debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombreE)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneLetras(telefonoE)){
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números ");
            return;
        }

        if(contieneLetras(idEmpleado)){
            JOptionPane.showMessageDialog(null, "El idEmpleado solo debe contener números ");
            return;
        }

        try {

            String sql= (" UPDATE empleado SET nombreEmpleado= '" + nombreE + "', direccionEmpleado= '"
                    + direccionE + "', telefonoEmpleado= '" + telefonoE + "', correoEmpleado= '"
                    + correoE + "', fechaInicial= '" + fechaI + "', fechaFinal= '"
                    + fechaF + "', IDCargo= '" + cargoE + "', IDSexo= '"
                    + sexoE + "' WHERE IDEmpleado='" + idEmpleado + "' ");

            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

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
                String sql = "DELETE FROM empleado WHERE IDEmpleado = ?";

                PreparedStatement prest = conn.prepareStatement(sql);
                prest.setString(1, idEmpleado);

                if (prest.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del empleado: " + idEmpleado + " correctamente");

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

    private boolean existecorreo(String correo) {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from empleado where correoEmpleado = '" + correo + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "El correo: " + correo + " ya existe", "El correo que ingresó ¡Ya existe!.", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
    }

    private boolean existeTelefono(String Telefono) {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from empleado where telefonoEmpleado = '" + Telefono + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "El Teléfono: " + Telefono + " ya existe", "El número de teléfono que ingresó ¡Ya existe!.", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return false;
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

    private boolean validarSexo(String genero){
        int sexo = Integer.parseInt(genero);
        if(sexo >= 1 && sexo <= 2){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean validarCargo(String cargo){
        int carg = Integer.parseInt(cargo);
        if(carg >= 1 && carg <= 4){
            return true;
        }
        else{
            return false;
        }
    }

}

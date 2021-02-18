package Test;

import Controller.connect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientesTest {

    Connection conn = null;

    ClientesTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    ResultSet rs = null;
    PreparedStatement pst = null;

    public void AgregarCliente( String idCliente, String nombre, String telefono, String direccion, String correo, String sexo){
        if((idCliente.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el código de la asignatura.","Código asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombre.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre de la asignatura.","Nombre asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((telefono.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar la unidad valorativa de la asignatura.","Unidad valorativa de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccion.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe seleccionar una carrera para la asignatura","Carrera de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((correo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe seleccionar un requisito para la asignatura","Requisito1 de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((sexo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe seleccionar un requisito para la asignatura","Requisito2 de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("insert into cliente (nombreCliente,telefonoCLiente,dirreccionCliente,correoCliente,IDSexo)values(?,?,?,?,?)");
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, direccion);
            ps.setString(4, correo);
            ps.setString(5, sexo);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha guardado la información en Registro de Asignatura");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }
    }

    public void EliminarCliente(String idCliente, String nombre, String telefono, String direccion, String correo, String sexo){
        if (idCliente.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null,"¡Debe seleccionar la asignatura que desea eliminar! "
                    + "\n","¡AVISO!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro de asignatura " + idCliente + "", "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {

            try {
                Statement st2 = conn.createStatement();
                String sql = "DELETE FROM cliente WHERE IDCliente = ?";

                int rs2 = st2.executeUpdate(sql);
                System.out.println(rs2);
                if(rs2 > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información de la asignatura" + idCliente + " correctamente");

                }else {
                    JOptionPane.showMessageDialog(null, "¡Error al eliminar la información!");

                }

            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al borrar la información de la asignatura, podría ser por: "
                        + "\n 1.La Asignatura ya está en uso con alumnos matriculados."
                        + "\n 2.No se encuentra el código de la Asignatura a borrar."
                        + "\n 3.La Asignatura tiene secciones creadas","¡Error al Borrar!", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public void ActualizarCliente( String idCliente, String nombre, String telefono, String direccion, String correo, String sexo){
        if((idCliente.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el código de la asignatura.","Código asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombre.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre de la asignatura.","Nombre asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((telefono.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar la unidad valorativa de la asignatura.","Unidad valorativa de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccion.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe seleccionar una carrera para la asignatura","Carrera de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((correo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe seleccionar un requisito para la asignatura","Requisito1 de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((sexo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe seleccionar un requisito para la asignatura","Requisito2 de la asignatura requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            PreparedStatement ps;
            ResultSet rs;
            ps = conn.prepareStatement("update cliente set"
                    + " IDCliente = ? ,"
                    + " nombreCliente = ? ,"
                    + " telefonoCliente = ? ,"
                    + " direccionCliente = ? ,"
                    + " correoCliente = ? ,"
                    + " IDSexo = ? ,"
                    + " where IDCliente ='"+idCliente+"'");
            ps.setString(1, idCliente);
            ps.setString(2, nombre);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, correo);
            ps.setString(6, sexo);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actualizado la información en de la cliente: " + idCliente);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al actualizar la informacion de la asignatura, podría ser por: "
                    + "\n 1.La Asignatura ya está en uso con alumnos matriculados."
                    + "\n 2. No se encuentra el código de la Asignatura a actualizar."
                    + "\n 3.La Asignatura tiene secciones creadas","¡Error al Actuarlizar!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void BuscarCliente(){
        try {
            String sql = "SELECT * FROM cliente";
            Statement stmt = conn.createStatement();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                String []datos= new String[2];
                datos[0] =rs.getString("IDCliente");
                datos[1] =rs.getString("nombreCliente");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}

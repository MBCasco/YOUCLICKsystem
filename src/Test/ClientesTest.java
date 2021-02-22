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
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del cliente.","ID Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombre.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del cliente.","Nombre Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((telefono.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el telefono del cliente.","Telefono Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccion.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el direccion del cliente","Direccion Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((correo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del cliente","Correo Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((sexo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el sexo del cliente","Sexo Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Se ha guardado la información de Cliente");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }
    }

    public void EliminarCliente(String idCliente){
        if (idCliente.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null,"¡Debe seleccionar el ID del cliente que desea eliminar! "
                    + "\n","¡AVISO!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro de cliente " + idCliente + "", "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {

            try {
                Statement st2 = conn.createStatement();
                String sql = "DELETE FROM cliente WHERE IDCliente = ?";

                int rs2 = st2.executeUpdate(sql);
                System.out.println(rs2);
                if(rs2 > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del cliente" + idCliente + " correctamente");

                }else {
                    JOptionPane.showMessageDialog(null, "¡Error al eliminar la información!");

                }

            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al borrar la información del cliente.",
                        "¡Error al Borrar!", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public void ActualizarCliente( String idCliente, String nombre, String telefono, String direccion, String correo, String sexo){
        if((idCliente.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del cliente.","ID Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombre.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del cliente.","Nombre Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((telefono.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el telefono del cliente.","Telefono Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccion.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el direccion del cliente","Direccion Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((correo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del cliente","Correo Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((sexo.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el sexo del cliente","Sexo Cliente requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            PreparedStatement ps;
            ResultSet rs;

            ps = conn.prepareStatement("update cliente set nombreCliente= '" + nombre +
                    "', telefonoCliente= '" + telefono + "', dirreccionCliente= '" + direccion +
                    "', correoCliente= '" + correo + "', IDSexo= '" + sexo + " ' where IDCliente= '" + idCliente + "' ");
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, direccion);
            ps.setString(4, correo);
            ps.setString(5, sexo);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actualizado la información del cliente");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al Actualizar la información");
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

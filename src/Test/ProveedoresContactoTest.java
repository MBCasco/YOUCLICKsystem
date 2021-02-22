package Test;

import Controller.connect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProveedoresContactoTest {

    Connection conn = null;

    ProveedoresContactoTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    ResultSet rs = null;
    PreparedStatement pst = null;

    public void AgregarProveedorContacto(String IdContacto, String IProveedor, String NombreContacto, String Detalles, String TelefonoContacto, String CorreoContacto){
        if((IdContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del contacto.","ID Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((IProveedor.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del proveedor.","ID Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((NombreContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del contacto.","Nombre Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((Detalles.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el detalle del contacto","Correo Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((TelefonoContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el telefono del contacto.","Telefono Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((CorreoContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del contacto","Correo Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("insert into contactoproveedor (IDProveedor, nombreDeContacto, Detalles, Telefono, Correo)values(?,?,?,?,?)");
            ps.setString(1, IProveedor);
            ps.setString(2, NombreContacto);
            ps.setString(3, Detalles);
            ps.setString(4, TelefonoContacto);
            ps.setString(5, CorreoContacto);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha guardado la información del contacto");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }

    }

    public void ActualizarProveedorContacto(String IdContacto, String IProveedor, String NombreContacto, String Detalles, String TelefonoContacto, String CorreoContacto){
        if((IdContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del contacto.","ID Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((IProveedor.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del proveedor.","ID Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((NombreContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del contacto.","Nombre Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((Detalles.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el detalle del contacto","Correo Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((TelefonoContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el telefono del contacto.","Telefono Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((CorreoContacto.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del contacto","Correo Contacto requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("update contactoproveedor set nombreDeContacto= '" + NombreContacto + "', Detalles= '"
                    + Detalles + "', Telefono= '" + TelefonoContacto + "',Correo= '"
                    + CorreoContacto + "' where IDContactoProveedor='" + IdContacto + "' ");
            ps.setString(1, IProveedor);
            ps.setString(2, NombreContacto);
            ps.setString(3, Detalles);
            ps.setString(4, TelefonoContacto);
            ps.setString(5, CorreoContacto);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actulizado la información del contacto");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al actualizar la información");
        }
    }

    public void EliminarProveedorContacto(String IdContacto){
        if (IdContacto.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null,"¡Debe seleccionar el ID del contacto que desea eliminar! "
                    + "\n","¡AVISO!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro del contacto " + IdContacto + "", "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {

            try {
                Statement st2 = conn.createStatement();
                String sql = "DELETE FROM contactoproveedor WHERE IDContactoProveedor = ?";

                int rs2 = st2.executeUpdate(sql);
                System.out.println(rs2);
                if(rs2 > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del contacto" + IdContacto + " correctamente");

                }else {
                    JOptionPane.showMessageDialog(null, "¡Error al eliminar la información!");

                }

            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al borrar la información del contacto.",
                        "¡Error al Borrar!", JOptionPane.ERROR_MESSAGE);
            }

        }

    }

    public void BuscarProveedorContacto(){
        try {
            String sql = "SELECT * FROM contactoproveedor";
            Statement stmt = conn.createStatement();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                String []datos= new String[2];
                datos[0] =rs.getString("IDContactoProveedor");
                datos[1] =rs.getString("nombreDeContacto");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}

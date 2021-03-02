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

        if(existecorreo(CorreoContacto)){
            return;
        }

        if(existeTelefono(TelefonoContacto)){
            return;
        }

        if(!validarLongitudMax(NombreContacto,35)){
            JOptionPane.showMessageDialog(null, "El nombre del contacto sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if(!validarLongitudMax(TelefonoContacto,8)){
            JOptionPane.showMessageDialog(null, "El teléfono del contacto debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(NombreContacto)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneLetras(TelefonoContacto)){
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números ");
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

            ps.execute();

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

        if(existecorreo(CorreoContacto)){
            return;
        }

        if(existeTelefono(TelefonoContacto)){
            return;
        }

        if(!validarLongitudMax(NombreContacto,35)){
            JOptionPane.showMessageDialog(null, "El nombre del contacto sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if(!validarLongitudMax(TelefonoContacto,8)){
            JOptionPane.showMessageDialog(null, "El teléfono del contacto debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(NombreContacto)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneLetras(TelefonoContacto)){
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números ");
            return;
        }

        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("update contactoproveedor set nombreDeContacto= '" + NombreContacto + "', Detalles= '"
                    + Detalles + "', Telefono= '" + TelefonoContacto + "',Correo= '"
                    + CorreoContacto + "' where IDContactoProveedor='" + IdContacto + "' ");

            ps.execute();
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

                String sql = "DELETE FROM contactoproveedor WHERE IDContactoProveedor = ?";
                PreparedStatement prest = conn.prepareStatement(sql);
                prest.setString(1, IdContacto);

                if(prest.executeUpdate() > 0){

                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del contacto: " + IdContacto + " correctamente");

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

    private boolean existecorreo(String correo) {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from contactoproveedor where Correo = '" + correo + "'";
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
            String sql = "Select * from contactoproveedor where Telefono = '" + Telefono + "'";
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
}

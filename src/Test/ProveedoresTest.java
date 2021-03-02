package Test;

import Controller.connect;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProveedoresTest {

    Connection conn = null;

    ProveedoresTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    ResultSet rs = null;
    PreparedStatement pst = null;

    public void AgregarProveedor(String idProveedor, String nombreP, String correoP, String direccionP){
        if((idProveedor.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del proveedor.","ID Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombreP.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del proveedor.","Nombre Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((correoP.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del proveedor","Correo Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccionP.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el direccion del proveedor","Direccion Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(existecorreo(correoP)){
            return;
        }

        if(!validarLongitudMax(nombreP,35)){
            JOptionPane.showMessageDialog(null, "El nombre del proveedor sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(direccionP,50)){
            JOptionPane.showMessageDialog(null, "La dirección del proveedor sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombreP)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneLetras(idProveedor)){
            JOptionPane.showMessageDialog(null, "El idProveedor solo debe contener números ");
            return;
        }


        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("insert into proveedores(empresaProveedor,correoProveedor,direccionProveedor)values(?,?,?)");
            ps.setString(1, nombreP);
            ps.setString(2, correoP);
            ps.setString(3, direccionP);

            int res = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha guardado la información del proveedor");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al guardar la información");
        }

    }

    public void ActualizarProveedor(String idProveedor, String nombreP, String correoP, String direccionP){
        if((idProveedor.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el ID del proveedor.","ID Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((nombreP.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el nombre del proveedor.","Nombre Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((correoP.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el correo del proveedor","Correo Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if((direccionP.equals(""))){
            javax.swing.JOptionPane.showMessageDialog(null,"Debe ingresar el direccion del proveedor","Direccion Proveedor requerido",javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(existecorreo(correoP)){
            return;
        }

        if(!validarLongitudMax(nombreP,35)){
            JOptionPane.showMessageDialog(null, "El nombre del proveedor sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(direccionP,50)){
            JOptionPane.showMessageDialog(null, "La dirección del proveedor sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombreP)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneLetras(idProveedor)){
            JOptionPane.showMessageDialog(null, "El idProveedor solo debe contener números ");
            return;
        }


        try {

            String sql = ("update PROVEEDORES set empresaProveedor= '" + nombreP + "', correoProveedor= '"
                                        + correoP + "', direccionProveedor= '" + direccionP + "' where IDProveedor='"
                                        + idProveedor + "' ");

            pst = conn.prepareStatement(sql);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Se ha actualizado la información del proveedor");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al actualizar la información");
        }


    }

    public void EliminarProveedor(String idProveedor){
        if (idProveedor.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(null,"¡Debe seleccionar el ID del proveedor que desea eliminar! "
                    + "\n","¡AVISO!",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar el registro del proveedor " + idProveedor + "", "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION) {

            try {

                String sql = "DELETE FROM PROVEEDORES WHERE IDProveedor = ?";
                PreparedStatement prest = conn.prepareStatement(sql);
                prest.setString(1, idProveedor);


                if(prest.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del empleado: " + idProveedor + " correctamente");

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

    public void BuscarProveedor(){
        try {
            String sql = "SELECT * FROM proveedores";
            Statement stmt = conn.createStatement();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {

                String []datos= new String[2];
                datos[0] =rs.getString("IDProveedor");
                datos[1] =rs.getString("empresaProveedor");
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    private boolean existecorreo(String correo) {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from proveedores where correoProveedor = '" + correo + "'";
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

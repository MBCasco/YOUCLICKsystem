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
        try {
            PreparedStatement ps;

            ps = conn.prepareStatement("update PROVEEDORES set empresaProveedor= '" + nombreP + "', correoProveedor= '"
                                        + correoP + "', direccionProveedor= '" + direccionP + "' where IDProveedor='"
                                        + idProveedor + "' ");
            ps.setString(1, nombreP);
            ps.setString(2, correoP);
            ps.setString(3, direccionP);

            int res = ps.executeUpdate();
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
                Statement st2 = conn.createStatement();
                String sql = "DELETE FROM PROVEEDORES WHERE IDProveedor = ?";

                int rs2 = st2.executeUpdate(sql);
                System.out.println(rs2);
                if(rs2 > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del empleado" + idProveedor + " correctamente");

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
}

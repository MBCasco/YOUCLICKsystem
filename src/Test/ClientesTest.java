package Test;

import Controller.connect;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Logger;

public class ClientesTest {

    Connection conn = null;
    PreparedStatement pst = null;

    ClientesTest() {
        try {
            this.conn = connect.conDB();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


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

        if(contieneLetras(sexo)){
            JOptionPane.showMessageDialog(null, "El género solo debe contener números ");
            return;
        }

        if(!validarSexo(sexo)){
            JOptionPane.showMessageDialog(null, " Sexo solo puede contener valores entre 1 y 2", "Valor", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(existecorreo(correo)){
            return;
        }

        if(existeTelefono(telefono)){
            return;
        }

        if(!validarLongitudMax(nombre,35)){
            JOptionPane.showMessageDialog(null, "El nombre del cliente sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(direccion,50)){
            JOptionPane.showMessageDialog(null, "La dirección del cliente sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(telefono,8)){
            JOptionPane.showMessageDialog(null, "El teléfono del cliente debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombre)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneLetras(telefono)){
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números ");
            return;
        }

        if(contieneLetras(idCliente)){
            JOptionPane.showMessageDialog(null, "El idCliente solo debe contener números ");
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

            ps.executeUpdate();
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
            conn = connect.conDB();

            try {

                String ssql = "DELETE FROM cliente WHERE IDCliente = ?";
                PreparedStatement prest = conn.prepareStatement(ssql);
                prest.setString(1, idCliente);

                if(prest.executeUpdate() > 0){
                    JOptionPane.showMessageDialog(null, "Se ha borrado la información del cliente: " + idCliente + " correctamente");

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

        if(contieneLetras(sexo)){
            JOptionPane.showMessageDialog(null, "El género solo debe contener números ");
            return;
        }

        if(!validarSexo(sexo)){
            JOptionPane.showMessageDialog(null, " Sexo solo puede contener valores entre 1 y 2", "Valor", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(existecorreo(correo)){
            return;
        }

        if(existeTelefono(telefono)){
            return;
        }

        if(!validarLongitudMax(nombre,35)){
            JOptionPane.showMessageDialog(null, "El nombre del cliente sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(direccion,50)){
            JOptionPane.showMessageDialog(null, "La dirección del cliente sobrepasa la longitud permitida", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(!validarLongitudMax(telefono,8)){
            JOptionPane.showMessageDialog(null, "El teléfono del cliente debe conter 8 dígitos", "Alcanzó el limite de caracteres permitidos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(contieneNumeros(nombre)){
            JOptionPane.showMessageDialog(null, "Ingrese un nombre que solo contenga letras");
            return;
        }

        if(contieneLetras(telefono)){
            JOptionPane.showMessageDialog(null, "El teléfono solo debe contener números ");
            return;
        }

        if(contieneLetras(idCliente)){
            JOptionPane.showMessageDialog(null, "El idCliente solo debe contener números ");
            return;
        }

        try {

            String sql = ("update cliente set nombreCliente= '" + nombre +
                    "', telefonoCliente= '" + telefono + "', dirreccionCliente= '" + direccion +
                    "', correoCliente= '" + correo + "', IDSexo= '" + sexo + " ' where IDCliente= '" + idCliente + "' ");

            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actualizado la información del cliente");
        } catch ( Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al Actualizar la información");
        }
    }

    public void BuscarCliente(){


        try {
            String sql = "SELECT * FROM cliente";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

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

    private boolean existecorreo(String correo) {
        try {
            Statement st = conn.createStatement();
            String sql = "Select * from cliente where correoCliente = '" + correo + "'";
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
            String sql = "Select * from cliente where telefonoCliente = '" + Telefono + "'";
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

    private boolean validarSexo(String genero){
        int sexo = Integer.parseInt(genero);
        if(sexo >= 1 && sexo <= 2){
            return true;
        }
        else{
            return false;
        }
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

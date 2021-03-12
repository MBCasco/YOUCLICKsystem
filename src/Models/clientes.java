package Models;

import javafx.scene.control.CheckBox;

public class clientes{

    int idCliente;
    int telefono;
    String  sexo;
    String nombre, direccion, correo;
    private CheckBox inhabilitar;


    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setSexo(String Sexo) {
        this.sexo = Sexo;
    }

    public int getIdCliente() { return idCliente; }

    public int getTelefono() {
        return telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getSexo() {
        return sexo;
    }

    public CheckBox getInhabilitar() {
        return inhabilitar;
    }

    public void setInhabilitar(CheckBox inhabilitar) {
        this.inhabilitar = inhabilitar;
    }


    public clientes(int idCliente, String nombre, int telefono, String direccion, String correo, String sexo) {
        this.idCliente = idCliente;
        this.telefono = telefono;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.sexo = sexo;
        this.inhabilitar = new CheckBox();
    }


}

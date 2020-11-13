package Controller;

public class clientes {

    int idCliente;
    int telefono;
    int sexo;
    String nombre, direccion, correo;


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

    public void setSexo(int Sexo) {
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

    public int getSexo() {
        return sexo;
    }

    public clientes(int idCliente, String nombre, String direccion, int telefono, String correo, int sexo) {
        this.idCliente = idCliente;
        this.telefono = telefono;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.sexo = sexo;
    }
}

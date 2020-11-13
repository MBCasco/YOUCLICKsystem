package Controller;

public class proveedores {
    int idProveedor;
    int idContacto;
    String nombreP, direccionP, correoP;


    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public void setDireccionP(String direccionP) {
        this.direccionP = direccionP;
    }

    public void setCorreoP(String correoP) {
        this.correoP = correoP;
    }

    public void setIdContacto(int idContacto) {
        this.idContacto = idContacto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getNombreP() {
        return nombreP;
    }

    public String getDireccionP() {
        return direccionP;
    }

    public String getCorreoP() {
        return correoP;
    }

    public int getIdContacto() {
        return idContacto;
    }

    public proveedores(int idProveedor, String nombreP, String direccionP, String correoP, int idContacto) {
        this.idProveedor = idProveedor;
        this.nombreP = nombreP;
        this.direccionP = direccionP;
        this.correoP = correoP;
        this.idContacto = idContacto;
    }
}

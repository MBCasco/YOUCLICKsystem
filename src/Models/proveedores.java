package Models;

public class proveedores {
    int idProveedor;
    String nombreP, correoP, direccionP;

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

    public proveedores(int idProveedor, String nombreP, String correoP, String direccionP) {
        this.idProveedor = idProveedor;
        this.nombreP = nombreP;
        this.correoP = correoP;
        this.direccionP = direccionP;
    }
}


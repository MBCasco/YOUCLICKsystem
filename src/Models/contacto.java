package Models;

public class contacto {
    int IdContacto;
    int TelefonoContacto;
    String Detalles;
    String nombreproveedor;
    String NombreContacto, CorreoContacto;

    public String getNombreproveedor() {
        return nombreproveedor;
    }

    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
    }

    public int getIdContacto() {
        return IdContacto;
    }

    public void setIdContacto(int idContacto) {
        this.IdContacto = idContacto;
    }

    public int getTelefonoContacto() {
        return TelefonoContacto;
    }

    public void setTelefonoContacto(int telefonoContacto) {
        this.TelefonoContacto = telefonoContacto;
    }

    public String getNombreContacto() {
        return NombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.NombreContacto = nombreContacto;
    }

    public String getCorreoContacto() {
        return CorreoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.CorreoContacto = correoContacto;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String detalles) {
        this.Detalles = detalles;
    }

    public contacto(int IdContacto, String IProveedor, String NombreContacto, String Detalles, int TelefonoContacto, String CorreoContacto) {
        this.IdContacto = IdContacto;
        this.nombreproveedor = IProveedor;
        this.NombreContacto = NombreContacto;
        this.Detalles = Detalles;
        this.TelefonoContacto = TelefonoContacto;
        this.CorreoContacto = CorreoContacto;
    }
}


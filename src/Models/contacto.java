package Models;

public class contacto {
    int IdContacto;
    int TelefonoContacto;
    String Detalles;
    int IProveedor;
    String NombreContacto, CorreoContacto;

    public int getIProveedor() {
        return IProveedor;
    }

    public void setIProveedor(int IProveedor) {
        this.IProveedor = IProveedor;
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

    public contacto(int IdContacto, int IProveedor,  String NombreContacto, String Detalles, int TelefonoContacto, String CorreoContacto) {
        this.IdContacto = IdContacto;
        this.IProveedor = IProveedor;
        this.NombreContacto = NombreContacto;
        this.Detalles = Detalles;
        this.TelefonoContacto = TelefonoContacto;
        this.CorreoContacto = CorreoContacto;
    }
}


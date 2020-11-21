package Models;

public class empleados {
    int idEmpleado;
    int telefonoE;
    String sexoE;
    String cargoE;
    String nombreE;
    String direccionE;
    String correoE;


    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setTelefonoE(int telefonoE) {
        this.telefonoE = telefonoE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE = nombreE;
    }


    public void setDireccionE(String direccionE) {
        this.direccionE = direccionE;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }

    public void setSexoE(String SexoE) {
        this.sexoE = SexoE;
    }

    public void setCargoE(String cargoE) {
        this.cargoE = cargoE;
    }

    public int getIdEmpleado() { return idEmpleado; }

    public int getTelefonoE() {
        return telefonoE;
    }

    public String getNombreE() {
        return nombreE;
    }

    public String getDireccionE() {
        return direccionE;
    }

    public String getCorreoE() {
        return correoE;
    }

    public String getSexoE() {
        return sexoE;
    }
    public String getCargoE() {
        return cargoE;
    }


    public empleados( int idEmpleado, String nombreE, String direccionE , int telefonoE, String correoE ,String cargoE, String sexoE ) {
        this.idEmpleado = idEmpleado;
        this.cargoE = cargoE;
        this.nombreE = nombreE;
        this.direccionE = direccionE;
        this.correoE = correoE;
        this.sexoE = sexoE;
        this.telefonoE = telefonoE;
    }
}
package Models;

public class cargoHistorico {

    int ID;
    String nombreEmpleado, Cargo, fechaInicial, fechaFinal;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        this.Cargo = cargo;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public cargoHistorico(int ID, String nombreEmpleado, String cargo, String fechaInicial, String fechaFinal) {
        this.ID = ID;
        this.nombreEmpleado = nombreEmpleado;
        this.Cargo = cargo;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
    }

}

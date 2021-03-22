package Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class empleados {
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getTelefonoE() {
        return telefonoE;
    }

    public void setTelefonoE(int telefonoE) {
        this.telefonoE = telefonoE;
    }

    public String getSexoE() {
        return sexoE;
    }

    public void setSexoE(String sexoE) {
        this.sexoE = sexoE;
    }

    public String getCargoE() {
        return cargoE;
    }

    public void setCargoE(String cargoE) {
        this.cargoE = cargoE;
    }

    public String getNombreE() {
        return nombreE.get();
    }

    public SimpleStringProperty nombreEProperty() {
        return nombreE;
    }

    public void setNombreE(String nombreE) {
        this.nombreE.set(nombreE);
    }

    public String getDireccionE() {
        return direccionE;
    }

    public void setDireccionE(String direccionE) {
        this.direccionE = direccionE;
    }

    public String getCorreoE() {
        return correoE;
    }

    public void setCorreoE(String correoE) {
        this.correoE = correoE;
    }



    int idEmpleado;
    int telefonoE;
    String sexoE;
    String cargoE;
    SimpleStringProperty nombreE;
    String direccionE;
    String correoE;


    public empleados( int idEmpleado, String nombreE, String direccionE , int telefonoE, String correoE ,String cargoE, String sexoE ) {
        this.idEmpleado = idEmpleado;
        this.cargoE = cargoE;
        this.nombreE = new SimpleStringProperty(nombreE);
        this.direccionE = direccionE;
        this.correoE = correoE;
        this.sexoE = sexoE;
        this.telefonoE = telefonoE;

    }

    @Override
    public String toString(){
        return nombreE.get();
    }
}
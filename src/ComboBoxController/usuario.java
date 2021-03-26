package ComboBoxController;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static Controller.connect.conDB;

public class usuario {
    private IntegerProperty IDAcceso;
    private StringProperty nombre;

    public usuario(Integer IDAcceso, String nombre) {
        this.IDAcceso= new SimpleIntegerProperty(IDAcceso);
        this.nombre = new SimpleStringProperty(nombre);
    }

    public int getIDAcceso() {
        return IDAcceso.get();
    }

    public IntegerProperty IDAccesoProperty() {
        return IDAcceso;
    }

    public void setIDAcceso(int IDAcceso) {
        this.IDAcceso.set(IDAcceso);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public static ObservableList<usuario> getUsuario(){
        Connection conn = conDB();
        ObservableList<usuario> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM acceso");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new usuario(Integer.parseInt(rs.getString("IDAcceso")), rs.getString("nombreUsuario")));
            }
        }catch(Exception e){
        }
        return list;
    }

    @Override
    public String toString(){
        return nombre.get();
    }
}

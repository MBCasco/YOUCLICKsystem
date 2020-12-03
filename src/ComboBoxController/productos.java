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

public class productos {
    private IntegerProperty IDProducto;
    private StringProperty nombre;

    public productos(Integer IDProducto, String nombre) {
        this.IDProducto = new SimpleIntegerProperty(IDProducto);
        this.nombre = new SimpleStringProperty(nombre);
    }

    public int getIDProducto() {
        return IDProducto.get();
    }

    public IntegerProperty IDProductoProperty() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto.set(IDProducto);
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
    public static ObservableList<productos> getproductos(){
        Connection conn = conDB();
        ObservableList<productos> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDProducto, p.nombre FROM producto AS p;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new productos(Integer.parseInt(rs.getString("IDProducto")), rs.getString("nombre")));
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

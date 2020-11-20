package Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import static Controller.connect.conDB;

public class marca {
    private IntegerProperty IDMarca;
    private StringProperty nombreMarca;

    public marca(Integer IDMarca, String nombreMarca){
        this.IDMarca = new SimpleIntegerProperty(IDMarca);
        this.nombreMarca = new SimpleStringProperty(nombreMarca);
    }

    public int getIDMarca() {
        return IDMarca.get();
    }

    public IntegerProperty IDMarcaProperty() {
        return IDMarca;
    }

    public void setIDMarca(int IDMarca) {
        this.IDMarca.set(IDMarca);
    }

    public String getNombreMarca() {
        return nombreMarca.get();
    }

    public StringProperty nombreMarcaProperty() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca.set(nombreMarca);
    }

    public static ObservableList<marca> getdatamarca(){
        Connection conn = conDB();
        ObservableList<marca> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM marca");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new marca(Integer.parseInt(rs.getString("IDMarca")), rs.getString("nombreMarca")));
            }
        }catch(Exception e){
        }

        return list;
    }

    @Override
    public String toString(){
        return nombreMarca.get();
    }
}

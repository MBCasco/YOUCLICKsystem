package Controller;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import static Controller.connect.conDB;


public class categoria {
    private IntegerProperty IDCategoria;
    private StringProperty nombreCategoria;


    public categoria (Integer IDCategoria, String nombreCategoria){
        this.IDCategoria = new SimpleIntegerProperty(IDCategoria);
        this.nombreCategoria = new SimpleStringProperty(nombreCategoria);
    }

    public int getIDCategoria() {
        return IDCategoria.get();
    }

    public IntegerProperty IDCategoriaProperty() {
        return IDCategoria;
    }

    public void setIDCategoria(int IDCategoria) {
        this.IDCategoria.set(IDCategoria);
    }

    public String getnombreCategoria() {
        return nombreCategoria.get();
    }

    public StringProperty nombreCategoriaProperty() {
        return nombreCategoria;
    }

    public void setnombreCategoria(String nombreCategoria) {
        this.nombreCategoria.set(nombreCategoria);
    }

    public static ObservableList<categoria> getdatacategoria(){
        Connection conn = conDB();
        ObservableList<categoria> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new categoria(Integer.parseInt(rs.getString("IDCategoria")), rs.getString("nombreCategoria")));
            }
        }catch(Exception e){
        }
        return list;
    }

    @Override
    public String toString(){
        return nombreCategoria.get();
    }

}

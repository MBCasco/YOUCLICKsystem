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

public class TipoPago {
    private IntegerProperty IDTipoPago;
    private StringProperty desTipoPago;

    public int getIDTipoPago() {
        return IDTipoPago.get();
    }

    public IntegerProperty IDTipoPagoProperty() {
        return IDTipoPago;
    }

    public void setIDTipoPago(int IDTipoPago) {
        this.IDTipoPago.set(IDTipoPago);
    }

    public String getDesTipoPago() {
        return desTipoPago.get();
    }

    public StringProperty desTipoPagoProperty() {
        return desTipoPago;
    }

    public void setDesTipoPago(String desTipoPago) {
        this.desTipoPago.set(desTipoPago);
    }

    public TipoPago(Integer IDTipoPago, String desTipoPago){
        this.IDTipoPago = new SimpleIntegerProperty(IDTipoPago);
        this.desTipoPago = new SimpleStringProperty(desTipoPago);
    }

    public static ObservableList<TipoPago> getdataTP(){
        Connection conn = conDB();
        ObservableList<TipoPago> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tipopago");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new TipoPago(Integer.parseInt(rs.getString("IDTipoPago")), rs.getString("desTipoPago")));
            }
        }catch(Exception e){
        }

        return list;
    }

    @Override
    public String toString(){
        return desTipoPago.get();
    }

}

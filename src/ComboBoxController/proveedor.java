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

public class proveedor {
    private IntegerProperty IDProveedor;
    private StringProperty empresaProveedor;

    public proveedor(Integer IDProveedor, String empresaProveedor){
        this.IDProveedor = new SimpleIntegerProperty(IDProveedor);
        this.empresaProveedor = new SimpleStringProperty(empresaProveedor);
    }

    public int getIDProveedor() {return IDProveedor.get(); }

    public IntegerProperty IDProveedorProperty() {return IDProveedor; }

    public void setIDProveedor(int IDProveedor) {this.IDProveedor.set(IDProveedor);}

    public String getEmpresaProveedor() {return empresaProveedor.get(); }

    public StringProperty empresaProveedorProperty() {return empresaProveedor;}

    public void setEmpresaProveedor(String empresaProveedor) {this.empresaProveedor.set(empresaProveedor);}

    public static ObservableList<proveedor> getproveedor(){
        Connection conn = conDB();
        ObservableList<proveedor> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDProveedor, p.empresaProveedor FROM proveedores AS p");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new proveedor(Integer.parseInt(rs.getString("IDProveedor")), rs.getString("empresaProveedor")));
            }
        }catch(Exception e){
        }

        return list;
    }

    @Override
    public String toString(){
        return empresaProveedor.get();
    }
}

package ComboBoxController;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class marca {

    public IntegerProperty IDMarca;
    public StringProperty nombreMarca;

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

    @Override
    public String toString(){
        return nombreMarca.get();
    }

}

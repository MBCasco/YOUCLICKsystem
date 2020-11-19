package Controller;

public class precioHistorico {
    int ID;
    double precio;
    String fechaInicial, fechaFinal;
    String nombre;


    public void setId(int ID) {
        this.ID = ID;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public int getID() { return ID; }

    public double getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }


    public String getFechaInicial() {
        return fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public precioHistorico(int ID, String nombre, double precio, String fechaInicial, String fechaFinal) {
        this.ID = ID;
        this.precio = precio;
        this.nombre = nombre;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
    }
}

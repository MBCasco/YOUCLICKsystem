package Models;


public class detallefactura {

    int IDProducto;
    int IDDetalleFactura;

    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public int getIDDetalleFactura() {
        return IDDetalleFactura;
    }

    public void setIDDetalleFactura(int IDDetalleFactura) {
        this.IDDetalleFactura = IDDetalleFactura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    String nombre;
    double precio;
    int cantidad;



     public detallefactura(int IDProducto, int IDDetalleFactura, String nombre, double precio, int cantidad){

         this.IDProducto = IDProducto;
         this.IDDetalleFactura = IDDetalleFactura;
         this.nombre = nombre;
         this.precio = precio;
         this.cantidad = cantidad;

     }
}

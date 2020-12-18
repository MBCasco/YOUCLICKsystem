package Models;

public class detallecompra {
    int IDProducto;
    String empresaProveedor, nombre;
    int cantidad;
    int IDDetalleCompra;


    public int getIDProducto() {
        return IDProducto;
    }

    public String getEmpresaProveedor() {
        return empresaProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getIDDetalleCompra() {
        return IDDetalleCompra;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public void setEmpresaProveedor(String empresaProveedor) {
        this.empresaProveedor = empresaProveedor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setIDDetalleCompra(int IDDetalleCompra) {
        this.IDDetalleCompra = IDDetalleCompra;
    }

    public detallecompra(int IDProducto, String empresaProveedor, String nombre, int cantidad, int IDDetalleCompra) {
        this.IDProducto = IDProducto;
        this.empresaProveedor = empresaProveedor;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.IDDetalleCompra = IDDetalleCompra;
    }
}

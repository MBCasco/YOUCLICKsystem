package Models;

public class reporte {
    int IDCompra, cantidad;
    String empresaProveedor, nombre, desTipoPago, FechaPedido, FechaLlegada;
    double totalPago;

    public int getIDCompra() {
        return IDCompra;
    }

    public void setIDCompra(int IDCompra) {
        this.IDCompra = IDCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEmpresaProveedor() {
        return empresaProveedor;
    }

    public void setEmpresaProveedor(String empresaProveedor) {
        this.empresaProveedor = empresaProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesTipoPago() {
        return desTipoPago;
    }

    public void setDesTipoPago(String desTipoPago) {
        this.desTipoPago = desTipoPago;
    }

    public String getFechaPedido() {
        return FechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        FechaPedido = fechaPedido;
    }

    public String getFechaLlegada() {
        return FechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        FechaLlegada = fechaLlegada;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public reporte(int IDCompra, String empresaProveedor, String nombre, double totalPago, String desTipoPago, int cantidad,  String fechaPedido, String fechaLlegada) {
        this.IDCompra = IDCompra;
        this.empresaProveedor = empresaProveedor;
        this.nombre = nombre;
        this.totalPago = totalPago;
        this.desTipoPago = desTipoPago;
        this.cantidad = cantidad;
        FechaPedido = fechaPedido;
        FechaLlegada = fechaLlegada;
    }
}

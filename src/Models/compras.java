package Models;

public class compras {
    int IDCompra, IDProveedor, IDProducto, IDDetalleCompra, IDPago;
    String FechaPedido, FechaLlegada;
    double totalCompra, impuesto, subtotalCompra;


    public int getIDCompra() {
        return IDCompra;
    }

    public void setIDCompra(int IDCompra) {
        this.IDCompra = IDCompra;
    }

    public int getIDProveedor() {
        return IDProveedor;
    }

    public void setIDProveedor(int IDProveedor) {
        this.IDProveedor = IDProveedor;
    }

    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public int getIDDetalleCompra() {
        return IDDetalleCompra;
    }

    public void setIDDetalleCompra(int IDDetalleCompra) {
        this.IDDetalleCompra = IDDetalleCompra;
    }

    public int getIDPago() {
        return IDPago;
    }

    public void setIDPago(int IDPago) {
        this.IDPago = IDPago;
    }

    public String getFechaPedido() {
        return FechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.FechaPedido = fechaPedido;
    }

    public String getFechaLlegada() {
        return FechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        this.FechaLlegada = fechaLlegada;
    }

    public String getTotalCompra() {
        return String.valueOf(totalCompra);
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public String getImpuesto() {
        return String.valueOf(impuesto);
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public String getSubtotalCompra() {
        return String.valueOf(subtotalCompra);
    }

    public void setSubtotalCompra(double subtotalCompra) {
        this.subtotalCompra = subtotalCompra;
    }

    public compras(int IDCompra, String fechaPedido, String fechaLlegada, int IDProveedor, int IDProducto, int IDDetalleCompra, int IDPago,  double totalCompra, double impuesto, double subtotalCompra) {
        this.IDCompra = IDCompra;
        this.FechaPedido = fechaPedido;
        this.FechaLlegada = fechaLlegada;
        this.IDProveedor = IDProveedor;
        this.IDProducto = IDProducto;
        this.IDDetalleCompra = IDDetalleCompra;
        this.IDPago = IDPago;
        this.totalCompra = totalCompra;
        this.impuesto = impuesto;
        this.subtotalCompra = subtotalCompra;
    }
}

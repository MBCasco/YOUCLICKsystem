package Models;

public class compras {
int idCompra;
String empresaProveedor, nombreProducto, fechaP, fechaR;
int cantidad;

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getempresaProveedor() {
        return empresaProveedor;
    }

    public void setempresaProveedor(String empresaProveedor) {
        this.empresaProveedor = empresaProveedor;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getFechaP() {
        return fechaP;
    }

    public void setFechaP(String fechaP) {
        this.fechaP = fechaP;
    }

    public String getFechaR() {
        return fechaR;
    }

    public void setFechaR(String fechaR) {
        this.fechaR = fechaR;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public compras(int idCompra, String empresaProveedor, String nombreProducto, int cantidad, String fechaP, String fechaR ) {
        this.idCompra = idCompra;
        this.empresaProveedor = empresaProveedor;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.fechaP = fechaP;
        this.fechaR = fechaR;

    }
}

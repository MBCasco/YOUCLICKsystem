package Models;

public class factura {

    public int getIDFactura() {
        return IDFactura;
    }

    public void setIDFactura(int IDFactura) {
        this.IDFactura = IDFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getIDDetalleFactura() {
        return IDDetalleFactura;
    }

    public void setIDDetalleFactura(int IDDetalleFactura) {
        this.IDDetalleFactura = IDDetalleFactura;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }

    public int getIDEmpleado() {
        return IDEmpleado;
    }

    public void setIDEmpleado(int IDEmpleado) {
        this.IDEmpleado = IDEmpleado;
    }

    public String getTotalFactura() {
        return  String.valueOf(totalFactura);
    }

    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getImpuesto() {
        return  String.valueOf(impuesto);
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public String getSubtotalFactura() {

        return  String.valueOf(subtotalFactura);
    }

    public void setSubtotalFactura(double subtotalFactura) {
        this.subtotalFactura = subtotalFactura;
    }

    public int getIDPago() {
        return IDPago;
    }

    public void setIDPago(int IDPago) {
        this.IDPago = IDPago;
    }

    int IDFactura;
    String fechaFactura;
    int IDDetalleFactura;
    int IDCliente;
    int IDEmpleado;
    double totalFactura;
    double impuesto;
    double subtotalFactura;
    int IDPago;



    public factura( int IDFactura, String fechaFactura, int IDDetalleFactura, int IDCliente, int IDEmpleado, double totalFactura, double impuesto, double subtotalFactura, int IDPago){
        this.IDFactura = IDFactura;
        this.fechaFactura = fechaFactura;
        this.IDDetalleFactura = IDDetalleFactura;
        this.IDCliente = IDCliente;
        this.IDEmpleado = IDEmpleado;
        this.totalFactura = totalFactura;
        this.impuesto = impuesto;
        this.subtotalFactura = subtotalFactura;
        this.IDPago = IDPago;
    }

}

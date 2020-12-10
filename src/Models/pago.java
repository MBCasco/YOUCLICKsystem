package Models;

public class pago {
    int IDPago, IDCompra;
    String desTipoPago;
    double cantidadPagada, porcentajePagado, totalPago;

    public pago(int IDPago, int IDCompra, String desTipoPago, double cantidadPagada, double porcentajePagado, double totalPago) {
        this.IDPago = IDPago;
        this.IDCompra = IDCompra;
        this.desTipoPago = desTipoPago;
        this.cantidadPagada = cantidadPagada;
        this.porcentajePagado = porcentajePagado;
        this.totalPago = totalPago;
    }

    public int getIDPago() {
        return IDPago;
    }

    public void setIDPago(int IDPago) {
        this.IDPago = IDPago;
    }

    public int getIDCompra() {
        return IDCompra;
    }

    public void setIDCompra(int IDCompra) {
        this.IDCompra = IDCompra;
    }

    public String getDesTipoPago() {
        return desTipoPago;
    }

    public void setDesTipoPago(String desTipoPago) {
        this.desTipoPago = desTipoPago;
    }

    public double getCantidadPagada() {
        return cantidadPagada;
    }

    public void setCantidadPagada(double cantidadPagada) {
        this.cantidadPagada = cantidadPagada;
    }

    public double getPorcentajePagado() {
        return porcentajePagado;
    }

    public void setPorcentajePagado(double porcentajePagado) {
        this.porcentajePagado = porcentajePagado;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

}

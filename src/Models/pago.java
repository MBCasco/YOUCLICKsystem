package Models;

public class pago {


    public int getIDPago() {
        return IDPago;
    }

    public void setIDPago(int IDPago) {
        this.IDPago = IDPago;
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

    int IDPago;
    String desTipoPago;
    double cantidadPagada;
    double porcentajePagado;
    double totalPago;

    public pago(int IDPago, String desTipoPago, double cantidadPagada, double porcentajePagado, double totalPago) {
        this.IDPago=IDPago;
        this.desTipoPago=desTipoPago;
        this.cantidadPagada=cantidadPagada;
        this.porcentajePagado=porcentajePagado;
        this.totalPago=totalPago;
    }


}

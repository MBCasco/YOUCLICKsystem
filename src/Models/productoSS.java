package Models;

public class productoSS {
    int idpro, stock;
    String nombrepro;
    double preciopro;

    public int getIdpro() {
        return idpro;
    }

    public void setIdpro(int idpro) {
        this.idpro = idpro;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombrepro() {
        return nombrepro;
    }

    public void setNombrepro(String nombrepro) {
        this.nombrepro = nombrepro;
    }

    public double getPreciopro() {
        return preciopro;
    }

    public void setPreciopro(double preciopro) {
        this.preciopro = preciopro;
    }

    public productoSS(int idpro, String nombrepro, double preciopro, int stock) {
        this.idpro = idpro;
        this.nombrepro = nombrepro;
        this.preciopro = preciopro;
        this.stock = stock;
    }
}

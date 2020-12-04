package Controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import java.io.IOException;

public class MenuController implements GlobalConstans {


    public  void cliente(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaClientes.fxml"));
        stage.setTitle("Cliente");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();

    }
    public  void empleado(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaEmpleados.fxml"));
        stage.setTitle("Empleado");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }
    public  void proveedor() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaProveedores.fxml"));
        stage.setTitle("Proveedor");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }
    public  void producto(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaProductos.fxml"));
        stage.setTitle("Producto");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }
    public  void inventario(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaInventario.fxml"));
        stage.setTitle("inventario");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }
    public  void factura(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaFactura.fxml"));
        stage.setTitle("Factura");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }
    public  void contactos() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaProveedoresContacto.fxml"));
        stage.setTitle("Contactos");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }
    public  void pago() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaPago.fxml"));
        stage.setTitle("Pago");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }
    public  void compra() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaCompras.fxml"));
        stage.setTitle("Compras");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }


}

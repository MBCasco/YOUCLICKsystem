package Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable, GlobalConstans{

    @FXML
    private Button cliente;
    @FXML
    private Button empleado;
    @FXML
    private Button proveedor;
    @FXML
    private Button producto;
    @FXML
    private Button inventario;
    @FXML
    private Button factura;
    @FXML
    private Button btn_accesos;

    Connection conn = null;
    java.sql.PreparedStatement pst = null;
    private ResultSet rs;
    private ResultSet rs2;
    final Calendar calendar = Calendar.getInstance();
    final Date date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(date);

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
    public  void report() throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaReporteCompra.fxml"));
        stage.setTitle("Reporte");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

    public  void permisos() throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaAccesos.fxml"));
        stage.setTitle("Control de accesos");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

    public void activarPermisos(){
        try {
            conn = connect.conDB();
            String sql2 = "SELECT a.usuario, a.TipoUsuario FROM admins AS a;";
            pst = conn.prepareStatement(sql2);
            rs = pst.executeQuery();
            if(rs.next()){
                if(rs.getString("TipoUsuario").equals("admin")){
                    this.btn_accesos.setVisible(true);
                }
            }

            String sql = "select * from acceso";
            pst = conn.prepareStatement(sql);
            rs2 = pst.executeQuery();
            if(rs2.next()){
                if(rs2.getString("Clientes").equals("1")){
                    cliente.setDisable(false);
                }
                if(rs2.getString("Empleados").equals("1")){
                    empleado.setDisable(false);
                }
                if(rs2.getString("Proveedores").equals("1")){
                    proveedor.setDisable(false);
                }
                if(rs2.getString("Productos").equals("1")){
                    producto.setDisable(false);
                }
                if(rs2.getString("Inventario").equals("1")){
                    inventario.setDisable(false);
                }
                if(rs2.getString("Facturas").equals("1")){
                   factura.setDisable(false);
                }

            }
        } catch (SQLException e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\PRINCIPAL_" + fecha + ".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
            }
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activarPermisos();
       btn_accesos.setVisible(false);
    }
}

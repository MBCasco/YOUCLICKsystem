
package Controller;
import Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class connect {
    Connection conn = null;

    public static Connection conDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria","root","qwerty123456789");
            return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    public static ObservableList<clientes> getdataclientes(){
        Connection conn = conDB();
        ObservableList<clientes> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT c.IDCliente, c.nombreCliente, c.dirreccionCliente, c.telefonoCliente, c.correoCliente, s.descripcionSexo FROM cliente AS c INNER JOIN sexo AS s ON s.IDSexo = c.IDSexo");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new clientes( Integer.parseInt(rs.getString("IDCliente")), rs.getString("nombreCliente"),  Integer.parseInt(rs.getString("telefonoCliente")), rs.getString("dirreccionCliente"), rs.getString("correoCliente"), rs.getString("descripcionSexo")));
            }
        }catch(Exception e){
        }
        return list;
    }


    public static ObservableList<producto> getdataproducto(){
        Connection conn = conDB();
        ObservableList<producto> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDProducto, p.nombre, i.stock, p.descripcionProducto, i.ubicacion, p.precio, m.nombreMarca, c.nombreCategoria FROM producto AS p INNER JOIN inventario AS i ON i.IDProducto = p.IDProducto INNER JOIN marca AS m ON m.IDMarca = p.IDMarca INNER JOIN categoria AS c ON p.IDCategoria = c.IDCategoria;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new producto (Integer.parseInt(rs.getString("IDProducto")), rs.getString("nombre"), Integer.parseInt(rs.getString("stock")), rs.getString("descripcionProducto"), rs.getString("ubicacion"), Double.parseDouble(rs.getString("precio")), rs.getString("nombreMarca"), rs.getString("nombreCategoria")));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<precioHistorico> getdataprecioHistorico() {
        Connection conn = conDB();
        ObservableList<precioHistorico> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRECIOHISTORICO");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new precioHistorico( Integer.parseInt(rs.getString("IDPrecioHistorico")), rs.getString("nombre"),Double.parseDouble(rs.getString("precio")) , rs.getString("FechaInicial"), rs.getString("FechaFinal")));
            }
        }catch(Exception e){
        }
        return list;

    }
    public static ObservableList<proveedores> getdataproveedor(){
        Connection conn = conDB();
        ObservableList<proveedores> list = FXCollections.observableArrayList();
        try{
            PreparedStatement psp = conn.prepareStatement("select * from proveedores");
            ResultSet rsP = psp.executeQuery();
            while(rsP.next()){
                list.add(new proveedores(rsP.getInt("IDProveedor"),rsP.getString("empresaProveedor"), rsP.getString("correoProveedor"), rsP.getString("direccionProveedor")));
            }
        }catch(Exception e){
        }
        return list;
    }


    public static ObservableList<contacto> getdatacontacto(int value){
        Connection conn = conDB();
        ObservableList<contacto> list = FXCollections.observableArrayList();
        try{
            PreparedStatement psc = conn.prepareStatement("SELECT * FROM contactoproveedor where IDProveedor = ?");
            psc.setInt(1,value);
            ResultSet rsP = psc.executeQuery();

            while(rsP.next()){
                list.add(new contacto( Integer.parseInt(rsP.getString("IDContactoProveedor")), Integer.parseInt(rsP.getString("IDProveedor")), rsP.getString("nombreDeContacto"), rsP.getString("Detalles"), Integer.parseInt(rsP.getString("Telefono")), rsP.getString("Correo")));
            }
        }catch(Exception e){
        }
        return list;
    }
    public static ObservableList<empleados> getdataempleados(){
        Connection conn = conDB();
        ObservableList<empleados> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT e.IDEmpleado, e.nombreEmpleado, e.direccionEmpleado, e.telefonoEmpleado, e.correoEmpleado, c.nombreCargo, s.descripcionSexo FROM empleado AS e INNER JOIN cargo AS c ON c.IDCargo = e.IDCargo INNER JOIN sexo AS s ON s.IDSexo = e.IDSexo;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new empleados(Integer.parseInt(rs.getString("IDEmpleado")),  rs.getString("nombreEmpleado"),rs.getString("direccionEmpleado"),Integer.parseInt(rs.getString("telefonoEmpleado")), rs.getString("correoEmpleado"), rs.getString("nombreCargo"), rs.getString("descripcionSexo")));
            }
        }catch(Exception e){
        }
        return list;
    }
    public static ObservableList<inventario> getdatainventario(){
        Connection conn = conDB();
        ObservableList<inventario> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDProducto, p.nombre, i.stock, p.descripcionProducto, i.ubicacion, p.precio, m.nombreMarca, c.nombreCategoria FROM producto AS p INNER JOIN inventario AS i ON i.IDProducto = p.IDProducto INNER JOIN marca AS m ON m.IDMarca = p.IDMarca INNER JOIN categoria AS c ON p.IDCategoria = c.IDCategoria;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new inventario (Integer.parseInt(rs.getString("IDProducto")), rs.getString("nombre"), Integer.parseInt(rs.getString("stock")), rs.getString("descripcionProducto"), rs.getString("ubicacion"), Double.parseDouble(rs.getString("precio")), rs.getString("nombreMarca"), rs.getString("nombreCategoria")));
            }
        }catch(Exception e){
        }
        return list;
    }
    public static ObservableList<compras> getdatacompras(){
        Connection conn = conDB();
        ObservableList<compras> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT c.IDCompra, c.cantidad, c.FechaPedido, c.FechaLlegada, p.empresaProveedor, pr.nombre FROM compra AS c INNER JOIN proveedores AS p ON p.IDProveedor = c.IDProveedor INNER JOIN producto AS pr ON pr.IDProducto = c.IDProducto");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new compras (Integer.parseInt(rs.getString("IDCompra")), Integer.parseInt(rs.getString("cantidad")), rs.getString("fechaPedido"), rs.getString("fechaLlegada"), rs.getString("empresaProveedor"), rs.getString("nombre")));
            }
        }catch(Exception e){
        }
        return list;
    }



    public static ObservableList<String> getdatasexo(){
        Connection conn = conDB();
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT descripcionSexo FROM sexo");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new String((rs.getString("descripcionSexo"))));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<String> getdatacargo(){
        Connection conn = conDB();
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT nombreCargo FROM cargo");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new String((rs.getString("nombreCargo"))));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<cargoHistorico> getdatacargoHistorico() {
        Connection conn = conDB();
        ObservableList<cargoHistorico> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT c.IDCargoHistorico, e.nombreEmpleado, ca.nombreCargo, c.fechaInicial, c.fechaFinal  FROM cargohistorico AS c INNER JOIN empleado AS e ON e.nombreEmpleado = c.nombreEmpleado INNER JOIN cargo AS ca ON ca.IDCargo = c.IDCargo");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new cargoHistorico( Integer.parseInt(rs.getString("IDCargoHistorico")), rs.getString("nombreEmpleado"),rs.getString("nombreCargo") , rs.getString("fechaInicial"), rs.getString("fechaFinal")));
            }
        }catch(Exception e){
        }
        return list;

    }

    public static ObservableList<detallefactura> getDataDetalleFacturat(int x) {

        Connection conn = conDB();
        ObservableList<detallefactura> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement(  "SELECT  t2.IDProducto,t1.IDDetalleFactura,t2.nombre,t2.precio,t1.cantidad\n" +
                    "FROM\n" +
                    "    detallefacturat as t1\n" +
                    "INNER JOIN producto as t2 \n" +
                    "    ON t1.IDProducto = t2.IDProducto where IDFactura = ?; ");
            ps.setInt(1, x);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new detallefactura(rs.getInt("IDProducto"),rs.getInt("IDDetalleFactura"),rs.getString("nombre"),rs.getDouble("precio"),rs.getInt("cantidad") ));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<factura> getDataFacturat() {

        Connection conn = conDB();
        ObservableList<factura> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement(  "select * from facturat");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new factura(rs.getInt("IDFactura"), rs.getString("fechaFactura"), rs.getInt("IDDetalleFactura"), rs.getInt("IDCliente"), rs.getInt("IDEmpleado"), rs.getDouble("totalFactura"), rs.getDouble("impuesto"), rs.getDouble("subtotalFactura"), rs.getInt("IDPago")));
            }
        }catch(Exception e){
        }
        return list;
    }
}


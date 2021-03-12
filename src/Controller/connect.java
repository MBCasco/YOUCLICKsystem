
package Controller;
import ComboBoxController.marca;
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
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria","root","");
            return conn;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }

    public static ObservableList<marca> getdatamarca(){
        Connection conn = conDB();
        ObservableList<marca> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM marca");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new marca(Integer.parseInt(rs.getString("IDMarca")), rs.getString("nombreMarca")));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        return list;
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
            JOptionPane.showMessageDialog(null, e);
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
            PreparedStatement ps = conn.prepareStatement("SELECT ph.IDPrecioHistorico, p.nombre, ph.precio, ph.fechaInicial, ph.fechaFinal FROM PRECIOHISTORICO AS ph \n" +
                    "INNER JOIN producto AS p ON p.IDProducto = ph.IDProducto;");
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
            PreparedStatement psc = conn.prepareStatement(" SELECT c.IDContactoProveedor, p.empresaProveedor, c.nombreDeContacto, c.Detalles, c.Telefono, c.Correo FROM \n" +
                    " contactoproveedor as c  \n" +
                    " inner join proveedores as p on c.IDProveedor = p.IDProveedor\n" +
                    "where  c.IDProveedor = ?");
            psc.setInt(1,value);
            ResultSet rsP = psc.executeQuery();

            while(rsP.next()){
                list.add(new contacto(rsP.getInt("IDContactoProveedor"), rsP.getString("empresaProveedor"),rsP.getString("nombreDeContacto"),rsP.getString("Detalles"), rsP.getInt("Telefono"),rsP.getString("Correo")));
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

    public static ObservableList<compras> getdatacomprast(){
        Connection conn = conDB();
        ObservableList<compras> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM comprat");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new compras (rs.getInt("IDCompra"), rs.getString("FechaPedido"),rs.getString("FechaLlegada"), rs.getInt("IDProveedor"), rs.getInt("IDProducto"), rs.getInt("IDDetalleCompra"), rs.getInt("IDPago"), rs.getDouble("totalCompra"), rs.getDouble("impuesto"), rs.getDouble("subtotalCompra")));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<pago> getdatapagoF(int value){
        Connection conn = conDB();
        ObservableList<pago> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("select d.IDDetalleDePago as IDPago, tp.desTipoPago as desTipoPago, d.cantidadPagada as cantidadPagada, d.porcentajePagado as porcentajePagado, p.totalPago as totalPago\n" +
                    "                    from facturat as f\n" +
                    "                    inner join detalledepagofacturat as d on d.IDPago = f.IDFactura\n" +
                    "                    inner join tipopago as tp on tp.IDTipoPago = d.IDTipoPago\n" +
                    "                    inner join pagofacturat as p on p.IDFactura = f.IDFactura\n" +
                    "                    where f.IDFactura = ?");
            ps.setInt(1, value);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new pago(rs.getInt("IDPago"),rs.getString("desTipoPago"),rs.getDouble("cantidadPagada"),rs.getDouble("porcentajePagado"),rs.getDouble("totalPago")));
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
            PreparedStatement ps = conn.prepareStatement("SELECT c.IDCargoHistorico, e.nombreEmpleado, ca.nombreCargo, c.fechaInicial, c.fechaFinal  FROM cargohistorico AS c INNER JOIN empleado AS e ON e.IDEmpleado = c.IDEmpleado INNER JOIN cargo AS ca ON ca.IDCargo = c.IDCargo;");
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

    public static ObservableList<String> pagoAcumulado(int x) {

        Connection conn = conDB();
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement(  "select sum(cantidadPagada) from detalledepagofacturat where IDpago = ?");
            ps.setInt(1, x);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(String.valueOf(new Double (rs.getDouble("sum(cantidadPagada)"))));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<detallecompra> getDataComprat(int x){
        Connection conn = conDB();
        ObservableList<detallecompra> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT  p.IDProducto, pr.empresaProveedor, p.nombre, dct.cantidad, dct.IDDetalleCompra FROM detallecomprat as dct INNER JOIN producto as p ON dct.IDProducto = p.IDProducto INNER JOIN proveedores as pr ON pr.IDProveedor = dct.IDProveedor WHERE IDCompra = ?");
            ps.setInt(1, x);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new detallecompra(rs.getInt("IDProducto"), rs.getString("empresaProveedor"), rs.getString("nombre"), rs.getInt("Cantidad"), rs.getInt("IDDetalleCompra")));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return list;
    }

    public static ObservableList<productoSS> getDataProductoSS(){
        Connection conn = conDB();
        ObservableList<productoSS> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDProducto, p.nombre, p.precio, i.stock \n" +
                    "FROM producto AS p \n" +
                    "INNER JOIN inventario AS i ON i.IDProducto = p.IDProducto;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new productoSS (Integer.parseInt(rs.getString("IDProducto")), rs.getString("nombre"), rs.getDouble("precio"),  Integer.parseInt(rs.getString("stock"))));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<pago> getdatapago(int value){
        Connection conn = conDB();
        ObservableList<pago> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT p.IDPago, tp.desTipoPago, d.cantidadPagada, d.porcentajePagado, p.totalPago FROM pagocomprat AS p \n" +
                    "INNER JOIN detalledepagocomprat AS d ON d.IDPago = p.IDPago \n" +
                    "INNER JOIN tipopago AS tp ON tp.IDTipoPago = d.IDTipoPago WHERE IDCompra = ?");
            ps.setInt(1, value);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new pago(Integer.parseInt(rs.getString("IDPago")), rs.getString("desTipoPago"), Double.parseDouble(rs.getString("cantidadPagada")), Double.parseDouble(rs.getString("porcentajePagado")), Double.parseDouble(rs.getString("totalPago"))));
            }
        }catch(Exception e){
        }
        return list;
    }

    public static ObservableList<reporte> getDataReporte(){
        Connection conn = conDB();
        ObservableList<reporte> list = FXCollections.observableArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT  c.IDCompra, p.empresaProveedor, pd.nombre, pa.totalPago , tp.desTipoPago, dc.cantidad, c.FechaPedido, c.FechaLlegada\n" +
                    "FROM compra AS c \n" +
                    "INNER JOIN detallecompra AS dc ON dc.IDCompra = c.IDCompra\n" +
                    "INNER JOIN proveedores AS p ON p.IDProveedor = c.IDProveedor\n" +
                    "INNER JOIN producto as pd ON pd.IDProducto = c.IDProducto\n" +
                    "INNER JOIN pagocompra as pa ON pa.IDCompra = c.IDCompra\n" +
                    "INNER JOIN detalledepagocompra as dp ON dp.IDPago = pa.IDPago\n" +
                    "INNER JOIN tipopago as tp ON tp.IDTipoPago = dp.IDTipoPago;");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new reporte (rs.getInt("IDCompra"), rs.getString("empresaProveedor"), rs.getString("nombre"), rs.getDouble("totalPago"),  rs.getString("desTipoPago"), rs.getInt("cantidad"), rs.getString("FechaPedido"), rs.getString("FechaLlegada")));
            }
        }catch(Exception e){
        }
        return list;
    }
}


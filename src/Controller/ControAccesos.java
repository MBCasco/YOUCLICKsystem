package Controller;

import ComboBoxController.usuario;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControAccesos extends MenuController implements Initializable {
    @FXML
    private ComboBox<usuario> cmb_nombreUsuario;

    //Pantallas
    @FXML
    private CheckBox chb_clientes;
    @FXML
    private CheckBox chb_empleados;
    @FXML
    private CheckBox chb_productos;
    @FXML
    private CheckBox chb_inventario;
    @FXML
    private CheckBox chb_compras;
    @FXML
    private CheckBox chb_proveedores;
    @FXML
    private CheckBox chb_facturas;
    @FXML
    private CheckBox chb_marca;

    //Clientes
    @FXML
    private CheckBox chb_clientesR;
    @FXML
    private CheckBox chb_clientesA;
    @FXML
    private CheckBox chb_clientesE;
    @FXML
    private CheckBox chb_clientesI;
    //Empleados
    @FXML
    private CheckBox chb_empleadosR;
    @FXML
    private CheckBox chb_empleadosC;
    @FXML
    private CheckBox chb_empleadosA;
    @FXML
    private CheckBox chb_empleadosE;
    @FXML
    private CheckBox chb_empleadosI;
    //Productos
    @FXML
    private CheckBox chb_productosR;
    @FXML
    private CheckBox chb_productosP;
    @FXML
    private CheckBox chb_productosA;
    @FXML
    private CheckBox chb_productosE;
    @FXML
    private CheckBox chb_productosM;
    @FXML
    private CheckBox chb_productosI;
    //Inventario
    @FXML
    private CheckBox chb_inventarioC;
    @FXML
    private CheckBox chb_inventarioI;
    //Compra
    @FXML
    private CheckBox chb_compraR;
    @FXML
    private CheckBox chb_compraA;
    @FXML
    private CheckBox chb_compraE;
    @FXML
    private CheckBox chb_compraI;
    //Proveedores
    @FXML
    private CheckBox chb_proveedoresR;
    @FXML
    private CheckBox chb_proveedoresA;
    @FXML
    private CheckBox chb_proveedoresE;
    @FXML
    private CheckBox chb_proveedoresI;
    //Marca
    @FXML
    private CheckBox chb_marcaR;
    @FXML
    private CheckBox chb_marcaA;
    @FXML
    private CheckBox chb_marcaE;
    @FXML
    private CheckBox chb_marcaI;
    //Factura
    @FXML
    private CheckBox chb_facturasI;

    Connection conn = null;
    PreparedStatement pst = null;
    ObservableList<usuario> listaUsuario = usuario.getUsuario();
    final Calendar calendar = Calendar.getInstance();
    final Date date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(date);
    private ResultSet rs;


    public void habilitarClientes() {
        if (chb_clientes.isSelected()) {
            chb_clientesR.setDisable(false);
            chb_clientesA.setDisable(false);
            chb_clientesE.setDisable(false);
            chb_clientesI.setDisable(false);


        } else {
            chb_clientesR.setSelected(false);
            chb_clientesA.setSelected(false);
            chb_clientesE.setSelected(false);
            chb_clientesI.setSelected(false);
            chb_clientesR.setDisable(true);
            chb_clientesA.setDisable(true);
            chb_clientesE.setDisable(true);
            chb_clientesI.setDisable(true);
        }
    }

    public void habilitarEmpleados() {
        if (chb_empleados.isSelected()) {
            chb_empleadosR.setDisable(false);
            chb_empleadosC.setDisable(false);
            chb_empleadosA.setDisable(false);
            chb_empleadosE.setDisable(false);
            chb_empleadosI.setDisable(false);


        } else {
            chb_empleadosR.setSelected(false);
            chb_empleadosC.setSelected(false);
            chb_empleadosA.setSelected(false);
            chb_empleadosE.setSelected(false);
            chb_empleadosI.setSelected(false);
            chb_empleadosR.setDisable(true);
            chb_empleadosC.setDisable(true);
            chb_empleadosA.setDisable(true);
            chb_empleadosE.setDisable(true);
            chb_empleadosI.setDisable(true);
        }
    }

    public void habilitarProductos() {
        if (chb_productos.isSelected()) {
            chb_productosR.setDisable(false);
            chb_productosP.setDisable(false);
            chb_productosA.setDisable(false);
            chb_productosE.setDisable(false);
            chb_productosM.setDisable(false);
            chb_productosI.setDisable(false);

        } else {
            chb_productosR.setSelected(false);
            chb_productosP.setSelected(false);
            chb_productosA.setSelected(false);
            chb_productosE.setSelected(false);
            chb_productosM.setSelected(false);
            chb_productosI.setSelected(false);
            chb_productosR.setDisable(true);
            chb_productosP.setDisable(true);
            chb_productosA.setDisable(true);
            chb_productosE.setDisable(true);
            chb_productosM.setDisable(true);
            chb_productosI.setDisable(true);
        }
    }


    public void habilitarInventario() {
        if (chb_inventario.isSelected()) {
            chb_inventarioC.setDisable(false);
            chb_inventarioI.setDisable(false);

        } else {
            chb_inventarioC.setSelected(false);
            chb_inventarioI.setSelected(false);
            chb_inventarioC.setDisable(true);
            chb_inventarioI.setDisable(true);

        }
    }

    public void habilitarCompras() {
        if (chb_compras.isSelected()) {
            chb_compraR.setDisable(false);
            chb_compraA.setDisable(false);
            chb_compraE.setDisable(false);
            chb_compraI.setDisable(false);


        } else {
            chb_compraR.setSelected(false);
            chb_compraA.setSelected(false);
            chb_compraE.setSelected(false);
            chb_compraI.setSelected(false);
            chb_compraR.setDisable(true);
            chb_compraA.setDisable(true);
            chb_compraE.setDisable(true);
            chb_compraI.setDisable(true);
        }
    }

    public void habilitarProveedores() {
        if (chb_proveedores.isSelected()) {
            chb_proveedoresR.setDisable(false);
            chb_proveedoresA.setDisable(false);
            chb_proveedoresE.setDisable(false);
            chb_proveedoresI.setDisable(false);


        } else {
            chb_proveedoresR.setSelected(false);
            chb_proveedoresA.setSelected(false);
            chb_proveedoresE.setSelected(false);
            chb_proveedoresI.setSelected(false);
            chb_proveedoresR.setDisable(false);
            chb_proveedoresA.setDisable(false);
            chb_proveedoresE.setDisable(false);
            chb_proveedoresI.setDisable(false);
        }
    }

    public void habilitarMarca() {
        if (chb_marca.isSelected()) {
            chb_marcaR.setDisable(false);
            chb_marcaA.setDisable(false);
            chb_marcaE.setDisable(false);
            chb_marcaI.setDisable(false);


        } else {
            chb_marcaR.setSelected(false);
            chb_marcaA.setSelected(false);
            chb_marcaE.setSelected(false);
            chb_marcaI.setSelected(false);
            chb_marcaR.setDisable(true);
            chb_marcaA.setDisable(true);
            chb_marcaE.setDisable(true);
            chb_marcaI.setDisable(true);
        }
    }

    public void habilitarFacturas() {
        if (chb_facturas.isSelected()) {
            chb_facturasI.setDisable(false);


        } else {
            chb_facturasI.setSelected(false);
            chb_facturasI.setDisable(true);

        }
    }


    public void guardar() throws SQLException {

        try{

        conn = connect.conDB();

        String usuario = cmb_nombreUsuario.getSelectionModel().getSelectedItem().getNombre();
        String funcionesclientes = "";
        String funcionesempleados = "";
        String funcionesproductos = "";
        String funcionesinventario = "";
        String funcionescompras = "";
        String funcionesproveedores = "";
        String funcionesfacturas = "";
        String funcionesmarca = "";

        //Clientes
        if (chb_clientesR.isSelected()) {
            funcionesclientes += "R";
        }

        if (chb_clientesA.isSelected()) {
            funcionesclientes += "A";
        }

        if (chb_clientesE.isSelected()) {
            funcionesclientes += "E";
        }
        if (chb_clientesI.isSelected()) {
            funcionesclientes += "I";
        }


        //Empleado
        if (chb_empleadosR.isSelected()) {
            funcionesempleados += "R";
        }

        if (chb_empleadosA.isSelected()) {
            funcionesempleados += "A";
        }

        if (chb_empleadosC.isSelected()) {
            funcionesempleados += "C";
        }

        if (chb_empleadosE.isSelected()) {
            funcionesempleados += "E";
        }
        if (chb_empleadosI.isSelected()) {
            funcionesempleados += "I";
        }

        //Productos
        if (chb_productosR.isSelected()) {
            funcionesproductos += "R";
        }

        if (chb_productosA.isSelected()) {
            funcionesproductos += "A";
        }

        if (chb_productosP.isSelected()) {
            funcionesproductos += "P";
        }

        if (chb_productosE.isSelected()) {
            funcionesproductos += "E";
        }

        if (chb_productosM.isSelected()) {
            funcionesproductos += "M";
        }

        if (chb_productosI.isSelected()) {
            funcionesproductos += "I";
        }

        //Inventario
        if (chb_inventarioC.isSelected()) {
            funcionesinventario += "C";
        }

        if (chb_inventarioI.isSelected()) {
            funcionesinventario += "A";
        }

        //Compra
        if (chb_compraR.isSelected()) {
            funcionescompras += "R";
        }

        if (chb_compraA.isSelected()) {
            funcionescompras += "A";
        }

        if (chb_compraE.isSelected()) {
            funcionescompras += "E";
        }
        if (chb_compraI.isSelected()) {
            funcionescompras += "I";
        }

        //Proveedores
        if (chb_proveedoresR.isSelected()) {
            funcionesproveedores += "R";
        }

        if (chb_proveedoresA.isSelected()) {
            funcionesproveedores += "A";
        }

        if (chb_proveedoresE.isSelected()) {
            funcionesproveedores += "E";
        }
        if (chb_proveedoresI.isSelected()) {
            funcionesproveedores += "I";
        }

        //Marca
        if (chb_marcaR.isSelected()) {
            funcionesmarca += "R";
        }

        if (chb_marcaA.isSelected()) {
            funcionesmarca += "A";
        }

        if (chb_marcaE.isSelected()) {
            funcionesmarca += "E";
        }
        if (chb_marcaI.isSelected()) {
            funcionesmarca += "I";
        }

        //Facturas
        if (chb_facturasI.isSelected()) {
            funcionesfacturas += "I";
        }

        //Permisos
        boolean empleados;
        if (chb_empleados.isSelected()) {
            empleados = true;
        } else {
            empleados = false;
        }

        boolean clientes;
        if (chb_clientes.isSelected()) {
            clientes = true;
        } else {
            clientes = false;
        }

        boolean productos;
        if (chb_productos.isSelected()) {
            productos = true;
        } else {
            productos = false;
        }

        boolean inventario;
        if (chb_inventario.isSelected()) {
            inventario = true;
        } else {
            inventario = false;
        }

        boolean compras;
        if (chb_compras.isSelected()) {
            compras = true;
        } else {
            compras = false;
        }

        boolean proveedores;
        if (chb_proveedores.isSelected()) {
            proveedores = true;
        } else {
            proveedores = false;
        }

        boolean facturas;
        if (chb_facturas.isSelected()) {
            facturas = true;
        } else {
            facturas = false;
        }

        boolean marca;
        if (chb_marca.isSelected()) {
            marca = true;
        } else {
            marca = false;
        }

       if (validateUsuario()) {
            return;
       }


       /*

            if (!validarfuncionesSeleccionadasClientes()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla clientes");
               alert.showAndWait();
               return;
           }
           if (!validarfuncionesSeleccionadasEmpleados()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla empleados");
               alert.showAndWait();
               return;
           }
           if (!validarfuncionesSeleccionadasProductos()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla productos");
               alert.showAndWait();
               return;
           }
           if (!validarfuncionesSeleccionadasInventario()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla inventario");
               alert.showAndWait();
               return;
           }
           if (!validarfuncionesSeleccionadasCompras()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla compras");
               alert.showAndWait();
               return;
           }
           if (!validarfuncionesSeleccionadasProveedores()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla proveedores");
               alert.showAndWait();
               return;
           }
           if (!validarfuncionesSeleccionadasFacturas()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla facturas");
               alert.showAndWait();
               return;
           }

           if (!validarfuncionesSeleccionadasMarca()) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Espacio Vacio");
               alert.setHeaderText(null);
               alert.setContentText("Debe seleccionar al menos 1 función para la pantalla Marcas");
               alert.showAndWait();
               return;
           }

        */


           try {
               pst = conn.prepareStatement("Update acceso \n" +
                       "set nombreUsuario = ?,\n" +
                       "Clientes = ?,\n" +
                       "Empleados = ?,\n" +
                       "Proveedores = ?,\n" +
                       "Productos = ?,\n" +
                       "Inventario = ?,\n" +
                       "Facturas = ?,\n" +
                       "Marca = ?,\n" +
                       "Compras = ?,\n" +
                       "FuncionesClientes = ?,\n" +
                       "FuncionesEmpleados = ?,\n" +
                       "FuncionesProveedores = ?,\n" +
                       "Funcionesproductos = ?,\n" +
                       "FuncionesInventario = ?,\n" +
                       "FuncionesFacturas = ?,\n" +
                       "FuncionesMarca = ?,\n" +
                       "FuncionesCompras = ?,\n" +
                       "where nombreUsuario = '" + usuario + "'");
               pst.setString(2, usuario);
               pst.setBoolean(3, clientes);
               pst.setBoolean(4, empleados);
               pst.setBoolean(5, proveedores);
               pst.setBoolean(6, productos);
               pst.setBoolean(7, inventario);
               pst.setBoolean(8, facturas);
               pst.setBoolean(9, marca);
               pst.setBoolean(10, compras);
               pst.setString(11, funcionesclientes);
               pst.setString(12, funcionesempleados);
               pst.setString(13, funcionesproveedores);
               pst.setString(14, funcionesproductos);
               pst.setString(15, funcionesinventario);
               pst.setString(16, funcionesfacturas);
               pst.setString(17, funcionesmarca);
               pst.setString(18, funcionescompras);
               int res = pst.executeUpdate();

               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Confirmación");
               alert.setHeaderText(null);
               alert.setContentText("Se guardó el registro de permisos");
               alert.showAndWait();
               clearFields();
               deshabilitarFunciones();

           } catch (SQLException e) {
               try {
                   Log myLog;
                   String nombreArchivo = "src\\Log\\ACCESOS_" + fecha + ".txt";
                   myLog = new Log(nombreArchivo);
                   myLog.logger.setLevel(Level.ALL);
                   myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
               } catch (IOException ex) {
                   Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
               }
           }

          /* try {

               String sql2 = "INSERT INTO acceso ( nombreUsuario,Clientes,Empleados,Proveedores,Productos,Inventario,Facturas,Marca,Compras,FuncionesClientes,FuncionesEmpleados,FuncionesProveedores,FuncionesProductos,FuncionesInventario,FuncionesFacturas,FuncionesMarca,FuncionesCompras)"
                       + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
               pst = conn.prepareStatement(sql2);
               pst.setString(1, usuario);
               pst.setBoolean(2, clientes);
               pst.setBoolean(3, empleados);
               pst.setBoolean(4, proveedores);
               pst.setBoolean(5, productos);
               pst.setBoolean(6, inventario);
               pst.setBoolean(7, facturas);
               pst.setBoolean(8, marca);
               pst.setBoolean(9, compras);
               pst.setString(10, funcionesclientes);
               pst.setString(11, funcionesempleados);
               pst.setString(12, funcionesproveedores);
               pst.setString(13, funcionesproductos);
               pst.setString(14, funcionesinventario);
               pst.setString(15, funcionesfacturas);
               pst.setString(16, funcionesmarca);
               pst.setString(17, funcionescompras);

               int res = pst.executeUpdate();

               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("Confirmación");
               alert.setHeaderText(null);
               alert.setContentText("Se agregó el registro de permisos");
               alert.showAndWait();


           } catch (Exception e) {
               try {
                   Log myLog;
                   String nombreArchivo = "src\\Log\\ACCESOS_" + fecha + ".txt";
                   myLog = new Log(nombreArchivo);
                   myLog.logger.setLevel(Level.ALL);
                   myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
               } catch (IOException ex) {
                   Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
               }
           }

           */




            clearFields();
            deshabilitarFunciones();

        } catch (Exception e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\ACCESOS_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
            }
        }

    }

    public void intCombox (){
        cmb_nombreUsuario.setItems(listaUsuario);
    }

    private void habilitar() {
        chb_clientes.setDisable(false);
        chb_empleados.setDisable(false);
        chb_proveedores.setDisable(false);
        chb_productos.setDisable(false);
        chb_inventario.setDisable(false);
        chb_facturas.setDisable(false);
        chb_marca.setDisable(false);
        chb_compras.setDisable(false);
    }

    private void deshabilitar() {
        chb_clientes.setDisable(true);
        chb_empleados.setDisable(true);
        chb_proveedores.setDisable(true);
        chb_productos.setDisable(true);
        chb_inventario.setDisable(true);
        chb_facturas.setDisable(true);
        chb_marca.setDisable(true);
        chb_compras.setDisable(true);
    }

    private void deshabilitarFunciones() {
        chb_clientesR.setDisable(true);
        chb_clientesA.setDisable(true);
        chb_clientesE.setDisable(true);
        chb_clientesI.setDisable(true);

        chb_empleadosR.setDisable(true);
        chb_empleadosC.setDisable(true);
        chb_empleadosA.setDisable(true);
        chb_empleadosE.setDisable(true);
        chb_empleadosI.setDisable(true);

        chb_proveedoresR.setDisable(true);
        chb_proveedoresA.setDisable(true);
        chb_proveedoresE.setDisable(true);
        chb_proveedoresI.setDisable(true);

        chb_productosR.setDisable(true);
        chb_productosP.setDisable(true);
        chb_productosA.setDisable(true);
        chb_productosE.setDisable(true);
        chb_productosM.setDisable(true);
        chb_productosI.setDisable(true);

        chb_inventarioC.setDisable(true);
        chb_inventarioI.setDisable(true);

        chb_facturasI.setDisable(true);

        chb_marcaR.setDisable(true);
        chb_marcaA.setDisable(true);
        chb_marcaE.setDisable(true);
        chb_marcaI.setDisable(true);

        chb_compraR.setDisable(true);
        chb_compraA.setDisable(true);
        chb_compraE.setDisable(true);
        chb_compraI.setDisable(true);

    }

    private void clearFields() {
        deshabilitar();
        deshabilitarFunciones();
        deseleccionar();
        cmb_nombreUsuario.setSelectionModel(null);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        intCombox();
        deshabilitarFunciones();
        deshabilitar();

    }

    public void salir() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaPrincipal.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

    public void deseleccionar() {
        chb_clientes.setSelected(false);
        chb_clientesR.setSelected(false);
        chb_clientesA.setSelected(false);
        chb_clientesE.setSelected(false);
        chb_clientesI.setSelected(false);
        chb_empleados.setSelected(false);
        chb_empleadosR.setSelected(false);
        chb_empleadosC.setSelected(false);
        chb_empleadosA.setSelected(false);
        chb_empleadosE.setSelected(false);
        chb_empleadosI.setSelected(false);
        chb_productos.setSelected(false);
        chb_productosR.setSelected(false);
        chb_productosP.setSelected(false);
        chb_productosA.setSelected(false);
        chb_productosE.setSelected(false);
        chb_productosM.setSelected(false);
        chb_productosI.setSelected(false);
        chb_inventario.setSelected(false);
        chb_inventarioC.setSelected(false);
        chb_inventarioI.setSelected(false);
        chb_compras.setSelected(false);
        chb_compraR.setSelected(false);
        chb_compraA.setSelected(false);
        chb_compraE.setSelected(false);
        chb_compraI.setSelected(false);
        chb_proveedores.setSelected(false);
        chb_proveedoresR.setSelected(false);
        chb_proveedoresA.setSelected(false);
        chb_proveedoresE.setSelected(false);
        chb_proveedoresI.setSelected(false);
        chb_marca.setSelected(false);
        chb_marcaR.setSelected(false);
        chb_marcaA.setSelected(false);
        chb_marcaE.setSelected(false);
        chb_marcaI.setSelected(false);
        chb_facturas.setSelected(false);
        chb_facturasI.setSelected(false);
    }

    public void cmbUsuario() throws SQLException {
        try {
            conn = connect.conDB();
            habilitar();
            deseleccionar();
            deshabilitarFunciones();
            String usuario = cmb_nombreUsuario.getSelectionModel().getSelectedItem().getNombre();
            String sql = "Select * from acceso where nombreUsuario = '"+usuario+"'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()){
                if (rs.getString("Clientes").equals("1")) {
                    chb_clientes.setSelected(true);
                    habilitarClientes();
                }
                if (rs.getString("Empleados").equals("1")) {
                    chb_empleados.setSelected(true);
                    habilitarEmpleados();
                }
                if (rs.getString("Proveedores").equals("1")) {
                    chb_proveedores.setSelected(true);
                    habilitarProveedores();
                }
                if (rs.getString("Productos").equals("1")) {
                    chb_productos.setSelected(true);
                    habilitarProductos();
                }
                if (rs.getString("Inventario").equals("1")) {
                    chb_inventario.setSelected(true);
                    habilitarInventario();
                }
                if (rs.getString("Facturas").equals("1")) {
                    chb_facturas.setSelected(true);
                    habilitarFacturas();
                }
                if (rs.getString("Marca").equals("1")) {
                    chb_marca.setSelected(true);
                    habilitarMarca();
                }
                if (rs.getString("Compras").equals("1")) {
                    chb_compras.setSelected(true);
                    habilitarCompras();
                }

                //Funciones Clientes
                if(rs.getString("FuncionesClientes").contains("R")){
                    chb_clientesR.setSelected(true);
                }
                if(rs.getString("FuncionesClientes").contains("A")){
                    chb_clientesA.setSelected(true);
                }
                if(rs.getString("FuncionesClientes").contains("E")){
                    chb_clientesE.setSelected(true);
                }
                if(rs.getString("FuncionesClientes").contains("I")){
                    chb_clientesI.setSelected(true);
                }


                //Funciones del empleado
                if(rs.getString("FuncionesEmpleados").contains("R")){
                    chb_empleadosR.setSelected(true);
                }

                if(rs.getString("FuncionesEmpleados").contains("A")){
                    chb_empleadosA.setSelected(true);
                }

                if(rs.getString("FuncionesEmpleados").contains("C")){
                    chb_empleadosC.setSelected(true);
                }

                if(rs.getString("FuncionesEmpleados").contains("E")){
                    chb_empleadosE.setSelected(true);
                }

                if(rs.getString("FuncionesEmpleados").contains("I")){
                    chb_empleadosI.setSelected(true);
                }

                //Funciones Productos
                if(rs.getString("FuncionesProductos").contains("R")){
                    chb_productosR.setSelected(true);
                }
                if(rs.getString("FuncionesProductos").contains("P")){
                    chb_productosP.setSelected(true);
                }
                if(rs.getString("FuncionesProductos").contains("A")){
                    chb_productosA.setSelected(true);
                }
                if(rs.getString("FuncionesProductos").contains("E")){
                    chb_productosE.setSelected(true);
                }
                if(rs.getString("FuncionesProductos").contains("M")){
                    chb_productosM.setSelected(true);
                }
                if(rs.getString("FuncionesProductos").contains("I")){
                    chb_productosI.setSelected(true);
                }

                //Funciones inventario
                if(rs.getString("FuncionesInventario").contains("C")){
                    chb_inventarioC.setSelected(true);
                }
                if(rs.getString("FuncionesInventario").contains("I")){
                    chb_inventarioI.setSelected(true);
                }
                //Funciones compra
                if(rs.getString("FuncionesCompras").contains("R")){
                    chb_compraR.setSelected(true);
                }
                if(rs.getString("FuncionesCompras").contains("A")){
                    chb_compraA.setSelected(true);
                }
                if(rs.getString("FuncionesCompras").contains("E")){
                    chb_compraE.setSelected(true);
                }
                if(rs.getString("FuncionesCompras").contains("I")){
                    chb_compraI.setSelected(true);
                }

                //Funciones proveedores
                if(rs.getString("FuncionesProveedores").contains("R")){
                    chb_proveedoresR.setSelected(true);
                }
                if(rs.getString("FuncionesProveedores").contains("A")){
                    chb_proveedoresA.setSelected(true);
                }
                if(rs.getString("FuncionesProveedores").contains("E")){
                    chb_proveedoresE.setSelected(true);
                }
                if(rs.getString("FuncionesProveedores").contains("I")){
                    chb_proveedoresI.setSelected(true);
                }

                //Funciones marca
                if(rs.getString("FuncionesMarca").contains("I")){
                    chb_facturasI.setSelected(true);
                }
                if(rs.getString("FuncionesMarca").contains("A")){
                    chb_marcaA.setSelected(true);
                }
                if(rs.getString("FuncionesMarca").contains("E")){
                    chb_marca.setSelected(true);
                }
                if(rs.getString("FuncionesMarca").contains("I")){
                    chb_marcaI.setSelected(true);
                }

                //Funciones facturas
                if(rs.getString("Funcionesfacturas").contains("I")){
                    chb_facturasI.setSelected(true);
                }

            }
        }catch (SQLException e){
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\ACCESOS_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
            }
        }

        }catch (Exception e){
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\ACCESOS_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.ALL);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (IOException ex) {
                Logger.getLogger(EmpleadosController.class.getName()).log(Level.ALL, null, ex);
            }
        }

    }

    //VALIDACIONES
    private boolean validateUsuario() {

        if (cmb_nombreUsuario.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error usuario");
            alert.setHeaderText(null);
            alert.setContentText("Selecione un usuario para dar permisos");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasClientes(){
        if(chb_clientes.isSelected()){
            int contador = 0;

            if(chb_clientesR.isSelected()){
                contador++;
            }

            if(chb_clientesA.isSelected()){
                contador++;
            }

            if(chb_clientesE.isSelected()){
                contador++;
            }

            if(chb_clientesI.isSelected()){
                contador++;
            }

            if(contador == 0){
                return false;
            }
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasEmpleados(){
        if(chb_empleados.isSelected()){
            int contador = 0;

            if(chb_empleadosR.isSelected()){
                contador++;
            }

            if(chb_empleadosC.isSelected()){
                contador++;
            }

            if(chb_empleadosA.isSelected()){
                contador++;
            }

            if(chb_empleadosE.isSelected()){
                contador++;
            }

            if(chb_empleadosI.isSelected()){
                contador++;
            }

            if(contador == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasProductos(){
        if(chb_productos.isSelected()){
            int contador = 0;

            if(chb_productosR.isSelected()){
                contador++;
            }

            if(chb_productosP.isSelected()){
                contador++;
            }

            if(chb_productosA.isSelected()){
                contador++;
            }

            if(chb_productosE.isSelected()){
                contador++;
            }

            if(chb_productosM.isSelected()){
                contador++;
            }

            if(chb_productosI.isSelected()){
                contador++;
            }

            if(contador == 0){
                return false;
            }
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasInventario(){
        if(chb_inventario.isSelected()){
            int contador = 0;

            if(chb_inventarioC.isSelected()){
                contador++;
            }

            if(chb_inventarioI.isSelected()){
                contador++;
            }

            if(contador == 0){
                return false;
            }
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasCompras(){
        if(chb_compras.isSelected()){
            int contador = 0;

            if(chb_compraR.isSelected()){
                contador++;
            }

            if(chb_compraA.isSelected()){
                contador++;
            }

            if(chb_compraE.isSelected()){
                contador++;
            }

            if(chb_compraI.isSelected()){
                contador++;
            }

            if(contador == 0){
                return false;
            }
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasProveedores(){
        if(chb_proveedores.isSelected()){
            int contador = 0;

            if(chb_proveedoresR.isSelected()){
                contador++;
            }

            if(chb_proveedoresA.isSelected()){
                contador++;
            }

            if(chb_proveedoresE.isSelected()){
                contador++;
            }

            if(chb_proveedoresI.isSelected()){
                contador++;
            }

            if(contador == 0){
                return false;
            }
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasMarca(){
        if(chb_marca.isSelected()){
            int contador = 0;

            if(chb_marcaR.isSelected()){
                contador++;
            }

            if(chb_marcaA.isSelected()){
                contador++;
            }

            if(chb_marcaE.isSelected()){
                contador++;
            }

            if(chb_marcaI.isSelected()){
                contador++;
            }

            if(contador == 0){
                return false;
            }
        }
        return true;
    }

    private boolean validarfuncionesSeleccionadasFacturas(){
        if(chb_facturas.isSelected()){
            int contador = 0;


            if(chb_facturasI.isSelected()){
                contador++;
            }

            return contador != 0;
        }
        return true;
    }

}

package Controller;

import ComboBoxController.categoria;
import ComboBoxController.marca;
import Models.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InventarioController extends MenuController implements Initializable {

    @FXML
    private TableView<inventario> tablaInventario;

    @FXML
    private TableColumn<inventario, Integer> ColID;

    @FXML
    private TableColumn<inventario, String> ColNombre;

    @FXML
    private TableColumn<inventario, Integer> ColStock;

    @FXML
    private TableColumn<inventario, String> ColDescrip;

    @FXML
    private TableColumn<inventario, String> ColUbicacion;

    @FXML
    private TableColumn<inventario, Double> ColPrecio;

    @FXML
    private TableColumn<inventario, marca> ColMarca;

    @FXML
    private TableColumn<inventario, categoria> ColCategoria;

    @FXML
    private TextField filterField;

    @FXML
    private Button btn_hacercompra;
    @FXML
    private Button btn_reporte;

    ObservableList<inventario> listI;
    ObservableList<inventario> dataList;
    Connection conn = null;

    private void habilitar(String nombreUsuario){
        try {
            Statement st = conn.createStatement();
            String sql = "select * from acceso where nombrUsuario = '"+nombreUsuario+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){

                if(rs.getString("FuncionesInventario").contains("C")){
                    btn_hacercompra.setVisible(true);
                }
                if(rs.getString("FuncionesClientes").contains("I")){
                    btn_reporte.setVisible(true);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pruebaI(javafx.event.ActionEvent actionEvent) throws IOException {
        compra();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
        SearchInventario();
    }
    public void UpdateTable(){
        ColID.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        ColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        ColUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        ColPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        listI = connect.getdatainventario();
        tablaInventario.setItems(listI);
    }
    void SearchInventario(){
        ColID.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        ColNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ColStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ColDescrip.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        ColUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        ColPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        ColMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        ColCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        dataList = connect.getdatainventario();
        tablaInventario.setItems(dataList);
        FilteredList<inventario> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(inventario -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (inventario.getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(inventario.getIdProducto()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<inventario> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tablaInventario.comparatorProperty());
        tablaInventario.setItems(sortedData);
    }

    public void reporteInventario() throws JRException, ClassNotFoundException, SQLException {

        JasperReport reporte;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria", "root", "");
            reporte = JasperCompileManager.compileReport("src/Reportes/Inventario.jrxml");
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("inventarioParameter", 1);

            JasperPrint jp = JasperFillManager.fillReport(reporte, parameters, conn);

            JasperViewer.viewReport(jp, false);
        }catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, "Ocurrio este error " + e.getMessage() );
        }
    }

}

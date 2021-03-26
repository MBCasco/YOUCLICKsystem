package Controller;

import ComboBoxController.marca;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarcaController extends MenuController implements Initializable {

    @FXML
    private TableView<marca> table_marca;
    @FXML
    private TableColumn<marca, Integer> col_id;
    @FXML
    private TableColumn<marca, String> col_nombre;

    @FXML
    private TextField txt_id;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_eliminar;
    @FXML
    private TextField txt_buscar;


    @FXML
    private Button btn_agregar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_reporte;

    int value;


    ObservableList<marca> listMarca;
    ObservableList<marca> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    final Calendar calendar = Calendar.getInstance();
    final java.util.Date  date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd_HH.mm.ss").format(date);

    private void habilitar(String nombreUsuario){
        try {
            Statement st = conn.createStatement();
            String sql = "select * from acceso where nombrUsuario = '"+nombreUsuario+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){

                if(rs.getString("FuncionesMarca").contains("R")){
                    btn_agregar.setVisible(true);
                }
                if(rs.getString("FuncionesMarca").contains("A")){
                    btn_actualizar.setVisible(true);
                }
                if(rs.getString("FuncionesMarca").contains("E")){
                    btn_eliminar.setVisible(true);
                }
                if(rs.getString("FuncionesMarca").contains("I")){
                    btn_reporte.setVisible(true);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void agregar() {
        conn = connect.conDB();
        String sql = "insert into marca (nombreMarca)values(?)";


        if (validateName() & validateFields()){


            try {
                pst = conn.prepareStatement(sql);

                pst.setString(1, txt_nombre.getText());
                pst.execute();


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó cliente exitosamente");
                alert.showAndWait();

                clearFields();
                UpdateTable();
                Search_marca();

            } catch (Exception e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\MARCA_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe(e.getMessage() + " : " + e.getCause());
                } catch (IOException ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void clearFields() {
        txt_id.clear();
        txt_nombre.clear();
        checkBtnStatus(0);
    }

    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_agregar.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);
        }
        if (check == 0){
            btn_agregar.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);
        }
    }

    public void Delete(){
        Toolkit.getDefaultToolkit().beep();
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro ¿Qué quieres eliminar esta marca?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                conn = connect.conDB();
                try {
                    String Ssql = "DELETE FROM marca WHERE IDMarca = ?";
                    PreparedStatement prest = conn.prepareStatement(Ssql);
                    prest.setString(1, txt_eliminar.getText());

                    if (prest.executeUpdate() > 0){
                        Alert alert1 =new Alert(Alert.AlertType.ERROR);
                        alert1.setTitle("Eliminado");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Se elimino la marca con éxito");
                        alert1.showAndWait();
                        UpdateTable();
                        clearFields();
                    }

                }catch (Exception e){
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\MARCA_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.SEVERE);
                        myLog.logger.severe(e.getMessage());
                    } catch (Exception ex) {
                        Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void UpdateTable(){

        col_id.setCellValueFactory(new PropertyValueFactory<>("IDMarca"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombreMarca"));

        listMarca = connect.getdatamarca();
        table_marca.setItems(listMarca);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
        Search_marca();
        clearFields();
        checkBtnStatus(0);

    }

    public void Edit(){

        if (validateFields() & validateName()) {

            try {
                conn = connect.conDB();

                String value1 = txt_id.getText();
                String value2 = txt_nombre.getText();


                String sql = ("update marca set nombreMarca= '" + value2 + "' where IDMarca='" + value1 + "' ");

                pst = conn.prepareStatement(sql);
                pst.execute();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Se actualizó la marca exitosamente");
                alert.showAndWait();

                UpdateTable();
                Search_marca();
                clearFields();

            } catch (Exception e) {
                try {
                    Log myLog;
                    String nombreArchivo = "src\\Log\\MARCA_"+fecha+".txt";
                    myLog = new Log(nombreArchivo);
                    myLog.logger.setLevel(Level.SEVERE);
                    myLog.logger.severe(e.getMessage());
                } catch (Exception ex) {
                    Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }



    void Search_marca(){
        col_id.setCellValueFactory(new PropertyValueFactory<>("IDMarca"));
        col_nombre.setCellValueFactory(new PropertyValueFactory<>("nombreMarca"));

        dataList = connect.getdatamarca();
        table_marca.setItems(dataList);
        FilteredList<marca> filteredData = new FilteredList<>(dataList, b -> true);
        txt_buscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNombreMarca().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }else if (String.valueOf(person.getIDMarca()).indexOf(lowerCaseFilter)!=-1)
                    return true;

                else
                    return false;
            });
        });
        SortedList<marca> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_marca.comparatorProperty());
        table_marca.setItems(sortedData);
    }

    public void getSelected(javafx.scene.input.MouseEvent event) {
        index = table_marca.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }

        txt_id.setText(col_id.getCellData(index).toString());
        txt_nombre.setText(col_nombre.getCellData(index));
        txt_eliminar.setText(String.valueOf(value));
        checkBtnStatus(1);
    }

    private boolean validateName(){
        Pattern p = Pattern.compile("^([A-Z]{1}[a-z]+[ ]*)([A-Za-z ñáéíóú]+)$");
        Matcher m = p.matcher(txt_nombre.getText());

        if(m.find() && m.group().equals(txt_nombre.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validar Nombre");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente informacion: " +
                    " \nEste campo solo acepta letras" +
                    " \nEl nombre escrito debe llevar la primera letra en mayuscula " +
                    " \nEj: Alambre");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateFields(){
        if (txt_nombre.getText().isEmpty()){

            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.show();

            return false;
        }
        return true;
    }


    public void Salir(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaProductos.fxml"));
        stage.setTitle("Productos");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();
    }

    public void reporteMarca() throws JRException, ClassNotFoundException, SQLException {

        JasperReport reporte;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria", "root", "");
            reporte = JasperCompileManager.compileReport("src/Reportes/Marca.jrxml");
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("marcaParameter", 1);

            JasperPrint jp = JasperFillManager.fillReport(reporte, parameters, conn);

            JasperViewer.viewReport(jp, false);
        }catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, "Ocurrio este error " + e.getMessage() );
        }
    }
}

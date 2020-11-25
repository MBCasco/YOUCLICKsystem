package Controller;
import Models.contacto;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProveedoresContactoController extends MenuController implements Initializable {

    private static Object txt_IDProveedor;
    @FXML
    private TableView<contacto> table_contacto;
    @FXML
    private TableColumn<contacto, Integer> col_idContacto;
    @FXML
    private TableColumn<contacto, String> col_correoElectronicoContacto;
    @FXML
    private TableColumn<contacto, String> col_detallesContacto;
    @FXML
    private TableColumn<contacto, Integer> col_telefonoContacto;
    @FXML
    private TableColumn<contacto, String> col_nombreContacto;
    @FXML
    private TableColumn<contacto, String> col_idProveedor;

    @FXML
    private TextField txt_correoContacto;
    @FXML
    private TextField txt_detallesContacto;
    @FXML
    private TextField txt_telefonoContacto;
    @FXML
    private TextField txt_eliminarContacto;
    @FXML
    private TextField txt_buscarContacto;
    @FXML
    private TextField txt_IdContacto;
    @FXML
    private TextField txt_nombreContacto;

    private ObservableList<contacto> listC;
    private ObservableList<contacto> dataListC;

    int index = -1;
    Connection conn = null;
    PreparedStatement pst = null;
    int IDContacto = 0;

    public void prueba() throws IOException {
        proveedor();

    }

    public static int value (int value){
        txt_IDProveedor = value;
        System.out.println(txt_IDProveedor);
        return value;
    }
    public void add_contacto(){
        conn = connect.conDB();
        String sql = "insert into contactoproveedor (IDProveedor, nombreDeContacto, Detalles, Telefono, Correo)values(?,?,?,?,?)";
        if (validateFields() & validateName() & validateDetalles()  & validateNumber() & validateEmail() ) {
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_IDProveedor.toString());
                pst.setString(2, txt_nombreContacto.getText());
                pst.setString(3, txt_detallesContacto.getText());
                pst.setString(4, txt_telefonoContacto.getText());
                pst.setString(5, txt_correoContacto.getText());
                pst.execute();

                JOptionPane.showMessageDialog(null, "Agregado");
                UpdateTableContacto();
                Search_contacto();
                clearFieldsContacto();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    public void UpdateTableContacto(){
        col_idContacto.setCellValueFactory(new PropertyValueFactory<>("IdContacto"));
        col_idProveedor.setCellValueFactory(new PropertyValueFactory<>("IProveedor"));
        col_nombreContacto.setCellValueFactory(new PropertyValueFactory<>("NombreContacto"));
        col_detallesContacto.setCellValueFactory(new PropertyValueFactory<>("Detalles"));
        col_telefonoContacto.setCellValueFactory(new PropertyValueFactory<>("TelefonoContacto"));
        col_correoElectronicoContacto.setCellValueFactory(new PropertyValueFactory<>("CorreoContacto"));


        listC = connect.getdatacontacto();
        table_contacto.setItems(listC);


    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        UpdateTableContacto();
        clearFieldsContacto();
        Search_contacto();

    }

    @FXML
    void Search_contacto(){
        /// modelo
        col_idContacto.setCellValueFactory(new PropertyValueFactory<>("IdContacto"));
        col_nombreContacto.setCellValueFactory(new PropertyValueFactory<>("NombreContacto"));

        dataListC = connect.getdatacontacto();
        table_contacto.setItems(dataListC);
        FilteredList<contacto> filteredDataContacto = new FilteredList<>(dataListC, b -> true);
        txt_buscarContacto.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataContacto.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getNombreContacto().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (String.valueOf(person.getIdContacto()).contains(lowerCaseFilter))
                    return true;

                else
                    return false;
            });
        });
        SortedList<contacto> sortedData = new SortedList<>(filteredDataContacto);
        sortedData.comparatorProperty().bind(table_contacto.comparatorProperty());
        table_contacto.setItems(sortedData);
    }

    private void clearFieldsContacto() {

        txt_nombreContacto.clear();
        txt_telefonoContacto.clear();
        txt_correoContacto.clear();
        txt_detallesContacto.clear();
        txt_IdContacto.clear();
        txt_buscarContacto.clear();
    }

    public void deleteContacto(){
        conn = connect.conDB();
        String sql = "Delete from contactoproveedor where IDContactoProveedor = ?";
        try{
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_eliminarContacto.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Eliminado");
            UpdateTableContacto();
            Search_contacto();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void EditContacto(){
        if (validateFields() & validateName() & validateDetalles()  & validateNumber() & validateEmail()){
            try {
                conn = connect.conDB();
                String value1 = txt_IdContacto.getText();
                String value2 = txt_nombreContacto.getText();
                String value3 = txt_detallesContacto.getText();
                String value4 = txt_telefonoContacto.getText();
                String value5 = txt_correoContacto.getText();

                String sql = "update contactoproveedor set nombreDeContacto= '" + value2 + "', Detalles= '" + value3 + "', Telefono= '" + value4 + "',Correo= '" + value5 + "' where IDContactoProveedor='" + value1 + "' ";

                pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Actualizado");
                UpdateTableContacto();
                Search_contacto();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    @FXML
    public void getSelectedContacto(MouseEvent event) {
        index = table_contacto.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_IdContacto.setText(col_idContacto.getCellData(index).toString());
        txt_nombreContacto.setText(col_nombreContacto.getCellData(index));
        txt_detallesContacto.setText(col_detallesContacto.getCellData(index).toString());
        txt_telefonoContacto.setText(col_telefonoContacto.getCellData(index).toString());
        txt_correoContacto.setText(col_correoElectronicoContacto.getCellData(index));
    }

    /*
        ////////////
        VALIDACIONES
        ////////////
     */

    private boolean validateName(){
        Pattern p = Pattern.compile("[A-Za-z ]+");
        Matcher m = p.matcher(txt_nombreContacto.getText());

        if(m.find() && m.group().equals(txt_nombreContacto.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Nombre");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un nombre válido");
            alert.showAndWait();

            return false;
        }
    }
    private boolean validateDetalles(){
        Pattern p = Pattern.compile("[A-Za-z ]+");
        Matcher m = p.matcher(txt_detallesContacto.getText());

        if(m.find() && m.group().equals(txt_detallesContacto.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Detalles");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un detalle válido");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateNumber(){
        Pattern p = Pattern.compile("[0-9]{8}");
        Matcher m = p.matcher(txt_telefonoContacto.getText().trim());

        if(m.find() && m.group().equals(txt_telefonoContacto.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Número");
            alert.setHeaderText(null);
            alert.setContentText("El número debe contener maximo 8 digitos" +
                    " y el campo no acepta espacios en blanco");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateEmail(){
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(txt_correoContacto.getText());

        if(m.find() && m.group().equals(txt_correoContacto.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Correo");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un correo válido" +
                    " ejemplo@gmail.com");
            alert.showAndWait();

            return false;
        }
    }

    private boolean validateFields(){
        if (txt_nombreContacto.getText().isEmpty() | txt_detallesContacto.getText().isEmpty() | txt_telefonoContacto.getText().isEmpty() | txt_correoContacto.getText().isEmpty() ){

            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Espacios vacios!");
            alert.setHeaderText(null);
            alert.setContentText("Espacios vacíos, por favor ingresar datos");
            alert.showAndWait();

            return false;
        }
        return true;
    }
}

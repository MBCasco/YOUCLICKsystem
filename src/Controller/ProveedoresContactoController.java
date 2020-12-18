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
    @FXML
    private Button btn_registrar;
    @FXML
    private Button btn_eliminar;
    @FXML
    private Button btn_actualizar;
    @FXML
    private Button btn_clear;

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
        if (validateFields() & limite() & validateName() & validateDetalles()  & validateNumber() & validateEmail() ) {
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_IDProveedor.toString());
                pst.setString(2, txt_nombreContacto.getText());
                pst.setString(3, txt_detallesContacto.getText());
                pst.setString(4, txt_telefonoContacto.getText());
                pst.setString(5, txt_correoContacto.getText());
                pst.execute();

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Se agregó exitosamente");
                alert.showAndWait();

                UpdateTableContacto();
                Search_contacto();
                clearFields();

            } catch (Exception e) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("El correo debe ser único." +
                        "\n Revise que su correo sea único y vuelva a intentarlo. ");
                alert.showAndWait();
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

        listC = connect.getdatacontacto((Integer) txt_IDProveedor);
        table_contacto.setItems(listC);


    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        UpdateTableContacto();
        clearFields();
        Search_contacto();
        checkBtnStatus(0);

    }

    @FXML
    void Search_contacto(){
        /// modelo
        col_idContacto.setCellValueFactory(new PropertyValueFactory<>("IdContacto"));
        col_nombreContacto.setCellValueFactory(new PropertyValueFactory<>("NombreContacto"));

        dataListC = connect.getdatacontacto((Integer) txt_IDProveedor);
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

    @FXML
    private void clearFields() {

        txt_nombreContacto.clear();
        txt_telefonoContacto.clear();
        txt_correoContacto.clear();
        txt_detallesContacto.clear();
        txt_IdContacto.clear();
        txt_buscarContacto.clear();
        txt_eliminarContacto.clear();
        checkBtnStatus(0);
    }

    private void checkBtnStatus(int check) {
        if (check == 1){
            btn_registrar.setDisable(true);
            btn_actualizar.setDisable(false);
            btn_eliminar.setDisable(false);

        }
        if (check == 0){
            btn_registrar.setDisable(false);
            btn_actualizar.setDisable(true);
            btn_eliminar.setDisable(true);

        }
    }

    public void deleteContacto(){
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText(null);
        alert.setContentText("Estás seguro ¿Qué quieres eliminar este proveedor?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                conn = connect.conDB();
                try {
                    String Ssql = "DELETE FROM contactoproveedor WHERE IDContactoProveedor = ?";
                    PreparedStatement prest = conn.prepareStatement(Ssql);
                    prest.setString(1, txt_eliminarContacto.getText());

                    if (prest.executeUpdate() > 0){
                        Alert alert1 =new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Informacion");
                        alert1.setHeaderText(null);
                        alert1.setContentText("Se elimino con éxito");
                        alert1.showAndWait();
                        UpdateTableContacto();
                        clearFields();

                    }else{
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Error");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Hubo un error al eliminar");
                    }
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Error, por favor vuelva a intentarlo");
                }
            }
        });
    }

    public void EditContacto(){
        if (validateFields() & limite() & validateName() & validateDetalles()  & validateNumber() & validateEmail()){
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

                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setHeaderText(null);
                alert.setContentText("Se actualizó exitosamente");
                alert.showAndWait();

                UpdateTableContacto();
                Search_contacto();

            } catch (Exception e) {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al actualizar, revise que todos los campos estén llenados correctamente");
                alert.showAndWait();
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
        txt_eliminarContacto.setText(col_idContacto.getCellData(index).toString());
        checkBtnStatus(1);
    }

    /*
        ////////////
        VALIDACIONES
        ////////////
     */

    private boolean validateName(){
        Pattern p = Pattern.compile("^([A-Z]{1}[a-z]+[ ]*){2,4}$");
        Matcher m = p.matcher(txt_nombreContacto.getText());


        if(m.find() && m.group().equals(txt_nombreContacto.getText())){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar nombre");
            alert.setHeaderText(null);
            alert.setContentText("Por favor ingresar un nombre válido \n" +
                    " Deberá escribir un nombre que contenga:\n" +
                    " - Primera letra mayúscula\n" +
                    " - Al menos un apellido");
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
        Pattern  pp = Pattern.compile("[23789]");
        Matcher  mm = pp.matcher(txt_telefonoContacto.getText().substring(0,1));

        if(m.find() && m.group().equals(txt_telefonoContacto.getText()) &&  mm.matches()){
            return true;
        } else{
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validar Número");
            alert.setHeaderText(null);
            alert.setContentText("Verifique la siguiente información: " +
                    " \nQue el telefono comience con: 2,3,7,8 o 9 " +
                    " \nQue el telefono contenga maximo 8 digitos " +
                    " \nEste campo solo acepta numeros");
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
    private boolean limite(){
        if(txt_nombreContacto.getText().length() >= 35){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supera Limite Permitido");
            alert.setHeaderText("Error");
            alert.setContentText("Supero el Limite de caracteres.+" +
                    " \n El limite de caracteres es de 35");
            alert.showAndWait();
            return false;
        }
        if(txt_detallesContacto.getText().length() >= 20){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Supera Limite Permitido");
            alert.setHeaderText("Error");
            alert.setContentText("Supero el Limite de caracteres.+" +
                    " \n El limite de caracteres es de 20");
            alert.showAndWait();
            return false;
        }
        return true;

    }
}

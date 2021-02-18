package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;

public class LoginController<Private> implements GlobalConstans {

    @FXML
    private Label lbl_close;

    @FXML
    private Label lblError;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private TextField contraseñaField;

    @FXML
    private Button loginButton;


    public void handleButtonAction(MouseEvent event){
        if(event.getSource() == lbl_close){
            System.exit(0);
        }
        if(event.getSource() == loginButton){
            //login here
            if(LogIn().equals("Success")){
                try{

                    Scene scene = new Scene (FXMLLoader.load(getClass().getResource("/Layout/pantallaPrincipal.fxml")));
                    stage.setTitle("Pantalla Principal");
                    stage.setScene(scene);
                    stage.show();

                }catch (IOException ex){
                    System.err.println(ex.getMessage());
                }
            }
        }
    }


   /* @FXML
    private void clearFields() {
        usuarioTextField.clear();
        contraseñaField.clear();
        lblError.setText("");
    }
    */


    public LoginController(){ con = connect.conDB(); }
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private String LogIn() {
        String usuario = usuarioTextField.getText();
        String contraseña = contraseñaField.getText();

        String sql = "SELECT * FROM admins Where usuario = ? and contraseña = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contraseña);
            resultSet = preparedStatement.executeQuery();


            if (!resultSet.next()) {

                lblError.setTextFill(Color.TOMATO);
                lblError.setText("Su intento de ingreso ha fallado,\n verifíque sus credenciales");

                System.err.println("Inicio Incorrecto");
                return "Error";


            } else{

                lblError.setTextFill(Color.GREEN);
                lblError.setText("Inicio Exitoso..Redireccionado..");
                System.out.println("Inicio Exitoso");
                return "Success";
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return "Exception";
        }
    }

}

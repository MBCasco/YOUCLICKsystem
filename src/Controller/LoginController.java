package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.paint.Color;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.input.MouseEvent;

public class LoginController implements GlobalConstans {

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

    final Calendar calendar = Calendar.getInstance();
    final java.util.Date  date = calendar.getTime();
    String fecha = new SimpleDateFormat("yyyyMMdd-hh.mm.ss").format(date);



    public void handleButtonAction(MouseEvent event) throws SQLException {
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

                }catch (IOException e){
                    try {
                        Log myLog;
                        String nombreArchivo = "src\\Log\\LOGIN_"+fecha+".txt";
                        myLog = new Log(nombreArchivo);
                        myLog.logger.setLevel(Level.SEVERE);
                        myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
                    } catch (IOException ex) {
                        Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }


    @FXML
    private void clearFields() {
        usuarioTextField.clear();
        contraseñaField.clear();
    }



    public LoginController(){
        con = connect.conDB();
    }

    Connection con;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private String LogIn() {

        String usuario = usuarioTextField.getText();
        String contraseña = contraseñaField.getText();



        String sql = "SELECT * FROM admins where usuario = ? and contraseña = ?";

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
                clearFields();
                lblError.setTextFill(Color.GREEN);
                lblError.setText("Inicio Exitoso..Redireccionado..");
                System.out.println("Inicio Exitoso");
                return "Success";
            }

        } catch (SQLException e) {
            try {
                Log myLog;
                String nombreArchivo = "src\\Log\\LOGIN_"+fecha+".txt";
                myLog = new Log(nombreArchivo);
                myLog.logger.setLevel(Level.SEVERE);
                myLog.logger.severe(e.getMessage() + " Causado por: " + e.getCause());
            } catch (IOException ex) {
                Logger.getLogger(ClientesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "Exception";
        }
    }

}

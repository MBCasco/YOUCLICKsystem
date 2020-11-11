import Controller.GlobalConstans;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main extends Application implements GlobalConstans {

    //Estas son las variables de la conexion a la BD

    private static Connection con;
    private static final String driver="com.mysql.cj.jdbc.Driver";
    private static final String user="uzarfsc6tdyihxyb";
    private static final String pass="emPtsIbZlNSpWdC6dfcx";
    private static final String url="jdbc:mysql://uzarfsc6tdyihxyb:emPtsIbZlNSpWdC6dfcx@bn5ipqznij2ib3blk0ie-mysql.services.clever-cloud.com:3306/bn5ipqznij2ib3blk0ie";


   // Este medoto es el de conexion a la BD
    public void conector() {
        // Reseteamos a null la conexion a la bd
        con=null;
        try{
            Class.forName(driver);
            // Nos conectamos a la bd
            con= (Connection) DriverManager.getConnection(url, user, pass);
            // Si la conexion fue exitosa mostramos un mensaje de conexion exitosa
            if (con!=null){
                System.out.println("Conexion establecida");
            }
        }
        // Si la conexion NO fue exitosa mostramos un mensaje de error
        catch (ClassNotFoundException | SQLException e){
            System.out.println("Conexion no establecida  " + e );
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) throws Exception {
        //Este es el codigo para lanzar la pantalla principal(aun no terminada) en javaFX
        // lo que se deberia lanzar aqui realmente es el login pero aun estamos en pruebas del menu junto a Naho asi que...

        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaPrincipal.fxml"));
        stage.setTitle("Inicio");
        stage.setScene(new Scene(root, 1360, 768));
        stage.show();




        //Esta es una query para la bd la el primer value es la Primary key asi que se debe cambiar
        // cuando la bd este bien eso no deben poner porque bueno sera auto incremental

        // String query = "INSERT INTO CLIENTE(IDCliente,nombreCliente, apellidoCliente, telefonoCliente, dirreccionCliente, correoCliente) VALUES(6,'Jaime4','Jimenez', '99909990','sukaza','zukorreo')";
        //        con.createStatement().executeUpdate(query);
        //        System.out.println("Funziona");

    }


}

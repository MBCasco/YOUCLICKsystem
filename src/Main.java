
import Controller.GlobalConstans;
import Controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.Connection;

public class Main extends Application implements GlobalConstans {
    private double xOffset = 0;
    private double yOffset = 0;

    //Estas son las variables de la conexion a la BD


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) throws Exception {
        //Este es el codigo para lanzar la pantalla login

        Parent root = FXMLLoader.load(getClass().getResource("/Layout/pantallaLogin.fxml"));

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        stage.setScene(new Scene(root, 620, 350));
        stage.show();

    }


}

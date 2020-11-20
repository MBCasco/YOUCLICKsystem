package Controller;

import Models.precioHistorico;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class PrecioHistoricoController extends MenuController implements Initializable {
    @FXML
    private TableView<precioHistorico> table_precioHistorico;

    @FXML
    private TableColumn<precioHistorico, Integer> col_ID;

    @FXML
    private TableColumn<precioHistorico, String> col_Nombre;

    @FXML
    private TableColumn<precioHistorico, Double> col_Precio;


    @FXML
    private TableColumn<precioHistorico, String> col_FechaInicial;

    @FXML
    private TableColumn<precioHistorico, String> col_FechaFinal;

    @FXML
    private TextField filterField;


    ObservableList<precioHistorico> listM;
    ObservableList<precioHistorico> dataList;

    int index = -1;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public void UpdateTable(){
        col_ID.setCellValueFactory(new PropertyValueFactory<precioHistorico,Integer>("idPrecioHistorico"));
        col_Nombre.setCellValueFactory(new PropertyValueFactory<precioHistorico,String>("nombre"));
        col_Precio.setCellValueFactory(new PropertyValueFactory<precioHistorico, Double>("precio"));
        col_FechaInicial.setCellValueFactory(new PropertyValueFactory<precioHistorico,String>("fechaInicial"));
        col_FechaFinal.setCellValueFactory(new PropertyValueFactory<precioHistorico, String>("fechaFinal"));

        listM = connect.getdataprecioHistorico();
        table_precioHistorico.setItems(listM);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        UpdateTable();
        Search_precioHistorico();
    }

    @FXML
    void Search_precioHistorico(){
        col_ID.setCellValueFactory(new PropertyValueFactory<precioHistorico,Integer>("ID"));
        col_Nombre.setCellValueFactory(new PropertyValueFactory<precioHistorico,String>("Nombre"));
        col_Precio.setCellValueFactory(new PropertyValueFactory<precioHistorico, Double>("Precio"));
        col_FechaInicial.setCellValueFactory(new PropertyValueFactory<precioHistorico,String>("FechaInicial"));
        col_FechaFinal.setCellValueFactory(new PropertyValueFactory<precioHistorico, String>("FechaFinal"));


        dataList = connect.getdataprecioHistorico();
        table_precioHistorico.setItems(dataList);
        FilteredList<precioHistorico> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(producto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (producto.getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(producto.getID()).indexOf(lowerCaseFilter) != -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<precioHistorico> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_precioHistorico.comparatorProperty());
        table_precioHistorico.setItems(sortedData);
    }

}


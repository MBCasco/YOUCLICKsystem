package Controller;

import ComboBoxController.categoria;
import ComboBoxController.marca;
import Models.*;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    ObservableList<inventario> listI;
    ObservableList<inventario> dataList;

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

}

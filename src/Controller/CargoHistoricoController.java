package Controller;


import Models.cargoHistorico;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
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
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CargoHistoricoController extends MenuController implements Initializable {

        @FXML
        private TableView<cargoHistorico> table_cargoHistorico;

        @FXML
        private TableColumn<cargoHistorico, Integer> col_ID;


        @FXML
        private TableColumn<cargoHistorico, String> col_NombreEmpleado;

        @FXML
        private TableColumn<cargoHistorico, String> col_Cargo;

        @FXML
        private TableColumn<cargoHistorico, String> col_FechaInicial;

        @FXML
        private TableColumn<cargoHistorico, String> col_FechaFinal;

        @FXML
        private TextField filterField;


        ObservableList<cargoHistorico> listM;
        ObservableList<cargoHistorico> dataList;

        int index = -1;
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        public void UpdateTable() {
            col_ID.setCellValueFactory(new PropertyValueFactory<cargoHistorico, Integer>("ID"));
            col_NombreEmpleado.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("nombreEmpleado"));
            col_Cargo.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("Cargo"));
            col_FechaInicial.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("fechaInicial"));
            col_FechaFinal.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("fechaFinal"));

            listM = connect.getdatacargoHistorico();
            table_cargoHistorico.setItems(listM);
        }

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            UpdateTable();
            Search_cargoHistorico();
        }

        @FXML
        void Search_cargoHistorico() {

            col_ID.setCellValueFactory(new PropertyValueFactory<cargoHistorico, Integer>("ID"));
            col_NombreEmpleado.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("nombreEmpleado"));
            col_Cargo.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("Cargo"));
            col_FechaInicial.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("fechaInicial"));
            col_FechaFinal.setCellValueFactory(new PropertyValueFactory<cargoHistorico, String>("fechaFinal"));


            dataList = connect.getdatacargoHistorico();
            table_cargoHistorico.setItems(dataList);
            FilteredList<cargoHistorico> filteredData = new FilteredList<>(dataList, b -> true);
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(cargo -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (cargo.getNombreEmpleado().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(cargo.getID()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
            SortedList<cargoHistorico> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(table_cargoHistorico.comparatorProperty());
            table_cargoHistorico.setItems(sortedData);
        }

}



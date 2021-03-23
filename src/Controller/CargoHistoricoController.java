package Controller;


import Models.cargoHistorico;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
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

        public void UpdateTable() {
            col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
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

    public void reporteCargoHistorico() throws JRException, ClassNotFoundException, SQLException {

        JasperReport reporte;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/ferreteria", "root", "");
            reporte = JasperCompileManager.compileReport("src/Reportes/CargoHistorico.jrxml");
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("cargohistoricoParameter", 1);

            JasperPrint jp = JasperFillManager.fillReport(reporte, parameters, conn);

            JasperViewer.viewReport(jp, false);
        }catch (ClassNotFoundException | SQLException | JRException e){
            JOptionPane.showMessageDialog(null, "Ocurrio este error " + e.getMessage() );
        }
    }

}



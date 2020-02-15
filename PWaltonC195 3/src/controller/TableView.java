package controller;

import database.DAOI;
import database.DAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import tdisplay.ReportDisplay;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class TableView  implements Initializable {


    public Button exitT;
    /*
     public ListView moTableV;
     public ListView wTableV;
     */
    public TableColumn presTypeW;
    public TableColumn totW;
    public TableColumn presMType;
    public TableColumn totM;
    public javafx.scene.control.TableView monthTbl;
    public javafx.scene.control.TableView weekTbl;


    public ObservableList<ReportDisplay> monthTypeList = FXCollections.observableArrayList();


    public ObservableList<ReportDisplay> weekTypeList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        buildApptTypeLists();

        presMType.setCellValueFactory(new PropertyValueFactory<>("type"));
        totM.setCellValueFactory(new PropertyValueFactory<>("total"));
        presTypeW.setCellValueFactory(new PropertyValueFactory<>("type"));
        totW.setCellValueFactory((new PropertyValueFactory<>("total")));

        monthTbl.setItems(monthTypeList);
        weekTbl.setItems(weekTypeList);

    }


    private void buildApptTypeLists() {
        weekTypeList.clear();
        monthTypeList.clear();
        DAOI dbWorker = new DAOImpl();
        ObservableList<Appointment> appByTypeW = dbWorker.getAppointments();


        HashMap<String, Integer> weekHashMap = new HashMap<String, Integer>();
        HashMap<String, Integer> monthHashMap = new HashMap<String, Integer>();
        for (String type : Appointment.allTypes) {
            weekHashMap.put(type, 0);
            monthHashMap.put(type, 0);
        }

        for (Appointment appByWeek1 : appByTypeW) {
            if (Appointment.appointmentInWeek(appByWeek1)) {
                Integer total = weekHashMap.get(appByWeek1.getType()) + 1;
                weekHashMap.replace(appByWeek1.getType(), total);
            }
            if (Appointment.appointmentInMonth(appByWeek1)) {
                Integer total = monthHashMap.get(appByWeek1.getType()) + 1;
                monthHashMap.replace(appByWeek1.getType(), total);
            }


        } // end of for loop


        // THIS IS
        Set<String> keySet = weekHashMap.keySet();
        for (String type : keySet) {
            Integer total = weekHashMap.get(type);
            ReportDisplay rd = new ReportDisplay(type, total);
            weekTypeList.add(rd);
            total = monthHashMap.get(type);
            rd = new ReportDisplay(type, total);
            monthTypeList.add(rd);
        }
        weekTbl.setItems(weekTypeList);
        monthTbl.setItems(monthTypeList);


    }


    public void bCM(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ApptManager.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            Stage stage1 = (Stage) exitT.getScene().getWindow();
            stage1.close();


        } catch (IOException e) {
            e.printStackTrace();

        }

    }


}
package controller;

import adisplay.AppointmentDisplay;
import database.DAOI;
import database.DAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import tdisplay.ReportDisplay;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

public class ApptManager implements Initializable {
    // public final static String ALL = "All";

    public Button exitAAM;
    public Button MonthVB;
    public Button WeekVB;
    public TableView<AppointmentDisplay> apptMCal;

    public TableColumn uName;
    public TableColumn cName;
    public TableColumn start;
    public TableColumn end;
    public TableColumn type;
    public ObservableList<AppointmentDisplay> apptList = FXCollections.observableArrayList();
    public ObservableList<AppointmentDisplay> weekView = FXCollections.observableArrayList();
    public ObservableList<AppointmentDisplay> monthView = FXCollections.observableArrayList();

    public ObservableList<AppointmentDisplay> monthList = FXCollections.observableArrayList();

    public ObservableList<AppointmentDisplay> weekList = FXCollections.observableArrayList();
    public ObservableList<String> apList = FXCollections.observableArrayList();

    public ObservableList<Customer> custoList = FXCollections.observableArrayList();
    public ObservableList<String> atype = FXCollections.observableArrayList();
    public ObservableList<User> userNList = FXCollections.observableArrayList();

    public ObservableList<User> schUserList = FXCollections.observableArrayList();

    public ObservableList<String> startList = FXCollections.observableArrayList();

    public ObservableList<String> endList = FXCollections.observableArrayList();

    public ObservableList<AppointmentDisplay> wkApptList = FXCollections.observableArrayList();

    public ObservableList<ReportDisplay> monthTypeList = FXCollections.observableArrayList();


    public ObservableList<ReportDisplay> weekTypeList = FXCollections.observableArrayList();


    public Button addM;


    public Button cl;

    public Button aView;
    public Button popAAM;
    public Button deleteAAM;
    public ComboBox<String> typeA;
    public ComboBox<Customer> custD;
    public ComboBox<User> userN;
    public ComboBox<String> endi;
    public ComboBox<String> starti;
    public DatePicker date1;
    public TableColumn<?, ?> datei;


    private final static int VIEWALL = 1;

    private final static int VIEWWEEK = 2;

    private final static int VIEWMONTH = 3;
    private final static int VIEWSCED = 4;
    public Button updatei;
    public ComboBox<User> schedCombo;
    public Button userSch;
    public TableView weekTable;
    public TableView monthTable;
    public TableColumn wkAType;
    public TableColumn totW;
    public TableColumn mAType;
    public TableColumn totM;
    private int viewMode = VIEWALL;
    public String typeAp;
    public int total;
    public boolean isOverLapping;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apptMCal.setItems(wkApptList);

        apptMCal.setItems(monthView);
        apptMCal.setItems(weekView);
        apptMCal.setItems(apptList);
        typeA.setItems(atype);
        custD.setItems(custoList);
        userN.setItems(userNList);
        starti.setItems(startList);
        endi.setItems(endList);
        schedCombo.setItems(schUserList);
        // wkRTable.setItems(weekTypeList); // baclwards
        //mRTbl.setItems(monthTypeList);
        //TODO endi & starti


        uName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        cName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        datei.setCellValueFactory(new PropertyValueFactory<>("dateV"));
        start.setCellValueFactory(new PropertyValueFactory<>("startV"));
        end.setCellValueFactory(new PropertyValueFactory<>("endV"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        buildAppointmentList();
        wkAType.setCellValueFactory(new PropertyValueFactory<>("type"));
        totW.setCellValueFactory(new PropertyValueFactory<>("total"));
        mAType.setCellValueFactory(new PropertyValueFactory<>("type"));
        totM.setCellValueFactory(new PropertyValueFactory<>("total"));

        buildTypeList();
        buildApptTypeLists();


        buildTimeCombo();
        buildConsultantList();

        buildCustomerList();
        //buildUserList();
        date1.setValue(LocalDate.now());
        userN.getSelectionModel().selectFirst();
        custD.getSelectionModel().selectFirst();
        typeA.getSelectionModel().selectFirst();
        starti.getSelectionModel().selectFirst();
        endi.getSelectionModel().selectFirst();
        schedCombo.getSelectionModel().selectFirst();


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
        weekTable.setItems(weekTypeList);
        monthTable.setItems(monthTypeList);


    }


    private void buildConsultantList() {

        DAOI dbWorker = new DAOImpl();
        ObservableList<User> uuUser = dbWorker.getUsers();


        for (User u : uuUser) {
            userNList.add(u);
            schUserList.add(u);

        }
        schedCombo.getSelectionModel().select(0);

    }


    private void buildAppointmentList() {

        apptList.clear();
        DAOI dbWorker = new DAOImpl();

        ObservableList<Appointment> bAppt = dbWorker.getAppointments();
        ObservableList<Customer> oCust = dbWorker.getCustomers();
        ObservableList<User> cUser = dbWorker.getUsers();

        for (Appointment a : bAppt) {
            if (viewMode == VIEWWEEK) {

                if (!Appointment.appointmentInWeek(a)) {
                    continue;
                }


            } else if (viewMode == VIEWMONTH) {

                if (!Appointment.appointmentInMonth(a)) {
                    continue;
                }


            } else if (viewMode == VIEWSCED) {
                User uR = schedCombo.getSelectionModel().getSelectedItem();
                {
                    if (uR.getUserId() != a.getUserId()) {
                        continue;

                    }
                }

                // get user from combo box
                // if user Id not equal  userId in combo  continue

            }


            Customer c = null;
            for (Customer cu : oCust) {
                if (a.getCustomerId() == cu.getCustomerId()) {
                    //     bAppt = a;
                    c = cu;
                    break;
                }

                //  AppointmentDisplay aD = new AppointmentDisplay()
            } // cust C
            User u = null;
            for (User uL : cUser) {
                if (a.getUserId() == uL.getUserId()) {
                    u = uL;
                    break;
                }

            }  // for user

            // public  AppointmentDisplay(int appointmentId, String userName, String customerName, LocalDateTime start, LocalDateTime end, String type)
            AppointmentDisplay aD = new AppointmentDisplay(a.getAppointmentId(), c.getCustomerId(), u.getUserId(), u.getUserName(), c.getCustomerName(), a.getStart(), a.getEnd(), a.getType());
            apptList.add(aD);

        }// appt a
        buildApptTypeLists();


    }
    @FunctionalInterface
    interface lamba2 {
        LocalTime advanceTime (LocalTime t);
    }


    private void buildTimeCombo() {
        startList.clear();
        endList.clear();

        // Setting lambda to replace 15 plus call
        lamba2 aT = t-> { return  t.plusMinutes(15);};



        LocalTime lt = LocalTime.MIDNIGHT;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        do {
            startList.add(dtf.format(lt));
            endList.add(dtf.format(lt));
            //lt = lt.plusMinutes(15);

            lt = aT.advanceTime(lt);

        }
        while (!lt.equals(LocalTime.MIDNIGHT));
        starti.getSelectionModel().selectFirst();
        endi.getSelectionModel().selectFirst();
    }


    private void buildTypeList() {


        typeA.setItems(Appointment.allTypes);
        typeA.getSelectionModel().selectFirst();


    }


    private void buildCustomerList() {
        DAOI dbWorker = new DAOImpl();

        ObservableList<Customer> oCust = dbWorker.getCustomers();
        for (Customer cu : oCust) {
            custoList.add(cu);
        }
    }


    public void exitApptM(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/transitionScreen.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            Stage stage1 = (Stage) exitAAM.getScene().getWindow();
            stage1.close();


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void MonthV(ActionEvent actionEvent) {
        viewMode = VIEWMONTH;
        buildAppointmentList();
        type.getSortType();


    }

    public void WeekV(ActionEvent actionEvent) {
        viewMode = VIEWWEEK;
        buildAppointmentList();
    }

    public void clear1(ActionEvent actionEvent) {

        buildAppointmentList();
        buildCustomerList();
        date1.setValue(LocalDate.now());
        typeA.getSelectionModel().selectFirst();
        starti.getSelectionModel().selectFirst();
        endi.getSelectionModel().selectFirst();


    }

    public void addAppt(ActionEvent actionEvent) {
        try {


            User u = userN.getSelectionModel().getSelectedItem();
            Customer c = custD.getSelectionModel().getSelectedItem();
            LocalDate ld = date1.getValue();
            LocalTime lts = LocalTime.MIDNIGHT.plusMinutes(15 * starti.getSelectionModel().getSelectedIndex());
            LocalTime lte = LocalTime.MIDNIGHT.plusMinutes(15 * endi.getSelectionModel().getSelectedIndex());
            String type = typeA.getSelectionModel().getSelectedItem();

            if (isOutOfBusinessHrs(lts, lte)) {
                System.out.println("OUTSIDE bus hrs replace me");
                // return;
                Alert alert = new Alert((Alert.AlertType.WARNING));
                alert.setTitle(("Warning"));
                alert.setContentText("Your appointment time must be within business hours (8am -4:30pm) please enter valid appointment times.");
                alert.showAndWait();// TODO pop replace
                return;
            }
            if (isOverLapping(u, ld, lts, lte, -1)) {
                System.out.println("overlappiong appts");

                Alert alert = new Alert((Alert.AlertType.WARNING));
                alert.setTitle(("Warning"));
                alert.setContentText("Your appointment time chosen must not overlap appointment times.");
                alert.showAndWait();

                return;
            }


            DAOI dbWorker = new DAOImpl();

            Appointment aAppt = new Appointment(c.getCustomerId(), u.getUserId(), "", "", "", "", type, "", LocalDateTime.of(ld, lts), LocalDateTime.of(ld, lte));


            dbWorker.newAppointment(aAppt);
            buildAppointmentList();
        }
            catch (Exception e) {
            Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
            alert.setTitle(("Confirmation Dialog"));
            alert.setContentText("Congratulations, your appointment is booked! Smile and create something extraordinary in at least one arena of your life today, just to celebrate!");
            alert.showAndWait();


        }
        /*
            catch (Exception e){
                 Alert alert = new Alert((Alert.AlertType.INFORMATION));
                 alert.setTitle(("Information Dialog"));
                 alert.setContentText("Your appointment is within business hours, & we Thank you!" );
                 alert.showAndWait();

*/
            }







    public static boolean isOverLapping(User u, LocalDate dt, LocalTime start, LocalTime end, int exId) {

            LocalDateTime lts = LocalDateTime.of(dt, start);
            LocalDateTime lte = LocalDateTime.of(dt, end);
            DAOI dbWorker = new DAOImpl();

            ObservableList<Appointment> bAppt = dbWorker.getAppointments();
            /*
            The start time is between the appointment start and appointment end time

            The end time is between the appointment start and appointment end time

             The start time is less than the appointment start time and the end time is greater than the appointment end time.
             */



            for (Appointment a : bAppt) {
                if (a.getUserId() != u.getUserId()) {
                    continue;  // TODO ck start time vs start appt & same with end
                }
                if ( a.getAppointmentId() == exId ){
                    continue;
                }
                LocalDateTime apptStart = a.getStart();
                LocalDateTime apptEnd = a.getEnd();





                if ((lts.isAfter(apptStart) || lts.isEqual(apptStart)) && (lts.isBefore(apptEnd))) {
                    return true;
                }
                if (lte.isAfter(apptStart) && (lte.isBefore(apptEnd) || lte.isEqual(apptEnd))){
                    return true;
                }
                if ((lts.isBefore(apptStart) || lts.isEqual(apptStart)) && (lte.isAfter(apptEnd) || lte.isEqual(apptEnd))){
                    return true;
                }
                //  TODO Above  works but still adds appt





            }
            return false;




        }


    public static boolean isOutOfBusinessHrs(LocalTime start, LocalTime end) {


        LocalTime startLDT = LocalTime.of(8, 00);  // TODO Mon - Thurs for business days
        LocalTime endLDT = LocalTime.of(16, 30);

        if (start.isBefore(startLDT))
            return true;
        if (start.isAfter(endLDT))
            return true;
        if (end.isBefore(startLDT))
            return true;
        if (end.isAfter(endLDT))
            return true;
        return false;

    }


    public void viewAll(ActionEvent actionEvent) {
        viewMode = VIEWALL;
        buildAppointmentList();

    }

    public void deleteA(ActionEvent actionEvent) {
        AppointmentDisplay aD = apptMCal.getSelectionModel().getSelectedItem();
        if (aD == null) {
            return;
        }
        DAOI dbWorker = new DAOImpl();
        dbWorker.deleteAppointment(aD.getAppointmentId());
        // clear1(null);
        buildAppointmentList();


    }


    public void updateAppt(ActionEvent actionEvent) {
        AppointmentDisplay aD = apptMCal.getSelectionModel().getSelectedItem();
        if (aD == null) {
            return;
        }
        User u = userN.getSelectionModel().getSelectedItem();
        Customer c = custD.getSelectionModel().getSelectedItem();
        LocalDate ld = date1.getValue();
        LocalTime lts = LocalTime.MIDNIGHT.plusMinutes(15 * starti.getSelectionModel().getSelectedIndex());
        LocalTime lte = LocalTime.MIDNIGHT.plusMinutes(15 * endi.getSelectionModel().getSelectedIndex());
        String type = typeA.getSelectionModel().getSelectedItem();

        if (isOutOfBusinessHrs(lts, lte)) {
            System.out.println("OUTSIDE bus hrs replace me");
            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.setTitle(("Warning"));
            alert.setContentText("Your appointment time must be within business hours (8am -4:30pm), please enter valid appointment times.");
            alert.showAndWait();// TODO pop replace
            return;

        }
        if (isOverLapping(u, ld, lts, lte , aD.getAppointmentId())) {

            System.out.println("overlapping appts");
            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.setTitle(("Warning"));
            alert.setContentText("Your appointment time chosen must not overlap appointment times.");
            alert.showAndWait();

            return;
        }



        DAOI dbWorker = new DAOImpl();

        ObservableList<Appointment> appUPA = dbWorker.getAppointments();


        Appointment apptMod = null;



        for (Appointment aUP : appUPA) {
            if (aD.getAppointmentId() == aUP.getAppointmentId()) {
                apptMod = aUP;
                break;
            }




        }// end of for loop

        apptMod.setUserId(u.getUserId());
        apptMod.setCustomerId(c.getCustomerId());
        apptMod.setStart(LocalDateTime.of(ld,lts));
        apptMod.setEnd(LocalDateTime.of(ld, lte));
        apptMod.setType(type);

        dbWorker.updateAppointment(apptMod);
        buildAppointmentList();
    }




        public void popUApppt (ActionEvent actionEvent){
            AppointmentDisplay aDD = apptMCal.getSelectionModel().getSelectedItem();
            if (aDD == null) {

                Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setTitle(("Error Dialog"));
                alert.setContentText(" All fields must have content, take a deep breath try again.");
                alert.showAndWait();
                return;
            }

            for ( int i = 0; i < custoList.size(); i ++) {
                Customer c = custoList.get(i);
                if ( c.getCustomerId() == aDD.getCustomerId()){
                    custD.getSelectionModel().select(i);
                    break;
                }

            }// end for cust
            for (int i = 0; i < userNList.size(); i ++){
                User u = userNList.get(i);
                if(u.getUserId() == aDD.getUserId()){
                    userN.getSelectionModel().select(i);
                    break;
                }
            }// end user
            date1.setValue(aDD.getStart().toLocalDate());
            LocalTime lts = aDD.getStart().toLocalTime();
            LocalTime lte = aDD.getEnd().toLocalTime();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
            starti.getSelectionModel().select(dtf.format(lts));
            endi.getSelectionModel().select(dtf.format(lte));
            typeA.getSelectionModel().select(aDD.getType());


        }


    public void userSchedHandler(ActionEvent actionEvent) {
        viewMode = VIEWSCED;
        buildAppointmentList();

    }

    public void typeReports(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/TableView.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            Stage stage1 = (Stage) exitAAM.getScene().getWindow();
            stage1.close();


        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}


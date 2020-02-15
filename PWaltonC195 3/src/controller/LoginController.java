package controller;

import database.DAOI;
import database.DAOImpl;
import database.dbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.*;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    public PasswordField passwordTF;
    public TextField userTF;
    private DAOI dbWorker = new DAOImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void logIn(ActionEvent actionEvent) throws IOException {
        DAOI dbWorker = new DAOImpl();

        // grab data from text field userName & password
        String loginU = userTF.getText();
        String passU = passwordTF.getText();

        // check for exception  empty username or password or not in system
        String filename = "Atomic Appointment Manager users.txt";

        User user2 = dbWorker.getLoginUser(loginU, passU);


        // NOTE using try with resources
        try ( PrintWriter pw = new PrintWriter(new FileOutputStream(
                new File(filename),
                true /* append = true */)); ){



            if (user2 == null) {
                pw.println("user " + loginU + " Invalid login at " + LocalDateTime.now());
                pw.flush();
                pw.close();

                ResourceBundle rb = ResourceBundle.getBundle("controller/language", Locale.getDefault());

                Alert alert = new Alert((Alert.AlertType.ERROR));
                alert.setTitle((rb.getString("errordialog")));
                alert.setContentText(rb.getString("alerttext"));
                alert.showAndWait();
                return;
            }

            pw.println("user " + user2.getUserName() + " logged in at " + LocalDateTime.now());
            pw.flush();

            ObservableList<Appointment> bAppt = dbWorker.getAppointments();

            // get login user2
            // get list of appointments
            // get  time now
            // get now + 15 mins
            // walk list of appts
            // check uerId if not users appt  continue
            // start  > now
            // start <= 15
            // alert
           // ResourceBundle  rb = ResourceBundle.getBundle(""); // TODO check as rb wasn't active until I dropped this in
            ResourceBundle rb = ResourceBundle.getBundle("controller/language",Locale.getDefault());





            LocalDateTime sTime = LocalDateTime.now();
            LocalDateTime aTime = sTime.plusMinutes(15);
            for (Appointment a : bAppt){
                if (a.getUserId() != user2.getUserId()){
                    continue;
                }
                if (a.getStart().isAfter(sTime) && a.getStart().isBefore(aTime)){
                    Alert alert = new Alert((Alert.AlertType.INFORMATION));
                    alert.setTitle((rb.getString("infoalert")));
                    alert.setContentText(rb.getString("infotext"));
                    alert.showAndWait();
                    break;
                    // Information alert
                    //"You have an appointment in approx.   minutes"
                }

            }

        }
        catch (FileNotFoundException fex ){
            fex.printStackTrace();
        }

            Parent parent = FXMLLoader.load(getClass().getResource("/view/transitionScreen.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            Stage stage1 = (Stage) userTF.getScene().getWindow();
            stage1.close();



    }
}





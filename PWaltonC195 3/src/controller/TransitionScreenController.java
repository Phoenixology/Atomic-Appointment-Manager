package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

public class TransitionScreenController implements Initializable {


    public Button logOutB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
/// get list of appt and user that logged in pass user in and see if each appt is within 15 mins

    }

    public void exitT(ActionEvent actionEvent) {

        ResourceBundle rb = ResourceBundle.getBundle("controller/language", Locale.getDefault());
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"),rb);


            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Login Screen");
            stage.show();
            Stage stage1 =(Stage) logOutB.getScene().getWindow();
            stage1.close();



        }
        catch (IOException e){
            e.printStackTrace();

        }
    }

    public void appts(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ApptManager.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Appointment Manager");
            stage.show();
            Stage stage1 =(Stage) logOutB.getScene().getWindow();
            stage1.close();



        }
        catch (IOException e){
            e.printStackTrace();

        }
    }


    public void cust(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/CustManager.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Customer Manager");
            stage.show();
            Stage stage1 =(Stage) logOutB.getScene().getWindow();
            stage1.close();



        }
        catch (IOException e){
            e.printStackTrace();

        }
    }

}


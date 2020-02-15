package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.*;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("controller/language",Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginScreen.fxml"),rb);
        primaryStage.setTitle(rb.getString("screenname" ));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }



        public static void main(String[] args) {



            launch(args);






        }
    }


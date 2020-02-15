package controller;
import adisplay.AppointmentDisplay;
import cdisplay.CustomerDisplay;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class CustManager implements Initializable {
    public Button exitCust;
    //public final static String ALL = "All";

    public Button exitCR;
    public TableView<CustomerDisplay> custTable;
    public TableColumn nameC;
    public TableColumn addC;

    public TableColumn zipC;
    public TableColumn phone;

    public ObservableList<CustomerDisplay> custList = FXCollections.observableArrayList();
    public ObservableList<String> apList = FXCollections.observableArrayList();

    public ObservableList<String> consultList = FXCollections.observableArrayList();


    public ObservableList<String> cityList = FXCollections.observableArrayList();
    public Button popCR;
    public Button addCust;
    public TextField nameCRecord;
    public TextField addCRecord;
    public TextField zipCRecord;
    public ComboBox<String> cityCRecord;
    public TextField phoneCRecord;
    public ComboBox<String> consult;
    public Button clear;
    public Button deleteBCR;
    public Button upD;
    public Button selectC;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        custTable.setItems(custList);
        nameC.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addC.setCellValueFactory(new PropertyValueFactory<>("address"));

        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        zipC.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        cityCRecord.setItems(cityList);



        //TODO the others later

        buildCustomerList();
        buildCityList();

        buildAppointmentList();

    }

    private void buildCustomerList() {
        custList.clear();
        DAOI dbWorker = new DAOImpl();

        //get all customers
        ObservableList<Customer> oCust = dbWorker.getCustomers();

        // get all addresses
        ObservableList<Address> aList = dbWorker.getAddress();
        // for each customer
        for (Customer c : oCust) {
            Address add = null;
            for (Address a : aList) {
                if (c.getAddressId() == a.getAddressId()) {
                    add = a;
                    break;
                } // if

            } //for address
            CustomerDisplay cd = new CustomerDisplay(c.getCustomerId(), add.getAddressId(), c.getCustomerName(), add.getAddress(), add.getPhone(), add.getPostalCode());
            custList.add(cd);
        }// for customer

        // find address for customer
        //build customer display
        // add customer display to list
    }

    private void buildAppointmentList() {
        DAOI dbWorker = new DAOImpl();
        ObservableList<Appointment> aApptRpt = dbWorker.getAppointments();
        for (Appointment a : aApptRpt) {
            apList.add(a.getType());

        }
    }


    private void buildCityList() {
        DAOI dbWorker = new DAOImpl();
        ObservableList<City> oCity = dbWorker.getCity();
        // get list of cities from DB
        for (City c : oCity) {
            cityList.add(c.getCity());
        }

        cityCRecord.getSelectionModel().select(0);
        // walk list of cities adding names to city list
    }


    public void exitCust(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/transitionScreen.fxml"));
            javafx.stage.Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            Stage stage1 = (Stage) exitCR.getScene().getWindow();
            stage1.close();


        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public void addC(ActionEvent actionEvent) {
        try {
            String name = nameCRecord.getText();
            String address = addCRecord.getText();
            int cityIndex = cityCRecord.getSelectionModel().getSelectedIndex();// string for city name
            String phone1 = phoneCRecord.getText();
            String zip = zipCRecord.getText();

            if (nameCRecord.getText().equals("")) {
                throw new Exception(" All fields require valid input");
            }
            if (addCRecord.getText().equals("")) {
                throw new Exception(" All fields require valid input");
            }
            if (phoneCRecord.getText().equals("")) {
                throw new Exception(" All fields require valid input");
            }
            if (zipCRecord.getText().equals("")) {
                throw new Exception(" All fields require valid input");
            }



            //is phone ect have a valid field then add popup then return

            DAOI dbWorker = new DAOImpl();
            ObservableList<City> pCty = dbWorker.getCity();

            City addressCity = pCty.get(cityIndex);
            System.out.println(addressCity.getCity());
            //public Address( String address, String address2, int cityId, String postalCode, String phone)
            Address addO = new Address(address, "", addressCity.getCityId(), zip, phone1);
            dbWorker.newAddress(addO);

            // Customer(String customerName, int addressId, int active)
            Customer custO = new Customer(name, addO.getAddressId(), 1);
            dbWorker.newCustomer(custO);

            clearB(null);

            buildCustomerList();

            // update is like add pull matchimg address from db


        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle(("Error Dialog"));
            alert.setContentText("All fields require valid inputs!");
            alert.showAndWait();
        }
    }
    // Lambda 1
    @FunctionalInterface
    interface lamba1 {
        boolean compare (int c, int d);
    }


    public void pop(ActionEvent actionEvent) {
        CustomerDisplay cDD = custTable.getSelectionModel().getSelectedItem();
        if (cDD == null) {
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle(("Error Dialog"));
            alert.setContentText(" All fields must have content, try again.");
            alert.showAndWait();
            return;
        }

        DAOI dbWorker = new DAOImpl();
        ObservableList<Customer> pCust = dbWorker.getCustomers();
        ObservableList<Address> pAdd = dbWorker.getAddress();
        ObservableList<City> pCty = dbWorker.getCity();
        Customer cData = null;
        Address aData = null;
        City ctyData = null;
        int addressId = -1;
        int cityId = -1;

        // Lambda to replace comparison
        lamba1 myL = (int c , int d) -> {return c == d;};


        for (Customer c : pCust) {
           // if (cDD.getCustomerId() == c.getCustomerId()) {
            if (myL.compare( cDD.getCustomerId(),c.getCustomerId())) {
                cData = c;
                addressId = c.getAddressId();
                break;
            }


        } // end customer c
        for (Address a : pAdd) {
            if (a.getAddressId() == addressId) {
                aData = a;
                cityId = a.getCityId();
                break;


            }
        } // end addresss
        for (City c : pCty) {
            if (c.getCityId() == cityId) {
                ctyData = c;
                break;
            }
        }

        // gather all data

        nameCRecord.setText(cData.getCustomerName());
        addCRecord.setText(aData.getAddress());
        zipCRecord.setText(aData.getPostalCode());
        phoneCRecord.setText(aData.getPhone());
        cityCRecord.getSelectionModel().select(ctyData.getCity());


    }


    public void clearB(ActionEvent actionEvent) {

        nameCRecord.setText("");
        addCRecord.setText("");
        zipCRecord.setText("");

        phoneCRecord.setText("");


    }

    public void dCR(ActionEvent actionEvent) {


        CustomerDisplay cDD = custTable.getSelectionModel().getSelectedItem();
        if (cDD == null) {
            return;
        }
        // TODO delete appointments for customer
        DAOI dbWorker = new DAOImpl();
        dbWorker.deleteAppointmentByCustomer(cDD.getCustomerId()); //
        dbWorker.deleteCustomer(cDD.getCustomerId());
        dbWorker.deleteAddress(cDD.getAddressId());
       // clearB(null);
        buildCustomerList();


    }

    public void uPDate(ActionEvent actionEvent) {
        try {
        CustomerDisplay cDD = custTable.getSelectionModel().getSelectedItem();
        if (cDD == null) {
            return;
        }
        String name = nameCRecord.getText();
        String address = addCRecord.getText();
        int cityIndex = cityCRecord.getSelectionModel().getSelectedIndex();// string for city name
        String phone1 = phoneCRecord.getText();
        String zip = zipCRecord.getText();





                if (nameCRecord.getText().equals("")) {
                    throw new Exception(" All fields require valid input");
                }
                if (addCRecord.getText().equals("")) {
                    throw new Exception(" All fields require valid input");
                }
                if (phoneCRecord.getText().equals("")) {
                    throw new Exception(" All fields require valid input");
                }
                if (zipCRecord.getText().equals("")) {
                    throw new Exception(" All fields require valid input");
                }



        DAOI dbWorker = new DAOImpl();
        ObservableList<City> pCty = dbWorker.getCity();

        ObservableList<Customer> pCust = dbWorker.getCustomers();
        ObservableList<Address> pAdd = dbWorker.getAddress();

        City addressCity = pCty.get(cityIndex);
        Customer cData = null;
        Address aData = null;

        for (Customer c : pCust) {
            if (cDD.getCustomerId() == c.getCustomerId()) {
                cData = c;
                break;
            }

        }// for loop customer
        for (Address a : pAdd) {
            if (cDD.getAddressId() == a.getAddressId()) {
                aData = a;
                break;
            }
        }// end of for loop ADDRESS

        cData.setCustomerName(name);
        aData.setAddress(address);
        aData.setCityId(addressCity.getCityId());
        aData.setPhone(phone1);
        aData.setPostalCode(zip);
        dbWorker.updateCustomer(cData);
        dbWorker.updateAddress(aData);
        clearB(null);
        buildCustomerList();

        }
        catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert((Alert.AlertType.ERROR));
            alert.setTitle(("Error Dialog"));
            alert.setContentText("All fields require valid inputs!");
            alert.showAndWait();



        }

        }




    public void showCSch(ActionEvent actionEvent) {

    }

}




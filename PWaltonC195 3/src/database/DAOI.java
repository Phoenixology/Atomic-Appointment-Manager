package database;

import javafx.collections.ObservableList;
import model.*;

import java.lang.reflect.Type;
import java.sql.Connection;

public interface DAOI {
     ObservableList<User> getUsers();
     ObservableList<Customer> getCustomers();
     ObservableList<City> getCity();
     ObservableList<Address> getAddress();
     ObservableList<Appointment> getAppointments();
     ObservableList<Country> getCountry();





     void updateUser(User user);
     void updateCustomer(Customer customer);
     void updateCity(City city);
     void updateAddress(Address address);
     void updateAppointment(Appointment appointment);
     void updateCountry(Country country);

     void deleteUser(int userId);
     void deleteCustomer(int customerId);
     void deleteCity(int cityId);
     void deleteAddress(int addressId);
     void deleteAppointment(int appointmentId);
     void deleteCountry(int countryId);

     void newUser(User user);
     void newCustomer(Customer customer);
     void newCity(City city);
     void newAddress(Address address);
     void newAppointment(Appointment appointment);
     void newCountry(Country country);

     //do for the others
     User getLoginUser(String userName,String password );


    void deleteAppointmentByCustomer(int customerId);
}

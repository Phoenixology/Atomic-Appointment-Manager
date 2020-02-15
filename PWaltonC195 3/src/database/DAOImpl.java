package database;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


// TODO added abstract between public and class
public class DAOImpl implements DAOI {
    private Connection dbConn;


    public DAOImpl() {


        dbConn = dbConnect.startConnection();
        if (dbConn == null) {
            System.out.println("null");
            return;
        } else {
            System.out.println("  ");
        }
    }


    private int getLastId() throws SQLException {
        String lastId = " Select last_insert_Id() as ID";
        PreparedStatement ps = dbConn.prepareStatement(lastId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int ID = rs.getInt("ID");
            return (ID);
        }
        return Country.NOID;
    }

    @Override
    public ObservableList<User> getUsers() {
        ObservableList<User> uList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = dbConn.prepareStatement("Select userId,userName,password, active from user");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {// is there more data to look at
                int userId = rs.getInt("userId");

                String name = rs.getString("userName");
                String password = rs.getString("password");
                int active = rs.getInt("active");

                // public User ( int userId, String userName, String password, int active, int createDate, String createdBy, int lastUpdate, String lastUpdateBy)
                User newUser = new User(userId, name, password, active);
                uList.add(newUser);
            }

            return uList;
        } catch (SQLException e) {
            e.printStackTrace();
            return uList;
        }
    }

    @Override
    public ObservableList<Customer> getCustomers() {
        ObservableList<Customer> cuList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = dbConn.prepareStatement("Select customerId,customerName,addressId, active,createDate,createdBy,lastUpdate,lastUpdateBy from customer");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                int active = rs.getInt("active");

                Customer customerA = new Customer(customerId, customerName, addressId, active);
                cuList.add(customerA);
            }
            /*
            System.out.println("Customer List");  // create list for country and city appointment
            for (Customer cu : cuList) {
                System.out.println(cu);
            }
            */
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return cuList;
    }

    @Override
    public ObservableList<Address> getAddress() {
        ObservableList<Address> adList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = dbConn.prepareStatement("Select  addressId, address,address2,cityId,postalCode, phone, createDate,createdBy,lastUpdate,lastUpdateBy from address");
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                int addressId = rs.getInt("addressId");
                String address = rs.getString("address");
                String address2 = rs.getString("address2");
                int cityId = rs.getInt("cityId");
                String postalCode = rs.getString("postalCode");
                String phone = rs.getString("phone"); //

                // public Address(int addressId, String address, String address2, int cityId, String postalCode, String phone, int createDate, String createdBy, int lastUpdate, String lastUpdateBy
                Address addressO = new Address(addressId, address, address2, cityId, postalCode, phone);
                adList.add(addressO);
            }
            /*
            System.out.println("Address List");  // create list for country and city appointment
            for (Address ad : adList) {
                System.out.println(ad);
            }
            */

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return adList;
    }

    @Override
    public ObservableList<Country> getCountry() {
        ObservableList<Country> coList = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = dbConn.prepareStatement("Select countryId,country,createDate,lastUpdate,lastUpdateBy from country");
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                int countryId = rs.getInt("countryId"); // TODO weird as it s public in country and not working with column
                String country = rs.getString("country");
                Country countryI = new Country(countryId, country);
                coList.add(countryI);
            }
            /*
            System.out.println("Country List");  // create list for country and city appointment
            for (Country co : coList) {
                System.out.println(co);
            }
            */
        } catch (
                SQLException e) {
            e.printStackTrace();

        }
        return coList;
    }

    @Override
    public ObservableList<City> getCity() {
        ObservableList<City> cList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = dbConn.prepareStatement("Select cityId,city,countryId, createDate, createdBy,lastUpdate,lastUpdateBy from city");
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                int cityId = rs.getInt("cityId");
                String city = rs.getString("city");
                int countryId = rs.getInt("countryId");

                City cityI = new City(cityId, city, countryId);
                cList.add(cityI);
            }
            /*
            System.out.println("City List");  // create list for country and city appointment
            for (City c : cList) {
                System.out.println(c);
            }
            */
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return cList;
    }

    @Override
    public ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> aList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = dbConn.prepareStatement("Select appointmentId,customerId,userId," +
                    "title, description, location, contact,type, url, start, end, createDate,lastUpdate,lastUpdateBy from appointment");
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                int userId = rs.getInt("userId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String contact = rs.getString("contact");
                String type = rs.getString("type");
                String url = rs.getString("url");
                Timestamp startTS = rs.getTimestamp("start");
                Timestamp endTS = rs.getTimestamp("end");
                LocalDateTime start = startTS.toLocalDateTime();
                LocalDateTime end = endTS.toLocalDateTime();


                Appointment appointmentA = new Appointment(appointmentId, customerId, userId, title, description, location, contact, type, url, start, end);
                aList.add(appointmentA);

            }



        } catch (SQLException e) {
            e.printStackTrace();

        }
        return aList;
    }


    @Override
    public void updateUser(User user) {
        try {
            String sql = "update user set userName = ?, password = ?,  active = ?, lastUpdate = now(),lastUpdateBy = ? where userId = ?";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1,user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setInt(3,user.getActive());
            ps.setString(4, "pw");
            ps.setInt(5, user.getUserId());
            ps.execute();
            //dbConn.commit(); is this an auto commit database (this is)
        } catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            String sql = "Update customer set customerName = ?, addressId = ?, active = ?, lastUpdate = now(), lastUpdateBy = ?  where customerId = ?";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, customer.getAddressId());
            ps.setInt(3, customer.getActive());
            ps.setString(4, "pw");
            ps.setInt(5, customer.getCustomerId());
            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void updateCity(City city) {
        try {
       String sql = "update city set city = ? , countryId = ?, lastUpdate = now(), lastUpdateBy = ? where cityId = ?";
       PreparedStatement ps = dbConn.prepareStatement(sql);
       ps.setString(1,city.getCity());
       ps.setInt(2, city.getCountryId());
       ps.setString(3, "pw");
       ps.setInt(4, city.getCityId());
       ps.execute();
    }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void updateAddress(Address address) {
        try {

            String sql = "update address set  address = ? , address2 = ?, cityId = ?,  postalCode = ?, phone = ?, lastUpdate = now(), lastUpdateBy = ? where addressId = ?";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1,address.getAddress());
            ps.setString(2, address.getAddress2());
            ps.setInt(3, address.getCityId());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getPhone());
            ps.setString(6, "pw");
            ps.setInt(7, address.getAddressId());
            ps.execute();

        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {
       try {
          String sql = " update appointment set customerId = ?, userId = ?, title = ? , description = ?, location = ?," +
                  " contact = ?, type = ?, url = ? , start = ?, end = ? , lastUpdate = now(), lastUpdateBy = ?" +
                  " where appointmentId = ? ";
          PreparedStatement ps = dbConn.prepareStatement(sql);
          ps.setInt(1, appointment.getCustomerId());
          ps.setInt(2, appointment.getUserId());
          ps.setString(3, appointment.getTitle());
          ps.setString(4, appointment.getDescription());
          ps.setString(5, appointment.getLocation());
          ps.setString(6, appointment.getContact());
          ps.setString(7, appointment.getType());
          ps.setString(8, appointment.getUrl());
          ps.setTimestamp(9, Timestamp.valueOf(appointment.getStart()));
          ps.setTimestamp(10, Timestamp.valueOf(appointment.getEnd()));
          ps.setString(11, "pw");
          ps.setInt(12, appointment.getAppointmentId());
          ps.execute();
 }
    catch (
         SQLException e) {

     e.printStackTrace();
     //dbConn.rollback(); locked tables & rows create issues

 }
    }

    @Override
    public void updateCountry(Country country) {
        try{
            String sql = " update country set country = ? , lastUpdate = now(), lastUpdateBy = ? where countryId = ?  ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, country.getCountry());
            ps.setString(2, "pw");
            ps.setInt(3, country.getCountryId());
            ps.execute();


            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void deleteUser(int userId) {
        try {
            String sql = " delete from user  where userId = ?";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }


    @Override
    public void deleteCustomer(int customerId) {
        try {
            String sql = " delete from customer  where customerId = ?";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1,customerId);
            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void deleteCity(int cityId) {
        try {
            String sql = " delete from city where cityId = ?";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1,cityId);
            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void deleteAddress(int addressId) {
        try {
            String sql = " delete from address where addressId = ? ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1,addressId);
            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void deleteAppointment(int appointmentId) {
        try {
            String sql = " delete from appointment where appointmentId = ? ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1, appointmentId );
            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void deleteAppointmentByCustomer(int customerId) {
        try {
            String sql = " delete from appointment where customerId = ? ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1, customerId );
            ps.execute();
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void deleteCountry(int countryId) {
        try {
            String  sql = " delete from country where countryId = ?";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1, countryId);
            ps.execute();

        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void newUser(User user) {
        try {
            String sql =    " INSERT INTO user VALUES (null,? ,?,?,now(),'pw',now(),'pw') "; // TODO  add in user parameters in ()
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2,user.getPassword());
            ps.setInt(3,user.getActive());
            ps.execute();
            user.setUserId(getLastId());

        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void newCustomer(Customer customer) {
        try{
            String sql = " INSERT INTO customer VALUES (null,? ,?,?,now(),'pw',now(),'pw') ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1,customer.getCustomerName());
            ps.setInt(2,customer.getAddressId());
            ps.setInt(3,customer.getActive());
            ps.execute();
            customer.setCustomerId(getLastId());
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void newCity(City city) {
        try {
            String sql = "INSERT INTO city VALUES (null,? ,?,now(),'pw',now(),'pw') ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1,city.getCity());
            ps.setInt(2,city.getCountryId());
            ps.execute();
            city.setCityId(getLastId());

        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void newAddress(Address address) {
        try {
            String sql = " INSERT INTO address VALUES (null,? ,?,?,?,?,now(),'pw',now(),'pw') ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1,address.getAddress());
            ps.setString(2, address.getAddress2());
            ps.setInt(3,address.getCityId());
            ps.setString(4,address.getPostalCode());
            ps.setString(5,address.getPhone());
            ps.execute();
            address.setAddressId(getLastId());

        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void newAppointment(Appointment appointment) {
        try {
            String sql = " INSERT INTO appointment VALUES (null,? ,?,?,?,?,?, ?, ?, ?, ?, now(),'pw',now(),'pw')  ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1,appointment.getCustomerId());
            ps.setInt(2,appointment.getUserId());
            ps.setString(3,appointment.getTitle());
            ps.setString(4,appointment.getDescription());
            ps.setString(5,appointment.getLocation());
            ps.setString(6,appointment.getContact());
            ps.setString(7,appointment.getType());
            ps.setString(8,appointment.getUrl());
            ps.setTimestamp(9,Timestamp.valueOf(appointment.getStart()));
            ps.setTimestamp(10,Timestamp.valueOf(appointment.getEnd()));

            ps.execute();
            appointment.setAppointmentId(getLastId());

        } catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }

    @Override
    public void newCountry(Country country) {
        try {
            String sql = " INSERT INTO country VALUES (null,?,  now(),'pw',now(),'pw') ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1,country.getCountry());
            ps.execute();



            country.setCountryId(getLastId());
        }
        catch (
                SQLException e) {

            e.printStackTrace();
            //dbConn.rollback(); locked tables & rows create issues

        }
    }
//     User getLoginUser(String userName,String password );
    @Override
    public User getLoginUser(String userName, String password) {
        try {
            String sql = "Select * from user  where userName = ? and password = ? ";
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1 , userName);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {// is there more data to look at

                int userId = rs.getInt("userId");

                String name = rs.getString("userName");
                String pwd = rs.getString("password");
                int active = rs.getInt("active");

                // public User ( int userId, String userName, String password, int active, int createDate, String createdBy, int lastUpdate, String lastUpdateBy)
                User newUser = new User(userId, name, pwd, active);
                return newUser;

            }

            return null;



        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

}
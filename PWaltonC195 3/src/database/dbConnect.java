package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnect {
    // jdbc url parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U05Uwt";
    //jdbc url
    private static final String jdbcUrl = protocol + vendorName + ipAddress;
    //driver interface reference
    private static Connection conn = null;
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static final String userName = "U05Uwt"; // Username
    private static final String password = "53688610289"; // password

    public static Connection startConnection(){
        if(conn != null){
            return conn;
        }
        try{
            Class.forName(MYSQLJDBCDriver);
            conn =(Connection) DriverManager.getConnection(jdbcUrl, userName, password);
        }

        catch(ClassNotFoundException e)
        {

            e.printStackTrace();

        }

        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return conn;
    }
}


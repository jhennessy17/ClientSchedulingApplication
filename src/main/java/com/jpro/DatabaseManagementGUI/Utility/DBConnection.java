package com.jpro.DatabaseManagementGUI.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Jeremy Hennessy
 *
 * DBConnection
 * Sets the connection to the database
 */
public class DBConnection {
    private static final String protocol ="jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//urjf9hxkbjtrhjev:PH7Z6btcDUW7ByqxEu1x@bl7zrq8wspwfx06gpi6b-mysql.services.clever-cloud.com:3306/";//"//wgudb.ucertify.com:3306/";
    private static final String dbName = "bl7zrq8wspwfx06gpi6b";//"WJ086qK";

    private static final String url = protocol + vendorName + ipAddress + dbName;

    private static final String driver="com.mysql.cj.jdbc.Driver";

    private static final String username = "urjf9hxkbjtrhjev";//"U086qK";
    private static final String password = "PH7Z6btcDUW7ByqxEu1x";//"53689215710";
    public static Connection conn = null;

    /**
     * Creates the connection
     */
    public static void startConnection()
    {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection
     */
    public static void closeConnection(){
        try {
            conn.close();
            System.out.println("Connection Closed");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

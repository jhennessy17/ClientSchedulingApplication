package DatabaseManagementGUI.Utility;

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
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName ="WJ086qK";

    private static final String url = protocol + vendorName + ipAddress + dbName;

    private static final String driver="com.mysql.cj.jdbc.Driver";

    private static final String username = "U086qK";
    private static final String password = "53689215710";
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

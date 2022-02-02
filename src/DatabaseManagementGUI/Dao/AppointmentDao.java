package DatabaseManagementGUI.Dao;

import DatabaseManagementGUI.Model.Appointment;
import DatabaseManagementGUI.Utility.QueryExecution;
import DatabaseManagementGUI.View_Controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * Appointment DAO Class
 */

public class AppointmentDao {

    /**
     *
     * @return ObservableList of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments";

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            while (result.next()) {
                int appointmentID = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int userID = result.getInt("User_ID");
                int contactID = result.getInt("Contact_ID");
                allAppointments.add(new Appointment(appointmentID, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdateBy, customerID, userID, contactID));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     *
     * @param userID userID
     * @return ObservableList of appointments related to userID
     */
    public static ObservableList<Appointment> getUserAppointments(int userID){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE User_ID = " + userID;

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            while (result.next()) {
                int appointmentID = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int contactID = result.getInt("Contact_ID");
                allAppointments.add(new Appointment(appointmentID, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdateBy, customerID, userID, contactID));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Adds the appointment to a Database
     *
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startDate startDate
     * @param endDate endDate
     * @param customerID customerID
     * @param userID userID
     * @param contactID contactID
     *
     * */
    public static void addAppointment(String title, String description, String location, String type, LocalDateTime startDate,
                                      LocalDateTime endDate, int customerID, int userID, int contactID){
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, Created_By," +
                "Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(" +
                "'" + title + "'," +
                "'" + description + "'," +
                "'" + location + "'," +
                "'" + type + "'," +
                "'" + startDate + "'," +
                "'" + endDate + "'," +
                "NOW()," +
                "'" + LoginController.user.getUserName() + "'," +
                "NOW()," +
                "'" + LoginController.user.getUserName() + "'," +
                "'" + customerID + "'," +
                "'" + userID + "'," +
                "'" + contactID +"');";
        QueryExecution.query(sql);
    }

    /**
     * Updates appointments in database
     *
     * @param appointmentID appointmentID
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startDate startDate
     * @param endDate endDate
     * @param customerID customerID
     * @param userID userID
     * @param contactID contactID
     */
    public static void updateAppointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startDate,
                                         LocalDateTime endDate, int customerID, int userID, int contactID){
        String sql = "Update appointments SET " +
                "Title = '" + title + "'," +
                "Description = '" + description + "'," +
                "Location = '" + location + "'," +
                "Type = '" + type + "'," +
                "Start = '" + startDate + "'," +
                "End = '" + endDate + "'," +
                "Last_Update = NOW()," +
                "last_Updated_By = '" + LoginController.user.getUserName() + "'," +
                "Customer_ID = '" + customerID + "'," +
                "User_ID = '" + userID + "'," +
                "Contact_ID = '" + contactID + "'" +
                "WHERE Appointment_ID = " + appointmentID;
        QueryExecution.query(sql);
    }

    /**
     *
     * @param customerID customerID of customer
     * @return True if the customer has and appointment and false otherwise
     */
    public static boolean hasAppointments(int customerID){
        String sql = "SELECT * FROM appointments WHERE Customer_ID = " + customerID;
        QueryExecution.query(sql);
        try {
            return (QueryExecution.getResult().next());
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param customerID customerID
     * @param start start date and time
     * @param end end date and time
     * @return true if the appointment overlaps an existing one and false otherwise
     */
    public static boolean hasOverlappingAppointment(int customerID, LocalDateTime start, LocalDateTime end){
        String sql = "SELECT * FROM appointments WHERE Customer_ID = " + customerID;
        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try{
            while(result.next()){
                LocalDateTime scheduledStart = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime scheduledEnd = result.getTimestamp("End").toLocalDateTime();

                //Check if the date is in between an existing date
                if((scheduledStart.isBefore(end) && scheduledEnd.isAfter(start))){
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param appointmentID appointmentID
     * @param customerID customerID
     * @param start start date and time
     * @param end end date and time
     * @return true if the appointment overlaps an existing one and false otherwise; does not return true if
     * the overlapping appointment is the current appointment being updated
     */
    public static boolean hasOverlappingAppointment(int appointmentID, int customerID, LocalDateTime start, LocalDateTime end){
        String sql = "SELECT * FROM appointments WHERE Customer_ID = " + customerID +
                " AND Appointment_ID != " + appointmentID;
        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try{
            while(result.next()){
                LocalDateTime scheduledStart = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime scheduledEnd = result.getTimestamp("End").toLocalDateTime();

                if((scheduledStart.isBefore(end) && scheduledEnd.isAfter(start))){
                    return true;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes appointment from the database
     *
     * @param appointmentID appointmentID
     *
     */
    public static void deleteAppointment(int appointmentID){
        String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
        QueryExecution.query(sql);
    }

    public static ObservableList<String> getTypes(){
        ObservableList<String> types = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT Type FROM appointments";

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try{
            while(result.next()){
                types.add(result.getString("Type"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return types;
    }

    /**
     *
     * @param type appointment type
     * @param month appointment month
     * @return the amount of appointments of a specific type for the month specified
     */
    public static int appointmentTypePerMonth(String type, int month){
        String sql = "SELECT * FROM appointments WHERE Type = '" + type + "' AND MONTH(Start) = " + month;

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            int count = 0;
            while(result.next()){
                count++;
            }
            return count;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param contactID contactID
     * @return ObservableList of appointments related to the given contactID
     */
    public static ObservableList<Appointment> getContactAppointments(int contactID){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Contact_ID = " + contactID;

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            while (result.next()) {
                int appointmentID = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                int customerID = result.getInt("Customer_ID");
                int userID = result.getInt("User_ID");
                allAppointments.add(new Appointment(appointmentID, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdateBy, customerID, userID, contactID));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     *
     * @param customerID customerID
     * @return ObservableList of appointments related to the given customerID
     */
    public static ObservableList<Appointment> getCustomerAppointments(int customerID){
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Customer_ID = " + customerID;

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            while (result.next()) {
                int appointmentID = result.getInt("Appointment_ID");
                String title = result.getString("Title");
                String description = result.getString("Description");
                String location = result.getString("Location");
                String type = result.getString("Type");
                LocalDateTime start = result.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = result.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                int userID = result.getInt("User_ID");
                int contactID = result.getInt("Contact_ID");
                allAppointments.add(new Appointment(appointmentID, title, description, location, type, start, end, createDate,
                        createdBy, lastUpdate, lastUpdateBy, customerID, userID, contactID));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allAppointments;
    }
}

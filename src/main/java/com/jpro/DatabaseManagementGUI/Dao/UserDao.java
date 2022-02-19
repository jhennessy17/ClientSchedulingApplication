package com.jpro.DatabaseManagementGUI.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import com.jpro.DatabaseManagementGUI.Model.User;
import com.jpro.DatabaseManagementGUI.Utility.QueryExecution;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jeremy Hennessy
 *
 * User Dao Class
 */

public class UserDao {

    /**
     *
     * @param userName userName
     * @param password password
     * @return Specific User related to the username and password
     */
    public static User getUser(String userName, String password){
        String sql = "SELECT * FROM users WHERE User_Name = '" + userName + "' AND Password = '" + password + "';";

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            if(result.next()) {
                int userid = result.getInt("User_ID");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateby = result.getString("Last_Updated_By");
                return new User(userid, userName, password, createDate, createdBy, lastUpdate, lastUpdateby);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param userID userID
     * @return Specific user related to userID
     */
    public static User getUser(int userID){
        String sql = "SELECT * FROM users WHERE User_ID = " + userID;

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            if(result.next()) {
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateby = result.getString("Last_Updated_By");
                return new User(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdateby);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return ObservableList of all users in the database
     */
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM users";

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            while (result.next()) {
                int userid = result.getInt("User_ID");
                String userName = result.getString("User_Name");
                String password = result.getString("Password");
                LocalDateTime createDate = LocalDateTime.of(result.getDate("Create_Date").toLocalDate(), result.getTime("Create_Date").toLocalTime());
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateby = result.getString("Last_Updated_By");
                allUsers.add(new User(userid, userName, password, createDate, createdBy, lastUpdate, lastUpdateby));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allUsers;
    }

}

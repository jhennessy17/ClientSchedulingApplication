package com.jpro.DatabaseManagementGUI.Dao;

import com.jpro.DatabaseManagementGUI.Model.Customer;
import com.jpro.DatabaseManagementGUI.Utility.QueryExecution;
import com.jpro.DatabaseManagementGUI.View_Controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * Customer Dao Class
 */
public class CustomerDao {

    /**
     *
     * @param customerID customerID
     * @return Specific Customer object from database related to customerID
     */
    public static Customer getCustomer(int customerID) {
        String sql = "SELECT * FROM customers WHERE Customer_ID = " + customerID;

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            if (result.next()) {
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                LocalDateTime createDate = LocalDateTime.of(result.getDate("Create_Date").toLocalDate(),
                        result.getTime("Create_Date").toLocalTime());
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                int divisionID = result.getInt("Division_ID");
                return new Customer(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy, divisionID);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return ObservableList of all customers from the database
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customers;";

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            while (result.next()) {
                int customerID = result.getInt("Customer_ID");
                String customerName = result.getString("Customer_Name");
                String address = result.getString("Address");
                String postalCode = result.getString("Postal_Code");
                String phone = result.getString("Phone");
                LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();//LocalDateTime.of(result.getDate("Create_Date").toLocalDate(),
                        //result.getTime("Create_Date").toLocalTime());
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdateBy = result.getString("Last_Updated_By");
                int divisionID = result.getInt("Division_ID");
                allCustomers.add(new Customer(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy, divisionID));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Creates a customer in the database
     *
     * @param customerName customer name
     * @param address address of customer
     * @param postalCode postal code
     * @param phone phone number
     * @param divisionID first level division ID
     *
     */
    public static void createCustomer(String customerName, String address, String postalCode, String phone, int divisionID){
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date," +
                "Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(" +
                "'" + customerName + "'," +
                "'" + address + "'," +
                "'" + postalCode + "'," +
                "'" + phone + "'," +
                "NOW()," +
                "'" + LoginController.user.getUserName() + "'," +
                "NOW()," +
                "'" + LoginController.user.getUserName() + "'," +
                "'" + divisionID +"');";
        QueryExecution.query(sql);
    }

    /**
     * Updates Customer in database
     *
     * @param customerID customer ID
     * @param customerName customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param divisionID first level division ID of customer
     *
     */
    public static void updateCustomer(int customerID, String customerName, String address, String postalCode, String phone, int divisionID){
        String sql = "Update customers SET " +
                "Customer_Name = '" + customerName + "'," +
                "Address = '" + address + "'," +
                "Postal_Code = '" + postalCode + "'," +
                "Phone = '" + phone + "'," +
                "Last_Update = NOW()," +
                "last_Updated_By = '" + LoginController.user.getUserName() + "'," +
                "Division_ID = '" + divisionID + "'" +
                "WHERE Customer_ID = " + customerID;
        QueryExecution.query(sql);
    }


    /**
     * Deletes customer in database corresponding with customerID
     *
     * @param customerID customer ID
     */
    public static void deleteCustomer(int customerID){
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customerID;
        QueryExecution.query(sql);
    }

}

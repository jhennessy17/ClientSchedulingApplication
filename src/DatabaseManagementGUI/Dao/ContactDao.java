package DatabaseManagementGUI.Dao;

import DatabaseManagementGUI.Model.Contact;
import DatabaseManagementGUI.Utility.QueryExecution;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jeremy Hennessy
 *
 * Contact Dao Class
 */

public class ContactDao {

    /**
     *
     * @return ObservableList of all contacts in the database
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts";
        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            while (result.next()) {
                int contactID = result.getInt("Contact_ID");
                String contactName = result.getString("Contact_Name");
                String email = result.getString("Email");

                allContacts.add(new Contact(contactID, contactName, email));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allContacts;
    }

    /**
     *
     * @param contactID contactID
     * @return Contact object related to contactID in database
     */
    public static Contact getContact(int contactID){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM contacts WHERE Contact_ID = " + contactID;
        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();
        try {
            if (result.next()) {
                String contactName = result.getString("Contact_Name");
                String email = result.getString("Email");

                return new Contact(contactID, contactName, email);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}

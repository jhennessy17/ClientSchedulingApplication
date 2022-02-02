package DatabaseManagementGUI.View_Controller;

import DatabaseManagementGUI.Dao.AppointmentDao;
import DatabaseManagementGUI.Dao.ContactDao;
import DatabaseManagementGUI.Dao.CustomerDao;
import DatabaseManagementGUI.Model.Appointment;
import DatabaseManagementGUI.Model.Contact;
import DatabaseManagementGUI.Model.Customer;
import DatabaseManagementGUI.Utility.TimeConversion;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jeremy Hennessy
 *
 * Schedule Controller
 */
public class ScheduleController implements Initializable {

    @FXML private TableView<Appointment> table;
    @FXML private TableColumn<Appointment, Integer> appointmentID;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, String> startDate;
    @FXML private TableColumn<Appointment, String> endDate;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerID;

    @FXML private Label label;
    @FXML private ComboBox people;
    boolean contactDisplay;

    /**
     * Sets up schedule scene
     *
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(cellData -> new SimpleStringProperty(TimeConversion.formatDate(cellData.getValue().getStartDate())));
        endDate.setCellValueFactory(cellData -> new SimpleStringProperty(TimeConversion.formatDate(cellData.getValue().getEndDate())));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**
     * Sets up the scene for contact schedules
     */
    public void setContacts(){
        label.setText("Contact Appointments");
        people.setItems(ContactDao.getAllContacts());
        contactDisplay = true;
    }

    /**
     * Sets up the scene for customer schedules
     */
    public void setCustomers(){
        label.setText("Customer Appointments");
        people.setItems(CustomerDao.getAllCustomers());
        appointmentCustomerID.setVisible(false);
        table.setPrefWidth(1022);
        table.setLayoutX(50);
        contactDisplay = false;
    }

    /**
     * Sets up the table with specific contact or customer selected in the combo box
     */
    public void setTable(){
        if(people.getValue() != null && contactDisplay){
            table.setItems(AppointmentDao.getContactAppointments(((Contact)people.getValue()).getContactID()));
        }else if(people.getValue() != null && !contactDisplay){
            table.setItems(AppointmentDao.getCustomerAppointments(((Customer)people.getValue()).getCustomerID()));
        }
    }

    /**
     * Returns to the main interface
     *
     * @param event event
     * @throws IOException IOException
     */
    public void goBack(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Interface.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.centerOnScreen();
        window.show();
    }
}

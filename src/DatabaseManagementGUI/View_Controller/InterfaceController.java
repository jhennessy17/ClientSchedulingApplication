package DatabaseManagementGUI.View_Controller;

import DatabaseManagementGUI.Dao.AppointmentDao;
import DatabaseManagementGUI.Dao.ContactDao;
import DatabaseManagementGUI.Dao.CustomerDao;
import DatabaseManagementGUI.Dao.FirstLevelDivisionDao;
import DatabaseManagementGUI.Model.Appointment;
import DatabaseManagementGUI.Model.Customer;
import DatabaseManagementGUI.Utility.DBConnection;
import DatabaseManagementGUI.Utility.TimeConversion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Jeremy Hennessy
 *
 * Interface Controller
 */
public class InterfaceController implements Initializable {
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerID;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private TableColumn<Customer, String> phone;
    @FXML private TableColumn<Customer, String> address;
    @FXML private TableColumn<Customer, String> firstLevelDivision;
    @FXML private TableColumn<Customer, String> postalCode;
    @FXML private TableColumn<Customer, String> createDate;
    @FXML private TableColumn<Customer, String> createdBy;
    @FXML private TableColumn<Customer, String> lastUpdate;
    @FXML private TableColumn<Customer, String> lastUpdateBy;

    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentID;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, String> location;
    @FXML private TableColumn<Appointment, String> contact;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, String> startDate;
    @FXML private TableColumn<Appointment, String> endDate;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerID;

    @FXML private RadioButton all;
    @FXML private RadioButton monthly;
    @FXML private RadioButton weekly;
    @FXML private Label upcomingAppointments;

    @FXML private ComboBox<String> months;
    @FXML private ComboBox<String> types;
    @FXML private Label count;

    public static ObservableList<String> monthList = FXCollections.observableArrayList(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
    );

    /**
     * Sets up the Main Menu Interface
     *
     * Lambda is used 6 times in setting the cellValueFactory for 3 columns in each table
     * Lambda easily applies a way to format the date data and to easily convert the ID's of the contact
     * and first level divisions to the corresponding string name values
     *
     * @param url url
     * @param rb rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        //set customer table
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        firstLevelDivision.setCellValueFactory(cellData -> new SimpleStringProperty(FirstLevelDivisionDao.getDivision(cellData.getValue().getDivisionID()).getDivision()));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        createDate.setCellValueFactory(cellData -> new SimpleStringProperty(TimeConversion.formatDate(cellData.getValue().getCreateDate())));
        createdBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdate.setCellValueFactory(cellData -> new SimpleStringProperty(TimeConversion.formatDate(cellData.getValue().getLastUpdate())));
        lastUpdateBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));

        customerTable.setItems(CustomerDao.getAllCustomers());

        //set appointment table
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(cellData -> new SimpleStringProperty(ContactDao.getContact(cellData.getValue().getContactID()).getContactName()));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDate.setCellValueFactory(cellData -> new SimpleStringProperty(TimeConversion.formatDate(cellData.getValue().getStartDate())));
        endDate.setCellValueFactory(cellData -> new SimpleStringProperty(TimeConversion.formatDate(cellData.getValue().getEndDate())));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        appointmentTable.setItems(AppointmentDao.getAllAppointments());

        //set up radio buttons
        ToggleGroup calenderToggle = new ToggleGroup();
        all.setToggleGroup(calenderToggle);
        monthly.setToggleGroup(calenderToggle);
        weekly.setToggleGroup(calenderToggle);

        //set appointment table radio button to all since it will be originally set to all appointments
        all.setSelected(true);

        months.setItems(monthList);
        types.setItems(AppointmentDao.getTypes());
    }

    /**
     * Takes to customer scene and sets it to be ready to add a customer
     *
     * @param event event
     * @throws IOException IOException
     */
    public void addCustomer(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Customer.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.centerOnScreen();
        window.show();
    }

    /**
     * Takes scene to customer scene and sets up the scene to update the selected customer
     *
     * @param event event
     * @throws IOException IOException
     */
    public void updateCustomer(ActionEvent event) throws IOException {
        if (!customerTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Customer.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            CustomerController controller = loader.getController();
            controller.passCustomer(customerTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.centerOnScreen();
            window.show();
        }
    }

    /**
     * Deletes a customer and makes sure no appointments are available for that customer
     * before deleting
     *
     */
    public void deleteCustomer(){
        if (!customerTable.getSelectionModel().isEmpty()) {
            if (AppointmentDao.hasAppointments(customerTable.getSelectionModel().getSelectedItem().getCustomerID())) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Delete Invalid");
                errorAlert.setContentText("Customers With Appointments Must Cancel Appointments First");
                errorAlert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Customer");
                alert.setHeaderText("Delete Customer");
                alert.setContentText("Are You Sure You Would Like To Delete This Customer?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {
                    Customer customer = customerTable.getSelectionModel().getSelectedItem();
                    customerTable.getItems().remove(customer);
                    CustomerDao.deleteCustomer(customer.getCustomerID());
                }
            }
        }
    }

    /**
     * Changes scene to appointment scene and sets it up for adding and appointment
     *
     * @param event event
     * @throws IOException IOException
     */
    public void addAppointment(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Appointment.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.centerOnScreen();
        window.show();
    }

    /**
     * Changes scene to appointment scene and sets it up for updating the appointment that was selected
     *
     * @param event event
     * @throws IOException IOException
     */
    public void updateAppointments(ActionEvent event) throws IOException {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Appointment.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);

            AppointmentController controller = loader.getController();
            controller.passAppointment(appointmentTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.centerOnScreen();
            window.show();
        }
    }

    /**
     * Deletes the appointment
     */
    public void deleteAppointment(){
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Appointment");
            alert.setHeaderText("Delete Appointment");
            alert.setContentText("Are You Sure You Would Like To Delete This Appointment?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
                appointmentTable.getItems().remove(appointment);
                AppointmentDao.deleteAppointment(appointment.getAppointmentID());
            }

        }
    }

    /**
     * Changes the appointment scene to weekly, monthly, or all appointments
     */
    public void toggleMonthAndWeek(){
        if(all.isSelected()) {
            appointmentTable.setItems(AppointmentDao.getAllAppointments());
        } else if(monthly.isSelected()){
            appointmentTable.getItems().clear();
            //gathers all appointments in the month
            for (Appointment appointment : AppointmentDao.getAllAppointments()) {
                if (appointment.getStartDate().getMonth().equals(LocalDate.now().getMonth())) {
                    appointmentTable.getItems().add(appointment);
                }
            }
        } else if(weekly.isSelected()){
            appointmentTable.getItems().clear();
            //gathers all appointments in the week
            for (Appointment appointment : AppointmentDao.getAllAppointments()) {
                if(isSameWeek(appointment.getStartDate().toLocalDate())){
                    appointmentTable.getItems().add(appointment);
                }
            }
        }
    }

    /**
     *
     * @param date date to be checked within the week
     * @return true if the date is in the current week and false otherwise
     */
    public boolean isSameWeek(LocalDate date){
        LocalDate sunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate saturday = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        return (date.isAfter(sunday) || date.isEqual(sunday)) && (date.isBefore(saturday) || date.isEqual(saturday));
    }

    /**
     * Sets the upcoming appointments label to say there are no upcoming appointments
     */
    public void setUpcomingAppointmentsLabel(){
        upcomingAppointments.setText("No Upcoming Appointments");
    }

    /**
     * Exits the program and closes the connection
     */
    public void exit(){
        DBConnection.closeConnection();
        System.exit(0);
    }

    /**
     * Generates a report to show the count for each type of appointment per month
     */
    public void generateCustomerReport(){
        if(months.getValue() != null && types.getValue() != null){
            count.setText(String.valueOf(AppointmentDao.appointmentTypePerMonth(types.getValue(),
                    monthList.indexOf(months.getValue()) + 1)));
        }
    }

    /**
     * Changes the scene to the Schedule scene and sets it up to show customer schedules
     *
     * @param event event
     * @throws IOException IOException
     */
    public void getCustomerSchedule(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Schedule.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        ScheduleController controller = loader.getController();
        controller.setCustomers();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.centerOnScreen();
        window.show();
    }

    /**
     * Changes the scene to the Schedule scene and sets it up to show contact schedules
     *
     * @param event event
     * @throws IOException IOException
     */
    public void getContactSchedule(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Schedule.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        ScheduleController controller = loader.getController();
        controller.setContacts();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.centerOnScreen();
        window.show();
    }

}

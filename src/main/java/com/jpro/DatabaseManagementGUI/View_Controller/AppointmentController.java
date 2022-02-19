package com.jpro.DatabaseManagementGUI.View_Controller;

import com.jpro.DatabaseManagementGUI.Dao.AppointmentDao;
import com.jpro.DatabaseManagementGUI.Dao.ContactDao;
import com.jpro.DatabaseManagementGUI.Dao.CustomerDao;
import com.jpro.DatabaseManagementGUI.Dao.UserDao;
import com.jpro.DatabaseManagementGUI.Model.Appointment;
import com.jpro.DatabaseManagementGUI.Model.Contact;
import com.jpro.DatabaseManagementGUI.Model.Customer;
import com.jpro.DatabaseManagementGUI.Model.User;
import com.jpro.DatabaseManagementGUI.Utility.TimeConversion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

/**
 * @author Jeremy Hennessy
 *
 * Appointment Controller
 */
public class AppointmentController implements Initializable {
    @FXML Label pageTitle;
    @FXML Label appointmentIDLabel;
    @FXML Button addUpdateButton;
    @FXML TextField appointmentID;
    @FXML TextField title;
    @FXML TextField description;
    @FXML TextField location;
    @FXML TextField type;
    @FXML DatePicker date;
    @FXML ComboBox<LocalTime> startTime;
    @FXML ComboBox<LocalTime> endTime;
    @FXML ComboBox<Contact> contact;
    @FXML ComboBox<Customer> customer;
    @FXML ComboBox<User> user;

    /**
     * Initializes the appointment scene
     *
     * Lambda is used here to easily set the DatePicker to not be
     * able to select dates that are before the current date
     *
     * Dates are set to available times in local time zone that correspond with
     * 8am to 10pm EST
     *
     * @param url url
     * @param resourceBundle rb
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Make sure past dates cannot be selected
        //Lambda Expression
        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty){
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        //Converts 8:00AM EST opening business time to the ZoneTime equivalent
        LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0));
        ZonedDateTime estZdt = time.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localZdt = estZdt.withZoneSameInstant(ZoneId.systemDefault());

        LocalTime appointmentTimes = localZdt.toLocalTime();

        //Appointments will be available every 30 minutes from 8:00AM to 10:00PM EST
        //8 to 10 is 14 hours * 2 is 28 because of the 30 minute intervals which is the amount of appointment times available
        //This loop ensures no time outside of business hours will be selected
        for(int i = 0; i <= 28; i++){
            startTime.getItems().add(appointmentTimes);
            appointmentTimes = appointmentTimes.plusMinutes(30);
        }

        contact.setItems(ContactDao.getAllContacts());
        customer.setItems(CustomerDao.getAllCustomers());
        user.setItems(UserDao.getAllUsers());
    }

    /**
     *
     * Sets the scene with the selected appointment passed and prepares for an Update on the database
     *
     * @param appointment appointment used to set the scene elements
     */
    public void passAppointment(Appointment appointment){
        pageTitle.setText("Update Appointment");
        appointmentIDLabel.setVisible(true);
        appointmentIDLabel.setText("Appointment ID");
        appointmentID.setVisible(true);
        appointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        addUpdateButton.setText("Update");
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        location.setText(appointment.getLocation());
        type.setText(appointment.getType());
        date.setValue(appointment.getStartDate().toLocalDate());
        startTime.setValue(appointment.getStartDate().toLocalTime());
        endTime.setValue(appointment.getEndDate().toLocalTime());
        contact.setValue(ContactDao.getContact(appointment.getContactID()));
        customer.setValue(CustomerDao.getCustomer(appointment.getCustomerID()));
        user.setValue(UserDao.getUser(appointment.getUserID()));
        for(int i = startTime.getItems().indexOf(startTime.getValue()) + 1; i < startTime.getItems().size(); i++){
            endTime.getItems().add(startTime.getItems().get(i));
        }
    }

    /**
     *
     * Adjusts endTime to times past the start time
     */
    public void adjustEndTime(){
        if(startTime.getValue() != null){
            endTime.getItems().clear();
            endTime.setValue(null);
            for(int i = startTime.getItems().indexOf(startTime.getValue()) + 1; i < startTime.getItems().size(); i++){
                endTime.getItems().add(startTime.getItems().get(i));
            }
        }
    }

    /**
     *
     * Adds or updates appointment to the database
     *
     * @param event button click
     * @throws IOException IOException
     */
    public void addAppointment(ActionEvent event) throws IOException {
        //Make sure all fields have values
        if(title.getText().isEmpty() || description.getText().isEmpty() || location.getText().isEmpty() || type.getText().isEmpty()
        || date.getValue() == null || startTime.getValue() == null || endTime.getValue() == null || contact.getValue() == null
        || customer.getValue() == null || user.getValue() == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input Not Valid");
            errorAlert.setContentText("All Fields Must Contain Values");
            errorAlert.showAndWait();
            //Make sure Time hasn't already passed today
        } else if(date.getValue().equals(LocalDate.now()) && !startTime.getValue().isAfter(LocalTime.now())){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Time Issue");
            errorAlert.setContentText("That time has already passed today. Choose a later time or date.");
            errorAlert.showAndWait();
        } else{ //Prepare for an update to the database instead of an add if appointmentID already exists
            if(appointmentID.isVisible()){
                //Check for overlapping appointments excluding current appointment
                if(AppointmentDao.hasOverlappingAppointment(Integer.parseInt(appointmentID.getText()), customer.getValue().getCustomerID(),
                        LocalDateTime.of(date.getValue(), startTime.getValue()),
                        LocalDateTime.of(date.getValue(), endTime.getValue()))){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Overlapping Appointments");
                    errorAlert.setContentText("This customer already has an appointment scheduled during this time");
                    errorAlert.showAndWait();
                }else {
                    //Update appointments
                    AppointmentDao.updateAppointment(Integer.parseInt(appointmentID.getText()), title.getText(), description.getText(), location.getText(), type.getText(),
                            TimeConversion.toUTC(LocalDateTime.of(date.getValue(), startTime.getValue())),
                            TimeConversion.toUTC(LocalDateTime.of(date.getValue(), endTime.getValue())),
                            customer.getValue().getCustomerID(),
                            user.getValue().getUserId(), contact.getValue().getContactID());
                }
            }else{
                //Check for overlapping appointments
                if(AppointmentDao.hasOverlappingAppointment(customer.getValue().getCustomerID(),
                        LocalDateTime.of(date.getValue(), startTime.getValue()),
                        LocalDateTime.of(date.getValue(), endTime.getValue()))) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Overlapping Appointments");
                    errorAlert.setContentText("This customer already has an appointment scheduled during this time");
                    errorAlert.showAndWait();
                }else {
                    //add the appointment
                    AppointmentDao.addAppointment(title.getText(), description.getText(), location.getText(), type.getText(),
                            TimeConversion.toUTC(LocalDateTime.of(date.getValue(), startTime.getValue())),
                            TimeConversion.toUTC(LocalDateTime.of(date.getValue(), endTime.getValue())),
                            customer.getValue().getCustomerID(),
                            user.getValue().getUserId(), contact.getValue().getContactID());
                }
            }


            Parent parent = FXMLLoader.load(getClass().getResource("Interface.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(parent));
            window.centerOnScreen();
            window.show();
        }
    }

    /**
     * Sends the scene back to the main menu without touching the database
     *
     * @param event event
     * @throws IOException IOException
     */
    public void cancel(ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("Interface.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(parent));
        window.centerOnScreen();
        window.show();
    }
}

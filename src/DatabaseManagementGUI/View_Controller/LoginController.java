package DatabaseManagementGUI.View_Controller;

import DatabaseManagementGUI.Dao.AppointmentDao;
import DatabaseManagementGUI.Dao.UserDao;
import DatabaseManagementGUI.Model.Appointment;
import DatabaseManagementGUI.Model.User;
import DatabaseManagementGUI.Utility.TimeConversion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * @author Jeremy Hennessy
 *
 * Login Controller
 */
public class LoginController implements Initializable {
    @FXML private Label title;
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label zoneID;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button login;
    private ResourceBundle rb = null;
    public static User user;

    /**
     *Sets up Login Scene in either english or French
     *
     * @param url url
     * @param rb that will hold bundles with French and English language properties
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;

        zoneID.setText(ZoneId.systemDefault().toString());
        title.setText(rb.getString("title"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        login.setText(rb.getString("login"));
    }


    /**
     * Accepts username and password entered and verifies that they are valid in database
     * Records all login attempts to a file called login_activity.txt in the root of this directory
     *
     * @param event event
     * @throws IOException IOException
     */
    public void login(ActionEvent event) throws IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        FileWriter fwriter = new FileWriter("login_activity.txt", true);
        PrintWriter outFile = new PrintWriter(fwriter);
        user = UserDao.getUser(username.getText(), password.getText());


        if(user != null){
            outFile.println("User " + user.getUserName() + " successful login at " + timestamp);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Interface.fxml"));
            Parent tableViewParent = loader.load();

            Scene tableViewScene = new Scene(tableViewParent);
            InterfaceController controller = loader.getController();

            if(!alertAppointment()){
                //if there is no appointment display that in the interface scene
                controller.setUpcomingAppointmentsLabel();
            }

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.centerOnScreen();
            window.show();


        }else{
            outFile.println("Failed login at " + timestamp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("invalidHeader"));
            alert.setHeaderText(rb.getString("invalidHeaderText"));
            alert.setContentText(rb.getString("invalidContextText"));

            alert.showAndWait();
        }
        outFile.close();
    }

    /**
     * Alerts the user whether there is an upcoming appointment or not
     *
     * @return true if there is an appointment within 15 minutes and false otherwise
     */
    public boolean alertAppointment() {
        for(Appointment appointment : AppointmentDao.getUserAppointments(user.getUserId())){
            if(appointment.getStartDate().toLocalDate().equals(LocalDate.now())){
                if(ChronoUnit.MINUTES.between(LocalDateTime.now(), appointment.getStartDate()) <= 15
                && ChronoUnit.MINUTES.between(LocalDateTime.now(), appointment.getStartDate()) >= 0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Upcoming Appointment");
                    alert.setContentText("You have appointment " + appointment.getAppointmentID() +
                            " at " + TimeConversion.formatDate(appointment.getStartDate()));
                    alert.showAndWait();
                    return true;
                }
            }
        }
        return false;
    }

}

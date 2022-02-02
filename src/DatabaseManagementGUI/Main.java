package DatabaseManagementGUI;

import DatabaseManagementGUI.Utility.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Jeremy Hennessy
 *
 * Database Management GUI
 */
public class Main extends Application {

    /**
     * Sets the main stage and the resource bundle for the login scene
     *
     * @param primaryStage primary stage
     * @throws Exception Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle rb = ResourceBundle.getBundle("DatabaseManagementGUI/languageProperties/rb", Locale.getDefault());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("View_Controller/Login.fxml"));
        loader.setResources(rb);
        Parent root = loader.load();
        primaryStage.setTitle(rb.getString("header"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * Starts and ends connection
     * @param args main scene
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}

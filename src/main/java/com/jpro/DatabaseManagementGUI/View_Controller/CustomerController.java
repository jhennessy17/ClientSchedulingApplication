package com.jpro.DatabaseManagementGUI.View_Controller;

import com.jpro.DatabaseManagementGUI.Dao.CountryDao;
import com.jpro.DatabaseManagementGUI.Dao.CustomerDao;
import com.jpro.DatabaseManagementGUI.Dao.FirstLevelDivisionDao;
import com.jpro.DatabaseManagementGUI.Model.Country;
import com.jpro.DatabaseManagementGUI.Model.Customer;
import com.jpro.DatabaseManagementGUI.Model.FirstLevelDivision;
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
import java.util.ResourceBundle;

/**
 * @author Jeremy Hennessy
 *
 * Customer Controller
 */
public class CustomerController implements Initializable {
    @FXML public Label title;
    @FXML public Button addUpdateButton;
    @FXML public Label customerIDLabel;
    @FXML public TextField customerID;
    @FXML public TextField customerName;
    @FXML public TextField address;
    @FXML public TextField postalCode;
    @FXML public TextField phoneNumber;
    @FXML public ComboBox<Country> countries;
    @FXML public ComboBox<FirstLevelDivision> firstLevelDivisions;

    /**
     * Sets the countries combo box to country elements
     *
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countries.setItems(CountryDao.getAllCountries());
    }

    /**
     * Sets scene elements to customer data that way the customer can be updated
     *
     * @param customer customer used to set scene elements
     */
    public void passCustomer(Customer customer){
        title.setText("Update Customer");
        customerIDLabel.setVisible(true);
        customerIDLabel.setText("Customer ID");
        addUpdateButton.setText("Update");
        customerID.setVisible(true);
        customerID.setText((String.valueOf(customer.getCustomerID())));
        customerName.setText(customer.getCustomerName());
        address.setText(customer.getAddress());
        phoneNumber.setText(customer.getPhone());
        postalCode.setText(customer.getPostalCode());
        FirstLevelDivision division = FirstLevelDivisionDao.getDivision(customer.getDivisionID());
        countries.setValue(CountryDao.getCountry(division.getCountryID()));
        firstLevelDivisions.setValue(division);


        //set first level division items
        firstLevelDivisions.setItems(FirstLevelDivisionDao.getRelatedFirstLevelDivisions(countries.getValue().getCountryID()));

    }

    /**
     * Sets the first Level division combo box to first level divisions that correspond to the chosen country
     */
    public void setFirstLevelDivisions(){
        firstLevelDivisions.setItems(FirstLevelDivisionDao.getRelatedFirstLevelDivisions(countries.getValue().getCountryID()));
    }

    /**
     *Updates or Adds the customer to the database
     *
     * @param event event
     * @throws IOException IOException
     */
    public void add(ActionEvent event) throws IOException{
        //Make sure the fields all contain values
        if(customerName.getText().isEmpty() || address.getText().isEmpty() || phoneNumber.getText().isEmpty() || postalCode.getText().isEmpty()
                || countries.getValue() == null || firstLevelDivisions.getValue() == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("All Fields Must Contain Values");
            errorAlert.showAndWait();
        }else{
            //Check whether we are updating or adding
            if(customerID.isVisible()){
                //update customer
                CustomerDao.updateCustomer(Integer.parseInt(customerID.getText()), customerName.getText(), address.getText(), postalCode.getText(), phoneNumber.getText(),
                        firstLevelDivisions.getValue().getDivisionID());
            }else {
                //add customer
                CustomerDao.createCustomer(customerName.getText(), address.getText(), postalCode.getText(), phoneNumber.getText(),
                        firstLevelDivisions.getValue().getDivisionID());
            }
            Parent parent = FXMLLoader.load(getClass().getResource("Interface.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(parent));
            window.centerOnScreen();
            window.show();
        }
    }

    /**
     * Returns the scene to the main menu without adjusting the database
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

package com.jpro.DatabaseManagementGUI.Dao;

import com.jpro.DatabaseManagementGUI.Model.Country;
import com.jpro.DatabaseManagementGUI.Utility.QueryExecution;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * Country DAO Class
 */

public class CountryDao {

    /**
     *
     * @param countryID countryID
     * @return Specific country from database related to countryID
     */
    public static Country getCountry(int countryID){
        String sql = "SELECT * FROM countries WHERE Country_ID = " + countryID;
        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            if (result.next()) {
                String country = result.getString("Country");
                LocalDateTime createDate = LocalDateTime.of(result.getDate("Create_Date").toLocalDate(),
                        result.getTime("Create_Date").toLocalTime());
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = LocalDateTime.of(result.getDate("Last_Update").toLocalDate(),
                        result.getTime("Last_Update").toLocalTime());
                String lastUpdateBy = result.getString("Last_Updated_By");
                return new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdateBy);
            }
            return null;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return ObservableList of all countries in the database
     */
    public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        String sql = "SELECT * FROM countries;";

        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            while (result.next()) {
                int countryID = result.getInt("Country_ID");
                String country = result.getString("Country");
                LocalDateTime createDate = LocalDateTime.of(result.getDate("Create_Date").toLocalDate(),
                        result.getTime("Create_Date").toLocalTime());
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = LocalDateTime.of(result.getDate("Last_Update").toLocalDate(),
                        result.getTime("Last_Update").toLocalTime());
                String lastUpdateBy = result.getString("Last_Updated_By");
                allCountries.add(new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdateBy));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allCountries;
    }
}

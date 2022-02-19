package com.jpro.DatabaseManagementGUI.Dao;

import com.jpro.DatabaseManagementGUI.Model.FirstLevelDivision;
import com.jpro.DatabaseManagementGUI.Utility.QueryExecution;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * First Level Division Dao Class
 */

public class FirstLevelDivisionDao {

    /**
     *
     * @param divisionID division ID
     * @return FirstLevelDivision related to divisionID
     */
    public static FirstLevelDivision getDivision(int divisionID){
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = " + divisionID;
        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            if (result.next()) {
                String division = result.getString("Division");
                LocalDateTime createDate = LocalDateTime.of(result.getDate("Create_Date").toLocalDate(),
                        result.getTime("Create_Date").toLocalTime());
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = LocalDateTime.of(result.getDate("Last_Update").toLocalDate(),
                        result.getTime("Last_Update").toLocalTime());
                String lastUpdateBy = result.getString("Last_Updated_By");
                int countryID = result.getInt("Country_ID");
                return new FirstLevelDivision(divisionID, division, createDate, createdBy, lastUpdate, lastUpdateBy, countryID);
            }
            return null;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param countryID countryID
     * @return ObservableList of FirstLevelDivisions related to countryID
     */
    public static ObservableList<FirstLevelDivision> getRelatedFirstLevelDivisions(int countryID){
        ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = " + countryID;
        QueryExecution.query(sql);
        ResultSet result = QueryExecution.getResult();

        try {
            while (result.next()) {
                int divisionID = result.getInt("Division_ID");
                String division = result.getString("Division");
                LocalDateTime createDate = LocalDateTime.of(result.getDate("Create_Date").toLocalDate(),
                        result.getTime("Create_Date").toLocalTime());
                String createdBy = result.getString("Created_By");
                LocalDateTime lastUpdate = LocalDateTime.of(result.getDate("Last_Update").toLocalDate(),
                        result.getTime("Last_Update").toLocalTime());
                String lastUpdateBy = result.getString("Last_Updated_By");
                allFirstLevelDivisions.add(new FirstLevelDivision(divisionID, division, createDate, createdBy, lastUpdate, lastUpdateBy, countryID));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return allFirstLevelDivisions;
    }

}

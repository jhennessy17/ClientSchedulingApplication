package com.jpro.DatabaseManagementGUI.Model;

import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * First Level Division Class
 */
public class FirstLevelDivision {
    private final int divisionID;
    private final String division;
    private final LocalDateTime createDate;
    private final String createdBy;
    private final LocalDateTime lastUpdate;
    private final String lastUpdateBy;
    private final int countryID;

    /**
     *
     * @param divisionID division ID
     * @param division division name
     * @param createDate creation date
     * @param createdBy user that created
     * @param lastUpdate last update
     * @param lastUpdateBy user that last updated
     * @param countryID related country ID
     */
    public FirstLevelDivision(int divisionID, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.countryID = countryID;
    }

    /**
     * Overrides object's toString method to return division name
     *
     * @return division
     */
    @Override
    public String toString(){
        return division;
    }

    /**
     *
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     *
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @return createDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     *
     * @return createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @return lastUpdate
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @return lastUpdateBy
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }
}

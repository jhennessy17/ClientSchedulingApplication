package DatabaseManagementGUI.Model;

import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * Country Class
 */
public class Country {
    private final int countryID;
    private final String country;
    private final LocalDateTime createDate;
    private final String createdBy;
    private final LocalDateTime lastUpdate;
    private final String lastUpdateBy;

    /**
     *Constructor
     * @param countryID countryID
     * @param country country name
     * @param createDate create date
     * @param createdBy user that created the object
     * @param lastUpdate last update
     * @param lastUpdateBy last update by user
     */
    public Country(int countryID, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Overrides object's toString method to return the country name
     *
     * @return country
     */
    @Override
    public String toString(){
        return country;
    }

    /**
     *
     * @return countryID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @return country
     */
    public String getCountry() {
        return country;
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

}

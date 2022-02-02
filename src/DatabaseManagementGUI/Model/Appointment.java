package DatabaseManagementGUI.Model;

import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * Appointment Class
 */

public class Appointment {
    private final int appointmentID;
    private final String title;
    private final String description;
    private final String location;
    private final String type;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LocalDateTime createDate;
    private final String createdBy;
    private final LocalDateTime lastUpdate;
    private final String lastUpdateBy;
    private final int customerID;
    private final int userID;
    private final int contactID;

    /**
     *Constructor
     * @param appointmentID appointmentID
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startDate startDate
     * @param endDate endDate
     * @param createDate createDate
     * @param createdBy createdBy
     * @param lastUpdate last Update
     * @param lastUpdateBy Last Update By
     * @param customerID Related customerID
     * @param userID Related userId
     * @param contactID Related contactID
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     *
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     *
     * @return endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
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
     * @return customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @return userID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }
}

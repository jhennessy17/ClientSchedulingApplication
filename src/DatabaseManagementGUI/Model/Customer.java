package DatabaseManagementGUI.Model;

import java.time.LocalDateTime;

/**
 * @author Jeremy Hennessy
 *
 * Customer Class
 */
public class Customer {
    private final int customerID;
    private final String customerName;
    private final String address;
    private final String postalCode;
    private final String phone;
    private final LocalDateTime createDate;
    private final String createdBy;
    private final LocalDateTime lastUpdate;
    private final String lastUpdateBy;
    private final int divisionID;

    /**
     * Constructor
     * @param customerID customerID
     * @param customerName customer Name
     * @param address address of customer
     * @param postalCode postal code of customer
     * @param phone phone number of customer
     * @param createDate date customer was created
     * @param createdBy user that created the customer
     * @param lastUpdate last update of customer
     * @param lastUpdateBy last user that updated the customer
     * @param divisionID divisionID related to the customer
     */
    public Customer(int customerID, String customerName, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.divisionID = divisionID;
    }

    /**
     * Overrides object's toString method to return customer's name
     *
     * @return customerName
     */
    @Override public String toString(){
        return customerName;
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
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @return postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
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
     * @return divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }
}

package DatabaseManagementGUI.Model;

/**
 * @author Jeremy Hennessy
 *
 * Contact Class
 */
public class Contact {
    private final int contactID;
    private final String contactName;
    private final String email;

    /**
     *Constructor
     * @param contactID contactID
     * @param contactName contact name
     * @param email email
     */
    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Overrides object's toString method to return a string of the contactName
     *
     * @return contactName
     */
    @Override public String toString(){
        return contactName;
    }

    /**
     *
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }
}

package workshop.server.common;

public interface IDataAccess {
    /**
     * Conects to the database.
     * @return true if connection was successfully completed.
     */
    public boolean connect ();

    /**
     * Disconects from database.
     */
    public void disconnect ();

    /**
     * Adds user with current username and password.
     * @param username is name of user.
     * @param password is password of user.
     * @return true if user was successfully added.
     */
    public boolean addUser(String username, String password);
    
    /**
     * Checks is user exists in database.
     * @param username is name of user.
     * @param password is password of user.
     * @return true if user exists.
     */
    public boolean checkUser(String username, String password);
    
    /**
     * Gets shedule from database.
     * @return string of busy days, separated by ";".
     */
    public String getShedule();
    
    /**
     * Makes order.
     * @param username is name of user.
     * @param description is description of the proplem.
     * @param phone is phine number.
     * @param time is time from shedule.
     * @return true if order was made successfully. 
     */
    public boolean makeOrder(String username, String description, String phone, String time);
    
    /**
     * Gets orders of current user.
     * @param username is user's name.
     * @return string of orders, separated by ";".
     */
    public String getOrders(String username);
}

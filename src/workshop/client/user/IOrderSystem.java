package workshop.client.user;

public interface IOrderSystem {
    /**
     * Gets shedule.
     * @return list of times when workshop is busy.
     */
    public String[] getShedule();
    
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
     * @return array of orders.
     */
    public String[] getOrders(String username);
}

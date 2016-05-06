package workshop.server.usercontroller;

import workshop.common.ISocketConnection;
import workshop.common.Request;

public interface IUserController {
    
    /**
     * Process the requeest.
     * @param request is input request.
     * @param socketConection is socket connection with the client.
     */
    public void processRequest(Request request, ISocketConnection socketConection);
    
    /**
     * Registration of user with current login and password.
     * @param username is user's username.
     * @param password is user's password.
     * @param socketConnection is socket connection with user.
     */
    public void registration(String username, String password,
            ISocketConnection socketConnection);
    
    /**
     * Authorization of user with current login and password.
     * @param username is user's username.
     * @param password is user's password.
     * @param socketConnection is socket connection with user.
     */
    public void authorization(String username, String password,
            ISocketConnection socketConnection);
    
    /**
     * Gets shedule.
     * @param socketConnection is socket connection with user.
     */
    public void getShedule(ISocketConnection socketConnection);
    
    /**
     * Makes order.
     * @param username is name of user.
     * @param description is description of the proplem.
     * @param phone is phine number.
     * @param time is time from shedule.
     * @param socketConnection is socket connection with the client.
     */
    public void makeOrder(String username, String description, String phone, String time,
            ISocketConnection socketConnection);
    
    /**
     * Gets orders of current user.
     * @param username is user's name.
     * @param socketConnection is socket connection with the client.
     */
    public void getOrders(String username, ISocketConnection socketConnection);
}

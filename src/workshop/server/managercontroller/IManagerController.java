package workshop.server.managercontroller;

import workshop.common.ISocketConnection;
import workshop.common.Request;

public interface IManagerController {
    
    /**
     * Process the requeest.
     * @param request is input request.
     * @param socketConection is socket connection with the client.
     */
    public void processRequest(Request request, ISocketConnection socketConection);
    
    /**
     * Gets shedule.
     * @param socketConnection is socket connection with user.
     */
    public void getShedule(ISocketConnection socketConnection);
    
    public void getInfo(ISocketConnection socketConnection, String time);
}

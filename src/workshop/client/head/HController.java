package workshop.client.head;

import workshop.client.manager.Controller;
import workshop.common.ISocketConnection;

public class HController extends Controller implements IHController{

    public HController(ISocketConnection socketConnection) {
        super(socketConnection);
    }
    
}

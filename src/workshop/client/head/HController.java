package workshop.client.head;

import workshop.client.manager.Controller;
import workshop.common.Constants;
import workshop.common.ISocketConnection;
import workshop.common.Request;

public class HController extends Controller implements IHController{

    public HController(ISocketConnection socketConnection) {
        super(socketConnection);
    }

    @Override
    public String getLog() {
        Request request = new Request();
        request.senderType = Constants.HEAD;
        request.requestType = Constants.GET_LOG;
        request.body = "";
        Request response = mSocketConnection.sendAndGetResponse(request);

        return response.body;
    } 
}

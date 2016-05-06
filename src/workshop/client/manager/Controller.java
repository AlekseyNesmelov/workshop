package workshop.client.manager;

import workshop.common.Constants;
import workshop.common.ISocketConnection;
import workshop.common.Request;

public class Controller implements IController {

    private final ISocketConnection mSocketConnection;

    public Controller(ISocketConnection socketConnection) {
        mSocketConnection = socketConnection;
    }

    @Override
    public String[] getShedule() {
        Request request = new Request();
        request.senderType = Constants.MANAGER;
        request.requestType = Constants.GET_SHEDULE;
        request.body = "";
        Request response = mSocketConnection.sendAndGetResponse(request);

        if (response.body.equals(Constants.FAIL)) {
            return null;
        }
        return response.body.split(";");
    }

    @Override
    public String getInfo(String time) {
        Request request = new Request();
        request.senderType = Constants.MANAGER;
        request.requestType = Constants.GET_INFO;
        request.body = time;
        Request response = mSocketConnection.sendAndGetResponse(request);

        if (response.body.equals(Constants.FAIL)) {
            return null;
        }
        return response.body;
    }
}

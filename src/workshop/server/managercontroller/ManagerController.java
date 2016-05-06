package workshop.server.managercontroller;

import workshop.common.Constants;
import workshop.common.ISocketConnection;
import workshop.common.Request;
import workshop.server.common.DataAccess;
import workshop.server.common.IDataAccess;

public class ManagerController implements IManagerController {
    
    IDataAccess mDataAccess = DataAccess.getInstance();
    
    public ManagerController() {
    }
    
    @Override
    public void processRequest(Request request, ISocketConnection socketConnection) {
        switch (request.requestType) {
            case Constants.GET_SHEDULE: {
                getShedule(socketConnection);
                break;
            }
            case Constants.GET_INFO: {
                getInfo(socketConnection, request.body);
                break;
            }
        }
    }   
    
    private void sendFail(ISocketConnection socketConnection) {
        Request response = new Request();
        response.senderType = Constants.SERVER;
        response.requestType = Constants.RESPONSE;
        response.body = Constants.FAIL;
        socketConnection.send(response);
    }

    @Override
    public void getShedule(ISocketConnection socketConnection) {
        String times = mDataAccess.getShedule();
        if (times != null) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.GET_SHEDULE;
            response.body = times;
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }

    @Override
    public void getInfo(ISocketConnection socketConnection, String time) {
        String info = mDataAccess.getInfo(time);
        if (info != null) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.GET_INFO;
            response.body = info;
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }
}

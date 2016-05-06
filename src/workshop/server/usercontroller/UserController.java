package workshop.server.usercontroller;

import workshop.common.Constants;
import workshop.common.ISocketConnection;
import workshop.common.Request;
import workshop.server.common.DataAccess;
import workshop.server.common.IDataAccess;

public class UserController implements IUserController {
    
    IDataAccess mDataAccess = DataAccess.getInstance();
    
    public UserController() {
    }
    
    @Override
    public void registration(String username, String password,
            ISocketConnection socketConnection) {
        if (mDataAccess.addUser(username, password)) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.REGISTRATION;
            response.body = Constants.SUCCESS;
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }

    @Override
    public void authorization(String username, String password,
            ISocketConnection socketConnection) {
        if (mDataAccess.checkUser(username, password)) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.AUTHORIZATION;
            response.body = Constants.SUCCESS;
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }

    @Override
    public void processRequest(Request request, ISocketConnection socketConnection) {
        switch (request.requestType) {
            case Constants.REGISTRATION: {
                String[] body = request.body.split(":", 2);
                if (body.length == 2) {
                    registration(body[0], body[1], socketConnection);
                } else {
                    sendFail(socketConnection);
                }
                break;
            }
            case Constants.AUTHORIZATION: {
                String[] body = request.body.split(":", 2);
                if (body.length == 2) {
                    authorization(body[0], body[1], socketConnection);
                } else {
                    sendFail(socketConnection);
                }
                break;
            }
            case Constants.GET_SHEDULE: {
                getShedule(socketConnection);
                break;
            }
            case Constants.MAKE_ORDER: {
                String[] body = request.body.split("=", 4);
                if (body.length == 4) {
                    makeOrder(body[0], body[1], body[2], body[3], socketConnection);
                } else {
                    sendFail(socketConnection);
                }
                break;
            }
            case Constants.GET_ORDERS: {
                getOrders(request.body, socketConnection);
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
    public void makeOrder(String username, String description, String phone, String time,
            ISocketConnection socketConnection) {
        if (mDataAccess.makeOrder(username, description, phone, time)) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.MAKE_ORDER;
            response.body = Constants.SUCCESS;
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }

    @Override
    public void getOrders(String username, ISocketConnection socketConnection) {
        String orders = mDataAccess.getOrders(username);
        if (orders != null) {
            Request response = new Request();
            response.senderType = Constants.SERVER;
            response.requestType = Constants.GET_ORDERS;
            response.body = orders;
            socketConnection.send(response);
        } else {
            sendFail(socketConnection);
        }
    }
}

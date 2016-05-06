package workshop.client.user;

import workshop.common.ISocketConnection;

public class Controller implements IController {
    private final ISocketConnection mSocketConnection;
    private final IRegistrationSystem mRegistrationSystem;
    private final IAuthorizationSystem mAuthorizationSystem;
    private final IOrderSystem mOrderSystem;

    public Controller(ISocketConnection socketConnection) {
        mSocketConnection = socketConnection;
        mRegistrationSystem = new RegistrationSystem(mSocketConnection);
        mAuthorizationSystem = new AuthorizationSystem(mSocketConnection);
        mOrderSystem = new OrderSystem(mSocketConnection);
    }

    @Override
    public boolean registration(String username, String password) {
        return mRegistrationSystem.registration(username, password);
    }

    @Override
    public boolean authorization(String username, String password) {
        return mAuthorizationSystem.authorization(username, password);
    } 

    @Override
    public String[] getShedule() {
        return mOrderSystem.getShedule();
    }

    @Override
    public boolean makeOrder(String username, String description, String phone, String time) {
        return mOrderSystem.makeOrder(username, description, phone, time);
    }

    @Override
    public String[] getOrders(String username) {
        return mOrderSystem.getOrders(username);
    }
}

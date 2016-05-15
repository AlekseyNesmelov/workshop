package workshop.client.user;

import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import workshop.common.ISocketConnection;
import workshop.common.SocketConnection;

public class UControllerTest {
    private static final String IP = "localhost";
    private static final int PORT = 8888;
    private static final ISocketConnection socketConnection = new SocketConnection();
    private static UController uController = null;

    
    public UControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        if (socketConnection.connect(IP, PORT)) {
            uController = new UController(socketConnection);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        socketConnection.disconnect();
    }
    
    @Test
    public void testMethods() {
        testSuccessRegistration();
        
        testFailedRegistration();
        
        testAuthorization();
        
        testGetSchedule();
        
        testSuccessMakeOrder();
        
        testFailedMakeOrder();
        
        testGetOrders();
        
        testAcceptOrder();
        
        testRejectOrder();
    }
    
    public void testSuccessRegistration() {      
        boolean johnReg = uController.registration("John", "asdfghjk");
                
        assertTrue(johnReg);
    }
    
    public void testFailedRegistration() {      
        boolean alexReg = uController.registration("Alex", "asdfghjk");
        
        assertFalse(alexReg);
    }
    
    public void testAuthorization() {      
        boolean johnReg = uController.authorization("John", "asdfghjk");
        boolean alexReg = uController.authorization("Alex", "asdfghjk");
        
        assertTrue(johnReg && alexReg);
    }
    
    public void testGetSchedule() {
        String[] shedule = uController.getShedule();
        
        assertNotNull(shedule); 
    }
    
    public void testSuccessMakeOrder() {
        boolean ordersWasMade = uController.makeOrder("Alex", "abc", "111111", "13:00-15.05.2016");
        
        assertTrue(ordersWasMade); 
    }
    
    public void testFailedMakeOrder() {
        boolean ordersWasMade = uController.makeOrder("Alex", "abc", "111111", "12:00-15.05.2016");
        
        assertFalse(ordersWasMade); 
    }
    
    public void testGetOrders() {
        String[] orders = uController.getOrders("Andrey");
        
        assertTrue(orders[0].contains("абв") && orders[1].contains("где")); 
    }
    
    public void testAcceptOrder() {
        uController.acceptOrder("Andrey", "12:00-15.05.2016");
        
        String[] orders = uController.getOrders("Andrey");
        
        assertTrue(orders[1].contains("Согласие клиента")); 
    }
    
    public void testRejectOrder() {
        uController.rejectOrder("Andrey", "12:00-15.05.2016");
        
        String[] orders = uController.getOrders("Andrey");
        
        assertTrue(orders[1].contains("Отказ клиента")); 
    }
}

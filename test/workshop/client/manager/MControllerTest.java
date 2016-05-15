package workshop.client.manager;

import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import workshop.common.ISocketConnection;
import workshop.common.SocketConnection;

public class MControllerTest {
    private static final String IP = "localhost";
    private static final int PORT = 8888;
    private static final ISocketConnection socketConnection = new SocketConnection();
    private static MController mController = null;

    
    public MControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        if (socketConnection.connect(IP, PORT)) {
            mController = new MController(socketConnection);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        socketConnection.disconnect();
    }
    
    @Test
    public void testMethods() {
        testGetSchedule();
 
        testGetInfo();
        
        testSetStatus();
        
        testChangeTime();
        
        testDeleteTime();    
    }
    
    public void testGetSchedule() {
        String[] shedule = mController.getShedule();
        
        assertNotNull(shedule); 
    }
    
    public void testGetInfo() {
        String info = mController.getInfo("11:00-15.05.2016");
        
        assertNotNull(info.contains("абв")); 
    }
    
    public void testSetStatus() {
        String response = mController.editStatus("11:00-15.05.2016", "В работе", "qwerty");
        
        assertTrue(response.contains("Статус был изменен")); 
    }
    
    public void testChangeTime() {
        String response = mController.changeTime("11:00-15.05.2016", "14:00-15.05.2016");
        
        assertTrue(response.contains("Время заказа изменено"));
    }
    
    public void testDeleteTime() {
        String response = mController.deleteRecord("14:00-15.05.2016");
        
        assertTrue(response.contains("Заказ был удален"));
    }
}
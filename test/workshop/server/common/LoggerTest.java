package workshop.server.common;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class LoggerTest {    
    private ILogger logger;
    
    public LoggerTest() {
    }
    
    @Before
    public void setUp() {
        logger = new Logger("log.txt");      
    }
    
    @Test
    public void testMethods() {
        testLog();
        
        testGetLog();
    }
    
    public void testLog() {
        logger.log("qwerty");
        
        String log = logger.getLog();
        
        assertTrue(log.contains("qwerty"));
    } 
    
    public void testGetLog() {
        String log = logger.getLog();
        
        assertTrue(log.contains("qwerty"));
    }
}

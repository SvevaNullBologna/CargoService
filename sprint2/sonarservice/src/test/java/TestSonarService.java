
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class TestSonarService{
	private static Interaction conn;

    @BeforeClass
    public static void setup() {
        conn = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "localhost", "8004");
    }
    
    @Test
    public void testProductDetection() throws Exception{
    	String event = CommUtils.buildEvent("junitTest", "productDetected", "productDetected(99)").toString();
        conn.forward(event);

        // Attendo eventuale risposta (se qualche attore risponde via TCP)
        String answer = conn.receiveMsg();
        System.out.println("Answer: " + answer);
        assertNotNull("Deve arrivare una risposta o notifica", answer);
    }
    
    @Test
    public void testSendProductDetected() throws Exception {
        String event = CommUtils.buildEvent("junitTest", "productDetected", "productDetected(42)").toString();
        conn.forward(event);   // pubblico l’evento
        System.out.println("Sent: " + event);
    }
    
    
    @Test
    public void testSendAnomaly() throws Exception {
        String event = CommUtils.buildEvent("junitTest", "anomalyDetected", "anomalyDetected(40)").toString();
        conn.forward(event);
        System.out.println("Sent: " + event);
    }
    
    @Test
    public void testSendAnomalyFixed() throws Exception {
        String event = CommUtils.buildEvent("junitTest", "anomalyFixed", "anomalyFixed(0)").toString();
        conn.forward(event);
        System.out.println("Sent: " + event);
    }
    
    @Test
    public void waitNextEvent() throws Exception {
        System.out.println("In attesa del prossimo evento...");
        
        String msg = conn.receiveMsg();   // blocca finché non arriva un messaggio
        System.out.println("Ricevuto: " + msg);
        
        assertNotNull("Doveva arrivare un evento", msg);
        // opzionale: verifico che sia proprio un evento
        assertTrue("Non sembra un evento", msg.contains("event"));
    }
}
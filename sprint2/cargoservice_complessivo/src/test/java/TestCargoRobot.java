package test.java;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class TestCargoRobot {
    
    private static Interaction conn;

    @BeforeClass
    public static void setup() {
        conn = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "localhost", "8000");
    }

    @Test
    public void testDeliveryCommand() throws Exception {
        String cmdMsg = CommUtils.buildDispatch("tester", "command", "command(0,0)", "cargorobot").toString();
        System.out.println("Invio comando: " + cmdMsg);
        conn.forward(cmdMsg);

        Thread.sleep(1000);
        
        assertTrue(true);
    }

    @Test
    public void testAnomalyDetected() throws Exception {
        // Simulo evento anomalyDetected
        String anomalyMsg = CommUtils.buildEvent("sonar_mock", "anomalyDetected", "anomalyDetected(T)").toString();
        System.out.println("Invio evento anomalia: " + anomalyMsg);
        conn.forward(anomalyMsg);

        Thread.sleep(500);

        // Ora simulo evento anomalyFixed dopo ritardo
        String fixedMsg = CommUtils.buildEvent("sonar_mock", "anomalyFixed", "anomalyFixed(T)").toString();
        System.out.println("Invio evento anomalia fissata: " + fixedMsg);
        conn.forward(fixedMsg);

        Thread.sleep(500);

        assertTrue(true); // placeholder per verifiche
    }

    @Test
    public void testMoveFailed() throws Exception {
        String failMsg = CommUtils.buildReply("basicrobot", "moverobotfailed", "moverobotfailed(T)", "cargorobot").toString();
        System.out.println("Invio moverobotfailed: " + failMsg);
        conn.forward(failMsg);

        Thread.sleep(500);

        assertTrue(true); 
    }
}

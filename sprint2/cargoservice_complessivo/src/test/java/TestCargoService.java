package test.java;

import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class TestCargoService {
    
	private static Interaction conn;
	
	@BeforeClass
	public static void setup() {
		conn = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "localhost", "8000");
	}
	
	
    @Test
    public void testLoadRequestAccepted() throws Exception { //usiamo un pid che è presente nel DB
        String requestStr = CommUtils.buildRequest("tester","loadrequest", "loadrequest(10)", "cargoservice").toString();
        System.out.println("Richiesta: " + requestStr);
        String response = conn.request(requestStr);
        System.out.println("Risposta: " + response);
        assertTrue("TEST: richiesta accettata", response.contains("accepted"));
    }
    
    @Test
    public void testLoadRequestRejectedByProductService() throws Exception {//usiamo un pid che non è presente nel DB
        // PID inesistente (es: 999)
        String requestStr = CommUtils.buildRequest("tester", "loadrequest", "loadrequest(-999)", "cargoservice").toString();
        System.out.println("Richiesta: " + requestStr);
        String response = conn.request(requestStr);
        System.out.println("Risposta: " + response);

        assertTrue("TEST: richiesta rifiutata per PID inesistente", response.contains("refused"));
    }

    @Test
    public void testLoadRequestRejectedForWeight() throws Exception {
        // PID troppo pesante (es: 3 = 1100)
        String requestStr = CommUtils.buildRequest("tester", "loadrequest", "loadrequest(3)", "cargoservice").toString();
        System.out.println("Richiesta: " + requestStr);
        String response = conn.request(requestStr);
        System.out.println("Risposta: " + response);

        assertTrue("TEST: richiesta rifiutata per peso eccessivo", 
            response.contains("refused"));
    }

    @Test
    public void testLoadRequestRejectedForSlot() throws Exception {
        String requestStr = CommUtils.buildRequest(
            "tester", 
            "loadrequest",          
            "loadrequest(20)",      // PID 20 che dovrebbe far scattare il rifiuto per slot occupati
            "cargoservice"
        ).toString();

        System.out.println("Richiesta: " + requestStr);

        String response = conn.request(requestStr);

        System.out.println("Risposta: " + response);

        // Controlla che la risposta contenga 'refused' (come da tuo Qak)
        assertTrue("TEST: richiesta rifiutata per slot pieni",
            response.contains("refused"));
    }


    
   
    
    
}
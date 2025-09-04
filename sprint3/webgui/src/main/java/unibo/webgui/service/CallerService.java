package unibo.webgui.service;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.webgui.utils.HoldResponseParser;
import unibo.webgui.ws.WSHandler;

@RestController
public class CallerService {

    @Autowired
    private WSHandler wsHandler;

    private Interaction conn;

    public CallerService() {
        try {
        	// usa questo da dentro i container
        	//conn = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "cargoservice", "8000");
            conn = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "127.0.0.1", "8000");
            //conn = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "localhost", "8000");
        } catch (Exception e) {
            System.err.println("Errore nella connessione TCP iniziale: " + e.getMessage());
        }
    }

    @GetMapping("/caller")
    public String callCargoservice(@RequestParam("pid") String pid) {
        try {
        	CommUtils.outblue("send request to cargoservice");
        	IApplMessage getreq = CommUtils.buildRequest(
        		    "webgui",
        		    "sendrequest",
        		    "sendrequest(\""+pid+"\")",  // <- nota le virgolette
        		    "companyrequestreceiver"
        		);
        	IApplMessage answer = conn.request(getreq,10000);  //raises exception
            return cleanMessage(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
    
    
    private String cleanMessage(IApplMessage answer) {
    	if(answer != null) {
            String full = answer.msgContent();
            int start = full.indexOf('\''); 
            int end   = full.lastIndexOf('\'');
            if(start >= 0 && end > start) {
                return full.substring(start+1, end);
            } else {
                return full;
            }
        } else {
            return "late response";
        }
    }
}
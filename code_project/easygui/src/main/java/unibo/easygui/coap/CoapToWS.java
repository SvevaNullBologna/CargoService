package unibo.easygui.coap;

//QUESTO FILE FA DA PONTE TRA UN ENDPOINT COAP E IL WEBSOCKET DEL FRONTEND HTML

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unibo.basicomm23.utils.CommUtils;
import unibo.easygui.utils.HoldResponseParser;
import unibo.easygui.ws.WSHandler;
import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class CoapToWS {
	// nome che viene risolto da dentro il container
//	private static final String COAP_ENDPOINT = "coap://arch3:8000/ctx_cargoservice/hold";
    private static final String COAP_ENDPOINT = "coap://127.0.0.1:8000/ctx_easy/example";

    private CoapClient client;
    private CoapObserveRelation observeRelation;

    @Autowired
    private WSHandler wsHandler;

    @PostConstruct
    public void init() {
        client = new CoapClient(COAP_ENDPOINT);
        observeRelation = client.observe(new CoapHandler() {
            @Override
            public void onLoad(CoapResponse response) {
                String content = response.getResponseText();
                CommUtils.outblue("CoAP payload: " + content);

                //per il funzionamento vero e proprio
                try {
                    JSONObject payload = HoldResponseParser.parseHoldState(content);
                    if (payload != null) {
                        wsHandler.sendToAll(payload.toString());
                    } else {
                    	CommUtils.outred("Evento CoAP non valido: " + content);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //per il test della stringa
                /*try {
                	wsHandler.sendToAll(content);
                }
                catch(IOException e) {
                	CommUtils.outred("errore. Evento CoAP non valido!");
                }*/
            }

            @Override
            public void onError() {
                System.err.println("Errore nell'osservazione CoAP.");
            }
        });
        System.out.println("Iniziata osservazione CoAP su: " + COAP_ENDPOINT);
    }
}

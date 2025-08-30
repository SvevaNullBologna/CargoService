package unibo.webgui.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HoldResponseParser {
	

    // Parsa una stringa JSON completa e restituisce un oggetto JSON 
	// nel formato leggibile dalla webgui.
	public static JSONObject parseHoldState(String message) {
	    String jsonString = null;
	    JSONObject payload = new JSONObject();

	    // Rimuovo eventuali apici esterni
	    if (message.startsWith("'") && message.endsWith("'")) {
	        jsonString = message.substring(1, message.length() - 1);
	    } else if (message.startsWith("{")) {
	        jsonString = message;
	    } else {
	        System.err.println("Messaggio non riconosciuto: " + message);
	        return payload;
	    }

	    // Parsing del JSON originale
	    JSONObject original = new JSONObject(jsonString);
	    
	    //controllo del tipo di messaggio
	    String type = original.optString("type", "unknown");
        payload.put("type", type);
        
        switch(type) {
        case "holdUpdate":
            int currentLoad = original.getInt("current_hold_weight");
            JSONArray slotsArray = original.getJSONArray("slots");

            List<String> slotStatusList = new ArrayList<>();
            for (int i = 0; i < slotsArray.length(); i++) {
                JSONObject slot = slotsArray.getJSONObject(i);
                boolean available = slot.getBoolean("available");
                slotStatusList.add(available ? "libero" : "pieno");
            }
            
            payload.put("shipLoad", currentLoad);
            payload.put("slots", slotStatusList);
            break;

        case "endOfRequest":
            String result = original.optString("result", "unknown");
            payload.put("result", result);
            break;

        default:
            System.err.println("Tipo di messaggio non riconosciuto: " + type);
            break;
        }
        
        return payload;
	   
	}
}



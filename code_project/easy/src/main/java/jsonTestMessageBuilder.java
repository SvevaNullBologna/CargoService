package main.java;

import org.json.JSONObject;
import org.json.JSONArray;


public class jsonTestMessageBuilder{
	public jsonTestMessageBuilder() {
		
	}
	
	public static String holdToJson() {
		JSONObject obj = new JSONObject();
	    obj.put("maxload", 1000);
	    obj.put("current_hold_weight", 100);

	    JSONArray slotsArray = new JSONArray();
	    for (int i = 1; i <= 5; i++) {
	        JSONObject slotObj = new JSONObject();
	        slotObj.put("id", i);
	        slotObj.put("x", i);
	        slotObj.put("y", i);
	        slotObj.put("dir","up");
	        slotObj.put("available", true);
	        slotsArray.put(slotObj);
	    }

	    obj.put("slots", slotsArray);
	    return "'" + obj.toString() + "'";
	}
}
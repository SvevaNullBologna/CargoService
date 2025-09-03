package main.java;


import main.java.Slots;
import main.java.Slot;
import org.json.JSONObject;
import org.json.JSONArray;

public class HoldData{
	private Slots s;
	private int MaxLoad = 1000;
	private int Cur_HoldWeight;
	
	public HoldData() {
		s = new Slots();
		Cur_HoldWeight = 0; 
	}
	
	public boolean canLoad(int slotID, int productWeight) {
		return productWeight > 0 && (Cur_HoldWeight + productWeight) <= MaxLoad && slotID != -1 ;
	}
	
	public Slot getAvailableSlot(){
		int id = s.getAvailableSlotID();
		return s.getSlotById(id);
	}
	
	public void registerProductInSlot(Slot slot, int productWeight) {
	        s.registerProductInSlot(slot);
	        Cur_HoldWeight += productWeight;
	}

	public String checkResultToJson(Slot slot, int pid, int weight) {
	    JSONObject obj = new JSONObject();
	    obj.put("pid", pid);
	    obj.put("weight", weight);
	    obj.put("id", slot.getId());
	    obj.put("x", slot.getDepositX());
	    obj.put("y", slot.getDepositY());
	    obj.put("dir", slot.getDepositDir());
	    return "'" + obj.toString() + "'";
	}
	
	public String holdToJson() {
	    JSONObject obj = new JSONObject();
	    obj.put("maxload", MaxLoad);
	    obj.put("current_hold_weight", Cur_HoldWeight);

	    JSONArray slotsArray = new JSONArray();
	    for (int i = 1; i <= 5; i++) {
	        Slot slot = s.getSlotById(i);
	        JSONObject slotObj = new JSONObject();
	        slotObj.put("id", slot.getId());
	        slotObj.put("x", slot.getDepositX());
	        slotObj.put("y", slot.getDepositY());
	        slotObj.put("dir", slot.getDepositDir());
	        slotObj.put("available", slot.isAvaiable());
	        slotsArray.put(slotObj);
	    }

	    obj.put("slots", slotsArray);
	    return "'" + obj.toString() + "'";
	}
	
}
package main.java;


import main.java.Slot;
import java.util.List;
import java.util.ArrayList;


public class Slots{
	private List<Slot> slotList;
	public Slots(int numberOfSlots, int numberOfSpaces) {
		slotList = new ArrayList<Slot>();
		for(int i=1; i<=numberOfSlots; i++) {
			slotList.add(new Slot(i, numberOfSpaces));
		}
	}
	
	public int getAvaiableSlot(){
		for(Slot s: slotList) {
			if(s.isAvaiable()){
				return s.getId();
			}
		}
		return -1;
	}
	
	public void setAvaiableSlot(int slot){
		slot.occupySpace();
	}
}
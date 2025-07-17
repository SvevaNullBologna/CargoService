package main.java;


import main.java.Slot;
import java.util.List;
import java.util.ArrayList;


public class Slots{
	private List<Slot> slotList;
	public Slots(int numberOfSlots, int numberOfSpaces) {
		slotList = new ArrayList<Slot>();
		for(int i=1; i<=numberOfSlots; i++) {
			switch(i) {
			case 1 : slotList.add(new Slot(i, numberOfSpaces, 1, 1)); break;
			case 2 : slotList.add(new Slot(i, numberOfSpaces, 2, 2)); break;
			case 3 : slotList.add(new Slot(i, numberOfSpaces, 3, 3)); break;
			case 4 : slotList.add(new Slot(i, numberOfSpaces, 4, 4)); break;
			case 5 : slotList.add(new Slot(i, numberOfSpaces, 5, 5)); break;
			}
			
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
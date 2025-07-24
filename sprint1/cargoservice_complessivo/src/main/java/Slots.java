package main.java;

import java.util.List;
import java.util.ArrayList;


public class Slots{
	private ArrayList<Slot> slotList;
	public Slots() {
		slotList = new ArrayList<Slot>();
		for(int i=1; i<=5; i++) {
			switch(i) {
			case 1 : slotList.add(new Slot(i,1,2)); break;
			case 2 : slotList.add(new Slot(i,1,3)); break;
			case 3 : slotList.add(new Slot(i,3,3)); break;
			case 4 : slotList.add(new Slot(i,3,4)); break;
			case 5 : slotList.add(new Slot(i,2,5)); break;
			}
			
		}
	}
	
	public Slot getSlotById(int id){
	    for(Slot s : slotList){
	        if(s.getId() == id) return s;
	    }
	    return null;
	}

	
	public String getSlotPositionById(int id) {
		return slotList.get(id-1).getPosition();
	}
	
	public int getAvaiableSlot(){
		for(Slot s: slotList) {
			if(s.isAvaiable()){
				return s.getId();
			}
		}
		return -1;
	}
	
	
	public void registerProductInSlot(int id) {
		this.registerProductInSlot(getSlotById(id));
	}
	public void registerProductInSlot(Slot slot){
		slot.occupySpace();
	}

}
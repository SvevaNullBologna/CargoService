package main.java;

import java.util.ArrayList;


public class Slots{
	private ArrayList<Slot> slotList;
	public Slots() {
		slotList = new ArrayList<Slot>();
		for(int i=1; i<=5; i++) {
			switch(i) {
			case 1 : slotList.add(new Slot(i,1,2,1,1,"right")); break;
			case 2 : slotList.add(new Slot(i,1,3,1,4,"left")); break;
			case 3 : slotList.add(new Slot(i,3,3,3,1,"right")); break;
			case 4 : slotList.add(new Slot(i,3,4,3,4,"left")); break;
			case 5 : slotList.add(new Slot(i,2,5,0,0,"up")); break;//doesn't get used. In case of problems, goes back to home position
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
	
	public String getSlotPickupPositionById(int id) {
		return slotList.get(id-1).getPickUpPosition();
	}
	
	public String getSlotPickupDirectionById(int id) {
		return slotList.get(id-1).getPickupDir();
	}
	
	public String getSlotPositionBySlot(Slot slot) {
		return slot.getPosition();
	}
	
	public String getSlotPickupPositionBySlot(Slot slot) {
		return slot.getPickUpPosition();
	}
	
	public String getSlotPickupDirectionBySlot(Slot slot) {
		return slot.getPickupDir();
	}
	
	public int getPickupPositionXById(int id) {
		return getSlotById(id).getPickupX();
	}
	
	public int getPickupPositionYById(int id) {
		return getSlotById(id).getPickupY();
	}
	
	public int getAvaiableSlotID(){
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
package main.java;


public class Slot{
	private int id;
	private boolean avaiable;
	private int positionx;
	private int positiony;
	private int pickupX;
	private int pickupY;
	private String pickup_dir;
	
	public Slot(int id, int positionx, int positiony, int pickupX, int pickupY, String pickup_dir) {
		if(id == 5 ) {
			this.avaiable = false;
		}
		else {
			this.avaiable = true;
		}
		this.id = id;
		this.positionx = positionx;
		this.positiony = positiony;
		this.pickupX = pickupX;
		this.pickupY = pickupY;
		this.pickup_dir = pickup_dir;
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public boolean isAvaiable(){
		return this.avaiable;
	}
	
	public void occupySpace(){
		avaiable = false;
	}
	
	public int getX() {
		return this.positionx;
	}
	
	public int getY() {
		return this.positiony;
	}
	
	
	public int getPickupX(){
		return this.pickupX;
	}
	
	public int getPickupY(){
		return this.pickupY;
	}
	
	public String getPickupDir() {//down, up, left, right
		return this.pickup_dir;
	}
	
	public String getPosition() {
		return "(" + this.positionx + "," + this.positiony + ")";
	}
	
	public String getPickUpPosition() {
		return "(" + this.pickupX + "," + this.pickupY + ")";
	}
	
	public String toString() {
		return ("" + this.getId());
	}
	
	
}
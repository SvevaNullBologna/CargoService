package main.java;


public class Slot{
	private int id;
	private boolean avaiable;
	private int positionx;
	private int positiony;
	
	public Slot(int id, int positionx, int positiony) {
		if(id == 5 ) {
			this.avaiable = false;
		}
		else {
			this.avaiable = true;
		}
		this.id = id;
		this.positionx = positionx;
		this.positiony = positiony;
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
	
	public String getPosition() {
		return "(" + this.positionx + "," + this.positiony + ")";
	}
	
	public String toString() {
		return ("" + this.getId());
	}
	
	
}
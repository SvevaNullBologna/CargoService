package main.java;


public class Slot{
	private int id;
	private boolean avaiable;
	private int depositX;
	private int depositY;
	private String deposit_dir;
	
	public Slot(int id, int depositX, int depositY, String deposit_dir) {
		if(id == 5 ) {
			this.avaiable = false;
		}
		else {
			this.avaiable = true;
		}
		this.id = id;
		this.depositX = depositX;
		this.depositY = depositY;
		this.deposit_dir = deposit_dir;
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
	
	public int getDepositX(){
		return this.depositX;
	}
	
	public int getDepositY(){
		return this.depositY;
	}
	
	public String getDepositDir() {//down, up, left, right
		return this.deposit_dir;
	}
	
	public String getDepositPosition() {
		return "(" + this.depositX + "," + this.depositY + ")";
	}
	
	public String toString() {
		return ("" + this.getId());
	}
	
	
}
package main.java;


public class Slot{
	private int id;
	private int numberOfSpaces;
	private boolean avaiable;
	
	public Slot(int id, int numberOfSpaces) {
		if(id == 5 ) {
			this.avaiable = false;
		}
		else {
			this.avaiable = true;
		}
		this.id = id;
		this.numberOfSpaces = numberOfSpaces;
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public boolean isAvaiable(){
		return this.avaiable;
	}
	
	public void occupySpace(){
		this.numberOfSpaces = this.numberOfSpaces - 1;
		if(this.numberOfSpaces <= 0) {
			this.avaiable = false;
		}
	}
	
	public String toString() {
		return this.getId().toString;
	}
}
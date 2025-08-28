package test.java;

import java.util.Random;

public class RandomDistance {
	
	public static int getDistanceValue() {
		Random rand = new Random();
		return rand.nextInt(111);
	}
}
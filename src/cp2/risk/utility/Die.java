package cp2.risk.utility;

import java.util.Random;

public class Die {
	
	private static Die instance = null;
	
	public static Die getInstance() {
		if (instance == null) instance = new Die();
		return instance;
	}
	
	private Random rand;
	
	private Die() {
		rand = new Random();
	}
	
	public int rollDie() {
		return rand.nextInt(5) + 1;
	}

}

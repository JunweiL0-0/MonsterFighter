package main.java.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the medicine item.
 */
public class Medicine extends GameItem {
	
	int addHealth;
	Map<Integer, String> effects;
	
	public Medicine(String name, int price) {
		super(name, price);
<<<<<<< HEAD
		this.name = name;
=======
	
>>>>>>> 647707f7323d117b2f2de15e2fe3ba6b8805e07d
		
	}
	
	public static Medicine effects(){
<<<<<<< HEAD
		effects = new HashMap<>();
=======
		return null;
>>>>>>> 647707f7323d117b2f2de15e2fe3ba6b8805e07d
		
	}
	
	public void consumeItem(Medicine med) {
		
	}
	
	
	
	public String getDescription() {
		return this.getName();
	}

	
}

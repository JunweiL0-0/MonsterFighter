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
		this.name = name;
		
	}
	
	public static Medicine effects(){
		effects = new HashMap<>();
		
	}
	
	public void consumeItem(Medicine med) {
		
	}
	
	
	
	public String getDescription() {
		return this.getName();
	}

	
}

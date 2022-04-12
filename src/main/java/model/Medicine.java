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
	
		
	}
	
	public static Medicine effects(){
		return null;
		
	}
	
	public void consumeItem(Medicine med) {
		
	}
	
	
	
	public String getDescription() {
		return this.getName();
	}

	
}

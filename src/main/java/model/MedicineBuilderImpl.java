package main.java.model;

/**
 * A MedicineBuilderImpl class which implement the interface MedicineBuilder.
 * We can create a new medicine instance by chaining all the properties together.
 * Example:
 * MedicineBuilderImpl builder = new MedicineBuilderImpl();
 * Medicine medicine = new builder.price(3).name("Medicine").....build()
 * Nice and handy :)
 */
public class MedicineBuilderImpl implements MedicineBuilder{
	private String name;
    private int price;
    private int rarity;
    private int effect;


	/**
	 * Store the name of the medicine for later construction.
	 *
	 * @param name a string represents the name of the medicine
	 * @return the instance itself (MedicineBuilder) but with the medicine name store in it.
	 */
	public MedicineBuilder name(String name) {
		// TODO Auto-generated method stub
		this.name = name;
		return this;
	}

	/**
	 * Store the price of the medicine for later construction.
	 *
	 * @param price an integer represents the price of the medicine
	 * @return the instance itself (MedicineBuilder) but with the medicine price store in it.
	 */
	public MedicineBuilder price(int price) {
		// TODO Auto-generated method stub
		this.price = price;
		return this;
	}

	/**
	 * Store the rarity of the medicine for later construction.
	 *
	 * @param rarity an integer represents the rarity of the medicine
	 * @return the instance itself (MedicineBuilder) but with the medicine rarity store in it.
	 */
	public MedicineBuilder rarity(int rarity) {
		// TODO Auto-generated method stub
		this.rarity = rarity;
		return this;
	}

	/**
	 * Store the effect of the medicine for later construction.
	 *
	 * @param effect an integer represents the effect of the medicine
	 * @return the instance itself (MedicineBuilder) but with the medicine effect store in it.
	 */
	public MedicineBuilder effect(int effect) {
		// TODO Auto-generated method stub
		this.effect = effect;
		return this;
	}

	/**
	 * Construct a new Medicine instance with all the knowledge stored in the MedicineBuilder instance.
	 *
	 * @return a newly created Medicine instance.
	 */
	public Medicine build() {
		// TODO Auto-generated method stub
		return new Medicine(this.name,this.price,this.effect,this.rarity);
	}
}

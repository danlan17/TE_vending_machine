package sellables;

public class Drink extends Sellables{

	public Drink(String slot, String name, String price) {
		super(slot, name, price);
	}
	
	public String getMessage() {
		return "Glug Glug, Yum!";
	}
}

package sellables;

public class Chip extends Sellables{
	
	public Chip(String slot, String name, String price) {
		super(slot, name, price);
	}
	
	public String getMessage() {
		return "Crunch Crunch, Yum!";
	}

}

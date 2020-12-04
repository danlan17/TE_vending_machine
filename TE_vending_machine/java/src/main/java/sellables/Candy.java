package sellables;

public class Candy extends Sellables{
	
	public Candy(String slot, String name, String price) {
		super(slot, name, price);
	}

	public String getMessage() {
		return "Munch Munch, Yum!";
	}
}

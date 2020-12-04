package sellables;

public class Gum extends Sellables{

	public Gum(String slot, String name, String price) {
		super(slot, name, price);
	}
	
	public String getMessage() {
		return "Chew Chew, Yum!";
	}
}

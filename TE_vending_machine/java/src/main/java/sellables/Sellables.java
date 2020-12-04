package sellables;

import java.math.BigDecimal;

public abstract class Sellables {
	
	private String slot;
	private String name;
	private BigDecimal price;
	private int quantity = 5;
	
	public Sellables(String slot, String name, String price) {
		this.slot = slot;
		this.name = name;
		this.price = new BigDecimal(price);
	}
	
	public String getSlot() {
		return this.slot;
	}
	public String getName() {
		return this.name;
	}
	public BigDecimal getPrice() {
		return this.price;
	}
	public int getQuantity() {
		return this.quantity;
	}
	
	public abstract String getMessage();
	
	public void vend(int i) {
		this.quantity -= i;
	}

}

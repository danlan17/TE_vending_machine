package sellables;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

public class SellablesTest {

	@Test
	public void testConstructor() {
		
		Sellables chip = new Chip("A1", "Kettle", "2.00");
		Sellables drink = new Drink("B2", "Coke Zero", "1.50");
		Sellables candy = new Candy("C3", "Snickers", "2.00");
		Sellables gum = new Gum("D4", "5 Gum", "1.50");
		BigDecimal expectedPrice = new BigDecimal("1.50");
		
		assertEquals("A1", chip.getSlot());
		assertEquals(expectedPrice, drink.getPrice());
		assertEquals("Snickers", candy.getName());
		assertEquals("Chew Chew, Yum!", gum.getMessage());
	}
	
	@Test
	public void testGetMessage() {
		
		Sellables chip = new Chip("A1", "Kettle", "2.00");
		Sellables drink = new Drink("B2", "Coke Zero", "1.50");
		Sellables candy = new Candy("C3", "Snickers", "2.00");
		Sellables gum = new Gum("D4", "5 Gum", "1.50");
		
		assertEquals("Chew Chew, Yum!", gum.getMessage());
		assertEquals("Munch Munch, Yum!", candy.getMessage());
		assertEquals("Glug Glug, Yum!", drink.getMessage());
		assertEquals("Crunch Crunch, Yum!", chip.getMessage());
	}
	
	@Test
	public void testVend() {
		
		Sellables chip = new Chip("A1", "Kettle", "2.00");
		chip.vend(5);
		
		assertEquals(0, chip.getQuantity());
	}

}

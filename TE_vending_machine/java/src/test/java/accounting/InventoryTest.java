package accounting;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import sellables.Sellables;

public class InventoryTest {

	@Test
	public void testInitilize() {
		
		Inventory goods = new Inventory();
		goods.initialize();
		
		Sellables item1 = goods.getItemMap().get("U-Chews");
		Sellables item2 = goods.getItemMap().get("Crunchie");
		int index1 = goods.getIndexMap().get("U-Chews");
		int index2 = goods.getIndexMap().get("Crunchie");
		
		String expected1 = "\tD1\tU-Chews              $0.85\tQuantity: 5";
		String expected2 = "\tB4\tCrunchie             $1.75	Quantity: 5";
		String actual1 = goods.getItemDisplay()[index1];
		String actual2 = goods.getItemDisplay()[index2];
		
		//Tests for creation and filling of arrays and map
		assertTrue(goods.getItemNames().length > 0);
		assertTrue(goods.getItemDisplay().length > 0);
		assertTrue(goods.getItemMap().size() > 0);
		assertTrue(goods.getIndexMap().size() > 0);
		
		//Tests for correct products and index locations
		assertTrue(goods.getItemNames()[15].equals("Triplemint"));
		assertTrue(goods.getItemMap().containsKey("Potato Crisps"));
		assertTrue(goods.getIndexMap().containsKey("Cola"));
		assertEquals(Integer.valueOf(8), goods.getIndexMap().get("Cola"));
		
		//Tests for proper display format
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		
	}

	@Test
	public void testUpdate() {
		
		Inventory goods = new Inventory();
		goods.initialize();
		Sellables item1 = goods.getItemMap().get("Stackers");
		Sellables item2 = goods.getItemMap().get("Cola");
		int index1 = goods.getIndexMap().get("Stackers");
		int index2 = goods.getIndexMap().get("Cola");
		
		item1.vend(2);
		item2.vend(5);
		goods.update("Stackers");
		goods.update("Cola");
		
		String display1 = goods.getItemDisplay()[index1];
		String display2 = goods.getItemDisplay()[index2];
		
		//Tests for proper quantity update in display after item vend
		assertEquals('3', display1.charAt(display1.length()-1));
		assertEquals("SOLD OUT", display2.substring(display2.length()-8));
	}
	
	@Test
	public void testVendProduct() {
		
		Funds account = new Funds();
		Inventory stash = new Inventory();
		stash.initialize();
		
		account.addBalance(BigDecimal.valueOf(10));
		Sellables item = stash.getItemMap().get("Cola");
		Map<String, Integer> sales = stash.getSales();
		
		stash.vendProduct(item, account);
		stash.vendProduct(item, account);
		
		//Tests for proper sales map key and value update
		assertTrue(sales.containsKey("Cola"));
		assertEquals((Integer) 2, sales.get("Cola"));
		
	}
}

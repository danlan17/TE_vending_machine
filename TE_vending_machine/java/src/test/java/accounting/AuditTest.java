package accounting;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

import sellables.Sellables;

public class AuditTest {

	@Test
	public void testRecord() {
		
		Funds account = new Funds();
		Inventory stash = new Inventory();
		stash.initialize();
		Audit tracker = new Audit();
		
		Sellables item = stash.getItemMap().get("Cola");
		stash.vendProduct(item, account);
		String action = item.getName() + " " + item.getSlot();
		
		tracker.record(account, action, "test_log");
		
		File file = new File("test_log.txt");
		
		String actual = "";
		
		try (Scanner input = new Scanner(file)) {
			actual = input.nextLine();
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
		
		//Tests for file creation and is logged to when function called
		assertTrue(file.exists());
		assertTrue(!actual.isEmpty());
	}

	@Test
	public void testReport() {
		
		Funds account = new Funds();
		Inventory stash = new Inventory();
		stash.initialize();
		Audit tracker = new Audit();
		
		Sellables item = stash.getItemMap().get("Cola");
		stash.vendProduct(item, account);
		
		tracker.report(stash);
		
		File dir = new File("Sales Report");
		
		//Tests for file creation and is logged to when function called
		assertTrue(dir.exists());
		assertTrue(dir.length() > 0);
		
	}
	
}

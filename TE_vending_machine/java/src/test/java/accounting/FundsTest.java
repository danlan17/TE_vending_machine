package accounting;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

public class FundsTest {

	@Test
	public void testBalance() {
		
		Funds account = new Funds();
		
		account.addBalance(BigDecimal.valueOf(5));
		BigDecimal expected1 = BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP);
		account.updatePreBalance();
		
		
		//Tests for proper update after balance manipulation
		assertEquals(expected1, account.getBalance());
		assertEquals(expected1, account.getPreBalance());
		
		account.purchase(BigDecimal.valueOf(2.50));
		BigDecimal expected2 = BigDecimal.valueOf(2.50).setScale(2, RoundingMode.HALF_UP);
		assertEquals(expected2, account.getBalance());
	}

		
	@Test
	public void testMakeChange() {
		
		Funds account = new Funds();
		
		account.addBalance(BigDecimal.valueOf(4.40));
		
		String expected1 = "Change ($4.40):\nQuarters: 17\nDimes: 1\nNickels: 1\n";
		BigDecimal expected2 = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP);
		String expected3 = "Change ($5.10):\nQuarters: 20\nDimes: 1\n";
		
		//Tests for proper change return of least number of coins and ending balance of $0.00;
		assertEquals(expected1, account.makeChange());
		assertEquals(expected2, account.getBalance());
		
		account.addBalance(BigDecimal.valueOf(5.10));
		assertEquals(expected3, account.makeChange());
	}
	
	@Test
	public void testProcessMoney() {
		
		Funds account = new Funds();
		
		BigDecimal expected1 = BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP);
		BigDecimal expected2 = BigDecimal.valueOf(6).setScale(2, RoundingMode.HALF_UP);
		BigDecimal expected3 = BigDecimal.valueOf(16).setScale(2, RoundingMode.HALF_UP);
		
		//Tests for correct funds added
		account.processMoney("$1");
		assertEquals(expected1, account.getBalance());
		
		account.processMoney("$5");
		assertEquals(expected2, account.getBalance());
		
		account.processMoney("$10");
		assertEquals(expected3, account.getBalance());
	}
}

package accounting;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Funds {
	
	private static BigDecimal quarter = new BigDecimal(0.25).setScale(2, RoundingMode.HALF_UP);
	private static BigDecimal dime = new BigDecimal(0.10).setScale(2, RoundingMode.HALF_UP);
	private static BigDecimal nickel = new BigDecimal(0.05).setScale(2, RoundingMode.HALF_UP);
	private BigDecimal balance = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
	private BigDecimal preBalance = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	public BigDecimal getPreBalance() {
		return this.preBalance;
	}
	
	public void updatePreBalance() {
		this.preBalance = this.balance;
	}
	
	public void addBalance(BigDecimal amountAdded) {
		updatePreBalance();
		this.balance = this.balance.add(amountAdded).setScale(2, RoundingMode.HALF_UP);
	}
	
	public void purchase(BigDecimal price) {
		updatePreBalance();
		this.balance = this.balance.subtract(price).setScale(2, RoundingMode.HALF_UP);
	}
	
	public String makeChange() {
		
		BigDecimal total = this.balance.setScale(2, RoundingMode.HALF_UP);
		BigDecimal[] qr = total.divideAndRemainder(quarter);
		int numQuarters = qr[0].setScale(2, RoundingMode.HALF_UP).intValue();
		BigDecimal remain = qr[1].setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal[] dr = remain.divideAndRemainder(dime);
		int numDimes = dr[0].setScale(2, RoundingMode.HALF_UP).intValue();	
		BigDecimal remain2 = dr[1].setScale(2, RoundingMode.HALF_UP);
		int numNickels = remain2.divide(nickel, 2, RoundingMode.HALF_UP).intValue();
	
		String change = String.format("Change ($%s):\n", total);
		
		if (numQuarters > 0) {
			change += String.format("Quarters: %s\n", numQuarters);
		}
		if (numDimes > 0) {
			change += String.format("Dimes: %s\n", numDimes);
		}
		if (numNickels > 0) {
			change += String.format("Nickels: %s\n", numNickels);
		}
		this.balance = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP);;
		return change;
	}
	
	public void processMoney(String selection) {

		if (selection.equals("$1")) {
			addBalance(BigDecimal.valueOf(1));
		} 
		else if (selection.equals("$5")) {
			addBalance(BigDecimal.valueOf(5));
		} 
		else if (selection.equals("$10")) {
			addBalance(BigDecimal.valueOf(10));
		}
		if (!selection.equals("Back")) {
			System.out.println("\nCurrent Money Provided: $" + getBalance());
		}
	}
}

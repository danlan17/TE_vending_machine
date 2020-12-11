package com.techelevator;

import java.math.BigDecimal;
import java.util.Map;

import com.techelevator.view.Menu;

import accounting.Audit;
import accounting.Funds;
import accounting.Inventory;
import sellables.Sellables;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			"Exit", "Sales Report"};
	
	private static final String[] PURCHASE_MENU = { "Feed Money", "Select Product", "Finish Transaction", "Back"};
	private static final String[] ADD_FUNDS_MENU = { "$1", "$5", "$10", "Back"};
	private static String logName = "Log";
	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		Funds account = new Funds();
		Audit tracker = new Audit();
		Inventory stash = new Inventory(); 
		stash.initialize();
		
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			System.out.println("\nYou Have Selected: " + choice + "\n");

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				
				for (String item : stash.getItemDisplay()) {
					System.out.println(item);
				}
			} 
			else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				String selection = "";

				while (!selection.equals("Back")) {
					System.out.println("\nCurrent Money Provided: " + account.getBalance());
					selection = (String) menu.getChoiceFromOptions(PURCHASE_MENU);
					
					if (selection.equals("Feed Money")) {
						System.out.println("\nYou Have Selected: Feed Money");
						fundsSelect(account, tracker);
					}
					else if (selection.equals("Select Product")) {
						System.out.println("\nYou Have Selected: Select Product");
						String name = (String) menu.getChoiceFromOptions(stash.getItemNames());
						if (name.equals("Back")) {
							continue;
						}
						productSelect(stash, account, name, tracker);
						
					} 
					else if (selection.equals("Finish Transaction")) {
						System.out.println("\nYou Have Selected: Finish Transaction\n");
						account.updatePreBalance();
						System.out.println(account.makeChange());
						tracker.record(account, "GIVE CHANGE:", logName);
						break;
					}
				}
			}
			else if (choice.equals("Exit")) {
				if (account.getBalance().compareTo(BigDecimal.valueOf(0)) > 0) {
					System.out.println("\nYour Current Balance Is: " + account.getBalance() +
							"\nPlease Finish Your Transaction To Recieve Your Change");
				}
				else {
					System.out.println("\nThank You For Using Vendo-Matic 800!");
					System.exit(1);
				}
			}
			else if (choice.equals("Sales Report")) {
				tracker.report(stash);
			}
		}
	}
	
	public void fundsSelect(Funds account, Audit tracker) {
		
		String fundsOption = "";
		
		while (!fundsOption.equals("Back")) {
			fundsOption = (String) menu.getChoiceFromOptions(ADD_FUNDS_MENU);
			account.processMoney(fundsOption);
			if (fundsOption.equals("$1") || fundsOption.equals("$5") || fundsOption.equals("$10")) {
				tracker.record(account, "FEED MONEY:", logName);
			}
		}
		
	}
	
	public void productSelect(Inventory stash, Funds account, String name, Audit tracker) {
		
		Sellables item = stash.getItemMap().get(name);
		String action = item.getName() + " " + item.getSlot();
		
		if (item.getPrice().compareTo(account.getBalance()) > 0) {
			System.out.println("\nNot Enough Funds");
		}
		else if (item.getQuantity() < 1) {
			System.out.println("\nITEM SOLD OUT");
		}
		else {
			System.out.println("\nVending");
			System.out.println("-------");
			stash.vendProduct(item, account);
			tracker.record(account, action, logName);
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}

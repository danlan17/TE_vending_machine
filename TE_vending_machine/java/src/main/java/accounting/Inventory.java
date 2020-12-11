package accounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sellables.Candy;
import sellables.Chip;
import sellables.Drink;
import sellables.Gum;
import sellables.Sellables;

public class Inventory {
	
	/*Initialize() reads from csv file, extracts info and maps item names to instantiated item 
	 *objects, creates String[] of names for Purchase Menu, and calls createDisplay() to 
	 *create String[] of formatted item info for Display Menu and maps item names to their indices.
	 *Update() used to update itemDisplay after each vend to reflect proper quantity remaining.
	*/
	
	private String[] itemDisplay;
	private String[] itemNames;
	private Map<String, Sellables> itemMap = new HashMap<>();
	private Map<String, Integer> indexMap = new HashMap<>();
	private Map<String, Integer> sales = new HashMap<>();
	
	public String[] getItemDisplay() {
		return this.itemDisplay;
	}
	
	public String[] getItemNames() {
		return this.itemNames;
	}
	
	public Map<String, Sellables> getItemMap() {
		return this.itemMap;
	}
	
	public Map<String, Integer> getIndexMap() {
		return this.indexMap;
	}
	
	public Map<String, Integer> getSales() {
		return this.sales;
	}
	
	public void initialize() {
		
		File input = new File("vendingmachine.csv");
		
		if (!input.exists()) {
			System.out.println("File not found");
			System.exit(1);
		}
		
		try (Scanner dataFile = new Scanner(input)) {
			
			List<String> tempNames = new ArrayList<>();
			
			while (dataFile.hasNextLine()) {
				
				String[] info = dataFile.nextLine().split("\\|");
				String slot = info[0];
				String name = info[1];
				String price = info[2];
				String type = info[3];
				
				tempNames.add(name);
				
				if (type.equals("Candy")) {
					itemMap.put(name, new Candy(slot, name, price));
				}
				else if (type.equals("Chip")) {
					itemMap.put(name, new Chip(slot, name, price));
				}
				else if (type.equals("Drink")) {
					itemMap.put(name, new Drink(slot, name, price));
				}
				else if (type.equals("Gum")) {
					itemMap.put(name, new Gum(slot, name, price));
				}
			}
			tempNames.add("Back");
			this.itemNames = tempNames.toArray(new String[tempNames.size()]);
		}
		catch (FileNotFoundException e) {
			System.out.println("File could not be opened");
		}
		createDisplay();
	}
	
	private String displayFormat(Sellables item) {
		
		String quant = String.valueOf(item.getQuantity());
		if (quant.equals("0")) {
			quant = "SOLD OUT";
		}
		String name = String.format("%-" + 20 + "s", item.getName());
		String display = String.format("%s\t%s $%s\tQuantity: %s", item.getSlot(), 
				name, item.getPrice(), quant);
		
		return display;
	}
	
	private void createDisplay() {
		
		List<String> tempDisplay = new ArrayList<>();
		
		for (int i=0; i < itemNames.length-1; i++) {
			
			Sellables item = itemMap.get(itemNames[i]);
			String display = displayFormat(item);
			
			tempDisplay.add(display);
			indexMap.put(itemNames[i], i);
		}
		this.itemDisplay = tempDisplay.toArray(new String[tempDisplay.size()]);
	}
		
	public void update(String name) {
		
		Sellables item = this.itemMap.get(name);
		String display = displayFormat(item);
		int index = this.indexMap.get(name);
		this.itemDisplay[index] = display;
	}
	
	public void vendProduct(Sellables item, Funds account) {
		
		item.vend(1);
		account.purchase(item.getPrice());
		update(item.getName());
		
		if (!this.sales.containsKey(item.getName())) {
			this.sales.put(item.getName(), 1);
		}
		else {
			int sold = sales.get(item.getName());
			this.sales.put(item.getName(), sold + 1);
		}
		String message = String.format("\n%s \tCost: $%s\tBalance: $%s\n\n%s", item.getName(), item.getPrice(),
				account.getBalance(), item.getMessage());
		
		System.out.println(message);
	}
	
}

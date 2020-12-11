package accounting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import sellables.Sellables;

public class Audit {
	
	private static DateTimeFormatter logFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
	private static DateTimeFormatter salesFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy--hh-mm-ss-a");
	
	public void record(Funds account, String action, String fileName) {
		
		String fileSave = fileName + ".txt";
		File outputFile = new File(fileSave);
		LocalDateTime time = LocalDateTime.now();
		String now = time.format(logFormat);
		String actionFormat = String.format("%-" + 20 + "s", action);
		
		String log = String.format("%s \t%s \t\t$%s\t$%s", now, actionFormat, account.getPreBalance(), account.getBalance());
		
		try (PrintWriter dataOutput = new PrintWriter(new FileOutputStream(outputFile, true))) {
			
			dataOutput.println(log);
		}
		catch (FileNotFoundException e) {
			System.err.println("File could not be opened");
		}
	}
	
	public void report(Inventory stash) {
		
		LocalDateTime time = LocalDateTime.now();
		File dir = new File("Sales Report");
		
		if (!dir.exists()) {
			try {
				dir.mkdir();
			}
			catch (Exception e) {
				System.err.println("Directory could not be created");
			}
		}
		
		String fileName = time.format(salesFormat) + "_Sales.txt";
		File outputFile = new File(dir, fileName);
		
		if (!outputFile.exists()) {
			try {
				outputFile.createNewFile();
			}
			catch (IOException e) {
				System.err.println("File could not be created");
			}
		}
		
		try (PrintWriter dataOutput = new PrintWriter(outputFile)) {
			
			Sellables product;
			BigDecimal total = new BigDecimal(0);
			
			for (Map.Entry<String, Integer> item : stash.getSales().entrySet()) {
				
				product = stash.getItemMap().get(item.getKey());
				BigDecimal num = BigDecimal.valueOf(item.getValue());
				
				dataOutput.println(item.getKey() + "|" + item.getValue());
				total = total.add(num.multiply(product.getPrice())).setScale(2, RoundingMode.HALF_UP);
			}
			dataOutput.println("\n**TOTAL SALES**\n$" + total);
		}
		catch (FileNotFoundException e) {
			System.err.println("File could not be opened");
		}
		System.out.println("\nFile Can Be Found In 'Sales Report' Directory");
	}

}

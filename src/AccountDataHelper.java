import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

// This class handles the retrieval and update of account data (so the other classes don't have to
// worry about it)
public class AccountDataHelper {
	
	//storing indexes for what is where in the account data file
	private final int ACCOUNT_NUMBER_INDEX = 0;
	private final int ACCOUNT_NAME_INDEX = 1;
	private final int PIN_INDEX = 2;
	private final int BALANCE_INDEX = 3;
	
	//This is the path to the account data file
	private final String ACCOUNT_DATA_FILE_PATH = "AccountDataFile";

	// retrieves bank account data from the account data file and passes it back as an object
	public BankAccount getAccount(String targetaccountnumber)
	{

		try 
		{
			//BufferedReader reader = new BufferedReader(new FileReader("AccountDataFile"));
			Scanner in = new Scanner(new FileReader(ACCOUNT_DATA_FILE_PATH));
			in.nextLine(); //skipping header row
			while(in.hasNext())
			{
				String[] accountData = in.nextLine().split(",");
				String currentAccountNumber = accountData[ACCOUNT_NUMBER_INDEX];
				
				if (Objects.equals(currentAccountNumber, targetaccountnumber)) //means we found the account number we're looking for
				{
					String currentAccountName = accountData[ACCOUNT_NAME_INDEX];
					String currentPIN = accountData[PIN_INDEX];
					BigDecimal currentBalance = new BigDecimal(accountData[BALANCE_INDEX]);
					BankAccount targetBankAccount = new BankAccount(currentAccountNumber, currentAccountName, currentPIN, currentBalance);
					return targetBankAccount;
				}
			}
			
			in.close();
			
		} 
		
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//TODO: Handle this better (when an account is not found)
		return new BankAccount("","","", new BigDecimal("0.00"));


	}
	
	// takes in a BankAccount object and updates the relevant record in the data file based on the account number
	public void updateAccountInfo(BankAccount account)
	{
		//TODO: Make this work.
		try
		{
			Scanner file = new Scanner(new FileReader(ACCOUNT_DATA_FILE_PATH));
			StringBuilder builder = new StringBuilder();
			
			//skipping headers
			builder.append(file.nextLine());
			builder.append(System.lineSeparator());
			
			while (file.hasNext())
			{
				//get current line
				String currentLine = file.nextLine();
				
				//get current line's account number
				String[] accountData = currentLine.split(",");
				String currentAccountNumber = accountData[ACCOUNT_NUMBER_INDEX];
				
				// if the current line's account number is not the one we want to update, then
				// just push it to the stringbuilder
				if(!Objects.equals(currentAccountNumber,account.getAccountNumber()))
				{
					builder.append(currentLine);
					builder.append(System.lineSeparator());
				}
				
				else
				{
					// build the updated account data line
					ArrayList<String> updatedLine = new ArrayList<String>();
					updatedLine.add(account.getAccountNumber());
					updatedLine.add(account.getAccountName());
					updatedLine.add(account.getPIN());
					updatedLine.add(account.getBalance().toString());
					
					// join and push the line to the string builder
					String updatedLineAsString = String.join(",",updatedLine);
					builder.append(updatedLineAsString);
					builder.append(System.lineSeparator());
				}
			}
			
			file.close();
			
			FileWriter writer = new FileWriter(ACCOUNT_DATA_FILE_PATH);
			writer.write(builder.toString());
			writer.close();
		} 
		
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

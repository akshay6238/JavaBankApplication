import java.io.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;

// This class handles the retrieval and update of account data (so the other classes don't have to
// worry about it
public class AccountDataHelper {
	
	//storing indexes for what is where in the account data file
	private final int ACCOUNT_NUMBER_INDEX = 0;
	private final int ACCOUNT_NAME_INDEX = 1;
	private final int PIN_INDEX = 2;
	private final int BALANCE_INDEX = 3;

	// retrieves bank account data from the account data file and passes it back as an object
	public BankAccount getAccount(String targetaccountnumber)
	{

		try 
		{
			//BufferedReader reader = new BufferedReader(new FileReader("AccountDataFile"));
			Scanner in = new Scanner(new FileReader("AccountDataFile"));
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
		
	}
}

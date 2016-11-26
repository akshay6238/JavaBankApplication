import java.math.BigDecimal;
import java.text.NumberFormat;

public class ATM {

	public static void main(String[] args) 
	{ 
		// initialize classes
		atmScreen = new Screen();
	    atmWithdrawalTray = new WithdrawalTray();
	    atmDepositSlot = new DepositSlot();
	    
	    // go to main menu
		routeToMainMenu();
		
	}
	
	public static BankAccount currentAccount;
	public static Screen atmScreen;
	public static WithdrawalTray atmWithdrawalTray;
	public static DepositSlot atmDepositSlot;
	
	private static void routeToMainMenu()
	{
		MainMenu atmMainMenu = new MainMenu();
		
		// first check if the current account is set. If it's not, then we need to ask for it
		if (currentAccount == null)
		{
			String enteredAccountNumber = atmMainMenu.AccountAuthenticate(atmScreen);
			// create a data helper to access the account data
			AccountDataHelper helper = new AccountDataHelper();
			//set the current account based on what the helper passes back
			currentAccount = helper.getAccount(enteredAccountNumber);
		}
		
		int mainSelection;
		mainSelection = atmMainMenu.displayMenu(atmScreen);
		routeMainSelection(mainSelection);
	}
	
	// handles the main menu
	private static void routeMainSelection(int mainSelection) 
	{
		switch(mainSelection)
		{
			case 1:
				BalanceInquiry();
				break;
			case 2:
				routeToWithdrawalMenu();
				break;
			case 3:
				routeToDepositMenu();
				break;
			case 4:
				Exit();
				break;
			default:
				System.out.println("Invalid selection. Returning to main menu");
				routeToMainMenu();
			
		}
		
	}

	
	private static void routeToDepositMenu()
	{
		// TODO Auto-generated method stub
		DepositMenu atmDepositMenu = new DepositMenu();
		int depositMenuSelection;
		depositMenuSelection = atmDepositMenu.displayMenu(atmScreen);
		routeDepositMenuSelection(depositMenuSelection);
		
	}

	private static void routeDepositMenuSelection(int depositMenuSelection)
	{
		 if(depositMenuSelection <= 0)
		 {
			 System.out.println("Deposit Cancelled. Returning to Main Menu.");
			 routeToMainMenu();
		 }
		 else
		 {
			 // TODO: Have this wait for two minutes for confirmation
			 System.out.println("Thank you. Please insert envelope into tray...");
			 atmDepositSlot.acceptDeposit();
			 
			 
			 BigDecimal depositAmount = new BigDecimal((double)depositMenuSelection / 100);
			 currentAccount.Deposit(depositAmount);
			 
			 // commit transaction to database
			 AccountDataHelper helper = new AccountDataHelper();
			 helper.updateAccountInfo(currentAccount);
			 
			 //let the user know what happened.
			 System.out.println("Deposit complete! Your balance is now: " + currencyFormat(currentAccount.getBalance()));
			 System.out.println("Returning to main menu...");
			 //return to main menu
			 routeToMainMenu();
		 }
	}

	private static void routeToWithdrawalMenu()
	{
		// TODO Auto-generated method stub
		
	}

	// outputs the balance and routes back to the main menu
	private static void BalanceInquiry()
	{
		BigDecimal currentBalance = currentAccount.getBalance();
		System.out.println("Your current balance is: " + currencyFormat(currentBalance));
		routeToMainMenu();

	}

	// formats a BigDecimal as currency (for output purposes)
	public static String currencyFormat(BigDecimal n) 
	{
	    return NumberFormat.getCurrencyInstance().format(n);
	}
	
	// exits the application
	private static void Exit()
	{
		System.exit(0);
	}


}

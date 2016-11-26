import java.math.BigDecimal;
import java.math.RoundingMode;
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
		//return to main menu
		routeToMainMenu();
	}

	private static void routeDepositMenuSelection(int depositMenuSelection)
	{
		 if(depositMenuSelection <= 0)
		 {
			 atmScreen.Display("Deposit Cancelled. Returning to Main Menu.");
			 routeToMainMenu();
		 }
		 else
		 {
			 // TODO: Have this wait for two minutes for confirmation
			 atmScreen.Display("Thank you. Please insert envelope into tray...");
			 atmDepositSlot.acceptDeposit();
			 
			 
			 BigDecimal depositAmount = new BigDecimal((double)depositMenuSelection / 100);
			 
			 //set the number of decimal places to 2 in order to not have floating point errors.
			 depositAmount = depositAmount.setScale(2, RoundingMode.FLOOR);
			 currentAccount.Deposit(depositAmount);
			 
			 // commit transaction to database
			 AccountDataHelper helper = new AccountDataHelper();
			 helper.updateAccountInfo(currentAccount);
			 
			 //let the user know what happened.
			 atmScreen.Display("Deposit complete! Your balance is now: " + currencyFormat(currentAccount.getBalance()));
			 atmScreen.Display("Returning to main menu...");
			 
		 }
	}

	private static void routeToWithdrawalMenu()
	{
		// TODO Auto-generated method stub
		WithdrawalMenu atmWithdrawalMenu = new WithdrawalMenu();
		
		int withdrawalMenuSelection;
		withdrawalMenuSelection =  atmWithdrawalMenu.displayMenu(atmScreen);
		routeWithdrawalMenuSelection(withdrawalMenuSelection);
		
		//return to main menu
		routeToMainMenu();
	}

	private static void routeWithdrawalMenuSelection(int withdrawalMenuSelection) {
		// TODO Auto-generated method stub
		BigDecimal withdrawalAmount = new BigDecimal(0);
		
		switch(withdrawalMenuSelection)
		{
			case 1:
				withdrawalAmount = new BigDecimal(20);
				break;
			case 2:
				withdrawalAmount = new BigDecimal(40);
				break;
			case 3:
				withdrawalAmount = new BigDecimal(60);
				break;
			case 4:
				withdrawalAmount = new BigDecimal(100);
				break;
			case 5:
				withdrawalAmount = new BigDecimal(200);
				break;
			case 6:
				atmScreen.Display("Withdrawal Cancelled. Returning to Main Menu.");
				routeToMainMenu();
				break;
			
		}
		
		atmScreen.Display("Thank you. Please take your cash below...");
		atmWithdrawalTray.dispenseMoney();
		currentAccount.withdraw(withdrawalAmount);
		
		AccountDataHelper helper = new AccountDataHelper();
		helper.updateAccountInfo(currentAccount);
		
		atmScreen.Display("Withdrawal complete! Your balance is now: " + currencyFormat(currentAccount.getBalance()));
		atmScreen.Display("Returning to main menu...");

		
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
		atmScreen.Display("Thank you. Have a nice day!");
		System.exit(0);
	}


}

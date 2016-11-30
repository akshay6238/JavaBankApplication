import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Objects;

public class ATM {

	public static void main(String[] args) 
	{ 
		// initialize classes
		atmScreen = new Screen();
	    atmWithdrawalTray = new WithdrawalTray();
	    atmDepositSlot = new DepositSlot();
            
            // initialize loading of cash dispenser at beginning of day with 500 $20 bills
            atmWithdrawalTray.initializeAvailableBills();
	    
	    // go to main menu
            routeToMainMenu();
		
	}
	
	public static BankAccount currentAccount;
	public static Screen atmScreen;
	public static WithdrawalTray atmWithdrawalTray;
	public static DepositSlot atmDepositSlot;
	private static boolean sessionAuthenticated;
	
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
                        
                        while (currentAccount.getAccountNumber().isEmpty() || currentAccount.getAccountNumber().equals("")) 
                        {
                            currentAccount = helper.getAccount(enteredAccountNumber);
                            
                           if (currentAccount.getAccountNumber().isEmpty() || currentAccount.getAccountNumber().equals("")) 
                           {
                               atmScreen.Display("That account number does not exist in our records. Returning to the Main Menu.\n");
                               
                               enteredAccountNumber = atmMainMenu.AccountAuthenticate(atmScreen);
                           }
                        }
			
			//check if the current session is authenticated. If it's not, then we need to ask for a PIN:
			if (!sessionAuthenticated)
			{
				String enteredPIN = atmMainMenu.PinAuthenticate(atmScreen);
				if(Objects.equals(enteredPIN, currentAccount.getPIN()))
				{
					sessionAuthenticated = true;
                                        atmScreen.Display("Welcome, " + currentAccount.getAccountName() + ".\n");
				}
				else
				{
					atmScreen.Display("PIN authentication failed. Returning to Main Menu.\n");
					currentAccount = null;
					routeToMainMenu();
				}
			}
		}
		
		if (currentAccount != null && sessionAuthenticated)
		{
			int mainSelection;
			mainSelection = atmMainMenu.displayMenu(atmScreen);
			routeMainSelection(mainSelection);
		}
		
		else
		{
			routeToMainMenu();
		}
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
                                currentAccount = null;
                                sessionAuthenticated = false;
                                routeToMainMenu();
				break;
			default:
				System.out.println("Invalid selection. Returning to Main Menu.\n");
				routeToMainMenu();
			
		}
		
	}

	
	private static void routeToDepositMenu()
	{
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
			 atmScreen.Display("Returning to Main Menu...\n");
			 
		 }
	}

	private static void routeToWithdrawalMenu()
	{
		WithdrawalMenu atmWithdrawalMenu = new WithdrawalMenu();
		
		int withdrawalMenuSelection;
		withdrawalMenuSelection =  atmWithdrawalMenu.displayMenu(atmScreen);
		routeWithdrawalMenuSelection(withdrawalMenuSelection);
		
		//return to main menu
		routeToMainMenu();
	}

	private static void routeWithdrawalMenuSelection(int withdrawalMenuSelection) {
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
				atmScreen.Display("Withdrawal Cancelled. Returning to Main Menu.\n");
				routeToMainMenu();
				break;
			
		}
		// Validation that acccount has enough funds and ATM has enough $20 bills
                if (withdrawalAmount.compareTo(currentAccount.getBalance()) <= 0 && withdrawalAmount.divide(new BigDecimal(20)).compareTo(atmWithdrawalTray.getAvailableBills()) <= 0)
                {
                    atmScreen.Display("Thank you. Please take your cash below...");
                    atmWithdrawalTray.dispenseMoney();
                    
                    atmWithdrawalTray.setAvailableBills(atmWithdrawalTray.getAvailableBills().subtract(withdrawalAmount.divide(new BigDecimal(20))));
                    
                    currentAccount.withdraw(withdrawalAmount);
                    
                    AccountDataHelper helper = new AccountDataHelper();
                    helper.updateAccountInfo(currentAccount);
                    
                    atmScreen.Display("Withdrawal complete! Your balance is now: " + currencyFormat(currentAccount.getBalance()));
                    atmScreen.Display("Returning to Main Menu...\n");
                }
                else if (withdrawalAmount.divide(new BigDecimal(20)).compareTo(atmWithdrawalTray.getAvailableBills()) > 0) 
                {
                    atmScreen.Display("The ATM does not contain enough money to satisfy the requested amount, sorry for the inconvenience. Please choose a smaller amount to withdraw.\n");
                    routeToWithdrawalMenu();                    
                }
                else
                {
                    atmScreen.Display("Insufficient Funds in your account. Please choose a smaller amount to Withdraw.\n");
                    routeToWithdrawalMenu();
                }
	}

	// outputs the balance and routes back to the main menu
	private static void BalanceInquiry()
	{
		BigDecimal currentBalance = currentAccount.getBalance();
		System.out.println("Your current balance is: " + currencyFormat(currentBalance) + "\n");
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

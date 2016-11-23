
public class ATM {

	public static void main(String[] args) 
	{ 
		System.out.println(System.getProperty("user.dir"));
		
		// TODO Auto-generated method stub
		MainMenu atmMainMenu = new MainMenu();
		//just some code for testing the menus
		Screen atmScreen = new Screen();
		String enteredAccountNumber = atmMainMenu.AccountAuthenticate(atmScreen);
		
		// create a data helper to access the account data
		AccountDataHelper helper = new AccountDataHelper();
		//set the current account based on what the helper passes back
		currentAccount = helper.getAccount(enteredAccountNumber);
		
		int mainSelection;
		mainSelection = atmMainMenu.displayMenu(atmScreen);
		
		
		
		
	}
	
	public static BankAccount currentAccount;
	


}

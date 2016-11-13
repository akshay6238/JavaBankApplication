
public class ATM {

	public static void main(String[] args) 
	{ 
		// TODO Auto-generated method stub
		MainMenu atmMainMenu = new MainMenu();
		//just some code for testing the menus
		Screen atmScreen = new Screen();
		atmMainMenu.AccountAuthenticate(atmScreen);
		int mainSelection;
		mainSelection = atmMainMenu.displayMenu(atmScreen);
		
		
		
		
	}

}

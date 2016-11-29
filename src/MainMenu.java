public class MainMenu extends Menu
{
	 
	private String[] menuText = {"For balance inquiry press 1","To make a withdrawal press 2"
			,"To make a deposit press 3", "To exit press 4"};

	@Override
	public int displayMenu(Screen atmScreen) 
	{ int selection = 0;
		atmScreen.Display(menuText); 
		while(selection < 1 || selection > 4)
		{
			selection = super.input.nextInt();
			if (selection < 1 || selection > 4)
			{
				atmScreen.Display("Invalid pick");
			}
		}

		return selection;
	}
	
	//Welcome method that gets account number from user
	public String AccountAuthenticate(Screen atmScreen)
	{   boolean validNum = false;
		String accountNum="";
		atmScreen.Display( "Welcome, please enter your account number: ");
		while(validNum == false)
		{
			accountNum= super.input.next();
			if (accountNum.length() == 5)
				{
					validNum = true;
					return accountNum;
				}
			else
				{
				    atmScreen.Display("Invalid account number, enter a valid 5 digit account number: ");
				}
		}
		return accountNum;
	//pin screen	
	}
        
	public String PinAuthenticate(Screen atmScreen)
	{ 
                boolean validNum = false;
		String pin="";
		atmScreen.Display("Please enter your 5 digit pin number: ");
		while(validNum == false)
		{
			pin= super.input.next();
			if (pin.length() == 5)
				{
					validNum = true;
					return pin;
				}
			else
				{
					atmScreen.Display("Invalid pin number, enter valid 5 digit pin number: ");
				}
		}
		
		return pin;
	}
	
	
	
}

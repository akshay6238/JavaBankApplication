
public class DepositMenu extends Menu
{
	
	@Override
	public int displayMenu(Screen atmScreen)
	{
		int depositAmount = 0;
		atmScreen.Display("Enter deposit amount (in cents) or if you wish to cancel enter 0:");
		depositAmount = super.input.nextInt();
		
		return depositAmount;
		
	}

}
